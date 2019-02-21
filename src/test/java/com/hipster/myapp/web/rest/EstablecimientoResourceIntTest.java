package com.hipster.myapp.web.rest;

import com.hipster.myapp.MyappApp;

import com.hipster.myapp.domain.Establecimiento;
import com.hipster.myapp.repository.EstablecimientoRepository;
import com.hipster.myapp.repository.search.EstablecimientoSearchRepository;
import com.hipster.myapp.web.rest.errors.ExceptionTranslator;

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
import java.util.Collections;
import java.util.List;


import static com.hipster.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstablecimientoResource REST controller.
 *
 * @see EstablecimientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class EstablecimientoResourceIntTest {

    private static final Long DEFAULT_NRO_CUE = 1L;
    private static final Long UPDATED_NRO_CUE = 2L;

    private static final String DEFAULT_GESTION = "AAAAAAAAAA";
    private static final String UPDATED_GESTION = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEL = "AAAAAAAAAA";
    private static final String UPDATED_NIVEL = "BBBBBBBBBB";

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    /**
     * This repository is mocked in the com.hipster.myapp.repository.search test package.
     *
     * @see com.hipster.myapp.repository.search.EstablecimientoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstablecimientoSearchRepository mockEstablecimientoSearchRepository;

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

    private MockMvc restEstablecimientoMockMvc;

    private Establecimiento establecimiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstablecimientoResource establecimientoResource = new EstablecimientoResource(establecimientoRepository, mockEstablecimientoSearchRepository);
        this.restEstablecimientoMockMvc = MockMvcBuilders.standaloneSetup(establecimientoResource)
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
    public static Establecimiento createEntity(EntityManager em) {
        Establecimiento establecimiento = new Establecimiento()
            .nroCue(DEFAULT_NRO_CUE)
            .gestion(DEFAULT_GESTION)
            .modalidad(DEFAULT_MODALIDAD)
            .nivel(DEFAULT_NIVEL);
        return establecimiento;
    }

    @Before
    public void initTest() {
        establecimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstablecimiento() throws Exception {
        int databaseSizeBeforeCreate = establecimientoRepository.findAll().size();

        // Create the Establecimiento
        restEstablecimientoMockMvc.perform(post("/api/establecimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establecimiento)))
            .andExpect(status().isCreated());

        // Validate the Establecimiento in the database
        List<Establecimiento> establecimientoList = establecimientoRepository.findAll();
        assertThat(establecimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Establecimiento testEstablecimiento = establecimientoList.get(establecimientoList.size() - 1);
        assertThat(testEstablecimiento.getNroCue()).isEqualTo(DEFAULT_NRO_CUE);
        assertThat(testEstablecimiento.getGestion()).isEqualTo(DEFAULT_GESTION);
        assertThat(testEstablecimiento.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testEstablecimiento.getNivel()).isEqualTo(DEFAULT_NIVEL);

        // Validate the Establecimiento in Elasticsearch
        verify(mockEstablecimientoSearchRepository, times(1)).save(testEstablecimiento);
    }

    @Test
    @Transactional
    public void createEstablecimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = establecimientoRepository.findAll().size();

        // Create the Establecimiento with an existing ID
        establecimiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablecimientoMockMvc.perform(post("/api/establecimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establecimiento)))
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        List<Establecimiento> establecimientoList = establecimientoRepository.findAll();
        assertThat(establecimientoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Establecimiento in Elasticsearch
        verify(mockEstablecimientoSearchRepository, times(0)).save(establecimiento);
    }

    @Test
    @Transactional
    public void getAllEstablecimientos() throws Exception {
        // Initialize the database
        establecimientoRepository.saveAndFlush(establecimiento);

        // Get all the establecimientoList
        restEstablecimientoMockMvc.perform(get("/api/establecimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establecimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroCue").value(hasItem(DEFAULT_NRO_CUE.intValue())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION.toString())))
            .andExpect(jsonPath("$.[*].modalidad").value(hasItem(DEFAULT_MODALIDAD.toString())))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getEstablecimiento() throws Exception {
        // Initialize the database
        establecimientoRepository.saveAndFlush(establecimiento);

        // Get the establecimiento
        restEstablecimientoMockMvc.perform(get("/api/establecimientos/{id}", establecimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(establecimiento.getId().intValue()))
            .andExpect(jsonPath("$.nroCue").value(DEFAULT_NRO_CUE.intValue()))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION.toString()))
            .andExpect(jsonPath("$.modalidad").value(DEFAULT_MODALIDAD.toString()))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstablecimiento() throws Exception {
        // Get the establecimiento
        restEstablecimientoMockMvc.perform(get("/api/establecimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstablecimiento() throws Exception {
        // Initialize the database
        establecimientoRepository.saveAndFlush(establecimiento);

        int databaseSizeBeforeUpdate = establecimientoRepository.findAll().size();

        // Update the establecimiento
        Establecimiento updatedEstablecimiento = establecimientoRepository.findById(establecimiento.getId()).get();
        // Disconnect from session so that the updates on updatedEstablecimiento are not directly saved in db
        em.detach(updatedEstablecimiento);
        updatedEstablecimiento
            .nroCue(UPDATED_NRO_CUE)
            .gestion(UPDATED_GESTION)
            .modalidad(UPDATED_MODALIDAD)
            .nivel(UPDATED_NIVEL);

        restEstablecimientoMockMvc.perform(put("/api/establecimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstablecimiento)))
            .andExpect(status().isOk());

        // Validate the Establecimiento in the database
        List<Establecimiento> establecimientoList = establecimientoRepository.findAll();
        assertThat(establecimientoList).hasSize(databaseSizeBeforeUpdate);
        Establecimiento testEstablecimiento = establecimientoList.get(establecimientoList.size() - 1);
        assertThat(testEstablecimiento.getNroCue()).isEqualTo(UPDATED_NRO_CUE);
        assertThat(testEstablecimiento.getGestion()).isEqualTo(UPDATED_GESTION);
        assertThat(testEstablecimiento.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testEstablecimiento.getNivel()).isEqualTo(UPDATED_NIVEL);

        // Validate the Establecimiento in Elasticsearch
        verify(mockEstablecimientoSearchRepository, times(1)).save(testEstablecimiento);
    }

    @Test
    @Transactional
    public void updateNonExistingEstablecimiento() throws Exception {
        int databaseSizeBeforeUpdate = establecimientoRepository.findAll().size();

        // Create the Establecimiento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc.perform(put("/api/establecimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establecimiento)))
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        List<Establecimiento> establecimientoList = establecimientoRepository.findAll();
        assertThat(establecimientoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Establecimiento in Elasticsearch
        verify(mockEstablecimientoSearchRepository, times(0)).save(establecimiento);
    }

    @Test
    @Transactional
    public void deleteEstablecimiento() throws Exception {
        // Initialize the database
        establecimientoRepository.saveAndFlush(establecimiento);

        int databaseSizeBeforeDelete = establecimientoRepository.findAll().size();

        // Delete the establecimiento
        restEstablecimientoMockMvc.perform(delete("/api/establecimientos/{id}", establecimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Establecimiento> establecimientoList = establecimientoRepository.findAll();
        assertThat(establecimientoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Establecimiento in Elasticsearch
        verify(mockEstablecimientoSearchRepository, times(1)).deleteById(establecimiento.getId());
    }

    @Test
    @Transactional
    public void searchEstablecimiento() throws Exception {
        // Initialize the database
        establecimientoRepository.saveAndFlush(establecimiento);
        when(mockEstablecimientoSearchRepository.search(queryStringQuery("id:" + establecimiento.getId())))
            .thenReturn(Collections.singletonList(establecimiento));
        // Search the establecimiento
        restEstablecimientoMockMvc.perform(get("/api/_search/establecimientos?query=id:" + establecimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establecimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroCue").value(hasItem(DEFAULT_NRO_CUE.intValue())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)))
            .andExpect(jsonPath("$.[*].modalidad").value(hasItem(DEFAULT_MODALIDAD)))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Establecimiento.class);
        Establecimiento establecimiento1 = new Establecimiento();
        establecimiento1.setId(1L);
        Establecimiento establecimiento2 = new Establecimiento();
        establecimiento2.setId(establecimiento1.getId());
        assertThat(establecimiento1).isEqualTo(establecimiento2);
        establecimiento2.setId(2L);
        assertThat(establecimiento1).isNotEqualTo(establecimiento2);
        establecimiento1.setId(null);
        assertThat(establecimiento1).isNotEqualTo(establecimiento2);
    }
}
