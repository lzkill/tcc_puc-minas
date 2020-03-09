package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.ResultadoChecklistRepository;
import com.lzkill.sgq.service.ResultadoChecklistService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ResultadoChecklistCriteria;
import com.lzkill.sgq.service.ResultadoChecklistQueryService;

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

/**
 * Integration tests for the {@link ResultadoChecklistResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ResultadoChecklistResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_VERIFICACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VERIFICACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResultadoChecklistRepository resultadoChecklistRepository;

    @Mock
    private ResultadoChecklistRepository resultadoChecklistRepositoryMock;

    @Mock
    private ResultadoChecklistService resultadoChecklistServiceMock;

    @Autowired
    private ResultadoChecklistService resultadoChecklistService;

    @Autowired
    private ResultadoChecklistQueryService resultadoChecklistQueryService;

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

    private MockMvc restResultadoChecklistMockMvc;

    private ResultadoChecklist resultadoChecklist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadoChecklistResource resultadoChecklistResource = new ResultadoChecklistResource(resultadoChecklistService, resultadoChecklistQueryService);
        this.restResultadoChecklistMockMvc = MockMvcBuilders.standaloneSetup(resultadoChecklistResource)
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
    public static ResultadoChecklist createEntity(EntityManager em) {
        ResultadoChecklist resultadoChecklist = new ResultadoChecklist()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataVerificacao(DEFAULT_DATA_VERIFICACAO);
        // Add required entity
        Checklist checklist;
        if (TestUtil.findAll(em, Checklist.class).isEmpty()) {
            checklist = ChecklistResourceIT.createEntity(em);
            em.persist(checklist);
            em.flush();
        } else {
            checklist = TestUtil.findAll(em, Checklist.class).get(0);
        }
        resultadoChecklist.setChecklist(checklist);
        return resultadoChecklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadoChecklist createUpdatedEntity(EntityManager em) {
        ResultadoChecklist resultadoChecklist = new ResultadoChecklist()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataVerificacao(UPDATED_DATA_VERIFICACAO);
        // Add required entity
        Checklist checklist;
        if (TestUtil.findAll(em, Checklist.class).isEmpty()) {
            checklist = ChecklistResourceIT.createUpdatedEntity(em);
            em.persist(checklist);
            em.flush();
        } else {
            checklist = TestUtil.findAll(em, Checklist.class).get(0);
        }
        resultadoChecklist.setChecklist(checklist);
        return resultadoChecklist;
    }

    @BeforeEach
    public void initTest() {
        resultadoChecklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadoChecklist() throws Exception {
        int databaseSizeBeforeCreate = resultadoChecklistRepository.findAll().size();

        // Create the ResultadoChecklist
        restResultadoChecklistMockMvc.perform(post("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isCreated());

        // Validate the ResultadoChecklist in the database
        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadoChecklist testResultadoChecklist = resultadoChecklistList.get(resultadoChecklistList.size() - 1);
        assertThat(testResultadoChecklist.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testResultadoChecklist.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testResultadoChecklist.getDataVerificacao()).isEqualTo(DEFAULT_DATA_VERIFICACAO);
    }

    @Test
    @Transactional
    public void createResultadoChecklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoChecklistRepository.findAll().size();

        // Create the ResultadoChecklist with an existing ID
        resultadoChecklist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoChecklistMockMvc.perform(post("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoChecklist in the database
        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoChecklistRepository.findAll().size();
        // set the field null
        resultadoChecklist.setIdUsuarioRegistro(null);

        // Create the ResultadoChecklist, which fails.

        restResultadoChecklistMockMvc.perform(post("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isBadRequest());

        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoChecklistRepository.findAll().size();
        // set the field null
        resultadoChecklist.setDataRegistro(null);

        // Create the ResultadoChecklist, which fails.

        restResultadoChecklistMockMvc.perform(post("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isBadRequest());

        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataVerificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoChecklistRepository.findAll().size();
        // set the field null
        resultadoChecklist.setDataVerificacao(null);

        // Create the ResultadoChecklist, which fails.

        restResultadoChecklistMockMvc.perform(post("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isBadRequest());

        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklists() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataVerificacao").value(hasItem(DEFAULT_DATA_VERIFICACAO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllResultadoChecklistsWithEagerRelationshipsIsEnabled() throws Exception {
        ResultadoChecklistResource resultadoChecklistResource = new ResultadoChecklistResource(resultadoChecklistServiceMock, resultadoChecklistQueryService);
        when(resultadoChecklistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restResultadoChecklistMockMvc = MockMvcBuilders.standaloneSetup(resultadoChecklistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists?eagerload=true"))
        .andExpect(status().isOk());

        verify(resultadoChecklistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllResultadoChecklistsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ResultadoChecklistResource resultadoChecklistResource = new ResultadoChecklistResource(resultadoChecklistServiceMock, resultadoChecklistQueryService);
            when(resultadoChecklistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restResultadoChecklistMockMvc = MockMvcBuilders.standaloneSetup(resultadoChecklistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists?eagerload=true"))
        .andExpect(status().isOk());

            verify(resultadoChecklistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getResultadoChecklist() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get the resultadoChecklist
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists/{id}", resultadoChecklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadoChecklist.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataVerificacao").value(DEFAULT_DATA_VERIFICACAO.toString()));
    }


    @Test
    @Transactional
    public void getResultadoChecklistsByIdFiltering() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        Long id = resultadoChecklist.getId();

        defaultResultadoChecklistShouldBeFound("id.equals=" + id);
        defaultResultadoChecklistShouldNotBeFound("id.notEquals=" + id);

        defaultResultadoChecklistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultadoChecklistShouldNotBeFound("id.greaterThan=" + id);

        defaultResultadoChecklistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultadoChecklistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro is not null
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the resultadoChecklistList where idUsuarioRegistro is null
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the resultadoChecklistList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultResultadoChecklistShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultResultadoChecklistShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the resultadoChecklistList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the resultadoChecklistList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultResultadoChecklistShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultResultadoChecklistShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the resultadoChecklistList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultResultadoChecklistShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataRegistro is not null
        defaultResultadoChecklistShouldBeFound("dataRegistro.specified=true");

        // Get all the resultadoChecklistList where dataRegistro is null
        defaultResultadoChecklistShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataVerificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataVerificacao equals to DEFAULT_DATA_VERIFICACAO
        defaultResultadoChecklistShouldBeFound("dataVerificacao.equals=" + DEFAULT_DATA_VERIFICACAO);

        // Get all the resultadoChecklistList where dataVerificacao equals to UPDATED_DATA_VERIFICACAO
        defaultResultadoChecklistShouldNotBeFound("dataVerificacao.equals=" + UPDATED_DATA_VERIFICACAO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataVerificacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataVerificacao not equals to DEFAULT_DATA_VERIFICACAO
        defaultResultadoChecklistShouldNotBeFound("dataVerificacao.notEquals=" + DEFAULT_DATA_VERIFICACAO);

        // Get all the resultadoChecklistList where dataVerificacao not equals to UPDATED_DATA_VERIFICACAO
        defaultResultadoChecklistShouldBeFound("dataVerificacao.notEquals=" + UPDATED_DATA_VERIFICACAO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataVerificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataVerificacao in DEFAULT_DATA_VERIFICACAO or UPDATED_DATA_VERIFICACAO
        defaultResultadoChecklistShouldBeFound("dataVerificacao.in=" + DEFAULT_DATA_VERIFICACAO + "," + UPDATED_DATA_VERIFICACAO);

        // Get all the resultadoChecklistList where dataVerificacao equals to UPDATED_DATA_VERIFICACAO
        defaultResultadoChecklistShouldNotBeFound("dataVerificacao.in=" + UPDATED_DATA_VERIFICACAO);
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByDataVerificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);

        // Get all the resultadoChecklistList where dataVerificacao is not null
        defaultResultadoChecklistShouldBeFound("dataVerificacao.specified=true");

        // Get all the resultadoChecklistList where dataVerificacao is null
        defaultResultadoChecklistShouldNotBeFound("dataVerificacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoChecklistsByResultadoItemIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        ResultadoItemChecklist resultadoItem = ResultadoItemChecklistResourceIT.createEntity(em);
        em.persist(resultadoItem);
        em.flush();
        resultadoChecklist.addResultadoItem(resultadoItem);
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Long resultadoItemId = resultadoItem.getId();

        // Get all the resultadoChecklistList where resultadoItem equals to resultadoItemId
        defaultResultadoChecklistShouldBeFound("resultadoItemId.equals=" + resultadoItemId);

        // Get all the resultadoChecklistList where resultadoItem equals to resultadoItemId + 1
        defaultResultadoChecklistShouldNotBeFound("resultadoItemId.equals=" + (resultadoItemId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        resultadoChecklist.addNaoConformidade(naoConformidade);
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the resultadoChecklistList where naoConformidade equals to naoConformidadeId
        defaultResultadoChecklistShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the resultadoChecklistList where naoConformidade equals to naoConformidadeId + 1
        defaultResultadoChecklistShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByProdutoNaoConformeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        ProdutoNaoConforme produtoNaoConforme = ProdutoNaoConformeResourceIT.createEntity(em);
        em.persist(produtoNaoConforme);
        em.flush();
        resultadoChecklist.addProdutoNaoConforme(produtoNaoConforme);
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Long produtoNaoConformeId = produtoNaoConforme.getId();

        // Get all the resultadoChecklistList where produtoNaoConforme equals to produtoNaoConformeId
        defaultResultadoChecklistShouldBeFound("produtoNaoConformeId.equals=" + produtoNaoConformeId);

        // Get all the resultadoChecklistList where produtoNaoConforme equals to produtoNaoConformeId + 1
        defaultResultadoChecklistShouldNotBeFound("produtoNaoConformeId.equals=" + (produtoNaoConformeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByChecklistIsEqualToSomething() throws Exception {
        // Get already existing entity
        Checklist checklist = resultadoChecklist.getChecklist();
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Long checklistId = checklist.getId();

        // Get all the resultadoChecklistList where checklist equals to checklistId
        defaultResultadoChecklistShouldBeFound("checklistId.equals=" + checklistId);

        // Get all the resultadoChecklistList where checklist equals to checklistId + 1
        defaultResultadoChecklistShouldNotBeFound("checklistId.equals=" + (checklistId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoChecklistsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        resultadoChecklist.addAnexo(anexo);
        resultadoChecklistRepository.saveAndFlush(resultadoChecklist);
        Long anexoId = anexo.getId();

        // Get all the resultadoChecklistList where anexo equals to anexoId
        defaultResultadoChecklistShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the resultadoChecklistList where anexo equals to anexoId + 1
        defaultResultadoChecklistShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultadoChecklistShouldBeFound(String filter) throws Exception {
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataVerificacao").value(hasItem(DEFAULT_DATA_VERIFICACAO.toString())));

        // Check, that the count call also returns 1
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultadoChecklistShouldNotBeFound(String filter) throws Exception {
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResultadoChecklist() throws Exception {
        // Get the resultadoChecklist
        restResultadoChecklistMockMvc.perform(get("/api/resultado-checklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadoChecklist() throws Exception {
        // Initialize the database
        resultadoChecklistService.save(resultadoChecklist);

        int databaseSizeBeforeUpdate = resultadoChecklistRepository.findAll().size();

        // Update the resultadoChecklist
        ResultadoChecklist updatedResultadoChecklist = resultadoChecklistRepository.findById(resultadoChecklist.getId()).get();
        // Disconnect from session so that the updates on updatedResultadoChecklist are not directly saved in db
        em.detach(updatedResultadoChecklist);
        updatedResultadoChecklist
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataVerificacao(UPDATED_DATA_VERIFICACAO);

        restResultadoChecklistMockMvc.perform(put("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadoChecklist)))
            .andExpect(status().isOk());

        // Validate the ResultadoChecklist in the database
        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeUpdate);
        ResultadoChecklist testResultadoChecklist = resultadoChecklistList.get(resultadoChecklistList.size() - 1);
        assertThat(testResultadoChecklist.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testResultadoChecklist.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testResultadoChecklist.getDataVerificacao()).isEqualTo(UPDATED_DATA_VERIFICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadoChecklist() throws Exception {
        int databaseSizeBeforeUpdate = resultadoChecklistRepository.findAll().size();

        // Create the ResultadoChecklist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoChecklistMockMvc.perform(put("/api/resultado-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoChecklist in the database
        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadoChecklist() throws Exception {
        // Initialize the database
        resultadoChecklistService.save(resultadoChecklist);

        int databaseSizeBeforeDelete = resultadoChecklistRepository.findAll().size();

        // Delete the resultadoChecklist
        restResultadoChecklistMockMvc.perform(delete("/api/resultado-checklists/{id}", resultadoChecklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultadoChecklist> resultadoChecklistList = resultadoChecklistRepository.findAll();
        assertThat(resultadoChecklistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
