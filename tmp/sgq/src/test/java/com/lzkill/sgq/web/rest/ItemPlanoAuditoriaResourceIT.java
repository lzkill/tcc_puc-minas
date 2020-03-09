package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.repository.ItemPlanoAuditoriaRepository;
import com.lzkill.sgq.service.ItemPlanoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ItemPlanoAuditoriaCriteria;
import com.lzkill.sgq.service.ItemPlanoAuditoriaQueryService;

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

import com.lzkill.sgq.domain.enumeration.ModalidadeAuditoria;
/**
 * Integration tests for the {@link ItemPlanoAuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ItemPlanoAuditoriaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ModalidadeAuditoria DEFAULT_MODALIDADE = ModalidadeAuditoria.INTERNA;
    private static final ModalidadeAuditoria UPDATED_MODALIDADE = ModalidadeAuditoria.EXTERNA;

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository;

    @Mock
    private ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepositoryMock;

    @Mock
    private ItemPlanoAuditoriaService itemPlanoAuditoriaServiceMock;

    @Autowired
    private ItemPlanoAuditoriaService itemPlanoAuditoriaService;

    @Autowired
    private ItemPlanoAuditoriaQueryService itemPlanoAuditoriaQueryService;

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

    private MockMvc restItemPlanoAuditoriaMockMvc;

    private ItemPlanoAuditoria itemPlanoAuditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPlanoAuditoriaResource itemPlanoAuditoriaResource = new ItemPlanoAuditoriaResource(itemPlanoAuditoriaService, itemPlanoAuditoriaQueryService);
        this.restItemPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemPlanoAuditoriaResource)
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
    public static ItemPlanoAuditoria createEntity(EntityManager em) {
        ItemPlanoAuditoria itemPlanoAuditoria = new ItemPlanoAuditoria()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .modalidade(DEFAULT_MODALIDADE)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        // Add required entity
        PlanoAuditoria planoAuditoria;
        if (TestUtil.findAll(em, PlanoAuditoria.class).isEmpty()) {
            planoAuditoria = PlanoAuditoriaResourceIT.createEntity(em);
            em.persist(planoAuditoria);
            em.flush();
        } else {
            planoAuditoria = TestUtil.findAll(em, PlanoAuditoria.class).get(0);
        }
        itemPlanoAuditoria.setPlano(planoAuditoria);
        return itemPlanoAuditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPlanoAuditoria createUpdatedEntity(EntityManager em) {
        ItemPlanoAuditoria itemPlanoAuditoria = new ItemPlanoAuditoria()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .modalidade(UPDATED_MODALIDADE)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);
        // Add required entity
        PlanoAuditoria planoAuditoria;
        if (TestUtil.findAll(em, PlanoAuditoria.class).isEmpty()) {
            planoAuditoria = PlanoAuditoriaResourceIT.createUpdatedEntity(em);
            em.persist(planoAuditoria);
            em.flush();
        } else {
            planoAuditoria = TestUtil.findAll(em, PlanoAuditoria.class).get(0);
        }
        itemPlanoAuditoria.setPlano(planoAuditoria);
        return itemPlanoAuditoria;
    }

    @BeforeEach
    public void initTest() {
        itemPlanoAuditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPlanoAuditoria() throws Exception {
        int databaseSizeBeforeCreate = itemPlanoAuditoriaRepository.findAll().size();

        // Create the ItemPlanoAuditoria
        restItemPlanoAuditoriaMockMvc.perform(post("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isCreated());

        // Validate the ItemPlanoAuditoria in the database
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPlanoAuditoria testItemPlanoAuditoria = itemPlanoAuditoriaList.get(itemPlanoAuditoriaList.size() - 1);
        assertThat(testItemPlanoAuditoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testItemPlanoAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testItemPlanoAuditoria.getModalidade()).isEqualTo(DEFAULT_MODALIDADE);
        assertThat(testItemPlanoAuditoria.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testItemPlanoAuditoria.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void createItemPlanoAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPlanoAuditoriaRepository.findAll().size();

        // Create the ItemPlanoAuditoria with an existing ID
        itemPlanoAuditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPlanoAuditoriaMockMvc.perform(post("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPlanoAuditoria in the database
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPlanoAuditoriaRepository.findAll().size();
        // set the field null
        itemPlanoAuditoria.setTitulo(null);

        // Create the ItemPlanoAuditoria, which fails.

        restItemPlanoAuditoriaMockMvc.perform(post("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPlanoAuditoriaRepository.findAll().size();
        // set the field null
        itemPlanoAuditoria.setModalidade(null);

        // Create the ItemPlanoAuditoria, which fails.

        restItemPlanoAuditoriaMockMvc.perform(post("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPlanoAuditoriaRepository.findAll().size();
        // set the field null
        itemPlanoAuditoria.setDataInicio(null);

        // Create the ItemPlanoAuditoria, which fails.

        restItemPlanoAuditoriaMockMvc.perform(post("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isBadRequest());

        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditorias() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPlanoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllItemPlanoAuditoriasWithEagerRelationshipsIsEnabled() throws Exception {
        ItemPlanoAuditoriaResource itemPlanoAuditoriaResource = new ItemPlanoAuditoriaResource(itemPlanoAuditoriaServiceMock, itemPlanoAuditoriaQueryService);
        when(itemPlanoAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restItemPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemPlanoAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias?eagerload=true"))
        .andExpect(status().isOk());

        verify(itemPlanoAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllItemPlanoAuditoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        ItemPlanoAuditoriaResource itemPlanoAuditoriaResource = new ItemPlanoAuditoriaResource(itemPlanoAuditoriaServiceMock, itemPlanoAuditoriaQueryService);
            when(itemPlanoAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restItemPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemPlanoAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias?eagerload=true"))
        .andExpect(status().isOk());

            verify(itemPlanoAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getItemPlanoAuditoria() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get the itemPlanoAuditoria
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias/{id}", itemPlanoAuditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPlanoAuditoria.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.modalidade").value(DEFAULT_MODALIDADE.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }


    @Test
    @Transactional
    public void getItemPlanoAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        Long id = itemPlanoAuditoria.getId();

        defaultItemPlanoAuditoriaShouldBeFound("id.equals=" + id);
        defaultItemPlanoAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultItemPlanoAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemPlanoAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultItemPlanoAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemPlanoAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo equals to DEFAULT_TITULO
        defaultItemPlanoAuditoriaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the itemPlanoAuditoriaList where titulo equals to UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo not equals to DEFAULT_TITULO
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the itemPlanoAuditoriaList where titulo not equals to UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the itemPlanoAuditoriaList where titulo equals to UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo is not null
        defaultItemPlanoAuditoriaShouldBeFound("titulo.specified=true");

        // Get all the itemPlanoAuditoriaList where titulo is null
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloContainsSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo contains DEFAULT_TITULO
        defaultItemPlanoAuditoriaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the itemPlanoAuditoriaList where titulo contains UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where titulo does not contain DEFAULT_TITULO
        defaultItemPlanoAuditoriaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the itemPlanoAuditoriaList where titulo does not contain UPDATED_TITULO
        defaultItemPlanoAuditoriaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByModalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where modalidade equals to DEFAULT_MODALIDADE
        defaultItemPlanoAuditoriaShouldBeFound("modalidade.equals=" + DEFAULT_MODALIDADE);

        // Get all the itemPlanoAuditoriaList where modalidade equals to UPDATED_MODALIDADE
        defaultItemPlanoAuditoriaShouldNotBeFound("modalidade.equals=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByModalidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where modalidade not equals to DEFAULT_MODALIDADE
        defaultItemPlanoAuditoriaShouldNotBeFound("modalidade.notEquals=" + DEFAULT_MODALIDADE);

        // Get all the itemPlanoAuditoriaList where modalidade not equals to UPDATED_MODALIDADE
        defaultItemPlanoAuditoriaShouldBeFound("modalidade.notEquals=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByModalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where modalidade in DEFAULT_MODALIDADE or UPDATED_MODALIDADE
        defaultItemPlanoAuditoriaShouldBeFound("modalidade.in=" + DEFAULT_MODALIDADE + "," + UPDATED_MODALIDADE);

        // Get all the itemPlanoAuditoriaList where modalidade equals to UPDATED_MODALIDADE
        defaultItemPlanoAuditoriaShouldNotBeFound("modalidade.in=" + UPDATED_MODALIDADE);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByModalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where modalidade is not null
        defaultItemPlanoAuditoriaShouldBeFound("modalidade.specified=true");

        // Get all the itemPlanoAuditoriaList where modalidade is null
        defaultItemPlanoAuditoriaShouldNotBeFound("modalidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataInicio equals to DEFAULT_DATA_INICIO
        defaultItemPlanoAuditoriaShouldBeFound("dataInicio.equals=" + DEFAULT_DATA_INICIO);

        // Get all the itemPlanoAuditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultItemPlanoAuditoriaShouldNotBeFound("dataInicio.equals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataInicio not equals to DEFAULT_DATA_INICIO
        defaultItemPlanoAuditoriaShouldNotBeFound("dataInicio.notEquals=" + DEFAULT_DATA_INICIO);

        // Get all the itemPlanoAuditoriaList where dataInicio not equals to UPDATED_DATA_INICIO
        defaultItemPlanoAuditoriaShouldBeFound("dataInicio.notEquals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataInicioIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataInicio in DEFAULT_DATA_INICIO or UPDATED_DATA_INICIO
        defaultItemPlanoAuditoriaShouldBeFound("dataInicio.in=" + DEFAULT_DATA_INICIO + "," + UPDATED_DATA_INICIO);

        // Get all the itemPlanoAuditoriaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultItemPlanoAuditoriaShouldNotBeFound("dataInicio.in=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataInicio is not null
        defaultItemPlanoAuditoriaShouldBeFound("dataInicio.specified=true");

        // Get all the itemPlanoAuditoriaList where dataInicio is null
        defaultItemPlanoAuditoriaShouldNotBeFound("dataInicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataFimIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataFim equals to DEFAULT_DATA_FIM
        defaultItemPlanoAuditoriaShouldBeFound("dataFim.equals=" + DEFAULT_DATA_FIM);

        // Get all the itemPlanoAuditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultItemPlanoAuditoriaShouldNotBeFound("dataFim.equals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataFim not equals to DEFAULT_DATA_FIM
        defaultItemPlanoAuditoriaShouldNotBeFound("dataFim.notEquals=" + DEFAULT_DATA_FIM);

        // Get all the itemPlanoAuditoriaList where dataFim not equals to UPDATED_DATA_FIM
        defaultItemPlanoAuditoriaShouldBeFound("dataFim.notEquals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataFimIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataFim in DEFAULT_DATA_FIM or UPDATED_DATA_FIM
        defaultItemPlanoAuditoriaShouldBeFound("dataFim.in=" + DEFAULT_DATA_FIM + "," + UPDATED_DATA_FIM);

        // Get all the itemPlanoAuditoriaList where dataFim equals to UPDATED_DATA_FIM
        defaultItemPlanoAuditoriaShouldNotBeFound("dataFim.in=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataFim is not null
        defaultItemPlanoAuditoriaShouldBeFound("dataFim.specified=true");

        // Get all the itemPlanoAuditoriaList where dataFim is null
        defaultItemPlanoAuditoriaShouldNotBeFound("dataFim.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByItemAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        ItemAuditoria itemAuditoria = ItemAuditoriaResourceIT.createEntity(em);
        em.persist(itemAuditoria);
        em.flush();
        itemPlanoAuditoria.setItemAuditoria(itemAuditoria);
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Long itemAuditoriaId = itemAuditoria.getId();

        // Get all the itemPlanoAuditoriaList where itemAuditoria equals to itemAuditoriaId
        defaultItemPlanoAuditoriaShouldBeFound("itemAuditoriaId.equals=" + itemAuditoriaId);

        // Get all the itemPlanoAuditoriaList where itemAuditoria equals to itemAuditoriaId + 1
        defaultItemPlanoAuditoriaShouldNotBeFound("itemAuditoriaId.equals=" + (itemAuditoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        itemPlanoAuditoria.addAnexo(anexo);
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Long anexoId = anexo.getId();

        // Get all the itemPlanoAuditoriaList where anexo equals to anexoId
        defaultItemPlanoAuditoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the itemPlanoAuditoriaList where anexo equals to anexoId + 1
        defaultItemPlanoAuditoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByPlanoIsEqualToSomething() throws Exception {
        // Get already existing entity
        PlanoAuditoria plano = itemPlanoAuditoria.getPlano();
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Long planoId = plano.getId();

        // Get all the itemPlanoAuditoriaList where plano equals to planoId
        defaultItemPlanoAuditoriaShouldBeFound("planoId.equals=" + planoId);

        // Get all the itemPlanoAuditoriaList where plano equals to planoId + 1
        defaultItemPlanoAuditoriaShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemPlanoAuditoriaShouldBeFound(String filter) throws Exception {
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPlanoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));

        // Check, that the count call also returns 1
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemPlanoAuditoriaShouldNotBeFound(String filter) throws Exception {
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemPlanoAuditoria() throws Exception {
        // Get the itemPlanoAuditoria
        restItemPlanoAuditoriaMockMvc.perform(get("/api/item-plano-auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPlanoAuditoria() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaService.save(itemPlanoAuditoria);

        int databaseSizeBeforeUpdate = itemPlanoAuditoriaRepository.findAll().size();

        // Update the itemPlanoAuditoria
        ItemPlanoAuditoria updatedItemPlanoAuditoria = itemPlanoAuditoriaRepository.findById(itemPlanoAuditoria.getId()).get();
        // Disconnect from session so that the updates on updatedItemPlanoAuditoria are not directly saved in db
        em.detach(updatedItemPlanoAuditoria);
        updatedItemPlanoAuditoria
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .modalidade(UPDATED_MODALIDADE)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);

        restItemPlanoAuditoriaMockMvc.perform(put("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemPlanoAuditoria)))
            .andExpect(status().isOk());

        // Validate the ItemPlanoAuditoria in the database
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
        ItemPlanoAuditoria testItemPlanoAuditoria = itemPlanoAuditoriaList.get(itemPlanoAuditoriaList.size() - 1);
        assertThat(testItemPlanoAuditoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testItemPlanoAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testItemPlanoAuditoria.getModalidade()).isEqualTo(UPDATED_MODALIDADE);
        assertThat(testItemPlanoAuditoria.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testItemPlanoAuditoria.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPlanoAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = itemPlanoAuditoriaRepository.findAll().size();

        // Create the ItemPlanoAuditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPlanoAuditoriaMockMvc.perform(put("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPlanoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPlanoAuditoria in the database
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPlanoAuditoria() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaService.save(itemPlanoAuditoria);

        int databaseSizeBeforeDelete = itemPlanoAuditoriaRepository.findAll().size();

        // Delete the itemPlanoAuditoria
        restItemPlanoAuditoriaMockMvc.perform(delete("/api/item-plano-auditorias/{id}", itemPlanoAuditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
