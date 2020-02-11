package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ResultadoAuditoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.repository.ResultadoAuditoriaRepository;
import com.lzkill.sgq.service.ResultadoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ResultadoAuditoriaCriteria;
import com.lzkill.sgq.service.ResultadoAuditoriaQueryService;

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

/**
 * Integration tests for the {@link ResultadoAuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ResultadoAuditoriaResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_RESPONSAVEL = 1;
    private static final Integer UPDATED_ID_USUARIO_RESPONSAVEL = 2;
    private static final Integer SMALLER_ID_USUARIO_RESPONSAVEL = 1 - 1;

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ResultadoAuditoriaRepository resultadoAuditoriaRepository;

    @Autowired
    private ResultadoAuditoriaService resultadoAuditoriaService;

    @Autowired
    private ResultadoAuditoriaQueryService resultadoAuditoriaQueryService;

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

    private MockMvc restResultadoAuditoriaMockMvc;

    private ResultadoAuditoria resultadoAuditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadoAuditoriaResource resultadoAuditoriaResource = new ResultadoAuditoriaResource(resultadoAuditoriaService, resultadoAuditoriaQueryService);
        this.restResultadoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(resultadoAuditoriaResource)
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
    public static ResultadoAuditoria createEntity(EntityManager em) {
        ResultadoAuditoria resultadoAuditoria = new ResultadoAuditoria()
            .idUsuarioResponsavel(DEFAULT_ID_USUARIO_RESPONSAVEL)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .descricao(DEFAULT_DESCRICAO);
        return resultadoAuditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadoAuditoria createUpdatedEntity(EntityManager em) {
        ResultadoAuditoria resultadoAuditoria = new ResultadoAuditoria()
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .descricao(UPDATED_DESCRICAO);
        return resultadoAuditoria;
    }

    @BeforeEach
    public void initTest() {
        resultadoAuditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadoAuditoria() throws Exception {
        int databaseSizeBeforeCreate = resultadoAuditoriaRepository.findAll().size();

        // Create the ResultadoAuditoria
        restResultadoAuditoriaMockMvc.perform(post("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isCreated());

        // Validate the ResultadoAuditoria in the database
        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadoAuditoria testResultadoAuditoria = resultadoAuditoriaList.get(resultadoAuditoriaList.size() - 1);
        assertThat(testResultadoAuditoria.getIdUsuarioResponsavel()).isEqualTo(DEFAULT_ID_USUARIO_RESPONSAVEL);
        assertThat(testResultadoAuditoria.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testResultadoAuditoria.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testResultadoAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createResultadoAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoAuditoriaRepository.findAll().size();

        // Create the ResultadoAuditoria with an existing ID
        resultadoAuditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoAuditoriaMockMvc.perform(post("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoAuditoria in the database
        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoAuditoriaRepository.findAll().size();
        // set the field null
        resultadoAuditoria.setIdUsuarioResponsavel(null);

        // Create the ResultadoAuditoria, which fails.

        restResultadoAuditoriaMockMvc.perform(post("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoAuditoriaRepository.findAll().size();
        // set the field null
        resultadoAuditoria.setDataInicio(null);

        // Create the ResultadoAuditoria, which fails.

        restResultadoAuditoriaMockMvc.perform(post("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoAuditoriaRepository.findAll().size();
        // set the field null
        resultadoAuditoria.setDataFim(null);

        // Create the ResultadoAuditoria, which fails.

        restResultadoAuditoriaMockMvc.perform(post("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditorias() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getResultadoAuditoria() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get the resultadoAuditoria
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias/{id}", resultadoAuditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadoAuditoria.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioResponsavel").value(DEFAULT_ID_USUARIO_RESPONSAVEL))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getResultadoAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        Long id = resultadoAuditoria.getId();

        defaultResultadoAuditoriaShouldBeFound("id.equals=" + id);
        defaultResultadoAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultResultadoAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultadoAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultResultadoAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultadoAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.equals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.equals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel not equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.notEquals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel not equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.notEquals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel in DEFAULT_ID_USUARIO_RESPONSAVEL or UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.in=" + DEFAULT_ID_USUARIO_RESPONSAVEL + "," + UPDATED_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.in=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is not null
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.specified=true");

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is null
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is greater than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is greater than or equal to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is less than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.lessThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is less than or equal to SMALLER_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.lessThanOrEqual=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsLessThanSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is less than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.lessThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is less than UPDATED_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.lessThan=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByIdUsuarioResponsavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is greater than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldNotBeFound("idUsuarioResponsavel.greaterThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the resultadoAuditoriaList where idUsuarioResponsavel is greater than SMALLER_ID_USUARIO_RESPONSAVEL
        defaultResultadoAuditoriaShouldBeFound("idUsuarioResponsavel.greaterThan=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataInicio equals to DEFAULT_DATA_INICIO
        defaultResultadoAuditoriaShouldBeFound("dataInicio.equals=" + DEFAULT_DATA_INICIO);

        // Get all the resultadoAuditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultResultadoAuditoriaShouldNotBeFound("dataInicio.equals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataInicio not equals to DEFAULT_DATA_INICIO
        defaultResultadoAuditoriaShouldNotBeFound("dataInicio.notEquals=" + DEFAULT_DATA_INICIO);

        // Get all the resultadoAuditoriaList where dataInicio not equals to UPDATED_DATA_INICIO
        defaultResultadoAuditoriaShouldBeFound("dataInicio.notEquals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataInicioIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataInicio in DEFAULT_DATA_INICIO or UPDATED_DATA_INICIO
        defaultResultadoAuditoriaShouldBeFound("dataInicio.in=" + DEFAULT_DATA_INICIO + "," + UPDATED_DATA_INICIO);

        // Get all the resultadoAuditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultResultadoAuditoriaShouldNotBeFound("dataInicio.in=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataInicio is not null
        defaultResultadoAuditoriaShouldBeFound("dataInicio.specified=true");

        // Get all the resultadoAuditoriaList where dataInicio is null
        defaultResultadoAuditoriaShouldNotBeFound("dataInicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataFimIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataFim equals to DEFAULT_DATA_FIM
        defaultResultadoAuditoriaShouldBeFound("dataFim.equals=" + DEFAULT_DATA_FIM);

        // Get all the resultadoAuditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultResultadoAuditoriaShouldNotBeFound("dataFim.equals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataFim not equals to DEFAULT_DATA_FIM
        defaultResultadoAuditoriaShouldNotBeFound("dataFim.notEquals=" + DEFAULT_DATA_FIM);

        // Get all the resultadoAuditoriaList where dataFim not equals to UPDATED_DATA_FIM
        defaultResultadoAuditoriaShouldBeFound("dataFim.notEquals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataFimIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataFim in DEFAULT_DATA_FIM or UPDATED_DATA_FIM
        defaultResultadoAuditoriaShouldBeFound("dataFim.in=" + DEFAULT_DATA_FIM + "," + UPDATED_DATA_FIM);

        // Get all the resultadoAuditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultResultadoAuditoriaShouldNotBeFound("dataFim.in=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByDataFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);

        // Get all the resultadoAuditoriaList where dataFim is not null
        defaultResultadoAuditoriaShouldBeFound("dataFim.specified=true");

        // Get all the resultadoAuditoriaList where dataFim is null
        defaultResultadoAuditoriaShouldNotBeFound("dataFim.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoAuditoriasByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        resultadoAuditoria.addNaoConformidade(naoConformidade);
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the resultadoAuditoriaList where naoConformidade equals to naoConformidadeId
        defaultResultadoAuditoriaShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the resultadoAuditoriaList where naoConformidade equals to naoConformidadeId + 1
        defaultResultadoAuditoriaShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoAuditoriasByProdutoNaoConformeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        ProdutoNaoConforme produtoNaoConforme = ProdutoNaoConformeResourceIT.createEntity(em);
        em.persist(produtoNaoConforme);
        em.flush();
        resultadoAuditoria.addProdutoNaoConforme(produtoNaoConforme);
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        Long produtoNaoConformeId = produtoNaoConforme.getId();

        // Get all the resultadoAuditoriaList where produtoNaoConforme equals to produtoNaoConformeId
        defaultResultadoAuditoriaShouldBeFound("produtoNaoConformeId.equals=" + produtoNaoConformeId);

        // Get all the resultadoAuditoriaList where produtoNaoConforme equals to produtoNaoConformeId + 1
        defaultResultadoAuditoriaShouldNotBeFound("produtoNaoConformeId.equals=" + (produtoNaoConformeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoAuditoriasByAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        Auditoria auditoria = AuditoriaResourceIT.createEntity(em);
        em.persist(auditoria);
        em.flush();
        resultadoAuditoria.setAuditoria(auditoria);
        resultadoAuditoriaRepository.saveAndFlush(resultadoAuditoria);
        Long auditoriaId = auditoria.getId();

        // Get all the resultadoAuditoriaList where auditoria equals to auditoriaId
        defaultResultadoAuditoriaShouldBeFound("auditoriaId.equals=" + auditoriaId);

        // Get all the resultadoAuditoriaList where auditoria equals to auditoriaId + 1
        defaultResultadoAuditoriaShouldNotBeFound("auditoriaId.equals=" + (auditoriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultadoAuditoriaShouldBeFound(String filter) throws Exception {
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultadoAuditoriaShouldNotBeFound(String filter) throws Exception {
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResultadoAuditoria() throws Exception {
        // Get the resultadoAuditoria
        restResultadoAuditoriaMockMvc.perform(get("/api/resultado-auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadoAuditoria() throws Exception {
        // Initialize the database
        resultadoAuditoriaService.save(resultadoAuditoria);

        int databaseSizeBeforeUpdate = resultadoAuditoriaRepository.findAll().size();

        // Update the resultadoAuditoria
        ResultadoAuditoria updatedResultadoAuditoria = resultadoAuditoriaRepository.findById(resultadoAuditoria.getId()).get();
        // Disconnect from session so that the updates on updatedResultadoAuditoria are not directly saved in db
        em.detach(updatedResultadoAuditoria);
        updatedResultadoAuditoria
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .descricao(UPDATED_DESCRICAO);

        restResultadoAuditoriaMockMvc.perform(put("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadoAuditoria)))
            .andExpect(status().isOk());

        // Validate the ResultadoAuditoria in the database
        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
        ResultadoAuditoria testResultadoAuditoria = resultadoAuditoriaList.get(resultadoAuditoriaList.size() - 1);
        assertThat(testResultadoAuditoria.getIdUsuarioResponsavel()).isEqualTo(UPDATED_ID_USUARIO_RESPONSAVEL);
        assertThat(testResultadoAuditoria.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testResultadoAuditoria.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testResultadoAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadoAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = resultadoAuditoriaRepository.findAll().size();

        // Create the ResultadoAuditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoAuditoriaMockMvc.perform(put("/api/resultado-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoAuditoria in the database
        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadoAuditoria() throws Exception {
        // Initialize the database
        resultadoAuditoriaService.save(resultadoAuditoria);

        int databaseSizeBeforeDelete = resultadoAuditoriaRepository.findAll().size();

        // Delete the resultadoAuditoria
        restResultadoAuditoriaMockMvc.perform(delete("/api/resultado-auditorias/{id}", resultadoAuditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultadoAuditoria> resultadoAuditoriaList = resultadoAuditoriaRepository.findAll();
        assertThat(resultadoAuditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
