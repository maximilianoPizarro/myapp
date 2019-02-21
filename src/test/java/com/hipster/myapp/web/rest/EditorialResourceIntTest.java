package com.hipster.myapp.web.rest;

import com.hipster.myapp.MyappApp;

import com.hipster.myapp.domain.Editorial;
import com.hipster.myapp.repository.EditorialRepository;
import com.hipster.myapp.repository.search.EditorialSearchRepository;
import com.hipster.myapp.service.EditorialService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the EditorialResource REST controller.
 *
 * @see EditorialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class EditorialResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EditorialRepository editorialRepository;

    @Autowired
    private EditorialService editorialService;

    /**
     * This repository is mocked in the com.hipster.myapp.repository.search test package.
     *
     * @see com.hipster.myapp.repository.search.EditorialSearchRepositoryMockConfiguration
     */
    @Autowired
    private EditorialSearchRepository mockEditorialSearchRepository;

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

    private MockMvc restEditorialMockMvc;

    private Editorial editorial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EditorialResource editorialResource = new EditorialResource(editorialService);
        this.restEditorialMockMvc = MockMvcBuilders.standaloneSetup(editorialResource)
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
    public static Editorial createEntity(EntityManager em) {
        Editorial editorial = new Editorial()
            .nombre(DEFAULT_NOMBRE)
            .fecha(DEFAULT_FECHA);
        return editorial;
    }

    @Before
    public void initTest() {
        editorial = createEntity(em);
    }

    @Test
    @Transactional
    public void createEditorial() throws Exception {
        int databaseSizeBeforeCreate = editorialRepository.findAll().size();

        // Create the Editorial
        restEditorialMockMvc.perform(post("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorial)))
            .andExpect(status().isCreated());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeCreate + 1);
        Editorial testEditorial = editorialList.get(editorialList.size() - 1);
        assertThat(testEditorial.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEditorial.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the Editorial in Elasticsearch
        verify(mockEditorialSearchRepository, times(1)).save(testEditorial);
    }

    @Test
    @Transactional
    public void createEditorialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = editorialRepository.findAll().size();

        // Create the Editorial with an existing ID
        editorial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditorialMockMvc.perform(post("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorial)))
            .andExpect(status().isBadRequest());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeCreate);

        // Validate the Editorial in Elasticsearch
        verify(mockEditorialSearchRepository, times(0)).save(editorial);
    }

    @Test
    @Transactional
    public void getAllEditorials() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);

        // Get all the editorialList
        restEditorialMockMvc.perform(get("/api/editorials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(editorial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getEditorial() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);

        // Get the editorial
        restEditorialMockMvc.perform(get("/api/editorials/{id}", editorial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(editorial.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEditorial() throws Exception {
        // Get the editorial
        restEditorialMockMvc.perform(get("/api/editorials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEditorial() throws Exception {
        // Initialize the database
        editorialService.save(editorial);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEditorialSearchRepository);

        int databaseSizeBeforeUpdate = editorialRepository.findAll().size();

        // Update the editorial
        Editorial updatedEditorial = editorialRepository.findById(editorial.getId()).get();
        // Disconnect from session so that the updates on updatedEditorial are not directly saved in db
        em.detach(updatedEditorial);
        updatedEditorial
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA);

        restEditorialMockMvc.perform(put("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEditorial)))
            .andExpect(status().isOk());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeUpdate);
        Editorial testEditorial = editorialList.get(editorialList.size() - 1);
        assertThat(testEditorial.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEditorial.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the Editorial in Elasticsearch
        verify(mockEditorialSearchRepository, times(1)).save(testEditorial);
    }

    @Test
    @Transactional
    public void updateNonExistingEditorial() throws Exception {
        int databaseSizeBeforeUpdate = editorialRepository.findAll().size();

        // Create the Editorial

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEditorialMockMvc.perform(put("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorial)))
            .andExpect(status().isBadRequest());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Editorial in Elasticsearch
        verify(mockEditorialSearchRepository, times(0)).save(editorial);
    }

    @Test
    @Transactional
    public void deleteEditorial() throws Exception {
        // Initialize the database
        editorialService.save(editorial);

        int databaseSizeBeforeDelete = editorialRepository.findAll().size();

        // Delete the editorial
        restEditorialMockMvc.perform(delete("/api/editorials/{id}", editorial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Editorial in Elasticsearch
        verify(mockEditorialSearchRepository, times(1)).deleteById(editorial.getId());
    }

    @Test
    @Transactional
    public void searchEditorial() throws Exception {
        // Initialize the database
        editorialService.save(editorial);
        when(mockEditorialSearchRepository.search(queryStringQuery("id:" + editorial.getId())))
            .thenReturn(Collections.singletonList(editorial));
        // Search the editorial
        restEditorialMockMvc.perform(get("/api/_search/editorials?query=id:" + editorial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(editorial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Editorial.class);
        Editorial editorial1 = new Editorial();
        editorial1.setId(1L);
        Editorial editorial2 = new Editorial();
        editorial2.setId(editorial1.getId());
        assertThat(editorial1).isEqualTo(editorial2);
        editorial2.setId(2L);
        assertThat(editorial1).isNotEqualTo(editorial2);
        editorial1.setId(null);
        assertThat(editorial1).isNotEqualTo(editorial2);
    }
}
