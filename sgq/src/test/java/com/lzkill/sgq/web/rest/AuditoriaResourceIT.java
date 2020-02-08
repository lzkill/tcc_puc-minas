package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.repository.AuditoriaRepository;
import com.lzkill.sgq.service.AuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.AuditoriaCriteria;
import com.lzkill.sgq.service.AuditoriaQueryService;

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
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lzkill.sgq.domain.enumeration.TipoAuditoria;
/**
 * Integration tests for the {@link AuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class AuditoriaResourceIT {

    private static final TipoAuditoria DEFAULT_TIPO = TipoAuditoria.INTERNA;
    private static final TipoAuditoria UPDATED_TIPO = TipoAuditoria.EXTERNA;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private AuditoriaQueryService auditoriaQueryService;

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

    private MockMvc restAuditoriaMockMvc;

    private Auditoria auditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditoriaResource auditoriaResource = new AuditoriaResource(auditoriaService, auditoriaQueryService);
        this.restAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(auditoriaResource)
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
    public static Auditoria createEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .tipo(DEFAULT_TIPO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return auditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditoria createUpdatedEntity(EntityManager em) {
        Auditoria auditoria = new Auditoria()
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        return auditoria;
    }

    @BeforeEach
    public void initTest() {
        auditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditoria() throws Exception {
        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();

        // Create the Auditoria
        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isCreated());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAuditoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditoriaRepository.findAll().size();

        // Create the Auditoria with an existing ID
        auditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setTipo(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaRepository.findAll().size();
        // set the field null
        auditoria.setTitulo(null);

        // Create the Auditoria, which fails.

        restAuditoriaMockMvc.perform(post("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuditorias() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getAuditoria() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get the auditoria
        restAuditoriaMockMvc.perform(get("/api/auditorias/{id}", auditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditoria.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        Long id = auditoria.getId();

        defaultAuditoriaShouldBeFound("id.equals=" + id);
        defaultAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where tipo equals to DEFAULT_TIPO
        defaultAuditoriaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the auditoriaList where tipo equals to UPDATED_TIPO
        defaultAuditoriaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where tipo not equals to DEFAULT_TIPO
        defaultAuditoriaShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the auditoriaList where tipo not equals to UPDATED_TIPO
        defaultAuditoriaShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultAuditoriaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the auditoriaList where tipo equals to UPDATED_TIPO
        defaultAuditoriaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where tipo is not null
        defaultAuditoriaShouldBeFound("tipo.specified=true");

        // Get all the auditoriaList where tipo is null
        defaultAuditoriaShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo equals to DEFAULT_TITULO
        defaultAuditoriaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo equals to UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo not equals to DEFAULT_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo not equals to UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the auditoriaList where titulo equals to UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo is not null
        defaultAuditoriaShouldBeFound("titulo.specified=true");

        // Get all the auditoriaList where titulo is null
        defaultAuditoriaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuditoriasByTituloContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo contains DEFAULT_TITULO
        defaultAuditoriaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo contains UPDATED_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllAuditoriasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);

        // Get all the auditoriaList where titulo does not contain DEFAULT_TITULO
        defaultAuditoriaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the auditoriaList where titulo does not contain UPDATED_TITULO
        defaultAuditoriaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllAuditoriasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        auditoriaRepository.saveAndFlush(auditoria);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        auditoria.setProcesso(processo);
        auditoriaRepository.saveAndFlush(auditoria);
        Long processoId = processo.getId();

        // Get all the auditoriaList where processo equals to processoId
        defaultAuditoriaShouldBeFound("processoId.equals=" + processoId);

        // Get all the auditoriaList where processo equals to processoId + 1
        defaultAuditoriaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditoriaShouldBeFound(String filter) throws Exception {
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restAuditoriaMockMvc.perform(get("/api/auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditoriaShouldNotBeFound(String filter) throws Exception {
        restAuditoriaMockMvc.perform(get("/api/auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditoriaMockMvc.perform(get("/api/auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAuditoria() throws Exception {
        // Get the auditoria
        restAuditoriaMockMvc.perform(get("/api/auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditoria() throws Exception {
        // Initialize the database
        auditoriaService.save(auditoria);

        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Update the auditoria
        Auditoria updatedAuditoria = auditoriaRepository.findById(auditoria.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoria are not directly saved in db
        em.detach(updatedAuditoria);
        updatedAuditoria
            .tipo(UPDATED_TIPO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restAuditoriaMockMvc.perform(put("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditoria)))
            .andExpect(status().isOk());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
        Auditoria testAuditoria = auditoriaList.get(auditoriaList.size() - 1);
        assertThat(testAuditoria.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAuditoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaRepository.findAll().size();

        // Create the Auditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaMockMvc.perform(put("/api/auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoria)))
            .andExpect(status().isBadRequest());

        // Validate the Auditoria in the database
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditoria() throws Exception {
        // Initialize the database
        auditoriaService.save(auditoria);

        int databaseSizeBeforeDelete = auditoriaRepository.findAll().size();

        // Delete the auditoria
        restAuditoriaMockMvc.perform(delete("/api/auditorias/{id}", auditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auditoria> auditoriaList = auditoriaRepository.findAll();
        assertThat(auditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
