package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.PlanoAuditoriaRepository;
import com.lzkill.sgq.service.PlanoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.PlanoAuditoriaCriteria;
import com.lzkill.sgq.service.PlanoAuditoriaQueryService;

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
import java.util.ArrayList;
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlanoAuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class PlanoAuditoriaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADO = false;
    private static final Boolean UPDATED_HABILITADO = true;

    @Autowired
    private PlanoAuditoriaRepository planoAuditoriaRepository;

    @Mock
    private PlanoAuditoriaRepository planoAuditoriaRepositoryMock;

    @Mock
    private PlanoAuditoriaService planoAuditoriaServiceMock;

    @Autowired
    private PlanoAuditoriaService planoAuditoriaService;

    @Autowired
    private PlanoAuditoriaQueryService planoAuditoriaQueryService;

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

    private MockMvc restPlanoAuditoriaMockMvc;

    private PlanoAuditoria planoAuditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoAuditoriaResource planoAuditoriaResource = new PlanoAuditoriaResource(planoAuditoriaService, planoAuditoriaQueryService);
        this.restPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(planoAuditoriaResource)
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
    public static PlanoAuditoria createEntity(EntityManager em) {
        PlanoAuditoria planoAuditoria = new PlanoAuditoria()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .habilitado(DEFAULT_HABILITADO);
        return planoAuditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAuditoria createUpdatedEntity(EntityManager em) {
        PlanoAuditoria planoAuditoria = new PlanoAuditoria()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);
        return planoAuditoria;
    }

    @BeforeEach
    public void initTest() {
        planoAuditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoAuditoria() throws Exception {
        int databaseSizeBeforeCreate = planoAuditoriaRepository.findAll().size();

        // Create the PlanoAuditoria
        restPlanoAuditoriaMockMvc.perform(post("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoAuditoria)))
            .andExpect(status().isCreated());

        // Validate the PlanoAuditoria in the database
        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoAuditoria testPlanoAuditoria = planoAuditoriaList.get(planoAuditoriaList.size() - 1);
        assertThat(testPlanoAuditoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPlanoAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoAuditoria.isHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    public void createPlanoAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoAuditoriaRepository.findAll().size();

        // Create the PlanoAuditoria with an existing ID
        planoAuditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoAuditoriaMockMvc.perform(post("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoAuditoria in the database
        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAuditoriaRepository.findAll().size();
        // set the field null
        planoAuditoria.setTitulo(null);

        // Create the PlanoAuditoria, which fails.

        restPlanoAuditoriaMockMvc.perform(post("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoAuditoria)))
            .andExpect(status().isBadRequest());

        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilitadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAuditoriaRepository.findAll().size();
        // set the field null
        planoAuditoria.setHabilitado(null);

        // Create the PlanoAuditoria, which fails.

        restPlanoAuditoriaMockMvc.perform(post("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoAuditoria)))
            .andExpect(status().isBadRequest());

        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditorias() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPlanoAuditoriasWithEagerRelationshipsIsEnabled() throws Exception {
        PlanoAuditoriaResource planoAuditoriaResource = new PlanoAuditoriaResource(planoAuditoriaServiceMock, planoAuditoriaQueryService);
        when(planoAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(planoAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias?eagerload=true"))
        .andExpect(status().isOk());

        verify(planoAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlanoAuditoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        PlanoAuditoriaResource planoAuditoriaResource = new PlanoAuditoriaResource(planoAuditoriaServiceMock, planoAuditoriaQueryService);
            when(planoAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPlanoAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(planoAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias?eagerload=true"))
        .andExpect(status().isOk());

            verify(planoAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPlanoAuditoria() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get the planoAuditoria
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias/{id}", planoAuditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoAuditoria.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.habilitado").value(DEFAULT_HABILITADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getPlanoAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        Long id = planoAuditoria.getId();

        defaultPlanoAuditoriaShouldBeFound("id.equals=" + id);
        defaultPlanoAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo equals to DEFAULT_TITULO
        defaultPlanoAuditoriaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the planoAuditoriaList where titulo equals to UPDATED_TITULO
        defaultPlanoAuditoriaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo not equals to DEFAULT_TITULO
        defaultPlanoAuditoriaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the planoAuditoriaList where titulo not equals to UPDATED_TITULO
        defaultPlanoAuditoriaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultPlanoAuditoriaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the planoAuditoriaList where titulo equals to UPDATED_TITULO
        defaultPlanoAuditoriaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo is not null
        defaultPlanoAuditoriaShouldBeFound("titulo.specified=true");

        // Get all the planoAuditoriaList where titulo is null
        defaultPlanoAuditoriaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloContainsSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo contains DEFAULT_TITULO
        defaultPlanoAuditoriaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the planoAuditoriaList where titulo contains UPDATED_TITULO
        defaultPlanoAuditoriaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where titulo does not contain DEFAULT_TITULO
        defaultPlanoAuditoriaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the planoAuditoriaList where titulo does not contain UPDATED_TITULO
        defaultPlanoAuditoriaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllPlanoAuditoriasByHabilitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where habilitado equals to DEFAULT_HABILITADO
        defaultPlanoAuditoriaShouldBeFound("habilitado.equals=" + DEFAULT_HABILITADO);

        // Get all the planoAuditoriaList where habilitado equals to UPDATED_HABILITADO
        defaultPlanoAuditoriaShouldNotBeFound("habilitado.equals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByHabilitadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where habilitado not equals to DEFAULT_HABILITADO
        defaultPlanoAuditoriaShouldNotBeFound("habilitado.notEquals=" + DEFAULT_HABILITADO);

        // Get all the planoAuditoriaList where habilitado not equals to UPDATED_HABILITADO
        defaultPlanoAuditoriaShouldBeFound("habilitado.notEquals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByHabilitadoIsInShouldWork() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where habilitado in DEFAULT_HABILITADO or UPDATED_HABILITADO
        defaultPlanoAuditoriaShouldBeFound("habilitado.in=" + DEFAULT_HABILITADO + "," + UPDATED_HABILITADO);

        // Get all the planoAuditoriaList where habilitado equals to UPDATED_HABILITADO
        defaultPlanoAuditoriaShouldNotBeFound("habilitado.in=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByHabilitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);

        // Get all the planoAuditoriaList where habilitado is not null
        defaultPlanoAuditoriaShouldBeFound("habilitado.specified=true");

        // Get all the planoAuditoriaList where habilitado is null
        defaultPlanoAuditoriaShouldNotBeFound("habilitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoAuditoriasByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);
        ItemPlanoAuditoria item = ItemPlanoAuditoriaResourceIT.createEntity(em);
        em.persist(item);
        em.flush();
        planoAuditoria.addItem(item);
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);
        Long itemId = item.getId();

        // Get all the planoAuditoriaList where item equals to itemId
        defaultPlanoAuditoriaShouldBeFound("itemId.equals=" + itemId);

        // Get all the planoAuditoriaList where item equals to itemId + 1
        defaultPlanoAuditoriaShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoAuditoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        planoAuditoria.addAnexo(anexo);
        planoAuditoriaRepository.saveAndFlush(planoAuditoria);
        Long anexoId = anexo.getId();

        // Get all the planoAuditoriaList where anexo equals to anexoId
        defaultPlanoAuditoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the planoAuditoriaList where anexo equals to anexoId + 1
        defaultPlanoAuditoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoAuditoriaShouldBeFound(String filter) throws Exception {
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));

        // Check, that the count call also returns 1
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoAuditoriaShouldNotBeFound(String filter) throws Exception {
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPlanoAuditoria() throws Exception {
        // Get the planoAuditoria
        restPlanoAuditoriaMockMvc.perform(get("/api/plano-auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoAuditoria() throws Exception {
        // Initialize the database
        planoAuditoriaService.save(planoAuditoria);

        int databaseSizeBeforeUpdate = planoAuditoriaRepository.findAll().size();

        // Update the planoAuditoria
        PlanoAuditoria updatedPlanoAuditoria = planoAuditoriaRepository.findById(planoAuditoria.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoAuditoria are not directly saved in db
        em.detach(updatedPlanoAuditoria);
        updatedPlanoAuditoria
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);

        restPlanoAuditoriaMockMvc.perform(put("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanoAuditoria)))
            .andExpect(status().isOk());

        // Validate the PlanoAuditoria in the database
        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
        PlanoAuditoria testPlanoAuditoria = planoAuditoriaList.get(planoAuditoriaList.size() - 1);
        assertThat(testPlanoAuditoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPlanoAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoAuditoria.isHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = planoAuditoriaRepository.findAll().size();

        // Create the PlanoAuditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAuditoriaMockMvc.perform(put("/api/plano-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoAuditoria in the database
        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoAuditoria() throws Exception {
        // Initialize the database
        planoAuditoriaService.save(planoAuditoria);

        int databaseSizeBeforeDelete = planoAuditoriaRepository.findAll().size();

        // Delete the planoAuditoria
        restPlanoAuditoriaMockMvc.perform(delete("/api/plano-auditorias/{id}", planoAuditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoAuditoria> planoAuditoriaList = planoAuditoriaRepository.findAll();
        assertThat(planoAuditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
