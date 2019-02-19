package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.FinancialAccount;
import com.compsis.repository.FinancialAccountRepository;
import com.compsis.service.FinancialAccountService;
import com.compsis.service.dto.FinancialAccountDTO;
import com.compsis.service.mapper.FinancialAccountMapper;
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

/**
 * Test class for the FinancialAccountResource REST controller.
 *
 * @see FinancialAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class FinancialAccountResourceIntTest {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private FinancialAccountRepository financialAccountRepository;

    @Autowired
    private FinancialAccountMapper financialAccountMapper;

    @Autowired
    private FinancialAccountService financialAccountService;

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

    private MockMvc restFinancialAccountMockMvc;

    private FinancialAccount financialAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinancialAccountResource financialAccountResource = new FinancialAccountResource(financialAccountService);
        this.restFinancialAccountMockMvc = MockMvcBuilders.standaloneSetup(financialAccountResource)
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
    public static FinancialAccount createEntity(EntityManager em) {
        FinancialAccount financialAccount = new FinancialAccount()
            .alias(DEFAULT_ALIAS)
            .balance(DEFAULT_BALANCE);
        return financialAccount;
    }

    @Before
    public void initTest() {
        financialAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinancialAccount() throws Exception {
        int databaseSizeBeforeCreate = financialAccountRepository.findAll().size();

        // Create the FinancialAccount
        FinancialAccountDTO financialAccountDTO = financialAccountMapper.toDto(financialAccount);
        restFinancialAccountMockMvc.perform(post("/api/financial-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialAccount in the database
        List<FinancialAccount> financialAccountList = financialAccountRepository.findAll();
        assertThat(financialAccountList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialAccount testFinancialAccount = financialAccountList.get(financialAccountList.size() - 1);
        assertThat(testFinancialAccount.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testFinancialAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createFinancialAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financialAccountRepository.findAll().size();

        // Create the FinancialAccount with an existing ID
        financialAccount.setId(1L);
        FinancialAccountDTO financialAccountDTO = financialAccountMapper.toDto(financialAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialAccountMockMvc.perform(post("/api/financial-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialAccount in the database
        List<FinancialAccount> financialAccountList = financialAccountRepository.findAll();
        assertThat(financialAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFinancialAccounts() throws Exception {
        // Initialize the database
        financialAccountRepository.saveAndFlush(financialAccount);

        // Get all the financialAccountList
        restFinancialAccountMockMvc.perform(get("/api/financial-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }
    
    @Test
    @Transactional
    public void getFinancialAccount() throws Exception {
        // Initialize the database
        financialAccountRepository.saveAndFlush(financialAccount);

        // Get the financialAccount
        restFinancialAccountMockMvc.perform(get("/api/financial-accounts/{id}", financialAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financialAccount.getId().intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFinancialAccount() throws Exception {
        // Get the financialAccount
        restFinancialAccountMockMvc.perform(get("/api/financial-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinancialAccount() throws Exception {
        // Initialize the database
        financialAccountRepository.saveAndFlush(financialAccount);

        int databaseSizeBeforeUpdate = financialAccountRepository.findAll().size();

        // Update the financialAccount
        FinancialAccount updatedFinancialAccount = financialAccountRepository.findById(financialAccount.getId()).get();
        // Disconnect from session so that the updates on updatedFinancialAccount are not directly saved in db
        em.detach(updatedFinancialAccount);
        updatedFinancialAccount
            .alias(UPDATED_ALIAS)
            .balance(UPDATED_BALANCE);
        FinancialAccountDTO financialAccountDTO = financialAccountMapper.toDto(updatedFinancialAccount);

        restFinancialAccountMockMvc.perform(put("/api/financial-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountDTO)))
            .andExpect(status().isOk());

        // Validate the FinancialAccount in the database
        List<FinancialAccount> financialAccountList = financialAccountRepository.findAll();
        assertThat(financialAccountList).hasSize(databaseSizeBeforeUpdate);
        FinancialAccount testFinancialAccount = financialAccountList.get(financialAccountList.size() - 1);
        assertThat(testFinancialAccount.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testFinancialAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFinancialAccount() throws Exception {
        int databaseSizeBeforeUpdate = financialAccountRepository.findAll().size();

        // Create the FinancialAccount
        FinancialAccountDTO financialAccountDTO = financialAccountMapper.toDto(financialAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancialAccountMockMvc.perform(put("/api/financial-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialAccount in the database
        List<FinancialAccount> financialAccountList = financialAccountRepository.findAll();
        assertThat(financialAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinancialAccount() throws Exception {
        // Initialize the database
        financialAccountRepository.saveAndFlush(financialAccount);

        int databaseSizeBeforeDelete = financialAccountRepository.findAll().size();

        // Delete the financialAccount
        restFinancialAccountMockMvc.perform(delete("/api/financial-accounts/{id}", financialAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinancialAccount> financialAccountList = financialAccountRepository.findAll();
        assertThat(financialAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialAccount.class);
        FinancialAccount financialAccount1 = new FinancialAccount();
        financialAccount1.setId(1L);
        FinancialAccount financialAccount2 = new FinancialAccount();
        financialAccount2.setId(financialAccount1.getId());
        assertThat(financialAccount1).isEqualTo(financialAccount2);
        financialAccount2.setId(2L);
        assertThat(financialAccount1).isNotEqualTo(financialAccount2);
        financialAccount1.setId(null);
        assertThat(financialAccount1).isNotEqualTo(financialAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialAccountDTO.class);
        FinancialAccountDTO financialAccountDTO1 = new FinancialAccountDTO();
        financialAccountDTO1.setId(1L);
        FinancialAccountDTO financialAccountDTO2 = new FinancialAccountDTO();
        assertThat(financialAccountDTO1).isNotEqualTo(financialAccountDTO2);
        financialAccountDTO2.setId(financialAccountDTO1.getId());
        assertThat(financialAccountDTO1).isEqualTo(financialAccountDTO2);
        financialAccountDTO2.setId(2L);
        assertThat(financialAccountDTO1).isNotEqualTo(financialAccountDTO2);
        financialAccountDTO1.setId(null);
        assertThat(financialAccountDTO1).isNotEqualTo(financialAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financialAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financialAccountMapper.fromId(null)).isNull();
    }
}
