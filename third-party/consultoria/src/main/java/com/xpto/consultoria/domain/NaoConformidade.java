package com.xpto.consultoria.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "naoConformidade")
    private Set<AcaoSGQ> acaoSGQS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "NaoConformidade{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", procedente='" + isProcedente() + "'" +
            ", causa='" + getCausa() + "'" +
            ", prazoConclusao='" + getPrazoConclusao() + "'" +
            ", novoPrazoConclusao='" + getNovoPrazoConclusao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            "}";
    }
}
