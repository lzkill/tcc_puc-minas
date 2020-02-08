package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.repository.CategoriaPublicacaoRepository;
import com.lzkill.sgq.service.CategoriaPublicacaoService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.CategoriaPublicacaoCriteria;
import com.lzkill.sgq.service.CategoriaPublicacaoQueryService;

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
 * Integration tests for the {@link CategoriaPublicacaoResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class CategoriaPublicacaoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CategoriaPublicacaoRepository categoriaPublicacaoRepository;

    @Autowired
    private CategoriaPublicacaoService categoriaPublicacaoService;

    @Autowired
    private CategoriaPublicacaoQueryService categoriaPublicacaoQueryService;

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

    private MockMvc restCategoriaPublicacaoMockMvc;

    private CategoriaPublicacao categoriaPublicacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaPublicacaoResource categoriaPublicacaoResource = new CategoriaPublicacaoResource(categoriaPublicacaoService, categoriaPublicacaoQueryService);
        this.restCategoriaPublicacaoMockMvc = MockMvcBuilders.standaloneSetup(categoriaPublicacaoResource)
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
    public static CategoriaPublicacao createEntity(EntityManager em) {
        CategoriaPublicacao categoriaPublicacao = new CategoriaPublicacao()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return categoriaPublicacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaPublicacao createUpdatedEntity(EntityManager em) {
        CategoriaPublicacao categoriaPublicacao = new CategoriaPublicacao()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        return categoriaPublicacao;
    }

    @BeforeEach
    public void initTest() {
        categoriaPublicacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaPublicacao() throws Exception {
        int databaseSizeBeforeCreate = categoriaPublicacaoRepository.findAll().size();

        // Create the CategoriaPublicacao
        restCategoriaPublicacaoMockMvc.perform(post("/api/categoria-publicacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacao)))
            .andExpect(status().isCreated());

        // Validate the CategoriaPublicacao in the database
        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaPublicacao testCategoriaPublicacao = categoriaPublicacaoList.get(categoriaPublicacaoList.size() - 1);
        assertThat(testCategoriaPublicacao.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCategoriaPublicacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCategoriaPublicacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaPublicacaoRepository.findAll().size();

        // Create the CategoriaPublicacao with an existing ID
        categoriaPublicacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaPublicacaoMockMvc.perform(post("/api/categoria-publicacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacao)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacao in the database
        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaPublicacaoRepository.findAll().size();
        // set the field null
        categoriaPublicacao.setTitulo(null);

        // Create the CategoriaPublicacao, which fails.

        restCategoriaPublicacaoMockMvc.perform(post("/api/categoria-publicacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacao)))
            .andExpect(status().isBadRequest());

        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaPublicacaos() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaPublicacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaPublicacao() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get the categoriaPublicacao
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos/{id}", categoriaPublicacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaPublicacao.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getCategoriaPublicacaosByIdFiltering() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        Long id = categoriaPublicacao.getId();

        defaultCategoriaPublicacaoShouldBeFound("id.equals=" + id);
        defaultCategoriaPublicacaoShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaPublicacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaPublicacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaPublicacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaPublicacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo equals to DEFAULT_TITULO
        defaultCategoriaPublicacaoShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the categoriaPublicacaoList where titulo equals to UPDATED_TITULO
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo not equals to DEFAULT_TITULO
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the categoriaPublicacaoList where titulo not equals to UPDATED_TITULO
        defaultCategoriaPublicacaoShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultCategoriaPublicacaoShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the categoriaPublicacaoList where titulo equals to UPDATED_TITULO
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo is not null
        defaultCategoriaPublicacaoShouldBeFound("titulo.specified=true");

        // Get all the categoriaPublicacaoList where titulo is null
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloContainsSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo contains DEFAULT_TITULO
        defaultCategoriaPublicacaoShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the categoriaPublicacaoList where titulo contains UPDATED_TITULO
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);

        // Get all the categoriaPublicacaoList where titulo does not contain DEFAULT_TITULO
        defaultCategoriaPublicacaoShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the categoriaPublicacaoList where titulo does not contain UPDATED_TITULO
        defaultCategoriaPublicacaoShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByBoletimIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);
        BoletimInformativo boletim = BoletimInformativoResourceIT.createEntity(em);
        em.persist(boletim);
        em.flush();
        categoriaPublicacao.addBoletim(boletim);
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);
        Long boletimId = boletim.getId();

        // Get all the categoriaPublicacaoList where boletim equals to boletimId
        defaultCategoriaPublicacaoShouldBeFound("boletimId.equals=" + boletimId);

        // Get all the categoriaPublicacaoList where boletim equals to boletimId + 1
        defaultCategoriaPublicacaoShouldNotBeFound("boletimId.equals=" + (boletimId + 1));
    }


    @Test
    @Transactional
    public void getAllCategoriaPublicacaosByPublicacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);
        PublicacaoFeed publicacao = PublicacaoFeedResourceIT.createEntity(em);
        em.persist(publicacao);
        em.flush();
        categoriaPublicacao.addPublicacao(publicacao);
        categoriaPublicacaoRepository.saveAndFlush(categoriaPublicacao);
        Long publicacaoId = publicacao.getId();

        // Get all the categoriaPublicacaoList where publicacao equals to publicacaoId
        defaultCategoriaPublicacaoShouldBeFound("publicacaoId.equals=" + publicacaoId);

        // Get all the categoriaPublicacaoList where publicacao equals to publicacaoId + 1
        defaultCategoriaPublicacaoShouldNotBeFound("publicacaoId.equals=" + (publicacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaPublicacaoShouldBeFound(String filter) throws Exception {
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaPublicacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaPublicacaoShouldNotBeFound(String filter) throws Exception {
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaPublicacao() throws Exception {
        // Get the categoriaPublicacao
        restCategoriaPublicacaoMockMvc.perform(get("/api/categoria-publicacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaPublicacao() throws Exception {
        // Initialize the database
        categoriaPublicacaoService.save(categoriaPublicacao);

        int databaseSizeBeforeUpdate = categoriaPublicacaoRepository.findAll().size();

        // Update the categoriaPublicacao
        CategoriaPublicacao updatedCategoriaPublicacao = categoriaPublicacaoRepository.findById(categoriaPublicacao.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaPublicacao are not directly saved in db
        em.detach(updatedCategoriaPublicacao);
        updatedCategoriaPublicacao
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restCategoriaPublicacaoMockMvc.perform(put("/api/categoria-publicacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaPublicacao)))
            .andExpect(status().isOk());

        // Validate the CategoriaPublicacao in the database
        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaPublicacao testCategoriaPublicacao = categoriaPublicacaoList.get(categoriaPublicacaoList.size() - 1);
        assertThat(testCategoriaPublicacao.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCategoriaPublicacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaPublicacao() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacaoRepository.findAll().size();

        // Create the CategoriaPublicacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaPublicacaoMockMvc.perform(put("/api/categoria-publicacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacao)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacao in the database
        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriaPublicacao() throws Exception {
        // Initialize the database
        categoriaPublicacaoService.save(categoriaPublicacao);

        int databaseSizeBeforeDelete = categoriaPublicacaoRepository.findAll().size();

        // Delete the categoriaPublicacao
        restCategoriaPublicacaoMockMvc.perform(delete("/api/categoria-publicacaos/{id}", categoriaPublicacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaPublicacao> categoriaPublicacaoList = categoriaPublicacaoRepository.findAll();
        assertThat(categoriaPublicacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
