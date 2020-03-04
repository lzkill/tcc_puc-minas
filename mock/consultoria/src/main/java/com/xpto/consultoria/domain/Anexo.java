package com.xpto.consultoria.domain;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
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

	@ManyToMany(mappedBy = "anexos")
	@JsonIgnore
	private Set<AcaoSGQ> acaoSGQS = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@JsonIgnore
	private Set<AnaliseConsultoria> analiseConsultorias = new HashSet<>();

	@ManyToMany(mappedBy = "anexos")
	@JsonIgnore
	private Set<NaoConformidade> naoConformidades = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
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
		return "Anexo{" + "id=" + getId() + ", nomeArquivo='" + getNomeArquivo() + "'" + ", conteudo='" + getConteudo()
				+ "'" + ", conteudoContentType='" + getConteudoContentType() + "'" + "}";
	}
}
