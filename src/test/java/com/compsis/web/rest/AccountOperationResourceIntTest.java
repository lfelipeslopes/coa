package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.AccountOperation;
import com.compsis.repository.AccountOperationRepository;
import com.compsis.service.AccountOperationService;
import com.compsis.service.dto.AccountOperationDTO;
import com.compsis.service.mapper.AccountOperationMapper;
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
 * Test class for the AccountOperationResource REST controller.
 *
 * @see AccountOperationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class AccountOperationResourceIntTest {

    private static final ZonedDateTime DEFAULT_OCCURRENCE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OCCURRENCE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private AccountOperationMapper accountOperationMapper;

    @Autowired
    private AccountOperationService accountOperationService;

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

    private MockMvc restAccountOperationMockMvc;

    private AccountOperation accountOperation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountOperationResource accountOperationResource = new AccountOperationResource(accountOperationService);
        this.restAccountOperationMockMvc = MockMvcBuilders.standaloneSetup(accountOperationResource)
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
    public static AccountOperation createEntity(EntityManager em) {
        AccountOperation accountOperation = new AccountOperation()
            .occurrenceDate(DEFAULT_OCCURRENCE_DATE);
        return accountOperation;
    }

    @Before
    public void initTest() {
        accountOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountOperation() throws Exception {
        int databaseSizeBeforeCreate = accountOperationRepository.findAll().size();

        // Create the AccountOperation
        AccountOperationDTO accountOperationDTO = accountOperationMapper.toDto(accountOperation);
        restAccountOperationMockMvc.perform(post("/api/account-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountOperationDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountOperation in the database
        List<AccountOperation> accountOperationList = accountOperationRepository.findAll();
        assertThat(accountOperationList).hasSize(databaseSizeBeforeCreate + 1);
        AccountOperation testAccountOperation = accountOperationList.get(accountOperationList.size() - 1);
        assertThat(testAccountOperation.getOccurrenceDate()).isEqualTo(DEFAULT_OCCURRENCE_DATE);
    }

    @Test
    @Transactional
    public void createAccountOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountOperationRepository.findAll().size();

        // Create the AccountOperation with an existing ID
        accountOperation.setId(1L);
        AccountOperationDTO accountOperationDTO = accountOperationMapper.toDto(accountOperation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountOperationMockMvc.perform(post("/api/account-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountOperation in the database
        List<AccountOperation> accountOperationList = accountOperationRepository.findAll();
        assertThat(accountOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountOperations() throws Exception {
        // Initialize the database
        accountOperationRepository.saveAndFlush(accountOperation);

        // Get all the accountOperationList
        restAccountOperationMockMvc.perform(get("/api/account-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].occurrenceDate").value(hasItem(sameInstant(DEFAULT_OCCURRENCE_DATE))));
    }
    
    @Test
    @Transactional
    public void getAccountOperation() throws Exception {
        // Initialize the database
        accountOperationRepository.saveAndFlush(accountOperation);

        // Get the accountOperation
        restAccountOperationMockMvc.perform(get("/api/account-operations/{id}", accountOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountOperation.getId().intValue()))
            .andExpect(jsonPath("$.occurrenceDate").value(sameInstant(DEFAULT_OCCURRENCE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAccountOperation() throws Exception {
        // Get the accountOperation
        restAccountOperationMockMvc.perform(get("/api/account-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountOperation() throws Exception {
        // Initialize the database
        accountOperationRepository.saveAndFlush(accountOperation);

        int databaseSizeBeforeUpdate = accountOperationRepository.findAll().size();

        // Update the accountOperation
        AccountOperation updatedAccountOperation = accountOperationRepository.findById(accountOperation.getId()).get();
        // Disconnect from session so that the updates on updatedAccountOperation are not directly saved in db
        em.detach(updatedAccountOperation);
        updatedAccountOperation
            .occurrenceDate(UPDATED_OCCURRENCE_DATE);
        AccountOperationDTO accountOperationDTO = accountOperationMapper.toDto(updatedAccountOperation);

        restAccountOperationMockMvc.perform(put("/api/account-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountOperationDTO)))
            .andExpect(status().isOk());

        // Validate the AccountOperation in the database
        List<AccountOperation> accountOperationList = accountOperationRepository.findAll();
        assertThat(accountOperationList).hasSize(databaseSizeBeforeUpdate);
        AccountOperation testAccountOperation = accountOperationList.get(accountOperationList.size() - 1);
        assertThat(testAccountOperation.getOccurrenceDate()).isEqualTo(UPDATED_OCCURRENCE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountOperation() throws Exception {
        int databaseSizeBeforeUpdate = accountOperationRepository.findAll().size();

        // Create the AccountOperation
        AccountOperationDTO accountOperationDTO = accountOperationMapper.toDto(accountOperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountOperationMockMvc.perform(put("/api/account-operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountOperationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountOperation in the database
        List<AccountOperation> accountOperationList = accountOperationRepository.findAll();
        assertThat(accountOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountOperation() throws Exception {
        // Initialize the database
        accountOperationRepository.saveAndFlush(accountOperation);

        int databaseSizeBeforeDelete = accountOperationRepository.findAll().size();

        // Delete the accountOperation
        restAccountOperationMockMvc.perform(delete("/api/account-operations/{id}", accountOperation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountOperation> accountOperationList = accountOperationRepository.findAll();
        assertThat(accountOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountOperation.class);
        AccountOperation accountOperation1 = new AccountOperation();
        accountOperation1.setId(1L);
        AccountOperation accountOperation2 = new AccountOperation();
        accountOperation2.setId(accountOperation1.getId());
        assertThat(accountOperation1).isEqualTo(accountOperation2);
        accountOperation2.setId(2L);
        assertThat(accountOperation1).isNotEqualTo(accountOperation2);
        accountOperation1.setId(null);
        assertThat(accountOperation1).isNotEqualTo(accountOperation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountOperationDTO.class);
        AccountOperationDTO accountOperationDTO1 = new AccountOperationDTO();
        accountOperationDTO1.setId(1L);
        AccountOperationDTO accountOperationDTO2 = new AccountOperationDTO();
        assertThat(accountOperationDTO1).isNotEqualTo(accountOperationDTO2);
        accountOperationDTO2.setId(accountOperationDTO1.getId());
        assertThat(accountOperationDTO1).isEqualTo(accountOperationDTO2);
        accountOperationDTO2.setId(2L);
        assertThat(accountOperationDTO1).isNotEqualTo(accountOperationDTO2);
        accountOperationDTO1.setId(null);
        assertThat(accountOperationDTO1).isNotEqualTo(accountOperationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountOperationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountOperationMapper.fromId(null)).isNull();
    }
}
