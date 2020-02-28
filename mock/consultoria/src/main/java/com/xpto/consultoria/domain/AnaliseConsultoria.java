package com.xpto.consultoria.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.xpto.consultoria.domain.enumeration.StatusAprovacao;

/**
 * A AnaliseConsultoria.
 */
@Entity
@Table(name = "analise_consultoria")
public class AnaliseConsultoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_analise", nullable = false)
    private Instant dataAnalise;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "responsavel", length = 100, nullable = false)
    private String responsavel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAprovacao status;

    @OneToOne(mappedBy = "analiseConsultoria")
    @JsonIgnore
    private SolicitacaoAnalise solicitacaoAnalise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAnalise() {
        return dataAnalise;
    }

    public AnaliseConsultoria dataAnalise(Instant dataAnalise) {
        this.dataAnalise = dataAnalise;
        return this;
    }

    public void setDataAnalise(Instant dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public String getDescricao() {
        return descricao;
    }

    public AnaliseConsultoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public AnaliseConsultoria responsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public StatusAprovacao getStatus() {
        return status;
    }

    public AnaliseConsultoria status(StatusAprovacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAprovacao status) {
        this.status = status;
    }

    public SolicitacaoAnalise getSolicitacaoAnalise() {
        return solicitacaoAnalise;
    }

    public AnaliseConsultoria solicitacaoAnalise(SolicitacaoAnalise solicitacaoAnalise) {
        this.solicitacaoAnalise = solicitacaoAnalise;
        return this;
    }

    public void setSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnalise) {
        this.solicitacaoAnalise = solicitacaoAnalise;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnaliseConsultoria)) {
            return false;
        }
        return id != null && id.equals(((AnaliseConsultoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnaliseConsultoria{" +
            "id=" + getId() +
            ", dataAnalise='" + getDataAnalise() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
