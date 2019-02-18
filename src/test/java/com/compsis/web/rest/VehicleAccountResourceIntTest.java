package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.VehicleAccount;
import com.compsis.repository.VehicleAccountRepository;
import com.compsis.service.VehicleAccountService;
import com.compsis.service.dto.VehicleAccountDTO;
import com.compsis.service.mapper.VehicleAccountMapper;
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
 * Test class for the VehicleAccountResource REST controller.
 *
 * @see VehicleAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class VehicleAccountResourceIntTest {

    @Autowired
    private VehicleAccountRepository vehicleAccountRepository;

    @Autowired
    private VehicleAccountMapper vehicleAccountMapper;

    @Autowired
    private VehicleAccountService vehicleAccountService;

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

    private MockMvc restVehicleAccountMockMvc;

    private VehicleAccount vehicleAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleAccountResource vehicleAccountResource = new VehicleAccountResource(vehicleAccountService);
        this.restVehicleAccountMockMvc = MockMvcBuilders.standaloneSetup(vehicleAccountResource)
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
    public static VehicleAccount createEntity(EntityManager em) {
        VehicleAccount vehicleAccount = new VehicleAccount();
        return vehicleAccount;
    }

    @Before
    public void initTest() {
        vehicleAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleAccount() throws Exception {
        int databaseSizeBeforeCreate = vehicleAccountRepository.findAll().size();

        // Create the VehicleAccount
        VehicleAccountDTO vehicleAccountDTO = vehicleAccountMapper.toDto(vehicleAccount);
        restVehicleAccountMockMvc.perform(post("/api/vehicle-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleAccount in the database
        List<VehicleAccount> vehicleAccountList = vehicleAccountRepository.findAll();
        assertThat(vehicleAccountList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleAccount testVehicleAccount = vehicleAccountList.get(vehicleAccountList.size() - 1);
    }

    @Test
    @Transactional
    public void createVehicleAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleAccountRepository.findAll().size();

        // Create the VehicleAccount with an existing ID
        vehicleAccount.setId(1L);
        VehicleAccountDTO vehicleAccountDTO = vehicleAccountMapper.toDto(vehicleAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleAccountMockMvc.perform(post("/api/vehicle-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleAccount in the database
        List<VehicleAccount> vehicleAccountList = vehicleAccountRepository.findAll();
        assertThat(vehicleAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleAccounts() throws Exception {
        // Initialize the database
        vehicleAccountRepository.saveAndFlush(vehicleAccount);

        // Get all the vehicleAccountList
        restVehicleAccountMockMvc.perform(get("/api/vehicle-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleAccount.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getVehicleAccount() throws Exception {
        // Initialize the database
        vehicleAccountRepository.saveAndFlush(vehicleAccount);

        // Get the vehicleAccount
        restVehicleAccountMockMvc.perform(get("/api/vehicle-accounts/{id}", vehicleAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleAccount.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleAccount() throws Exception {
        // Get the vehicleAccount
        restVehicleAccountMockMvc.perform(get("/api/vehicle-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleAccount() throws Exception {
        // Initialize the database
        vehicleAccountRepository.saveAndFlush(vehicleAccount);

        int databaseSizeBeforeUpdate = vehicleAccountRepository.findAll().size();

        // Update the vehicleAccount
        VehicleAccount updatedVehicleAccount = vehicleAccountRepository.findById(vehicleAccount.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleAccount are not directly saved in db
        em.detach(updatedVehicleAccount);
        VehicleAccountDTO vehicleAccountDTO = vehicleAccountMapper.toDto(updatedVehicleAccount);

        restVehicleAccountMockMvc.perform(put("/api/vehicle-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleAccountDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleAccount in the database
        List<VehicleAccount> vehicleAccountList = vehicleAccountRepository.findAll();
        assertThat(vehicleAccountList).hasSize(databaseSizeBeforeUpdate);
        VehicleAccount testVehicleAccount = vehicleAccountList.get(vehicleAccountList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleAccount() throws Exception {
        int databaseSizeBeforeUpdate = vehicleAccountRepository.findAll().size();

        // Create the VehicleAccount
        VehicleAccountDTO vehicleAccountDTO = vehicleAccountMapper.toDto(vehicleAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleAccountMockMvc.perform(put("/api/vehicle-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleAccount in the database
        List<VehicleAccount> vehicleAccountList = vehicleAccountRepository.findAll();
        assertThat(vehicleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleAccount() throws Exception {
        // Initialize the database
        vehicleAccountRepository.saveAndFlush(vehicleAccount);

        int databaseSizeBeforeDelete = vehicleAccountRepository.findAll().size();

        // Delete the vehicleAccount
        restVehicleAccountMockMvc.perform(delete("/api/vehicle-accounts/{id}", vehicleAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleAccount> vehicleAccountList = vehicleAccountRepository.findAll();
        assertThat(vehicleAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleAccount.class);
        VehicleAccount vehicleAccount1 = new VehicleAccount();
        vehicleAccount1.setId(1L);
        VehicleAccount vehicleAccount2 = new VehicleAccount();
        vehicleAccount2.setId(vehicleAccount1.getId());
        assertThat(vehicleAccount1).isEqualTo(vehicleAccount2);
        vehicleAccount2.setId(2L);
        assertThat(vehicleAccount1).isNotEqualTo(vehicleAccount2);
        vehicleAccount1.setId(null);
        assertThat(vehicleAccount1).isNotEqualTo(vehicleAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleAccountDTO.class);
        VehicleAccountDTO vehicleAccountDTO1 = new VehicleAccountDTO();
        vehicleAccountDTO1.setId(1L);
        VehicleAccountDTO vehicleAccountDTO2 = new VehicleAccountDTO();
        assertThat(vehicleAccountDTO1).isNotEqualTo(vehicleAccountDTO2);
        vehicleAccountDTO2.setId(vehicleAccountDTO1.getId());
        assertThat(vehicleAccountDTO1).isEqualTo(vehicleAccountDTO2);
        vehicleAccountDTO2.setId(2L);
        assertThat(vehicleAccountDTO1).isNotEqualTo(vehicleAccountDTO2);
        vehicleAccountDTO1.setId(null);
        assertThat(vehicleAccountDTO1).isNotEqualTo(vehicleAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleAccountMapper.fromId(null)).isNull();
    }
}
