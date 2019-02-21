package com.hipster.myapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hipster.myapp.MyappApp;

import com.hipster.myapp.domain.Documento;
import com.hipster.myapp.repository.DocumentoRepository;
import com.hipster.myapp.repository.TituloSecundarioRepository;
import com.hipster.myapp.repository.search.DocumentoSearchRepository;
import com.hipster.myapp.repository.search.TituloSecundarioSearchRepository;
import com.hipster.myapp.service.DocumentoService;
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
import com.hipster.myapp.service.BlockchainService;

/**
 * Test class for the DocumentoResource REST controller.
 *
 * @see DocumentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class DocumentoResourceIntTest {

    private static final Integer DEFAULT_BAID = 1;
    private static final Integer UPDATED_BAID = 2;

    private static final String DEFAULT_CUIL = "AAAAAAAAAA";
    private static final String UPDATED_CUIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPODOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPODOCUMENTO = "BBBBBBBBBB";

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private TituloSecundarioRepository tituloSecundarioRepository;
    @Autowired
    private TituloSecundarioSearchRepository tituloSecundarioSearchRepository;
    @Autowired
    private TituloSecundarioResource tituloSecundarioResource;
    @Autowired
    private BlockchainService blockchainService;
    /**
     * This repository is mocked in the com.hipster.myapp.repository.search test package.
     *
     * @see com.hipster.myapp.repository.search.DocumentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentoSearchRepository mockDocumentoSearchRepository;

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

    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoResource documentoResource = new DocumentoResource(documentoService,tituloSecundarioRepository,tituloSecundarioSearchRepository,tituloSecundarioResource,blockchainService);
        this.restDocumentoMockMvc = MockMvcBuilders.standaloneSetup(documentoResource)
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
    public static Documento createEntity(EntityManager em) throws JsonProcessingException {
        Documento documento = new Documento()
            .baid(DEFAULT_BAID)
            .cuil(DEFAULT_CUIL)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .tipodocumento(DEFAULT_TIPODOCUMENTO);
        return documento;
    }

    @Before
    public void initTest() throws JsonProcessingException {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getBaid()).isEqualTo(DEFAULT_BAID);
        assertThat(testDocumento.getCuil()).isEqualTo(DEFAULT_CUIL);
        assertThat(testDocumento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDocumento.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testDocumento.getTipodocumento()).isEqualTo(DEFAULT_TIPODOCUMENTO);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).save(testDocumento);
    }

    @Test
    @Transactional
    public void createDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento with an existing ID
        documento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(0)).save(documento);
    }

    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].baid").value(hasItem(DEFAULT_BAID)))
            .andExpect(jsonPath("$.[*].cuil").value(hasItem(DEFAULT_CUIL.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].tipodocumento").value(hasItem(DEFAULT_TIPODOCUMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.baid").value(DEFAULT_BAID))
            .andExpect(jsonPath("$.cuil").value(DEFAULT_CUIL.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.tipodocumento").value(DEFAULT_TIPODOCUMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoService.save(documento);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDocumentoSearchRepository);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .baid(UPDATED_BAID)
            .cuil(UPDATED_CUIL)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .tipodocumento(UPDATED_TIPODOCUMENTO);

        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumento)))
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getBaid()).isEqualTo(UPDATED_BAID);
        assertThat(testDocumento.getCuil()).isEqualTo(UPDATED_CUIL);
        assertThat(testDocumento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDocumento.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testDocumento.getTipodocumento()).isEqualTo(UPDATED_TIPODOCUMENTO);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).save(testDocumento);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Create the Documento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(0)).save(documento);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoService.save(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc.perform(delete("/api/documentos/{id}", documento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).deleteById(documento.getId());
    }

    @Test
    @Transactional
    public void searchDocumento() throws Exception {
        // Initialize the database
        documentoService.save(documento);
        when(mockDocumentoSearchRepository.search(queryStringQuery("id:" + documento.getId())))
            .thenReturn(Collections.singletonList(documento));
        // Search the documento
        restDocumentoMockMvc.perform(get("/api/_search/documentos?query=id:" + documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].baid").value(hasItem(DEFAULT_BAID)))
            .andExpect(jsonPath("$.[*].cuil").value(hasItem(DEFAULT_CUIL)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].tipodocumento").value(hasItem(DEFAULT_TIPODOCUMENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documento.class);
        Documento documento1 = new Documento();
        documento1.setId(1L);
        Documento documento2 = new Documento();
        documento2.setId(documento1.getId());
        assertThat(documento1).isEqualTo(documento2);
        documento2.setId(2L);
        assertThat(documento1).isNotEqualTo(documento2);
        documento1.setId(null);
        assertThat(documento1).isNotEqualTo(documento2);
    }
}
