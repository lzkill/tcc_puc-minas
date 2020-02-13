package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anexo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    
    @Lob
    @Column(name = "conteudo", nullable = false)
    private byte[] conteudo;

    @Column(name = "conteudo_content_type", nullable = false)
    private String conteudoContentType;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private AcaoSGQ acaoSGQ;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private AnaliseConsultoria analiseConsultoria;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private Checklist checklist;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private BoletimInformativo boletimInformativo;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private CampanhaRecall campanhaRecall;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private EventoOperacional eventoOperacional;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private ItemChecklist itemChecklist;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private ItemPlanoAuditoria itemPlanoAuditoria;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private NaoConformidade naoConformidade;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private Processo processo;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private PlanoAuditoria planoAuditoria;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private ProdutoNaoConforme produtoNaoConforme;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private PublicacaoFeed publicacaoFeed;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private ResultadoChecklist resultadoChecklist;

    @ManyToOne
    @JsonIgnoreProperties("anexos")
    private ResultadoItemChecklist resultadoItemChecklist;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public Anexo nomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        return this;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public Anexo conteudo(byte[] conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudoContentType() {
        return conteudoContentType;
    }

    public Anexo conteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
        return this;
    }

    public void setConteudoContentType(String conteudoContentType) {
        this.conteudoContentType = conteudoContentType;
    }

    public AcaoSGQ getAcaoSGQ() {
        return acaoSGQ;
    }

    public Anexo acaoSGQ(AcaoSGQ acaoSGQ) {
        this.acaoSGQ = acaoSGQ;
        return this;
    }

    public void setAcaoSGQ(AcaoSGQ acaoSGQ) {
        this.acaoSGQ = acaoSGQ;
    }

    public AnaliseConsultoria getAnaliseConsultoria() {
        return analiseConsultoria;
    }

    public Anexo analiseConsultoria(AnaliseConsultoria analiseConsultoria) {
        this.analiseConsultoria = analiseConsultoria;
        return this;
    }

    public void setAnaliseConsultoria(AnaliseConsultoria analiseConsultoria) {
        this.analiseConsultoria = analiseConsultoria;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public Anexo checklist(Checklist checklist) {
        this.checklist = checklist;
        return this;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public BoletimInformativo getBoletimInformativo() {
        return boletimInformativo;
    }

    public Anexo boletimInformativo(BoletimInformativo boletimInformativo) {
        this.boletimInformativo = boletimInformativo;
        return this;
    }

    public void setBoletimInformativo(BoletimInformativo boletimInformativo) {
        this.boletimInformativo = boletimInformativo;
    }

    public CampanhaRecall getCampanhaRecall() {
        return campanhaRecall;
    }

    public Anexo campanhaRecall(CampanhaRecall campanhaRecall) {
        this.campanhaRecall = campanhaRecall;
        return this;
    }

    public void setCampanhaRecall(CampanhaRecall campanhaRecall) {
        this.campanhaRecall = campanhaRecall;
    }

    public EventoOperacional getEventoOperacional() {
        return eventoOperacional;
    }

    public Anexo eventoOperacional(EventoOperacional eventoOperacional) {
        this.eventoOperacional = eventoOperacional;
        return this;
    }

    public void setEventoOperacional(EventoOperacional eventoOperacional) {
        this.eventoOperacional = eventoOperacional;
    }

    public ItemChecklist getItemChecklist() {
        return itemChecklist;
    }

    public Anexo itemChecklist(ItemChecklist itemChecklist) {
        this.itemChecklist = itemChecklist;
        return this;
    }

    public void setItemChecklist(ItemChecklist itemChecklist) {
        this.itemChecklist = itemChecklist;
    }

    public ItemPlanoAuditoria getItemPlanoAuditoria() {
        return itemPlanoAuditoria;
    }

    public Anexo itemPlanoAuditoria(ItemPlanoAuditoria itemPlanoAuditoria) {
        this.itemPlanoAuditoria = itemPlanoAuditoria;
        return this;
    }

    public void setItemPlanoAuditoria(ItemPlanoAuditoria itemPlanoAuditoria) {
        this.itemPlanoAuditoria = itemPlanoAuditoria;
    }

    public NaoConformidade getNaoConformidade() {
        return naoConformidade;
    }

    public Anexo naoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
        return this;
    }

    public void setNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Anexo processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Produto getProduto() {
        return produto;
    }

    public Anexo produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public PlanoAuditoria getPlanoAuditoria() {
        return planoAuditoria;
    }

    public Anexo planoAuditoria(PlanoAuditoria planoAuditoria) {
        this.planoAuditoria = planoAuditoria;
        return this;
    }

    public void setPlanoAuditoria(PlanoAuditoria planoAuditoria) {
        this.planoAuditoria = planoAuditoria;
    }

    public ProdutoNaoConforme getProdutoNaoConforme() {
        return produtoNaoConforme;
    }

    public Anexo produtoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConforme = produtoNaoConforme;
        return this;
    }

    public void setProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConforme = produtoNaoConforme;
    }

    public PublicacaoFeed getPublicacaoFeed() {
        return publicacaoFeed;
    }

    public Anexo publicacaoFeed(PublicacaoFeed publicacaoFeed) {
        this.publicacaoFeed = publicacaoFeed;
        return this;
    }

    public void setPublicacaoFeed(PublicacaoFeed publicacaoFeed) {
        this.publicacaoFeed = publicacaoFeed;
    }

    public ResultadoChecklist getResultadoChecklist() {
        return resultadoChecklist;
    }

    public Anexo resultadoChecklist(ResultadoChecklist resultadoChecklist) {
        this.resultadoChecklist = resultadoChecklist;
        return this;
    }

    public void setResultadoChecklist(ResultadoChecklist resultadoChecklist) {
        this.resultadoChecklist = resultadoChecklist;
    }

    public ResultadoItemChecklist getResultadoItemChecklist() {
        return resultadoItemChecklist;
    }

    public Anexo resultadoItemChecklist(ResultadoItemChecklist resultadoItemChecklist) {
        this.resultadoItemChecklist = resultadoItemChecklist;
        return this;
    }

    public void setResultadoItemChecklist(ResultadoItemChecklist resultadoItemChecklist) {
        this.resultadoItemChecklist = resultadoItemChecklist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anexo)) {
            return false;
        }
        return id != null && id.equals(((Anexo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Anexo{" +
            "id=" + getId() +
            ", nomeArquivo='" + getNomeArquivo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", conteudoContentType='" + getConteudoContentType() + "'" +
            "}";
    }
}
