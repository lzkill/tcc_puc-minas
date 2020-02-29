package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.AuditoriaRepository;
import com.lzkill.sgq.service.AuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.AuditoriaCriteria;
import com.lzkill.sgq.service.AuditoriaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lzkill.sgq.domain.enumeration.ModalidadeAuditoria;
/**
 * Integration tests for the {@link AuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class AuditoriaResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ModalidadeAuditoria DEFAULT_MODALIDADE = ModalidadeAuditoria.INTERNA;
    private static final ModalidadeAuditoria UPDATED_MODALIDADE = ModalidadeAuditoria.EXTERNA;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AUDITOR = "AAAAAAAAAA";
    private static final String UPDATED_AUDITOR = "BBBBBBBBBB";

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Mock
    private AuditoriaRepository auditoriaRepositoryMock;

    @Mock
    private AuditoriaService auditoriaServiceMock;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private AuditoriaQueryService auditoriaQueryService;

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

    private MockMvc restAuditoriaMockMvc;

    private Auditoria auditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditoriaResource auditoriaResource = new AuditoriaResource(auditoriaService, auditoriaQueryService);
        this.restAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(auditoriaResource)
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
    public static Auditoria createEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .modalidade(DEFAULT_MODALIDADE)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .auditor(DEFAULT_AUDITOR);
        return auditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditoria createUpdatedEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .modalidade(UPDATED_MODALIDADE)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .auditor(UPDATED_AUDITOR);
        return auditoria;
    }

    @BeforeEach
    public void initTest() {
        auditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditoria() throws Exception {
        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();

        // Create the Auditoria
        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isCreated());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testAuditoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAuditoria.getModalidade()).isEqualTo(DEFAULT_MODALIDADE);
        assertThat(testAuditoria.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testAuditoria.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testAuditoria.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testAuditoria.getAuditor()).isEqualTo(DEFAULT_AUDITOR);
    }

    @Test
    @Transactional
    public void createAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();

        // Create the Auditoria with an existing ID
        auditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setIdUsuarioRegistro(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setTitulo(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setModalidade(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setDataRegistro(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuditorIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setAuditor(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuditorias() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].auditor").value(hasItem(DEFAULT_AUDITOR)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAuditoriasWithEagerRelationshipsIsEnabled() throws Exception {
        AuditoriaResource auditoriaResource = new AuditoriaResource(auditoriaServiceMock, auditoriaQueryService);
        when(auditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(auditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAuditoriaMockMvc.perform(get("/api/auditorias?eagerload=true"))
        .andExpect(status().isOk());

        verify(auditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAuditoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        AuditoriaResource auditoriaResource = new AuditoriaResource(auditoriaServiceMock, auditoriaQueryService);
            when(auditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(auditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAuditoriaMockMvc.perform(get("/api/auditorias?eagerload=true"))
        .andExpect(status().isOk());

            verify(auditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAuditoria() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get the auditoria
        restAuditoriaMockMvc.perform(get("/api/auditorias/{id}", auditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditoria.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.modalidade").value(DEFAULT_MODALIDADE.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.auditor").value(DEFAULT_AUDITOR));
    }


    @Test
    @Transactional
    public void getAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        Long id = auditoria.getId();

        defaultAuditoriaShouldBeFound("id.equals=" + id);
        defaultAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro is not null
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the auditoriaList where idUsuarioRegistro is null
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the auditoriaList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultAuditoriaShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo equals to DEFAULT_TITULO
        defaultAuditoriaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo equals to UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo not equals to DEFAULT_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo not equals to UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the auditoriaList where titulo equals to UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo is not null
        defaultAuditoriaShouldBeFound("titulo.specified=true");

        // Get all the auditoriaList where titulo is null
        defaultAuditoriaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuditoriasByTituloContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo contains DEFAULT_TITULO
        defaultAuditoriaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo contains UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo does not contain DEFAULT_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo does not contain UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByModalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where modalidade equals to DEFAULT_MODALIDADE
        defaultAuditoriaShouldBeFound("modalidade.equals=" + DEFAULT_MODALIDADE);

        // Get all the auditoriaList where modalidade equals to UPDATED_MODALIDADE
        defaultAuditoriaShouldNotBeFound("modalidade.equals=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByModalidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where modalidade not equals to DEFAULT_MODALIDADE
        defaultAuditoriaShouldNotBeFound("modalidade.notEquals=" + DEFAULT_MODALIDADE);

        // Get all the auditoriaList where modalidade not equals to UPDATED_MODALIDADE
        defaultAuditoriaShouldBeFound("modalidade.notEquals=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByModalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where modalidade in DEFAULT_MODALIDADE or UPDATED_MODALIDADE
        defaultAuditoriaShouldBeFound("modalidade.in=" + DEFAULT_MODALIDADE + "," + UPDATED_MODALIDADE);

        // Get all the auditoriaList where modalidade equals to UPDATED_MODALIDADE
        defaultAuditoriaShouldNotBeFound("modalidade.in=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByModalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where modalidade is not null
        defaultAuditoriaShouldBeFound("modalidade.specified=true");

        // Get all the auditoriaList where modalidade is null
        defaultAuditoriaShouldNotBeFound("modalidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultAuditoriaShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the auditoriaList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultAuditoriaShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultAuditoriaShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the auditoriaList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultAuditoriaShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultAuditoriaShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the auditoriaList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultAuditoriaShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataRegistro is not null
        defaultAuditoriaShouldBeFound("dataRegistro.specified=true");

        // Get all the auditoriaList where dataRegistro is null
        defaultAuditoriaShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataInicio equals to DEFAULT_DATA_INICIO
        defaultAuditoriaShouldBeFound("dataInicio.equals=" + DEFAULT_DATA_INICIO);

        // Get all the auditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultAuditoriaShouldNotBeFound("dataInicio.equals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataInicio not equals to DEFAULT_DATA_INICIO
        defaultAuditoriaShouldNotBeFound("dataInicio.notEquals=" + DEFAULT_DATA_INICIO);

        // Get all the auditoriaList where dataInicio not equals to UPDATED_DATA_INICIO
        defaultAuditoriaShouldBeFound("dataInicio.notEquals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataInicioIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataInicio in DEFAULT_DATA_INICIO or UPDATED_DATA_INICIO
        defaultAuditoriaShouldBeFound("dataInicio.in=" + DEFAULT_DATA_INICIO + "," + UPDATED_DATA_INICIO);

        // Get all the auditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultAuditoriaShouldNotBeFound("dataInicio.in=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataInicio is not null
        defaultAuditoriaShouldBeFound("dataInicio.specified=true");

        // Get all the auditoriaList where dataInicio is null
        defaultAuditoriaShouldNotBeFound("dataInicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataFimIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataFim equals to DEFAULT_DATA_FIM
        defaultAuditoriaShouldBeFound("dataFim.equals=" + DEFAULT_DATA_FIM);

        // Get all the auditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultAuditoriaShouldNotBeFound("dataFim.equals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataFim not equals to DEFAULT_DATA_FIM
        defaultAuditoriaShouldNotBeFound("dataFim.notEquals=" + DEFAULT_DATA_FIM);

        // Get all the auditoriaList where dataFim not equals to UPDATED_DATA_FIM
        defaultAuditoriaShouldBeFound("dataFim.notEquals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataFimIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataFim in DEFAULT_DATA_FIM or UPDATED_DATA_FIM
        defaultAuditoriaShouldBeFound("dataFim.in=" + DEFAULT_DATA_FIM + "," + UPDATED_DATA_FIM);

        // Get all the auditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultAuditoriaShouldNotBeFound("dataFim.in=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByDataFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where dataFim is not null
        defaultAuditoriaShouldBeFound("dataFim.specified=true");

        // Get all the auditoriaList where dataFim is null
        defaultAuditoriaShouldNotBeFound("dataFim.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByAuditorIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor equals to DEFAULT_AUDITOR
        defaultAuditoriaShouldBeFound("auditor.equals=" + DEFAULT_AUDITOR);

        // Get all the auditoriaList where auditor equals to UPDATED_AUDITOR
        defaultAuditoriaShouldNotBeFound("auditor.equals=" + UPDATED_AUDITOR);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByAuditorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor not equals to DEFAULT_AUDITOR
        defaultAuditoriaShouldNotBeFound("auditor.notEquals=" + DEFAULT_AUDITOR);

        // Get all the auditoriaList where auditor not equals to UPDATED_AUDITOR
        defaultAuditoriaShouldBeFound("auditor.notEquals=" + UPDATED_AUDITOR);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByAuditorIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor in DEFAULT_AUDITOR or UPDATED_AUDITOR
        defaultAuditoriaShouldBeFound("auditor.in=" + DEFAULT_AUDITOR + "," + UPDATED_AUDITOR);

        // Get all the auditoriaList where auditor equals to UPDATED_AUDITOR
        defaultAuditoriaShouldNotBeFound("auditor.in=" + UPDATED_AUDITOR);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByAuditorIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor is not null
        defaultAuditoriaShouldBeFound("auditor.specified=true");

        // Get all the auditoriaList where auditor is null
        defaultAuditoriaShouldNotBeFound("auditor.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuditoriasByAuditorContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor contains DEFAULT_AUDITOR
        defaultAuditoriaShouldBeFound("auditor.contains=" + DEFAULT_AUDITOR);

        // Get all the auditoriaList where auditor contains UPDATED_AUDITOR
        defaultAuditoriaShouldNotBeFound("auditor.contains=" + UPDATED_AUDITOR);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByAuditorNotContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where auditor does not contain DEFAULT_AUDITOR
        defaultAuditoriaShouldNotBeFound("auditor.doesNotContain=" + DEFAULT_AUDITOR);

        // Get all the auditoriaList where auditor does not contain UPDATED_AUDITOR
        defaultAuditoriaShouldBeFound("auditor.doesNotContain=" + UPDATED_AUDITOR);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        auditoria.addNaoConformidade(naoConformidade);
        auditoriaRepository.saveAndFlush(auditoria);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the auditoriaList where naoConformidade equals to naoConformidadeId
        defaultAuditoriaShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the auditoriaList where naoConformidade equals to naoConformidadeId + 1
        defaultAuditoriaShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllAuditoriasByConsultoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);
        Consultoria consultoria = ConsultoriaResourceIT.createEntity(em);
        em.persist(consultoria);
        em.flush();
        auditoria.setConsultoria(consultoria);
        auditoriaRepository.saveAndFlush(auditoria);
        Long consultoriaId = consultoria.getId();

        // Get all the auditoriaList where consultoria equals to consultoriaId
        defaultAuditoriaShouldBeFound("consultoriaId.equals=" + consultoriaId);

        // Get all the auditoriaList where consultoria equals to consultoriaId + 1
        defaultAuditoriaShouldNotBeFound("consultoriaId.equals=" + (consultoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllAuditoriasByItemAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);
        ItemAuditoria itemAuditoria = ItemAuditoriaResourceIT.createEntity(em);
        em.persist(itemAuditoria);
        em.flush();
        auditoria.addItemAuditoria(itemAuditoria);
        auditoriaRepository.saveAndFlush(auditoria);
        Long itemAuditoriaId = itemAuditoria.getId();

        // Get all the auditoriaList where itemAuditoria equals to itemAuditoriaId
        defaultAuditoriaShouldBeFound("itemAuditoriaId.equals=" + itemAuditoriaId);

        // Get all the auditoriaList where itemAuditoria equals to itemAuditoriaId + 1
        defaultAuditoriaShouldNotBeFound("itemAuditoriaId.equals=" + (itemAuditoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllAuditoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        auditoria.addAnexo(anexo);
        auditoriaRepository.saveAndFlush(auditoria);
        Long anexoId = anexo.getId();

        // Get all the auditoriaList where anexo equals to anexoId
        defaultAuditoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the auditoriaList where anexo equals to anexoId + 1
        defaultAuditoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditoriaShouldBeFound(String filter) throws Exception {
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].auditor").value(hasItem(DEFAULT_AUDITOR)));

        // Check, that the count call also returns 1
        restAuditoriaMockMvc.perform(get("/api/auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditoriaShouldNotBeFound(String filter) throws Exception {
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditoriaMockMvc.perform(get("/api/auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAuditoria() throws Exception {
        // Get the auditoria
        restAuditoriaMockMvc.perform(get("/api/auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditoria() throws Exception {
        // Initialize the database
        auditoriaService.save(auditoria);

        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Update the auditoria
        Auditoria updatedAuditoria = auditoriaRepository.findById(auditoria.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoria are not directly saved in db
        em.detach(updatedAuditoria);
        updatedAuditoria
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .modalidade(UPDATED_MODALIDADE)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .auditor(UPDATED_AUDITOR);

        restAuditoriaMockMvc.perform(put("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditoria)))
            .andExpect(status().isOk());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testAuditoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAuditoria.getModalidade()).isEqualTo(UPDATED_MODALIDADE);
        assertThat(testAuditoria.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testAuditoria.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testAuditoria.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testAuditoria.getAuditor()).isEqualTo(UPDATED_AUDITOR);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Create the Auditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc.perform(put("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditoria() throws Exception {
        // Initialize the database
        auditoriaService.save(auditoria);

        int databaseSizeBeforeDelete = auditoriaRepository.findAll().size();

        // Delete the auditoria
        restAuditoriaMockMvc.perform(delete("/api/auditorias/{id}", auditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
