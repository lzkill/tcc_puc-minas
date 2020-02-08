package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Feed;
import com.lzkill.sgq.repository.FeedRepository;
import com.lzkill.sgq.service.FeedService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.FeedCriteria;
import com.lzkill.sgq.service.FeedQueryService;

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

import com.lzkill.sgq.domain.enumeration.TipoFeed;
import com.lzkill.sgq.domain.enumeration.StatusFeed;
/**
 * Integration tests for the {@link FeedResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class FeedResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final TipoFeed DEFAULT_TIPO = TipoFeed.RSS;
    private static final TipoFeed UPDATED_TIPO = TipoFeed.ATOM;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_IMAGEM = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTURA_IMAGEM = 32;
    private static final Integer UPDATED_ALTURA_IMAGEM = 33;
    private static final Integer SMALLER_ALTURA_IMAGEM = 32 - 1;

    private static final Integer DEFAULT_LARGURA_IMAGEM = 32;
    private static final Integer UPDATED_LARGURA_IMAGEM = 33;
    private static final Integer SMALLER_LARGURA_IMAGEM = 32 - 1;

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusFeed DEFAULT_STATUS = StatusFeed.ATIVO;
    private static final StatusFeed UPDATED_STATUS = StatusFeed.INATIVO;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedQueryService feedQueryService;

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

    private MockMvc restFeedMockMvc;

    private Feed feed;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeedResource feedResource = new FeedResource(feedService, feedQueryService);
        this.restFeedMockMvc = MockMvcBuilders.standaloneSetup(feedResource)
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
    public static Feed createEntity(EntityManager em) {
        Feed feed = new Feed()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .uri(DEFAULT_URI)
            .link(DEFAULT_LINK)
            .urlImagem(DEFAULT_URL_IMAGEM)
            .tituloImagem(DEFAULT_TITULO_IMAGEM)
            .alturaImagem(DEFAULT_ALTURA_IMAGEM)
            .larguraImagem(DEFAULT_LARGURA_IMAGEM)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .status(DEFAULT_STATUS);
        return feed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feed createUpdatedEntity(EntityManager em) {
        Feed feed = new Feed()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .uri(UPDATED_URI)
            .link(UPDATED_LINK)
            .urlImagem(UPDATED_URL_IMAGEM)
            .tituloImagem(UPDATED_TITULO_IMAGEM)
            .alturaImagem(UPDATED_ALTURA_IMAGEM)
            .larguraImagem(UPDATED_LARGURA_IMAGEM)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .status(UPDATED_STATUS);
        return feed;
    }

    @BeforeEach
    public void initTest() {
        feed = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeed() throws Exception {
        int databaseSizeBeforeCreate = feedRepository.findAll().size();

        // Create the Feed
        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isCreated());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeCreate + 1);
        Feed testFeed = feedList.get(feedList.size() - 1);
        assertThat(testFeed.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testFeed.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testFeed.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testFeed.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFeed.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testFeed.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testFeed.getUrlImagem()).isEqualTo(DEFAULT_URL_IMAGEM);
        assertThat(testFeed.getTituloImagem()).isEqualTo(DEFAULT_TITULO_IMAGEM);
        assertThat(testFeed.getAlturaImagem()).isEqualTo(DEFAULT_ALTURA_IMAGEM);
        assertThat(testFeed.getLarguraImagem()).isEqualTo(DEFAULT_LARGURA_IMAGEM);
        assertThat(testFeed.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testFeed.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedRepository.findAll().size();

        // Create the Feed with an existing ID
        feed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setIdUsuarioRegistro(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setTipo(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setTitulo(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setDescricao(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setUri(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setDataRegistro(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedRepository.findAll().size();
        // set the field null
        feed.setStatus(null);

        // Create the Feed, which fails.

        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeeds() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList
        restFeedMockMvc.perform(get("/api/feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feed.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].urlImagem").value(hasItem(DEFAULT_URL_IMAGEM)))
            .andExpect(jsonPath("$.[*].tituloImagem").value(hasItem(DEFAULT_TITULO_IMAGEM)))
            .andExpect(jsonPath("$.[*].alturaImagem").value(hasItem(DEFAULT_ALTURA_IMAGEM)))
            .andExpect(jsonPath("$.[*].larguraImagem").value(hasItem(DEFAULT_LARGURA_IMAGEM)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFeed() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", feed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feed.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.urlImagem").value(DEFAULT_URL_IMAGEM))
            .andExpect(jsonPath("$.tituloImagem").value(DEFAULT_TITULO_IMAGEM))
            .andExpect(jsonPath("$.alturaImagem").value(DEFAULT_ALTURA_IMAGEM))
            .andExpect(jsonPath("$.larguraImagem").value(DEFAULT_LARGURA_IMAGEM))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getFeedsByIdFiltering() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        Long id = feed.getId();

        defaultFeedShouldBeFound("id.equals=" + id);
        defaultFeedShouldNotBeFound("id.notEquals=" + id);

        defaultFeedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro is not null
        defaultFeedShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the feedList where idUsuarioRegistro is null
        defaultFeedShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultFeedShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the feedList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultFeedShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllFeedsByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tipo equals to DEFAULT_TIPO
        defaultFeedShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the feedList where tipo equals to UPDATED_TIPO
        defaultFeedShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tipo not equals to DEFAULT_TIPO
        defaultFeedShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the feedList where tipo not equals to UPDATED_TIPO
        defaultFeedShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultFeedShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the feedList where tipo equals to UPDATED_TIPO
        defaultFeedShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tipo is not null
        defaultFeedShouldBeFound("tipo.specified=true");

        // Get all the feedList where tipo is null
        defaultFeedShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo equals to DEFAULT_TITULO
        defaultFeedShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the feedList where titulo equals to UPDATED_TITULO
        defaultFeedShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo not equals to DEFAULT_TITULO
        defaultFeedShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the feedList where titulo not equals to UPDATED_TITULO
        defaultFeedShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultFeedShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the feedList where titulo equals to UPDATED_TITULO
        defaultFeedShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo is not null
        defaultFeedShouldBeFound("titulo.specified=true");

        // Get all the feedList where titulo is null
        defaultFeedShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByTituloContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo contains DEFAULT_TITULO
        defaultFeedShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the feedList where titulo contains UPDATED_TITULO
        defaultFeedShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where titulo does not contain DEFAULT_TITULO
        defaultFeedShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the feedList where titulo does not contain UPDATED_TITULO
        defaultFeedShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllFeedsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao equals to DEFAULT_DESCRICAO
        defaultFeedShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the feedList where descricao equals to UPDATED_DESCRICAO
        defaultFeedShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao not equals to DEFAULT_DESCRICAO
        defaultFeedShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the feedList where descricao not equals to UPDATED_DESCRICAO
        defaultFeedShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultFeedShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the feedList where descricao equals to UPDATED_DESCRICAO
        defaultFeedShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao is not null
        defaultFeedShouldBeFound("descricao.specified=true");

        // Get all the feedList where descricao is null
        defaultFeedShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao contains DEFAULT_DESCRICAO
        defaultFeedShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the feedList where descricao contains UPDATED_DESCRICAO
        defaultFeedShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where descricao does not contain DEFAULT_DESCRICAO
        defaultFeedShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the feedList where descricao does not contain UPDATED_DESCRICAO
        defaultFeedShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllFeedsByUriIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri equals to DEFAULT_URI
        defaultFeedShouldBeFound("uri.equals=" + DEFAULT_URI);

        // Get all the feedList where uri equals to UPDATED_URI
        defaultFeedShouldNotBeFound("uri.equals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllFeedsByUriIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri not equals to DEFAULT_URI
        defaultFeedShouldNotBeFound("uri.notEquals=" + DEFAULT_URI);

        // Get all the feedList where uri not equals to UPDATED_URI
        defaultFeedShouldBeFound("uri.notEquals=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllFeedsByUriIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri in DEFAULT_URI or UPDATED_URI
        defaultFeedShouldBeFound("uri.in=" + DEFAULT_URI + "," + UPDATED_URI);

        // Get all the feedList where uri equals to UPDATED_URI
        defaultFeedShouldNotBeFound("uri.in=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllFeedsByUriIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri is not null
        defaultFeedShouldBeFound("uri.specified=true");

        // Get all the feedList where uri is null
        defaultFeedShouldNotBeFound("uri.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByUriContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri contains DEFAULT_URI
        defaultFeedShouldBeFound("uri.contains=" + DEFAULT_URI);

        // Get all the feedList where uri contains UPDATED_URI
        defaultFeedShouldNotBeFound("uri.contains=" + UPDATED_URI);
    }

    @Test
    @Transactional
    public void getAllFeedsByUriNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where uri does not contain DEFAULT_URI
        defaultFeedShouldNotBeFound("uri.doesNotContain=" + DEFAULT_URI);

        // Get all the feedList where uri does not contain UPDATED_URI
        defaultFeedShouldBeFound("uri.doesNotContain=" + UPDATED_URI);
    }


    @Test
    @Transactional
    public void getAllFeedsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link equals to DEFAULT_LINK
        defaultFeedShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the feedList where link equals to UPDATED_LINK
        defaultFeedShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllFeedsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link not equals to DEFAULT_LINK
        defaultFeedShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the feedList where link not equals to UPDATED_LINK
        defaultFeedShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllFeedsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link in DEFAULT_LINK or UPDATED_LINK
        defaultFeedShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the feedList where link equals to UPDATED_LINK
        defaultFeedShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllFeedsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link is not null
        defaultFeedShouldBeFound("link.specified=true");

        // Get all the feedList where link is null
        defaultFeedShouldNotBeFound("link.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByLinkContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link contains DEFAULT_LINK
        defaultFeedShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the feedList where link contains UPDATED_LINK
        defaultFeedShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllFeedsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where link does not contain DEFAULT_LINK
        defaultFeedShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the feedList where link does not contain UPDATED_LINK
        defaultFeedShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }


    @Test
    @Transactional
    public void getAllFeedsByUrlImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem equals to DEFAULT_URL_IMAGEM
        defaultFeedShouldBeFound("urlImagem.equals=" + DEFAULT_URL_IMAGEM);

        // Get all the feedList where urlImagem equals to UPDATED_URL_IMAGEM
        defaultFeedShouldNotBeFound("urlImagem.equals=" + UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByUrlImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem not equals to DEFAULT_URL_IMAGEM
        defaultFeedShouldNotBeFound("urlImagem.notEquals=" + DEFAULT_URL_IMAGEM);

        // Get all the feedList where urlImagem not equals to UPDATED_URL_IMAGEM
        defaultFeedShouldBeFound("urlImagem.notEquals=" + UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByUrlImagemIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem in DEFAULT_URL_IMAGEM or UPDATED_URL_IMAGEM
        defaultFeedShouldBeFound("urlImagem.in=" + DEFAULT_URL_IMAGEM + "," + UPDATED_URL_IMAGEM);

        // Get all the feedList where urlImagem equals to UPDATED_URL_IMAGEM
        defaultFeedShouldNotBeFound("urlImagem.in=" + UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByUrlImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem is not null
        defaultFeedShouldBeFound("urlImagem.specified=true");

        // Get all the feedList where urlImagem is null
        defaultFeedShouldNotBeFound("urlImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByUrlImagemContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem contains DEFAULT_URL_IMAGEM
        defaultFeedShouldBeFound("urlImagem.contains=" + DEFAULT_URL_IMAGEM);

        // Get all the feedList where urlImagem contains UPDATED_URL_IMAGEM
        defaultFeedShouldNotBeFound("urlImagem.contains=" + UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByUrlImagemNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where urlImagem does not contain DEFAULT_URL_IMAGEM
        defaultFeedShouldNotBeFound("urlImagem.doesNotContain=" + DEFAULT_URL_IMAGEM);

        // Get all the feedList where urlImagem does not contain UPDATED_URL_IMAGEM
        defaultFeedShouldBeFound("urlImagem.doesNotContain=" + UPDATED_URL_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllFeedsByTituloImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem equals to DEFAULT_TITULO_IMAGEM
        defaultFeedShouldBeFound("tituloImagem.equals=" + DEFAULT_TITULO_IMAGEM);

        // Get all the feedList where tituloImagem equals to UPDATED_TITULO_IMAGEM
        defaultFeedShouldNotBeFound("tituloImagem.equals=" + UPDATED_TITULO_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem not equals to DEFAULT_TITULO_IMAGEM
        defaultFeedShouldNotBeFound("tituloImagem.notEquals=" + DEFAULT_TITULO_IMAGEM);

        // Get all the feedList where tituloImagem not equals to UPDATED_TITULO_IMAGEM
        defaultFeedShouldBeFound("tituloImagem.notEquals=" + UPDATED_TITULO_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloImagemIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem in DEFAULT_TITULO_IMAGEM or UPDATED_TITULO_IMAGEM
        defaultFeedShouldBeFound("tituloImagem.in=" + DEFAULT_TITULO_IMAGEM + "," + UPDATED_TITULO_IMAGEM);

        // Get all the feedList where tituloImagem equals to UPDATED_TITULO_IMAGEM
        defaultFeedShouldNotBeFound("tituloImagem.in=" + UPDATED_TITULO_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem is not null
        defaultFeedShouldBeFound("tituloImagem.specified=true");

        // Get all the feedList where tituloImagem is null
        defaultFeedShouldNotBeFound("tituloImagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllFeedsByTituloImagemContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem contains DEFAULT_TITULO_IMAGEM
        defaultFeedShouldBeFound("tituloImagem.contains=" + DEFAULT_TITULO_IMAGEM);

        // Get all the feedList where tituloImagem contains UPDATED_TITULO_IMAGEM
        defaultFeedShouldNotBeFound("tituloImagem.contains=" + UPDATED_TITULO_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByTituloImagemNotContainsSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where tituloImagem does not contain DEFAULT_TITULO_IMAGEM
        defaultFeedShouldNotBeFound("tituloImagem.doesNotContain=" + DEFAULT_TITULO_IMAGEM);

        // Get all the feedList where tituloImagem does not contain UPDATED_TITULO_IMAGEM
        defaultFeedShouldBeFound("tituloImagem.doesNotContain=" + UPDATED_TITULO_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem equals to DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.equals=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem equals to UPDATED_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.equals=" + UPDATED_ALTURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem not equals to DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.notEquals=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem not equals to UPDATED_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.notEquals=" + UPDATED_ALTURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem in DEFAULT_ALTURA_IMAGEM or UPDATED_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.in=" + DEFAULT_ALTURA_IMAGEM + "," + UPDATED_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem equals to UPDATED_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.in=" + UPDATED_ALTURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem is not null
        defaultFeedShouldBeFound("alturaImagem.specified=true");

        // Get all the feedList where alturaImagem is null
        defaultFeedShouldNotBeFound("alturaImagem.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem is greater than or equal to DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.greaterThanOrEqual=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem is greater than or equal to (DEFAULT_ALTURA_IMAGEM + 1)
        defaultFeedShouldNotBeFound("alturaImagem.greaterThanOrEqual=" + (DEFAULT_ALTURA_IMAGEM + 1));
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem is less than or equal to DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.lessThanOrEqual=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem is less than or equal to SMALLER_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.lessThanOrEqual=" + SMALLER_ALTURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsLessThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem is less than DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.lessThan=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem is less than (DEFAULT_ALTURA_IMAGEM + 1)
        defaultFeedShouldBeFound("alturaImagem.lessThan=" + (DEFAULT_ALTURA_IMAGEM + 1));
    }

    @Test
    @Transactional
    public void getAllFeedsByAlturaImagemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where alturaImagem is greater than DEFAULT_ALTURA_IMAGEM
        defaultFeedShouldNotBeFound("alturaImagem.greaterThan=" + DEFAULT_ALTURA_IMAGEM);

        // Get all the feedList where alturaImagem is greater than SMALLER_ALTURA_IMAGEM
        defaultFeedShouldBeFound("alturaImagem.greaterThan=" + SMALLER_ALTURA_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem equals to DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.equals=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem equals to UPDATED_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.equals=" + UPDATED_LARGURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem not equals to DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.notEquals=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem not equals to UPDATED_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.notEquals=" + UPDATED_LARGURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem in DEFAULT_LARGURA_IMAGEM or UPDATED_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.in=" + DEFAULT_LARGURA_IMAGEM + "," + UPDATED_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem equals to UPDATED_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.in=" + UPDATED_LARGURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem is not null
        defaultFeedShouldBeFound("larguraImagem.specified=true");

        // Get all the feedList where larguraImagem is null
        defaultFeedShouldNotBeFound("larguraImagem.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem is greater than or equal to DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.greaterThanOrEqual=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem is greater than or equal to (DEFAULT_LARGURA_IMAGEM + 1)
        defaultFeedShouldNotBeFound("larguraImagem.greaterThanOrEqual=" + (DEFAULT_LARGURA_IMAGEM + 1));
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem is less than or equal to DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.lessThanOrEqual=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem is less than or equal to SMALLER_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.lessThanOrEqual=" + SMALLER_LARGURA_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsLessThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem is less than DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.lessThan=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem is less than (DEFAULT_LARGURA_IMAGEM + 1)
        defaultFeedShouldBeFound("larguraImagem.lessThan=" + (DEFAULT_LARGURA_IMAGEM + 1));
    }

    @Test
    @Transactional
    public void getAllFeedsByLarguraImagemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where larguraImagem is greater than DEFAULT_LARGURA_IMAGEM
        defaultFeedShouldNotBeFound("larguraImagem.greaterThan=" + DEFAULT_LARGURA_IMAGEM);

        // Get all the feedList where larguraImagem is greater than SMALLER_LARGURA_IMAGEM
        defaultFeedShouldBeFound("larguraImagem.greaterThan=" + SMALLER_LARGURA_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllFeedsByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultFeedShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the feedList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultFeedShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultFeedShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the feedList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultFeedShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultFeedShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the feedList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultFeedShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllFeedsByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where dataRegistro is not null
        defaultFeedShouldBeFound("dataRegistro.specified=true");

        // Get all the feedList where dataRegistro is null
        defaultFeedShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllFeedsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where status equals to DEFAULT_STATUS
        defaultFeedShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the feedList where status equals to UPDATED_STATUS
        defaultFeedShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFeedsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where status not equals to DEFAULT_STATUS
        defaultFeedShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the feedList where status not equals to UPDATED_STATUS
        defaultFeedShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFeedsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFeedShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the feedList where status equals to UPDATED_STATUS
        defaultFeedShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFeedsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList where status is not null
        defaultFeedShouldBeFound("status.specified=true");

        // Get all the feedList where status is null
        defaultFeedShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedShouldBeFound(String filter) throws Exception {
        restFeedMockMvc.perform(get("/api/feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feed.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].urlImagem").value(hasItem(DEFAULT_URL_IMAGEM)))
            .andExpect(jsonPath("$.[*].tituloImagem").value(hasItem(DEFAULT_TITULO_IMAGEM)))
            .andExpect(jsonPath("$.[*].alturaImagem").value(hasItem(DEFAULT_ALTURA_IMAGEM)))
            .andExpect(jsonPath("$.[*].larguraImagem").value(hasItem(DEFAULT_LARGURA_IMAGEM)))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restFeedMockMvc.perform(get("/api/feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedShouldNotBeFound(String filter) throws Exception {
        restFeedMockMvc.perform(get("/api/feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedMockMvc.perform(get("/api/feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFeed() throws Exception {
        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeed() throws Exception {
        // Initialize the database
        feedService.save(feed);

        int databaseSizeBeforeUpdate = feedRepository.findAll().size();

        // Update the feed
        Feed updatedFeed = feedRepository.findById(feed.getId()).get();
        // Disconnect from session so that the updates on updatedFeed are not directly saved in db
        em.detach(updatedFeed);
        updatedFeed
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .uri(UPDATED_URI)
            .link(UPDATED_LINK)
            .urlImagem(UPDATED_URL_IMAGEM)
            .tituloImagem(UPDATED_TITULO_IMAGEM)
            .alturaImagem(UPDATED_ALTURA_IMAGEM)
            .larguraImagem(UPDATED_LARGURA_IMAGEM)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .status(UPDATED_STATUS);

        restFeedMockMvc.perform(put("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeed)))
            .andExpect(status().isOk());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeUpdate);
        Feed testFeed = feedList.get(feedList.size() - 1);
        assertThat(testFeed.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testFeed.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testFeed.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testFeed.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFeed.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testFeed.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testFeed.getUrlImagem()).isEqualTo(UPDATED_URL_IMAGEM);
        assertThat(testFeed.getTituloImagem()).isEqualTo(UPDATED_TITULO_IMAGEM);
        assertThat(testFeed.getAlturaImagem()).isEqualTo(UPDATED_ALTURA_IMAGEM);
        assertThat(testFeed.getLarguraImagem()).isEqualTo(UPDATED_LARGURA_IMAGEM);
        assertThat(testFeed.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testFeed.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFeed() throws Exception {
        int databaseSizeBeforeUpdate = feedRepository.findAll().size();

        // Create the Feed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedMockMvc.perform(put("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feed)))
            .andExpect(status().isBadRequest());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeed() throws Exception {
        // Initialize the database
        feedService.save(feed);

        int databaseSizeBeforeDelete = feedRepository.findAll().size();

        // Delete the feed
        restFeedMockMvc.perform(delete("/api/feeds/{id}", feed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
