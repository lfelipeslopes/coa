package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.BillingLocation;
import com.compsis.repository.BillingLocationRepository;
import com.compsis.service.BillingLocationService;
import com.compsis.service.dto.BillingLocationDTO;
import com.compsis.service.mapper.BillingLocationMapper;
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
 * Test class for the BillingLocationResource REST controller.
 *
 * @see BillingLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class BillingLocationResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private BillingLocationRepository billingLocationRepository;

    @Autowired
    private BillingLocationMapper billingLocationMapper;

    @Autowired
    private BillingLocationService billingLocationService;

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

    private MockMvc restBillingLocationMockMvc;

    private BillingLocation billingLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingLocationResource billingLocationResource = new BillingLocationResource(billingLocationService);
        this.restBillingLocationMockMvc = MockMvcBuilders.standaloneSetup(billingLocationResource)
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
    public static BillingLocation createEntity(EntityManager em) {
        BillingLocation billingLocation = new BillingLocation()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        return billingLocation;
    }

    @Before
    public void initTest() {
        billingLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingLocation() throws Exception {
        int databaseSizeBeforeCreate = billingLocationRepository.findAll().size();

        // Create the BillingLocation
        BillingLocationDTO billingLocationDTO = billingLocationMapper.toDto(billingLocation);
        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeCreate + 1);
        BillingLocation testBillingLocation = billingLocationList.get(billingLocationList.size() - 1);
        assertThat(testBillingLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBillingLocation.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createBillingLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingLocationRepository.findAll().size();

        // Create the BillingLocation with an existing ID
        billingLocation.setId(1L);
        BillingLocationDTO billingLocationDTO = billingLocationMapper.toDto(billingLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingLocationMockMvc.perform(post("/api/billing-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBillingLocations() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        // Get all the billingLocationList
        restBillingLocationMockMvc.perform(get("/api/billing-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getBillingLocation() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        // Get the billingLocation
        restBillingLocationMockMvc.perform(get("/api/billing-locations/{id}", billingLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingLocation.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingLocation() throws Exception {
        // Get the billingLocation
        restBillingLocationMockMvc.perform(get("/api/billing-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingLocation() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        int databaseSizeBeforeUpdate = billingLocationRepository.findAll().size();

        // Update the billingLocation
        BillingLocation updatedBillingLocation = billingLocationRepository.findById(billingLocation.getId()).get();
        // Disconnect from session so that the updates on updatedBillingLocation are not directly saved in db
        em.detach(updatedBillingLocation);
        updatedBillingLocation
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        BillingLocationDTO billingLocationDTO = billingLocationMapper.toDto(updatedBillingLocation);

        restBillingLocationMockMvc.perform(put("/api/billing-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingLocationDTO)))
            .andExpect(status().isOk());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeUpdate);
        BillingLocation testBillingLocation = billingLocationList.get(billingLocationList.size() - 1);
        assertThat(testBillingLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBillingLocation.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingLocation() throws Exception {
        int databaseSizeBeforeUpdate = billingLocationRepository.findAll().size();

        // Create the BillingLocation
        BillingLocationDTO billingLocationDTO = billingLocationMapper.toDto(billingLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingLocationMockMvc.perform(put("/api/billing-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingLocation in the database
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingLocation() throws Exception {
        // Initialize the database
        billingLocationRepository.saveAndFlush(billingLocation);

        int databaseSizeBeforeDelete = billingLocationRepository.findAll().size();

        // Delete the billingLocation
        restBillingLocationMockMvc.perform(delete("/api/billing-locations/{id}", billingLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillingLocation> billingLocationList = billingLocationRepository.findAll();
        assertThat(billingLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingLocation.class);
        BillingLocation billingLocation1 = new BillingLocation();
        billingLocation1.setId(1L);
        BillingLocation billingLocation2 = new BillingLocation();
        billingLocation2.setId(billingLocation1.getId());
        assertThat(billingLocation1).isEqualTo(billingLocation2);
        billingLocation2.setId(2L);
        assertThat(billingLocation1).isNotEqualTo(billingLocation2);
        billingLocation1.setId(null);
        assertThat(billingLocation1).isNotEqualTo(billingLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingLocationDTO.class);
        BillingLocationDTO billingLocationDTO1 = new BillingLocationDTO();
        billingLocationDTO1.setId(1L);
        BillingLocationDTO billingLocationDTO2 = new BillingLocationDTO();
        assertThat(billingLocationDTO1).isNotEqualTo(billingLocationDTO2);
        billingLocationDTO2.setId(billingLocationDTO1.getId());
        assertThat(billingLocationDTO1).isEqualTo(billingLocationDTO2);
        billingLocationDTO2.setId(2L);
        assertThat(billingLocationDTO1).isNotEqualTo(billingLocationDTO2);
        billingLocationDTO1.setId(null);
        assertThat(billingLocationDTO1).isNotEqualTo(billingLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billingLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billingLocationMapper.fromId(null)).isNull();
    }
}
