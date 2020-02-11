package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Produto;
import com.lzkill.sgq.domain.ResultadoAuditoria;
import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.repository.ProdutoNaoConformeRepository;
import com.lzkill.sgq.service.ProdutoNaoConformeService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ProdutoNaoConformeCriteria;
import com.lzkill.sgq.service.ProdutoNaoConformeQueryService;

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
 * Integration tests for the {@link ProdutoNaoConformeResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ProdutoNaoConformeResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final Integer DEFAULT_ID_USUARIO_RESPONSAVEL = 1;
    private static final Integer UPDATED_ID_USUARIO_RESPONSAVEL = 2;
    private static final Integer SMALLER_ID_USUARIO_RESPONSAVEL = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROCEDENTE = false;
    private static final Boolean UPDATED_PROCEDENTE = true;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ANALISE_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_ANALISE_FINAL = "BBBBBBBBBB";

    private static final StatusSGQ DEFAULT_STATUS_SGQ = StatusSGQ.REGISTRADO;
    private static final StatusSGQ UPDATED_STATUS_SGQ = StatusSGQ.PENDENTE;

    @Autowired
    private ProdutoNaoConformeRepository produtoNaoConformeRepository;

    @Autowired
    private ProdutoNaoConformeService produtoNaoConformeService;

    @Autowired
    private ProdutoNaoConformeQueryService produtoNaoConformeQueryService;

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

    private MockMvc restProdutoNaoConformeMockMvc;

    private ProdutoNaoConforme produtoNaoConforme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoNaoConformeResource produtoNaoConformeResource = new ProdutoNaoConformeResource(produtoNaoConformeService, produtoNaoConformeQueryService);
        this.restProdutoNaoConformeMockMvc = MockMvcBuilders.standaloneSetup(produtoNaoConformeResource)
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
    public static ProdutoNaoConforme createEntity(EntityManager em) {
        ProdutoNaoConforme produtoNaoConforme = new ProdutoNaoConforme()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(DEFAULT_ID_USUARIO_RESPONSAVEL)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .procedente(DEFAULT_PROCEDENTE)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
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
        produtoNaoConforme.setAcao(acaoSGQ);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        produtoNaoConforme.setProduto(produto);
        return produtoNaoConforme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdutoNaoConforme createUpdatedEntity(EntityManager em) {
        ProdutoNaoConforme produtoNaoConforme = new ProdutoNaoConforme()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .procedente(UPDATED_PROCEDENTE)
            .dataRegistro(UPDATED_DATA_REGISTRO)
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
        produtoNaoConforme.setAcao(acaoSGQ);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        produtoNaoConforme.setProduto(produto);
        return produtoNaoConforme;
    }

    @BeforeEach
    public void initTest() {
        produtoNaoConforme = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdutoNaoConforme() throws Exception {
        int databaseSizeBeforeCreate = produtoNaoConformeRepository.findAll().size();

        // Create the ProdutoNaoConforme
        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isCreated());

        // Validate the ProdutoNaoConforme in the database
        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeCreate + 1);
        ProdutoNaoConforme testProdutoNaoConforme = produtoNaoConformeList.get(produtoNaoConformeList.size() - 1);
        assertThat(testProdutoNaoConforme.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testProdutoNaoConforme.getIdUsuarioResponsavel()).isEqualTo(DEFAULT_ID_USUARIO_RESPONSAVEL);
        assertThat(testProdutoNaoConforme.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProdutoNaoConforme.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProdutoNaoConforme.isProcedente()).isEqualTo(DEFAULT_PROCEDENTE);
        assertThat(testProdutoNaoConforme.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testProdutoNaoConforme.getAnaliseFinal()).isEqualTo(DEFAULT_ANALISE_FINAL);
        assertThat(testProdutoNaoConforme.getStatusSGQ()).isEqualTo(DEFAULT_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void createProdutoNaoConformeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoNaoConformeRepository.findAll().size();

        // Create the ProdutoNaoConforme with an existing ID
        produtoNaoConforme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        // Validate the ProdutoNaoConforme in the database
        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setIdUsuarioRegistro(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdUsuarioResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setIdUsuarioResponsavel(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setTitulo(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcedenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setProcedente(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setDataRegistro(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusSGQIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoNaoConformeRepository.findAll().size();
        // set the field null
        produtoNaoConforme.setStatusSGQ(null);

        // Create the ProdutoNaoConforme, which fails.

        restProdutoNaoConformeMockMvc.perform(post("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformes() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoNaoConforme.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].procedente").value(hasItem(DEFAULT_PROCEDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].analiseFinal").value(hasItem(DEFAULT_ANALISE_FINAL.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));
    }
    
    @Test
    @Transactional
    public void getProdutoNaoConforme() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get the produtoNaoConforme
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes/{id}", produtoNaoConforme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produtoNaoConforme.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.idUsuarioResponsavel").value(DEFAULT_ID_USUARIO_RESPONSAVEL))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.procedente").value(DEFAULT_PROCEDENTE.booleanValue()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.analiseFinal").value(DEFAULT_ANALISE_FINAL.toString()))
            .andExpect(jsonPath("$.statusSGQ").value(DEFAULT_STATUS_SGQ.toString()));
    }


    @Test
    @Transactional
    public void getProdutoNaoConformesByIdFiltering() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        Long id = produtoNaoConforme.getId();

        defaultProdutoNaoConformeShouldBeFound("id.equals=" + id);
        defaultProdutoNaoConformeShouldNotBeFound("id.notEquals=" + id);

        defaultProdutoNaoConformeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProdutoNaoConformeShouldNotBeFound("id.greaterThan=" + id);

        defaultProdutoNaoConformeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProdutoNaoConformeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is not null
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the produtoNaoConformeList where idUsuarioRegistro is null
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the produtoNaoConformeList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.equals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.equals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel not equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.notEquals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel not equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.notEquals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel in DEFAULT_ID_USUARIO_RESPONSAVEL or UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.in=" + DEFAULT_ID_USUARIO_RESPONSAVEL + "," + UPDATED_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.in=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is not null
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.specified=true");

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is null
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is greater than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is greater than or equal to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is less than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.lessThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is less than or equal to SMALLER_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.lessThanOrEqual=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is less than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.lessThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is less than UPDATED_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.lessThan=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByIdUsuarioResponsavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is greater than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldNotBeFound("idUsuarioResponsavel.greaterThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the produtoNaoConformeList where idUsuarioResponsavel is greater than SMALLER_ID_USUARIO_RESPONSAVEL
        defaultProdutoNaoConformeShouldBeFound("idUsuarioResponsavel.greaterThan=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo equals to DEFAULT_TITULO
        defaultProdutoNaoConformeShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the produtoNaoConformeList where titulo equals to UPDATED_TITULO
        defaultProdutoNaoConformeShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo not equals to DEFAULT_TITULO
        defaultProdutoNaoConformeShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the produtoNaoConformeList where titulo not equals to UPDATED_TITULO
        defaultProdutoNaoConformeShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultProdutoNaoConformeShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the produtoNaoConformeList where titulo equals to UPDATED_TITULO
        defaultProdutoNaoConformeShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo is not null
        defaultProdutoNaoConformeShouldBeFound("titulo.specified=true");

        // Get all the produtoNaoConformeList where titulo is null
        defaultProdutoNaoConformeShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloContainsSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo contains DEFAULT_TITULO
        defaultProdutoNaoConformeShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the produtoNaoConformeList where titulo contains UPDATED_TITULO
        defaultProdutoNaoConformeShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where titulo does not contain DEFAULT_TITULO
        defaultProdutoNaoConformeShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the produtoNaoConformeList where titulo does not contain UPDATED_TITULO
        defaultProdutoNaoConformeShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByProcedenteIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where procedente equals to DEFAULT_PROCEDENTE
        defaultProdutoNaoConformeShouldBeFound("procedente.equals=" + DEFAULT_PROCEDENTE);

        // Get all the produtoNaoConformeList where procedente equals to UPDATED_PROCEDENTE
        defaultProdutoNaoConformeShouldNotBeFound("procedente.equals=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByProcedenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where procedente not equals to DEFAULT_PROCEDENTE
        defaultProdutoNaoConformeShouldNotBeFound("procedente.notEquals=" + DEFAULT_PROCEDENTE);

        // Get all the produtoNaoConformeList where procedente not equals to UPDATED_PROCEDENTE
        defaultProdutoNaoConformeShouldBeFound("procedente.notEquals=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByProcedenteIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where procedente in DEFAULT_PROCEDENTE or UPDATED_PROCEDENTE
        defaultProdutoNaoConformeShouldBeFound("procedente.in=" + DEFAULT_PROCEDENTE + "," + UPDATED_PROCEDENTE);

        // Get all the produtoNaoConformeList where procedente equals to UPDATED_PROCEDENTE
        defaultProdutoNaoConformeShouldNotBeFound("procedente.in=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByProcedenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where procedente is not null
        defaultProdutoNaoConformeShouldBeFound("procedente.specified=true");

        // Get all the produtoNaoConformeList where procedente is null
        defaultProdutoNaoConformeShouldNotBeFound("procedente.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the produtoNaoConformeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the produtoNaoConformeList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultProdutoNaoConformeShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the produtoNaoConformeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultProdutoNaoConformeShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where dataRegistro is not null
        defaultProdutoNaoConformeShouldBeFound("dataRegistro.specified=true");

        // Get all the produtoNaoConformeList where dataRegistro is null
        defaultProdutoNaoConformeShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByStatusSGQIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where statusSGQ equals to DEFAULT_STATUS_SGQ
        defaultProdutoNaoConformeShouldBeFound("statusSGQ.equals=" + DEFAULT_STATUS_SGQ);

        // Get all the produtoNaoConformeList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultProdutoNaoConformeShouldNotBeFound("statusSGQ.equals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByStatusSGQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where statusSGQ not equals to DEFAULT_STATUS_SGQ
        defaultProdutoNaoConformeShouldNotBeFound("statusSGQ.notEquals=" + DEFAULT_STATUS_SGQ);

        // Get all the produtoNaoConformeList where statusSGQ not equals to UPDATED_STATUS_SGQ
        defaultProdutoNaoConformeShouldBeFound("statusSGQ.notEquals=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByStatusSGQIsInShouldWork() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where statusSGQ in DEFAULT_STATUS_SGQ or UPDATED_STATUS_SGQ
        defaultProdutoNaoConformeShouldBeFound("statusSGQ.in=" + DEFAULT_STATUS_SGQ + "," + UPDATED_STATUS_SGQ);

        // Get all the produtoNaoConformeList where statusSGQ equals to UPDATED_STATUS_SGQ
        defaultProdutoNaoConformeShouldNotBeFound("statusSGQ.in=" + UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByStatusSGQIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);

        // Get all the produtoNaoConformeList where statusSGQ is not null
        defaultProdutoNaoConformeShouldBeFound("statusSGQ.specified=true");

        // Get all the produtoNaoConformeList where statusSGQ is null
        defaultProdutoNaoConformeShouldNotBeFound("statusSGQ.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoNaoConformesByAcaoIsEqualToSomething() throws Exception {
        // Get already existing entity
        AcaoSGQ acao = produtoNaoConforme.getAcao();
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long acaoId = acao.getId();

        // Get all the produtoNaoConformeList where acao equals to acaoId
        defaultProdutoNaoConformeShouldBeFound("acaoId.equals=" + acaoId);

        // Get all the produtoNaoConformeList where acao equals to acaoId + 1
        defaultProdutoNaoConformeShouldNotBeFound("acaoId.equals=" + (acaoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        produtoNaoConforme.setNaoConformidade(naoConformidade);
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the produtoNaoConformeList where naoConformidade equals to naoConformidadeId
        defaultProdutoNaoConformeShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the produtoNaoConformeList where naoConformidade equals to naoConformidadeId + 1
        defaultProdutoNaoConformeShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        produtoNaoConforme.addAnexo(anexo);
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long anexoId = anexo.getId();

        // Get all the produtoNaoConformeList where anexo equals to anexoId
        defaultProdutoNaoConformeShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the produtoNaoConformeList where anexo equals to anexoId + 1
        defaultProdutoNaoConformeShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByProdutoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Produto produto = produtoNaoConforme.getProduto();
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long produtoId = produto.getId();

        // Get all the produtoNaoConformeList where produto equals to produtoId
        defaultProdutoNaoConformeShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the produtoNaoConformeList where produto equals to produtoId + 1
        defaultProdutoNaoConformeShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByResultadoAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        ResultadoAuditoria resultadoAuditoria = ResultadoAuditoriaResourceIT.createEntity(em);
        em.persist(resultadoAuditoria);
        em.flush();
        produtoNaoConforme.setResultadoAuditoria(resultadoAuditoria);
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long resultadoAuditoriaId = resultadoAuditoria.getId();

        // Get all the produtoNaoConformeList where resultadoAuditoria equals to resultadoAuditoriaId
        defaultProdutoNaoConformeShouldBeFound("resultadoAuditoriaId.equals=" + resultadoAuditoriaId);

        // Get all the produtoNaoConformeList where resultadoAuditoria equals to resultadoAuditoriaId + 1
        defaultProdutoNaoConformeShouldNotBeFound("resultadoAuditoriaId.equals=" + (resultadoAuditoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutoNaoConformesByResultadoItemChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        ResultadoItemChecklist resultadoItemChecklist = ResultadoItemChecklistResourceIT.createEntity(em);
        em.persist(resultadoItemChecklist);
        em.flush();
        produtoNaoConforme.setResultadoItemChecklist(resultadoItemChecklist);
        produtoNaoConformeRepository.saveAndFlush(produtoNaoConforme);
        Long resultadoItemChecklistId = resultadoItemChecklist.getId();

        // Get all the produtoNaoConformeList where resultadoItemChecklist equals to resultadoItemChecklistId
        defaultProdutoNaoConformeShouldBeFound("resultadoItemChecklistId.equals=" + resultadoItemChecklistId);

        // Get all the produtoNaoConformeList where resultadoItemChecklist equals to resultadoItemChecklistId + 1
        defaultProdutoNaoConformeShouldNotBeFound("resultadoItemChecklistId.equals=" + (resultadoItemChecklistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdutoNaoConformeShouldBeFound(String filter) throws Exception {
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoNaoConforme.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].procedente").value(hasItem(DEFAULT_PROCEDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].analiseFinal").value(hasItem(DEFAULT_ANALISE_FINAL.toString())))
            .andExpect(jsonPath("$.[*].statusSGQ").value(hasItem(DEFAULT_STATUS_SGQ.toString())));

        // Check, that the count call also returns 1
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdutoNaoConformeShouldNotBeFound(String filter) throws Exception {
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProdutoNaoConforme() throws Exception {
        // Get the produtoNaoConforme
        restProdutoNaoConformeMockMvc.perform(get("/api/produto-nao-conformes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdutoNaoConforme() throws Exception {
        // Initialize the database
        produtoNaoConformeService.save(produtoNaoConforme);

        int databaseSizeBeforeUpdate = produtoNaoConformeRepository.findAll().size();

        // Update the produtoNaoConforme
        ProdutoNaoConforme updatedProdutoNaoConforme = produtoNaoConformeRepository.findById(produtoNaoConforme.getId()).get();
        // Disconnect from session so that the updates on updatedProdutoNaoConforme are not directly saved in db
        em.detach(updatedProdutoNaoConforme);
        updatedProdutoNaoConforme
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .procedente(UPDATED_PROCEDENTE)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .analiseFinal(UPDATED_ANALISE_FINAL)
            .statusSGQ(UPDATED_STATUS_SGQ);

        restProdutoNaoConformeMockMvc.perform(put("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdutoNaoConforme)))
            .andExpect(status().isOk());

        // Validate the ProdutoNaoConforme in the database
        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeUpdate);
        ProdutoNaoConforme testProdutoNaoConforme = produtoNaoConformeList.get(produtoNaoConformeList.size() - 1);
        assertThat(testProdutoNaoConforme.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testProdutoNaoConforme.getIdUsuarioResponsavel()).isEqualTo(UPDATED_ID_USUARIO_RESPONSAVEL);
        assertThat(testProdutoNaoConforme.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProdutoNaoConforme.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProdutoNaoConforme.isProcedente()).isEqualTo(UPDATED_PROCEDENTE);
        assertThat(testProdutoNaoConforme.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testProdutoNaoConforme.getAnaliseFinal()).isEqualTo(UPDATED_ANALISE_FINAL);
        assertThat(testProdutoNaoConforme.getStatusSGQ()).isEqualTo(UPDATED_STATUS_SGQ);
    }

    @Test
    @Transactional
    public void updateNonExistingProdutoNaoConforme() throws Exception {
        int databaseSizeBeforeUpdate = produtoNaoConformeRepository.findAll().size();

        // Create the ProdutoNaoConforme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoNaoConformeMockMvc.perform(put("/api/produto-nao-conformes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoNaoConforme)))
            .andExpect(status().isBadRequest());

        // Validate the ProdutoNaoConforme in the database
        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProdutoNaoConforme() throws Exception {
        // Initialize the database
        produtoNaoConformeService.save(produtoNaoConforme);

        int databaseSizeBeforeDelete = produtoNaoConformeRepository.findAll().size();

        // Delete the produtoNaoConforme
        restProdutoNaoConformeMockMvc.perform(delete("/api/produto-nao-conformes/{id}", produtoNaoConforme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdutoNaoConforme> produtoNaoConformeList = produtoNaoConformeRepository.findAll();
        assertThat(produtoNaoConformeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
