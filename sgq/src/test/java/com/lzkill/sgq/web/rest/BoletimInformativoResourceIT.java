package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.domain.PublicoAlvo;
import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.BoletimInformativoRepository;
import com.lzkill.sgq.service.BoletimInformativoService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.BoletimInformativoCriteria;
import com.lzkill.sgq.service.BoletimInformativoQueryService;

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

import com.lzkill.sgq.domain.enumeration.StatusPublicacao;
/**
 * Integration tests for the {@link BoletimInformativoResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class BoletimInformativoResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_PUBLICACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PUBLICACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusPublicacao DEFAULT_STATUS = StatusPublicacao.RASCUNHO;
    private static final StatusPublicacao UPDATED_STATUS = StatusPublicacao.APROVADO;

    @Autowired
    private BoletimInformativoRepository boletimInformativoRepository;

    @Mock
    private BoletimInformativoRepository boletimInformativoRepositoryMock;

    @Mock
    private BoletimInformativoService boletimInformativoServiceMock;

    @Autowired
    private BoletimInformativoService boletimInformativoService;

    @Autowired
    private BoletimInformativoQueryService boletimInformativoQueryService;

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

    private MockMvc restBoletimInformativoMockMvc;

    private BoletimInformativo boletimInformativo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoletimInformativoResource boletimInformativoResource = new BoletimInformativoResource(boletimInformativoService, boletimInformativoQueryService);
        this.restBoletimInformativoMockMvc = MockMvcBuilders.standaloneSetup(boletimInformativoResource)
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
    public static BoletimInformativo createEntity(EntityManager em) {
        BoletimInformativo boletimInformativo = new BoletimInformativo()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .status(DEFAULT_STATUS);
        // Add required entity
        PublicoAlvo publicoAlvo;
        if (TestUtil.findAll(em, PublicoAlvo.class).isEmpty()) {
            publicoAlvo = PublicoAlvoResourceIT.createEntity(em);
            em.persist(publicoAlvo);
            em.flush();
        } else {
            publicoAlvo = TestUtil.findAll(em, PublicoAlvo.class).get(0);
        }
        boletimInformativo.setPublicoAlvo(publicoAlvo);
        // Add required entity
        CategoriaPublicacao categoriaPublicacao;
        if (TestUtil.findAll(em, CategoriaPublicacao.class).isEmpty()) {
            categoriaPublicacao = CategoriaPublicacaoResourceIT.createEntity(em);
            em.persist(categoriaPublicacao);
            em.flush();
        } else {
            categoriaPublicacao = TestUtil.findAll(em, CategoriaPublicacao.class).get(0);
        }
        boletimInformativo.getCategorias().add(categoriaPublicacao);
        return boletimInformativo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimInformativo createUpdatedEntity(EntityManager em) {
        BoletimInformativo boletimInformativo = new BoletimInformativo()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);
        // Add required entity
        PublicoAlvo publicoAlvo;
        if (TestUtil.findAll(em, PublicoAlvo.class).isEmpty()) {
            publicoAlvo = PublicoAlvoResourceIT.createUpdatedEntity(em);
            em.persist(publicoAlvo);
            em.flush();
        } else {
            publicoAlvo = TestUtil.findAll(em, PublicoAlvo.class).get(0);
        }
        boletimInformativo.setPublicoAlvo(publicoAlvo);
        // Add required entity
        CategoriaPublicacao categoriaPublicacao;
        if (TestUtil.findAll(em, CategoriaPublicacao.class).isEmpty()) {
            categoriaPublicacao = CategoriaPublicacaoResourceIT.createUpdatedEntity(em);
            em.persist(categoriaPublicacao);
            em.flush();
        } else {
            categoriaPublicacao = TestUtil.findAll(em, CategoriaPublicacao.class).get(0);
        }
        boletimInformativo.getCategorias().add(categoriaPublicacao);
        return boletimInformativo;
    }

    @BeforeEach
    public void initTest() {
        boletimInformativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimInformativo() throws Exception {
        int databaseSizeBeforeCreate = boletimInformativoRepository.findAll().size();

        // Create the BoletimInformativo
        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isCreated());

        // Validate the BoletimInformativo in the database
        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimInformativo testBoletimInformativo = boletimInformativoList.get(boletimInformativoList.size() - 1);
        assertThat(testBoletimInformativo.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testBoletimInformativo.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testBoletimInformativo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimInformativo.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testBoletimInformativo.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testBoletimInformativo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBoletimInformativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimInformativoRepository.findAll().size();

        // Create the BoletimInformativo with an existing ID
        boletimInformativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimInformativo in the database
        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimInformativoRepository.findAll().size();
        // set the field null
        boletimInformativo.setIdUsuarioRegistro(null);

        // Create the BoletimInformativo, which fails.

        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimInformativoRepository.findAll().size();
        // set the field null
        boletimInformativo.setTitulo(null);

        // Create the BoletimInformativo, which fails.

        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimInformativoRepository.findAll().size();
        // set the field null
        boletimInformativo.setDataRegistro(null);

        // Create the BoletimInformativo, which fails.

        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimInformativoRepository.findAll().size();
        // set the field null
        boletimInformativo.setStatus(null);

        // Create the BoletimInformativo, which fails.

        restBoletimInformativoMockMvc.perform(post("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativos() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimInformativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBoletimInformativosWithEagerRelationshipsIsEnabled() throws Exception {
        BoletimInformativoResource boletimInformativoResource = new BoletimInformativoResource(boletimInformativoServiceMock, boletimInformativoQueryService);
        when(boletimInformativoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBoletimInformativoMockMvc = MockMvcBuilders.standaloneSetup(boletimInformativoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos?eagerload=true"))
        .andExpect(status().isOk());

        verify(boletimInformativoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBoletimInformativosWithEagerRelationshipsIsNotEnabled() throws Exception {
        BoletimInformativoResource boletimInformativoResource = new BoletimInformativoResource(boletimInformativoServiceMock, boletimInformativoQueryService);
            when(boletimInformativoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBoletimInformativoMockMvc = MockMvcBuilders.standaloneSetup(boletimInformativoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos?eagerload=true"))
        .andExpect(status().isOk());

            verify(boletimInformativoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBoletimInformativo() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get the boletimInformativo
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos/{id}", boletimInformativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boletimInformativo.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getBoletimInformativosByIdFiltering() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        Long id = boletimInformativo.getId();

        defaultBoletimInformativoShouldBeFound("id.equals=" + id);
        defaultBoletimInformativoShouldNotBeFound("id.notEquals=" + id);

        defaultBoletimInformativoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBoletimInformativoShouldNotBeFound("id.greaterThan=" + id);

        defaultBoletimInformativoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBoletimInformativoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro is not null
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the boletimInformativoList where idUsuarioRegistro is null
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the boletimInformativoList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultBoletimInformativoShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllBoletimInformativosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo equals to DEFAULT_TITULO
        defaultBoletimInformativoShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the boletimInformativoList where titulo equals to UPDATED_TITULO
        defaultBoletimInformativoShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo not equals to DEFAULT_TITULO
        defaultBoletimInformativoShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the boletimInformativoList where titulo not equals to UPDATED_TITULO
        defaultBoletimInformativoShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultBoletimInformativoShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the boletimInformativoList where titulo equals to UPDATED_TITULO
        defaultBoletimInformativoShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo is not null
        defaultBoletimInformativoShouldBeFound("titulo.specified=true");

        // Get all the boletimInformativoList where titulo is null
        defaultBoletimInformativoShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimInformativosByTituloContainsSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo contains DEFAULT_TITULO
        defaultBoletimInformativoShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the boletimInformativoList where titulo contains UPDATED_TITULO
        defaultBoletimInformativoShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where titulo does not contain DEFAULT_TITULO
        defaultBoletimInformativoShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the boletimInformativoList where titulo does not contain UPDATED_TITULO
        defaultBoletimInformativoShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllBoletimInformativosByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultBoletimInformativoShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the boletimInformativoList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the boletimInformativoList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultBoletimInformativoShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultBoletimInformativoShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the boletimInformativoList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultBoletimInformativoShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataRegistro is not null
        defaultBoletimInformativoShouldBeFound("dataRegistro.specified=true");

        // Get all the boletimInformativoList where dataRegistro is null
        defaultBoletimInformativoShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataPublicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataPublicacao equals to DEFAULT_DATA_PUBLICACAO
        defaultBoletimInformativoShouldBeFound("dataPublicacao.equals=" + DEFAULT_DATA_PUBLICACAO);

        // Get all the boletimInformativoList where dataPublicacao equals to UPDATED_DATA_PUBLICACAO
        defaultBoletimInformativoShouldNotBeFound("dataPublicacao.equals=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataPublicacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataPublicacao not equals to DEFAULT_DATA_PUBLICACAO
        defaultBoletimInformativoShouldNotBeFound("dataPublicacao.notEquals=" + DEFAULT_DATA_PUBLICACAO);

        // Get all the boletimInformativoList where dataPublicacao not equals to UPDATED_DATA_PUBLICACAO
        defaultBoletimInformativoShouldBeFound("dataPublicacao.notEquals=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataPublicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataPublicacao in DEFAULT_DATA_PUBLICACAO or UPDATED_DATA_PUBLICACAO
        defaultBoletimInformativoShouldBeFound("dataPublicacao.in=" + DEFAULT_DATA_PUBLICACAO + "," + UPDATED_DATA_PUBLICACAO);

        // Get all the boletimInformativoList where dataPublicacao equals to UPDATED_DATA_PUBLICACAO
        defaultBoletimInformativoShouldNotBeFound("dataPublicacao.in=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByDataPublicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where dataPublicacao is not null
        defaultBoletimInformativoShouldBeFound("dataPublicacao.specified=true");

        // Get all the boletimInformativoList where dataPublicacao is null
        defaultBoletimInformativoShouldNotBeFound("dataPublicacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where status equals to DEFAULT_STATUS
        defaultBoletimInformativoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the boletimInformativoList where status equals to UPDATED_STATUS
        defaultBoletimInformativoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where status not equals to DEFAULT_STATUS
        defaultBoletimInformativoShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the boletimInformativoList where status not equals to UPDATED_STATUS
        defaultBoletimInformativoShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBoletimInformativoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the boletimInformativoList where status equals to UPDATED_STATUS
        defaultBoletimInformativoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);

        // Get all the boletimInformativoList where status is not null
        defaultBoletimInformativoShouldBeFound("status.specified=true");

        // Get all the boletimInformativoList where status is null
        defaultBoletimInformativoShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimInformativosByPublicoAlvoIsEqualToSomething() throws Exception {
        // Get already existing entity
        PublicoAlvo publicoAlvo = boletimInformativo.getPublicoAlvo();
        boletimInformativoRepository.saveAndFlush(boletimInformativo);
        Long publicoAlvoId = publicoAlvo.getId();

        // Get all the boletimInformativoList where publicoAlvo equals to publicoAlvoId
        defaultBoletimInformativoShouldBeFound("publicoAlvoId.equals=" + publicoAlvoId);

        // Get all the boletimInformativoList where publicoAlvo equals to publicoAlvoId + 1
        defaultBoletimInformativoShouldNotBeFound("publicoAlvoId.equals=" + (publicoAlvoId + 1));
    }

/*
    @Test
    @Transactional
    public void getAllBoletimInformativosByCategoriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CategoriaPublicacao categoria = boletimInformativo.getCategoria();
        boletimInformativoRepository.saveAndFlush(boletimInformativo);
        Long categoriaId = categoria.getId();

        // Get all the boletimInformativoList where categoria equals to categoriaId
        defaultBoletimInformativoShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the boletimInformativoList where categoria equals to categoriaId + 1
        defaultBoletimInformativoShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }
*/

    @Test
    @Transactional
    public void getAllBoletimInformativosByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimInformativoRepository.saveAndFlush(boletimInformativo);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        boletimInformativo.addAnexo(anexo);
        boletimInformativoRepository.saveAndFlush(boletimInformativo);
        Long anexoId = anexo.getId();

        // Get all the boletimInformativoList where anexo equals to anexoId
        defaultBoletimInformativoShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the boletimInformativoList where anexo equals to anexoId + 1
        defaultBoletimInformativoShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBoletimInformativoShouldBeFound(String filter) throws Exception {
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimInformativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBoletimInformativoShouldNotBeFound(String filter) throws Exception {
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBoletimInformativo() throws Exception {
        // Get the boletimInformativo
        restBoletimInformativoMockMvc.perform(get("/api/boletim-informativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimInformativo() throws Exception {
        // Initialize the database
        boletimInformativoService.save(boletimInformativo);

        int databaseSizeBeforeUpdate = boletimInformativoRepository.findAll().size();

        // Update the boletimInformativo
        BoletimInformativo updatedBoletimInformativo = boletimInformativoRepository.findById(boletimInformativo.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimInformativo are not directly saved in db
        em.detach(updatedBoletimInformativo);
        updatedBoletimInformativo
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);

        restBoletimInformativoMockMvc.perform(put("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoletimInformativo)))
            .andExpect(status().isOk());

        // Validate the BoletimInformativo in the database
        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeUpdate);
        BoletimInformativo testBoletimInformativo = boletimInformativoList.get(boletimInformativoList.size() - 1);
        assertThat(testBoletimInformativo.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testBoletimInformativo.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testBoletimInformativo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimInformativo.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testBoletimInformativo.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testBoletimInformativo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimInformativo() throws Exception {
        int databaseSizeBeforeUpdate = boletimInformativoRepository.findAll().size();

        // Create the BoletimInformativo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimInformativoMockMvc.perform(put("/api/boletim-informativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boletimInformativo)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimInformativo in the database
        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimInformativo() throws Exception {
        // Initialize the database
        boletimInformativoService.save(boletimInformativo);

        int databaseSizeBeforeDelete = boletimInformativoRepository.findAll().size();

        // Delete the boletimInformativo
        restBoletimInformativoMockMvc.perform(delete("/api/boletim-informativos/{id}", boletimInformativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimInformativo> boletimInformativoList = boletimInformativoRepository.findAll();
        assertThat(boletimInformativoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
