package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.repository.ProcessoRepository;
import com.lzkill.sgq.service.ProcessoService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ProcessoCriteria;
import com.lzkill.sgq.service.ProcessoQueryService;

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

/**
 * Integration tests for the {@link ProcessoResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ProcessoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProcessoQueryService processoQueryService;

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

    private MockMvc restProcessoMockMvc;

    private Processo processo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessoResource processoResource = new ProcessoResource(processoService, processoQueryService);
        this.restProcessoMockMvc = MockMvcBuilders.standaloneSetup(processoResource)
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
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        processo.setSetor(setor);
        return processo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createUpdatedEntity(EntityManager em) {
        Processo processo = new Processo()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createUpdatedEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        processo.setSetor(setor);
        return processo;
    }

    @BeforeEach
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProcesso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo with an existing ID
        processo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setTitulo(null);

        // Create the Processo, which fails.

        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc.perform(get("/api/processos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getProcessosByIdFiltering() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        Long id = processo.getId();

        defaultProcessoShouldBeFound("id.equals=" + id);
        defaultProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProcessosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo equals to DEFAULT_TITULO
        defaultProcessoShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the processoList where titulo equals to UPDATED_TITULO
        defaultProcessoShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProcessosByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo not equals to DEFAULT_TITULO
        defaultProcessoShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the processoList where titulo not equals to UPDATED_TITULO
        defaultProcessoShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProcessosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultProcessoShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the processoList where titulo equals to UPDATED_TITULO
        defaultProcessoShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProcessosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo is not null
        defaultProcessoShouldBeFound("titulo.specified=true");

        // Get all the processoList where titulo is null
        defaultProcessoShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessosByTituloContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo contains DEFAULT_TITULO
        defaultProcessoShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the processoList where titulo contains UPDATED_TITULO
        defaultProcessoShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllProcessosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where titulo does not contain DEFAULT_TITULO
        defaultProcessoShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the processoList where titulo does not contain UPDATED_TITULO
        defaultProcessoShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllProcessosByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        processo.setAnexo(anexo);
        processoRepository.saveAndFlush(processo);
        Long anexoId = anexo.getId();

        // Get all the processoList where anexo equals to anexoId
        defaultProcessoShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the processoList where anexo equals to anexoId + 1
        defaultProcessoShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllProcessosBySetorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Setor setor = processo.getSetor();
        processoRepository.saveAndFlush(processo);
        Long setorId = setor.getId();

        // Get all the processoList where setor equals to setorId
        defaultProcessoShouldBeFound("setorId.equals=" + setorId);

        // Get all the processoList where setor equals to setorId + 1
        defaultProcessoShouldNotBeFound("setorId.equals=" + (setorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoShouldBeFound(String filter) throws Exception {
        restProcessoMockMvc.perform(get("/api/processos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restProcessoMockMvc.perform(get("/api/processos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoShouldNotBeFound(String filter) throws Exception {
        restProcessoMockMvc.perform(get("/api/processos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoMockMvc.perform(get("/api/processos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesso() throws Exception {
        // Initialize the database
        processoService.save(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcesso)))
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProcesso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Create the Processo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcesso() throws Exception {
        // Initialize the database
        processoService.save(processo);

        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Delete the processo
        restProcessoMockMvc.perform(delete("/api/processos/{id}", processo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
