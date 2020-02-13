package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.SgqApp;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.domain.CampanhaRecall;
import com.lzkill.sgq.domain.EventoOperacional;
import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.Produto;
import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.repository.AnexoRepository;
import com.lzkill.sgq.service.AnexoService;
import com.lzkill.sgq.web.rest.errors.ExceptionTranslator;
import com.lzkill.sgq.service.dto.AnexoCriteria;
import com.lzkill.sgq.service.AnexoQueryService;

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
 * Integration tests for the {@link AnexoResource} REST controller.
 */
@SpringBootTest(classes = SgqApp.class)
public class AnexoResourceIT {

    private static final String DEFAULT_NOME_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ARQUIVO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTEUDO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTEUDO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTEUDO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTEUDO_CONTENT_TYPE = "image/png";

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoService anexoService;

    @Autowired
    private AnexoQueryService anexoQueryService;

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

    private MockMvc restAnexoMockMvc;

    private Anexo anexo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnexoResource anexoResource = new AnexoResource(anexoService, anexoQueryService);
        this.restAnexoMockMvc = MockMvcBuilders.standaloneSetup(anexoResource)
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
    public static Anexo createEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .nomeArquivo(DEFAULT_NOME_ARQUIVO)
            .conteudo(DEFAULT_CONTEUDO)
            .conteudoContentType(DEFAULT_CONTEUDO_CONTENT_TYPE);
        return anexo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anexo createUpdatedEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .nomeArquivo(UPDATED_NOME_ARQUIVO)
            .conteudo(UPDATED_CONTEUDO)
            .conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);
        return anexo;
    }

    @BeforeEach
    public void initTest() {
        anexo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isCreated());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getNomeArquivo()).isEqualTo(DEFAULT_NOME_ARQUIVO);
        assertThat(testAnexo.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testAnexo.getConteudoContentType()).isEqualTo(DEFAULT_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAnexoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo with an existing ID
        anexo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeArquivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexoRepository.findAll().size();
        // set the field null
        anexo.setNomeArquivo(null);

        // Create the Anexo, which fails.

        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isBadRequest());

        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnexos() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeArquivo").value(hasItem(DEFAULT_NOME_ARQUIVO)))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));
    }
    
    @Test
    @Transactional
    public void getAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexo.getId().intValue()))
            .andExpect(jsonPath("$.nomeArquivo").value(DEFAULT_NOME_ARQUIVO))
            .andExpect(jsonPath("$.conteudoContentType").value(DEFAULT_CONTEUDO_CONTENT_TYPE))
            .andExpect(jsonPath("$.conteudo").value(Base64Utils.encodeToString(DEFAULT_CONTEUDO)));
    }


    @Test
    @Transactional
    public void getAnexosByIdFiltering() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        Long id = anexo.getId();

        defaultAnexoShouldBeFound("id.equals=" + id);
        defaultAnexoShouldNotBeFound("id.notEquals=" + id);

        defaultAnexoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnexoShouldNotBeFound("id.greaterThan=" + id);

        defaultAnexoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnexoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnexosByNomeArquivoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo equals to DEFAULT_NOME_ARQUIVO
        defaultAnexoShouldBeFound("nomeArquivo.equals=" + DEFAULT_NOME_ARQUIVO);

        // Get all the anexoList where nomeArquivo equals to UPDATED_NOME_ARQUIVO
        defaultAnexoShouldNotBeFound("nomeArquivo.equals=" + UPDATED_NOME_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllAnexosByNomeArquivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo not equals to DEFAULT_NOME_ARQUIVO
        defaultAnexoShouldNotBeFound("nomeArquivo.notEquals=" + DEFAULT_NOME_ARQUIVO);

        // Get all the anexoList where nomeArquivo not equals to UPDATED_NOME_ARQUIVO
        defaultAnexoShouldBeFound("nomeArquivo.notEquals=" + UPDATED_NOME_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllAnexosByNomeArquivoIsInShouldWork() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo in DEFAULT_NOME_ARQUIVO or UPDATED_NOME_ARQUIVO
        defaultAnexoShouldBeFound("nomeArquivo.in=" + DEFAULT_NOME_ARQUIVO + "," + UPDATED_NOME_ARQUIVO);

        // Get all the anexoList where nomeArquivo equals to UPDATED_NOME_ARQUIVO
        defaultAnexoShouldNotBeFound("nomeArquivo.in=" + UPDATED_NOME_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllAnexosByNomeArquivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo is not null
        defaultAnexoShouldBeFound("nomeArquivo.specified=true");

        // Get all the anexoList where nomeArquivo is null
        defaultAnexoShouldNotBeFound("nomeArquivo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnexosByNomeArquivoContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo contains DEFAULT_NOME_ARQUIVO
        defaultAnexoShouldBeFound("nomeArquivo.contains=" + DEFAULT_NOME_ARQUIVO);

        // Get all the anexoList where nomeArquivo contains UPDATED_NOME_ARQUIVO
        defaultAnexoShouldNotBeFound("nomeArquivo.contains=" + UPDATED_NOME_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllAnexosByNomeArquivoNotContainsSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList where nomeArquivo does not contain DEFAULT_NOME_ARQUIVO
        defaultAnexoShouldNotBeFound("nomeArquivo.doesNotContain=" + DEFAULT_NOME_ARQUIVO);

        // Get all the anexoList where nomeArquivo does not contain UPDATED_NOME_ARQUIVO
        defaultAnexoShouldBeFound("nomeArquivo.doesNotContain=" + UPDATED_NOME_ARQUIVO);
    }


    @Test
    @Transactional
    public void getAllAnexosByAcaoSGQIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        AcaoSGQ acaoSGQ = AcaoSGQResourceIT.createEntity(em);
        em.persist(acaoSGQ);
        em.flush();
        anexo.setAcaoSGQ(acaoSGQ);
        anexoRepository.saveAndFlush(anexo);
        Long acaoSGQId = acaoSGQ.getId();

        // Get all the anexoList where acaoSGQ equals to acaoSGQId
        defaultAnexoShouldBeFound("acaoSGQId.equals=" + acaoSGQId);

        // Get all the anexoList where acaoSGQ equals to acaoSGQId + 1
        defaultAnexoShouldNotBeFound("acaoSGQId.equals=" + (acaoSGQId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByAnaliseConsultoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        AnaliseConsultoria analiseConsultoria = AnaliseConsultoriaResourceIT.createEntity(em);
        em.persist(analiseConsultoria);
        em.flush();
        anexo.setAnaliseConsultoria(analiseConsultoria);
        anexoRepository.saveAndFlush(anexo);
        Long analiseConsultoriaId = analiseConsultoria.getId();

        // Get all the anexoList where analiseConsultoria equals to analiseConsultoriaId
        defaultAnexoShouldBeFound("analiseConsultoriaId.equals=" + analiseConsultoriaId);

        // Get all the anexoList where analiseConsultoria equals to analiseConsultoriaId + 1
        defaultAnexoShouldNotBeFound("analiseConsultoriaId.equals=" + (analiseConsultoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        Checklist checklist = ChecklistResourceIT.createEntity(em);
        em.persist(checklist);
        em.flush();
        anexo.setChecklist(checklist);
        anexoRepository.saveAndFlush(anexo);
        Long checklistId = checklist.getId();

        // Get all the anexoList where checklist equals to checklistId
        defaultAnexoShouldBeFound("checklistId.equals=" + checklistId);

        // Get all the anexoList where checklist equals to checklistId + 1
        defaultAnexoShouldNotBeFound("checklistId.equals=" + (checklistId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByBoletimInformativoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        BoletimInformativo boletimInformativo = BoletimInformativoResourceIT.createEntity(em);
        em.persist(boletimInformativo);
        em.flush();
        anexo.setBoletimInformativo(boletimInformativo);
        anexoRepository.saveAndFlush(anexo);
        Long boletimInformativoId = boletimInformativo.getId();

        // Get all the anexoList where boletimInformativo equals to boletimInformativoId
        defaultAnexoShouldBeFound("boletimInformativoId.equals=" + boletimInformativoId);

        // Get all the anexoList where boletimInformativo equals to boletimInformativoId + 1
        defaultAnexoShouldNotBeFound("boletimInformativoId.equals=" + (boletimInformativoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByCampanhaRecallIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        CampanhaRecall campanhaRecall = CampanhaRecallResourceIT.createEntity(em);
        em.persist(campanhaRecall);
        em.flush();
        anexo.setCampanhaRecall(campanhaRecall);
        anexoRepository.saveAndFlush(anexo);
        Long campanhaRecallId = campanhaRecall.getId();

        // Get all the anexoList where campanhaRecall equals to campanhaRecallId
        defaultAnexoShouldBeFound("campanhaRecallId.equals=" + campanhaRecallId);

        // Get all the anexoList where campanhaRecall equals to campanhaRecallId + 1
        defaultAnexoShouldNotBeFound("campanhaRecallId.equals=" + (campanhaRecallId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByEventoOperacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        EventoOperacional eventoOperacional = EventoOperacionalResourceIT.createEntity(em);
        em.persist(eventoOperacional);
        em.flush();
        anexo.setEventoOperacional(eventoOperacional);
        anexoRepository.saveAndFlush(anexo);
        Long eventoOperacionalId = eventoOperacional.getId();

        // Get all the anexoList where eventoOperacional equals to eventoOperacionalId
        defaultAnexoShouldBeFound("eventoOperacionalId.equals=" + eventoOperacionalId);

        // Get all the anexoList where eventoOperacional equals to eventoOperacionalId + 1
        defaultAnexoShouldNotBeFound("eventoOperacionalId.equals=" + (eventoOperacionalId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByItemChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        ItemChecklist itemChecklist = ItemChecklistResourceIT.createEntity(em);
        em.persist(itemChecklist);
        em.flush();
        anexo.setItemChecklist(itemChecklist);
        anexoRepository.saveAndFlush(anexo);
        Long itemChecklistId = itemChecklist.getId();

        // Get all the anexoList where itemChecklist equals to itemChecklistId
        defaultAnexoShouldBeFound("itemChecklistId.equals=" + itemChecklistId);

        // Get all the anexoList where itemChecklist equals to itemChecklistId + 1
        defaultAnexoShouldNotBeFound("itemChecklistId.equals=" + (itemChecklistId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByItemPlanoAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        ItemPlanoAuditoria itemPlanoAuditoria = ItemPlanoAuditoriaResourceIT.createEntity(em);
        em.persist(itemPlanoAuditoria);
        em.flush();
        anexo.setItemPlanoAuditoria(itemPlanoAuditoria);
        anexoRepository.saveAndFlush(anexo);
        Long itemPlanoAuditoriaId = itemPlanoAuditoria.getId();

        // Get all the anexoList where itemPlanoAuditoria equals to itemPlanoAuditoriaId
        defaultAnexoShouldBeFound("itemPlanoAuditoriaId.equals=" + itemPlanoAuditoriaId);

        // Get all the anexoList where itemPlanoAuditoria equals to itemPlanoAuditoriaId + 1
        defaultAnexoShouldNotBeFound("itemPlanoAuditoriaId.equals=" + (itemPlanoAuditoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByNaoConformidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        NaoConformidade naoConformidade = NaoConformidadeResourceIT.createEntity(em);
        em.persist(naoConformidade);
        em.flush();
        anexo.setNaoConformidade(naoConformidade);
        anexoRepository.saveAndFlush(anexo);
        Long naoConformidadeId = naoConformidade.getId();

        // Get all the anexoList where naoConformidade equals to naoConformidadeId
        defaultAnexoShouldBeFound("naoConformidadeId.equals=" + naoConformidadeId);

        // Get all the anexoList where naoConformidade equals to naoConformidadeId + 1
        defaultAnexoShouldNotBeFound("naoConformidadeId.equals=" + (naoConformidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        anexo.setProcesso(processo);
        anexoRepository.saveAndFlush(anexo);
        Long processoId = processo.getId();

        // Get all the anexoList where processo equals to processoId
        defaultAnexoShouldBeFound("processoId.equals=" + processoId);

        // Get all the anexoList where processo equals to processoId + 1
        defaultAnexoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        anexo.setProduto(produto);
        anexoRepository.saveAndFlush(anexo);
        Long produtoId = produto.getId();

        // Get all the anexoList where produto equals to produtoId
        defaultAnexoShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the anexoList where produto equals to produtoId + 1
        defaultAnexoShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByPlanoAuditoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        PlanoAuditoria planoAuditoria = PlanoAuditoriaResourceIT.createEntity(em);
        em.persist(planoAuditoria);
        em.flush();
        anexo.setPlanoAuditoria(planoAuditoria);
        anexoRepository.saveAndFlush(anexo);
        Long planoAuditoriaId = planoAuditoria.getId();

        // Get all the anexoList where planoAuditoria equals to planoAuditoriaId
        defaultAnexoShouldBeFound("planoAuditoriaId.equals=" + planoAuditoriaId);

        // Get all the anexoList where planoAuditoria equals to planoAuditoriaId + 1
        defaultAnexoShouldNotBeFound("planoAuditoriaId.equals=" + (planoAuditoriaId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByProdutoNaoConformeIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        ProdutoNaoConforme produtoNaoConforme = ProdutoNaoConformeResourceIT.createEntity(em);
        em.persist(produtoNaoConforme);
        em.flush();
        anexo.setProdutoNaoConforme(produtoNaoConforme);
        anexoRepository.saveAndFlush(anexo);
        Long produtoNaoConformeId = produtoNaoConforme.getId();

        // Get all the anexoList where produtoNaoConforme equals to produtoNaoConformeId
        defaultAnexoShouldBeFound("produtoNaoConformeId.equals=" + produtoNaoConformeId);

        // Get all the anexoList where produtoNaoConforme equals to produtoNaoConformeId + 1
        defaultAnexoShouldNotBeFound("produtoNaoConformeId.equals=" + (produtoNaoConformeId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByPublicacaoFeedIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        PublicacaoFeed publicacaoFeed = PublicacaoFeedResourceIT.createEntity(em);
        em.persist(publicacaoFeed);
        em.flush();
        anexo.setPublicacaoFeed(publicacaoFeed);
        anexoRepository.saveAndFlush(anexo);
        Long publicacaoFeedId = publicacaoFeed.getId();

        // Get all the anexoList where publicacaoFeed equals to publicacaoFeedId
        defaultAnexoShouldBeFound("publicacaoFeedId.equals=" + publicacaoFeedId);

        // Get all the anexoList where publicacaoFeed equals to publicacaoFeedId + 1
        defaultAnexoShouldNotBeFound("publicacaoFeedId.equals=" + (publicacaoFeedId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByResultadoChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        ResultadoChecklist resultadoChecklist = ResultadoChecklistResourceIT.createEntity(em);
        em.persist(resultadoChecklist);
        em.flush();
        anexo.setResultadoChecklist(resultadoChecklist);
        anexoRepository.saveAndFlush(anexo);
        Long resultadoChecklistId = resultadoChecklist.getId();

        // Get all the anexoList where resultadoChecklist equals to resultadoChecklistId
        defaultAnexoShouldBeFound("resultadoChecklistId.equals=" + resultadoChecklistId);

        // Get all the anexoList where resultadoChecklist equals to resultadoChecklistId + 1
        defaultAnexoShouldNotBeFound("resultadoChecklistId.equals=" + (resultadoChecklistId + 1));
    }


    @Test
    @Transactional
    public void getAllAnexosByResultadoItemChecklistIsEqualToSomething() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        ResultadoItemChecklist resultadoItemChecklist = ResultadoItemChecklistResourceIT.createEntity(em);
        em.persist(resultadoItemChecklist);
        em.flush();
        anexo.setResultadoItemChecklist(resultadoItemChecklist);
        anexoRepository.saveAndFlush(anexo);
        Long resultadoItemChecklistId = resultadoItemChecklist.getId();

        // Get all the anexoList where resultadoItemChecklist equals to resultadoItemChecklistId
        defaultAnexoShouldBeFound("resultadoItemChecklistId.equals=" + resultadoItemChecklistId);

        // Get all the anexoList where resultadoItemChecklist equals to resultadoItemChecklistId + 1
        defaultAnexoShouldNotBeFound("resultadoItemChecklistId.equals=" + (resultadoItemChecklistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoShouldBeFound(String filter) throws Exception {
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeArquivo").value(hasItem(DEFAULT_NOME_ARQUIVO)))
            .andExpect(jsonPath("$.[*].conteudoContentType").value(hasItem(DEFAULT_CONTEUDO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTEUDO))));

        // Check, that the count call also returns 1
        restAnexoMockMvc.perform(get("/api/anexos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoShouldNotBeFound(String filter) throws Exception {
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoMockMvc.perform(get("/api/anexos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnexo() throws Exception {
        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo() throws Exception {
        // Initialize the database
        anexoService.save(anexo);

        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Update the anexo
        Anexo updatedAnexo = anexoRepository.findById(anexo.getId()).get();
        // Disconnect from session so that the updates on updatedAnexo are not directly saved in db
        em.detach(updatedAnexo);
        updatedAnexo
            .nomeArquivo(UPDATED_NOME_ARQUIVO)
            .conteudo(UPDATED_CONTEUDO)
            .conteudoContentType(UPDATED_CONTEUDO_CONTENT_TYPE);

        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnexo)))
            .andExpect(status().isOk());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getNomeArquivo()).isEqualTo(UPDATED_NOME_ARQUIVO);
        assertThat(testAnexo.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testAnexo.getConteudoContentType()).isEqualTo(UPDATED_CONTEUDO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo() throws Exception {
        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Create the Anexo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnexo() throws Exception {
        // Initialize the database
        anexoService.save(anexo);

        int databaseSizeBeforeDelete = anexoRepository.findAll().size();

        // Delete the anexo
        restAnexoMockMvc.perform(delete("/api/anexos/{id}", anexo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
