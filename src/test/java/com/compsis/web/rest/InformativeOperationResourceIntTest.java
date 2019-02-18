package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.InformativeOperation;
import com.compsis.repository.InformativeOperationRepository;
import com.compsis.service.InformativeOperationService;
import com.compsis.service.dto.InformativeOperationDTO;
import com.compsis.service.mapper.InformativeOperationMapper;
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
import java.util.List;


import static com.compsis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InformativeOperationResource REST controller.
 *
 * @see InformativeOperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class InformativeOperationResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private InformativeOperationRepository informativeOperationRepository;

    @Autowired
    private InformativeOperationMapper informativeOperationMapper;

    @Autowired
    private InformativeOperationService informativeOperationService;

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

    private MockMvc restInformativeOperationMockMvc;

    private InformativeOperation informativeOperation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InformativeOperationResource informativeOperationResource = new InformativeOperationResource(informativeOperationService);
        this.restInformativeOperationMockMvc = MockMvcBuilders.standaloneSetup(informativeOperationResource)
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
    public static InformativeOperation createEntity(EntityManager em) {
        InformativeOperation informativeOperation = new InformativeOperation()
            .comments(DEFAULT_COMMENTS);
        return informativeOperation;
    }

    @Before
    public void initTest() {
        informativeOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformativeOperation() throws Exception {
        int databaseSizeBeforeCreate = informativeOperationRepository.findAll().size();

        // Create the InformativeOperation
        InformativeOperationDTO informativeOperationDTO = informativeOperationMapper.toDto(informativeOperation);
        restInformativeOperationMockMvc.perform(post("/api/informative-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(informativeOperationDTO)))
            .andExpect(status().isCreated());

        // Validate the InformativeOperation in the database
        List<InformativeOperation> informativeOperationList = informativeOperationRepository.findAll();
        assertThat(informativeOperationList).hasSize(databaseSizeBeforeCreate + 1);
        InformativeOperation testInformativeOperation = informativeOperationList.get(informativeOperationList.size() - 1);
        assertThat(testInformativeOperation.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createInformativeOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informativeOperationRepository.findAll().size();

        // Create the InformativeOperation with an existing ID
        informativeOperation.setId(1L);
        InformativeOperationDTO informativeOperationDTO = informativeOperationMapper.toDto(informativeOperation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformativeOperationMockMvc.perform(post("/api/informative-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(informativeOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformativeOperation in the database
        List<InformativeOperation> informativeOperationList = informativeOperationRepository.findAll();
        assertThat(informativeOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInformativeOperations() throws Exception {
        // Initialize the database
        informativeOperationRepository.saveAndFlush(informativeOperation);

        // Get all the informativeOperationList
        restInformativeOperationMockMvc.perform(get("/api/informative-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informativeOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }
    
    @Test
    @Transactional
    public void getInformativeOperation() throws Exception {
        // Initialize the database
        informativeOperationRepository.saveAndFlush(informativeOperation);

        // Get the informativeOperation
        restInformativeOperationMockMvc.perform(get("/api/informative-operations/{id}", informativeOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(informativeOperation.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInformativeOperation() throws Exception {
        // Get the informativeOperation
        restInformativeOperationMockMvc.perform(get("/api/informative-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformativeOperation() throws Exception {
        // Initialize the database
        informativeOperationRepository.saveAndFlush(informativeOperation);

        int databaseSizeBeforeUpdate = informativeOperationRepository.findAll().size();

        // Update the informativeOperation
        InformativeOperation updatedInformativeOperation = informativeOperationRepository.findById(informativeOperation.getId()).get();
        // Disconnect from session so that the updates on updatedInformativeOperation are not directly saved in db
        em.detach(updatedInformativeOperation);
        updatedInformativeOperation
            .comments(UPDATED_COMMENTS);
        InformativeOperationDTO informativeOperationDTO = informativeOperationMapper.toDto(updatedInformativeOperation);

        restInformativeOperationMockMvc.perform(put("/api/informative-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(informativeOperationDTO)))
            .andExpect(status().isOk());

        // Validate the InformativeOperation in the database
        List<InformativeOperation> informativeOperationList = informativeOperationRepository.findAll();
        assertThat(informativeOperationList).hasSize(databaseSizeBeforeUpdate);
        InformativeOperation testInformativeOperation = informativeOperationList.get(informativeOperationList.size() - 1);
        assertThat(testInformativeOperation.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingInformativeOperation() throws Exception {
        int databaseSizeBeforeUpdate = informativeOperationRepository.findAll().size();

        // Create the InformativeOperation
        InformativeOperationDTO informativeOperationDTO = informativeOperationMapper.toDto(informativeOperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformativeOperationMockMvc.perform(put("/api/informative-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(informativeOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformativeOperation in the database
        List<InformativeOperation> informativeOperationList = informativeOperationRepository.findAll();
        assertThat(informativeOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInformativeOperation() throws Exception {
        // Initialize the database
        informativeOperationRepository.saveAndFlush(informativeOperation);

        int databaseSizeBeforeDelete = informativeOperationRepository.findAll().size();

        // Delete the informativeOperation
        restInformativeOperationMockMvc.perform(delete("/api/informative-operations/{id}", informativeOperation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InformativeOperation> informativeOperationList = informativeOperationRepository.findAll();
        assertThat(informativeOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformativeOperation.class);
        InformativeOperation informativeOperation1 = new InformativeOperation();
        informativeOperation1.setId(1L);
        InformativeOperation informativeOperation2 = new InformativeOperation();
        informativeOperation2.setId(informativeOperation1.getId());
        assertThat(informativeOperation1).isEqualTo(informativeOperation2);
        informativeOperation2.setId(2L);
        assertThat(informativeOperation1).isNotEqualTo(informativeOperation2);
        informativeOperation1.setId(null);
        assertThat(informativeOperation1).isNotEqualTo(informativeOperation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformativeOperationDTO.class);
        InformativeOperationDTO informativeOperationDTO1 = new InformativeOperationDTO();
        informativeOperationDTO1.setId(1L);
        InformativeOperationDTO informativeOperationDTO2 = new InformativeOperationDTO();
        assertThat(informativeOperationDTO1).isNotEqualTo(informativeOperationDTO2);
        informativeOperationDTO2.setId(informativeOperationDTO1.getId());
        assertThat(informativeOperationDTO1).isEqualTo(informativeOperationDTO2);
        informativeOperationDTO2.setId(2L);
        assertThat(informativeOperationDTO1).isNotEqualTo(informativeOperationDTO2);
        informativeOperationDTO1.setId(null);
        assertThat(informativeOperationDTO1).isNotEqualTo(informativeOperationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(informativeOperationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(informativeOperationMapper.fromId(null)).isNull();
    }
}
