package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.PublicoAlvo;
import com.lzkill.sgq.repository.PublicoAlvoRepository;
import com.lzkill.sgq.service.PublicoAlvoService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.PublicoAlvoCriteria;
import com.lzkill.sgq.service.PublicoAlvoQueryService;

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
 * Integration tests for the {@link PublicoAlvoResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class PublicoAlvoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADO = false;
    private static final Boolean UPDATED_HABILITADO = true;

    @Autowired
    private PublicoAlvoRepository publicoAlvoRepository;

    @Autowired
    private PublicoAlvoService publicoAlvoService;

    @Autowired
    private PublicoAlvoQueryService publicoAlvoQueryService;

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

    private MockMvc restPublicoAlvoMockMvc;

    private PublicoAlvo publicoAlvo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicoAlvoResource publicoAlvoResource = new PublicoAlvoResource(publicoAlvoService, publicoAlvoQueryService);
        this.restPublicoAlvoMockMvc = MockMvcBuilders.standaloneSetup(publicoAlvoResource)
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
    public static PublicoAlvo createEntity(EntityManager em) {
        PublicoAlvo publicoAlvo = new PublicoAlvo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .habilitado(DEFAULT_HABILITADO);
        return publicoAlvo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicoAlvo createUpdatedEntity(EntityManager em) {
        PublicoAlvo publicoAlvo = new PublicoAlvo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);
        return publicoAlvo;
    }

    @BeforeEach
    public void initTest() {
        publicoAlvo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicoAlvo() throws Exception {
        int databaseSizeBeforeCreate = publicoAlvoRepository.findAll().size();

        // Create the PublicoAlvo
        restPublicoAlvoMockMvc.perform(post("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicoAlvo)))
            .andExpect(status().isCreated());

        // Validate the PublicoAlvo in the database
        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeCreate + 1);
        PublicoAlvo testPublicoAlvo = publicoAlvoList.get(publicoAlvoList.size() - 1);
        assertThat(testPublicoAlvo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPublicoAlvo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPublicoAlvo.isHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    public void createPublicoAlvoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicoAlvoRepository.findAll().size();

        // Create the PublicoAlvo with an existing ID
        publicoAlvo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicoAlvoMockMvc.perform(post("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicoAlvo)))
            .andExpect(status().isBadRequest());

        // Validate the PublicoAlvo in the database
        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicoAlvoRepository.findAll().size();
        // set the field null
        publicoAlvo.setNome(null);

        // Create the PublicoAlvo, which fails.

        restPublicoAlvoMockMvc.perform(post("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicoAlvo)))
            .andExpect(status().isBadRequest());

        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilitadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicoAlvoRepository.findAll().size();
        // set the field null
        publicoAlvo.setHabilitado(null);

        // Create the PublicoAlvo, which fails.

        restPublicoAlvoMockMvc.perform(post("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicoAlvo)))
            .andExpect(status().isBadRequest());

        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvos() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicoAlvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPublicoAlvo() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get the publicoAlvo
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos/{id}", publicoAlvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicoAlvo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.habilitado").value(DEFAULT_HABILITADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getPublicoAlvosByIdFiltering() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        Long id = publicoAlvo.getId();

        defaultPublicoAlvoShouldBeFound("id.equals=" + id);
        defaultPublicoAlvoShouldNotBeFound("id.notEquals=" + id);

        defaultPublicoAlvoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPublicoAlvoShouldNotBeFound("id.greaterThan=" + id);

        defaultPublicoAlvoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPublicoAlvoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPublicoAlvosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome equals to DEFAULT_NOME
        defaultPublicoAlvoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the publicoAlvoList where nome equals to UPDATED_NOME
        defaultPublicoAlvoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome not equals to DEFAULT_NOME
        defaultPublicoAlvoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the publicoAlvoList where nome not equals to UPDATED_NOME
        defaultPublicoAlvoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPublicoAlvoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the publicoAlvoList where nome equals to UPDATED_NOME
        defaultPublicoAlvoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome is not null
        defaultPublicoAlvoShouldBeFound("nome.specified=true");

        // Get all the publicoAlvoList where nome is null
        defaultPublicoAlvoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicoAlvosByNomeContainsSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome contains DEFAULT_NOME
        defaultPublicoAlvoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the publicoAlvoList where nome contains UPDATED_NOME
        defaultPublicoAlvoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where nome does not contain DEFAULT_NOME
        defaultPublicoAlvoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the publicoAlvoList where nome does not contain UPDATED_NOME
        defaultPublicoAlvoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllPublicoAlvosByHabilitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where habilitado equals to DEFAULT_HABILITADO
        defaultPublicoAlvoShouldBeFound("habilitado.equals=" + DEFAULT_HABILITADO);

        // Get all the publicoAlvoList where habilitado equals to UPDATED_HABILITADO
        defaultPublicoAlvoShouldNotBeFound("habilitado.equals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByHabilitadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where habilitado not equals to DEFAULT_HABILITADO
        defaultPublicoAlvoShouldNotBeFound("habilitado.notEquals=" + DEFAULT_HABILITADO);

        // Get all the publicoAlvoList where habilitado not equals to UPDATED_HABILITADO
        defaultPublicoAlvoShouldBeFound("habilitado.notEquals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByHabilitadoIsInShouldWork() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where habilitado in DEFAULT_HABILITADO or UPDATED_HABILITADO
        defaultPublicoAlvoShouldBeFound("habilitado.in=" + DEFAULT_HABILITADO + "," + UPDATED_HABILITADO);

        // Get all the publicoAlvoList where habilitado equals to UPDATED_HABILITADO
        defaultPublicoAlvoShouldNotBeFound("habilitado.in=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllPublicoAlvosByHabilitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicoAlvoRepository.saveAndFlush(publicoAlvo);

        // Get all the publicoAlvoList where habilitado is not null
        defaultPublicoAlvoShouldBeFound("habilitado.specified=true");

        // Get all the publicoAlvoList where habilitado is null
        defaultPublicoAlvoShouldNotBeFound("habilitado.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublicoAlvoShouldBeFound(String filter) throws Exception {
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicoAlvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));

        // Check, that the count call also returns 1
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublicoAlvoShouldNotBeFound(String filter) throws Exception {
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPublicoAlvo() throws Exception {
        // Get the publicoAlvo
        restPublicoAlvoMockMvc.perform(get("/api/publico-alvos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicoAlvo() throws Exception {
        // Initialize the database
        publicoAlvoService.save(publicoAlvo);

        int databaseSizeBeforeUpdate = publicoAlvoRepository.findAll().size();

        // Update the publicoAlvo
        PublicoAlvo updatedPublicoAlvo = publicoAlvoRepository.findById(publicoAlvo.getId()).get();
        // Disconnect from session so that the updates on updatedPublicoAlvo are not directly saved in db
        em.detach(updatedPublicoAlvo);
        updatedPublicoAlvo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);

        restPublicoAlvoMockMvc.perform(put("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicoAlvo)))
            .andExpect(status().isOk());

        // Validate the PublicoAlvo in the database
        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeUpdate);
        PublicoAlvo testPublicoAlvo = publicoAlvoList.get(publicoAlvoList.size() - 1);
        assertThat(testPublicoAlvo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPublicoAlvo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPublicoAlvo.isHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicoAlvo() throws Exception {
        int databaseSizeBeforeUpdate = publicoAlvoRepository.findAll().size();

        // Create the PublicoAlvo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicoAlvoMockMvc.perform(put("/api/publico-alvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicoAlvo)))
            .andExpect(status().isBadRequest());

        // Validate the PublicoAlvo in the database
        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicoAlvo() throws Exception {
        // Initialize the database
        publicoAlvoService.save(publicoAlvo);

        int databaseSizeBeforeDelete = publicoAlvoRepository.findAll().size();

        // Delete the publicoAlvo
        restPublicoAlvoMockMvc.perform(delete("/api/publico-alvos/{id}", publicoAlvo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicoAlvo> publicoAlvoList = publicoAlvoRepository.findAll();
        assertThat(publicoAlvoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
