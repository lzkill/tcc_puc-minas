package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.CampanhaRecall;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Produto;
import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.repository.CampanhaRecallRepository;
import com.lzkill.sgq.service.CampanhaRecallService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.CampanhaRecallCriteria;
import com.lzkill.sgq.service.CampanhaRecallQueryService;

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
 * Integration tests for the {@link CampanhaRecallResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class CampanhaRecallResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    @Autowired
    private CampanhaRecallRepository campanhaRecallRepository;

    @Autowired
    private CampanhaRecallService campanhaRecallService;

    @Autowired
    private CampanhaRecallQueryService campanhaRecallQueryService;

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

    private MockMvc restCampanhaRecallMockMvc;

    private CampanhaRecall campanhaRecall;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampanhaRecallResource campanhaRecallResource = new CampanhaRecallResource(campanhaRecallService, campanhaRecallQueryService);
        this.restCampanhaRecallMockMvc = MockMvcBuilders.standaloneSetup(campanhaRecallResource)
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
    public static CampanhaRecall createEntity(EntityManager em) {
        CampanhaRecall campanhaRecall = new CampanhaRecall()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .resultado(DEFAULT_RESULTADO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        campanhaRecall.setProduto(produto);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        campanhaRecall.setSetorResponsavel(setor);
        return campanhaRecall;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampanhaRecall createUpdatedEntity(EntityManager em) {
        CampanhaRecall campanhaRecall = new CampanhaRecall()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .resultado(UPDATED_RESULTADO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        campanhaRecall.setProduto(produto);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createUpdatedEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        campanhaRecall.setSetorResponsavel(setor);
        return campanhaRecall;
    }

    @BeforeEach
    public void initTest() {
        campanhaRecall = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampanhaRecall() throws Exception {
        int databaseSizeBeforeCreate = campanhaRecallRepository.findAll().size();

        // Create the CampanhaRecall
        restCampanhaRecallMockMvc.perform(post("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isCreated());

        // Validate the CampanhaRecall in the database
        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeCreate + 1);
        CampanhaRecall testCampanhaRecall = campanhaRecallList.get(campanhaRecallList.size() - 1);
        assertThat(testCampanhaRecall.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testCampanhaRecall.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCampanhaRecall.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCampanhaRecall.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testCampanhaRecall.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testCampanhaRecall.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testCampanhaRecall.getResultado()).isEqualTo(DEFAULT_RESULTADO);
    }

    @Test
    @Transactional
    public void createCampanhaRecallWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campanhaRecallRepository.findAll().size();

        // Create the CampanhaRecall with an existing ID
        campanhaRecall.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampanhaRecallMockMvc.perform(post("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isBadRequest());

        // Validate the CampanhaRecall in the database
        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = campanhaRecallRepository.findAll().size();
        // set the field null
        campanhaRecall.setIdUsuarioRegistro(null);

        // Create the CampanhaRecall, which fails.

        restCampanhaRecallMockMvc.perform(post("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isBadRequest());

        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = campanhaRecallRepository.findAll().size();
        // set the field null
        campanhaRecall.setTitulo(null);

        // Create the CampanhaRecall, which fails.

        restCampanhaRecallMockMvc.perform(post("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isBadRequest());

        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = campanhaRecallRepository.findAll().size();
        // set the field null
        campanhaRecall.setDataRegistro(null);

        // Create the CampanhaRecall, which fails.

        restCampanhaRecallMockMvc.perform(post("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isBadRequest());

        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecalls() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campanhaRecall.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getCampanhaRecall() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get the campanhaRecall
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls/{id}", campanhaRecall.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campanhaRecall.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()));
    }


    @Test
    @Transactional
    public void getCampanhaRecallsByIdFiltering() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        Long id = campanhaRecall.getId();

        defaultCampanhaRecallShouldBeFound("id.equals=" + id);
        defaultCampanhaRecallShouldNotBeFound("id.notEquals=" + id);

        defaultCampanhaRecallShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCampanhaRecallShouldNotBeFound("id.greaterThan=" + id);

        defaultCampanhaRecallShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCampanhaRecallShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro is not null
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the campanhaRecallList where idUsuarioRegistro is null
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the campanhaRecallList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultCampanhaRecallShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo equals to DEFAULT_TITULO
        defaultCampanhaRecallShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the campanhaRecallList where titulo equals to UPDATED_TITULO
        defaultCampanhaRecallShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo not equals to DEFAULT_TITULO
        defaultCampanhaRecallShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the campanhaRecallList where titulo not equals to UPDATED_TITULO
        defaultCampanhaRecallShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultCampanhaRecallShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the campanhaRecallList where titulo equals to UPDATED_TITULO
        defaultCampanhaRecallShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo is not null
        defaultCampanhaRecallShouldBeFound("titulo.specified=true");

        // Get all the campanhaRecallList where titulo is null
        defaultCampanhaRecallShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloContainsSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo contains DEFAULT_TITULO
        defaultCampanhaRecallShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the campanhaRecallList where titulo contains UPDATED_TITULO
        defaultCampanhaRecallShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where titulo does not contain DEFAULT_TITULO
        defaultCampanhaRecallShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the campanhaRecallList where titulo does not contain UPDATED_TITULO
        defaultCampanhaRecallShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultCampanhaRecallShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the campanhaRecallList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the campanhaRecallList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultCampanhaRecallShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultCampanhaRecallShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the campanhaRecallList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultCampanhaRecallShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataRegistro is not null
        defaultCampanhaRecallShouldBeFound("dataRegistro.specified=true");

        // Get all the campanhaRecallList where dataRegistro is null
        defaultCampanhaRecallShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataInicio equals to DEFAULT_DATA_INICIO
        defaultCampanhaRecallShouldBeFound("dataInicio.equals=" + DEFAULT_DATA_INICIO);

        // Get all the campanhaRecallList where dataInicio equals to UPDATED_DATA_INICIO
        defaultCampanhaRecallShouldNotBeFound("dataInicio.equals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataInicio not equals to DEFAULT_DATA_INICIO
        defaultCampanhaRecallShouldNotBeFound("dataInicio.notEquals=" + DEFAULT_DATA_INICIO);

        // Get all the campanhaRecallList where dataInicio not equals to UPDATED_DATA_INICIO
        defaultCampanhaRecallShouldBeFound("dataInicio.notEquals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataInicioIsInShouldWork() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataInicio in DEFAULT_DATA_INICIO or UPDATED_DATA_INICIO
        defaultCampanhaRecallShouldBeFound("dataInicio.in=" + DEFAULT_DATA_INICIO + "," + UPDATED_DATA_INICIO);

        // Get all the campanhaRecallList where dataInicio equals to UPDATED_DATA_INICIO
        defaultCampanhaRecallShouldNotBeFound("dataInicio.in=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataInicio is not null
        defaultCampanhaRecallShouldBeFound("dataInicio.specified=true");

        // Get all the campanhaRecallList where dataInicio is null
        defaultCampanhaRecallShouldNotBeFound("dataInicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataFimIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataFim equals to DEFAULT_DATA_FIM
        defaultCampanhaRecallShouldBeFound("dataFim.equals=" + DEFAULT_DATA_FIM);

        // Get all the campanhaRecallList where dataFim equals to UPDATED_DATA_FIM
        defaultCampanhaRecallShouldNotBeFound("dataFim.equals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataFim not equals to DEFAULT_DATA_FIM
        defaultCampanhaRecallShouldNotBeFound("dataFim.notEquals=" + DEFAULT_DATA_FIM);

        // Get all the campanhaRecallList where dataFim not equals to UPDATED_DATA_FIM
        defaultCampanhaRecallShouldBeFound("dataFim.notEquals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataFimIsInShouldWork() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataFim in DEFAULT_DATA_FIM or UPDATED_DATA_FIM
        defaultCampanhaRecallShouldBeFound("dataFim.in=" + DEFAULT_DATA_FIM + "," + UPDATED_DATA_FIM);

        // Get all the campanhaRecallList where dataFim equals to UPDATED_DATA_FIM
        defaultCampanhaRecallShouldNotBeFound("dataFim.in=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByDataFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);

        // Get all the campanhaRecallList where dataFim is not null
        defaultCampanhaRecallShouldBeFound("dataFim.specified=true");

        // Get all the campanhaRecallList where dataFim is null
        defaultCampanhaRecallShouldNotBeFound("dataFim.specified=false");
    }

    @Test
    @Transactional
    public void getAllCampanhaRecallsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        campanhaRecallRepository.saveAndFlush(campanhaRecall);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        campanhaRecall.addAnexo(anexo);
        campanhaRecallRepository.saveAndFlush(campanhaRecall);
        Long anexoId = anexo.getId();

        // Get all the campanhaRecallList where anexo equals to anexoId
        defaultCampanhaRecallShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the campanhaRecallList where anexo equals to anexoId + 1
        defaultCampanhaRecallShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllCampanhaRecallsByProdutoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Produto produto = campanhaRecall.getProduto();
        campanhaRecallRepository.saveAndFlush(campanhaRecall);
        Long produtoId = produto.getId();

        // Get all the campanhaRecallList where produto equals to produtoId
        defaultCampanhaRecallShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the campanhaRecallList where produto equals to produtoId + 1
        defaultCampanhaRecallShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllCampanhaRecallsBySetorResponsavelIsEqualToSomething() throws Exception {
        // Get already existing entity
        Setor setorResponsavel = campanhaRecall.getSetorResponsavel();
        campanhaRecallRepository.saveAndFlush(campanhaRecall);
        Long setorResponsavelId = setorResponsavel.getId();

        // Get all the campanhaRecallList where setorResponsavel equals to setorResponsavelId
        defaultCampanhaRecallShouldBeFound("setorResponsavelId.equals=" + setorResponsavelId);

        // Get all the campanhaRecallList where setorResponsavel equals to setorResponsavelId + 1
        defaultCampanhaRecallShouldNotBeFound("setorResponsavelId.equals=" + (setorResponsavelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCampanhaRecallShouldBeFound(String filter) throws Exception {
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campanhaRecall.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())));

        // Check, that the count call also returns 1
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCampanhaRecallShouldNotBeFound(String filter) throws Exception {
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCampanhaRecall() throws Exception {
        // Get the campanhaRecall
        restCampanhaRecallMockMvc.perform(get("/api/campanha-recalls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampanhaRecall() throws Exception {
        // Initialize the database
        campanhaRecallService.save(campanhaRecall);

        int databaseSizeBeforeUpdate = campanhaRecallRepository.findAll().size();

        // Update the campanhaRecall
        CampanhaRecall updatedCampanhaRecall = campanhaRecallRepository.findById(campanhaRecall.getId()).get();
        // Disconnect from session so that the updates on updatedCampanhaRecall are not directly saved in db
        em.detach(updatedCampanhaRecall);
        updatedCampanhaRecall
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .resultado(UPDATED_RESULTADO);

        restCampanhaRecallMockMvc.perform(put("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampanhaRecall)))
            .andExpect(status().isOk());

        // Validate the CampanhaRecall in the database
        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeUpdate);
        CampanhaRecall testCampanhaRecall = campanhaRecallList.get(campanhaRecallList.size() - 1);
        assertThat(testCampanhaRecall.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testCampanhaRecall.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCampanhaRecall.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCampanhaRecall.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testCampanhaRecall.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testCampanhaRecall.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testCampanhaRecall.getResultado()).isEqualTo(UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCampanhaRecall() throws Exception {
        int databaseSizeBeforeUpdate = campanhaRecallRepository.findAll().size();

        // Create the CampanhaRecall

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampanhaRecallMockMvc.perform(put("/api/campanha-recalls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campanhaRecall)))
            .andExpect(status().isBadRequest());

        // Validate the CampanhaRecall in the database
        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampanhaRecall() throws Exception {
        // Initialize the database
        campanhaRecallService.save(campanhaRecall);

        int databaseSizeBeforeDelete = campanhaRecallRepository.findAll().size();

        // Delete the campanhaRecall
        restCampanhaRecallMockMvc.perform(delete("/api/campanha-recalls/{id}", campanhaRecall.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CampanhaRecall> campanhaRecallList = campanhaRecallRepository.findAll();
        assertThat(campanhaRecallList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
