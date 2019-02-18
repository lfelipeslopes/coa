package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.BillingTariff;
import com.compsis.repository.BillingTariffRepository;
import com.compsis.service.BillingTariffService;
import com.compsis.service.dto.BillingTariffDTO;
import com.compsis.service.mapper.BillingTariffMapper;
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
 * Test class for the BillingTariffResource REST controller.
 *
 * @see BillingTariffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class BillingTariffResourceIntTest {

    private static final Integer DEFAULT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_DAY_OF_WEEK = 2;

    private static final ZonedDateTime DEFAULT_STARTED_IN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTED_IN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Autowired
    private BillingTariffRepository billingTariffRepository;

    @Autowired
    private BillingTariffMapper billingTariffMapper;

    @Autowired
    private BillingTariffService billingTariffService;

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

    private MockMvc restBillingTariffMockMvc;

    private BillingTariff billingTariff;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingTariffResource billingTariffResource = new BillingTariffResource(billingTariffService);
        this.restBillingTariffMockMvc = MockMvcBuilders.standaloneSetup(billingTariffResource)
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
    public static BillingTariff createEntity(EntityManager em) {
        BillingTariff billingTariff = new BillingTariff()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .startedIn(DEFAULT_STARTED_IN)
            .value(DEFAULT_VALUE);
        return billingTariff;
    }

    @Before
    public void initTest() {
        billingTariff = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingTariff() throws Exception {
        int databaseSizeBeforeCreate = billingTariffRepository.findAll().size();

        // Create the BillingTariff
        BillingTariffDTO billingTariffDTO = billingTariffMapper.toDto(billingTariff);
        restBillingTariffMockMvc.perform(post("/api/billing-tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingTariffDTO)))
            .andExpect(status().isCreated());

        // Validate the BillingTariff in the database
        List<BillingTariff> billingTariffList = billingTariffRepository.findAll();
        assertThat(billingTariffList).hasSize(databaseSizeBeforeCreate + 1);
        BillingTariff testBillingTariff = billingTariffList.get(billingTariffList.size() - 1);
        assertThat(testBillingTariff.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testBillingTariff.getStartedIn()).isEqualTo(DEFAULT_STARTED_IN);
        assertThat(testBillingTariff.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBillingTariffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingTariffRepository.findAll().size();

        // Create the BillingTariff with an existing ID
        billingTariff.setId(1L);
        BillingTariffDTO billingTariffDTO = billingTariffMapper.toDto(billingTariff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingTariffMockMvc.perform(post("/api/billing-tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingTariffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingTariff in the database
        List<BillingTariff> billingTariffList = billingTariffRepository.findAll();
        assertThat(billingTariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBillingTariffs() throws Exception {
        // Initialize the database
        billingTariffRepository.saveAndFlush(billingTariff);

        // Get all the billingTariffList
        restBillingTariffMockMvc.perform(get("/api/billing-tariffs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingTariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].startedIn").value(hasItem(sameInstant(DEFAULT_STARTED_IN))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getBillingTariff() throws Exception {
        // Initialize the database
        billingTariffRepository.saveAndFlush(billingTariff);

        // Get the billingTariff
        restBillingTariffMockMvc.perform(get("/api/billing-tariffs/{id}", billingTariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingTariff.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.startedIn").value(sameInstant(DEFAULT_STARTED_IN)))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingTariff() throws Exception {
        // Get the billingTariff
        restBillingTariffMockMvc.perform(get("/api/billing-tariffs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingTariff() throws Exception {
        // Initialize the database
        billingTariffRepository.saveAndFlush(billingTariff);

        int databaseSizeBeforeUpdate = billingTariffRepository.findAll().size();

        // Update the billingTariff
        BillingTariff updatedBillingTariff = billingTariffRepository.findById(billingTariff.getId()).get();
        // Disconnect from session so that the updates on updatedBillingTariff are not directly saved in db
        em.detach(updatedBillingTariff);
        updatedBillingTariff
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startedIn(UPDATED_STARTED_IN)
            .value(UPDATED_VALUE);
        BillingTariffDTO billingTariffDTO = billingTariffMapper.toDto(updatedBillingTariff);

        restBillingTariffMockMvc.perform(put("/api/billing-tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingTariffDTO)))
            .andExpect(status().isOk());

        // Validate the BillingTariff in the database
        List<BillingTariff> billingTariffList = billingTariffRepository.findAll();
        assertThat(billingTariffList).hasSize(databaseSizeBeforeUpdate);
        BillingTariff testBillingTariff = billingTariffList.get(billingTariffList.size() - 1);
        assertThat(testBillingTariff.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testBillingTariff.getStartedIn()).isEqualTo(UPDATED_STARTED_IN);
        assertThat(testBillingTariff.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingTariff() throws Exception {
        int databaseSizeBeforeUpdate = billingTariffRepository.findAll().size();

        // Create the BillingTariff
        BillingTariffDTO billingTariffDTO = billingTariffMapper.toDto(billingTariff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingTariffMockMvc.perform(put("/api/billing-tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingTariffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingTariff in the database
        List<BillingTariff> billingTariffList = billingTariffRepository.findAll();
        assertThat(billingTariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingTariff() throws Exception {
        // Initialize the database
        billingTariffRepository.saveAndFlush(billingTariff);

        int databaseSizeBeforeDelete = billingTariffRepository.findAll().size();

        // Delete the billingTariff
        restBillingTariffMockMvc.perform(delete("/api/billing-tariffs/{id}", billingTariff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillingTariff> billingTariffList = billingTariffRepository.findAll();
        assertThat(billingTariffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingTariff.class);
        BillingTariff billingTariff1 = new BillingTariff();
        billingTariff1.setId(1L);
        BillingTariff billingTariff2 = new BillingTariff();
        billingTariff2.setId(billingTariff1.getId());
        assertThat(billingTariff1).isEqualTo(billingTariff2);
        billingTariff2.setId(2L);
        assertThat(billingTariff1).isNotEqualTo(billingTariff2);
        billingTariff1.setId(null);
        assertThat(billingTariff1).isNotEqualTo(billingTariff2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingTariffDTO.class);
        BillingTariffDTO billingTariffDTO1 = new BillingTariffDTO();
        billingTariffDTO1.setId(1L);
        BillingTariffDTO billingTariffDTO2 = new BillingTariffDTO();
        assertThat(billingTariffDTO1).isNotEqualTo(billingTariffDTO2);
        billingTariffDTO2.setId(billingTariffDTO1.getId());
        assertThat(billingTariffDTO1).isEqualTo(billingTariffDTO2);
        billingTariffDTO2.setId(2L);
        assertThat(billingTariffDTO1).isNotEqualTo(billingTariffDTO2);
        billingTariffDTO1.setId(null);
        assertThat(billingTariffDTO1).isNotEqualTo(billingTariffDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billingTariffMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billingTariffMapper.fromId(null)).isNull();
    }
}
