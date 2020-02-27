package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.repository.AnaliseConsultoriaRepository;
import com.lzkill.sgq.service.AnaliseConsultoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.AnaliseConsultoriaCriteria;
import com.lzkill.sgq.service.AnaliseConsultoriaQueryService;

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

import com.lzkill.sgq.domain.enumeration.StatusAprovacao;
/**
 * Integration tests for the {@link AnaliseConsultoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class AnaliseConsultoriaResourceIT {

    private static final Instant DEFAULT_DATA_ANALISE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ANALISE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL = "BBBBBBBBBB";

    private static final StatusAprovacao DEFAULT_STATUS = StatusAprovacao.APROVADO;
    private static final StatusAprovacao UPDATED_STATUS = StatusAprovacao.APROVADO_PARCIALMENTE;

    @Autowired
    private AnaliseConsultoriaRepository analiseConsultoriaRepository;

    @Mock
    private AnaliseConsultoriaRepository analiseConsultoriaRepositoryMock;

    @Mock
    private AnaliseConsultoriaService analiseConsultoriaServiceMock;

    @Autowired
    private AnaliseConsultoriaService analiseConsultoriaService;

    @Autowired
    private AnaliseConsultoriaQueryService analiseConsultoriaQueryService;

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

    private MockMvc restAnaliseConsultoriaMockMvc;

    private AnaliseConsultoria analiseConsultoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnaliseConsultoriaResource analiseConsultoriaResource = new AnaliseConsultoriaResource(analiseConsultoriaService, analiseConsultoriaQueryService);
        this.restAnaliseConsultoriaMockMvc = MockMvcBuilders.standaloneSetup(analiseConsultoriaResource)
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
    public static AnaliseConsultoria createEntity(EntityManager em) {
        AnaliseConsultoria analiseConsultoria = new AnaliseConsultoria()
            .dataAnalise(DEFAULT_DATA_ANALISE)
            .conteudo(DEFAULT_CONTEUDO)
            .responsavel(DEFAULT_RESPONSAVEL)
            .status(DEFAULT_STATUS);
        // Add required entity
        SolicitacaoAnalise solicitacaoAnalise;
        if (TestUtil.findAll(em, SolicitacaoAnalise.class).isEmpty()) {
            solicitacaoAnalise = SolicitacaoAnaliseResourceIT.createEntity(em);
            em.persist(solicitacaoAnalise);
            em.flush();
        } else {
            solicitacaoAnalise = TestUtil.findAll(em, SolicitacaoAnalise.class).get(0);
        }
        analiseConsultoria.setSolicitacaoAnalise(solicitacaoAnalise);
        return analiseConsultoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnaliseConsultoria createUpdatedEntity(EntityManager em) {
        AnaliseConsultoria analiseConsultoria = new AnaliseConsultoria()
            .dataAnalise(UPDATED_DATA_ANALISE)
            .conteudo(UPDATED_CONTEUDO)
            .responsavel(UPDATED_RESPONSAVEL)
            .status(UPDATED_STATUS);
        // Add required entity
        SolicitacaoAnalise solicitacaoAnalise;
        if (TestUtil.findAll(em, SolicitacaoAnalise.class).isEmpty()) {
            solicitacaoAnalise = SolicitacaoAnaliseResourceIT.createUpdatedEntity(em);
            em.persist(solicitacaoAnalise);
            em.flush();
        } else {
            solicitacaoAnalise = TestUtil.findAll(em, SolicitacaoAnalise.class).get(0);
        }
        analiseConsultoria.setSolicitacaoAnalise(solicitacaoAnalise);
        return analiseConsultoria;
    }

    @BeforeEach
    public void initTest() {
        analiseConsultoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnaliseConsultoria() throws Exception {
        int databaseSizeBeforeCreate = analiseConsultoriaRepository.findAll().size();

        // Create the AnaliseConsultoria
        restAnaliseConsultoriaMockMvc.perform(post("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isCreated());

        // Validate the AnaliseConsultoria in the database
        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeCreate + 1);
        AnaliseConsultoria testAnaliseConsultoria = analiseConsultoriaList.get(analiseConsultoriaList.size() - 1);
        assertThat(testAnaliseConsultoria.getDataAnalise()).isEqualTo(DEFAULT_DATA_ANALISE);
        assertThat(testAnaliseConsultoria.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testAnaliseConsultoria.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);
        assertThat(testAnaliseConsultoria.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAnaliseConsultoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analiseConsultoriaRepository.findAll().size();

        // Create the AnaliseConsultoria with an existing ID
        analiseConsultoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnaliseConsultoriaMockMvc.perform(post("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isBadRequest());

        // Validate the AnaliseConsultoria in the database
        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataAnaliseIsRequired() throws Exception {
        int databaseSizeBeforeTest = analiseConsultoriaRepository.findAll().size();
        // set the field null
        analiseConsultoria.setDataAnalise(null);

        // Create the AnaliseConsultoria, which fails.

        restAnaliseConsultoriaMockMvc.perform(post("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isBadRequest());

        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = analiseConsultoriaRepository.findAll().size();
        // set the field null
        analiseConsultoria.setResponsavel(null);

        // Create the AnaliseConsultoria, which fails.

        restAnaliseConsultoriaMockMvc.perform(post("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isBadRequest());

        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = analiseConsultoriaRepository.findAll().size();
        // set the field null
        analiseConsultoria.setStatus(null);

        // Create the AnaliseConsultoria, which fails.

        restAnaliseConsultoriaMockMvc.perform(post("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isBadRequest());

        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultorias() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analiseConsultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAnalise").value(hasItem(DEFAULT_DATA_ANALISE.toString())))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAnaliseConsultoriasWithEagerRelationshipsIsEnabled() throws Exception {
        AnaliseConsultoriaResource analiseConsultoriaResource = new AnaliseConsultoriaResource(analiseConsultoriaServiceMock, analiseConsultoriaQueryService);
        when(analiseConsultoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAnaliseConsultoriaMockMvc = MockMvcBuilders.standaloneSetup(analiseConsultoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias?eagerload=true"))
        .andExpect(status().isOk());

        verify(analiseConsultoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAnaliseConsultoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        AnaliseConsultoriaResource analiseConsultoriaResource = new AnaliseConsultoriaResource(analiseConsultoriaServiceMock, analiseConsultoriaQueryService);
            when(analiseConsultoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAnaliseConsultoriaMockMvc = MockMvcBuilders.standaloneSetup(analiseConsultoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias?eagerload=true"))
        .andExpect(status().isOk());

            verify(analiseConsultoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAnaliseConsultoria() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get the analiseConsultoria
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias/{id}", analiseConsultoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analiseConsultoria.getId().intValue()))
            .andExpect(jsonPath("$.dataAnalise").value(DEFAULT_DATA_ANALISE.toString()))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getAnaliseConsultoriasByIdFiltering() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        Long id = analiseConsultoria.getId();

        defaultAnaliseConsultoriaShouldBeFound("id.equals=" + id);
        defaultAnaliseConsultoriaShouldNotBeFound("id.notEquals=" + id);

        defaultAnaliseConsultoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnaliseConsultoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultAnaliseConsultoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnaliseConsultoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByDataAnaliseIsEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where dataAnalise equals to DEFAULT_DATA_ANALISE
        defaultAnaliseConsultoriaShouldBeFound("dataAnalise.equals=" + DEFAULT_DATA_ANALISE);

        // Get all the analiseConsultoriaList where dataAnalise equals to UPDATED_DATA_ANALISE
        defaultAnaliseConsultoriaShouldNotBeFound("dataAnalise.equals=" + UPDATED_DATA_ANALISE);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByDataAnaliseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where dataAnalise not equals to DEFAULT_DATA_ANALISE
        defaultAnaliseConsultoriaShouldNotBeFound("dataAnalise.notEquals=" + DEFAULT_DATA_ANALISE);

        // Get all the analiseConsultoriaList where dataAnalise not equals to UPDATED_DATA_ANALISE
        defaultAnaliseConsultoriaShouldBeFound("dataAnalise.notEquals=" + UPDATED_DATA_ANALISE);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByDataAnaliseIsInShouldWork() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where dataAnalise in DEFAULT_DATA_ANALISE or UPDATED_DATA_ANALISE
        defaultAnaliseConsultoriaShouldBeFound("dataAnalise.in=" + DEFAULT_DATA_ANALISE + "," + UPDATED_DATA_ANALISE);

        // Get all the analiseConsultoriaList where dataAnalise equals to UPDATED_DATA_ANALISE
        defaultAnaliseConsultoriaShouldNotBeFound("dataAnalise.in=" + UPDATED_DATA_ANALISE);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByDataAnaliseIsNullOrNotNull() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where dataAnalise is not null
        defaultAnaliseConsultoriaShouldBeFound("dataAnalise.specified=true");

        // Get all the analiseConsultoriaList where dataAnalise is null
        defaultAnaliseConsultoriaShouldNotBeFound("dataAnalise.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel equals to DEFAULT_RESPONSAVEL
        defaultAnaliseConsultoriaShouldBeFound("responsavel.equals=" + DEFAULT_RESPONSAVEL);

        // Get all the analiseConsultoriaList where responsavel equals to UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.equals=" + UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel not equals to DEFAULT_RESPONSAVEL
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.notEquals=" + DEFAULT_RESPONSAVEL);

        // Get all the analiseConsultoriaList where responsavel not equals to UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldBeFound("responsavel.notEquals=" + UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel in DEFAULT_RESPONSAVEL or UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldBeFound("responsavel.in=" + DEFAULT_RESPONSAVEL + "," + UPDATED_RESPONSAVEL);

        // Get all the analiseConsultoriaList where responsavel equals to UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.in=" + UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel is not null
        defaultAnaliseConsultoriaShouldBeFound("responsavel.specified=true");

        // Get all the analiseConsultoriaList where responsavel is null
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelContainsSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel contains DEFAULT_RESPONSAVEL
        defaultAnaliseConsultoriaShouldBeFound("responsavel.contains=" + DEFAULT_RESPONSAVEL);

        // Get all the analiseConsultoriaList where responsavel contains UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.contains=" + UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByResponsavelNotContainsSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where responsavel does not contain DEFAULT_RESPONSAVEL
        defaultAnaliseConsultoriaShouldNotBeFound("responsavel.doesNotContain=" + DEFAULT_RESPONSAVEL);

        // Get all the analiseConsultoriaList where responsavel does not contain UPDATED_RESPONSAVEL
        defaultAnaliseConsultoriaShouldBeFound("responsavel.doesNotContain=" + UPDATED_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where status equals to DEFAULT_STATUS
        defaultAnaliseConsultoriaShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the analiseConsultoriaList where status equals to UPDATED_STATUS
        defaultAnaliseConsultoriaShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where status not equals to DEFAULT_STATUS
        defaultAnaliseConsultoriaShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the analiseConsultoriaList where status not equals to UPDATED_STATUS
        defaultAnaliseConsultoriaShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAnaliseConsultoriaShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the analiseConsultoriaList where status equals to UPDATED_STATUS
        defaultAnaliseConsultoriaShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);

        // Get all the analiseConsultoriaList where status is not null
        defaultAnaliseConsultoriaShouldBeFound("status.specified=true");

        // Get all the analiseConsultoriaList where status is null
        defaultAnaliseConsultoriaShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnaliseConsultoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        analiseConsultoria.addAnexo(anexo);
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);
        Long anexoId = anexo.getId();

        // Get all the analiseConsultoriaList where anexo equals to anexoId
        defaultAnaliseConsultoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the analiseConsultoriaList where anexo equals to anexoId + 1
        defaultAnaliseConsultoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnaliseConsultoriasBySolicitacaoAnaliseIsEqualToSomething() throws Exception {
        // Get already existing entity
        SolicitacaoAnalise solicitacaoAnalise = analiseConsultoria.getSolicitacaoAnalise();
        analiseConsultoriaRepository.saveAndFlush(analiseConsultoria);
        Long solicitacaoAnaliseId = solicitacaoAnalise.getId();

        // Get all the analiseConsultoriaList where solicitacaoAnalise equals to solicitacaoAnaliseId
        defaultAnaliseConsultoriaShouldBeFound("solicitacaoAnaliseId.equals=" + solicitacaoAnaliseId);

        // Get all the analiseConsultoriaList where solicitacaoAnalise equals to solicitacaoAnaliseId + 1
        defaultAnaliseConsultoriaShouldNotBeFound("solicitacaoAnaliseId.equals=" + (solicitacaoAnaliseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnaliseConsultoriaShouldBeFound(String filter) throws Exception {
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analiseConsultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAnalise").value(hasItem(DEFAULT_DATA_ANALISE.toString())))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnaliseConsultoriaShouldNotBeFound(String filter) throws Exception {
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnaliseConsultoria() throws Exception {
        // Get the analiseConsultoria
        restAnaliseConsultoriaMockMvc.perform(get("/api/analise-consultorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnaliseConsultoria() throws Exception {
        // Initialize the database
        analiseConsultoriaService.save(analiseConsultoria);

        int databaseSizeBeforeUpdate = analiseConsultoriaRepository.findAll().size();

        // Update the analiseConsultoria
        AnaliseConsultoria updatedAnaliseConsultoria = analiseConsultoriaRepository.findById(analiseConsultoria.getId()).get();
        // Disconnect from session so that the updates on updatedAnaliseConsultoria are not directly saved in db
        em.detach(updatedAnaliseConsultoria);
        updatedAnaliseConsultoria
            .dataAnalise(UPDATED_DATA_ANALISE)
            .conteudo(UPDATED_CONTEUDO)
            .responsavel(UPDATED_RESPONSAVEL)
            .status(UPDATED_STATUS);

        restAnaliseConsultoriaMockMvc.perform(put("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnaliseConsultoria)))
            .andExpect(status().isOk());

        // Validate the AnaliseConsultoria in the database
        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeUpdate);
        AnaliseConsultoria testAnaliseConsultoria = analiseConsultoriaList.get(analiseConsultoriaList.size() - 1);
        assertThat(testAnaliseConsultoria.getDataAnalise()).isEqualTo(UPDATED_DATA_ANALISE);
        assertThat(testAnaliseConsultoria.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testAnaliseConsultoria.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);
        assertThat(testAnaliseConsultoria.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAnaliseConsultoria() throws Exception {
        int databaseSizeBeforeUpdate = analiseConsultoriaRepository.findAll().size();

        // Create the AnaliseConsultoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnaliseConsultoriaMockMvc.perform(put("/api/analise-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analiseConsultoria)))
            .andExpect(status().isBadRequest());

        // Validate the AnaliseConsultoria in the database
        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnaliseConsultoria() throws Exception {
        // Initialize the database
        analiseConsultoriaService.save(analiseConsultoria);

        int databaseSizeBeforeDelete = analiseConsultoriaRepository.findAll().size();

        // Delete the analiseConsultoria
        restAnaliseConsultoriaMockMvc.perform(delete("/api/analise-consultorias/{id}", analiseConsultoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnaliseConsultoria> analiseConsultoriaList = analiseConsultoriaRepository.findAll();
        assertThat(analiseConsultoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
