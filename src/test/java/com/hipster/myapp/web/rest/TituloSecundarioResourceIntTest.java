package com.hipster.myapp.web.rest;

import com.hipster.myapp.MyappApp;

import com.hipster.myapp.domain.TituloSecundario;
import com.hipster.myapp.repository.TituloSecundarioRepository;
import com.hipster.myapp.repository.search.TituloSecundarioSearchRepository;
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

import com.hipster.myapp.domain.enumeration.TipoEjemplar;
import com.hipster.myapp.domain.enumeration.TipoDni;
/**
 * Test class for the TituloSecundarioResource REST controller.
 *
 * @see TituloSecundarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class TituloSecundarioResourceIntTest {

    private static final Long DEFAULT_NRO_TITULO = 1L;
    private static final Long UPDATED_NRO_TITULO = 2L;

    private static final TipoEjemplar DEFAULT_TIPO_EJEMPLAR = TipoEjemplar.ORIGINAL;
    private static final TipoEjemplar UPDATED_TIPO_EJEMPLAR = TipoEjemplar.DUPLICADO;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final TipoDni DEFAULT_DNI = TipoDni.DNI;
    private static final TipoDni UPDATED_DNI = TipoDni.PAS;

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITULO_OTORGADO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_OTORGADO = "BBBBBBBBBB";

    private static final Double DEFAULT_PROMEDIO = 1D;
    private static final Double UPDATED_PROMEDIO = 2D;

    private static final String DEFAULT_MES_ANNIO_EGRESO = "AAAAAAAAAA";
    private static final String UPDATED_MES_ANNIO_EGRESO = "BBBBBBBBBB";

    private static final Long DEFAULT_VALIDEZ_NACIONAL = 1L;
    private static final Long UPDATED_VALIDEZ_NACIONAL = 2L;

    private static final String DEFAULT_DICTAMEN = "AAAAAAAAAA";
    private static final String UPDATED_DICTAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_REVISADO = "AAAAAAAAAA";
    private static final String UPDATED_REVISADO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EGRESO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TituloSecundarioRepository tituloSecundarioRepository;

    /**
     * This repository is mocked in the com.hipster.myapp.repository.search test package.
     *
     * @see com.hipster.myapp.repository.search.TituloSecundarioSearchRepositoryMockConfiguration
     */
    @Autowired
    private TituloSecundarioSearchRepository mockTituloSecundarioSearchRepository;

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

    private MockMvc restTituloSecundarioMockMvc;

    private TituloSecundario tituloSecundario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TituloSecundarioResource tituloSecundarioResource = new TituloSecundarioResource(tituloSecundarioRepository, mockTituloSecundarioSearchRepository);
        this.restTituloSecundarioMockMvc = MockMvcBuilders.standaloneSetup(tituloSecundarioResource)
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
    public static TituloSecundario createEntity(EntityManager em) {
        TituloSecundario tituloSecundario = new TituloSecundario()
            .nroTitulo(DEFAULT_NRO_TITULO)
            .tipoEjemplar(DEFAULT_TIPO_EJEMPLAR)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .dni(DEFAULT_DNI)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .tituloOtorgado(DEFAULT_TITULO_OTORGADO)
            .promedio(DEFAULT_PROMEDIO)
            .mesAnnioEgreso(DEFAULT_MES_ANNIO_EGRESO)
            .validezNacional(DEFAULT_VALIDEZ_NACIONAL)
            .dictamen(DEFAULT_DICTAMEN)
            .revisado(DEFAULT_REVISADO)
            .ingreso(DEFAULT_INGRESO)
            .egreso(DEFAULT_EGRESO);
        return tituloSecundario;
    }

    @Before
    public void initTest() {
        tituloSecundario = createEntity(em);
    }

    @Test
    @Transactional
    public void createTituloSecundario() throws Exception {
        int databaseSizeBeforeCreate = tituloSecundarioRepository.findAll().size();

        // Create the TituloSecundario
        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isCreated());

        // Validate the TituloSecundario in the database
        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeCreate + 1);
        TituloSecundario testTituloSecundario = tituloSecundarioList.get(tituloSecundarioList.size() - 1);
        assertThat(testTituloSecundario.getNroTitulo()).isEqualTo(DEFAULT_NRO_TITULO);
        assertThat(testTituloSecundario.getTipoEjemplar()).isEqualTo(DEFAULT_TIPO_EJEMPLAR);
        assertThat(testTituloSecundario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTituloSecundario.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testTituloSecundario.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testTituloSecundario.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testTituloSecundario.getTituloOtorgado()).isEqualTo(DEFAULT_TITULO_OTORGADO);
        assertThat(testTituloSecundario.getPromedio()).isEqualTo(DEFAULT_PROMEDIO);
        assertThat(testTituloSecundario.getMesAnnioEgreso()).isEqualTo(DEFAULT_MES_ANNIO_EGRESO);
        assertThat(testTituloSecundario.getValidezNacional()).isEqualTo(DEFAULT_VALIDEZ_NACIONAL);
        assertThat(testTituloSecundario.getDictamen()).isEqualTo(DEFAULT_DICTAMEN);
        assertThat(testTituloSecundario.getRevisado()).isEqualTo(DEFAULT_REVISADO);
        assertThat(testTituloSecundario.getIngreso()).isEqualTo(DEFAULT_INGRESO);
        assertThat(testTituloSecundario.getEgreso()).isEqualTo(DEFAULT_EGRESO);

        // Validate the TituloSecundario in Elasticsearch
        verify(mockTituloSecundarioSearchRepository, times(1)).save(testTituloSecundario);
    }

    @Test
    @Transactional
    public void createTituloSecundarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tituloSecundarioRepository.findAll().size();

        // Create the TituloSecundario with an existing ID
        tituloSecundario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        // Validate the TituloSecundario in the database
        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeCreate);

        // Validate the TituloSecundario in Elasticsearch
        verify(mockTituloSecundarioSearchRepository, times(0)).save(tituloSecundario);
    }

    @Test
    @Transactional
    public void checkNroTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setNroTitulo(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoEjemplarIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setTipoEjemplar(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setNombre(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setApellido(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setDni(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setFechaNacimiento(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloOtorgadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setTituloOtorgado(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPromedioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setPromedio(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMesAnnioEgresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setMesAnnioEgreso(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidezNacionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setValidezNacional(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevisadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setRevisado(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setIngreso(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEgresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloSecundarioRepository.findAll().size();
        // set the field null
        tituloSecundario.setEgreso(null);

        // Create the TituloSecundario, which fails.

        restTituloSecundarioMockMvc.perform(post("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTituloSecundarios() throws Exception {
        // Initialize the database
        tituloSecundarioRepository.saveAndFlush(tituloSecundario);

        // Get all the tituloSecundarioList
        restTituloSecundarioMockMvc.perform(get("/api/titulo-secundarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tituloSecundario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroTitulo").value(hasItem(DEFAULT_NRO_TITULO.intValue())))
            .andExpect(jsonPath("$.[*].tipoEjemplar").value(hasItem(DEFAULT_TIPO_EJEMPLAR.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tituloOtorgado").value(hasItem(DEFAULT_TITULO_OTORGADO.toString())))
            .andExpect(jsonPath("$.[*].promedio").value(hasItem(DEFAULT_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].mesAnnioEgreso").value(hasItem(DEFAULT_MES_ANNIO_EGRESO.toString())))
            .andExpect(jsonPath("$.[*].validezNacional").value(hasItem(DEFAULT_VALIDEZ_NACIONAL.intValue())))
            .andExpect(jsonPath("$.[*].dictamen").value(hasItem(DEFAULT_DICTAMEN.toString())))
            .andExpect(jsonPath("$.[*].revisado").value(hasItem(DEFAULT_REVISADO.toString())))
            .andExpect(jsonPath("$.[*].ingreso").value(hasItem(DEFAULT_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].egreso").value(hasItem(DEFAULT_EGRESO.toString())));
    }
    
    @Test
    @Transactional
    public void getTituloSecundario() throws Exception {
        // Initialize the database
        tituloSecundarioRepository.saveAndFlush(tituloSecundario);

        // Get the tituloSecundario
        restTituloSecundarioMockMvc.perform(get("/api/titulo-secundarios/{id}", tituloSecundario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tituloSecundario.getId().intValue()))
            .andExpect(jsonPath("$.nroTitulo").value(DEFAULT_NRO_TITULO.intValue()))
            .andExpect(jsonPath("$.tipoEjemplar").value(DEFAULT_TIPO_EJEMPLAR.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.tituloOtorgado").value(DEFAULT_TITULO_OTORGADO.toString()))
            .andExpect(jsonPath("$.promedio").value(DEFAULT_PROMEDIO.doubleValue()))
            .andExpect(jsonPath("$.mesAnnioEgreso").value(DEFAULT_MES_ANNIO_EGRESO.toString()))
            .andExpect(jsonPath("$.validezNacional").value(DEFAULT_VALIDEZ_NACIONAL.intValue()))
            .andExpect(jsonPath("$.dictamen").value(DEFAULT_DICTAMEN.toString()))
            .andExpect(jsonPath("$.revisado").value(DEFAULT_REVISADO.toString()))
            .andExpect(jsonPath("$.ingreso").value(DEFAULT_INGRESO.toString()))
            .andExpect(jsonPath("$.egreso").value(DEFAULT_EGRESO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTituloSecundario() throws Exception {
        // Get the tituloSecundario
        restTituloSecundarioMockMvc.perform(get("/api/titulo-secundarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTituloSecundario() throws Exception {
        // Initialize the database
        tituloSecundarioRepository.saveAndFlush(tituloSecundario);

        int databaseSizeBeforeUpdate = tituloSecundarioRepository.findAll().size();

        // Update the tituloSecundario
        TituloSecundario updatedTituloSecundario = tituloSecundarioRepository.findById(tituloSecundario.getId()).get();
        // Disconnect from session so that the updates on updatedTituloSecundario are not directly saved in db
        em.detach(updatedTituloSecundario);
        updatedTituloSecundario
            .nroTitulo(UPDATED_NRO_TITULO)
            .tipoEjemplar(UPDATED_TIPO_EJEMPLAR)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .tituloOtorgado(UPDATED_TITULO_OTORGADO)
            .promedio(UPDATED_PROMEDIO)
            .mesAnnioEgreso(UPDATED_MES_ANNIO_EGRESO)
            .validezNacional(UPDATED_VALIDEZ_NACIONAL)
            .dictamen(UPDATED_DICTAMEN)
            .revisado(UPDATED_REVISADO)
            .ingreso(UPDATED_INGRESO)
            .egreso(UPDATED_EGRESO);

        restTituloSecundarioMockMvc.perform(put("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTituloSecundario)))
            .andExpect(status().isOk());

        // Validate the TituloSecundario in the database
        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeUpdate);
        TituloSecundario testTituloSecundario = tituloSecundarioList.get(tituloSecundarioList.size() - 1);
        assertThat(testTituloSecundario.getNroTitulo()).isEqualTo(UPDATED_NRO_TITULO);
        assertThat(testTituloSecundario.getTipoEjemplar()).isEqualTo(UPDATED_TIPO_EJEMPLAR);
        assertThat(testTituloSecundario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTituloSecundario.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testTituloSecundario.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testTituloSecundario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testTituloSecundario.getTituloOtorgado()).isEqualTo(UPDATED_TITULO_OTORGADO);
        assertThat(testTituloSecundario.getPromedio()).isEqualTo(UPDATED_PROMEDIO);
        assertThat(testTituloSecundario.getMesAnnioEgreso()).isEqualTo(UPDATED_MES_ANNIO_EGRESO);
        assertThat(testTituloSecundario.getValidezNacional()).isEqualTo(UPDATED_VALIDEZ_NACIONAL);
        assertThat(testTituloSecundario.getDictamen()).isEqualTo(UPDATED_DICTAMEN);
        assertThat(testTituloSecundario.getRevisado()).isEqualTo(UPDATED_REVISADO);
        assertThat(testTituloSecundario.getIngreso()).isEqualTo(UPDATED_INGRESO);
        assertThat(testTituloSecundario.getEgreso()).isEqualTo(UPDATED_EGRESO);

        // Validate the TituloSecundario in Elasticsearch
        verify(mockTituloSecundarioSearchRepository, times(1)).save(testTituloSecundario);
    }

    @Test
    @Transactional
    public void updateNonExistingTituloSecundario() throws Exception {
        int databaseSizeBeforeUpdate = tituloSecundarioRepository.findAll().size();

        // Create the TituloSecundario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTituloSecundarioMockMvc.perform(put("/api/titulo-secundarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tituloSecundario)))
            .andExpect(status().isBadRequest());

        // Validate the TituloSecundario in the database
        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TituloSecundario in Elasticsearch
        verify(mockTituloSecundarioSearchRepository, times(0)).save(tituloSecundario);
    }

    @Test
    @Transactional
    public void deleteTituloSecundario() throws Exception {
        // Initialize the database
        tituloSecundarioRepository.saveAndFlush(tituloSecundario);

        int databaseSizeBeforeDelete = tituloSecundarioRepository.findAll().size();

        // Delete the tituloSecundario
        restTituloSecundarioMockMvc.perform(delete("/api/titulo-secundarios/{id}", tituloSecundario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TituloSecundario> tituloSecundarioList = tituloSecundarioRepository.findAll();
        assertThat(tituloSecundarioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TituloSecundario in Elasticsearch
        verify(mockTituloSecundarioSearchRepository, times(1)).deleteById(tituloSecundario.getId());
    }

    @Test
    @Transactional
    public void searchTituloSecundario() throws Exception {
        // Initialize the database
        tituloSecundarioRepository.saveAndFlush(tituloSecundario);
        when(mockTituloSecundarioSearchRepository.search(queryStringQuery("id:" + tituloSecundario.getId())))
            .thenReturn(Collections.singletonList(tituloSecundario));
        // Search the tituloSecundario
        restTituloSecundarioMockMvc.perform(get("/api/_search/titulo-secundarios?query=id:" + tituloSecundario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tituloSecundario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroTitulo").value(hasItem(DEFAULT_NRO_TITULO.intValue())))
            .andExpect(jsonPath("$.[*].tipoEjemplar").value(hasItem(DEFAULT_TIPO_EJEMPLAR.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tituloOtorgado").value(hasItem(DEFAULT_TITULO_OTORGADO)))
            .andExpect(jsonPath("$.[*].promedio").value(hasItem(DEFAULT_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].mesAnnioEgreso").value(hasItem(DEFAULT_MES_ANNIO_EGRESO)))
            .andExpect(jsonPath("$.[*].validezNacional").value(hasItem(DEFAULT_VALIDEZ_NACIONAL.intValue())))
            .andExpect(jsonPath("$.[*].dictamen").value(hasItem(DEFAULT_DICTAMEN)))
            .andExpect(jsonPath("$.[*].revisado").value(hasItem(DEFAULT_REVISADO)))
            .andExpect(jsonPath("$.[*].ingreso").value(hasItem(DEFAULT_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].egreso").value(hasItem(DEFAULT_EGRESO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TituloSecundario.class);
        TituloSecundario tituloSecundario1 = new TituloSecundario();
        tituloSecundario1.setId(1L);
        TituloSecundario tituloSecundario2 = new TituloSecundario();
        tituloSecundario2.setId(tituloSecundario1.getId());
        assertThat(tituloSecundario1).isEqualTo(tituloSecundario2);
        tituloSecundario2.setId(2L);
        assertThat(tituloSecundario1).isNotEqualTo(tituloSecundario2);
        tituloSecundario1.setId(null);
        assertThat(tituloSecundario1).isNotEqualTo(tituloSecundario2);
    }
}
