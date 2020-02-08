package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.Empresa;
import com.lzkill.sgq.repository.SetorRepository;
import com.lzkill.sgq.service.SetorService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.SetorCriteria;
import com.lzkill.sgq.service.SetorQueryService;

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
 * Integration tests for the {@link SetorResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class SetorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SetorService setorService;

    @Autowired
    private SetorQueryService setorQueryService;

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

    private MockMvc restSetorMockMvc;

    private Setor setor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SetorResource setorResource = new SetorResource(setorService, setorQueryService);
        this.restSetorMockMvc = MockMvcBuilders.standaloneSetup(setorResource)
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
    public static Setor createEntity(EntityManager em) {
        Setor setor = new Setor()
            .nome(DEFAULT_NOME);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        setor.setEmpresa(empresa);
        return setor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setor createUpdatedEntity(EntityManager em) {
        Setor setor = new Setor()
            .nome(UPDATED_NOME);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        setor.setEmpresa(empresa);
        return setor;
    }

    @BeforeEach
    public void initTest() {
        setor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetor() throws Exception {
        int databaseSizeBeforeCreate = setorRepository.findAll().size();

        // Create the Setor
        restSetorMockMvc.perform(post("/api/setors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setor)))
            .andExpect(status().isCreated());

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeCreate + 1);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createSetorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = setorRepository.findAll().size();

        // Create the Setor with an existing ID
        setor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSetorMockMvc.perform(post("/api/setors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setor)))
            .andExpect(status().isBadRequest());

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setorRepository.findAll().size();
        // set the field null
        setor.setNome(null);

        // Create the Setor, which fails.

        restSetorMockMvc.perform(post("/api/setors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setor)))
            .andExpect(status().isBadRequest());

        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetors() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList
        restSetorMockMvc.perform(get("/api/setors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getSetor() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get the setor
        restSetorMockMvc.perform(get("/api/setors/{id}", setor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(setor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getSetorsByIdFiltering() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        Long id = setor.getId();

        defaultSetorShouldBeFound("id.equals=" + id);
        defaultSetorShouldNotBeFound("id.notEquals=" + id);

        defaultSetorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSetorShouldNotBeFound("id.greaterThan=" + id);

        defaultSetorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSetorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSetorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome equals to DEFAULT_NOME
        defaultSetorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the setorList where nome equals to UPDATED_NOME
        defaultSetorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSetorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome not equals to DEFAULT_NOME
        defaultSetorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the setorList where nome not equals to UPDATED_NOME
        defaultSetorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSetorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultSetorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the setorList where nome equals to UPDATED_NOME
        defaultSetorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSetorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome is not null
        defaultSetorShouldBeFound("nome.specified=true");

        // Get all the setorList where nome is null
        defaultSetorShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllSetorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome contains DEFAULT_NOME
        defaultSetorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the setorList where nome contains UPDATED_NOME
        defaultSetorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSetorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);

        // Get all the setorList where nome does not contain DEFAULT_NOME
        defaultSetorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the setorList where nome does not contain UPDATED_NOME
        defaultSetorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllSetorsByChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);
        Checklist checklist = ChecklistResourceIT.createEntity(em);
        em.persist(checklist);
        em.flush();
        setor.addChecklist(checklist);
        setorRepository.saveAndFlush(setor);
        Long checklistId = checklist.getId();

        // Get all the setorList where checklist equals to checklistId
        defaultSetorShouldBeFound("checklistId.equals=" + checklistId);

        // Get all the setorList where checklist equals to checklistId + 1
        defaultSetorShouldNotBeFound("checklistId.equals=" + (checklistId + 1));
    }


    @Test
    @Transactional
    public void getAllSetorsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        setorRepository.saveAndFlush(setor);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        setor.addProcesso(processo);
        setorRepository.saveAndFlush(setor);
        Long processoId = processo.getId();

        // Get all the setorList where processo equals to processoId
        defaultSetorShouldBeFound("processoId.equals=" + processoId);

        // Get all the setorList where processo equals to processoId + 1
        defaultSetorShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }


    @Test
    @Transactional
    public void getAllSetorsByEmpresaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Empresa empresa = setor.getEmpresa();
        setorRepository.saveAndFlush(setor);
        Long empresaId = empresa.getId();

        // Get all the setorList where empresa equals to empresaId
        defaultSetorShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the setorList where empresa equals to empresaId + 1
        defaultSetorShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSetorShouldBeFound(String filter) throws Exception {
        restSetorMockMvc.perform(get("/api/setors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restSetorMockMvc.perform(get("/api/setors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSetorShouldNotBeFound(String filter) throws Exception {
        restSetorMockMvc.perform(get("/api/setors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSetorMockMvc.perform(get("/api/setors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSetor() throws Exception {
        // Get the setor
        restSetorMockMvc.perform(get("/api/setors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetor() throws Exception {
        // Initialize the database
        setorService.save(setor);

        int databaseSizeBeforeUpdate = setorRepository.findAll().size();

        // Update the setor
        Setor updatedSetor = setorRepository.findById(setor.getId()).get();
        // Disconnect from session so that the updates on updatedSetor are not directly saved in db
        em.detach(updatedSetor);
        updatedSetor
            .nome(UPDATED_NOME);

        restSetorMockMvc.perform(put("/api/setors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSetor)))
            .andExpect(status().isOk());

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().size();

        // Create the Setor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSetorMockMvc.perform(put("/api/setors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setor)))
            .andExpect(status().isBadRequest());

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSetor() throws Exception {
        // Initialize the database
        setorService.save(setor);

        int databaseSizeBeforeDelete = setorRepository.findAll().size();

        // Delete the setor
        restSetorMockMvc.perform(delete("/api/setors/{id}", setor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Setor> setorList = setorRepository.findAll();
        assertThat(setorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
