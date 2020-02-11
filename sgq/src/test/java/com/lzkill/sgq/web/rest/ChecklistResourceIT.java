package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.repository.ChecklistRepository;
import com.lzkill.sgq.service.ChecklistService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ChecklistCriteria;
import com.lzkill.sgq.service.ChecklistQueryService;

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

import com.lzkill.sgq.domain.enumeration.Periodicidade;
/**
 * Integration tests for the {@link ChecklistResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ChecklistResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Periodicidade DEFAULT_PERIODICIDADE = Periodicidade.DIARIO;
    private static final Periodicidade UPDATED_PERIODICIDADE = Periodicidade.SEMANAL;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistQueryService checklistQueryService;

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

    private MockMvc restChecklistMockMvc;

    private Checklist checklist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChecklistResource checklistResource = new ChecklistResource(checklistService, checklistQueryService);
        this.restChecklistMockMvc = MockMvcBuilders.standaloneSetup(checklistResource)
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
    public static Checklist createEntity(EntityManager em) {
        Checklist checklist = new Checklist()
            .titulo(DEFAULT_TITULO)
            .periodicidade(DEFAULT_PERIODICIDADE);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        checklist.setSetor(setor);
        return checklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checklist createUpdatedEntity(EntityManager em) {
        Checklist checklist = new Checklist()
            .titulo(UPDATED_TITULO)
            .periodicidade(UPDATED_PERIODICIDADE);
        // Add required entity
        Setor setor;
        if (TestUtil.findAll(em, Setor.class).isEmpty()) {
            setor = SetorResourceIT.createUpdatedEntity(em);
            em.persist(setor);
            em.flush();
        } else {
            setor = TestUtil.findAll(em, Setor.class).get(0);
        }
        checklist.setSetor(setor);
        return checklist;
    }

    @BeforeEach
    public void initTest() {
        checklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createChecklist() throws Exception {
        int databaseSizeBeforeCreate = checklistRepository.findAll().size();

        // Create the Checklist
        restChecklistMockMvc.perform(post("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklist)))
            .andExpect(status().isCreated());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeCreate + 1);
        Checklist testChecklist = checklistList.get(checklistList.size() - 1);
        assertThat(testChecklist.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testChecklist.getPeriodicidade()).isEqualTo(DEFAULT_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void createChecklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checklistRepository.findAll().size();

        // Create the Checklist with an existing ID
        checklist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChecklistMockMvc.perform(post("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklist)))
            .andExpect(status().isBadRequest());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = checklistRepository.findAll().size();
        // set the field null
        checklist.setTitulo(null);

        // Create the Checklist, which fails.

        restChecklistMockMvc.perform(post("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklist)))
            .andExpect(status().isBadRequest());

        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChecklists() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].periodicidade").value(hasItem(DEFAULT_PERIODICIDADE.toString())));
    }
    
    @Test
    @Transactional
    public void getChecklist() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get the checklist
        restChecklistMockMvc.perform(get("/api/checklists/{id}", checklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checklist.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.periodicidade").value(DEFAULT_PERIODICIDADE.toString()));
    }


    @Test
    @Transactional
    public void getChecklistsByIdFiltering() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        Long id = checklist.getId();

        defaultChecklistShouldBeFound("id.equals=" + id);
        defaultChecklistShouldNotBeFound("id.notEquals=" + id);

        defaultChecklistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChecklistShouldNotBeFound("id.greaterThan=" + id);

        defaultChecklistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChecklistShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChecklistsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo equals to DEFAULT_TITULO
        defaultChecklistShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the checklistList where titulo equals to UPDATED_TITULO
        defaultChecklistShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChecklistsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo not equals to DEFAULT_TITULO
        defaultChecklistShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the checklistList where titulo not equals to UPDATED_TITULO
        defaultChecklistShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChecklistsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultChecklistShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the checklistList where titulo equals to UPDATED_TITULO
        defaultChecklistShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChecklistsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo is not null
        defaultChecklistShouldBeFound("titulo.specified=true");

        // Get all the checklistList where titulo is null
        defaultChecklistShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllChecklistsByTituloContainsSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo contains DEFAULT_TITULO
        defaultChecklistShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the checklistList where titulo contains UPDATED_TITULO
        defaultChecklistShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChecklistsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where titulo does not contain DEFAULT_TITULO
        defaultChecklistShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the checklistList where titulo does not contain UPDATED_TITULO
        defaultChecklistShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllChecklistsByPeriodicidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where periodicidade equals to DEFAULT_PERIODICIDADE
        defaultChecklistShouldBeFound("periodicidade.equals=" + DEFAULT_PERIODICIDADE);

        // Get all the checklistList where periodicidade equals to UPDATED_PERIODICIDADE
        defaultChecklistShouldNotBeFound("periodicidade.equals=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllChecklistsByPeriodicidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where periodicidade not equals to DEFAULT_PERIODICIDADE
        defaultChecklistShouldNotBeFound("periodicidade.notEquals=" + DEFAULT_PERIODICIDADE);

        // Get all the checklistList where periodicidade not equals to UPDATED_PERIODICIDADE
        defaultChecklistShouldBeFound("periodicidade.notEquals=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllChecklistsByPeriodicidadeIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where periodicidade in DEFAULT_PERIODICIDADE or UPDATED_PERIODICIDADE
        defaultChecklistShouldBeFound("periodicidade.in=" + DEFAULT_PERIODICIDADE + "," + UPDATED_PERIODICIDADE);

        // Get all the checklistList where periodicidade equals to UPDATED_PERIODICIDADE
        defaultChecklistShouldNotBeFound("periodicidade.in=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllChecklistsByPeriodicidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where periodicidade is not null
        defaultChecklistShouldBeFound("periodicidade.specified=true");

        // Get all the checklistList where periodicidade is null
        defaultChecklistShouldNotBeFound("periodicidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        checklist.addAnexo(anexo);
        checklistRepository.saveAndFlush(checklist);
        Long anexoId = anexo.getId();

        // Get all the checklistList where anexo equals to anexoId
        defaultChecklistShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the checklistList where anexo equals to anexoId + 1
        defaultChecklistShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllChecklistsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);
        ItemChecklist item = ItemChecklistResourceIT.createEntity(em);
        em.persist(item);
        em.flush();
        checklist.addItem(item);
        checklistRepository.saveAndFlush(checklist);
        Long itemId = item.getId();

        // Get all the checklistList where item equals to itemId
        defaultChecklistShouldBeFound("itemId.equals=" + itemId);

        // Get all the checklistList where item equals to itemId + 1
        defaultChecklistShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllChecklistsBySetorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Setor setor = checklist.getSetor();
        checklistRepository.saveAndFlush(checklist);
        Long setorId = setor.getId();

        // Get all the checklistList where setor equals to setorId
        defaultChecklistShouldBeFound("setorId.equals=" + setorId);

        // Get all the checklistList where setor equals to setorId + 1
        defaultChecklistShouldNotBeFound("setorId.equals=" + (setorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChecklistShouldBeFound(String filter) throws Exception {
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].periodicidade").value(hasItem(DEFAULT_PERIODICIDADE.toString())));

        // Check, that the count call also returns 1
        restChecklistMockMvc.perform(get("/api/checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChecklistShouldNotBeFound(String filter) throws Exception {
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChecklistMockMvc.perform(get("/api/checklists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChecklist() throws Exception {
        // Get the checklist
        restChecklistMockMvc.perform(get("/api/checklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChecklist() throws Exception {
        // Initialize the database
        checklistService.save(checklist);

        int databaseSizeBeforeUpdate = checklistRepository.findAll().size();

        // Update the checklist
        Checklist updatedChecklist = checklistRepository.findById(checklist.getId()).get();
        // Disconnect from session so that the updates on updatedChecklist are not directly saved in db
        em.detach(updatedChecklist);
        updatedChecklist
            .titulo(UPDATED_TITULO)
            .periodicidade(UPDATED_PERIODICIDADE);

        restChecklistMockMvc.perform(put("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChecklist)))
            .andExpect(status().isOk());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeUpdate);
        Checklist testChecklist = checklistList.get(checklistList.size() - 1);
        assertThat(testChecklist.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testChecklist.getPeriodicidade()).isEqualTo(UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingChecklist() throws Exception {
        int databaseSizeBeforeUpdate = checklistRepository.findAll().size();

        // Create the Checklist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChecklistMockMvc.perform(put("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklist)))
            .andExpect(status().isBadRequest());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChecklist() throws Exception {
        // Initialize the database
        checklistService.save(checklist);

        int databaseSizeBeforeDelete = checklistRepository.findAll().size();

        // Delete the checklist
        restChecklistMockMvc.perform(delete("/api/checklists/{id}", checklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
