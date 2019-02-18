package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.Passage;
import com.compsis.repository.PassageRepository;
import com.compsis.service.PassageService;
import com.compsis.service.dto.PassageDTO;
import com.compsis.service.mapper.PassageMapper;
import com.compsis.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.compsis.web.rest.TestUtil.sameInstant;
import static com.compsis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PassageResource REST controller.
 *
 * @see PassageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class PassageResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_AUTOMATIC_PASSAGE = false;
    private static final Boolean UPDATED_AUTOMATIC_PASSAGE = true;

    private static final Integer DEFAULT_LANE = 1;
    private static final Integer UPDATED_LANE = 2;

    private static final ZonedDateTime DEFAULT_OCCURRENCE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OCCURRENCE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PASSAGE_IDENTIFICATION = 1L;
    private static final Long UPDATED_PASSAGE_IDENTIFICATION = 2L;

    private static final String DEFAULT_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_PLATE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PROCESSED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PROCESSED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PassageRepository passageRepository;

    @Autowired
    private PassageMapper passageMapper;

    @Autowired
    private PassageService passageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPassageMockMvc;

    private Passage passage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PassageResource passageResource = new PassageResource(passageService);
        this.restPassageMockMvc = MockMvcBuilders.standaloneSetup(passageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passage createEntity(EntityManager em) {
        Passage passage = new Passage()
            .amount(DEFAULT_AMOUNT)
            .automaticPassage(DEFAULT_AUTOMATIC_PASSAGE)
            .lane(DEFAULT_LANE)
            .occurrenceDate(DEFAULT_OCCURRENCE_DATE)
            .passageIdentification(DEFAULT_PASSAGE_IDENTIFICATION)
            .plate(DEFAULT_PLATE)
            .processedAt(DEFAULT_PROCESSED_AT);
        return passage;
    }

    @Before
    public void initTest() {
        passage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassage() throws Exception {
        int databaseSizeBeforeCreate = passageRepository.findAll().size();

        // Create the Passage
        PassageDTO passageDTO = passageMapper.toDto(passage);
        restPassageMockMvc.perform(post("/api/passages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passageDTO)))
            .andExpect(status().isCreated());

        // Validate the Passage in the database
        List<Passage> passageList = passageRepository.findAll();
        assertThat(passageList).hasSize(databaseSizeBeforeCreate + 1);
        Passage testPassage = passageList.get(passageList.size() - 1);
        assertThat(testPassage.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPassage.isAutomaticPassage()).isEqualTo(DEFAULT_AUTOMATIC_PASSAGE);
        assertThat(testPassage.getLane()).isEqualTo(DEFAULT_LANE);
        assertThat(testPassage.getOccurrenceDate()).isEqualTo(DEFAULT_OCCURRENCE_DATE);
        assertThat(testPassage.getPassageIdentification()).isEqualTo(DEFAULT_PASSAGE_IDENTIFICATION);
        assertThat(testPassage.getPlate()).isEqualTo(DEFAULT_PLATE);
        assertThat(testPassage.getProcessedAt()).isEqualTo(DEFAULT_PROCESSED_AT);
    }

    @Test
    @Transactional
    public void createPassageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passageRepository.findAll().size();

        // Create the Passage with an existing ID
        passage.setId(1L);
        PassageDTO passageDTO = passageMapper.toDto(passage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassageMockMvc.perform(post("/api/passages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passage in the database
        List<Passage> passageList = passageRepository.findAll();
        assertThat(passageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPassages() throws Exception {
        // Initialize the database
        passageRepository.saveAndFlush(passage);

        // Get all the passageList
        restPassageMockMvc.perform(get("/api/passages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passage.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].automaticPassage").value(hasItem(DEFAULT_AUTOMATIC_PASSAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].lane").value(hasItem(DEFAULT_LANE)))
            .andExpect(jsonPath("$.[*].occurrenceDate").value(hasItem(sameInstant(DEFAULT_OCCURRENCE_DATE))))
            .andExpect(jsonPath("$.[*].passageIdentification").value(hasItem(DEFAULT_PASSAGE_IDENTIFICATION.intValue())))
            .andExpect(jsonPath("$.[*].plate").value(hasItem(DEFAULT_PLATE.toString())))
            .andExpect(jsonPath("$.[*].processedAt").value(hasItem(sameInstant(DEFAULT_PROCESSED_AT))));
    }
    
    @Test
    @Transactional
    public void getPassage() throws Exception {
        // Initialize the database
        passageRepository.saveAndFlush(passage);

        // Get the passage
        restPassageMockMvc.perform(get("/api/passages/{id}", passage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(passage.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.automaticPassage").value(DEFAULT_AUTOMATIC_PASSAGE.booleanValue()))
            .andExpect(jsonPath("$.lane").value(DEFAULT_LANE))
            .andExpect(jsonPath("$.occurrenceDate").value(sameInstant(DEFAULT_OCCURRENCE_DATE)))
            .andExpect(jsonPath("$.passageIdentification").value(DEFAULT_PASSAGE_IDENTIFICATION.intValue()))
            .andExpect(jsonPath("$.plate").value(DEFAULT_PLATE.toString()))
            .andExpect(jsonPath("$.processedAt").value(sameInstant(DEFAULT_PROCESSED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingPassage() throws Exception {
        // Get the passage
        restPassageMockMvc.perform(get("/api/passages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassage() throws Exception {
        // Initialize the database
        passageRepository.saveAndFlush(passage);

        int databaseSizeBeforeUpdate = passageRepository.findAll().size();

        // Update the passage
        Passage updatedPassage = passageRepository.findById(passage.getId()).get();
        // Disconnect from session so that the updates on updatedPassage are not directly saved in db
        em.detach(updatedPassage);
        updatedPassage
            .amount(UPDATED_AMOUNT)
            .automaticPassage(UPDATED_AUTOMATIC_PASSAGE)
            .lane(UPDATED_LANE)
            .occurrenceDate(UPDATED_OCCURRENCE_DATE)
            .passageIdentification(UPDATED_PASSAGE_IDENTIFICATION)
            .plate(UPDATED_PLATE)
            .processedAt(UPDATED_PROCESSED_AT);
        PassageDTO passageDTO = passageMapper.toDto(updatedPassage);

        restPassageMockMvc.perform(put("/api/passages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passageDTO)))
            .andExpect(status().isOk());

        // Validate the Passage in the database
        List<Passage> passageList = passageRepository.findAll();
        assertThat(passageList).hasSize(databaseSizeBeforeUpdate);
        Passage testPassage = passageList.get(passageList.size() - 1);
        assertThat(testPassage.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPassage.isAutomaticPassage()).isEqualTo(UPDATED_AUTOMATIC_PASSAGE);
        assertThat(testPassage.getLane()).isEqualTo(UPDATED_LANE);
        assertThat(testPassage.getOccurrenceDate()).isEqualTo(UPDATED_OCCURRENCE_DATE);
        assertThat(testPassage.getPassageIdentification()).isEqualTo(UPDATED_PASSAGE_IDENTIFICATION);
        assertThat(testPassage.getPlate()).isEqualTo(UPDATED_PLATE);
        assertThat(testPassage.getProcessedAt()).isEqualTo(UPDATED_PROCESSED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPassage() throws Exception {
        int databaseSizeBeforeUpdate = passageRepository.findAll().size();

        // Create the Passage
        PassageDTO passageDTO = passageMapper.toDto(passage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassageMockMvc.perform(put("/api/passages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passage in the database
        List<Passage> passageList = passageRepository.findAll();
        assertThat(passageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePassage() throws Exception {
        // Initialize the database
        passageRepository.saveAndFlush(passage);

        int databaseSizeBeforeDelete = passageRepository.findAll().size();

        // Delete the passage
        restPassageMockMvc.perform(delete("/api/passages/{id}", passage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Passage> passageList = passageRepository.findAll();
        assertThat(passageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passage.class);
        Passage passage1 = new Passage();
        passage1.setId(1L);
        Passage passage2 = new Passage();
        passage2.setId(passage1.getId());
        assertThat(passage1).isEqualTo(passage2);
        passage2.setId(2L);
        assertThat(passage1).isNotEqualTo(passage2);
        passage1.setId(null);
        assertThat(passage1).isNotEqualTo(passage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassageDTO.class);
        PassageDTO passageDTO1 = new PassageDTO();
        passageDTO1.setId(1L);
        PassageDTO passageDTO2 = new PassageDTO();
        assertThat(passageDTO1).isNotEqualTo(passageDTO2);
        passageDTO2.setId(passageDTO1.getId());
        assertThat(passageDTO1).isEqualTo(passageDTO2);
        passageDTO2.setId(2L);
        assertThat(passageDTO1).isNotEqualTo(passageDTO2);
        passageDTO1.setId(null);
        assertThat(passageDTO1).isNotEqualTo(passageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(passageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(passageMapper.fromId(null)).isNull();
    }
}
