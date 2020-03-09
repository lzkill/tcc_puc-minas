package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.repository.ItemAuditoriaRepository;
import com.lzkill.sgq.service.ItemAuditoriaService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.ItemAuditoriaCriteria;
import com.lzkill.sgq.service.ItemAuditoriaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.lzkill.sgq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemAuditoriaResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class ItemAuditoriaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADO = false;
    private static final Boolean UPDATED_HABILITADO = true;

    @Autowired
    private ItemAuditoriaRepository itemAuditoriaRepository;

    @Mock
    private ItemAuditoriaRepository itemAuditoriaRepositoryMock;

    @Mock
    private ItemAuditoriaService itemAuditoriaServiceMock;

    @Autowired
    private ItemAuditoriaService itemAuditoriaService;

    @Autowired
    private ItemAuditoriaQueryService itemAuditoriaQueryService;

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

    private MockMvc restItemAuditoriaMockMvc;

    private ItemAuditoria itemAuditoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemAuditoriaResource itemAuditoriaResource = new ItemAuditoriaResource(itemAuditoriaService, itemAuditoriaQueryService);
        this.restItemAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemAuditoriaResource)
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
    public static ItemAuditoria createEntity(EntityManager em) {
        ItemAuditoria itemAuditoria = new ItemAuditoria()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .habilitado(DEFAULT_HABILITADO);
        return itemAuditoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemAuditoria createUpdatedEntity(EntityManager em) {
        ItemAuditoria itemAuditoria = new ItemAuditoria()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);
        return itemAuditoria;
    }

    @BeforeEach
    public void initTest() {
        itemAuditoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemAuditoria() throws Exception {
        int databaseSizeBeforeCreate = itemAuditoriaRepository.findAll().size();

        // Create the ItemAuditoria
        restItemAuditoriaMockMvc.perform(post("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemAuditoria)))
            .andExpect(status().isCreated());

        // Validate the ItemAuditoria in the database
        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemAuditoria testItemAuditoria = itemAuditoriaList.get(itemAuditoriaList.size() - 1);
        assertThat(testItemAuditoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testItemAuditoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testItemAuditoria.isHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    public void createItemAuditoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemAuditoriaRepository.findAll().size();

        // Create the ItemAuditoria with an existing ID
        itemAuditoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemAuditoriaMockMvc.perform(post("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ItemAuditoria in the database
        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemAuditoriaRepository.findAll().size();
        // set the field null
        itemAuditoria.setTitulo(null);

        // Create the ItemAuditoria, which fails.

        restItemAuditoriaMockMvc.perform(post("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemAuditoria)))
            .andExpect(status().isBadRequest());

        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHabilitadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemAuditoriaRepository.findAll().size();
        // set the field null
        itemAuditoria.setHabilitado(null);

        // Create the ItemAuditoria, which fails.

        restItemAuditoriaMockMvc.perform(post("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemAuditoria)))
            .andExpect(status().isBadRequest());

        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemAuditorias() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllItemAuditoriasWithEagerRelationshipsIsEnabled() throws Exception {
        ItemAuditoriaResource itemAuditoriaResource = new ItemAuditoriaResource(itemAuditoriaServiceMock, itemAuditoriaQueryService);
        when(itemAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restItemAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias?eagerload=true"))
        .andExpect(status().isOk());

        verify(itemAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllItemAuditoriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        ItemAuditoriaResource itemAuditoriaResource = new ItemAuditoriaResource(itemAuditoriaServiceMock, itemAuditoriaQueryService);
            when(itemAuditoriaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restItemAuditoriaMockMvc = MockMvcBuilders.standaloneSetup(itemAuditoriaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias?eagerload=true"))
        .andExpect(status().isOk());

            verify(itemAuditoriaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getItemAuditoria() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get the itemAuditoria
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias/{id}", itemAuditoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemAuditoria.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.habilitado").value(DEFAULT_HABILITADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getItemAuditoriasByIdFiltering() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        Long id = itemAuditoria.getId();

        defaultItemAuditoriaShouldBeFound("id.equals=" + id);
        defaultItemAuditoriaShouldNotBeFound("id.notEquals=" + id);

        defaultItemAuditoriaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemAuditoriaShouldNotBeFound("id.greaterThan=" + id);

        defaultItemAuditoriaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemAuditoriaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemAuditoriasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo equals to DEFAULT_TITULO
        defaultItemAuditoriaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the itemAuditoriaList where titulo equals to UPDATED_TITULO
        defaultItemAuditoriaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo not equals to DEFAULT_TITULO
        defaultItemAuditoriaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the itemAuditoriaList where titulo not equals to UPDATED_TITULO
        defaultItemAuditoriaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultItemAuditoriaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the itemAuditoriaList where titulo equals to UPDATED_TITULO
        defaultItemAuditoriaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo is not null
        defaultItemAuditoriaShouldBeFound("titulo.specified=true");

        // Get all the itemAuditoriaList where titulo is null
        defaultItemAuditoriaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemAuditoriasByTituloContainsSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo contains DEFAULT_TITULO
        defaultItemAuditoriaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the itemAuditoriaList where titulo contains UPDATED_TITULO
        defaultItemAuditoriaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where titulo does not contain DEFAULT_TITULO
        defaultItemAuditoriaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the itemAuditoriaList where titulo does not contain UPDATED_TITULO
        defaultItemAuditoriaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllItemAuditoriasByHabilitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where habilitado equals to DEFAULT_HABILITADO
        defaultItemAuditoriaShouldBeFound("habilitado.equals=" + DEFAULT_HABILITADO);

        // Get all the itemAuditoriaList where habilitado equals to UPDATED_HABILITADO
        defaultItemAuditoriaShouldNotBeFound("habilitado.equals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByHabilitadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where habilitado not equals to DEFAULT_HABILITADO
        defaultItemAuditoriaShouldNotBeFound("habilitado.notEquals=" + DEFAULT_HABILITADO);

        // Get all the itemAuditoriaList where habilitado not equals to UPDATED_HABILITADO
        defaultItemAuditoriaShouldBeFound("habilitado.notEquals=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByHabilitadoIsInShouldWork() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where habilitado in DEFAULT_HABILITADO or UPDATED_HABILITADO
        defaultItemAuditoriaShouldBeFound("habilitado.in=" + DEFAULT_HABILITADO + "," + UPDATED_HABILITADO);

        // Get all the itemAuditoriaList where habilitado equals to UPDATED_HABILITADO
        defaultItemAuditoriaShouldNotBeFound("habilitado.in=" + UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByHabilitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);

        // Get all the itemAuditoriaList where habilitado is not null
        defaultItemAuditoriaShouldBeFound("habilitado.specified=true");

        // Get all the itemAuditoriaList where habilitado is null
        defaultItemAuditoriaShouldNotBeFound("habilitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemAuditoriasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        itemAuditoria.setProcesso(processo);
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Long processoId = processo.getId();

        // Get all the itemAuditoriaList where processo equals to processoId
        defaultItemAuditoriaShouldBeFound("processoId.equals=" + processoId);

        // Get all the itemAuditoriaList where processo equals to processoId + 1
        defaultItemAuditoriaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemAuditoriasByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        itemAuditoria.addAnexo(anexo);
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Long anexoId = anexo.getId();

        // Get all the itemAuditoriaList where anexo equals to anexoId
        defaultItemAuditoriaShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the itemAuditoriaList where anexo equals to anexoId + 1
        defaultItemAuditoriaShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemAuditoriasByAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Auditoria auditoria = AuditoriaResourceIT.createEntity(em);
        em.persist(auditoria);
        em.flush();
        itemAuditoria.addAuditoria(auditoria);
        itemAuditoriaRepository.saveAndFlush(itemAuditoria);
        Long auditoriaId = auditoria.getId();

        // Get all the itemAuditoriaList where auditoria equals to auditoriaId
        defaultItemAuditoriaShouldBeFound("auditoriaId.equals=" + auditoriaId);

        // Get all the itemAuditoriaList where auditoria equals to auditoriaId + 1
        defaultItemAuditoriaShouldNotBeFound("auditoriaId.equals=" + (auditoriaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemAuditoriaShouldBeFound(String filter) throws Exception {
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemAuditoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));

        // Check, that the count call also returns 1
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemAuditoriaShouldNotBeFound(String filter) throws Exception {
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemAuditoria() throws Exception {
        // Get the itemAuditoria
        restItemAuditoriaMockMvc.perform(get("/api/item-auditorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemAuditoria() throws Exception {
        // Initialize the database
        itemAuditoriaService.save(itemAuditoria);

        int databaseSizeBeforeUpdate = itemAuditoriaRepository.findAll().size();

        // Update the itemAuditoria
        ItemAuditoria updatedItemAuditoria = itemAuditoriaRepository.findById(itemAuditoria.getId()).get();
        // Disconnect from session so that the updates on updatedItemAuditoria are not directly saved in db
        em.detach(updatedItemAuditoria);
        updatedItemAuditoria
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .habilitado(UPDATED_HABILITADO);

        restItemAuditoriaMockMvc.perform(put("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemAuditoria)))
            .andExpect(status().isOk());

        // Validate the ItemAuditoria in the database
        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeUpdate);
        ItemAuditoria testItemAuditoria = itemAuditoriaList.get(itemAuditoriaList.size() - 1);
        assertThat(testItemAuditoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testItemAuditoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testItemAuditoria.isHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    public void updateNonExistingItemAuditoria() throws Exception {
        int databaseSizeBeforeUpdate = itemAuditoriaRepository.findAll().size();

        // Create the ItemAuditoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemAuditoriaMockMvc.perform(put("/api/item-auditorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemAuditoria)))
            .andExpect(status().isBadRequest());

        // Validate the ItemAuditoria in the database
        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemAuditoria() throws Exception {
        // Initialize the database
        itemAuditoriaService.save(itemAuditoria);

        int databaseSizeBeforeDelete = itemAuditoriaRepository.findAll().size();

        // Delete the itemAuditoria
        restItemAuditoriaMockMvc.perform(delete("/api/item-auditorias/{id}", itemAuditoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemAuditoria> itemAuditoriaList = itemAuditoriaRepository.findAll();
        assertThat(itemAuditoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
