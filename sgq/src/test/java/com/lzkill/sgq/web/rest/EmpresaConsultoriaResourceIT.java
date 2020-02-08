package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.EmpresaConsultoria;
import com.lzkill.sgq.repository.EmpresaConsultoriaRepository;
import com.lzkill.sgq.service.EmpresaConsultoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.EmpresaConsultoriaCriteria;
import com.lzkill.sgq.service.EmpresaConsultoriaQueryService;

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
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmpresaConsultoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class EmpresaConsultoriaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_URL_INTEGRACAO = "AAAAAAAAAA";
    private static final String UPDATED_URL_INTEGRACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_ACESSO = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_ACESSO = "BBBBBBBBBB";

    @Autowired
    private EmpresaConsultoriaRepository empresaConsultoriaRepository;

    @Autowired
    private EmpresaConsultoriaService empresaConsultoriaService;

    @Autowired
    private EmpresaConsultoriaQueryService empresaConsultoriaQueryService;

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

    private MockMvc restEmpresaConsultoriaMockMvc;

    private EmpresaConsultoria empresaConsultoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaConsultoriaResource empresaConsultoriaResource = new EmpresaConsultoriaResource(empresaConsultoriaService, empresaConsultoriaQueryService);
        this.restEmpresaConsultoriaMockMvc = MockMvcBuilders.standaloneSetup(empresaConsultoriaResource)
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
    public static EmpresaConsultoria createEntity(EntityManager em) {
        EmpresaConsultoria empresaConsultoria = new EmpresaConsultoria()
            .nome(DEFAULT_NOME)
            .urlIntegracao(DEFAULT_URL_INTEGRACAO)
            .tokenAcesso(DEFAULT_TOKEN_ACESSO);
        return empresaConsultoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpresaConsultoria createUpdatedEntity(EntityManager em) {
        EmpresaConsultoria empresaConsultoria = new EmpresaConsultoria()
            .nome(UPDATED_NOME)
            .urlIntegracao(UPDATED_URL_INTEGRACAO)
            .tokenAcesso(UPDATED_TOKEN_ACESSO);
        return empresaConsultoria;
    }

    @BeforeEach
    public void initTest() {
        empresaConsultoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresaConsultoria() throws Exception {
        int databaseSizeBeforeCreate = empresaConsultoriaRepository.findAll().size();

        // Create the EmpresaConsultoria
        restEmpresaConsultoriaMockMvc.perform(post("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isCreated());

        // Validate the EmpresaConsultoria in the database
        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeCreate + 1);
        EmpresaConsultoria testEmpresaConsultoria = empresaConsultoriaList.get(empresaConsultoriaList.size() - 1);
        assertThat(testEmpresaConsultoria.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpresaConsultoria.getUrlIntegracao()).isEqualTo(DEFAULT_URL_INTEGRACAO);
        assertThat(testEmpresaConsultoria.getTokenAcesso()).isEqualTo(DEFAULT_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void createEmpresaConsultoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaConsultoriaRepository.findAll().size();

        // Create the EmpresaConsultoria with an existing ID
        empresaConsultoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaConsultoriaMockMvc.perform(post("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isBadRequest());

        // Validate the EmpresaConsultoria in the database
        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaConsultoriaRepository.findAll().size();
        // set the field null
        empresaConsultoria.setNome(null);

        // Create the EmpresaConsultoria, which fails.

        restEmpresaConsultoriaMockMvc.perform(post("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isBadRequest());

        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIntegracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaConsultoriaRepository.findAll().size();
        // set the field null
        empresaConsultoria.setUrlIntegracao(null);

        // Create the EmpresaConsultoria, which fails.

        restEmpresaConsultoriaMockMvc.perform(post("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isBadRequest());

        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenAcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaConsultoriaRepository.findAll().size();
        // set the field null
        empresaConsultoria.setTokenAcesso(null);

        // Create the EmpresaConsultoria, which fails.

        restEmpresaConsultoriaMockMvc.perform(post("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isBadRequest());

        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultorias() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresaConsultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].urlIntegracao").value(hasItem(DEFAULT_URL_INTEGRACAO)))
            .andExpect(jsonPath("$.[*].tokenAcesso").value(hasItem(DEFAULT_TOKEN_ACESSO)));
    }
    
    @Test
    @Transactional
    public void getEmpresaConsultoria() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get the empresaConsultoria
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias/{id}", empresaConsultoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresaConsultoria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.urlIntegracao").value(DEFAULT_URL_INTEGRACAO))
            .andExpect(jsonPath("$.tokenAcesso").value(DEFAULT_TOKEN_ACESSO));
    }


    @Test
    @Transactional
    public void getEmpresaConsultoriasByIdFiltering() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        Long id = empresaConsultoria.getId();

        defaultEmpresaConsultoriaShouldBeFound("id.equals=" + id);
        defaultEmpresaConsultoriaShouldNotBeFound("id.notEquals=" + id);

        defaultEmpresaConsultoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpresaConsultoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpresaConsultoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpresaConsultoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome equals to DEFAULT_NOME
        defaultEmpresaConsultoriaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the empresaConsultoriaList where nome equals to UPDATED_NOME
        defaultEmpresaConsultoriaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome not equals to DEFAULT_NOME
        defaultEmpresaConsultoriaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the empresaConsultoriaList where nome not equals to UPDATED_NOME
        defaultEmpresaConsultoriaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEmpresaConsultoriaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the empresaConsultoriaList where nome equals to UPDATED_NOME
        defaultEmpresaConsultoriaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome is not null
        defaultEmpresaConsultoriaShouldBeFound("nome.specified=true");

        // Get all the empresaConsultoriaList where nome is null
        defaultEmpresaConsultoriaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome contains DEFAULT_NOME
        defaultEmpresaConsultoriaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the empresaConsultoriaList where nome contains UPDATED_NOME
        defaultEmpresaConsultoriaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where nome does not contain DEFAULT_NOME
        defaultEmpresaConsultoriaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the empresaConsultoriaList where nome does not contain UPDATED_NOME
        defaultEmpresaConsultoriaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao equals to DEFAULT_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.equals=" + DEFAULT_URL_INTEGRACAO);

        // Get all the empresaConsultoriaList where urlIntegracao equals to UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.equals=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao not equals to DEFAULT_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.notEquals=" + DEFAULT_URL_INTEGRACAO);

        // Get all the empresaConsultoriaList where urlIntegracao not equals to UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.notEquals=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao in DEFAULT_URL_INTEGRACAO or UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.in=" + DEFAULT_URL_INTEGRACAO + "," + UPDATED_URL_INTEGRACAO);

        // Get all the empresaConsultoriaList where urlIntegracao equals to UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.in=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao is not null
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.specified=true");

        // Get all the empresaConsultoriaList where urlIntegracao is null
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao contains DEFAULT_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.contains=" + DEFAULT_URL_INTEGRACAO);

        // Get all the empresaConsultoriaList where urlIntegracao contains UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.contains=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByUrlIntegracaoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where urlIntegracao does not contain DEFAULT_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldNotBeFound("urlIntegracao.doesNotContain=" + DEFAULT_URL_INTEGRACAO);

        // Get all the empresaConsultoriaList where urlIntegracao does not contain UPDATED_URL_INTEGRACAO
        defaultEmpresaConsultoriaShouldBeFound("urlIntegracao.doesNotContain=" + UPDATED_URL_INTEGRACAO);
    }


    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso equals to DEFAULT_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.equals=" + DEFAULT_TOKEN_ACESSO);

        // Get all the empresaConsultoriaList where tokenAcesso equals to UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.equals=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso not equals to DEFAULT_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.notEquals=" + DEFAULT_TOKEN_ACESSO);

        // Get all the empresaConsultoriaList where tokenAcesso not equals to UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.notEquals=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso in DEFAULT_TOKEN_ACESSO or UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.in=" + DEFAULT_TOKEN_ACESSO + "," + UPDATED_TOKEN_ACESSO);

        // Get all the empresaConsultoriaList where tokenAcesso equals to UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.in=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso is not null
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.specified=true");

        // Get all the empresaConsultoriaList where tokenAcesso is null
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso contains DEFAULT_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.contains=" + DEFAULT_TOKEN_ACESSO);

        // Get all the empresaConsultoriaList where tokenAcesso contains UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.contains=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllEmpresaConsultoriasByTokenAcessoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaConsultoriaRepository.saveAndFlush(empresaConsultoria);

        // Get all the empresaConsultoriaList where tokenAcesso does not contain DEFAULT_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldNotBeFound("tokenAcesso.doesNotContain=" + DEFAULT_TOKEN_ACESSO);

        // Get all the empresaConsultoriaList where tokenAcesso does not contain UPDATED_TOKEN_ACESSO
        defaultEmpresaConsultoriaShouldBeFound("tokenAcesso.doesNotContain=" + UPDATED_TOKEN_ACESSO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaConsultoriaShouldBeFound(String filter) throws Exception {
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresaConsultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].urlIntegracao").value(hasItem(DEFAULT_URL_INTEGRACAO)))
            .andExpect(jsonPath("$.[*].tokenAcesso").value(hasItem(DEFAULT_TOKEN_ACESSO)));

        // Check, that the count call also returns 1
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaConsultoriaShouldNotBeFound(String filter) throws Exception {
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmpresaConsultoria() throws Exception {
        // Get the empresaConsultoria
        restEmpresaConsultoriaMockMvc.perform(get("/api/empresa-consultorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresaConsultoria() throws Exception {
        // Initialize the database
        empresaConsultoriaService.save(empresaConsultoria);

        int databaseSizeBeforeUpdate = empresaConsultoriaRepository.findAll().size();

        // Update the empresaConsultoria
        EmpresaConsultoria updatedEmpresaConsultoria = empresaConsultoriaRepository.findById(empresaConsultoria.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresaConsultoria are not directly saved in db
        em.detach(updatedEmpresaConsultoria);
        updatedEmpresaConsultoria
            .nome(UPDATED_NOME)
            .urlIntegracao(UPDATED_URL_INTEGRACAO)
            .tokenAcesso(UPDATED_TOKEN_ACESSO);

        restEmpresaConsultoriaMockMvc.perform(put("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpresaConsultoria)))
            .andExpect(status().isOk());

        // Validate the EmpresaConsultoria in the database
        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeUpdate);
        EmpresaConsultoria testEmpresaConsultoria = empresaConsultoriaList.get(empresaConsultoriaList.size() - 1);
        assertThat(testEmpresaConsultoria.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresaConsultoria.getUrlIntegracao()).isEqualTo(UPDATED_URL_INTEGRACAO);
        assertThat(testEmpresaConsultoria.getTokenAcesso()).isEqualTo(UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresaConsultoria() throws Exception {
        int databaseSizeBeforeUpdate = empresaConsultoriaRepository.findAll().size();

        // Create the EmpresaConsultoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaConsultoriaMockMvc.perform(put("/api/empresa-consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaConsultoria)))
            .andExpect(status().isBadRequest());

        // Validate the EmpresaConsultoria in the database
        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresaConsultoria() throws Exception {
        // Initialize the database
        empresaConsultoriaService.save(empresaConsultoria);

        int databaseSizeBeforeDelete = empresaConsultoriaRepository.findAll().size();

        // Delete the empresaConsultoria
        restEmpresaConsultoriaMockMvc.perform(delete("/api/empresa-consultorias/{id}", empresaConsultoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpresaConsultoria> empresaConsultoriaList = empresaConsultoriaRepository.findAll();
        assertThat(empresaConsultoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
