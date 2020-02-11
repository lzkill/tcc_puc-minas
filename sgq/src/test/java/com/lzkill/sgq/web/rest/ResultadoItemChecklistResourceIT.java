package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.repository.ResultadoItemChecklistRepository;
import com.lzkill.sgq.service.ResultadoItemChecklistService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ResultadoItemChecklistCriteria;
import com.lzkill.sgq.service.ResultadoItemChecklistQueryService;

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
 * Integration tests for the {@link ResultadoItemChecklistResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ResultadoItemChecklistResourceIT {

    private static final Boolean DEFAULT_CONFORME = false;
    private static final Boolean UPDATED_CONFORME = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ResultadoItemChecklistRepository resultadoItemChecklistRepository;

    @Autowired
    private ResultadoItemChecklistService resultadoItemChecklistService;

    @Autowired
    private ResultadoItemChecklistQueryService resultadoItemChecklistQueryService;

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

    private MockMvc restResultadoItemChecklistMockMvc;

    private ResultadoItemChecklist resultadoItemChecklist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadoItemChecklistResource resultadoItemChecklistResource = new ResultadoItemChecklistResource(resultadoItemChecklistService, resultadoItemChecklistQueryService);
        this.restResultadoItemChecklistMockMvc = MockMvcBuilders.standaloneSetup(resultadoItemChecklistResource)
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
    public static ResultadoItemChecklist createEntity(EntityManager em) {
        ResultadoItemChecklist resultadoItemChecklist = new ResultadoItemChecklist()
            .conforme(DEFAULT_CONFORME)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        ItemChecklist itemChecklist;
        if (TestUtil.findAll(em, ItemChecklist.class).isEmpty()) {
            itemChecklist = ItemChecklistResourceIT.createEntity(em);
            em.persist(itemChecklist);
            em.flush();
        } else {
            itemChecklist = TestUtil.findAll(em, ItemChecklist.class).get(0);
        }
        resultadoItemChecklist.setItem(itemChecklist);
        // Add required entity
        ResultadoChecklist resultadoChecklist;
        if (TestUtil.findAll(em, ResultadoChecklist.class).isEmpty()) {
            resultadoChecklist = ResultadoChecklistResourceIT.createEntity(em);
            em.persist(resultadoChecklist);
            em.flush();
        } else {
            resultadoChecklist = TestUtil.findAll(em, ResultadoChecklist.class).get(0);
        }
        resultadoItemChecklist.setResultado(resultadoChecklist);
        return resultadoItemChecklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadoItemChecklist createUpdatedEntity(EntityManager em) {
        ResultadoItemChecklist resultadoItemChecklist = new ResultadoItemChecklist()
            .conforme(UPDATED_CONFORME)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        ItemChecklist itemChecklist;
        if (TestUtil.findAll(em, ItemChecklist.class).isEmpty()) {
            itemChecklist = ItemChecklistResourceIT.createUpdatedEntity(em);
            em.persist(itemChecklist);
            em.flush();
        } else {
            itemChecklist = TestUtil.findAll(em, ItemChecklist.class).get(0);
        }
        resultadoItemChecklist.setItem(itemChecklist);
        // Add required entity
        ResultadoChecklist resultadoChecklist;
        if (TestUtil.findAll(em, ResultadoChecklist.class).isEmpty()) {
            resultadoChecklist = ResultadoChecklistResourceIT.createUpdatedEntity(em);
            em.persist(resultadoChecklist);
            em.flush();
        } else {
            resultadoChecklist = TestUtil.findAll(em, ResultadoChecklist.class).get(0);
        }
        resultadoItemChecklist.setResultado(resultadoChecklist);
        return resultadoItemChecklist;
    }

    @BeforeEach
    public void initTest() {
        resultadoItemChecklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadoItemChecklist() throws Exception {
        int databaseSizeBeforeCreate = resultadoItemChecklistRepository.findAll().size();

        // Create the ResultadoItemChecklist
        restResultadoItemChecklistMockMvc.perform(post("/api/resultado-item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoItemChecklist)))
            .andExpect(status().isCreated());

        // Validate the ResultadoItemChecklist in the database
        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadoItemChecklist testResultadoItemChecklist = resultadoItemChecklistList.get(resultadoItemChecklistList.size() - 1);
        assertThat(testResultadoItemChecklist.isConforme()).isEqualTo(DEFAULT_CONFORME);
        assertThat(testResultadoItemChecklist.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createResultadoItemChecklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoItemChecklistRepository.findAll().size();

        // Create the ResultadoItemChecklist with an existing ID
        resultadoItemChecklist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoItemChecklistMockMvc.perform(post("/api/resultado-item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoItemChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoItemChecklist in the database
        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConformeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoItemChecklistRepository.findAll().size();
        // set the field null
        resultadoItemChecklist.setConforme(null);

        // Create the ResultadoItemChecklist, which fails.

        restResultadoItemChecklistMockMvc.perform(post("/api/resultado-item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoItemChecklist)))
            .andExpect(status().isBadRequest());

        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultadoItemChecklists() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get all the resultadoItemChecklistList
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoItemChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].conforme").value(hasItem(DEFAULT_CONFORME.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getResultadoItemChecklist() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get the resultadoItemChecklist
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists/{id}", resultadoItemChecklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadoItemChecklist.getId().intValue()))
            .andExpect(jsonPath("$.conforme").value(DEFAULT_CONFORME.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getResultadoItemChecklistsByIdFiltering() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        Long id = resultadoItemChecklist.getId();

        defaultResultadoItemChecklistShouldBeFound("id.equals=" + id);
        defaultResultadoItemChecklistShouldNotBeFound("id.notEquals=" + id);

        defaultResultadoItemChecklistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultadoItemChecklistShouldNotBeFound("id.greaterThan=" + id);

        defaultResultadoItemChecklistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultadoItemChecklistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByConformeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get all the resultadoItemChecklistList where conforme equals to DEFAULT_CONFORME
        defaultResultadoItemChecklistShouldBeFound("conforme.equals=" + DEFAULT_CONFORME);

        // Get all the resultadoItemChecklistList where conforme equals to UPDATED_CONFORME
        defaultResultadoItemChecklistShouldNotBeFound("conforme.equals=" + UPDATED_CONFORME);
    }

    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByConformeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get all the resultadoItemChecklistList where conforme not equals to DEFAULT_CONFORME
        defaultResultadoItemChecklistShouldNotBeFound("conforme.notEquals=" + DEFAULT_CONFORME);

        // Get all the resultadoItemChecklistList where conforme not equals to UPDATED_CONFORME
        defaultResultadoItemChecklistShouldBeFound("conforme.notEquals=" + UPDATED_CONFORME);
    }

    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByConformeIsInShouldWork() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get all the resultadoItemChecklistList where conforme in DEFAULT_CONFORME or UPDATED_CONFORME
        defaultResultadoItemChecklistShouldBeFound("conforme.in=" + DEFAULT_CONFORME + "," + UPDATED_CONFORME);

        // Get all the resultadoItemChecklistList where conforme equals to UPDATED_CONFORME
        defaultResultadoItemChecklistShouldNotBeFound("conforme.in=" + UPDATED_CONFORME);
    }

    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByConformeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);

        // Get all the resultadoItemChecklistList where conforme is not null
        defaultResultadoItemChecklistShouldBeFound("conforme.specified=true");

        // Get all the resultadoItemChecklistList where conforme is null
        defaultResultadoItemChecklistShouldNotBeFound("conforme.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        resultadoItemChecklist.addAnexo(anexo);
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Long anexoId = anexo.getId();

        // Get all the resultadoItemChecklistList where anexo equals to anexoId
        defaultResultadoItemChecklistShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the resultadoItemChecklistList where anexo equals to anexoId + 1
        defaultResultadoItemChecklistShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        resultadoItemChecklist.addNaoConformidade(naoConformidade);
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the resultadoItemChecklistList where naoConformidade equals to naoConformidadeId
        defaultResultadoItemChecklistShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the resultadoItemChecklistList where naoConformidade equals to naoConformidadeId + 1
        defaultResultadoItemChecklistShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByProdutoNaoConformeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        ProdutoNaoConforme produtoNaoConforme = ProdutoNaoConformeResourceIT.createEntity(em);
        em.persist(produtoNaoConforme);
        em.flush();
        resultadoItemChecklist.addProdutoNaoConforme(produtoNaoConforme);
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Long produtoNaoConformeId = produtoNaoConforme.getId();

        // Get all the resultadoItemChecklistList where produtoNaoConforme equals to produtoNaoConformeId
        defaultResultadoItemChecklistShouldBeFound("produtoNaoConformeId.equals=" + produtoNaoConformeId);

        // Get all the resultadoItemChecklistList where produtoNaoConforme equals to produtoNaoConformeId + 1
        defaultResultadoItemChecklistShouldNotBeFound("produtoNaoConformeId.equals=" + (produtoNaoConformeId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        ItemChecklist item = resultadoItemChecklist.getItem();
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Long itemId = item.getId();

        // Get all the resultadoItemChecklistList where item equals to itemId
        defaultResultadoItemChecklistShouldBeFound("itemId.equals=" + itemId);

        // Get all the resultadoItemChecklistList where item equals to itemId + 1
        defaultResultadoItemChecklistShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllResultadoItemChecklistsByResultadoIsEqualToSomething() throws Exception {
        // Get already existing entity
        ResultadoChecklist resultado = resultadoItemChecklist.getResultado();
        resultadoItemChecklistRepository.saveAndFlush(resultadoItemChecklist);
        Long resultadoId = resultado.getId();

        // Get all the resultadoItemChecklistList where resultado equals to resultadoId
        defaultResultadoItemChecklistShouldBeFound("resultadoId.equals=" + resultadoId);

        // Get all the resultadoItemChecklistList where resultado equals to resultadoId + 1
        defaultResultadoItemChecklistShouldNotBeFound("resultadoId.equals=" + (resultadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultadoItemChecklistShouldBeFound(String filter) throws Exception {
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoItemChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].conforme").value(hasItem(DEFAULT_CONFORME.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultadoItemChecklistShouldNotBeFound(String filter) throws Exception {
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResultadoItemChecklist() throws Exception {
        // Get the resultadoItemChecklist
        restResultadoItemChecklistMockMvc.perform(get("/api/resultado-item-checklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadoItemChecklist() throws Exception {
        // Initialize the database
        resultadoItemChecklistService.save(resultadoItemChecklist);

        int databaseSizeBeforeUpdate = resultadoItemChecklistRepository.findAll().size();

        // Update the resultadoItemChecklist
        ResultadoItemChecklist updatedResultadoItemChecklist = resultadoItemChecklistRepository.findById(resultadoItemChecklist.getId()).get();
        // Disconnect from session so that the updates on updatedResultadoItemChecklist are not directly saved in db
        em.detach(updatedResultadoItemChecklist);
        updatedResultadoItemChecklist
            .conforme(UPDATED_CONFORME)
            .descricao(UPDATED_DESCRICAO);

        restResultadoItemChecklistMockMvc.perform(put("/api/resultado-item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadoItemChecklist)))
            .andExpect(status().isOk());

        // Validate the ResultadoItemChecklist in the database
        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeUpdate);
        ResultadoItemChecklist testResultadoItemChecklist = resultadoItemChecklistList.get(resultadoItemChecklistList.size() - 1);
        assertThat(testResultadoItemChecklist.isConforme()).isEqualTo(UPDATED_CONFORME);
        assertThat(testResultadoItemChecklist.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadoItemChecklist() throws Exception {
        int databaseSizeBeforeUpdate = resultadoItemChecklistRepository.findAll().size();

        // Create the ResultadoItemChecklist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoItemChecklistMockMvc.perform(put("/api/resultado-item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadoItemChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoItemChecklist in the database
        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadoItemChecklist() throws Exception {
        // Initialize the database
        resultadoItemChecklistService.save(resultadoItemChecklist);

        int databaseSizeBeforeDelete = resultadoItemChecklistRepository.findAll().size();

        // Delete the resultadoItemChecklist
        restResultadoItemChecklistMockMvc.perform(delete("/api/resultado-item-checklists/{id}", resultadoItemChecklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultadoItemChecklist> resultadoItemChecklistList = resultadoItemChecklistRepository.findAll();
        assertThat(resultadoItemChecklistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
