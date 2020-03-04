package com.xpto.consultoria.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xpto.consultoria.domain.enumeration.StatusSGQ;
import com.xpto.consultoria.domain.enumeration.TipoAcaoSGQ;

/**
 * A AcaoSGQ.
 */
@Entity
@Table(name = "acao_sgq")
public class AcaoSGQ implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private TipoAcaoSGQ tipo;

	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;

	@Lob
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "prazo_conclusao")
	private Instant prazoConclusao;

	@Column(name = "novo_prazo_conclusao")
	private Instant novoPrazoConclusao;

	@NotNull
	@Column(name = "data_registro", nullable = false)
	private Instant dataRegistro;

	@Column(name = "data_conclusao")
	private Instant dataConclusao;

	@Lob
	@Column(name = "resultado")
	private String resultado;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_sgq", nullable = false)
	private StatusSGQ statusSGQ;

	@ManyToMany
	@JoinTable(name = "acao_sgq_anexo", joinColumns = @JoinColumn(name = "acaosgq_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
	private Set<Anexo> anexos = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties("acaoSGQS")
	private NaoConformidade naoConformidade;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoAcaoSGQ getTipo() {
		return tipo;
	}

	public AcaoSGQ tipo(TipoAcaoSGQ tipo) {
		this.tipo = tipo;
		return this;
	}

	public void setTipo(TipoAcaoSGQ tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public AcaoSGQ titulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public AcaoSGQ descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getPrazoConclusao() {
		return prazoConclusao;
	}

	public AcaoSGQ prazoConclusao(Instant prazoConclusao) {
		this.prazoConclusao = prazoConclusao;
		return this;
	}

	public void setPrazoConclusao(Instant prazoConclusao) {
		this.prazoConclusao = prazoConclusao;
	}

	public Instant getNovoPrazoConclusao() {
		return novoPrazoConclusao;
	}

	public AcaoSGQ novoPrazoConclusao(Instant novoPrazoConclusao) {
		this.novoPrazoConclusao = novoPrazoConclusao;
		return this;
	}

	public void setNovoPrazoConclusao(Instant novoPrazoConclusao) {
		this.novoPrazoConclusao = novoPrazoConclusao;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public AcaoSGQ dataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
		return this;
	}

	public void setDataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Instant getDataConclusao() {
		return dataConclusao;
	}

	public AcaoSGQ dataConclusao(Instant dataConclusao) {
		this.dataConclusao = dataConclusao;
		return this;
	}

	public void setDataConclusao(Instant dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public String getResultado() {
		return resultado;
	}

	public AcaoSGQ resultado(String resultado) {
		this.resultado = resultado;
		return this;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public StatusSGQ getStatusSGQ() {
		return statusSGQ;
	}

	public AcaoSGQ statusSGQ(StatusSGQ statusSGQ) {
		this.statusSGQ = statusSGQ;
		return this;
	}

	public void setStatusSGQ(StatusSGQ statusSGQ) {
		this.statusSGQ = statusSGQ;
	}

	public Set<Anexo> getAnexos() {
		return anexos;
	}

	public AcaoSGQ anexos(Set<Anexo> anexos) {
		this.anexos = anexos;
		return this;
	}

	public AcaoSGQ addAnexo(Anexo anexo) {
		this.anexos.add(anexo);
		anexo.getAcaoSGQS().add(this);
		return this;
	}

	public AcaoSGQ removeAnexo(Anexo anexo) {
		this.anexos.remove(anexo);
		anexo.getAcaoSGQS().remove(this);
		return this;
	}

	public void setAnexos(Set<Anexo> anexos) {
		this.anexos = anexos;
	}

	public NaoConformidade getNaoConformidade() {
		return naoConformidade;
	}

	public AcaoSGQ naoConformidade(NaoConformidade naoConformidade) {
		this.naoConformidade = naoConformidade;
		return this;
	}

	public void setNaoConformidade(NaoConformidade naoConformidade) {
		this.naoConformidade = naoConformidade;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AcaoSGQ)) {
			return false;
		}
		return id != null && id.equals(((AcaoSGQ) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "AcaoSGQ{" + "id=" + getId() + ", tipo='" + getTipo() + "'" + ", titulo='" + getTitulo() + "'"
				+ ", descricao='" + getDescricao() + "'" + ", prazoConclusao='" + getPrazoConclusao() + "'"
				+ ", novoPrazoConclusao='" + getNovoPrazoConclusao() + "'" + ", dataRegistro='" + getDataRegistro()
				+ "'" + ", dataConclusao='" + getDataConclusao() + "'" + ", resultado='" + getResultado() + "'"
				+ ", statusSGQ='" + getStatusSGQ() + "'" + "}";
	}
}
