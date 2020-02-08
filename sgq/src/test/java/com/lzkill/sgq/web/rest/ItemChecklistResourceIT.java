package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.repository.ItemChecklistRepository;
import com.lzkill.sgq.service.ItemChecklistService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ItemChecklistCriteria;
import com.lzkill.sgq.service.ItemChecklistQueryService;

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
 * Integration tests for the {@link ItemChecklistResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ItemChecklistResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;
    private static final Integer SMALLER_ORDEM = 1 - 1;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ItemChecklistRepository itemChecklistRepository;

    @Autowired
    private ItemChecklistService itemChecklistService;

    @Autowired
    private ItemChecklistQueryService itemChecklistQueryService;

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

    private MockMvc restItemChecklistMockMvc;

    private ItemChecklist itemChecklist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemChecklistResource itemChecklistResource = new ItemChecklistResource(itemChecklistService, itemChecklistQueryService);
        this.restItemChecklistMockMvc = MockMvcBuilders.standaloneSetup(itemChecklistResource)
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
    public static ItemChecklist createEntity(EntityManager em) {
        ItemChecklist itemChecklist = new ItemChecklist()
            .ordem(DEFAULT_ORDEM)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Checklist checklist;
        if (TestUtil.findAll(em, Checklist.class).isEmpty()) {
            checklist = ChecklistResourceIT.createEntity(em);
            em.persist(checklist);
            em.flush();
        } else {
            checklist = TestUtil.findAll(em, Checklist.class).get(0);
        }
        itemChecklist.setChecklist(checklist);
        return itemChecklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemChecklist createUpdatedEntity(EntityManager em) {
        ItemChecklist itemChecklist = new ItemChecklist()
            .ordem(UPDATED_ORDEM)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Checklist checklist;
        if (TestUtil.findAll(em, Checklist.class).isEmpty()) {
            checklist = ChecklistResourceIT.createUpdatedEntity(em);
            em.persist(checklist);
            em.flush();
        } else {
            checklist = TestUtil.findAll(em, Checklist.class).get(0);
        }
        itemChecklist.setChecklist(checklist);
        return itemChecklist;
    }

    @BeforeEach
    public void initTest() {
        itemChecklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemChecklist() throws Exception {
        int databaseSizeBeforeCreate = itemChecklistRepository.findAll().size();

        // Create the ItemChecklist
        restItemChecklistMockMvc.perform(post("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemChecklist)))
            .andExpect(status().isCreated());

        // Validate the ItemChecklist in the database
        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeCreate + 1);
        ItemChecklist testItemChecklist = itemChecklistList.get(itemChecklistList.size() - 1);
        assertThat(testItemChecklist.getOrdem()).isEqualTo(DEFAULT_ORDEM);
        assertThat(testItemChecklist.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testItemChecklist.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createItemChecklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemChecklistRepository.findAll().size();

        // Create the ItemChecklist with an existing ID
        itemChecklist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemChecklistMockMvc.perform(post("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ItemChecklist in the database
        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrdemIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemChecklistRepository.findAll().size();
        // set the field null
        itemChecklist.setOrdem(null);

        // Create the ItemChecklist, which fails.

        restItemChecklistMockMvc.perform(post("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemChecklist)))
            .andExpect(status().isBadRequest());

        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemChecklistRepository.findAll().size();
        // set the field null
        itemChecklist.setTitulo(null);

        // Create the ItemChecklist, which fails.

        restItemChecklistMockMvc.perform(post("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemChecklist)))
            .andExpect(status().isBadRequest());

        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemChecklists() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList
        restItemChecklistMockMvc.perform(get("/api/item-checklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getItemChecklist() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get the itemChecklist
        restItemChecklistMockMvc.perform(get("/api/item-checklists/{id}", itemChecklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemChecklist.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getItemChecklistsByIdFiltering() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        Long id = itemChecklist.getId();

        defaultItemChecklistShouldBeFound("id.equals=" + id);
        defaultItemChecklistShouldNotBeFound("id.notEquals=" + id);

        defaultItemChecklistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemChecklistShouldNotBeFound("id.greaterThan=" + id);

        defaultItemChecklistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemChecklistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem equals to DEFAULT_ORDEM
        defaultItemChecklistShouldBeFound("ordem.equals=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem equals to UPDATED_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem not equals to DEFAULT_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.notEquals=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem not equals to UPDATED_ORDEM
        defaultItemChecklistShouldBeFound("ordem.notEquals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem in DEFAULT_ORDEM or UPDATED_ORDEM
        defaultItemChecklistShouldBeFound("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM);

        // Get all the itemChecklistList where ordem equals to UPDATED_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem is not null
        defaultItemChecklistShouldBeFound("ordem.specified=true");

        // Get all the itemChecklistList where ordem is null
        defaultItemChecklistShouldNotBeFound("ordem.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem is greater than or equal to DEFAULT_ORDEM
        defaultItemChecklistShouldBeFound("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem is greater than or equal to UPDATED_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem is less than or equal to DEFAULT_ORDEM
        defaultItemChecklistShouldBeFound("ordem.lessThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem is less than or equal to SMALLER_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem is less than DEFAULT_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.lessThan=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem is less than UPDATED_ORDEM
        defaultItemChecklistShouldBeFound("ordem.lessThan=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where ordem is greater than DEFAULT_ORDEM
        defaultItemChecklistShouldNotBeFound("ordem.greaterThan=" + DEFAULT_ORDEM);

        // Get all the itemChecklistList where ordem is greater than SMALLER_ORDEM
        defaultItemChecklistShouldBeFound("ordem.greaterThan=" + SMALLER_ORDEM);
    }


    @Test
    @Transactional
    public void getAllItemChecklistsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo equals to DEFAULT_TITULO
        defaultItemChecklistShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the itemChecklistList where titulo equals to UPDATED_TITULO
        defaultItemChecklistShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo not equals to DEFAULT_TITULO
        defaultItemChecklistShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the itemChecklistList where titulo not equals to UPDATED_TITULO
        defaultItemChecklistShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultItemChecklistShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the itemChecklistList where titulo equals to UPDATED_TITULO
        defaultItemChecklistShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo is not null
        defaultItemChecklistShouldBeFound("titulo.specified=true");

        // Get all the itemChecklistList where titulo is null
        defaultItemChecklistShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemChecklistsByTituloContainsSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo contains DEFAULT_TITULO
        defaultItemChecklistShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the itemChecklistList where titulo contains UPDATED_TITULO
        defaultItemChecklistShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemChecklistsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);

        // Get all the itemChecklistList where titulo does not contain DEFAULT_TITULO
        defaultItemChecklistShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the itemChecklistList where titulo does not contain UPDATED_TITULO
        defaultItemChecklistShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllItemChecklistsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemChecklistRepository.saveAndFlush(itemChecklist);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        itemChecklist.setAnexo(anexo);
        itemChecklistRepository.saveAndFlush(itemChecklist);
        Long anexoId = anexo.getId();

        // Get all the itemChecklistList where anexo equals to anexoId
        defaultItemChecklistShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the itemChecklistList where anexo equals to anexoId + 1
        defaultItemChecklistShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemChecklistsByChecklistIsEqualToSomething() throws Exception {
        // Get already existing entity
        Checklist checklist = itemChecklist.getChecklist();
        itemChecklistRepository.saveAndFlush(itemChecklist);
        Long checklistId = checklist.getId();

        // Get all the itemChecklistList where checklist equals to checklistId
        defaultItemChecklistShouldBeFound("checklistId.equals=" + checklistId);

        // Get all the itemChecklistList where checklist equals to checklistId + 1
        defaultItemChecklistShouldNotBeFound("checklistId.equals=" + (checklistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemChecklistShouldBeFound(String filter) throws Exception {
        restItemChecklistMockMvc.perform(get("/api/item-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemChecklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restItemChecklistMockMvc.perform(get("/api/item-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemChecklistShouldNotBeFound(String filter) throws Exception {
        restItemChecklistMockMvc.perform(get("/api/item-checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemChecklistMockMvc.perform(get("/api/item-checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemChecklist() throws Exception {
        // Get the itemChecklist
        restItemChecklistMockMvc.perform(get("/api/item-checklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemChecklist() throws Exception {
        // Initialize the database
        itemChecklistService.save(itemChecklist);

        int databaseSizeBeforeUpdate = itemChecklistRepository.findAll().size();

        // Update the itemChecklist
        ItemChecklist updatedItemChecklist = itemChecklistRepository.findById(itemChecklist.getId()).get();
        // Disconnect from session so that the updates on updatedItemChecklist are not directly saved in db
        em.detach(updatedItemChecklist);
        updatedItemChecklist
            .ordem(UPDATED_ORDEM)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restItemChecklistMockMvc.perform(put("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemChecklist)))
            .andExpect(status().isOk());

        // Validate the ItemChecklist in the database
        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeUpdate);
        ItemChecklist testItemChecklist = itemChecklistList.get(itemChecklistList.size() - 1);
        assertThat(testItemChecklist.getOrdem()).isEqualTo(UPDATED_ORDEM);
        assertThat(testItemChecklist.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testItemChecklist.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingItemChecklist() throws Exception {
        int databaseSizeBeforeUpdate = itemChecklistRepository.findAll().size();

        // Create the ItemChecklist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemChecklistMockMvc.perform(put("/api/item-checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemChecklist)))
            .andExpect(status().isBadRequest());

        // Validate the ItemChecklist in the database
        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemChecklist() throws Exception {
        // Initialize the database
        itemChecklistService.save(itemChecklist);

        int databaseSizeBeforeDelete = itemChecklistRepository.findAll().size();

        // Delete the itemChecklist
        restItemChecklistMockMvc.perform(delete("/api/item-checklists/{id}", itemChecklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemChecklist> itemChecklistList = itemChecklistRepository.findAll();
        assertThat(itemChecklistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
