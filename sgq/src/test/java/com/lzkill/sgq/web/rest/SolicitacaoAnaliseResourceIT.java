package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;
import com.lzkill.sgq.service.SolicitacaoAnaliseService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.SolicitacaoAnaliseCriteria;
import com.lzkill.sgq.service.SolicitacaoAnaliseQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
/**
 * Integration tests for the {@link SolicitacaoAnaliseResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class SolicitacaoAnaliseResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_SOLICITACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_SOLICITACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusSolicitacaoAnalise DEFAULT_STATUS = StatusSolicitacaoAnalise.REGISTRADO;
    private static final StatusSolicitacaoAnalise UPDATED_STATUS = StatusSolicitacaoAnalise.PENDENTE;

    @Autowired
    private SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;

    @Autowired
    private SolicitacaoAnaliseService solicitacaoAnaliseService;

    @Autowired
    private SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService;

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

    private MockMvc restSolicitacaoAnaliseMockMvc;

    private SolicitacaoAnalise solicitacaoAnalise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitacaoAnaliseResource solicitacaoAnaliseResource = new SolicitacaoAnaliseResource(solicitacaoAnaliseService, solicitacaoAnaliseQueryService);
        this.restSolicitacaoAnaliseMockMvc = MockMvcBuilders.standaloneSetup(solicitacaoAnaliseResource)
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
    public static SolicitacaoAnalise createEntity(EntityManager em) {
        SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
            .status(DEFAULT_STATUS);
        // Add required entity
        NaoConformidade naoConformidade;
        if (TestUtil.findAll(em, NaoConformidade.class).isEmpty()) {
            naoConformidade = NaoConformidadeResourceIT.createEntity(em);
            em.persist(naoConformidade);
            em.flush();
        } else {
            naoConformidade = TestUtil.findAll(em, NaoConformidade.class).get(0);
        }
        solicitacaoAnalise.setNaoConformidade(naoConformidade);
        // Add required entity
        Consultoria consultoria;
        if (TestUtil.findAll(em, Consultoria.class).isEmpty()) {
            consultoria = ConsultoriaResourceIT.createEntity(em);
            em.persist(consultoria);
            em.flush();
        } else {
            consultoria = TestUtil.findAll(em, Consultoria.class).get(0);
        }
        solicitacaoAnalise.setConsultoria(consultoria);
        return solicitacaoAnalise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoAnalise createUpdatedEntity(EntityManager em) {
        SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .status(UPDATED_STATUS);
        // Add required entity
        NaoConformidade naoConformidade;
        if (TestUtil.findAll(em, NaoConformidade.class).isEmpty()) {
            naoConformidade = NaoConformidadeResourceIT.createUpdatedEntity(em);
            em.persist(naoConformidade);
            em.flush();
        } else {
            naoConformidade = TestUtil.findAll(em, NaoConformidade.class).get(0);
        }
        solicitacaoAnalise.setNaoConformidade(naoConformidade);
        // Add required entity
        Consultoria consultoria;
        if (TestUtil.findAll(em, Consultoria.class).isEmpty()) {
            consultoria = ConsultoriaResourceIT.createUpdatedEntity(em);
            em.persist(consultoria);
            em.flush();
        } else {
            consultoria = TestUtil.findAll(em, Consultoria.class).get(0);
        }
        solicitacaoAnalise.setConsultoria(consultoria);
        return solicitacaoAnalise;
    }

    @BeforeEach
    public void initTest() {
        solicitacaoAnalise = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitacaoAnalise() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoAnaliseRepository.findAll().size();

        // Create the SolicitacaoAnalise
        restSolicitacaoAnaliseMockMvc.perform(post("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isCreated());

        // Validate the SolicitacaoAnalise in the database
        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitacaoAnalise testSolicitacaoAnalise = solicitacaoAnaliseList.get(solicitacaoAnaliseList.size() - 1);
        assertThat(testSolicitacaoAnalise.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testSolicitacaoAnalise.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testSolicitacaoAnalise.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testSolicitacaoAnalise.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSolicitacaoAnaliseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoAnaliseRepository.findAll().size();

        // Create the SolicitacaoAnalise with an existing ID
        solicitacaoAnalise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoAnaliseMockMvc.perform(post("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoAnalise in the database
        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoAnaliseRepository.findAll().size();
        // set the field null
        solicitacaoAnalise.setIdUsuarioRegistro(null);

        // Create the SolicitacaoAnalise, which fails.

        restSolicitacaoAnaliseMockMvc.perform(post("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoAnaliseRepository.findAll().size();
        // set the field null
        solicitacaoAnalise.setDataRegistro(null);

        // Create the SolicitacaoAnalise, which fails.

        restSolicitacaoAnaliseMockMvc.perform(post("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoAnaliseRepository.findAll().size();
        // set the field null
        solicitacaoAnalise.setStatus(null);

        // Create the SolicitacaoAnalise, which fails.

        restSolicitacaoAnaliseMockMvc.perform(post("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalises() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoAnalise.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(DEFAULT_DATA_SOLICITACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getSolicitacaoAnalise() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get the solicitacaoAnalise
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises/{id}", solicitacaoAnalise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitacaoAnalise.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataSolicitacao").value(DEFAULT_DATA_SOLICITACAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getSolicitacaoAnalisesByIdFiltering() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        Long id = solicitacaoAnalise.getId();

        defaultSolicitacaoAnaliseShouldBeFound("id.equals=" + id);
        defaultSolicitacaoAnaliseShouldNotBeFound("id.notEquals=" + id);

        defaultSolicitacaoAnaliseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSolicitacaoAnaliseShouldNotBeFound("id.greaterThan=" + id);

        defaultSolicitacaoAnaliseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSolicitacaoAnaliseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is not null
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is null
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the solicitacaoAnaliseList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the solicitacaoAnaliseList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the solicitacaoAnaliseList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the solicitacaoAnaliseList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataRegistro is not null
        defaultSolicitacaoAnaliseShouldBeFound("dataRegistro.specified=true");

        // Get all the solicitacaoAnaliseList where dataRegistro is null
        defaultSolicitacaoAnaliseShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataSolicitacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataSolicitacao equals to DEFAULT_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldBeFound("dataSolicitacao.equals=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the solicitacaoAnaliseList where dataSolicitacao equals to UPDATED_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataSolicitacao.equals=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataSolicitacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataSolicitacao not equals to DEFAULT_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataSolicitacao.notEquals=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the solicitacaoAnaliseList where dataSolicitacao not equals to UPDATED_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldBeFound("dataSolicitacao.notEquals=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataSolicitacaoIsInShouldWork() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataSolicitacao in DEFAULT_DATA_SOLICITACAO or UPDATED_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldBeFound("dataSolicitacao.in=" + DEFAULT_DATA_SOLICITACAO + "," + UPDATED_DATA_SOLICITACAO);

        // Get all the solicitacaoAnaliseList where dataSolicitacao equals to UPDATED_DATA_SOLICITACAO
        defaultSolicitacaoAnaliseShouldNotBeFound("dataSolicitacao.in=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByDataSolicitacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where dataSolicitacao is not null
        defaultSolicitacaoAnaliseShouldBeFound("dataSolicitacao.specified=true");

        // Get all the solicitacaoAnaliseList where dataSolicitacao is null
        defaultSolicitacaoAnaliseShouldNotBeFound("dataSolicitacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where status equals to DEFAULT_STATUS
        defaultSolicitacaoAnaliseShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the solicitacaoAnaliseList where status equals to UPDATED_STATUS
        defaultSolicitacaoAnaliseShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where status not equals to DEFAULT_STATUS
        defaultSolicitacaoAnaliseShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the solicitacaoAnaliseList where status not equals to UPDATED_STATUS
        defaultSolicitacaoAnaliseShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSolicitacaoAnaliseShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the solicitacaoAnaliseList where status equals to UPDATED_STATUS
        defaultSolicitacaoAnaliseShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);

        // Get all the solicitacaoAnaliseList where status is not null
        defaultSolicitacaoAnaliseShouldBeFound("status.specified=true");

        // Get all the solicitacaoAnaliseList where status is null
        defaultSolicitacaoAnaliseShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByNaoConformidadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        NaoConformidade naoConformidade = solicitacaoAnalise.getNaoConformidade();
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the solicitacaoAnaliseList where naoConformidade equals to naoConformidadeId
        defaultSolicitacaoAnaliseShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the solicitacaoAnaliseList where naoConformidade equals to naoConformidadeId + 1
        defaultSolicitacaoAnaliseShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByAnaliseConsultoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);
        AnaliseConsultoria analiseConsultoria = AnaliseConsultoriaResourceIT.createEntity(em);
        em.persist(analiseConsultoria);
        em.flush();
        solicitacaoAnalise.setAnaliseConsultoria(analiseConsultoria);
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);
        Long analiseConsultoriaId = analiseConsultoria.getId();

        // Get all the solicitacaoAnaliseList where analiseConsultoria equals to analiseConsultoriaId
        defaultSolicitacaoAnaliseShouldBeFound("analiseConsultoriaId.equals=" + analiseConsultoriaId);

        // Get all the solicitacaoAnaliseList where analiseConsultoria equals to analiseConsultoriaId + 1
        defaultSolicitacaoAnaliseShouldNotBeFound("analiseConsultoriaId.equals=" + (analiseConsultoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllSolicitacaoAnalisesByConsultoriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Consultoria consultoria = solicitacaoAnalise.getConsultoria();
        solicitacaoAnaliseRepository.saveAndFlush(solicitacaoAnalise);
        Long consultoriaId = consultoria.getId();

        // Get all the solicitacaoAnaliseList where consultoria equals to consultoriaId
        defaultSolicitacaoAnaliseShouldBeFound("consultoriaId.equals=" + consultoriaId);

        // Get all the solicitacaoAnaliseList where consultoria equals to consultoriaId + 1
        defaultSolicitacaoAnaliseShouldNotBeFound("consultoriaId.equals=" + (consultoriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSolicitacaoAnaliseShouldBeFound(String filter) throws Exception {
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoAnalise.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(DEFAULT_DATA_SOLICITACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSolicitacaoAnaliseShouldNotBeFound(String filter) throws Exception {
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSolicitacaoAnalise() throws Exception {
        // Get the solicitacaoAnalise
        restSolicitacaoAnaliseMockMvc.perform(get("/api/solicitacao-analises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitacaoAnalise() throws Exception {
        // Initialize the database
        solicitacaoAnaliseService.save(solicitacaoAnalise);

        int databaseSizeBeforeUpdate = solicitacaoAnaliseRepository.findAll().size();

        // Update the solicitacaoAnalise
        SolicitacaoAnalise updatedSolicitacaoAnalise = solicitacaoAnaliseRepository.findById(solicitacaoAnalise.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacaoAnalise are not directly saved in db
        em.detach(updatedSolicitacaoAnalise);
        updatedSolicitacaoAnalise
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .status(UPDATED_STATUS);

        restSolicitacaoAnaliseMockMvc.perform(put("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolicitacaoAnalise)))
            .andExpect(status().isOk());

        // Validate the SolicitacaoAnalise in the database
        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoAnalise testSolicitacaoAnalise = solicitacaoAnaliseList.get(solicitacaoAnaliseList.size() - 1);
        assertThat(testSolicitacaoAnalise.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testSolicitacaoAnalise.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testSolicitacaoAnalise.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testSolicitacaoAnalise.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitacaoAnalise() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoAnaliseRepository.findAll().size();

        // Create the SolicitacaoAnalise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoAnaliseMockMvc.perform(put("/api/solicitacao-analises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoAnalise)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoAnalise in the database
        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolicitacaoAnalise() throws Exception {
        // Initialize the database
        solicitacaoAnaliseService.save(solicitacaoAnalise);

        int databaseSizeBeforeDelete = solicitacaoAnaliseRepository.findAll().size();

        // Delete the solicitacaoAnalise
        restSolicitacaoAnaliseMockMvc.perform(delete("/api/solicitacao-analises/{id}", solicitacaoAnalise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitacaoAnalise> solicitacaoAnaliseList = solicitacaoAnaliseRepository.findAll();
        assertThat(solicitacaoAnaliseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
