package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.AccountTransction;
import com.compsis.repository.AccountTransctionRepository;
import com.compsis.service.AccountTransctionService;
import com.compsis.service.dto.AccountTransctionDTO;
import com.compsis.service.mapper.AccountTransctionMapper;
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

import com.compsis.domain.enumeration.TransactionType;
/**
 * Test class for the AccountTransctionResource REST controller.
 *
 * @see AccountTransctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class AccountTransctionResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.CREDIT;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.DEBIT;

    @Autowired
    private AccountTransctionRepository accountTransctionRepository;

    @Autowired
    private AccountTransctionMapper accountTransctionMapper;

    @Autowired
    private AccountTransctionService accountTransctionService;

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

    private MockMvc restAccountTransctionMockMvc;

    private AccountTransction accountTransction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountTransctionResource accountTransctionResource = new AccountTransctionResource(accountTransctionService);
        this.restAccountTransctionMockMvc = MockMvcBuilders.standaloneSetup(accountTransctionResource)
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
    public static AccountTransction createEntity(EntityManager em) {
        AccountTransction accountTransction = new AccountTransction()
            .amount(DEFAULT_AMOUNT)
            .origin(DEFAULT_ORIGIN)
            .transactionType(DEFAULT_TRANSACTION_TYPE);
        return accountTransction;
    }

    @Before
    public void initTest() {
        accountTransction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountTransction() throws Exception {
        int databaseSizeBeforeCreate = accountTransctionRepository.findAll().size();

        // Create the AccountTransction
        AccountTransctionDTO accountTransctionDTO = accountTransctionMapper.toDto(accountTransction);
        restAccountTransctionMockMvc.perform(post("/api/account-transctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTransctionDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountTransction in the database
        List<AccountTransction> accountTransctionList = accountTransctionRepository.findAll();
        assertThat(accountTransctionList).hasSize(databaseSizeBeforeCreate + 1);
        AccountTransction testAccountTransction = accountTransctionList.get(accountTransctionList.size() - 1);
        assertThat(testAccountTransction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAccountTransction.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testAccountTransction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void createAccountTransctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountTransctionRepository.findAll().size();

        // Create the AccountTransction with an existing ID
        accountTransction.setId(1L);
        AccountTransctionDTO accountTransctionDTO = accountTransctionMapper.toDto(accountTransction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountTransctionMockMvc.perform(post("/api/account-transctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTransctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountTransction in the database
        List<AccountTransction> accountTransctionList = accountTransctionRepository.findAll();
        assertThat(accountTransctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountTransctions() throws Exception {
        // Initialize the database
        accountTransctionRepository.saveAndFlush(accountTransction);

        // Get all the accountTransctionList
        restAccountTransctionMockMvc.perform(get("/api/account-transctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountTransction.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountTransction() throws Exception {
        // Initialize the database
        accountTransctionRepository.saveAndFlush(accountTransction);

        // Get the accountTransction
        restAccountTransctionMockMvc.perform(get("/api/account-transctions/{id}", accountTransction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountTransction.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountTransction() throws Exception {
        // Get the accountTransction
        restAccountTransctionMockMvc.perform(get("/api/account-transctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountTransction() throws Exception {
        // Initialize the database
        accountTransctionRepository.saveAndFlush(accountTransction);

        int databaseSizeBeforeUpdate = accountTransctionRepository.findAll().size();

        // Update the accountTransction
        AccountTransction updatedAccountTransction = accountTransctionRepository.findById(accountTransction.getId()).get();
        // Disconnect from session so that the updates on updatedAccountTransction are not directly saved in db
        em.detach(updatedAccountTransction);
        updatedAccountTransction
            .amount(UPDATED_AMOUNT)
            .origin(UPDATED_ORIGIN)
            .transactionType(UPDATED_TRANSACTION_TYPE);
        AccountTransctionDTO accountTransctionDTO = accountTransctionMapper.toDto(updatedAccountTransction);

        restAccountTransctionMockMvc.perform(put("/api/account-transctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTransctionDTO)))
            .andExpect(status().isOk());

        // Validate the AccountTransction in the database
        List<AccountTransction> accountTransctionList = accountTransctionRepository.findAll();
        assertThat(accountTransctionList).hasSize(databaseSizeBeforeUpdate);
        AccountTransction testAccountTransction = accountTransctionList.get(accountTransctionList.size() - 1);
        assertThat(testAccountTransction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAccountTransction.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testAccountTransction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountTransction() throws Exception {
        int databaseSizeBeforeUpdate = accountTransctionRepository.findAll().size();

        // Create the AccountTransction
        AccountTransctionDTO accountTransctionDTO = accountTransctionMapper.toDto(accountTransction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountTransctionMockMvc.perform(put("/api/account-transctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTransctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountTransction in the database
        List<AccountTransction> accountTransctionList = accountTransctionRepository.findAll();
        assertThat(accountTransctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountTransction() throws Exception {
        // Initialize the database
        accountTransctionRepository.saveAndFlush(accountTransction);

        int databaseSizeBeforeDelete = accountTransctionRepository.findAll().size();

        // Delete the accountTransction
        restAccountTransctionMockMvc.perform(delete("/api/account-transctions/{id}", accountTransction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountTransction> accountTransctionList = accountTransctionRepository.findAll();
        assertThat(accountTransctionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTransction.class);
        AccountTransction accountTransction1 = new AccountTransction();
        accountTransction1.setId(1L);
        AccountTransction accountTransction2 = new AccountTransction();
        accountTransction2.setId(accountTransction1.getId());
        assertThat(accountTransction1).isEqualTo(accountTransction2);
        accountTransction2.setId(2L);
        assertThat(accountTransction1).isNotEqualTo(accountTransction2);
        accountTransction1.setId(null);
        assertThat(accountTransction1).isNotEqualTo(accountTransction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTransctionDTO.class);
        AccountTransctionDTO accountTransctionDTO1 = new AccountTransctionDTO();
        accountTransctionDTO1.setId(1L);
        AccountTransctionDTO accountTransctionDTO2 = new AccountTransctionDTO();
        assertThat(accountTransctionDTO1).isNotEqualTo(accountTransctionDTO2);
        accountTransctionDTO2.setId(accountTransctionDTO1.getId());
        assertThat(accountTransctionDTO1).isEqualTo(accountTransctionDTO2);
        accountTransctionDTO2.setId(2L);
        assertThat(accountTransctionDTO1).isNotEqualTo(accountTransctionDTO2);
        accountTransctionDTO1.setId(null);
        assertThat(accountTransctionDTO1).isNotEqualTo(accountTransctionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountTransctionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountTransctionMapper.fromId(null)).isNull();
    }
}
