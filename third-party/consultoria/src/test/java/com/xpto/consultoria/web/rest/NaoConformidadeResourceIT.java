package com.xpto.consultoria.web.rest;

import com.xpto.consultoria.ConsultoriaApp;
import com.xpto.consultoria.domain.NaoConformidade;
import com.xpto.consultoria.domain.AcaoSGQ;
import com.xpto.consultoria.repository.NaoConformidadeRepository;
import com.xpto.consultoria.service.NaoConformidadeService;
import com.xpto.consultoria.web.rest.errors.ExceptionTranslator;
import com.xpto.consultoria.service.dto.NaoConformidadeCriteria;
import com.xpto.consultoria.service.NaoConformidadeQueryService;

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

import static com.xpto.consultoria.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NaoConformidadeResource} REST controller.
 */
@SpringBootTest(classes = ConsultoriaApp.class)
public class NaoConformidadeResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROCEDENTE = false;
    private static final Boolean UPDATED_PROCEDENTE = true;

    private static final String DEFAULT_CAUSA = "AAAAAAAAAA";
    private static final String UPDATED_CAUSA = "BBBBBBBBBB";

    private static final Instant DEFAULT_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOVO_PRAZO_CONCLUSAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOVO_PRAZO_CONCLUSAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NaoConformidadeRepository naoConformidadeRepository;

    @Autowired
    private NaoConformidadeService naoConformidadeService;

    @Autowired
    private NaoConformidadeQueryService naoConformidadeQueryService;

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

    private MockMvc restNaoConformidadeMockMvc;

    private NaoConformidade naoConformidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NaoConformidadeResource naoConformidadeResource = new NaoConformidadeResource(naoConformidadeService, naoConformidadeQueryService);
        this.restNaoConformidadeMockMvc = MockMvcBuilders.standaloneSetup(naoConformidadeResource)
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
    public static NaoConformidade createEntity(EntityManager em) {
        NaoConformidade naoConformidade = new NaoConformidade()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .procedente(DEFAULT_PROCEDENTE)
            .causa(DEFAULT_CAUSA)
            .prazoConclusao(DEFAULT_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(DEFAULT_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(DEFAULT_DATA_REGISTRO);
        return naoConformidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaoConformidade createUpdatedEntity(EntityManager em) {
        NaoConformidade naoConformidade = new NaoConformidade()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .procedente(UPDATED_PROCEDENTE)
            .causa(UPDATED_CAUSA)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO);
        return naoConformidade;
    }

    @BeforeEach
    public void initTest() {
        naoConformidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaoConformidade() throws Exception {
        int databaseSizeBeforeCreate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade
        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isCreated());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeCreate + 1);
        NaoConformidade testNaoConformidade = naoConformidadeList.get(naoConformidadeList.size() - 1);
        assertThat(testNaoConformidade.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNaoConformidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNaoConformidade.isProcedente()).isEqualTo(DEFAULT_PROCEDENTE);
        assertThat(testNaoConformidade.getCausa()).isEqualTo(DEFAULT_CAUSA);
        assertThat(testNaoConformidade.getPrazoConclusao()).isEqualTo(DEFAULT_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getNovoPrazoConclusao()).isEqualTo(DEFAULT_NOVO_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void createNaoConformidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade with an existing ID
        naoConformidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setTitulo(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = naoConformidadeRepository.findAll().size();
        // set the field null
        naoConformidade.setDataRegistro(null);

        // Create the NaoConformidade, which fails.

        restNaoConformidadeMockMvc.perform(post("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaoConformidades() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naoConformidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].procedente").value(hasItem(DEFAULT_PROCEDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].causa").value(hasItem(DEFAULT_CAUSA.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())));
    }
    
    @Test
    @Transactional
    public void getNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get the naoConformidade
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/{id}", naoConformidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(naoConformidade.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.procedente").value(DEFAULT_PROCEDENTE.booleanValue()))
            .andExpect(jsonPath("$.causa").value(DEFAULT_CAUSA.toString()))
            .andExpect(jsonPath("$.prazoConclusao").value(DEFAULT_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.novoPrazoConclusao").value(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString()))
            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()));
    }


    @Test
    @Transactional
    public void getNaoConformidadesByIdFiltering() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        Long id = naoConformidade.getId();

        defaultNaoConformidadeShouldBeFound("id.equals=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.notEquals=" + id);

        defaultNaoConformidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultNaoConformidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNaoConformidadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo equals to DEFAULT_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo equals to UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo not equals to DEFAULT_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo not equals to UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the naoConformidadeList where titulo equals to UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo is not null
        defaultNaoConformidadeShouldBeFound("titulo.specified=true");

        // Get all the naoConformidadeList where titulo is null
        defaultNaoConformidadeShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNaoConformidadesByTituloContainsSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo contains DEFAULT_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo contains UPDATED_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where titulo does not contain DEFAULT_TITULO
        defaultNaoConformidadeShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the naoConformidadeList where titulo does not contain UPDATED_TITULO
        defaultNaoConformidadeShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllNaoConformidadesByProcedenteIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where procedente equals to DEFAULT_PROCEDENTE
        defaultNaoConformidadeShouldBeFound("procedente.equals=" + DEFAULT_PROCEDENTE);

        // Get all the naoConformidadeList where procedente equals to UPDATED_PROCEDENTE
        defaultNaoConformidadeShouldNotBeFound("procedente.equals=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByProcedenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where procedente not equals to DEFAULT_PROCEDENTE
        defaultNaoConformidadeShouldNotBeFound("procedente.notEquals=" + DEFAULT_PROCEDENTE);

        // Get all the naoConformidadeList where procedente not equals to UPDATED_PROCEDENTE
        defaultNaoConformidadeShouldBeFound("procedente.notEquals=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByProcedenteIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where procedente in DEFAULT_PROCEDENTE or UPDATED_PROCEDENTE
        defaultNaoConformidadeShouldBeFound("procedente.in=" + DEFAULT_PROCEDENTE + "," + UPDATED_PROCEDENTE);

        // Get all the naoConformidadeList where procedente equals to UPDATED_PROCEDENTE
        defaultNaoConformidadeShouldNotBeFound("procedente.in=" + UPDATED_PROCEDENTE);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByProcedenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where procedente is not null
        defaultNaoConformidadeShouldBeFound("procedente.specified=true");

        // Get all the naoConformidadeList where procedente is null
        defaultNaoConformidadeShouldNotBeFound("procedente.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao equals to DEFAULT_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.equals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.equals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao not equals to DEFAULT_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.notEquals=" + DEFAULT_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao not equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.notEquals=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao in DEFAULT_PRAZO_CONCLUSAO or UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("prazoConclusao.in=" + DEFAULT_PRAZO_CONCLUSAO + "," + UPDATED_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where prazoConclusao equals to UPDATED_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.in=" + UPDATED_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where prazoConclusao is not null
        defaultNaoConformidadeShouldBeFound("prazoConclusao.specified=true");

        // Get all the naoConformidadeList where prazoConclusao is null
        defaultNaoConformidadeShouldNotBeFound("prazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.equals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.equals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao not equals to DEFAULT_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.notEquals=" + DEFAULT_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao not equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.notEquals=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao in DEFAULT_NOVO_PRAZO_CONCLUSAO or UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.in=" + DEFAULT_NOVO_PRAZO_CONCLUSAO + "," + UPDATED_NOVO_PRAZO_CONCLUSAO);

        // Get all the naoConformidadeList where novoPrazoConclusao equals to UPDATED_NOVO_PRAZO_CONCLUSAO
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.in=" + UPDATED_NOVO_PRAZO_CONCLUSAO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByNovoPrazoConclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where novoPrazoConclusao is not null
        defaultNaoConformidadeShouldBeFound("novoPrazoConclusao.specified=true");

        // Get all the naoConformidadeList where novoPrazoConclusao is null
        defaultNaoConformidadeShouldNotBeFound("novoPrazoConclusao.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro equals to DEFAULT_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.equals=" + DEFAULT_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.equals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro not equals to DEFAULT_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.notEquals=" + DEFAULT_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro not equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.notEquals=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro in DEFAULT_DATA_REGISTRO or UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldBeFound("dataRegistro.in=" + DEFAULT_DATA_REGISTRO + "," + UPDATED_DATA_REGISTRO);

        // Get all the naoConformidadeList where dataRegistro equals to UPDATED_DATA_REGISTRO
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.in=" + UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByDataRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);

        // Get all the naoConformidadeList where dataRegistro is not null
        defaultNaoConformidadeShouldBeFound("dataRegistro.specified=true");

        // Get all the naoConformidadeList where dataRegistro is null
        defaultNaoConformidadeShouldNotBeFound("dataRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllNaoConformidadesByAcaoSGQIsEqualToSomething() throws Exception {
        // Initialize the database
        naoConformidadeRepository.saveAndFlush(naoConformidade);
        AcaoSGQ acaoSGQ = AcaoSGQResourceIT.createEntity(em);
        em.persist(acaoSGQ);
        em.flush();
        naoConformidade.addAcaoSGQ(acaoSGQ);
        naoConformidadeRepository.saveAndFlush(naoConformidade);
        Long acaoSGQId = acaoSGQ.getId();

        // Get all the naoConformidadeList where acaoSGQ equals to acaoSGQId
        defaultNaoConformidadeShouldBeFound("acaoSGQId.equals=" + acaoSGQId);

        // Get all the naoConformidadeList where acaoSGQ equals to acaoSGQId + 1
        defaultNaoConformidadeShouldNotBeFound("acaoSGQId.equals=" + (acaoSGQId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNaoConformidadeShouldBeFound(String filter) throws Exception {
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naoConformidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].procedente").value(hasItem(DEFAULT_PROCEDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].causa").value(hasItem(DEFAULT_CAUSA.toString())))
            .andExpect(jsonPath("$.[*].prazoConclusao").value(hasItem(DEFAULT_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].novoPrazoConclusao").value(hasItem(DEFAULT_NOVO_PRAZO_CONCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())));

        // Check, that the count call also returns 1
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNaoConformidadeShouldNotBeFound(String filter) throws Exception {
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNaoConformidade() throws Exception {
        // Get the naoConformidade
        restNaoConformidadeMockMvc.perform(get("/api/nao-conformidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeService.save(naoConformidade);

        int databaseSizeBeforeUpdate = naoConformidadeRepository.findAll().size();

        // Update the naoConformidade
        NaoConformidade updatedNaoConformidade = naoConformidadeRepository.findById(naoConformidade.getId()).get();
        // Disconnect from session so that the updates on updatedNaoConformidade are not directly saved in db
        em.detach(updatedNaoConformidade);
        updatedNaoConformidade
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .procedente(UPDATED_PROCEDENTE)
            .causa(UPDATED_CAUSA)
            .prazoConclusao(UPDATED_PRAZO_CONCLUSAO)
            .novoPrazoConclusao(UPDATED_NOVO_PRAZO_CONCLUSAO)
            .dataRegistro(UPDATED_DATA_REGISTRO);

        restNaoConformidadeMockMvc.perform(put("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaoConformidade)))
            .andExpect(status().isOk());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeUpdate);
        NaoConformidade testNaoConformidade = naoConformidadeList.get(naoConformidadeList.size() - 1);
        assertThat(testNaoConformidade.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNaoConformidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNaoConformidade.isProcedente()).isEqualTo(UPDATED_PROCEDENTE);
        assertThat(testNaoConformidade.getCausa()).isEqualTo(UPDATED_CAUSA);
        assertThat(testNaoConformidade.getPrazoConclusao()).isEqualTo(UPDATED_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getNovoPrazoConclusao()).isEqualTo(UPDATED_NOVO_PRAZO_CONCLUSAO);
        assertThat(testNaoConformidade.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingNaoConformidade() throws Exception {
        int databaseSizeBeforeUpdate = naoConformidadeRepository.findAll().size();

        // Create the NaoConformidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaoConformidadeMockMvc.perform(put("/api/nao-conformidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naoConformidade)))
            .andExpect(status().isBadRequest());

        // Validate the NaoConformidade in the database
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNaoConformidade() throws Exception {
        // Initialize the database
        naoConformidadeService.save(naoConformidade);

        int databaseSizeBeforeDelete = naoConformidadeRepository.findAll().size();

        // Delete the naoConformidade
        restNaoConformidadeMockMvc.perform(delete("/api/nao-conformidades/{id}", naoConformidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NaoConformidade> naoConformidadeList = naoConformidadeRepository.findAll();
        assertThat(naoConformidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
