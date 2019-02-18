package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.BalanceCalculation;
import com.compsis.repository.BalanceCalculationRepository;
import com.compsis.service.BalanceCalculationService;
import com.compsis.service.dto.BalanceCalculationDTO;
import com.compsis.service.mapper.BalanceCalculationMapper;
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
 * Test class for the BalanceCalculationResource REST controller.
 *
 * @see BalanceCalculationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class BalanceCalculationResourceIntTest {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private BalanceCalculationRepository balanceCalculationRepository;

    @Autowired
    private BalanceCalculationMapper balanceCalculationMapper;

    @Autowired
    private BalanceCalculationService balanceCalculationService;

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

    private MockMvc restBalanceCalculationMockMvc;

    private BalanceCalculation balanceCalculation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BalanceCalculationResource balanceCalculationResource = new BalanceCalculationResource(balanceCalculationService);
        this.restBalanceCalculationMockMvc = MockMvcBuilders.standaloneSetup(balanceCalculationResource)
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
    public static BalanceCalculation createEntity(EntityManager em) {
        BalanceCalculation balanceCalculation = new BalanceCalculation()
            .balance(DEFAULT_BALANCE);
        return balanceCalculation;
    }

    @Before
    public void initTest() {
        balanceCalculation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBalanceCalculation() throws Exception {
        int databaseSizeBeforeCreate = balanceCalculationRepository.findAll().size();

        // Create the BalanceCalculation
        BalanceCalculationDTO balanceCalculationDTO = balanceCalculationMapper.toDto(balanceCalculation);
        restBalanceCalculationMockMvc.perform(post("/api/balance-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceCalculationDTO)))
            .andExpect(status().isCreated());

        // Validate the BalanceCalculation in the database
        List<BalanceCalculation> balanceCalculationList = balanceCalculationRepository.findAll();
        assertThat(balanceCalculationList).hasSize(databaseSizeBeforeCreate + 1);
        BalanceCalculation testBalanceCalculation = balanceCalculationList.get(balanceCalculationList.size() - 1);
        assertThat(testBalanceCalculation.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createBalanceCalculationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = balanceCalculationRepository.findAll().size();

        // Create the BalanceCalculation with an existing ID
        balanceCalculation.setId(1L);
        BalanceCalculationDTO balanceCalculationDTO = balanceCalculationMapper.toDto(balanceCalculation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceCalculationMockMvc.perform(post("/api/balance-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceCalculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BalanceCalculation in the database
        List<BalanceCalculation> balanceCalculationList = balanceCalculationRepository.findAll();
        assertThat(balanceCalculationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBalanceCalculations() throws Exception {
        // Initialize the database
        balanceCalculationRepository.saveAndFlush(balanceCalculation);

        // Get all the balanceCalculationList
        restBalanceCalculationMockMvc.perform(get("/api/balance-calculations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balanceCalculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }
    
    @Test
    @Transactional
    public void getBalanceCalculation() throws Exception {
        // Initialize the database
        balanceCalculationRepository.saveAndFlush(balanceCalculation);

        // Get the balanceCalculation
        restBalanceCalculationMockMvc.perform(get("/api/balance-calculations/{id}", balanceCalculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(balanceCalculation.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBalanceCalculation() throws Exception {
        // Get the balanceCalculation
        restBalanceCalculationMockMvc.perform(get("/api/balance-calculations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBalanceCalculation() throws Exception {
        // Initialize the database
        balanceCalculationRepository.saveAndFlush(balanceCalculation);

        int databaseSizeBeforeUpdate = balanceCalculationRepository.findAll().size();

        // Update the balanceCalculation
        BalanceCalculation updatedBalanceCalculation = balanceCalculationRepository.findById(balanceCalculation.getId()).get();
        // Disconnect from session so that the updates on updatedBalanceCalculation are not directly saved in db
        em.detach(updatedBalanceCalculation);
        updatedBalanceCalculation
            .balance(UPDATED_BALANCE);
        BalanceCalculationDTO balanceCalculationDTO = balanceCalculationMapper.toDto(updatedBalanceCalculation);

        restBalanceCalculationMockMvc.perform(put("/api/balance-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceCalculationDTO)))
            .andExpect(status().isOk());

        // Validate the BalanceCalculation in the database
        List<BalanceCalculation> balanceCalculationList = balanceCalculationRepository.findAll();
        assertThat(balanceCalculationList).hasSize(databaseSizeBeforeUpdate);
        BalanceCalculation testBalanceCalculation = balanceCalculationList.get(balanceCalculationList.size() - 1);
        assertThat(testBalanceCalculation.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingBalanceCalculation() throws Exception {
        int databaseSizeBeforeUpdate = balanceCalculationRepository.findAll().size();

        // Create the BalanceCalculation
        BalanceCalculationDTO balanceCalculationDTO = balanceCalculationMapper.toDto(balanceCalculation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalanceCalculationMockMvc.perform(put("/api/balance-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceCalculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BalanceCalculation in the database
        List<BalanceCalculation> balanceCalculationList = balanceCalculationRepository.findAll();
        assertThat(balanceCalculationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBalanceCalculation() throws Exception {
        // Initialize the database
        balanceCalculationRepository.saveAndFlush(balanceCalculation);

        int databaseSizeBeforeDelete = balanceCalculationRepository.findAll().size();

        // Delete the balanceCalculation
        restBalanceCalculationMockMvc.perform(delete("/api/balance-calculations/{id}", balanceCalculation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BalanceCalculation> balanceCalculationList = balanceCalculationRepository.findAll();
        assertThat(balanceCalculationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceCalculation.class);
        BalanceCalculation balanceCalculation1 = new BalanceCalculation();
        balanceCalculation1.setId(1L);
        BalanceCalculation balanceCalculation2 = new BalanceCalculation();
        balanceCalculation2.setId(balanceCalculation1.getId());
        assertThat(balanceCalculation1).isEqualTo(balanceCalculation2);
        balanceCalculation2.setId(2L);
        assertThat(balanceCalculation1).isNotEqualTo(balanceCalculation2);
        balanceCalculation1.setId(null);
        assertThat(balanceCalculation1).isNotEqualTo(balanceCalculation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceCalculationDTO.class);
        BalanceCalculationDTO balanceCalculationDTO1 = new BalanceCalculationDTO();
        balanceCalculationDTO1.setId(1L);
        BalanceCalculationDTO balanceCalculationDTO2 = new BalanceCalculationDTO();
        assertThat(balanceCalculationDTO1).isNotEqualTo(balanceCalculationDTO2);
        balanceCalculationDTO2.setId(balanceCalculationDTO1.getId());
        assertThat(balanceCalculationDTO1).isEqualTo(balanceCalculationDTO2);
        balanceCalculationDTO2.setId(2L);
        assertThat(balanceCalculationDTO1).isNotEqualTo(balanceCalculationDTO2);
        balanceCalculationDTO1.setId(null);
        assertThat(balanceCalculationDTO1).isNotEqualTo(balanceCalculationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(balanceCalculationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(balanceCalculationMapper.fromId(null)).isNull();
    }
}
