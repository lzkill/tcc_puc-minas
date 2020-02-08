package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.repository.ItemPlanoAuditoriaRepository;
import com.lzkill.sgq.service.ItemPlanoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ItemPlanoAuditoriaCriteria;
import com.lzkill.sgq.service.ItemPlanoAuditoriaQueryService;

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

/**
 * Integration tests for the {@link ItemPlanoAuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ItemPlanoAuditoriaResourceIT {

    private static final Integer DEFAULT_ID_USUARIO_RESPONSAVEL = 1;
    private static final Integer UPDATED_ID_USUARIO_RESPONSAVEL = 2;
    private static final Integer SMALLER_ID_USUARIO_RESPONSAVEL = 1 - 1;

    private static final Instant DEFAULT_DATA_AUDITORIA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_AUDITORIA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository;

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
            .idUsuarioResponsavel(DEFAULT_ID_USUARIO_RESPONSAVEL)
            .dataAuditoria(DEFAULT_DATA_AUDITORIA);
        // Add required entity
        Auditoria auditoria;
        if (TestUtil.findAll(em, Auditoria.class).isEmpty()) {
            auditoria = AuditoriaResourceIT.createEntity(em);
            em.persist(auditoria);
            em.flush();
        } else {
            auditoria = TestUtil.findAll(em, Auditoria.class).get(0);
        }
        itemPlanoAuditoria.setAuditoria(auditoria);
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
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .dataAuditoria(UPDATED_DATA_AUDITORIA);
        // Add required entity
        Auditoria auditoria;
        if (TestUtil.findAll(em, Auditoria.class).isEmpty()) {
            auditoria = AuditoriaResourceIT.createUpdatedEntity(em);
            em.persist(auditoria);
            em.flush();
        } else {
            auditoria = TestUtil.findAll(em, Auditoria.class).get(0);
        }
        itemPlanoAuditoria.setAuditoria(auditoria);
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
        assertThat(testItemPlanoAuditoria.getIdUsuarioResponsavel()).isEqualTo(DEFAULT_ID_USUARIO_RESPONSAVEL);
        assertThat(testItemPlanoAuditoria.getDataAuditoria()).isEqualTo(DEFAULT_DATA_AUDITORIA);
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
    public void checkIdUsuarioResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPlanoAuditoriaRepository.findAll().size();
        // set the field null
        itemPlanoAuditoria.setIdUsuarioResponsavel(null);

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
    public void checkDataAuditoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPlanoAuditoriaRepository.findAll().size();
        // set the field null
        itemPlanoAuditoria.setDataAuditoria(null);

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
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].dataAuditoria").value(hasItem(DEFAULT_DATA_AUDITORIA.toString())));
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
            .andExpect(jsonPath("$.idUsuarioResponsavel").value(DEFAULT_ID_USUARIO_RESPONSAVEL))
            .andExpect(jsonPath("$.dataAuditoria").value(DEFAULT_DATA_AUDITORIA.toString()));
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
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.equals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.equals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel not equals to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.notEquals=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel not equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.notEquals=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel in DEFAULT_ID_USUARIO_RESPONSAVEL or UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.in=" + DEFAULT_ID_USUARIO_RESPONSAVEL + "," + UPDATED_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel equals to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.in=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is not null
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.specified=true");

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is null
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is greater than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is greater than or equal to UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.greaterThanOrEqual=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is less than or equal to DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.lessThanOrEqual=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is less than or equal to SMALLER_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.lessThanOrEqual=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsLessThanSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is less than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.lessThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is less than UPDATED_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.lessThan=" + UPDATED_ID_USUARIO_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByIdUsuarioResponsavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is greater than DEFAULT_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldNotBeFound("idUsuarioResponsavel.greaterThan=" + DEFAULT_ID_USUARIO_RESPONSAVEL);

        // Get all the itemPlanoAuditoriaList where idUsuarioResponsavel is greater than SMALLER_ID_USUARIO_RESPONSAVEL
        defaultItemPlanoAuditoriaShouldBeFound("idUsuarioResponsavel.greaterThan=" + SMALLER_ID_USUARIO_RESPONSAVEL);
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataAuditoria equals to DEFAULT_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldBeFound("dataAuditoria.equals=" + DEFAULT_DATA_AUDITORIA);

        // Get all the itemPlanoAuditoriaList where dataAuditoria equals to UPDATED_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldNotBeFound("dataAuditoria.equals=" + UPDATED_DATA_AUDITORIA);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataAuditoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataAuditoria not equals to DEFAULT_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldNotBeFound("dataAuditoria.notEquals=" + DEFAULT_DATA_AUDITORIA);

        // Get all the itemPlanoAuditoriaList where dataAuditoria not equals to UPDATED_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldBeFound("dataAuditoria.notEquals=" + UPDATED_DATA_AUDITORIA);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataAuditoriaIsInShouldWork() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataAuditoria in DEFAULT_DATA_AUDITORIA or UPDATED_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldBeFound("dataAuditoria.in=" + DEFAULT_DATA_AUDITORIA + "," + UPDATED_DATA_AUDITORIA);

        // Get all the itemPlanoAuditoriaList where dataAuditoria equals to UPDATED_DATA_AUDITORIA
        defaultItemPlanoAuditoriaShouldNotBeFound("dataAuditoria.in=" + UPDATED_DATA_AUDITORIA);
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByDataAuditoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);

        // Get all the itemPlanoAuditoriaList where dataAuditoria is not null
        defaultItemPlanoAuditoriaShouldBeFound("dataAuditoria.specified=true");

        // Get all the itemPlanoAuditoriaList where dataAuditoria is null
        defaultItemPlanoAuditoriaShouldNotBeFound("dataAuditoria.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        itemPlanoAuditoria.setAnexo(anexo);
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Long anexoId = anexo.getId();

        // Get all the itemPlanoAuditoriaList where anexo equals to anexoId
        defaultItemPlanoAuditoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the itemPlanoAuditoriaList where anexo equals to anexoId + 1
        defaultItemPlanoAuditoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemPlanoAuditoriasByAuditoriaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Auditoria auditoria = itemPlanoAuditoria.getAuditoria();
        itemPlanoAuditoriaRepository.saveAndFlush(itemPlanoAuditoria);
        Long auditoriaId = auditoria.getId();

        // Get all the itemPlanoAuditoriaList where auditoria equals to auditoriaId
        defaultItemPlanoAuditoriaShouldBeFound("auditoriaId.equals=" + auditoriaId);

        // Get all the itemPlanoAuditoriaList where auditoria equals to auditoriaId + 1
        defaultItemPlanoAuditoriaShouldNotBeFound("auditoriaId.equals=" + (auditoriaId + 1));
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
            .andExpect(jsonPath("$.[*].idUsuarioResponsavel").value(hasItem(DEFAULT_ID_USUARIO_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].dataAuditoria").value(hasItem(DEFAULT_DATA_AUDITORIA.toString())));

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
            .idUsuarioResponsavel(UPDATED_ID_USUARIO_RESPONSAVEL)
            .dataAuditoria(UPDATED_DATA_AUDITORIA);

        restItemPlanoAuditoriaMockMvc.perform(put("/api/item-plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemPlanoAuditoria)))
            .andExpect(status().isOk());

        // Validate the ItemPlanoAuditoria in the database
        List<ItemPlanoAuditoria> itemPlanoAuditoriaList = itemPlanoAuditoriaRepository.findAll();
        assertThat(itemPlanoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
        ItemPlanoAuditoria testItemPlanoAuditoria = itemPlanoAuditoriaList.get(itemPlanoAuditoriaList.size() - 1);
        assertThat(testItemPlanoAuditoria.getIdUsuarioResponsavel()).isEqualTo(UPDATED_ID_USUARIO_RESPONSAVEL);
        assertThat(testItemPlanoAuditoria.getDataAuditoria()).isEqualTo(UPDATED_DATA_AUDITORIA);
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
