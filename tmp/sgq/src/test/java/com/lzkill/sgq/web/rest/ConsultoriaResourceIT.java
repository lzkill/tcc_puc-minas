package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.repository.ConsultoriaRepository;
import com.lzkill.sgq.service.ConsultoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ConsultoriaCriteria;
import com.lzkill.sgq.service.ConsultoriaQueryService;

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
 * Integration tests for the {@link ConsultoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ConsultoriaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_URL_INTEGRACAO = "AAAAAAAAAA";
    private static final String UPDATED_URL_INTEGRACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_ACESSO = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_ACESSO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADO = false;
    private static final Boolean UPDATED_HABILITADO = true;

    @Autowired
    private ConsultoriaRepository consultoriaRepository;

    @Autowired
    private ConsultoriaService consultoriaService;

    @Autowired
    private ConsultoriaQueryService consultoriaQueryService;

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

    private MockMvc restConsultoriaMockMvc;

    private Consultoria consultoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultoriaResource consultoriaResource = new ConsultoriaResource(consultoriaService, consultoriaQueryService);
        this.restConsultoriaMockMvc = MockMvcBuilders.standaloneSetup(consultoriaResource)
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
    public static Consultoria createEntity(EntityManager em) {
        Consultoria consultoria = new Consultoria()
            .nome(DEFAULT_NOME)
            .urlIntegracao(DEFAULT_URL_INTEGRACAO)
            .tokenAcesso(DEFAULT_TOKEN_ACESSO)
            .habilitado(DEFAULT_HABILITADO);
        return consultoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultoria createUpdatedEntity(EntityManager em) {
        Consultoria consultoria = new Consultoria()
            .nome(UPDATED_NOME)
            .urlIntegracao(UPDATED_URL_INTEGRACAO)
            .tokenAcesso(UPDATED_TOKEN_ACESSO)
            .habilitado(UPDATED_HABILITADO);
        return consultoria;
    }

    @BeforeEach
    public void initTest() {
        consultoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultoria() throws Exception {
        int databaseSizeBeforeCreate = consultoriaRepository.findAll().size();

        // Create the Consultoria
        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultoria)))
            .andExpect(status().isCreated());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Consultoria testConsultoria = consultoriaList.get(consultoriaList.size() - 1);
        assertThat(testConsultoria.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsultoria.getUrlIntegracao()).isEqualTo(DEFAULT_URL_INTEGRACAO);
        assertThat(testConsultoria.getTokenAcesso()).isEqualTo(DEFAULT_TOKEN_ACESSO);
        assertThat(testConsultoria.isHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    public void createConsultoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultoriaRepository.findAll().size();

        // Create the Consultoria with an existing ID
        consultoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultoria)))
            .andExpect(status().isBadRequest());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoriaRepository.findAll().size();
        // set the field null
        consultoria.setNome(null);

        // Create the Consultoria, which fails.

        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultoria)))
            .andExpect(status().isBadRequest());

        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilitadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoriaRepository.findAll().size();
        // set the field null
        consultoria.setHabilitado(null);

        // Create the Consultoria, which fails.

        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultoria)))
            .andExpect(status().isBadRequest());

        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultorias() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList
        restConsultoriaMockMvc.perform(get("/api/consultorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].urlIntegracao").value(hasItem(DEFAULT_URL_INTEGRACAO)))
            .andExpect(jsonPath("$.[*].tokenAcesso").value(hasItem(DEFAULT_TOKEN_ACESSO)))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConsultoria() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get the consultoria
        restConsultoriaMockMvc.perform(get("/api/consultorias/{id}", consultoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultoria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.urlIntegracao").value(DEFAULT_URL_INTEGRACAO))
            .andExpect(jsonPath("$.tokenAcesso").value(DEFAULT_TOKEN_ACESSO))
            .andExpect(jsonPath("$.habilitado").value(DEFAULT_HABILITADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getConsultoriasByIdFiltering() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        Long id = consultoria.getId();

        defaultConsultoriaShouldBeFound("id.equals=" + id);
        defaultConsultoriaShouldNotBeFound("id.notEquals=" + id);

        defaultConsultoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsultoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultConsultoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsultoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConsultoriasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome equals to DEFAULT_NOME
        defaultConsultoriaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the consultoriaList where nome equals to UPDATED_NOME
        defaultConsultoriaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome not equals to DEFAULT_NOME
        defaultConsultoriaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the consultoriaList where nome not equals to UPDATED_NOME
        defaultConsultoriaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultConsultoriaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the consultoriaList where nome equals to UPDATED_NOME
        defaultConsultoriaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome is not null
        defaultConsultoriaShouldBeFound("nome.specified=true");

        // Get all the consultoriaList where nome is null
        defaultConsultoriaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsultoriasByNomeContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome contains DEFAULT_NOME
        defaultConsultoriaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the consultoriaList where nome contains UPDATED_NOME
        defaultConsultoriaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where nome does not contain DEFAULT_NOME
        defaultConsultoriaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the consultoriaList where nome does not contain UPDATED_NOME
        defaultConsultoriaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao equals to DEFAULT_URL_INTEGRACAO
        defaultConsultoriaShouldBeFound("urlIntegracao.equals=" + DEFAULT_URL_INTEGRACAO);

        // Get all the consultoriaList where urlIntegracao equals to UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldNotBeFound("urlIntegracao.equals=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao not equals to DEFAULT_URL_INTEGRACAO
        defaultConsultoriaShouldNotBeFound("urlIntegracao.notEquals=" + DEFAULT_URL_INTEGRACAO);

        // Get all the consultoriaList where urlIntegracao not equals to UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldBeFound("urlIntegracao.notEquals=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoIsInShouldWork() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao in DEFAULT_URL_INTEGRACAO or UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldBeFound("urlIntegracao.in=" + DEFAULT_URL_INTEGRACAO + "," + UPDATED_URL_INTEGRACAO);

        // Get all the consultoriaList where urlIntegracao equals to UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldNotBeFound("urlIntegracao.in=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao is not null
        defaultConsultoriaShouldBeFound("urlIntegracao.specified=true");

        // Get all the consultoriaList where urlIntegracao is null
        defaultConsultoriaShouldNotBeFound("urlIntegracao.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao contains DEFAULT_URL_INTEGRACAO
        defaultConsultoriaShouldBeFound("urlIntegracao.contains=" + DEFAULT_URL_INTEGRACAO);

        // Get all the consultoriaList where urlIntegracao contains UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldNotBeFound("urlIntegracao.contains=" + UPDATED_URL_INTEGRACAO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByUrlIntegracaoNotContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where urlIntegracao does not contain DEFAULT_URL_INTEGRACAO
        defaultConsultoriaShouldNotBeFound("urlIntegracao.doesNotContain=" + DEFAULT_URL_INTEGRACAO);

        // Get all the consultoriaList where urlIntegracao does not contain UPDATED_URL_INTEGRACAO
        defaultConsultoriaShouldBeFound("urlIntegracao.doesNotContain=" + UPDATED_URL_INTEGRACAO);
    }


    @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso equals to DEFAULT_TOKEN_ACESSO
        defaultConsultoriaShouldBeFound("tokenAcesso.equals=" + DEFAULT_TOKEN_ACESSO);

        // Get all the consultoriaList where tokenAcesso equals to UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldNotBeFound("tokenAcesso.equals=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso not equals to DEFAULT_TOKEN_ACESSO
        defaultConsultoriaShouldNotBeFound("tokenAcesso.notEquals=" + DEFAULT_TOKEN_ACESSO);

        // Get all the consultoriaList where tokenAcesso not equals to UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldBeFound("tokenAcesso.notEquals=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso in DEFAULT_TOKEN_ACESSO or UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldBeFound("tokenAcesso.in=" + DEFAULT_TOKEN_ACESSO + "," + UPDATED_TOKEN_ACESSO);

        // Get all the consultoriaList where tokenAcesso equals to UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldNotBeFound("tokenAcesso.in=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso is not null
        defaultConsultoriaShouldBeFound("tokenAcesso.specified=true");

        // Get all the consultoriaList where tokenAcesso is null
        defaultConsultoriaShouldNotBeFound("tokenAcesso.specified=false");
    }
                @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso contains DEFAULT_TOKEN_ACESSO
        defaultConsultoriaShouldBeFound("tokenAcesso.contains=" + DEFAULT_TOKEN_ACESSO);

        // Get all the consultoriaList where tokenAcesso contains UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldNotBeFound("tokenAcesso.contains=" + UPDATED_TOKEN_ACESSO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByTokenAcessoNotContainsSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where tokenAcesso does not contain DEFAULT_TOKEN_ACESSO
        defaultConsultoriaShouldNotBeFound("tokenAcesso.doesNotContain=" + DEFAULT_TOKEN_ACESSO);

        // Get all the consultoriaList where tokenAcesso does not contain UPDATED_TOKEN_ACESSO
        defaultConsultoriaShouldBeFound("tokenAcesso.doesNotContain=" + UPDATED_TOKEN_ACESSO);
    }


    @Test
    @Transactional
    public void getAllConsultoriasByHabilitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where habilitado equals to DEFAULT_HABILITADO
        defaultConsultoriaShouldBeFound("habilitado.equals=" + DEFAULT_HABILITADO);

        // Get all the consultoriaList where habilitado equals to UPDATED_HABILITADO
        defaultConsultoriaShouldNotBeFound("habilitado.equals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByHabilitadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where habilitado not equals to DEFAULT_HABILITADO
        defaultConsultoriaShouldNotBeFound("habilitado.notEquals=" + DEFAULT_HABILITADO);

        // Get all the consultoriaList where habilitado not equals to UPDATED_HABILITADO
        defaultConsultoriaShouldBeFound("habilitado.notEquals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByHabilitadoIsInShouldWork() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where habilitado in DEFAULT_HABILITADO or UPDATED_HABILITADO
        defaultConsultoriaShouldBeFound("habilitado.in=" + DEFAULT_HABILITADO + "," + UPDATED_HABILITADO);

        // Get all the consultoriaList where habilitado equals to UPDATED_HABILITADO
        defaultConsultoriaShouldNotBeFound("habilitado.in=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllConsultoriasByHabilitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList where habilitado is not null
        defaultConsultoriaShouldBeFound("habilitado.specified=true");

        // Get all the consultoriaList where habilitado is null
        defaultConsultoriaShouldNotBeFound("habilitado.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsultoriaShouldBeFound(String filter) throws Exception {
        restConsultoriaMockMvc.perform(get("/api/consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].urlIntegracao").value(hasItem(DEFAULT_URL_INTEGRACAO)))
            .andExpect(jsonPath("$.[*].tokenAcesso").value(hasItem(DEFAULT_TOKEN_ACESSO)))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));

        // Check, that the count call also returns 1
        restConsultoriaMockMvc.perform(get("/api/consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsultoriaShouldNotBeFound(String filter) throws Exception {
        restConsultoriaMockMvc.perform(get("/api/consultorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsultoriaMockMvc.perform(get("/api/consultorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConsultoria() throws Exception {
        // Get the consultoria
        restConsultoriaMockMvc.perform(get("/api/consultorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultoria() throws Exception {
        // Initialize the database
        consultoriaService.save(consultoria);

        int databaseSizeBeforeUpdate = consultoriaRepository.findAll().size();

        // Update the consultoria
        Consultoria updatedConsultoria = consultoriaRepository.findById(consultoria.getId()).get();
        // Disconnect from session so that the updates on updatedConsultoria are not directly saved in db
        em.detach(updatedConsultoria);
        updatedConsultoria
            .nome(UPDATED_NOME)
            .urlIntegracao(UPDATED_URL_INTEGRACAO)
            .tokenAcesso(UPDATED_TOKEN_ACESSO)
            .habilitado(UPDATED_HABILITADO);

        restConsultoriaMockMvc.perform(put("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultoria)))
            .andExpect(status().isOk());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeUpdate);
        Consultoria testConsultoria = consultoriaList.get(consultoriaList.size() - 1);
        assertThat(testConsultoria.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsultoria.getUrlIntegracao()).isEqualTo(UPDATED_URL_INTEGRACAO);
        assertThat(testConsultoria.getTokenAcesso()).isEqualTo(UPDATED_TOKEN_ACESSO);
        assertThat(testConsultoria.isHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultoria() throws Exception {
        int databaseSizeBeforeUpdate = consultoriaRepository.findAll().size();

        // Create the Consultoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultoriaMockMvc.perform(put("/api/consultorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultoria)))
            .andExpect(status().isBadRequest());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsultoria() throws Exception {
        // Initialize the database
        consultoriaService.save(consultoria);

        int databaseSizeBeforeDelete = consultoriaRepository.findAll().size();

        // Delete the consultoria
        restConsultoriaMockMvc.perform(delete("/api/consultorias/{id}", consultoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
