package com.lzkill.sgq.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Column(name = "id_usuario_registro", nullable = false)
	private Integer idUsuarioRegistro;

	@NotNull
	@Column(name = "data_registro", nullable = false)
	private Instant dataRegistro;

	@NotNull
	@Column(name = "nome_arquivo", nullable = false)
	private String nomeArquivo;

	@Lob
	@Column(name = "conteudo", nullable = false)
	private byte[] conteudo;

	@Column(name = "conteudo_content_type", nullable = false)
	private String conteudoContentType;

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<AcaoSGQ> acaoSGQS = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<Auditoria> auditorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<AnaliseConsultoria> analiseConsultorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<BoletimInformativo> boletimInformativos = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<CampanhaRecall> campanhaRecalls = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<Checklist> checklists = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<EventoOperacional> eventoOperacionals = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ItemAuditoria> itemAuditorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ItemChecklist> itemChecklists = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ItemPlanoAuditoria> itemPlanoAuditorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<NaoConformidade> naoConformidades = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<Processo> processos = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<Produto> produtos = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<PlanoAuditoria> planoAuditorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ProdutoNaoConforme> produtoNaoConformes = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<PublicacaoFeed> publicacaoFeeds = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ResultadoChecklist> resultadoChecklists = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
	private Set<ResultadoItemChecklist> resultadoItemChecklists = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Anexo id(Long id) {
		this.id = id;
		return this;
	}

	public Integer getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	public Anexo idUsuarioRegistro(Integer idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
		return this;
	}

	public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public Anexo dataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
		return this;
	}

	public void setDataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
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

	public Set<AcaoSGQ> getAcaoSGQS() {
		return acaoSGQS;
	}

	public Anexo acaoSGQS(Set<AcaoSGQ> acaoSGQS) {
		this.acaoSGQS = acaoSGQS;
		return this;
	}

	public Anexo addAcaoSGQ(AcaoSGQ acaoSGQ) {
		this.acaoSGQS.add(acaoSGQ);
		acaoSGQ.getAnexos().add(this);
		return this;
	}

	public Anexo removeAcaoSGQ(AcaoSGQ acaoSGQ) {
		this.acaoSGQS.remove(acaoSGQ);
		acaoSGQ.getAnexos().remove(this);
		return this;
	}

	public void setAcaoSGQS(Set<AcaoSGQ> acaoSGQS) {
		this.acaoSGQS = acaoSGQS;
	}

	public Set<Auditoria> getAuditorias() {
		return auditorias;
	}

	public Anexo auditorias(Set<Auditoria> auditorias) {
		this.auditorias = auditorias;
		return this;
	}

	public Anexo addAuditoria(Auditoria auditoria) {
		this.auditorias.add(auditoria);
		auditoria.getAnexos().add(this);
		return this;
	}

	public Anexo removeAuditoria(Auditoria auditoria) {
		this.auditorias.remove(auditoria);
		auditoria.getAnexos().remove(this);
		return this;
	}

	public void setAuditorias(Set<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	public Set<AnaliseConsultoria> getAnaliseConsultorias() {
		return analiseConsultorias;
	}

	public Anexo analiseConsultorias(Set<AnaliseConsultoria> analiseConsultorias) {
		this.analiseConsultorias = analiseConsultorias;
		return this;
	}

	public Anexo addAnaliseConsultoria(AnaliseConsultoria analiseConsultoria) {
		this.analiseConsultorias.add(analiseConsultoria);
		analiseConsultoria.getAnexos().add(this);
		return this;
	}

	public Anexo removeAnaliseConsultoria(AnaliseConsultoria analiseConsultoria) {
		this.analiseConsultorias.remove(analiseConsultoria);
		analiseConsultoria.getAnexos().remove(this);
		return this;
	}

	public void setAnaliseConsultorias(Set<AnaliseConsultoria> analiseConsultorias) {
		this.analiseConsultorias = analiseConsultorias;
	}

	public Set<BoletimInformativo> getBoletimInformativos() {
		return boletimInformativos;
	}

	public Anexo boletimInformativos(Set<BoletimInformativo> boletimInformativos) {
		this.boletimInformativos = boletimInformativos;
		return this;
	}

	public Anexo addBoletimInformativo(BoletimInformativo boletimInformativo) {
		this.boletimInformativos.add(boletimInformativo);
		boletimInformativo.getAnexos().add(this);
		return this;
	}

	public Anexo removeBoletimInformativo(BoletimInformativo boletimInformativo) {
		this.boletimInformativos.remove(boletimInformativo);
		boletimInformativo.getAnexos().remove(this);
		return this;
	}

	public void setBoletimInformativos(Set<BoletimInformativo> boletimInformativos) {
		this.boletimInformativos = boletimInformativos;
	}

	public Set<CampanhaRecall> getCampanhaRecalls() {
		return campanhaRecalls;
	}

	public Anexo campanhaRecalls(Set<CampanhaRecall> campanhaRecalls) {
		this.campanhaRecalls = campanhaRecalls;
		return this;
	}

	public Anexo addCampanhaRecall(CampanhaRecall campanhaRecall) {
		this.campanhaRecalls.add(campanhaRecall);
		campanhaRecall.getAnexos().add(this);
		return this;
	}

	public Anexo removeCampanhaRecall(CampanhaRecall campanhaRecall) {
		this.campanhaRecalls.remove(campanhaRecall);
		campanhaRecall.getAnexos().remove(this);
		return this;
	}

	public void setCampanhaRecalls(Set<CampanhaRecall> campanhaRecalls) {
		this.campanhaRecalls = campanhaRecalls;
	}

	public Set<Checklist> getChecklists() {
		return checklists;
	}

	public Anexo checklists(Set<Checklist> checklists) {
		this.checklists = checklists;
		return this;
	}

	public Anexo addChecklist(Checklist checklist) {
		this.checklists.add(checklist);
		checklist.getAnexos().add(this);
		return this;
	}

	public Anexo removeChecklist(Checklist checklist) {
		this.checklists.remove(checklist);
		checklist.getAnexos().remove(this);
		return this;
	}

	public void setChecklists(Set<Checklist> checklists) {
		this.checklists = checklists;
	}

	public Set<EventoOperacional> getEventoOperacionals() {
		return eventoOperacionals;
	}

	public Anexo eventoOperacionals(Set<EventoOperacional> eventoOperacionals) {
		this.eventoOperacionals = eventoOperacionals;
		return this;
	}

	public Anexo addEventoOperacional(EventoOperacional eventoOperacional) {
		this.eventoOperacionals.add(eventoOperacional);
		eventoOperacional.getAnexos().add(this);
		return this;
	}

	public Anexo removeEventoOperacional(EventoOperacional eventoOperacional) {
		this.eventoOperacionals.remove(eventoOperacional);
		eventoOperacional.getAnexos().remove(this);
		return this;
	}

	public void setEventoOperacionals(Set<EventoOperacional> eventoOperacionals) {
		this.eventoOperacionals = eventoOperacionals;
	}

	public Set<ItemAuditoria> getItemAuditorias() {
		return itemAuditorias;
	}

	public Anexo itemAuditorias(Set<ItemAuditoria> itemAuditorias) {
		this.itemAuditorias = itemAuditorias;
		return this;
	}

	public Anexo addItemAuditoria(ItemAuditoria itemAuditoria) {
		this.itemAuditorias.add(itemAuditoria);
		itemAuditoria.getAnexos().add(this);
		return this;
	}

	public Anexo removeItemAuditoria(ItemAuditoria itemAuditoria) {
		this.itemAuditorias.remove(itemAuditoria);
		itemAuditoria.getAnexos().remove(this);
		return this;
	}

	public void setItemAuditorias(Set<ItemAuditoria> itemAuditorias) {
		this.itemAuditorias = itemAuditorias;
	}

	public Set<ItemChecklist> getItemChecklists() {
		return itemChecklists;
	}

	public Anexo itemChecklists(Set<ItemChecklist> itemChecklists) {
		this.itemChecklists = itemChecklists;
		return this;
	}

	public Anexo addItemChecklist(ItemChecklist itemChecklist) {
		this.itemChecklists.add(itemChecklist);
		itemChecklist.getAnexos().add(this);
		return this;
	}

	public Anexo removeItemChecklist(ItemChecklist itemChecklist) {
		this.itemChecklists.remove(itemChecklist);
		itemChecklist.getAnexos().remove(this);
		return this;
	}

	public void setItemChecklists(Set<ItemChecklist> itemChecklists) {
		this.itemChecklists = itemChecklists;
	}

	public Set<ItemPlanoAuditoria> getItemPlanoAuditorias() {
		return itemPlanoAuditorias;
	}

	public Anexo itemPlanoAuditorias(Set<ItemPlanoAuditoria> itemPlanoAuditorias) {
		this.itemPlanoAuditorias = itemPlanoAuditorias;
		return this;
	}

	public Anexo addItemPlanoAuditoria(ItemPlanoAuditoria itemPlanoAuditoria) {
		this.itemPlanoAuditorias.add(itemPlanoAuditoria);
		itemPlanoAuditoria.getAnexos().add(this);
		return this;
	}

	public Anexo removeItemPlanoAuditoria(ItemPlanoAuditoria itemPlanoAuditoria) {
		this.itemPlanoAuditorias.remove(itemPlanoAuditoria);
		itemPlanoAuditoria.getAnexos().remove(this);
		return this;
	}

	public void setItemPlanoAuditorias(Set<ItemPlanoAuditoria> itemPlanoAuditorias) {
		this.itemPlanoAuditorias = itemPlanoAuditorias;
	}

	public Set<NaoConformidade> getNaoConformidades() {
		return naoConformidades;
	}

	public Anexo naoConformidades(Set<NaoConformidade> naoConformidades) {
		this.naoConformidades = naoConformidades;
		return this;
	}

	public Anexo addNaoConformidade(NaoConformidade naoConformidade) {
		this.naoConformidades.add(naoConformidade);
		naoConformidade.getAnexos().add(this);
		return this;
	}

	public Anexo removeNaoConformidade(NaoConformidade naoConformidade) {
		this.naoConformidades.remove(naoConformidade);
		naoConformidade.getAnexos().remove(this);
		return this;
	}

	public void setNaoConformidades(Set<NaoConformidade> naoConformidades) {
		this.naoConformidades = naoConformidades;
	}

	public Set<Processo> getProcessos() {
		return processos;
	}

	public Anexo processos(Set<Processo> processos) {
		this.processos = processos;
		return this;
	}

	public Anexo addProcesso(Processo processo) {
		this.processos.add(processo);
		processo.getAnexos().add(this);
		return this;
	}

	public Anexo removeProcesso(Processo processo) {
		this.processos.remove(processo);
		processo.getAnexos().remove(this);
		return this;
	}

	public void setProcessos(Set<Processo> processos) {
		this.processos = processos;
	}

	public Set<Produto> getProdutos() {
		return produtos;
	}

	public Anexo produtos(Set<Produto> produtos) {
		this.produtos = produtos;
		return this;
	}

	public Anexo addProduto(Produto produto) {
		this.produtos.add(produto);
		produto.getAnexos().add(this);
		return this;
	}

	public Anexo removeProduto(Produto produto) {
		this.produtos.remove(produto);
		produto.getAnexos().remove(this);
		return this;
	}

	public void setProdutos(Set<Produto> produtos) {
		this.produtos = produtos;
	}

	public Set<PlanoAuditoria> getPlanoAuditorias() {
		return planoAuditorias;
	}

	public Anexo planoAuditorias(Set<PlanoAuditoria> planoAuditorias) {
		this.planoAuditorias = planoAuditorias;
		return this;
	}

	public Anexo addPlanoAuditoria(PlanoAuditoria planoAuditoria) {
		this.planoAuditorias.add(planoAuditoria);
		planoAuditoria.getAnexos().add(this);
		return this;
	}

	public Anexo removePlanoAuditoria(PlanoAuditoria planoAuditoria) {
		this.planoAuditorias.remove(planoAuditoria);
		planoAuditoria.getAnexos().remove(this);
		return this;
	}

	public void setPlanoAuditorias(Set<PlanoAuditoria> planoAuditorias) {
		this.planoAuditorias = planoAuditorias;
	}

	public Set<ProdutoNaoConforme> getProdutoNaoConformes() {
		return produtoNaoConformes;
	}

	public Anexo produtoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
		this.produtoNaoConformes = produtoNaoConformes;
		return this;
	}

	public Anexo addProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
		this.produtoNaoConformes.add(produtoNaoConforme);
		produtoNaoConforme.getAnexos().add(this);
		return this;
	}

	public Anexo removeProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
		this.produtoNaoConformes.remove(produtoNaoConforme);
		produtoNaoConforme.getAnexos().remove(this);
		return this;
	}

	public void setProdutoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
		this.produtoNaoConformes = produtoNaoConformes;
	}

	public Set<PublicacaoFeed> getPublicacaoFeeds() {
		return publicacaoFeeds;
	}

	public Anexo publicacaoFeeds(Set<PublicacaoFeed> publicacaoFeeds) {
		this.publicacaoFeeds = publicacaoFeeds;
		return this;
	}

	public Anexo addPublicacaoFeed(PublicacaoFeed publicacaoFeed) {
		this.publicacaoFeeds.add(publicacaoFeed);
		publicacaoFeed.getAnexos().add(this);
		return this;
	}

	public Anexo removePublicacaoFeed(PublicacaoFeed publicacaoFeed) {
		this.publicacaoFeeds.remove(publicacaoFeed);
		publicacaoFeed.getAnexos().remove(this);
		return this;
	}

	public void setPublicacaoFeeds(Set<PublicacaoFeed> publicacaoFeeds) {
		this.publicacaoFeeds = publicacaoFeeds;
	}

	public Set<ResultadoChecklist> getResultadoChecklists() {
		return resultadoChecklists;
	}

	public Anexo resultadoChecklists(Set<ResultadoChecklist> resultadoChecklists) {
		this.resultadoChecklists = resultadoChecklists;
		return this;
	}

	public Anexo addResultadoChecklist(ResultadoChecklist resultadoChecklist) {
		this.resultadoChecklists.add(resultadoChecklist);
		resultadoChecklist.getAnexos().add(this);
		return this;
	}

	public Anexo removeResultadoChecklist(ResultadoChecklist resultadoChecklist) {
		this.resultadoChecklists.remove(resultadoChecklist);
		resultadoChecklist.getAnexos().remove(this);
		return this;
	}

	public void setResultadoChecklists(Set<ResultadoChecklist> resultadoChecklists) {
		this.resultadoChecklists = resultadoChecklists;
	}

	public Set<ResultadoItemChecklist> getResultadoItemChecklists() {
		return resultadoItemChecklists;
	}

	public Anexo resultadoItemChecklists(Set<ResultadoItemChecklist> resultadoItemChecklists) {
		this.resultadoItemChecklists = resultadoItemChecklists;
		return this;
	}

	public Anexo addResultadoItemChecklist(ResultadoItemChecklist resultadoItemChecklist) {
		this.resultadoItemChecklists.add(resultadoItemChecklist);
		resultadoItemChecklist.getAnexos().add(this);
		return this;
	}

	public Anexo removeResultadoItemChecklist(ResultadoItemChecklist resultadoItemChecklist) {
		this.resultadoItemChecklists.remove(resultadoItemChecklist);
		resultadoItemChecklist.getAnexos().remove(this);
		return this;
	}

	public void setResultadoItemChecklists(Set<ResultadoItemChecklist> resultadoItemChecklists) {
		this.resultadoItemChecklists = resultadoItemChecklists;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

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
		return "Anexo{" + "id=" + getId() + ", idUsuarioRegistro=" + getIdUsuarioRegistro() + ", dataRegistro='"
				+ getDataRegistro() + "'" + ", nomeArquivo='" + getNomeArquivo() + "'" + ", conteudo='" + getConteudo()
				+ "'" + ", conteudoContentType='" + getConteudoContentType() + "'" + "}";
	}
}
