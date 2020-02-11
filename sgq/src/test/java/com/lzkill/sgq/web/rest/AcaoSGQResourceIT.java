package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.repository.AcaoSGQRepository;
import com.lzkill.sgq.service.AcaoSGQService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.AcaoSGQCriteria;
import com.lzkill.sgq.service.AcaoSGQQueryService;

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
import org.springframework.util.Base64Utils;
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

import com.lzkill.sgq.domain.enumeration.TipoAcaoSGQ;
import com.lzkill.sgq.domain.enumeration.StatusSGQ;
/**
 * Integration tests for the {@link AcaoSGQResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class AcaoSGQResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final Integer DEFAULT_ID_USUARIO_RESPONSAVEL = 1;
    private static final Integer UPDATED_ID_USUARIO_RESPONSAVEL = 2;
    private static final Integer SMALLER_ID_USUARIO_RESPONSAVEL = 1 - 1;

    private static final TipoAcaoSGQ DEFAULT_TIPO = TipoAcaoSGQ.ACAO_CORRETIVA;
    private static final TipoAcaoSGQ UPDATED_TIPO = TipoAcaoSGQ.ACAO_PREVENTIVA;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOVO_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOVO_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final StatusSGQ DEFAULT_STATUS_SGQ = StatusSGQ.REGISTRADO;
    private static final StatusSGQ UPDATED_STATUS_SGQ = StatusSGQ.PENDENTE;

    @Autowired
    private AcaoSGQRepository acaoSGQRepository;

    @Autowired
    private AcaoSGQService acaoSGQService;

    @Autowired
    private AcaoSGQQueryService acaoSGQQueryService;

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

    private MockMvc restAcaoSGQMockMvc;

    private AcaoSGQ acaoSGQ;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcaoSGQResource acaoSGQResource = new AcaoSGQResource(acaoSGQService, acaoSGQQueryService);
        this.restAcaoSGQMockMvc = MockMvcBuilders.standaloneSetup(acaoSGQResource)
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
    public static AcaoSGQ createEntity(EntityManager em) {
        AcaoSGQ acaoSGQ = new AcaoSGQ()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(DEFAULT_ID_USUARIO_RESPONSAVEL)
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .prazoConclusao(DEFAULT_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(DEFAULT_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataConclusao(DEFAULT_DATA_CONCLUSAO)
            .resultado(DEFAULT_RESULTADO)
            .statusSGQ(DEFAULT_STATUS_SGQ);
        return acaoSGQ;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcaoSGQ createUpdatedEntity(EntityManager em) {
        AcaoSGQ acaoSGQ = new AcaoSGQ()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .resultado(UPDATED_RESULTADO)
            .statusSGQ(UPDATED_STATUS_SGQ);
        return acaoSGQ;
    }

    @BeforeEach
    public void initTest() {
        acaoSGQ = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcaoSGQ() throws Exception {
        int databaseSizeBeforeCreate = acaoSGQRepository.findAll().size();

        // Create the AcaoSGQ
        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isCreated());

        // Validate the AcaoSGQ in the database
        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeCreate + 1);
        AcaoSGQ testAcaoSGQ = acaoSGQList.get(acaoSGQList.size() - 1);
        assertThat(testAcaoSGQ.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testAcaoSGQ.getIdUsuarioResponsavel()).isEqualTo(DEFAULT_ID_USUARIO_RESPONSAVEL);
        assertThat(testAcaoSGQ.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAcaoSGQ.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAcaoSGQ.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAcaoSGQ.getPrazoConclusao()).isEqualTo(DEFAULT_PRAZO_CONCLUSAO);
        assertThat(testAcaoSGQ.getNovoPrazoConclusao()).isEqualTo(DEFAULT_NOVO_PRAZO_CONCLUSAO);
        assertThat(testAcaoSGQ.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testAcaoSGQ.getDataConclusao()).isEqualTo(DEFAULT_DATA_CONCLUSAO);
        assertThat(testAcaoSGQ.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testAcaoSGQ.getStatusSGQ()).isEqualTo(DEFAULT_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void createAcaoSGQWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acaoSGQRepository.findAll().size();

        // Create the AcaoSGQ with an existing ID
        acaoSGQ.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        // Validate the AcaoSGQ in the database
        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setIdUsuarioRegistro(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdUsuarioResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setIdUsuarioResponsavel(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setTipo(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setTitulo(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrazoConclusaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setPrazoConclusao(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setDataRegistro(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusSGQIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoSGQRepository.findAll().size();
        // set the field null
        acaoSGQ.setStatusSGQ(null);

        // Create the AcaoSGQ, which fails.

        restAcaoSGQMockMvc.perform(post("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQS() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acaoSGQ.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataConclusao").value(hasItem(DEFAULT_DATA_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));
    }
    
    @Test
    @Transactional
    public void getAcaoSGQ() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get the acaoSGQ
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs/{id}", acaoSGQ.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acaoSGQ.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.idUsuarioResponsavel").value(DEFAULT_ID_USUARIO_RESPONSAVEL))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.prazoConclusao").value(DEFAULT_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.novoPrazoConclusao").value(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataConclusao").value(DEFAULT_DATA_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.statusSGQ").value(DEFAULT_STATUS_SGQ.toString()));
    }


    @Test
    @Transactional
    public void getAcaoSGQSByIdFiltering() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        Long id = acaoSGQ.getId();

        defaultAcaoSGQShouldBeFound("id.equals=" + id);
        defaultAcaoSGQShouldNotBeFound("id.notEquals=" + id);

        defaultAcaoSGQShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAcaoSGQShouldNotBeFound("id.greaterThan=" + id);

        defaultAcaoSGQShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAcaoSGQShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro is not null
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the acaoSGQList where idUsuarioRegistro is null
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the acaoSGQList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultAcaoSGQShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.equals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.equals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel not equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.notEquals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel not equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.notEquals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel in DEFAULT_ID_USUARIO_RESPONSAVEL or UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.in=" + DEFAULT_ID_USUARIO_RESPONSAVEL + "," + UPDATED_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.in=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel is not null
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.specified=true");

        // Get all the acaoSGQList where idUsuarioResponsavel is null
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel is greater than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel is greater than or equal to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel is less than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.lessThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel is less than or equal to SMALLER_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.lessThanOrEqual=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsLessThanSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel is less than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.lessThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel is less than UPDATED_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.lessThan=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByIdUsuarioResponsavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where idUsuarioResponsavel is greater than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldNotBeFound("idUsuarioResponsavel.greaterThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the acaoSGQList where idUsuarioResponsavel is greater than SMALLER_ID_USUARIO_RESPONSAVEL
        defaultAcaoSGQShouldBeFound("idUsuarioResponsavel.greaterThan=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllAcaoSGQSByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where tipo equals to DEFAULT_TIPO
        defaultAcaoSGQShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the acaoSGQList where tipo equals to UPDATED_TIPO
        defaultAcaoSGQShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where tipo not equals to DEFAULT_TIPO
        defaultAcaoSGQShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the acaoSGQList where tipo not equals to UPDATED_TIPO
        defaultAcaoSGQShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultAcaoSGQShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the acaoSGQList where tipo equals to UPDATED_TIPO
        defaultAcaoSGQShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where tipo is not null
        defaultAcaoSGQShouldBeFound("tipo.specified=true");

        // Get all the acaoSGQList where tipo is null
        defaultAcaoSGQShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo equals to DEFAULT_TITULO
        defaultAcaoSGQShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the acaoSGQList where titulo equals to UPDATED_TITULO
        defaultAcaoSGQShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo not equals to DEFAULT_TITULO
        defaultAcaoSGQShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the acaoSGQList where titulo not equals to UPDATED_TITULO
        defaultAcaoSGQShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultAcaoSGQShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the acaoSGQList where titulo equals to UPDATED_TITULO
        defaultAcaoSGQShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo is not null
        defaultAcaoSGQShouldBeFound("titulo.specified=true");

        // Get all the acaoSGQList where titulo is null
        defaultAcaoSGQShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAcaoSGQSByTituloContainsSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo contains DEFAULT_TITULO
        defaultAcaoSGQShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the acaoSGQList where titulo contains UPDATED_TITULO
        defaultAcaoSGQShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where titulo does not contain DEFAULT_TITULO
        defaultAcaoSGQShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the acaoSGQList where titulo does not contain UPDATED_TITULO
        defaultAcaoSGQShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllAcaoSGQSByPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where prazoConclusao equals to DEFAULT_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("prazoConclusao.equals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("prazoConclusao.equals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where prazoConclusao not equals to DEFAULT_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("prazoConclusao.notEquals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where prazoConclusao not equals to UPDATED_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("prazoConclusao.notEquals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where prazoConclusao in DEFAULT_PRAZO_CONCLUSAO or UPDATED_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("prazoConclusao.in=" + DEFAULT_PRAZO_CONCLUSAO + "," + UPDATED_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("prazoConclusao.in=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where prazoConclusao is not null
        defaultAcaoSGQShouldBeFound("prazoConclusao.specified=true");

        // Get all the acaoSGQList where prazoConclusao is null
        defaultAcaoSGQShouldNotBeFound("prazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByNovoPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where novoPrazoConclusao equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("novoPrazoConclusao.equals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("novoPrazoConclusao.equals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByNovoPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where novoPrazoConclusao not equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("novoPrazoConclusao.notEquals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where novoPrazoConclusao not equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("novoPrazoConclusao.notEquals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByNovoPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where novoPrazoConclusao in DEFAULT_NOVO_PRAZO_CONCLUSAO or UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldBeFound("novoPrazoConclusao.in=" + DEFAULT_NOVO_PRAZO_CONCLUSAO + "," + UPDATED_NOVO_PRAZO_CONCLUSAO);

        // Get all the acaoSGQList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("novoPrazoConclusao.in=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByNovoPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where novoPrazoConclusao is not null
        defaultAcaoSGQShouldBeFound("novoPrazoConclusao.specified=true");

        // Get all the acaoSGQList where novoPrazoConclusao is null
        defaultAcaoSGQShouldNotBeFound("novoPrazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultAcaoSGQShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the acaoSGQList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultAcaoSGQShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultAcaoSGQShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the acaoSGQList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultAcaoSGQShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultAcaoSGQShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the acaoSGQList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultAcaoSGQShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataRegistro is not null
        defaultAcaoSGQShouldBeFound("dataRegistro.specified=true");

        // Get all the acaoSGQList where dataRegistro is null
        defaultAcaoSGQShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataConclusao equals to DEFAULT_DATA_CONCLUSAO
        defaultAcaoSGQShouldBeFound("dataConclusao.equals=" + DEFAULT_DATA_CONCLUSAO);

        // Get all the acaoSGQList where dataConclusao equals to UPDATED_DATA_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("dataConclusao.equals=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataConclusao not equals to DEFAULT_DATA_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("dataConclusao.notEquals=" + DEFAULT_DATA_CONCLUSAO);

        // Get all the acaoSGQList where dataConclusao not equals to UPDATED_DATA_CONCLUSAO
        defaultAcaoSGQShouldBeFound("dataConclusao.notEquals=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataConclusao in DEFAULT_DATA_CONCLUSAO or UPDATED_DATA_CONCLUSAO
        defaultAcaoSGQShouldBeFound("dataConclusao.in=" + DEFAULT_DATA_CONCLUSAO + "," + UPDATED_DATA_CONCLUSAO);

        // Get all the acaoSGQList where dataConclusao equals to UPDATED_DATA_CONCLUSAO
        defaultAcaoSGQShouldNotBeFound("dataConclusao.in=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByDataConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where dataConclusao is not null
        defaultAcaoSGQShouldBeFound("dataConclusao.specified=true");

        // Get all the acaoSGQList where dataConclusao is null
        defaultAcaoSGQShouldNotBeFound("dataConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByStatusSGQIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where statusSGQ equals to DEFAULT_STATUS_SGQ
        defaultAcaoSGQShouldBeFound("statusSGQ.equals=" + DEFAULT_STATUS_SGQ);

        // Get all the acaoSGQList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultAcaoSGQShouldNotBeFound("statusSGQ.equals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByStatusSGQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where statusSGQ not equals to DEFAULT_STATUS_SGQ
        defaultAcaoSGQShouldNotBeFound("statusSGQ.notEquals=" + DEFAULT_STATUS_SGQ);

        // Get all the acaoSGQList where statusSGQ not equals to UPDATED_STATUS_SGQ
        defaultAcaoSGQShouldBeFound("statusSGQ.notEquals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByStatusSGQIsInShouldWork() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where statusSGQ in DEFAULT_STATUS_SGQ or UPDATED_STATUS_SGQ
        defaultAcaoSGQShouldBeFound("statusSGQ.in=" + DEFAULT_STATUS_SGQ + "," + UPDATED_STATUS_SGQ);

        // Get all the acaoSGQList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultAcaoSGQShouldNotBeFound("statusSGQ.in=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByStatusSGQIsNullOrNotNull() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);

        // Get all the acaoSGQList where statusSGQ is not null
        defaultAcaoSGQShouldBeFound("statusSGQ.specified=true");

        // Get all the acaoSGQList where statusSGQ is null
        defaultAcaoSGQShouldNotBeFound("statusSGQ.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcaoSGQSByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        acaoSGQ.addAnexo(anexo);
        acaoSGQRepository.saveAndFlush(acaoSGQ);
        Long anexoId = anexo.getId();

        // Get all the acaoSGQList where anexo equals to anexoId
        defaultAcaoSGQShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the acaoSGQList where anexo equals to anexoId + 1
        defaultAcaoSGQShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllAcaoSGQSByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        acaoSGQRepository.saveAndFlush(acaoSGQ);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        acaoSGQ.setNaoConformidade(naoConformidade);
        acaoSGQRepository.saveAndFlush(acaoSGQ);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the acaoSGQList where naoConformidade equals to naoConformidadeId
        defaultAcaoSGQShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the acaoSGQList where naoConformidade equals to naoConformidadeId + 1
        defaultAcaoSGQShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAcaoSGQShouldBeFound(String filter) throws Exception {
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acaoSGQ.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataConclusao").value(hasItem(DEFAULT_DATA_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));

        // Check, that the count call also returns 1
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAcaoSGQShouldNotBeFound(String filter) throws Exception {
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAcaoSGQ() throws Exception {
        // Get the acaoSGQ
        restAcaoSGQMockMvc.perform(get("/api/acao-sgqs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcaoSGQ() throws Exception {
        // Initialize the database
        acaoSGQService.save(acaoSGQ);

        int databaseSizeBeforeUpdate = acaoSGQRepository.findAll().size();

        // Update the acaoSGQ
        AcaoSGQ updatedAcaoSGQ = acaoSGQRepository.findById(acaoSGQ.getId()).get();
        // Disconnect from session so that the updates on updatedAcaoSGQ are not directly saved in db
        em.detach(updatedAcaoSGQ);
        updatedAcaoSGQ
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .resultado(UPDATED_RESULTADO)
            .statusSGQ(UPDATED_STATUS_SGQ);

        restAcaoSGQMockMvc.perform(put("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcaoSGQ)))
            .andExpect(status().isOk());

        // Validate the AcaoSGQ in the database
        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeUpdate);
        AcaoSGQ testAcaoSGQ = acaoSGQList.get(acaoSGQList.size() - 1);
        assertThat(testAcaoSGQ.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testAcaoSGQ.getIdUsuarioResponsavel()).isEqualTo(UPDATED_ID_USUARIO_RESPONSAVEL);
        assertThat(testAcaoSGQ.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAcaoSGQ.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAcaoSGQ.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAcaoSGQ.getPrazoConclusao()).isEqualTo(UPDATED_PRAZO_CONCLUSAO);
        assertThat(testAcaoSGQ.getNovoPrazoConclusao()).isEqualTo(UPDATED_NOVO_PRAZO_CONCLUSAO);
        assertThat(testAcaoSGQ.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testAcaoSGQ.getDataConclusao()).isEqualTo(UPDATED_DATA_CONCLUSAO);
        assertThat(testAcaoSGQ.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testAcaoSGQ.getStatusSGQ()).isEqualTo(UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void updateNonExistingAcaoSGQ() throws Exception {
        int databaseSizeBeforeUpdate = acaoSGQRepository.findAll().size();

        // Create the AcaoSGQ

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcaoSGQMockMvc.perform(put("/api/acao-sgqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acaoSGQ)))
            .andExpect(status().isBadRequest());

        // Validate the AcaoSGQ in the database
        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcaoSGQ() throws Exception {
        // Initialize the database
        acaoSGQService.save(acaoSGQ);

        int databaseSizeBeforeDelete = acaoSGQRepository.findAll().size();

        // Delete the acaoSGQ
        restAcaoSGQMockMvc.perform(delete("/api/acao-sgqs/{id}", acaoSGQ.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcaoSGQ> acaoSGQList = acaoSGQRepository.findAll();
        assertThat(acaoSGQList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
