package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Feed;
import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.repository.PublicacaoFeedRepository;
import com.lzkill.sgq.service.PublicacaoFeedService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.PublicacaoFeedCriteria;
import com.lzkill.sgq.service.PublicacaoFeedQueryService;

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
 * Integration tests for the {@link PublicacaoFeedResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class PublicacaoFeedResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR = "BBBBBBBBBB";

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_PUBLICACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PUBLICACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusPublicacao DEFAULT_STATUS = StatusPublicacao.RASCUNHO;
    private static final StatusPublicacao UPDATED_STATUS = StatusPublicacao.PUBLICADO;

    @Autowired
    private PublicacaoFeedRepository publicacaoFeedRepository;

    @Mock
    private PublicacaoFeedRepository publicacaoFeedRepositoryMock;

    @Mock
    private PublicacaoFeedService publicacaoFeedServiceMock;

    @Autowired
    private PublicacaoFeedService publicacaoFeedService;

    @Autowired
    private PublicacaoFeedQueryService publicacaoFeedQueryService;

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

    private MockMvc restPublicacaoFeedMockMvc;

    private PublicacaoFeed publicacaoFeed;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacaoFeedResource publicacaoFeedResource = new PublicacaoFeedResource(publicacaoFeedService, publicacaoFeedQueryService);
        this.restPublicacaoFeedMockMvc = MockMvcBuilders.standaloneSetup(publicacaoFeedResource)
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
    public static PublicacaoFeed createEntity(EntityManager em) {
        PublicacaoFeed publicacaoFeed = new PublicacaoFeed()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .titulo(DEFAULT_TITULO)
            .autor(DEFAULT_AUTOR)
            .uri(DEFAULT_URI)
            .link(DEFAULT_LINK)
            .conteudo(DEFAULT_CONTEUDO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .status(DEFAULT_STATUS);
        // Add required entity
        Feed feed;
        if (TestUtil.findAll(em, Feed.class).isEmpty()) {
            feed = FeedResourceIT.createEntity(em);
            em.persist(feed);
            em.flush();
        } else {
            feed = TestUtil.findAll(em, Feed.class).get(0);
        }
        publicacaoFeed.setFeed(feed);
        // Add required entity
        CategoriaPublicacao categoriaPublicacao;
        if (TestUtil.findAll(em, CategoriaPublicacao.class).isEmpty()) {
            categoriaPublicacao = CategoriaPublicacaoResourceIT.createEntity(em);
            em.persist(categoriaPublicacao);
            em.flush();
        } else {
            categoriaPublicacao = TestUtil.findAll(em, CategoriaPublicacao.class).get(0);
        }
        publicacaoFeed.getCategorias().add(categoriaPublicacao);
        return publicacaoFeed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicacaoFeed createUpdatedEntity(EntityManager em) {
        PublicacaoFeed publicacaoFeed = new PublicacaoFeed()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .autor(UPDATED_AUTOR)
            .uri(UPDATED_URI)
            .link(UPDATED_LINK)
            .conteudo(UPDATED_CONTEUDO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);
        // Add required entity
        Feed feed;
        if (TestUtil.findAll(em, Feed.class).isEmpty()) {
            feed = FeedResourceIT.createUpdatedEntity(em);
            em.persist(feed);
            em.flush();
        } else {
            feed = TestUtil.findAll(em, Feed.class).get(0);
        }
        publicacaoFeed.setFeed(feed);
        // Add required entity
        CategoriaPublicacao categoriaPublicacao;
        if (TestUtil.findAll(em, CategoriaPublicacao.class).isEmpty()) {
            categoriaPublicacao = CategoriaPublicacaoResourceIT.createUpdatedEntity(em);
            em.persist(categoriaPublicacao);
            em.flush();
        } else {
            categoriaPublicacao = TestUtil.findAll(em, CategoriaPublicacao.class).get(0);
        }
        publicacaoFeed.getCategorias().add(categoriaPublicacao);
        return publicacaoFeed;
    }

    @BeforeEach
    public void initTest() {
        publicacaoFeed = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacaoFeed() throws Exception {
        int databaseSizeBeforeCreate = publicacaoFeedRepository.findAll().size();

        // Create the PublicacaoFeed
        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isCreated());

        // Validate the PublicacaoFeed in the database
        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeCreate + 1);
        PublicacaoFeed testPublicacaoFeed = publicacaoFeedList.get(publicacaoFeedList.size() - 1);
        assertThat(testPublicacaoFeed.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testPublicacaoFeed.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublicacaoFeed.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testPublicacaoFeed.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testPublicacaoFeed.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testPublicacaoFeed.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testPublicacaoFeed.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testPublicacaoFeed.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testPublicacaoFeed.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPublicacaoFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacaoFeedRepository.findAll().size();

        // Create the PublicacaoFeed with an existing ID
        publicacaoFeed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        // Validate the PublicacaoFeed in the database
        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setIdUsuarioRegistro(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setTitulo(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setAutor(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setUri(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setDataRegistro(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataPublicacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setDataPublicacao(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacaoFeedRepository.findAll().size();
        // set the field null
        publicacaoFeed.setStatus(null);

        // Create the PublicacaoFeed, which fails.

        restPublicacaoFeedMockMvc.perform(post("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeeds() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacaoFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPublicacaoFeedsWithEagerRelationshipsIsEnabled() throws Exception {
        PublicacaoFeedResource publicacaoFeedResource = new PublicacaoFeedResource(publicacaoFeedServiceMock, publicacaoFeedQueryService);
        when(publicacaoFeedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPublicacaoFeedMockMvc = MockMvcBuilders.standaloneSetup(publicacaoFeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds?eagerload=true"))
        .andExpect(status().isOk());

        verify(publicacaoFeedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPublicacaoFeedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PublicacaoFeedResource publicacaoFeedResource = new PublicacaoFeedResource(publicacaoFeedServiceMock, publicacaoFeedQueryService);
            when(publicacaoFeedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPublicacaoFeedMockMvc = MockMvcBuilders.standaloneSetup(publicacaoFeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds?eagerload=true"))
        .andExpect(status().isOk());

            verify(publicacaoFeedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPublicacaoFeed() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get the publicacaoFeed
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds/{id}", publicacaoFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacaoFeed.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getPublicacaoFeedsByIdFiltering() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        Long id = publicacaoFeed.getId();

        defaultPublicacaoFeedShouldBeFound("id.equals=" + id);
        defaultPublicacaoFeedShouldNotBeFound("id.notEquals=" + id);

        defaultPublicacaoFeedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPublicacaoFeedShouldNotBeFound("id.greaterThan=" + id);

        defaultPublicacaoFeedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPublicacaoFeedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro is not null
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the publicacaoFeedList where idUsuarioRegistro is null
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the publicacaoFeedList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultPublicacaoFeedShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo equals to DEFAULT_TITULO
        defaultPublicacaoFeedShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the publicacaoFeedList where titulo equals to UPDATED_TITULO
        defaultPublicacaoFeedShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo not equals to DEFAULT_TITULO
        defaultPublicacaoFeedShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the publicacaoFeedList where titulo not equals to UPDATED_TITULO
        defaultPublicacaoFeedShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultPublicacaoFeedShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the publicacaoFeedList where titulo equals to UPDATED_TITULO
        defaultPublicacaoFeedShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo is not null
        defaultPublicacaoFeedShouldBeFound("titulo.specified=true");

        // Get all the publicacaoFeedList where titulo is null
        defaultPublicacaoFeedShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo contains DEFAULT_TITULO
        defaultPublicacaoFeedShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the publicacaoFeedList where titulo contains UPDATED_TITULO
        defaultPublicacaoFeedShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where titulo does not contain DEFAULT_TITULO
        defaultPublicacaoFeedShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the publicacaoFeedList where titulo does not contain UPDATED_TITULO
        defaultPublicacaoFeedShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor equals to DEFAULT_AUTOR
        defaultPublicacaoFeedShouldBeFound("autor.equals=" + DEFAULT_AUTOR);

        // Get all the publicacaoFeedList where autor equals to UPDATED_AUTOR
        defaultPublicacaoFeedShouldNotBeFound("autor.equals=" + UPDATED_AUTOR);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor not equals to DEFAULT_AUTOR
        defaultPublicacaoFeedShouldNotBeFound("autor.notEquals=" + DEFAULT_AUTOR);

        // Get all the publicacaoFeedList where autor not equals to UPDATED_AUTOR
        defaultPublicacaoFeedShouldBeFound("autor.notEquals=" + UPDATED_AUTOR);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor in DEFAULT_AUTOR or UPDATED_AUTOR
        defaultPublicacaoFeedShouldBeFound("autor.in=" + DEFAULT_AUTOR + "," + UPDATED_AUTOR);

        // Get all the publicacaoFeedList where autor equals to UPDATED_AUTOR
        defaultPublicacaoFeedShouldNotBeFound("autor.in=" + UPDATED_AUTOR);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor is not null
        defaultPublicacaoFeedShouldBeFound("autor.specified=true");

        // Get all the publicacaoFeedList where autor is null
        defaultPublicacaoFeedShouldNotBeFound("autor.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor contains DEFAULT_AUTOR
        defaultPublicacaoFeedShouldBeFound("autor.contains=" + DEFAULT_AUTOR);

        // Get all the publicacaoFeedList where autor contains UPDATED_AUTOR
        defaultPublicacaoFeedShouldNotBeFound("autor.contains=" + UPDATED_AUTOR);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAutorNotContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where autor does not contain DEFAULT_AUTOR
        defaultPublicacaoFeedShouldNotBeFound("autor.doesNotContain=" + DEFAULT_AUTOR);

        // Get all the publicacaoFeedList where autor does not contain UPDATED_AUTOR
        defaultPublicacaoFeedShouldBeFound("autor.doesNotContain=" + UPDATED_AUTOR);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri equals to DEFAULT_URI
        defaultPublicacaoFeedShouldBeFound("uri.equals=" + DEFAULT_URI);

        // Get all the publicacaoFeedList where uri equals to UPDATED_URI
        defaultPublicacaoFeedShouldNotBeFound("uri.equals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri not equals to DEFAULT_URI
        defaultPublicacaoFeedShouldNotBeFound("uri.notEquals=" + DEFAULT_URI);

        // Get all the publicacaoFeedList where uri not equals to UPDATED_URI
        defaultPublicacaoFeedShouldBeFound("uri.notEquals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri in DEFAULT_URI or UPDATED_URI
        defaultPublicacaoFeedShouldBeFound("uri.in=" + DEFAULT_URI + "," + UPDATED_URI);

        // Get all the publicacaoFeedList where uri equals to UPDATED_URI
        defaultPublicacaoFeedShouldNotBeFound("uri.in=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri is not null
        defaultPublicacaoFeedShouldBeFound("uri.specified=true");

        // Get all the publicacaoFeedList where uri is null
        defaultPublicacaoFeedShouldNotBeFound("uri.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri contains DEFAULT_URI
        defaultPublicacaoFeedShouldBeFound("uri.contains=" + DEFAULT_URI);

        // Get all the publicacaoFeedList where uri contains UPDATED_URI
        defaultPublicacaoFeedShouldNotBeFound("uri.contains=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByUriNotContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where uri does not contain DEFAULT_URI
        defaultPublicacaoFeedShouldNotBeFound("uri.doesNotContain=" + DEFAULT_URI);

        // Get all the publicacaoFeedList where uri does not contain UPDATED_URI
        defaultPublicacaoFeedShouldBeFound("uri.doesNotContain=" + UPDATED_URI);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link equals to DEFAULT_LINK
        defaultPublicacaoFeedShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the publicacaoFeedList where link equals to UPDATED_LINK
        defaultPublicacaoFeedShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link not equals to DEFAULT_LINK
        defaultPublicacaoFeedShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the publicacaoFeedList where link not equals to UPDATED_LINK
        defaultPublicacaoFeedShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link in DEFAULT_LINK or UPDATED_LINK
        defaultPublicacaoFeedShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the publicacaoFeedList where link equals to UPDATED_LINK
        defaultPublicacaoFeedShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link is not null
        defaultPublicacaoFeedShouldBeFound("link.specified=true");

        // Get all the publicacaoFeedList where link is null
        defaultPublicacaoFeedShouldNotBeFound("link.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link contains DEFAULT_LINK
        defaultPublicacaoFeedShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the publicacaoFeedList where link contains UPDATED_LINK
        defaultPublicacaoFeedShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where link does not contain DEFAULT_LINK
        defaultPublicacaoFeedShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the publicacaoFeedList where link does not contain UPDATED_LINK
        defaultPublicacaoFeedShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultPublicacaoFeedShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the publicacaoFeedList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the publicacaoFeedList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultPublicacaoFeedShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultPublicacaoFeedShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the publicacaoFeedList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultPublicacaoFeedShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataRegistro is not null
        defaultPublicacaoFeedShouldBeFound("dataRegistro.specified=true");

        // Get all the publicacaoFeedList where dataRegistro is null
        defaultPublicacaoFeedShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataPublicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataPublicacao equals to DEFAULT_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldBeFound("dataPublicacao.equals=" + DEFAULT_DATA_PUBLICACAO);

        // Get all the publicacaoFeedList where dataPublicacao equals to UPDATED_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldNotBeFound("dataPublicacao.equals=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataPublicacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataPublicacao not equals to DEFAULT_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldNotBeFound("dataPublicacao.notEquals=" + DEFAULT_DATA_PUBLICACAO);

        // Get all the publicacaoFeedList where dataPublicacao not equals to UPDATED_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldBeFound("dataPublicacao.notEquals=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataPublicacaoIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataPublicacao in DEFAULT_DATA_PUBLICACAO or UPDATED_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldBeFound("dataPublicacao.in=" + DEFAULT_DATA_PUBLICACAO + "," + UPDATED_DATA_PUBLICACAO);

        // Get all the publicacaoFeedList where dataPublicacao equals to UPDATED_DATA_PUBLICACAO
        defaultPublicacaoFeedShouldNotBeFound("dataPublicacao.in=" + UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByDataPublicacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where dataPublicacao is not null
        defaultPublicacaoFeedShouldBeFound("dataPublicacao.specified=true");

        // Get all the publicacaoFeedList where dataPublicacao is null
        defaultPublicacaoFeedShouldNotBeFound("dataPublicacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where status equals to DEFAULT_STATUS
        defaultPublicacaoFeedShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the publicacaoFeedList where status equals to UPDATED_STATUS
        defaultPublicacaoFeedShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where status not equals to DEFAULT_STATUS
        defaultPublicacaoFeedShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the publicacaoFeedList where status not equals to UPDATED_STATUS
        defaultPublicacaoFeedShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPublicacaoFeedShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the publicacaoFeedList where status equals to UPDATED_STATUS
        defaultPublicacaoFeedShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);

        // Get all the publicacaoFeedList where status is not null
        defaultPublicacaoFeedShouldBeFound("status.specified=true");

        // Get all the publicacaoFeedList where status is null
        defaultPublicacaoFeedShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicacaoFeedsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        publicacaoFeed.addAnexo(anexo);
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);
        Long anexoId = anexo.getId();

        // Get all the publicacaoFeedList where anexo equals to anexoId
        defaultPublicacaoFeedShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the publicacaoFeedList where anexo equals to anexoId + 1
        defaultPublicacaoFeedShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllPublicacaoFeedsByFeedIsEqualToSomething() throws Exception {
        // Get already existing entity
        Feed feed = publicacaoFeed.getFeed();
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);
        Long feedId = feed.getId();

        // Get all the publicacaoFeedList where feed equals to feedId
        defaultPublicacaoFeedShouldBeFound("feedId.equals=" + feedId);

        // Get all the publicacaoFeedList where feed equals to feedId + 1
        defaultPublicacaoFeedShouldNotBeFound("feedId.equals=" + (feedId + 1));
    }

/*
    @Test
    @Transactional
    public void getAllPublicacaoFeedsByCategoriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CategoriaPublicacao categoria = publicacaoFeed.getCategoria();
        publicacaoFeedRepository.saveAndFlush(publicacaoFeed);
        Long categoriaId = categoria.getId();

        // Get all the publicacaoFeedList where categoria equals to categoriaId
        defaultPublicacaoFeedShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the publicacaoFeedList where categoria equals to categoriaId + 1
        defaultPublicacaoFeedShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }
*/

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublicacaoFeedShouldBeFound(String filter) throws Exception {
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacaoFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublicacaoFeedShouldNotBeFound(String filter) throws Exception {
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPublicacaoFeed() throws Exception {
        // Get the publicacaoFeed
        restPublicacaoFeedMockMvc.perform(get("/api/publicacao-feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacaoFeed() throws Exception {
        // Initialize the database
        publicacaoFeedService.save(publicacaoFeed);

        int databaseSizeBeforeUpdate = publicacaoFeedRepository.findAll().size();

        // Update the publicacaoFeed
        PublicacaoFeed updatedPublicacaoFeed = publicacaoFeedRepository.findById(publicacaoFeed.getId()).get();
        // Disconnect from session so that the updates on updatedPublicacaoFeed are not directly saved in db
        em.detach(updatedPublicacaoFeed);
        updatedPublicacaoFeed
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .titulo(UPDATED_TITULO)
            .autor(UPDATED_AUTOR)
            .uri(UPDATED_URI)
            .link(UPDATED_LINK)
            .conteudo(UPDATED_CONTEUDO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .status(UPDATED_STATUS);

        restPublicacaoFeedMockMvc.perform(put("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicacaoFeed)))
            .andExpect(status().isOk());

        // Validate the PublicacaoFeed in the database
        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeUpdate);
        PublicacaoFeed testPublicacaoFeed = publicacaoFeedList.get(publicacaoFeedList.size() - 1);
        assertThat(testPublicacaoFeed.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testPublicacaoFeed.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublicacaoFeed.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testPublicacaoFeed.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testPublicacaoFeed.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testPublicacaoFeed.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testPublicacaoFeed.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testPublicacaoFeed.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testPublicacaoFeed.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacaoFeed() throws Exception {
        int databaseSizeBeforeUpdate = publicacaoFeedRepository.findAll().size();

        // Create the PublicacaoFeed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicacaoFeedMockMvc.perform(put("/api/publicacao-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacaoFeed)))
            .andExpect(status().isBadRequest());

        // Validate the PublicacaoFeed in the database
        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicacaoFeed() throws Exception {
        // Initialize the database
        publicacaoFeedService.save(publicacaoFeed);

        int databaseSizeBeforeDelete = publicacaoFeedRepository.findAll().size();

        // Delete the publicacaoFeed
        restPublicacaoFeedMockMvc.perform(delete("/api/publicacao-feeds/{id}", publicacaoFeed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicacaoFeed> publicacaoFeedList = publicacaoFeedRepository.findAll();
        assertThat(publicacaoFeedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
