package com.xpto.consultoria.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.xpto.consultoria.domain.enumeration.StatusSolicitacaoAnalise;

/**
 * A SolicitacaoAnalise.
 */
@Entity
@Table(name = "solicitacao_analise")
public class SolicitacaoAnalise implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "data_registro", nullable = false)
	private Instant dataRegistro;

	@Column(name = "data_solicitacao")
	private Instant dataSolicitacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusSolicitacaoAnalise status;

	@OneToOne
	@JoinColumn(unique = true)
	private AnaliseConsultoria analiseConsultoria;

	@OneToOne(optional = false)
	@NotNull
	@JoinColumn(unique = true)
	private NaoConformidade naoConformidade;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public SolicitacaoAnalise dataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
		return this;
	}

	public void setDataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Instant getDataSolicitacao() {
		return dataSolicitacao;
	}

	public SolicitacaoAnalise dataSolicitacao(Instant dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
		return this;
	}

	public void setDataSolicitacao(Instant dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public StatusSolicitacaoAnalise getStatus() {
		return status;
	}

	public SolicitacaoAnalise status(StatusSolicitacaoAnalise status) {
		this.status = status;
		return this;
	}

	public void setStatus(StatusSolicitacaoAnalise status) {
		this.status = status;
	}

	public AnaliseConsultoria getAnaliseConsultoria() {
		return analiseConsultoria;
	}

	public SolicitacaoAnalise analiseConsultoria(AnaliseConsultoria analiseConsultoria) {
		this.analiseConsultoria = analiseConsultoria;
		return this;
	}

	public void setAnaliseConsultoria(AnaliseConsultoria analiseConsultoria) {
		this.analiseConsultoria = analiseConsultoria;
	}

	public NaoConformidade getNaoConformidade() {
		return naoConformidade;
	}

	public SolicitacaoAnalise naoConformidade(NaoConformidade naoConformidade) {
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
		if (!(o instanceof SolicitacaoAnalise)) {
			return false;
		}
		return id != null && id.equals(((SolicitacaoAnalise) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "SolicitacaoAnalise{" + "id=" + getId() + ", dataRegistro='" + getDataRegistro() + "'"
				+ ", dataSolicitacao='" + getDataSolicitacao() + "'" + ", status='" + getStatus() + "'" + "}";
	}
}
