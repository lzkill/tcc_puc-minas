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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.xpto.consultoria.domain.enumeration.StatusSGQ;

import io.swagger.annotations.ApiModelProperty;

/**
 * A NaoConformidade.
 */
@Entity
@Table(name = "nao_conformidade")
public class NaoConformidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;

	@Lob
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "procedente")
	private Boolean procedente;

	@Lob
	@Column(name = "causa")
	private String causa;

	@Column(name = "prazo_conclusao")
	private Instant prazoConclusao;

	@Column(name = "novo_prazo_conclusao")
	private Instant novoPrazoConclusao;

	@NotNull
	@Column(name = "data_registro", nullable = false)
	private Instant dataRegistro;

	@Column(name = "data_conclusao")
	private Instant dataConclusao;

	/**
	 * Análise de eficácia
	 */
	@ApiModelProperty(value = "Análise de eficácia")
	@Lob
	@Column(name = "analise_final")
	private String analiseFinal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_sgq", nullable = false)
	private StatusSGQ statusSGQ;

	@OneToMany(mappedBy = "naoConformidade")
	private Set<AcaoSGQ> acaoSGQS = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "nao_conformidade_anexo", joinColumns = @JoinColumn(name = "nao_conformidade_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
	private Set<Anexo> anexos = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public NaoConformidade titulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public NaoConformidade descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean isProcedente() {
		return procedente;
	}

	public NaoConformidade procedente(Boolean procedente) {
		this.procedente = procedente;
		return this;
	}

	public void setProcedente(Boolean procedente) {
		this.procedente = procedente;
	}

	public String getCausa() {
		return causa;
	}

	public NaoConformidade causa(String causa) {
		this.causa = causa;
		return this;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public Instant getPrazoConclusao() {
		return prazoConclusao;
	}

	public NaoConformidade prazoConclusao(Instant prazoConclusao) {
		this.prazoConclusao = prazoConclusao;
		return this;
	}

	public void setPrazoConclusao(Instant prazoConclusao) {
		this.prazoConclusao = prazoConclusao;
	}

	public Instant getNovoPrazoConclusao() {
		return novoPrazoConclusao;
	}

	public NaoConformidade novoPrazoConclusao(Instant novoPrazoConclusao) {
		this.novoPrazoConclusao = novoPrazoConclusao;
		return this;
	}

	public void setNovoPrazoConclusao(Instant novoPrazoConclusao) {
		this.novoPrazoConclusao = novoPrazoConclusao;
	}

	public Instant getDataRegistro() {
		return dataRegistro;
	}

	public NaoConformidade dataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
		return this;
	}

	public void setDataRegistro(Instant dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Instant getDataConclusao() {
		return dataConclusao;
	}

	public NaoConformidade dataConclusao(Instant dataConclusao) {
		this.dataConclusao = dataConclusao;
		return this;
	}

	public void setDataConclusao(Instant dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public String getAnaliseFinal() {
		return analiseFinal;
	}

	public NaoConformidade analiseFinal(String analiseFinal) {
		this.analiseFinal = analiseFinal;
		return this;
	}

	public void setAnaliseFinal(String analiseFinal) {
		this.analiseFinal = analiseFinal;
	}

	public StatusSGQ getStatusSGQ() {
		return statusSGQ;
	}

	public NaoConformidade statusSGQ(StatusSGQ statusSGQ) {
		this.statusSGQ = statusSGQ;
		return this;
	}

	public void setStatusSGQ(StatusSGQ statusSGQ) {
		this.statusSGQ = statusSGQ;
	}

	public Set<AcaoSGQ> getAcaoSGQS() {
		return acaoSGQS;
	}

	public NaoConformidade acaoSGQS(Set<AcaoSGQ> acaoSGQS) {
		this.acaoSGQS = acaoSGQS;
		return this;
	}

	public NaoConformidade addAcaoSGQ(AcaoSGQ acaoSGQ) {
		this.acaoSGQS.add(acaoSGQ);
		acaoSGQ.setNaoConformidade(this);
		return this;
	}

	public NaoConformidade removeAcaoSGQ(AcaoSGQ acaoSGQ) {
		this.acaoSGQS.remove(acaoSGQ);
		acaoSGQ.setNaoConformidade(null);
		return this;
	}

	public void setAcaoSGQS(Set<AcaoSGQ> acaoSGQS) {
		this.acaoSGQS = acaoSGQS;
	}

	public Set<Anexo> getAnexos() {
		return anexos;
	}

	public NaoConformidade anexos(Set<Anexo> anexos) {
		this.anexos = anexos;
		return this;
	}

	public NaoConformidade addAnexo(Anexo anexo) {
		this.anexos.add(anexo);
		anexo.getNaoConformidades().add(this);
		return this;
	}

	public NaoConformidade removeAnexo(Anexo anexo) {
		this.anexos.remove(anexo);
		anexo.getNaoConformidades().remove(this);
		return this;
	}

	public void setAnexos(Set<Anexo> anexos) {
		this.anexos = anexos;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof NaoConformidade)) {
			return false;
		}
		return id != null && id.equals(((NaoConformidade) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "NaoConformidade{" + "id=" + getId() + ", titulo='" + getTitulo() + "'" + ", descricao='"
				+ getDescricao() + "'" + ", procedente='" + isProcedente() + "'" + ", causa='" + getCausa() + "'"
				+ ", prazoConclusao='" + getPrazoConclusao() + "'" + ", novoPrazoConclusao='" + getNovoPrazoConclusao()
				+ "'" + ", dataRegistro='" + getDataRegistro() + "'" + ", dataConclusao='" + getDataConclusao() + "'"
				+ ", analiseFinal='" + getAnaliseFinal() + "'" + ", statusSGQ='" + getStatusSGQ() + "'" + "}";
	}
}
