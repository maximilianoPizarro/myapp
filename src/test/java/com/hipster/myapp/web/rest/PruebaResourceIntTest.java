package com.hipster.myapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hipster.myapp.MyappApp;

import com.hipster.myapp.domain.Prueba;
import com.hipster.myapp.repository.PruebaRepository;
import com.hipster.myapp.repository.search.PruebaSearchRepository;
import com.hipster.myapp.service.PruebaService;
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
 * Test class for the PruebaResource REST controller.
 *
 * @see PruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class PruebaResourceIntTest {

    private static final String DEFAULT_CAMPO = "AAAAAAAAAA";
    private static final String UPDATED_CAMPO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private PruebaService pruebaService;

    /**
     * This repository is mocked in the com.hipster.myapp.repository.search test package.
     *
     * @see com.hipster.myapp.repository.search.PruebaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PruebaSearchRepository mockPruebaSearchRepository;

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

    private MockMvc restPruebaMockMvc;

    private Prueba prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PruebaResource pruebaResource = new PruebaResource(pruebaService);
        this.restPruebaMockMvc = MockMvcBuilders.standaloneSetup(pruebaResource)
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
     * @throws JsonProcessingException 
     */
    public static Prueba createEntity(EntityManager em) throws JsonProcessingException {
        Prueba prueba = new Prueba()
            .campo(DEFAULT_CAMPO)
            .valor(DEFAULT_VALOR);
        return prueba;
    }

    @Before
    public void initTest() throws JsonProcessingException {
        prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrueba() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getCampo()).isEqualTo(DEFAULT_CAMPO);
        assertThat(testPrueba.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Prueba in Elasticsearch
        verify(mockPruebaSearchRepository, times(1)).save(testPrueba);
    }

    @Test
    @Transactional
    public void createPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba with an existing ID
        prueba.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prueba in Elasticsearch
        verify(mockPruebaSearchRepository, times(0)).save(prueba);
    }

    @Test
    @Transactional
    public void getAllPruebas() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get all the pruebaList
        restPruebaMockMvc.perform(get("/api/pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].campo").value(hasItem(DEFAULT_CAMPO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    
    @Test
    @Transactional
    public void getPrueba() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prueba.getId().intValue()))
            .andExpect(jsonPath("$.campo").value(DEFAULT_CAMPO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrueba() throws Exception {
        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPruebaSearchRepository);

        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Update the prueba
        Prueba updatedPrueba = pruebaRepository.findById(prueba.getId()).get();
        // Disconnect from session so that the updates on updatedPrueba are not directly saved in db
        em.detach(updatedPrueba);
        updatedPrueba
            .campo(UPDATED_CAMPO)
            .valor(UPDATED_VALOR);

        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrueba)))
            .andExpect(status().isOk());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getCampo()).isEqualTo(UPDATED_CAMPO);
        assertThat(testPrueba.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Prueba in Elasticsearch
        verify(mockPruebaSearchRepository, times(1)).save(testPrueba);
    }

    @Test
    @Transactional
    public void updateNonExistingPrueba() throws Exception {
        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Create the Prueba

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prueba in Elasticsearch
        verify(mockPruebaSearchRepository, times(0)).save(prueba);
    }

    @Test
    @Transactional
    public void deletePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);

        int databaseSizeBeforeDelete = pruebaRepository.findAll().size();

        // Delete the prueba
        restPruebaMockMvc.perform(delete("/api/pruebas/{id}", prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prueba in Elasticsearch
        verify(mockPruebaSearchRepository, times(1)).deleteById(prueba.getId());
    }

    @Test
    @Transactional
    public void searchPrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);
        when(mockPruebaSearchRepository.search(queryStringQuery("id:" + prueba.getId())))
            .thenReturn(Collections.singletonList(prueba));
        // Search the prueba
        restPruebaMockMvc.perform(get("/api/_search/pruebas?query=id:" + prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].campo").value(hasItem(DEFAULT_CAMPO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prueba.class);
        Prueba prueba1 = new Prueba();
        prueba1.setId(1L);
        Prueba prueba2 = new Prueba();
        prueba2.setId(prueba1.getId());
        assertThat(prueba1).isEqualTo(prueba2);
        prueba2.setId(2L);
        assertThat(prueba1).isNotEqualTo(prueba2);
        prueba1.setId(null);
        assertThat(prueba1).isNotEqualTo(prueba2);
    }
}
