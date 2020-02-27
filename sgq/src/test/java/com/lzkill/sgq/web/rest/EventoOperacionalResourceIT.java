package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.EventoOperacional;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.EventoOperacionalRepository;
import com.lzkill.sgq.service.EventoOperacionalService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.EventoOperacionalCriteria;
import com.lzkill.sgq.service.EventoOperacionalQueryService;

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
import java.time.Duration;
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

import com.lzkill.sgq.domain.enumeration.TipoEventoOperacional;
/**
 * Integration tests for the {@link EventoOperacionalResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class EventoOperacionalResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_REGISTRO = 1;
    private static final Integer UPDATED_ID_USUARIO_REGISTRO = 2;
    private static final Integer SMALLER_ID_USUARIO_REGISTRO = 1 - 1;

    private static final TipoEventoOperacional DEFAULT_TIPO = TipoEventoOperacional.FALHA_EQUIPAMENTO;
    private static final TipoEventoOperacional UPDATED_TIPO = TipoEventoOperacional.FALHA_INFRA_ESTRUTURA;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_EVENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EVENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Duration DEFAULT_DURACAO = Duration.ofHours(6);
    private static final Duration UPDATED_DURACAO = Duration.ofHours(12);
    private static final Duration SMALLER_DURACAO = Duration.ofHours(5);

    private static final Boolean DEFAULT_HOUVE_PARADA_PRODUCAO = false;
    private static final Boolean UPDATED_HOUVE_PARADA_PRODUCAO = true;

    @Autowired
    private EventoOperacionalRepository eventoOperacionalRepository;

    @Mock
    private EventoOperacionalRepository eventoOperacionalRepositoryMock;

    @Mock
    private EventoOperacionalService eventoOperacionalServiceMock;

    @Autowired
    private EventoOperacionalService eventoOperacionalService;

    @Autowired
    private EventoOperacionalQueryService eventoOperacionalQueryService;

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

    private MockMvc restEventoOperacionalMockMvc;

    private EventoOperacional eventoOperacional;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoOperacionalResource eventoOperacionalResource = new EventoOperacionalResource(eventoOperacionalService, eventoOperacionalQueryService);
        this.restEventoOperacionalMockMvc = MockMvcBuilders.standaloneSetup(eventoOperacionalResource)
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
    public static EventoOperacional createEntity(EntityManager em) {
        EventoOperacional eventoOperacional = new EventoOperacional()
            .idUsuarioRegistro(DEFAULT_ID_USUARIO_REGISTRO)
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO)
            .dataEvento(DEFAULT_DATA_EVENTO)
            .duracao(DEFAULT_DURACAO)
            .houveParadaProducao(DEFAULT_HOUVE_PARADA_PRODUCAO);
        return eventoOperacional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventoOperacional createUpdatedEntity(EntityManager em) {
        EventoOperacional eventoOperacional = new EventoOperacional()
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataEvento(UPDATED_DATA_EVENTO)
            .duracao(UPDATED_DURACAO)
            .houveParadaProducao(UPDATED_HOUVE_PARADA_PRODUCAO);
        return eventoOperacional;
    }

    @BeforeEach
    public void initTest() {
        eventoOperacional = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventoOperacional() throws Exception {
        int databaseSizeBeforeCreate = eventoOperacionalRepository.findAll().size();

        // Create the EventoOperacional
        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isCreated());

        // Validate the EventoOperacional in the database
        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeCreate + 1);
        EventoOperacional testEventoOperacional = eventoOperacionalList.get(eventoOperacionalList.size() - 1);
        assertThat(testEventoOperacional.getIdUsuarioRegistro()).isEqualTo(DEFAULT_ID_USUARIO_REGISTRO);
        assertThat(testEventoOperacional.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEventoOperacional.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testEventoOperacional.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testEventoOperacional.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
        assertThat(testEventoOperacional.getDataEvento()).isEqualTo(DEFAULT_DATA_EVENTO);
        assertThat(testEventoOperacional.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testEventoOperacional.isHouveParadaProducao()).isEqualTo(DEFAULT_HOUVE_PARADA_PRODUCAO);
    }

    @Test
    @Transactional
    public void createEventoOperacionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoOperacionalRepository.findAll().size();

        // Create the EventoOperacional with an existing ID
        eventoOperacional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        // Validate the EventoOperacional in the database
        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setIdUsuarioRegistro(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setTipo(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setTitulo(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setDataRegistro(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setDataEvento(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouveParadaProducaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoOperacionalRepository.findAll().size();
        // set the field null
        eventoOperacional.setHouveParadaProducao(null);

        // Create the EventoOperacional, which fails.

        restEventoOperacionalMockMvc.perform(post("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionals() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoOperacional.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataEvento").value(hasItem(DEFAULT_DATA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].houveParadaProducao").value(hasItem(DEFAULT_HOUVE_PARADA_PRODUCAO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEventoOperacionalsWithEagerRelationshipsIsEnabled() throws Exception {
        EventoOperacionalResource eventoOperacionalResource = new EventoOperacionalResource(eventoOperacionalServiceMock, eventoOperacionalQueryService);
        when(eventoOperacionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEventoOperacionalMockMvc = MockMvcBuilders.standaloneSetup(eventoOperacionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals?eagerload=true"))
        .andExpect(status().isOk());

        verify(eventoOperacionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEventoOperacionalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EventoOperacionalResource eventoOperacionalResource = new EventoOperacionalResource(eventoOperacionalServiceMock, eventoOperacionalQueryService);
            when(eventoOperacionalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEventoOperacionalMockMvc = MockMvcBuilders.standaloneSetup(eventoOperacionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals?eagerload=true"))
        .andExpect(status().isOk());

            verify(eventoOperacionalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEventoOperacional() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get the eventoOperacional
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals/{id}", eventoOperacional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventoOperacional.getId().intValue()))
            .andExpect(jsonPath("$.idUsuarioRegistro").value(DEFAULT_ID_USUARIO_REGISTRO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
            .andExpect(jsonPath("$.dataEvento").value(DEFAULT_DATA_EVENTO.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO.toString()))
            .andExpect(jsonPath("$.houveParadaProducao").value(DEFAULT_HOUVE_PARADA_PRODUCAO.booleanValue()));
    }


    @Test
    @Transactional
    public void getEventoOperacionalsByIdFiltering() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        Long id = eventoOperacional.getId();

        defaultEventoOperacionalShouldBeFound("id.equals=" + id);
        defaultEventoOperacionalShouldNotBeFound("id.notEquals=" + id);

        defaultEventoOperacionalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventoOperacionalShouldNotBeFound("id.greaterThan=" + id);

        defaultEventoOperacionalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventoOperacionalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.equals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.equals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro not equals to DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.notEquals=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro not equals to UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.notEquals=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro in DEFAULT_ID_USUARIO_REGISTRO or UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.in=" + DEFAULT_ID_USUARIO_REGISTRO + "," + UPDATED_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro equals to UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.in=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro is not null
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.specified=true");

        // Get all the eventoOperacionalList where idUsuarioRegistro is null
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro is greater than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro is greater than or equal to UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.greaterThanOrEqual=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro is less than or equal to DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.lessThanOrEqual=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro is less than or equal to SMALLER_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.lessThanOrEqual=" + SMALLER_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro is less than DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.lessThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro is less than UPDATED_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.lessThan=" + UPDATED_ID_USUARIO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByIdUsuarioRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where idUsuarioRegistro is greater than DEFAULT_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("idUsuarioRegistro.greaterThan=" + DEFAULT_ID_USUARIO_REGISTRO);

        // Get all the eventoOperacionalList where idUsuarioRegistro is greater than SMALLER_ID_USUARIO_REGISTRO
        defaultEventoOperacionalShouldBeFound("idUsuarioRegistro.greaterThan=" + SMALLER_ID_USUARIO_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllEventoOperacionalsByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where tipo equals to DEFAULT_TIPO
        defaultEventoOperacionalShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the eventoOperacionalList where tipo equals to UPDATED_TIPO
        defaultEventoOperacionalShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where tipo not equals to DEFAULT_TIPO
        defaultEventoOperacionalShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the eventoOperacionalList where tipo not equals to UPDATED_TIPO
        defaultEventoOperacionalShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultEventoOperacionalShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the eventoOperacionalList where tipo equals to UPDATED_TIPO
        defaultEventoOperacionalShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where tipo is not null
        defaultEventoOperacionalShouldBeFound("tipo.specified=true");

        // Get all the eventoOperacionalList where tipo is null
        defaultEventoOperacionalShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo equals to DEFAULT_TITULO
        defaultEventoOperacionalShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the eventoOperacionalList where titulo equals to UPDATED_TITULO
        defaultEventoOperacionalShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo not equals to DEFAULT_TITULO
        defaultEventoOperacionalShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the eventoOperacionalList where titulo not equals to UPDATED_TITULO
        defaultEventoOperacionalShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultEventoOperacionalShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the eventoOperacionalList where titulo equals to UPDATED_TITULO
        defaultEventoOperacionalShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo is not null
        defaultEventoOperacionalShouldBeFound("titulo.specified=true");

        // Get all the eventoOperacionalList where titulo is null
        defaultEventoOperacionalShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloContainsSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo contains DEFAULT_TITULO
        defaultEventoOperacionalShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the eventoOperacionalList where titulo contains UPDATED_TITULO
        defaultEventoOperacionalShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where titulo does not contain DEFAULT_TITULO
        defaultEventoOperacionalShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the eventoOperacionalList where titulo does not contain UPDATED_TITULO
        defaultEventoOperacionalShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultEventoOperacionalShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the eventoOperacionalList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the eventoOperacionalList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultEventoOperacionalShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultEventoOperacionalShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the eventoOperacionalList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultEventoOperacionalShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataRegistro is not null
        defaultEventoOperacionalShouldBeFound("dataRegistro.specified=true");

        // Get all the eventoOperacionalList where dataRegistro is null
        defaultEventoOperacionalShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataEventoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataEvento equals to DEFAULT_DATA_EVENTO
        defaultEventoOperacionalShouldBeFound("dataEvento.equals=" + DEFAULT_DATA_EVENTO);

        // Get all the eventoOperacionalList where dataEvento equals to UPDATED_DATA_EVENTO
        defaultEventoOperacionalShouldNotBeFound("dataEvento.equals=" + UPDATED_DATA_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataEventoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataEvento not equals to DEFAULT_DATA_EVENTO
        defaultEventoOperacionalShouldNotBeFound("dataEvento.notEquals=" + DEFAULT_DATA_EVENTO);

        // Get all the eventoOperacionalList where dataEvento not equals to UPDATED_DATA_EVENTO
        defaultEventoOperacionalShouldBeFound("dataEvento.notEquals=" + UPDATED_DATA_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataEventoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataEvento in DEFAULT_DATA_EVENTO or UPDATED_DATA_EVENTO
        defaultEventoOperacionalShouldBeFound("dataEvento.in=" + DEFAULT_DATA_EVENTO + "," + UPDATED_DATA_EVENTO);

        // Get all the eventoOperacionalList where dataEvento equals to UPDATED_DATA_EVENTO
        defaultEventoOperacionalShouldNotBeFound("dataEvento.in=" + UPDATED_DATA_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDataEventoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where dataEvento is not null
        defaultEventoOperacionalShouldBeFound("dataEvento.specified=true");

        // Get all the eventoOperacionalList where dataEvento is null
        defaultEventoOperacionalShouldNotBeFound("dataEvento.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao equals to DEFAULT_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.equals=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao equals to UPDATED_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.equals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao not equals to DEFAULT_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.notEquals=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao not equals to UPDATED_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.notEquals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao in DEFAULT_DURACAO or UPDATED_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.in=" + DEFAULT_DURACAO + "," + UPDATED_DURACAO);

        // Get all the eventoOperacionalList where duracao equals to UPDATED_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.in=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao is not null
        defaultEventoOperacionalShouldBeFound("duracao.specified=true");

        // Get all the eventoOperacionalList where duracao is null
        defaultEventoOperacionalShouldNotBeFound("duracao.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao is greater than or equal to DEFAULT_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.greaterThanOrEqual=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao is greater than or equal to UPDATED_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.greaterThanOrEqual=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao is less than or equal to DEFAULT_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.lessThanOrEqual=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao is less than or equal to SMALLER_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.lessThanOrEqual=" + SMALLER_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsLessThanSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao is less than DEFAULT_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.lessThan=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao is less than UPDATED_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.lessThan=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByDuracaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where duracao is greater than DEFAULT_DURACAO
        defaultEventoOperacionalShouldNotBeFound("duracao.greaterThan=" + DEFAULT_DURACAO);

        // Get all the eventoOperacionalList where duracao is greater than SMALLER_DURACAO
        defaultEventoOperacionalShouldBeFound("duracao.greaterThan=" + SMALLER_DURACAO);
    }


    @Test
    @Transactional
    public void getAllEventoOperacionalsByHouveParadaProducaoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where houveParadaProducao equals to DEFAULT_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldBeFound("houveParadaProducao.equals=" + DEFAULT_HOUVE_PARADA_PRODUCAO);

        // Get all the eventoOperacionalList where houveParadaProducao equals to UPDATED_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldNotBeFound("houveParadaProducao.equals=" + UPDATED_HOUVE_PARADA_PRODUCAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByHouveParadaProducaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where houveParadaProducao not equals to DEFAULT_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldNotBeFound("houveParadaProducao.notEquals=" + DEFAULT_HOUVE_PARADA_PRODUCAO);

        // Get all the eventoOperacionalList where houveParadaProducao not equals to UPDATED_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldBeFound("houveParadaProducao.notEquals=" + UPDATED_HOUVE_PARADA_PRODUCAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByHouveParadaProducaoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where houveParadaProducao in DEFAULT_HOUVE_PARADA_PRODUCAO or UPDATED_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldBeFound("houveParadaProducao.in=" + DEFAULT_HOUVE_PARADA_PRODUCAO + "," + UPDATED_HOUVE_PARADA_PRODUCAO);

        // Get all the eventoOperacionalList where houveParadaProducao equals to UPDATED_HOUVE_PARADA_PRODUCAO
        defaultEventoOperacionalShouldNotBeFound("houveParadaProducao.in=" + UPDATED_HOUVE_PARADA_PRODUCAO);
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByHouveParadaProducaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);

        // Get all the eventoOperacionalList where houveParadaProducao is not null
        defaultEventoOperacionalShouldBeFound("houveParadaProducao.specified=true");

        // Get all the eventoOperacionalList where houveParadaProducao is null
        defaultEventoOperacionalShouldNotBeFound("houveParadaProducao.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventoOperacionalsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        eventoOperacional.setProcesso(processo);
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);
        Long processoId = processo.getId();

        // Get all the eventoOperacionalList where processo equals to processoId
        defaultEventoOperacionalShouldBeFound("processoId.equals=" + processoId);

        // Get all the eventoOperacionalList where processo equals to processoId + 1
        defaultEventoOperacionalShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }


    @Test
    @Transactional
    public void getAllEventoOperacionalsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        eventoOperacional.addAnexo(anexo);
        eventoOperacionalRepository.saveAndFlush(eventoOperacional);
        Long anexoId = anexo.getId();

        // Get all the eventoOperacionalList where anexo equals to anexoId
        defaultEventoOperacionalShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the eventoOperacionalList where anexo equals to anexoId + 1
        defaultEventoOperacionalShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventoOperacionalShouldBeFound(String filter) throws Exception {
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoOperacional.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuarioRegistro").value(hasItem(DEFAULT_ID_USUARIO_REGISTRO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataEvento").value(hasItem(DEFAULT_DATA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].houveParadaProducao").value(hasItem(DEFAULT_HOUVE_PARADA_PRODUCAO.booleanValue())));

        // Check, that the count call also returns 1
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventoOperacionalShouldNotBeFound(String filter) throws Exception {
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEventoOperacional() throws Exception {
        // Get the eventoOperacional
        restEventoOperacionalMockMvc.perform(get("/api/evento-operacionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventoOperacional() throws Exception {
        // Initialize the database
        eventoOperacionalService.save(eventoOperacional);

        int databaseSizeBeforeUpdate = eventoOperacionalRepository.findAll().size();

        // Update the eventoOperacional
        EventoOperacional updatedEventoOperacional = eventoOperacionalRepository.findById(eventoOperacional.getId()).get();
        // Disconnect from session so that the updates on updatedEventoOperacional are not directly saved in db
        em.detach(updatedEventoOperacional);
        updatedEventoOperacional
            .idUsuarioRegistro(UPDATED_ID_USUARIO_REGISTRO)
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .dataRegistro(UPDATED_DATA_REGISTRO)
            .dataEvento(UPDATED_DATA_EVENTO)
            .duracao(UPDATED_DURACAO)
            .houveParadaProducao(UPDATED_HOUVE_PARADA_PRODUCAO);

        restEventoOperacionalMockMvc.perform(put("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventoOperacional)))
            .andExpect(status().isOk());

        // Validate the EventoOperacional in the database
        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeUpdate);
        EventoOperacional testEventoOperacional = eventoOperacionalList.get(eventoOperacionalList.size() - 1);
        assertThat(testEventoOperacional.getIdUsuarioRegistro()).isEqualTo(UPDATED_ID_USUARIO_REGISTRO);
        assertThat(testEventoOperacional.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEventoOperacional.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testEventoOperacional.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testEventoOperacional.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
        assertThat(testEventoOperacional.getDataEvento()).isEqualTo(UPDATED_DATA_EVENTO);
        assertThat(testEventoOperacional.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testEventoOperacional.isHouveParadaProducao()).isEqualTo(UPDATED_HOUVE_PARADA_PRODUCAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEventoOperacional() throws Exception {
        int databaseSizeBeforeUpdate = eventoOperacionalRepository.findAll().size();

        // Create the EventoOperacional

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoOperacionalMockMvc.perform(put("/api/evento-operacionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoOperacional)))
            .andExpect(status().isBadRequest());

        // Validate the EventoOperacional in the database
        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventoOperacional() throws Exception {
        // Initialize the database
        eventoOperacionalService.save(eventoOperacional);

        int databaseSizeBeforeDelete = eventoOperacionalRepository.findAll().size();

        // Delete the eventoOperacional
        restEventoOperacionalMockMvc.perform(delete("/api/evento-operacionals/{id}", eventoOperacional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventoOperacional> eventoOperacionalList = eventoOperacionalRepository.findAll();
        assertThat(eventoOperacionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
