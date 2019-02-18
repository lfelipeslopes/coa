package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.VehicleClass;
import com.compsis.repository.VehicleClassRepository;
import com.compsis.service.VehicleClassService;
import com.compsis.service.dto.VehicleClassDTO;
import com.compsis.service.mapper.VehicleClassMapper;
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
 * Test class for the VehicleClassResource REST controller.
 *
 * @see VehicleClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class VehicleClassResourceIntTest {

    private static final Integer DEFAULT_AXES = 1;
    private static final Integer UPDATED_AXES = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOUBLE_WHEEL = 1;
    private static final Integer UPDATED_DOUBLE_WHEEL = 2;

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    private VehicleClassMapper vehicleClassMapper;

    @Autowired
    private VehicleClassService vehicleClassService;

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

    private MockMvc restVehicleClassMockMvc;

    private VehicleClass vehicleClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleClassResource vehicleClassResource = new VehicleClassResource(vehicleClassService);
        this.restVehicleClassMockMvc = MockMvcBuilders.standaloneSetup(vehicleClassResource)
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
    public static VehicleClass createEntity(EntityManager em) {
        VehicleClass vehicleClass = new VehicleClass()
            .axes(DEFAULT_AXES)
            .description(DEFAULT_DESCRIPTION)
            .doubleWheel(DEFAULT_DOUBLE_WHEEL);
        return vehicleClass;
    }

    @Before
    public void initTest() {
        vehicleClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleClass() throws Exception {
        int databaseSizeBeforeCreate = vehicleClassRepository.findAll().size();

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);
        restVehicleClassMockMvc.perform(post("/api/vehicle-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getAxes()).isEqualTo(DEFAULT_AXES);
        assertThat(testVehicleClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVehicleClass.getDoubleWheel()).isEqualTo(DEFAULT_DOUBLE_WHEEL);
    }

    @Test
    @Transactional
    public void createVehicleClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleClassRepository.findAll().size();

        // Create the VehicleClass with an existing ID
        vehicleClass.setId(1L);
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleClassMockMvc.perform(post("/api/vehicle-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleClasses() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList
        restVehicleClassMockMvc.perform(get("/api/vehicle-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].axes").value(hasItem(DEFAULT_AXES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].doubleWheel").value(hasItem(DEFAULT_DOUBLE_WHEEL)));
    }
    
    @Test
    @Transactional
    public void getVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get the vehicleClass
        restVehicleClassMockMvc.perform(get("/api/vehicle-classes/{id}", vehicleClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleClass.getId().intValue()))
            .andExpect(jsonPath("$.axes").value(DEFAULT_AXES))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.doubleWheel").value(DEFAULT_DOUBLE_WHEEL));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleClass() throws Exception {
        // Get the vehicleClass
        restVehicleClassMockMvc.perform(get("/api/vehicle-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();

        // Update the vehicleClass
        VehicleClass updatedVehicleClass = vehicleClassRepository.findById(vehicleClass.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleClass are not directly saved in db
        em.detach(updatedVehicleClass);
        updatedVehicleClass
            .axes(UPDATED_AXES)
            .description(UPDATED_DESCRIPTION)
            .doubleWheel(UPDATED_DOUBLE_WHEEL);
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(updatedVehicleClass);

        restVehicleClassMockMvc.perform(put("/api/vehicle-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getAxes()).isEqualTo(UPDATED_AXES);
        assertThat(testVehicleClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVehicleClass.getDoubleWheel()).isEqualTo(UPDATED_DOUBLE_WHEEL);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc.perform(put("/api/vehicle-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeDelete = vehicleClassRepository.findAll().size();

        // Delete the vehicleClass
        restVehicleClassMockMvc.perform(delete("/api/vehicle-classes/{id}", vehicleClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleClass.class);
        VehicleClass vehicleClass1 = new VehicleClass();
        vehicleClass1.setId(1L);
        VehicleClass vehicleClass2 = new VehicleClass();
        vehicleClass2.setId(vehicleClass1.getId());
        assertThat(vehicleClass1).isEqualTo(vehicleClass2);
        vehicleClass2.setId(2L);
        assertThat(vehicleClass1).isNotEqualTo(vehicleClass2);
        vehicleClass1.setId(null);
        assertThat(vehicleClass1).isNotEqualTo(vehicleClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleClassDTO.class);
        VehicleClassDTO vehicleClassDTO1 = new VehicleClassDTO();
        vehicleClassDTO1.setId(1L);
        VehicleClassDTO vehicleClassDTO2 = new VehicleClassDTO();
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
        vehicleClassDTO2.setId(vehicleClassDTO1.getId());
        assertThat(vehicleClassDTO1).isEqualTo(vehicleClassDTO2);
        vehicleClassDTO2.setId(2L);
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
        vehicleClassDTO1.setId(null);
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleClassMapper.fromId(null)).isNull();
    }
}
