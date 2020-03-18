package com.acme.normas.web.rest;

import com.acme.normas.NormasApp;
import com.acme.normas.domain.Norma;
import com.acme.normas.repository.NormaRepository;
import com.acme.normas.service.NormaService;
import com.acme.normas.web.rest.errors.ExceptionTranslator;
import com.acme.normas.service.dto.NormaCriteria;
import com.acme.normas.service.NormaQueryService;

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

import static com.acme.normas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.acme.normas.domain.enumeration.OrgaoNorma;
import com.acme.normas.domain.enumeration.CategoriaNorma;
/**
 * Integration tests for the {@link NormaResource} REST controller.
 */
@SpringBootTest(classes = NormasApp.class)
public class NormaResourceIT {

    private static final OrgaoNorma DEFAULT_ORGAO = OrgaoNorma.ABNT;
    private static final OrgaoNorma UPDATED_ORGAO = OrgaoNorma.IEEE;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_VERSAO = "AAAAAAAAAA";
    private static final String UPDATED_VERSAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_EDICAO = 1;
    private static final Integer UPDATED_NUMERO_EDICAO = 2;
    private static final Integer SMALLER_NUMERO_EDICAO = 1 - 1;

    private static final Instant DEFAULT_DATA_EDICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EDICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_INICIO_VALIDADE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO_VALIDADE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CategoriaNorma DEFAULT_CATEGORIA = CategoriaNorma.QUALIDADE;
    private static final CategoriaNorma UPDATED_CATEGORIA = CategoriaNorma.AMBIENTAL;

    private static final String DEFAULT_URL_DOWNLOAD = "AAAAAAAAAA";
    private static final String UPDATED_URL_DOWNLOAD = "BBBBBBBBBB";

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private NormaService normaService;

    @Autowired
    private NormaQueryService normaQueryService;

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

    private MockMvc restNormaMockMvc;

    private Norma norma;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NormaResource normaResource = new NormaResource(normaService, normaQueryService);
        this.restNormaMockMvc = MockMvcBuilders.standaloneSetup(normaResource)
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
    public static Norma createEntity(EntityManager em) {
        Norma norma = new Norma()
            .orgao(DEFAULT_ORGAO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .versao(DEFAULT_VERSAO)
            .numeroEdicao(DEFAULT_NUMERO_EDICAO)
            .dataEdicao(DEFAULT_DATA_EDICAO)
            .dataInicioValidade(DEFAULT_DATA_INICIO_VALIDADE)
            .categoria(DEFAULT_CATEGORIA)
            .urlDownload(DEFAULT_URL_DOWNLOAD);
        return norma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Norma createUpdatedEntity(EntityManager em) {
        Norma norma = new Norma()
            .orgao(UPDATED_ORGAO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .versao(UPDATED_VERSAO)
            .numeroEdicao(UPDATED_NUMERO_EDICAO)
            .dataEdicao(UPDATED_DATA_EDICAO)
            .dataInicioValidade(UPDATED_DATA_INICIO_VALIDADE)
            .categoria(UPDATED_CATEGORIA)
            .urlDownload(UPDATED_URL_DOWNLOAD);
        return norma;
    }

    @BeforeEach
    public void initTest() {
        norma = createEntity(em);
    }

    @Test
    @Transactional
    public void createNorma() throws Exception {
        int databaseSizeBeforeCreate = normaRepository.findAll().size();

        // Create the Norma
        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isCreated());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeCreate + 1);
        Norma testNorma = normaList.get(normaList.size() - 1);
        assertThat(testNorma.getOrgao()).isEqualTo(DEFAULT_ORGAO);
        assertThat(testNorma.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNorma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNorma.getVersao()).isEqualTo(DEFAULT_VERSAO);
        assertThat(testNorma.getNumeroEdicao()).isEqualTo(DEFAULT_NUMERO_EDICAO);
        assertThat(testNorma.getDataEdicao()).isEqualTo(DEFAULT_DATA_EDICAO);
        assertThat(testNorma.getDataInicioValidade()).isEqualTo(DEFAULT_DATA_INICIO_VALIDADE);
        assertThat(testNorma.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testNorma.getUrlDownload()).isEqualTo(DEFAULT_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void createNormaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaRepository.findAll().size();

        // Create the Norma with an existing ID
        norma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrgaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = normaRepository.findAll().size();
        // set the field null
        norma.setOrgao(null);

        // Create the Norma, which fails.

        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = normaRepository.findAll().size();
        // set the field null
        norma.setTitulo(null);

        // Create the Norma, which fails.

        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlDownloadIsRequired() throws Exception {
        int databaseSizeBeforeTest = normaRepository.findAll().size();
        // set the field null
        norma.setUrlDownload(null);

        // Create the Norma, which fails.

        restNormaMockMvc.perform(post("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNormas() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList
        restNormaMockMvc.perform(get("/api/normas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(norma.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgao").value(hasItem(DEFAULT_ORGAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].numeroEdicao").value(hasItem(DEFAULT_NUMERO_EDICAO)))
            .andExpect(jsonPath("$.[*].dataEdicao").value(hasItem(DEFAULT_DATA_EDICAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicioValidade").value(hasItem(DEFAULT_DATA_INICIO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].urlDownload").value(hasItem(DEFAULT_URL_DOWNLOAD)));
    }
    
    @Test
    @Transactional
    public void getNorma() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get the norma
        restNormaMockMvc.perform(get("/api/normas/{id}", norma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(norma.getId().intValue()))
            .andExpect(jsonPath("$.orgao").value(DEFAULT_ORGAO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO))
            .andExpect(jsonPath("$.numeroEdicao").value(DEFAULT_NUMERO_EDICAO))
            .andExpect(jsonPath("$.dataEdicao").value(DEFAULT_DATA_EDICAO.toString()))
            .andExpect(jsonPath("$.dataInicioValidade").value(DEFAULT_DATA_INICIO_VALIDADE.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.urlDownload").value(DEFAULT_URL_DOWNLOAD));
    }


    @Test
    @Transactional
    public void getNormasByIdFiltering() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        Long id = norma.getId();

        defaultNormaShouldBeFound("id.equals=" + id);
        defaultNormaShouldNotBeFound("id.notEquals=" + id);

        defaultNormaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNormaShouldNotBeFound("id.greaterThan=" + id);

        defaultNormaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNormaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNormasByOrgaoIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where orgao equals to DEFAULT_ORGAO
        defaultNormaShouldBeFound("orgao.equals=" + DEFAULT_ORGAO);

        // Get all the normaList where orgao equals to UPDATED_ORGAO
        defaultNormaShouldNotBeFound("orgao.equals=" + UPDATED_ORGAO);
    }

    @Test
    @Transactional
    public void getAllNormasByOrgaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where orgao not equals to DEFAULT_ORGAO
        defaultNormaShouldNotBeFound("orgao.notEquals=" + DEFAULT_ORGAO);

        // Get all the normaList where orgao not equals to UPDATED_ORGAO
        defaultNormaShouldBeFound("orgao.notEquals=" + UPDATED_ORGAO);
    }

    @Test
    @Transactional
    public void getAllNormasByOrgaoIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where orgao in DEFAULT_ORGAO or UPDATED_ORGAO
        defaultNormaShouldBeFound("orgao.in=" + DEFAULT_ORGAO + "," + UPDATED_ORGAO);

        // Get all the normaList where orgao equals to UPDATED_ORGAO
        defaultNormaShouldNotBeFound("orgao.in=" + UPDATED_ORGAO);
    }

    @Test
    @Transactional
    public void getAllNormasByOrgaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where orgao is not null
        defaultNormaShouldBeFound("orgao.specified=true");

        // Get all the normaList where orgao is null
        defaultNormaShouldNotBeFound("orgao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo equals to DEFAULT_TITULO
        defaultNormaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the normaList where titulo equals to UPDATED_TITULO
        defaultNormaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo not equals to DEFAULT_TITULO
        defaultNormaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the normaList where titulo not equals to UPDATED_TITULO
        defaultNormaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultNormaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the normaList where titulo equals to UPDATED_TITULO
        defaultNormaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo is not null
        defaultNormaShouldBeFound("titulo.specified=true");

        // Get all the normaList where titulo is null
        defaultNormaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNormasByTituloContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo contains DEFAULT_TITULO
        defaultNormaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the normaList where titulo contains UPDATED_TITULO
        defaultNormaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where titulo does not contain DEFAULT_TITULO
        defaultNormaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the normaList where titulo does not contain UPDATED_TITULO
        defaultNormaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllNormasByVersaoIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao equals to DEFAULT_VERSAO
        defaultNormaShouldBeFound("versao.equals=" + DEFAULT_VERSAO);

        // Get all the normaList where versao equals to UPDATED_VERSAO
        defaultNormaShouldNotBeFound("versao.equals=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    public void getAllNormasByVersaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao not equals to DEFAULT_VERSAO
        defaultNormaShouldNotBeFound("versao.notEquals=" + DEFAULT_VERSAO);

        // Get all the normaList where versao not equals to UPDATED_VERSAO
        defaultNormaShouldBeFound("versao.notEquals=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    public void getAllNormasByVersaoIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao in DEFAULT_VERSAO or UPDATED_VERSAO
        defaultNormaShouldBeFound("versao.in=" + DEFAULT_VERSAO + "," + UPDATED_VERSAO);

        // Get all the normaList where versao equals to UPDATED_VERSAO
        defaultNormaShouldNotBeFound("versao.in=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    public void getAllNormasByVersaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao is not null
        defaultNormaShouldBeFound("versao.specified=true");

        // Get all the normaList where versao is null
        defaultNormaShouldNotBeFound("versao.specified=false");
    }
                @Test
    @Transactional
    public void getAllNormasByVersaoContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao contains DEFAULT_VERSAO
        defaultNormaShouldBeFound("versao.contains=" + DEFAULT_VERSAO);

        // Get all the normaList where versao contains UPDATED_VERSAO
        defaultNormaShouldNotBeFound("versao.contains=" + UPDATED_VERSAO);
    }

    @Test
    @Transactional
    public void getAllNormasByVersaoNotContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where versao does not contain DEFAULT_VERSAO
        defaultNormaShouldNotBeFound("versao.doesNotContain=" + DEFAULT_VERSAO);

        // Get all the normaList where versao does not contain UPDATED_VERSAO
        defaultNormaShouldBeFound("versao.doesNotContain=" + UPDATED_VERSAO);
    }


    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao equals to DEFAULT_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.equals=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao equals to UPDATED_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.equals=" + UPDATED_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao not equals to DEFAULT_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.notEquals=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao not equals to UPDATED_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.notEquals=" + UPDATED_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao in DEFAULT_NUMERO_EDICAO or UPDATED_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.in=" + DEFAULT_NUMERO_EDICAO + "," + UPDATED_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao equals to UPDATED_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.in=" + UPDATED_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao is not null
        defaultNormaShouldBeFound("numeroEdicao.specified=true");

        // Get all the normaList where numeroEdicao is null
        defaultNormaShouldNotBeFound("numeroEdicao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao is greater than or equal to DEFAULT_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.greaterThanOrEqual=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao is greater than or equal to UPDATED_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.greaterThanOrEqual=" + UPDATED_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao is less than or equal to DEFAULT_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.lessThanOrEqual=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao is less than or equal to SMALLER_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.lessThanOrEqual=" + SMALLER_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsLessThanSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao is less than DEFAULT_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.lessThan=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao is less than UPDATED_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.lessThan=" + UPDATED_NUMERO_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByNumeroEdicaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where numeroEdicao is greater than DEFAULT_NUMERO_EDICAO
        defaultNormaShouldNotBeFound("numeroEdicao.greaterThan=" + DEFAULT_NUMERO_EDICAO);

        // Get all the normaList where numeroEdicao is greater than SMALLER_NUMERO_EDICAO
        defaultNormaShouldBeFound("numeroEdicao.greaterThan=" + SMALLER_NUMERO_EDICAO);
    }


    @Test
    @Transactional
    public void getAllNormasByDataEdicaoIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataEdicao equals to DEFAULT_DATA_EDICAO
        defaultNormaShouldBeFound("dataEdicao.equals=" + DEFAULT_DATA_EDICAO);

        // Get all the normaList where dataEdicao equals to UPDATED_DATA_EDICAO
        defaultNormaShouldNotBeFound("dataEdicao.equals=" + UPDATED_DATA_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByDataEdicaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataEdicao not equals to DEFAULT_DATA_EDICAO
        defaultNormaShouldNotBeFound("dataEdicao.notEquals=" + DEFAULT_DATA_EDICAO);

        // Get all the normaList where dataEdicao not equals to UPDATED_DATA_EDICAO
        defaultNormaShouldBeFound("dataEdicao.notEquals=" + UPDATED_DATA_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByDataEdicaoIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataEdicao in DEFAULT_DATA_EDICAO or UPDATED_DATA_EDICAO
        defaultNormaShouldBeFound("dataEdicao.in=" + DEFAULT_DATA_EDICAO + "," + UPDATED_DATA_EDICAO);

        // Get all the normaList where dataEdicao equals to UPDATED_DATA_EDICAO
        defaultNormaShouldNotBeFound("dataEdicao.in=" + UPDATED_DATA_EDICAO);
    }

    @Test
    @Transactional
    public void getAllNormasByDataEdicaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataEdicao is not null
        defaultNormaShouldBeFound("dataEdicao.specified=true");

        // Get all the normaList where dataEdicao is null
        defaultNormaShouldNotBeFound("dataEdicao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByDataInicioValidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataInicioValidade equals to DEFAULT_DATA_INICIO_VALIDADE
        defaultNormaShouldBeFound("dataInicioValidade.equals=" + DEFAULT_DATA_INICIO_VALIDADE);

        // Get all the normaList where dataInicioValidade equals to UPDATED_DATA_INICIO_VALIDADE
        defaultNormaShouldNotBeFound("dataInicioValidade.equals=" + UPDATED_DATA_INICIO_VALIDADE);
    }

    @Test
    @Transactional
    public void getAllNormasByDataInicioValidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataInicioValidade not equals to DEFAULT_DATA_INICIO_VALIDADE
        defaultNormaShouldNotBeFound("dataInicioValidade.notEquals=" + DEFAULT_DATA_INICIO_VALIDADE);

        // Get all the normaList where dataInicioValidade not equals to UPDATED_DATA_INICIO_VALIDADE
        defaultNormaShouldBeFound("dataInicioValidade.notEquals=" + UPDATED_DATA_INICIO_VALIDADE);
    }

    @Test
    @Transactional
    public void getAllNormasByDataInicioValidadeIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataInicioValidade in DEFAULT_DATA_INICIO_VALIDADE or UPDATED_DATA_INICIO_VALIDADE
        defaultNormaShouldBeFound("dataInicioValidade.in=" + DEFAULT_DATA_INICIO_VALIDADE + "," + UPDATED_DATA_INICIO_VALIDADE);

        // Get all the normaList where dataInicioValidade equals to UPDATED_DATA_INICIO_VALIDADE
        defaultNormaShouldNotBeFound("dataInicioValidade.in=" + UPDATED_DATA_INICIO_VALIDADE);
    }

    @Test
    @Transactional
    public void getAllNormasByDataInicioValidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where dataInicioValidade is not null
        defaultNormaShouldBeFound("dataInicioValidade.specified=true");

        // Get all the normaList where dataInicioValidade is null
        defaultNormaShouldNotBeFound("dataInicioValidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where categoria equals to DEFAULT_CATEGORIA
        defaultNormaShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the normaList where categoria equals to UPDATED_CATEGORIA
        defaultNormaShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllNormasByCategoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where categoria not equals to DEFAULT_CATEGORIA
        defaultNormaShouldNotBeFound("categoria.notEquals=" + DEFAULT_CATEGORIA);

        // Get all the normaList where categoria not equals to UPDATED_CATEGORIA
        defaultNormaShouldBeFound("categoria.notEquals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllNormasByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultNormaShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the normaList where categoria equals to UPDATED_CATEGORIA
        defaultNormaShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllNormasByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where categoria is not null
        defaultNormaShouldBeFound("categoria.specified=true");

        // Get all the normaList where categoria is null
        defaultNormaShouldNotBeFound("categoria.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByUrlDownloadIsEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload equals to DEFAULT_URL_DOWNLOAD
        defaultNormaShouldBeFound("urlDownload.equals=" + DEFAULT_URL_DOWNLOAD);

        // Get all the normaList where urlDownload equals to UPDATED_URL_DOWNLOAD
        defaultNormaShouldNotBeFound("urlDownload.equals=" + UPDATED_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void getAllNormasByUrlDownloadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload not equals to DEFAULT_URL_DOWNLOAD
        defaultNormaShouldNotBeFound("urlDownload.notEquals=" + DEFAULT_URL_DOWNLOAD);

        // Get all the normaList where urlDownload not equals to UPDATED_URL_DOWNLOAD
        defaultNormaShouldBeFound("urlDownload.notEquals=" + UPDATED_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void getAllNormasByUrlDownloadIsInShouldWork() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload in DEFAULT_URL_DOWNLOAD or UPDATED_URL_DOWNLOAD
        defaultNormaShouldBeFound("urlDownload.in=" + DEFAULT_URL_DOWNLOAD + "," + UPDATED_URL_DOWNLOAD);

        // Get all the normaList where urlDownload equals to UPDATED_URL_DOWNLOAD
        defaultNormaShouldNotBeFound("urlDownload.in=" + UPDATED_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void getAllNormasByUrlDownloadIsNullOrNotNull() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload is not null
        defaultNormaShouldBeFound("urlDownload.specified=true");

        // Get all the normaList where urlDownload is null
        defaultNormaShouldNotBeFound("urlDownload.specified=false");
    }
                @Test
    @Transactional
    public void getAllNormasByUrlDownloadContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload contains DEFAULT_URL_DOWNLOAD
        defaultNormaShouldBeFound("urlDownload.contains=" + DEFAULT_URL_DOWNLOAD);

        // Get all the normaList where urlDownload contains UPDATED_URL_DOWNLOAD
        defaultNormaShouldNotBeFound("urlDownload.contains=" + UPDATED_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void getAllNormasByUrlDownloadNotContainsSomething() throws Exception {
        // Initialize the database
        normaRepository.saveAndFlush(norma);

        // Get all the normaList where urlDownload does not contain DEFAULT_URL_DOWNLOAD
        defaultNormaShouldNotBeFound("urlDownload.doesNotContain=" + DEFAULT_URL_DOWNLOAD);

        // Get all the normaList where urlDownload does not contain UPDATED_URL_DOWNLOAD
        defaultNormaShouldBeFound("urlDownload.doesNotContain=" + UPDATED_URL_DOWNLOAD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNormaShouldBeFound(String filter) throws Exception {
        restNormaMockMvc.perform(get("/api/normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(norma.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgao").value(hasItem(DEFAULT_ORGAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO)))
            .andExpect(jsonPath("$.[*].numeroEdicao").value(hasItem(DEFAULT_NUMERO_EDICAO)))
            .andExpect(jsonPath("$.[*].dataEdicao").value(hasItem(DEFAULT_DATA_EDICAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicioValidade").value(hasItem(DEFAULT_DATA_INICIO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].urlDownload").value(hasItem(DEFAULT_URL_DOWNLOAD)));

        // Check, that the count call also returns 1
        restNormaMockMvc.perform(get("/api/normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNormaShouldNotBeFound(String filter) throws Exception {
        restNormaMockMvc.perform(get("/api/normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNormaMockMvc.perform(get("/api/normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNorma() throws Exception {
        // Get the norma
        restNormaMockMvc.perform(get("/api/normas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNorma() throws Exception {
        // Initialize the database
        normaService.save(norma);

        int databaseSizeBeforeUpdate = normaRepository.findAll().size();

        // Update the norma
        Norma updatedNorma = normaRepository.findById(norma.getId()).get();
        // Disconnect from session so that the updates on updatedNorma are not directly saved in db
        em.detach(updatedNorma);
        updatedNorma
            .orgao(UPDATED_ORGAO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .versao(UPDATED_VERSAO)
            .numeroEdicao(UPDATED_NUMERO_EDICAO)
            .dataEdicao(UPDATED_DATA_EDICAO)
            .dataInicioValidade(UPDATED_DATA_INICIO_VALIDADE)
            .categoria(UPDATED_CATEGORIA)
            .urlDownload(UPDATED_URL_DOWNLOAD);

        restNormaMockMvc.perform(put("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNorma)))
            .andExpect(status().isOk());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeUpdate);
        Norma testNorma = normaList.get(normaList.size() - 1);
        assertThat(testNorma.getOrgao()).isEqualTo(UPDATED_ORGAO);
        assertThat(testNorma.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNorma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNorma.getVersao()).isEqualTo(UPDATED_VERSAO);
        assertThat(testNorma.getNumeroEdicao()).isEqualTo(UPDATED_NUMERO_EDICAO);
        assertThat(testNorma.getDataEdicao()).isEqualTo(UPDATED_DATA_EDICAO);
        assertThat(testNorma.getDataInicioValidade()).isEqualTo(UPDATED_DATA_INICIO_VALIDADE);
        assertThat(testNorma.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testNorma.getUrlDownload()).isEqualTo(UPDATED_URL_DOWNLOAD);
    }

    @Test
    @Transactional
    public void updateNonExistingNorma() throws Exception {
        int databaseSizeBeforeUpdate = normaRepository.findAll().size();

        // Create the Norma

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormaMockMvc.perform(put("/api/normas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(norma)))
            .andExpect(status().isBadRequest());

        // Validate the Norma in the database
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNorma() throws Exception {
        // Initialize the database
        normaService.save(norma);

        int databaseSizeBeforeDelete = normaRepository.findAll().size();

        // Delete the norma
        restNormaMockMvc.perform(delete("/api/normas/{id}", norma.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Norma> normaList = normaRepository.findAll();
        assertThat(normaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
