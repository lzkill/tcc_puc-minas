package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.CategoriaNorma;
import com.lzkill.sgq.domain.Norma;
import com.lzkill.sgq.repository.CategoriaNormaRepository;
import com.lzkill.sgq.service.CategoriaNormaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.CategoriaNormaCriteria;
import com.lzkill.sgq.service.CategoriaNormaQueryService;

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
 * Integration tests for the {@link CategoriaNormaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class CategoriaNormaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CategoriaNormaRepository categoriaNormaRepository;

    @Autowired
    private CategoriaNormaService categoriaNormaService;

    @Autowired
    private CategoriaNormaQueryService categoriaNormaQueryService;

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

    private MockMvc restCategoriaNormaMockMvc;

    private CategoriaNorma categoriaNorma;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaNormaResource categoriaNormaResource = new CategoriaNormaResource(categoriaNormaService, categoriaNormaQueryService);
        this.restCategoriaNormaMockMvc = MockMvcBuilders.standaloneSetup(categoriaNormaResource)
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
    public static CategoriaNorma createEntity(EntityManager em) {
        CategoriaNorma categoriaNorma = new CategoriaNorma()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return categoriaNorma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaNorma createUpdatedEntity(EntityManager em) {
        CategoriaNorma categoriaNorma = new CategoriaNorma()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        return categoriaNorma;
    }

    @BeforeEach
    public void initTest() {
        categoriaNorma = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllCategoriaNormas() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaNorma.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaNorma() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get the categoriaNorma
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas/{id}", categoriaNorma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaNorma.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getCategoriaNormasByIdFiltering() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        Long id = categoriaNorma.getId();

        defaultCategoriaNormaShouldBeFound("id.equals=" + id);
        defaultCategoriaNormaShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaNormaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaNormaShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaNormaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaNormaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriaNormasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo equals to DEFAULT_TITULO
        defaultCategoriaNormaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the categoriaNormaList where titulo equals to UPDATED_TITULO
        defaultCategoriaNormaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaNormasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo not equals to DEFAULT_TITULO
        defaultCategoriaNormaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the categoriaNormaList where titulo not equals to UPDATED_TITULO
        defaultCategoriaNormaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaNormasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultCategoriaNormaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the categoriaNormaList where titulo equals to UPDATED_TITULO
        defaultCategoriaNormaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaNormasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo is not null
        defaultCategoriaNormaShouldBeFound("titulo.specified=true");

        // Get all the categoriaNormaList where titulo is null
        defaultCategoriaNormaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriaNormasByTituloContainsSomething() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo contains DEFAULT_TITULO
        defaultCategoriaNormaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the categoriaNormaList where titulo contains UPDATED_TITULO
        defaultCategoriaNormaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllCategoriaNormasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);

        // Get all the categoriaNormaList where titulo does not contain DEFAULT_TITULO
        defaultCategoriaNormaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the categoriaNormaList where titulo does not contain UPDATED_TITULO
        defaultCategoriaNormaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllCategoriaNormasByNormaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaNormaRepository.saveAndFlush(categoriaNorma);
        Norma norma = NormaResourceIT.createEntity(em);
        em.persist(norma);
        em.flush();
        categoriaNorma.addNorma(norma);
        categoriaNormaRepository.saveAndFlush(categoriaNorma);
        Long normaId = norma.getId();

        // Get all the categoriaNormaList where norma equals to normaId
        defaultCategoriaNormaShouldBeFound("normaId.equals=" + normaId);

        // Get all the categoriaNormaList where norma equals to normaId + 1
        defaultCategoriaNormaShouldNotBeFound("normaId.equals=" + (normaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaNormaShouldBeFound(String filter) throws Exception {
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaNorma.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaNormaShouldNotBeFound(String filter) throws Exception {
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaNorma() throws Exception {
        // Get the categoriaNorma
        restCategoriaNormaMockMvc.perform(get("/api/categoria-normas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
