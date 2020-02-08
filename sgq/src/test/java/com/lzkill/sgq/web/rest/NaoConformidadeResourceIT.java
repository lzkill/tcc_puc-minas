package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.repository.NaoConformidadeRepository;
import com.lzkill.sgq.service.NaoConformidadeService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.NaoConformidadeCriteria;
import com.lzkill.sgq.service.NaoConformidadeQueryService;

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

import com.lzkill.sgq.domain.enumeration.StatusSGQ;
/**
 * Integration tests for the {@link NaoConformidadeResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class NaoConformidadeResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final Integer DEFAULT_ID_USUARIO_RESPONSAVEL = 1;
    private static final Integer UPDATED_ID_USUARIO_RESPONSAVEL = 2;
    private static final Integer SMALLER_ID_USUARIO_RESPONSAVEL = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_FATO = "AAAAAAAAAA";
    private static final String UPDATED_FATO = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSA = "AAAAAAAAAA";
    private static final String UPDATED_CAUSA = "BBBBBBBBBB";

    private static final Instant DEFAULT_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOVO_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOVO_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ANALISE_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_ANALISE_FINAL = "BBBBBBBBBB";

    private static final StatusSGQ DEFAULT_STATUS_SGQ = StatusSGQ.REGISTRADO;
    private static final StatusSGQ UPDATED_STATUS_SGQ = StatusSGQ.PENDENTE;

    @Autowired
    private NaoConformidadeRepository naoConformidadeRepository;

    @Autowired
    private NaoConformidadeService naoConformidadeService;

    @Autowired
    private NaoConformidadeQueryService naoConformidadeQueryService;

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

    private MockMvc restNaoConformidadeMockMvc;

    private NaoConformidade naoConformidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NaoConformidadeResource naoConformidadeResource = new NaoConformidadeResource(naoConformidadeService, naoConformidadeQueryService);
        this.restNaoConformidadeMockMvc = MockMvcBuilders.standaloneSetup(naoConformidadeResource)
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
    public static NaoConformidade createEntity(EntityManager em) {
        NaoConformidade naoConformidade = new NaoConformidade()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(DEFAULT_ID_USUARIO_RESPONSAVEL)
            .titulo(DEFAULT_TITULO)
            .fato(DEFAULT_FATO)
            .causa(DEFAULT_CAUSA)
            .prazoConclusao(DEFAULT_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(DEFAULT_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataConclusao(DEFAULT_DATA_CONCLUSAO)
            .analiseFinal(DEFAULT_ANALISE_FINAL)
            .statusSGQ(DEFAULT_STATUS_SGQ);
        // Add required entity
        AcaoSGQ acaoSGQ;
        if (TestUtil.findAll(em, AcaoSGQ.class).isEmpty()) {
            acaoSGQ = AcaoSGQResourceIT.createEntity(em);
            em.persist(acaoSGQ);
            em.flush();
        } else {
            acaoSGQ = TestUtil.findAll(em, AcaoSGQ.class).get(0);
        }
        naoConformidade.getAcaos().add(acaoSGQ);
        return naoConformidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaoConformidade createUpdatedEntity(EntityManager em) {
        NaoConformidade naoConformidade = new NaoConformidade()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .titulo(UPDATED_TITULO)
            .fato(UPDATED_FATO)
            .causa(UPDATED_CAUSA)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .analiseFinal(UPDATED_ANALISE_FINAL)
            .statusSGQ(UPDATED_STATUS_SGQ);
        // Add required entity
        AcaoSGQ acaoSGQ;
        if (TestUtil.findAll(em, AcaoSGQ.class).isEmpty()) {
            acaoSGQ = AcaoSGQResourceIT.createUpdatedEntity(em);
            em.persist(acaoSGQ);
            em.flush();
        } else {
            acaoSGQ = TestUtil.findAll(em, AcaoSGQ.class).get(0);
        }
        naoConformidade.getAcaos().add(acaoSGQ);
        return naoConformidade;
    }

    @BeforeEach
    public void initTest() {
        naoConformidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaoConformidade() throws Exception {
        int databaseSizeBeforeCreate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade
        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isCreated());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeCreate + 1);
        NaoConformidade testNaoConformidade = naoConformidadeList.get(naoConformidadeList.size() - 1);
        assertThat(testNaoConformidade.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testNaoConformidade.getIdUsuarioResponsavel()).isEqualTo(DEFAULT_ID_USUARIO_RESPONSAVEL);
        assertThat(testNaoConformidade.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNaoConformidade.getFato()).isEqualTo(DEFAULT_FATO);
        assertThat(testNaoConformidade.getCausa()).isEqualTo(DEFAULT_CAUSA);
        assertThat(testNaoConformidade.getPrazoConclusao()).isEqualTo(DEFAULT_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getNovoPrazoConclusao()).isEqualTo(DEFAULT_NOVO_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testNaoConformidade.getDataConclusao()).isEqualTo(DEFAULT_DATA_CONCLUSAO);
        assertThat(testNaoConformidade.getAnaliseFinal()).isEqualTo(DEFAULT_ANALISE_FINAL);
        assertThat(testNaoConformidade.getStatusSGQ()).isEqualTo(DEFAULT_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void createNaoConformidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade with an existing ID
        naoConformidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setIdUsuarioRegistro(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdUsuarioResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setIdUsuarioResponsavel(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setTitulo(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrazoConclusaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setPrazoConclusao(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setDataRegistro(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusSGQIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setStatusSGQ(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaoConformidades() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naoConformidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].fato").value(hasItem(DEFAULT_FATO.toString())))
            .andExpect(jsonPath("$.[*].causa").value(hasItem(DEFAULT_CAUSA.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataConclusao").value(hasItem(DEFAULT_DATA_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].analiseFinal").value(hasItem(DEFAULT_ANALISE_FINAL.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));
    }
    
    @Test
    @Transactional
    public void getNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get the naoConformidade
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/{id}", naoConformidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(naoConformidade.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.idUsuarioResponsavel").value(DEFAULT_ID_USUARIO_RESPONSAVEL))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.fato").value(DEFAULT_FATO.toString()))
            .andExpect(jsonPath("$.causa").value(DEFAULT_CAUSA.toString()))
            .andExpect(jsonPath("$.prazoConclusao").value(DEFAULT_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.novoPrazoConclusao").value(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataConclusao").value(DEFAULT_DATA_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.analiseFinal").value(DEFAULT_ANALISE_FINAL.toString()))
            .andExpect(jsonPath("$.statusSGQ").value(DEFAULT_STATUS_SGQ.toString()));
    }


    @Test
    @Transactional
    public void getNaoConformidadesByIdFiltering() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        Long id = naoConformidade.getId();

        defaultNaoConformidadeShouldBeFound("id.equals=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.notEquals=" + id);

        defaultNaoConformidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultNaoConformidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro is not null
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the naoConformidadeList where idUsuarioRegistro is null
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the naoConformidadeList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultNaoConformidadeShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.equals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.equals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel not equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.notEquals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel not equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.notEquals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel in DEFAULT_ID_USUARIO_RESPONSAVEL or UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.in=" + DEFAULT_ID_USUARIO_RESPONSAVEL + "," + UPDATED_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.in=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel is not null
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.specified=true");

        // Get all the naoConformidadeList where idUsuarioResponsavel is null
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel is greater than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel is greater than or equal to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel is less than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.lessThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel is less than or equal to SMALLER_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.lessThanOrEqual=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsLessThanSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel is less than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.lessThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel is less than UPDATED_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.lessThan=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByIdUsuarioResponsavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where idUsuarioResponsavel is greater than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldNotBeFound("idUsuarioResponsavel.greaterThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the naoConformidadeList where idUsuarioResponsavel is greater than SMALLER_ID_USUARIO_RESPONSAVEL
        defaultNaoConformidadeShouldBeFound("idUsuarioResponsavel.greaterThan=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo equals to DEFAULT_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo equals to UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo not equals to DEFAULT_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo not equals to UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the naoConformidadeList where titulo equals to UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo is not null
        defaultNaoConformidadeShouldBeFound("titulo.specified=true");

        // Get all the naoConformidadeList where titulo is null
        defaultNaoConformidadeShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNaoConformidadesByTituloContainsSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo contains DEFAULT_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo contains UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo does not contain DEFAULT_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo does not contain UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao equals to DEFAULT_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.equals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.equals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao not equals to DEFAULT_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.notEquals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao not equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.notEquals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao in DEFAULT_PRAZO_CONCLUSAO or UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.in=" + DEFAULT_PRAZO_CONCLUSAO + "," + UPDATED_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.in=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao is not null
        defaultNaoConformidadeShouldBeFound("prazoConclusao.specified=true");

        // Get all the naoConformidadeList where prazoConclusao is null
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.equals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.equals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao not equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.notEquals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao not equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.notEquals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao in DEFAULT_NOVO_PRAZO_CONCLUSAO or UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.in=" + DEFAULT_NOVO_PRAZO_CONCLUSAO + "," + UPDATED_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.in=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao is not null
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.specified=true");

        // Get all the naoConformidadeList where novoPrazoConclusao is null
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro is not null
        defaultNaoConformidadeShouldBeFound("dataRegistro.specified=true");

        // Get all the naoConformidadeList where dataRegistro is null
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataConclusao equals to DEFAULT_DATA_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("dataConclusao.equals=" + DEFAULT_DATA_CONCLUSAO);

        // Get all the naoConformidadeList where dataConclusao equals to UPDATED_DATA_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("dataConclusao.equals=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataConclusao not equals to DEFAULT_DATA_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("dataConclusao.notEquals=" + DEFAULT_DATA_CONCLUSAO);

        // Get all the naoConformidadeList where dataConclusao not equals to UPDATED_DATA_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("dataConclusao.notEquals=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataConclusao in DEFAULT_DATA_CONCLUSAO or UPDATED_DATA_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("dataConclusao.in=" + DEFAULT_DATA_CONCLUSAO + "," + UPDATED_DATA_CONCLUSAO);

        // Get all the naoConformidadeList where dataConclusao equals to UPDATED_DATA_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("dataConclusao.in=" + UPDATED_DATA_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataConclusao is not null
        defaultNaoConformidadeShouldBeFound("dataConclusao.specified=true");

        // Get all the naoConformidadeList where dataConclusao is null
        defaultNaoConformidadeShouldNotBeFound("dataConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByStatusSGQIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where statusSGQ equals to DEFAULT_STATUS_SGQ
        defaultNaoConformidadeShouldBeFound("statusSGQ.equals=" + DEFAULT_STATUS_SGQ);

        // Get all the naoConformidadeList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultNaoConformidadeShouldNotBeFound("statusSGQ.equals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByStatusSGQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where statusSGQ not equals to DEFAULT_STATUS_SGQ
        defaultNaoConformidadeShouldNotBeFound("statusSGQ.notEquals=" + DEFAULT_STATUS_SGQ);

        // Get all the naoConformidadeList where statusSGQ not equals to UPDATED_STATUS_SGQ
        defaultNaoConformidadeShouldBeFound("statusSGQ.notEquals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByStatusSGQIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where statusSGQ in DEFAULT_STATUS_SGQ or UPDATED_STATUS_SGQ
        defaultNaoConformidadeShouldBeFound("statusSGQ.in=" + DEFAULT_STATUS_SGQ + "," + UPDATED_STATUS_SGQ);

        // Get all the naoConformidadeList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultNaoConformidadeShouldNotBeFound("statusSGQ.in=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByStatusSGQIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where statusSGQ is not null
        defaultNaoConformidadeShouldBeFound("statusSGQ.specified=true");

        // Get all the naoConformidadeList where statusSGQ is null
        defaultNaoConformidadeShouldNotBeFound("statusSGQ.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        naoConformidade.setAnexo(anexo);
        naoConformidadeRepository.saveAndFlush(naoConformidade);
        Long anexoId = anexo.getId();

        // Get all the naoConformidadeList where anexo equals to anexoId
        defaultNaoConformidadeShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the naoConformidadeList where anexo equals to anexoId + 1
        defaultNaoConformidadeShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

/*
    @Test
    @Transactional
    public void getAllNaoConformidadesByAcaoIsEqualToSomething() throws Exception {
        // Get already existing entity
        AcaoSGQ acao = naoConformidade.getAcao();
        naoConformidadeRepository.saveAndFlush(naoConformidade);
        Long acaoId = acao.getId();

        // Get all the naoConformidadeList where acao equals to acaoId
        defaultNaoConformidadeShouldBeFound("acaoId.equals=" + acaoId);

        // Get all the naoConformidadeList where acao equals to acaoId + 1
        defaultNaoConformidadeShouldNotBeFound("acaoId.equals=" + (acaoId + 1));
    }
*/

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNaoConformidadeShouldBeFound(String filter) throws Exception {
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naoConformidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].fato").value(hasItem(DEFAULT_FATO.toString())))
            .andExpect(jsonPath("$.[*].causa").value(hasItem(DEFAULT_CAUSA.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataConclusao").value(hasItem(DEFAULT_DATA_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].analiseFinal").value(hasItem(DEFAULT_ANALISE_FINAL.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));

        // Check, that the count call also returns 1
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNaoConformidadeShouldNotBeFound(String filter) throws Exception {
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNaoConformidade() throws Exception {
        // Get the naoConformidade
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeService.save(naoConformidade);

        int databaseSizeBeforeUpdate = naoConformidadeRepository.findAll().size();

        // Update the naoConformidade
        NaoConformidade updatedNaoConformidade = naoConformidadeRepository.findById(naoConformidade.getId()).get();
        // Disconnect from session so that the updates on updatedNaoConformidade are not directly saved in db
        em.detach(updatedNaoConformidade);
        updatedNaoConformidade
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .titulo(UPDATED_TITULO)
            .fato(UPDATED_FATO)
            .causa(UPDATED_CAUSA)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataConclusao(UPDATED_DATA_CONCLUSAO)
            .analiseFinal(UPDATED_ANALISE_FINAL)
            .statusSGQ(UPDATED_STATUS_SGQ);

        restNaoConformidadeMockMvc.perform(put("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaoConformidade)))
            .andExpect(status().isOk());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeUpdate);
        NaoConformidade testNaoConformidade = naoConformidadeList.get(naoConformidadeList.size() - 1);
        assertThat(testNaoConformidade.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testNaoConformidade.getIdUsuarioResponsavel()).isEqualTo(UPDATED_ID_USUARIO_RESPONSAVEL);
        assertThat(testNaoConformidade.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNaoConformidade.getFato()).isEqualTo(UPDATED_FATO);
        assertThat(testNaoConformidade.getCausa()).isEqualTo(UPDATED_CAUSA);
        assertThat(testNaoConformidade.getPrazoConclusao()).isEqualTo(UPDATED_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getNovoPrazoConclusao()).isEqualTo(UPDATED_NOVO_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testNaoConformidade.getDataConclusao()).isEqualTo(UPDATED_DATA_CONCLUSAO);
        assertThat(testNaoConformidade.getAnaliseFinal()).isEqualTo(UPDATED_ANALISE_FINAL);
        assertThat(testNaoConformidade.getStatusSGQ()).isEqualTo(UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void updateNonExistingNaoConformidade() throws Exception {
        int databaseSizeBeforeUpdate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaoConformidadeMockMvc.perform(put("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeService.save(naoConformidade);

        int databaseSizeBeforeDelete = naoConformidadeRepository.findAll().size();

        // Delete the naoConformidade
        restNaoConformidadeMockMvc.perform(delete("/api/nao-conformidades/{id}", naoConformidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
