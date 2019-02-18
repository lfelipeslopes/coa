package com.compsis.web.rest;

import com.compsis.CoaApp;

import com.compsis.domain.DataChange;
import com.compsis.repository.DataChangeRepository;
import com.compsis.service.DataChangeService;
import com.compsis.service.dto.DataChangeDTO;
import com.compsis.service.mapper.DataChangeMapper;
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
 * Test class for the DataChangeResource REST controller.
 *
 * @see DataChangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoaApp.class)
public class DataChangeResourceIntTest {

    private static final String DEFAULT_CHANGE_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_DETAIL = "BBBBBBBBBB";

    @Autowired
    private DataChangeRepository dataChangeRepository;

    @Autowired
    private DataChangeMapper dataChangeMapper;

    @Autowired
    private DataChangeService dataChangeService;

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

    private MockMvc restDataChangeMockMvc;

    private DataChange dataChange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataChangeResource dataChangeResource = new DataChangeResource(dataChangeService);
        this.restDataChangeMockMvc = MockMvcBuilders.standaloneSetup(dataChangeResource)
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
    public static DataChange createEntity(EntityManager em) {
        DataChange dataChange = new DataChange()
            .changeDetail(DEFAULT_CHANGE_DETAIL);
        return dataChange;
    }

    @Before
    public void initTest() {
        dataChange = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataChange() throws Exception {
        int databaseSizeBeforeCreate = dataChangeRepository.findAll().size();

        // Create the DataChange
        DataChangeDTO dataChangeDTO = dataChangeMapper.toDto(dataChange);
        restDataChangeMockMvc.perform(post("/api/data-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataChangeDTO)))
            .andExpect(status().isCreated());

        // Validate the DataChange in the database
        List<DataChange> dataChangeList = dataChangeRepository.findAll();
        assertThat(dataChangeList).hasSize(databaseSizeBeforeCreate + 1);
        DataChange testDataChange = dataChangeList.get(dataChangeList.size() - 1);
        assertThat(testDataChange.getChangeDetail()).isEqualTo(DEFAULT_CHANGE_DETAIL);
    }

    @Test
    @Transactional
    public void createDataChangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataChangeRepository.findAll().size();

        // Create the DataChange with an existing ID
        dataChange.setId(1L);
        DataChangeDTO dataChangeDTO = dataChangeMapper.toDto(dataChange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataChangeMockMvc.perform(post("/api/data-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataChangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataChange in the database
        List<DataChange> dataChangeList = dataChangeRepository.findAll();
        assertThat(dataChangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDataChanges() throws Exception {
        // Initialize the database
        dataChangeRepository.saveAndFlush(dataChange);

        // Get all the dataChangeList
        restDataChangeMockMvc.perform(get("/api/data-changes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeDetail").value(hasItem(DEFAULT_CHANGE_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getDataChange() throws Exception {
        // Initialize the database
        dataChangeRepository.saveAndFlush(dataChange);

        // Get the dataChange
        restDataChangeMockMvc.perform(get("/api/data-changes/{id}", dataChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataChange.getId().intValue()))
            .andExpect(jsonPath("$.changeDetail").value(DEFAULT_CHANGE_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataChange() throws Exception {
        // Get the dataChange
        restDataChangeMockMvc.perform(get("/api/data-changes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataChange() throws Exception {
        // Initialize the database
        dataChangeRepository.saveAndFlush(dataChange);

        int databaseSizeBeforeUpdate = dataChangeRepository.findAll().size();

        // Update the dataChange
        DataChange updatedDataChange = dataChangeRepository.findById(dataChange.getId()).get();
        // Disconnect from session so that the updates on updatedDataChange are not directly saved in db
        em.detach(updatedDataChange);
        updatedDataChange
            .changeDetail(UPDATED_CHANGE_DETAIL);
        DataChangeDTO dataChangeDTO = dataChangeMapper.toDto(updatedDataChange);

        restDataChangeMockMvc.perform(put("/api/data-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataChangeDTO)))
            .andExpect(status().isOk());

        // Validate the DataChange in the database
        List<DataChange> dataChangeList = dataChangeRepository.findAll();
        assertThat(dataChangeList).hasSize(databaseSizeBeforeUpdate);
        DataChange testDataChange = dataChangeList.get(dataChangeList.size() - 1);
        assertThat(testDataChange.getChangeDetail()).isEqualTo(UPDATED_CHANGE_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingDataChange() throws Exception {
        int databaseSizeBeforeUpdate = dataChangeRepository.findAll().size();

        // Create the DataChange
        DataChangeDTO dataChangeDTO = dataChangeMapper.toDto(dataChange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataChangeMockMvc.perform(put("/api/data-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataChangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataChange in the database
        List<DataChange> dataChangeList = dataChangeRepository.findAll();
        assertThat(dataChangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataChange() throws Exception {
        // Initialize the database
        dataChangeRepository.saveAndFlush(dataChange);

        int databaseSizeBeforeDelete = dataChangeRepository.findAll().size();

        // Delete the dataChange
        restDataChangeMockMvc.perform(delete("/api/data-changes/{id}", dataChange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataChange> dataChangeList = dataChangeRepository.findAll();
        assertThat(dataChangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataChange.class);
        DataChange dataChange1 = new DataChange();
        dataChange1.setId(1L);
        DataChange dataChange2 = new DataChange();
        dataChange2.setId(dataChange1.getId());
        assertThat(dataChange1).isEqualTo(dataChange2);
        dataChange2.setId(2L);
        assertThat(dataChange1).isNotEqualTo(dataChange2);
        dataChange1.setId(null);
        assertThat(dataChange1).isNotEqualTo(dataChange2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataChangeDTO.class);
        DataChangeDTO dataChangeDTO1 = new DataChangeDTO();
        dataChangeDTO1.setId(1L);
        DataChangeDTO dataChangeDTO2 = new DataChangeDTO();
        assertThat(dataChangeDTO1).isNotEqualTo(dataChangeDTO2);
        dataChangeDTO2.setId(dataChangeDTO1.getId());
        assertThat(dataChangeDTO1).isEqualTo(dataChangeDTO2);
        dataChangeDTO2.setId(2L);
        assertThat(dataChangeDTO1).isNotEqualTo(dataChangeDTO2);
        dataChangeDTO1.setId(null);
        assertThat(dataChangeDTO1).isNotEqualTo(dataChangeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataChangeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataChangeMapper.fromId(null)).isNull();
    }
}
