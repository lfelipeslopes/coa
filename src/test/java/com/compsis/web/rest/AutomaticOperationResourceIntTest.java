package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.AutomaticOperation;
import com.compsis.repository.AutomaticOperationRepository;
import com.compsis.service.AutomaticOperationService;
import com.compsis.service.dto.AutomaticOperationDTO;
import com.compsis.service.mapper.AutomaticOperationMapper;
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
import java.util.List;


import static com.compsis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.compsis.domain.enumeration.AutomaticOperationStatus;
import com.compsis.domain.enumeration.AutomaticEvent;
import com.compsis.domain.enumeration.TransactionType;
/**
 * Test class for the AutomaticOperationResource REST controller.
 *
 * @see AutomaticOperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class AutomaticOperationResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_RECURRENT = false;
    private static final Boolean UPDATED_RECURRENT = true;

    private static final AutomaticOperationStatus DEFAULT_AUTOMATICT_OPERATION_STATUS = AutomaticOperationStatus.ACTIVATED;
    private static final AutomaticOperationStatus UPDATED_AUTOMATICT_OPERATION_STATUS = AutomaticOperationStatus.DISABLED;

    private static final AutomaticEvent DEFAULT_AUTOMATICT_EVENT = AutomaticEvent.RECHARGE;
    private static final AutomaticEvent UPDATED_AUTOMATICT_EVENT = AutomaticEvent.BLOCK;

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.CREDIT;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.DEBIT;

    @Autowired
    private AutomaticOperationRepository automaticOperationRepository;

    @Autowired
    private AutomaticOperationMapper automaticOperationMapper;

    @Autowired
    private AutomaticOperationService automaticOperationService;

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

    private MockMvc restAutomaticOperationMockMvc;

    private AutomaticOperation automaticOperation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutomaticOperationResource automaticOperationResource = new AutomaticOperationResource(automaticOperationService);
        this.restAutomaticOperationMockMvc = MockMvcBuilders.standaloneSetup(automaticOperationResource)
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
    public static AutomaticOperation createEntity(EntityManager em) {
        AutomaticOperation automaticOperation = new AutomaticOperation()
            .amount(DEFAULT_AMOUNT)
            .recurrent(DEFAULT_RECURRENT)
            .automatictOperationStatus(DEFAULT_AUTOMATICT_OPERATION_STATUS)
            .automatictEvent(DEFAULT_AUTOMATICT_EVENT)
            .transactionType(DEFAULT_TRANSACTION_TYPE);
        return automaticOperation;
    }

    @Before
    public void initTest() {
        automaticOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutomaticOperation() throws Exception {
        int databaseSizeBeforeCreate = automaticOperationRepository.findAll().size();

        // Create the AutomaticOperation
        AutomaticOperationDTO automaticOperationDTO = automaticOperationMapper.toDto(automaticOperation);
        restAutomaticOperationMockMvc.perform(post("/api/automatic-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automaticOperationDTO)))
            .andExpect(status().isCreated());

        // Validate the AutomaticOperation in the database
        List<AutomaticOperation> automaticOperationList = automaticOperationRepository.findAll();
        assertThat(automaticOperationList).hasSize(databaseSizeBeforeCreate + 1);
        AutomaticOperation testAutomaticOperation = automaticOperationList.get(automaticOperationList.size() - 1);
        assertThat(testAutomaticOperation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAutomaticOperation.isRecurrent()).isEqualTo(DEFAULT_RECURRENT);
        assertThat(testAutomaticOperation.getAutomatictOperationStatus()).isEqualTo(DEFAULT_AUTOMATICT_OPERATION_STATUS);
        assertThat(testAutomaticOperation.getAutomatictEvent()).isEqualTo(DEFAULT_AUTOMATICT_EVENT);
        assertThat(testAutomaticOperation.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void createAutomaticOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = automaticOperationRepository.findAll().size();

        // Create the AutomaticOperation with an existing ID
        automaticOperation.setId(1L);
        AutomaticOperationDTO automaticOperationDTO = automaticOperationMapper.toDto(automaticOperation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutomaticOperationMockMvc.perform(post("/api/automatic-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automaticOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomaticOperation in the database
        List<AutomaticOperation> automaticOperationList = automaticOperationRepository.findAll();
        assertThat(automaticOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAutomaticOperations() throws Exception {
        // Initialize the database
        automaticOperationRepository.saveAndFlush(automaticOperation);

        // Get all the automaticOperationList
        restAutomaticOperationMockMvc.perform(get("/api/automatic-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(automaticOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].recurrent").value(hasItem(DEFAULT_RECURRENT.booleanValue())))
            .andExpect(jsonPath("$.[*].automatictOperationStatus").value(hasItem(DEFAULT_AUTOMATICT_OPERATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].automatictEvent").value(hasItem(DEFAULT_AUTOMATICT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutomaticOperation() throws Exception {
        // Initialize the database
        automaticOperationRepository.saveAndFlush(automaticOperation);

        // Get the automaticOperation
        restAutomaticOperationMockMvc.perform(get("/api/automatic-operations/{id}", automaticOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(automaticOperation.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.recurrent").value(DEFAULT_RECURRENT.booleanValue()))
            .andExpect(jsonPath("$.automatictOperationStatus").value(DEFAULT_AUTOMATICT_OPERATION_STATUS.toString()))
            .andExpect(jsonPath("$.automatictEvent").value(DEFAULT_AUTOMATICT_EVENT.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutomaticOperation() throws Exception {
        // Get the automaticOperation
        restAutomaticOperationMockMvc.perform(get("/api/automatic-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutomaticOperation() throws Exception {
        // Initialize the database
        automaticOperationRepository.saveAndFlush(automaticOperation);

        int databaseSizeBeforeUpdate = automaticOperationRepository.findAll().size();

        // Update the automaticOperation
        AutomaticOperation updatedAutomaticOperation = automaticOperationRepository.findById(automaticOperation.getId()).get();
        // Disconnect from session so that the updates on updatedAutomaticOperation are not directly saved in db
        em.detach(updatedAutomaticOperation);
        updatedAutomaticOperation
            .amount(UPDATED_AMOUNT)
            .recurrent(UPDATED_RECURRENT)
            .automatictOperationStatus(UPDATED_AUTOMATICT_OPERATION_STATUS)
            .automatictEvent(UPDATED_AUTOMATICT_EVENT)
            .transactionType(UPDATED_TRANSACTION_TYPE);
        AutomaticOperationDTO automaticOperationDTO = automaticOperationMapper.toDto(updatedAutomaticOperation);

        restAutomaticOperationMockMvc.perform(put("/api/automatic-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automaticOperationDTO)))
            .andExpect(status().isOk());

        // Validate the AutomaticOperation in the database
        List<AutomaticOperation> automaticOperationList = automaticOperationRepository.findAll();
        assertThat(automaticOperationList).hasSize(databaseSizeBeforeUpdate);
        AutomaticOperation testAutomaticOperation = automaticOperationList.get(automaticOperationList.size() - 1);
        assertThat(testAutomaticOperation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAutomaticOperation.isRecurrent()).isEqualTo(UPDATED_RECURRENT);
        assertThat(testAutomaticOperation.getAutomatictOperationStatus()).isEqualTo(UPDATED_AUTOMATICT_OPERATION_STATUS);
        assertThat(testAutomaticOperation.getAutomatictEvent()).isEqualTo(UPDATED_AUTOMATICT_EVENT);
        assertThat(testAutomaticOperation.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutomaticOperation() throws Exception {
        int databaseSizeBeforeUpdate = automaticOperationRepository.findAll().size();

        // Create the AutomaticOperation
        AutomaticOperationDTO automaticOperationDTO = automaticOperationMapper.toDto(automaticOperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutomaticOperationMockMvc.perform(put("/api/automatic-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automaticOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomaticOperation in the database
        List<AutomaticOperation> automaticOperationList = automaticOperationRepository.findAll();
        assertThat(automaticOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutomaticOperation() throws Exception {
        // Initialize the database
        automaticOperationRepository.saveAndFlush(automaticOperation);

        int databaseSizeBeforeDelete = automaticOperationRepository.findAll().size();

        // Delete the automaticOperation
        restAutomaticOperationMockMvc.perform(delete("/api/automatic-operations/{id}", automaticOperation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutomaticOperation> automaticOperationList = automaticOperationRepository.findAll();
        assertThat(automaticOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomaticOperation.class);
        AutomaticOperation automaticOperation1 = new AutomaticOperation();
        automaticOperation1.setId(1L);
        AutomaticOperation automaticOperation2 = new AutomaticOperation();
        automaticOperation2.setId(automaticOperation1.getId());
        assertThat(automaticOperation1).isEqualTo(automaticOperation2);
        automaticOperation2.setId(2L);
        assertThat(automaticOperation1).isNotEqualTo(automaticOperation2);
        automaticOperation1.setId(null);
        assertThat(automaticOperation1).isNotEqualTo(automaticOperation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomaticOperationDTO.class);
        AutomaticOperationDTO automaticOperationDTO1 = new AutomaticOperationDTO();
        automaticOperationDTO1.setId(1L);
        AutomaticOperationDTO automaticOperationDTO2 = new AutomaticOperationDTO();
        assertThat(automaticOperationDTO1).isNotEqualTo(automaticOperationDTO2);
        automaticOperationDTO2.setId(automaticOperationDTO1.getId());
        assertThat(automaticOperationDTO1).isEqualTo(automaticOperationDTO2);
        automaticOperationDTO2.setId(2L);
        assertThat(automaticOperationDTO1).isNotEqualTo(automaticOperationDTO2);
        automaticOperationDTO1.setId(null);
        assertThat(automaticOperationDTO1).isNotEqualTo(automaticOperationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(automaticOperationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(automaticOperationMapper.fromId(null)).isNull();
    }
}
