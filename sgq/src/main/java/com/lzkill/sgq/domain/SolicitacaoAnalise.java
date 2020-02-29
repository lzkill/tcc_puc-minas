package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;

/**
 * Os planos de ação das NCs podem ser analisados pela consultoria antes da\nimplementação na prática
 */
@ApiModel(description = "Os planos de ação das NCs podem ser analisados pela consultoria antes da\nimplementação na prática")
@Entity
@Table(name = "solicitacao_analise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SolicitacaoAnalise implements Serializable {

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

    /**
     * Data em que a solicitação foi recebida pela consultoria
     */
    @ApiModelProperty(value = "Data em que a solicitação foi recebida pela consultoria")
    @Column(name = "data_solicitacao")
    private Instant dataSolicitacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitacaoAnalise status;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private NaoConformidade naoConformidade;

    @OneToOne
    @JoinColumn(unique = true)
    private AnaliseConsultoria analiseConsultoria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("solicitacaoAnalises")
    private Consultoria consultoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public SolicitacaoAnalise idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
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

    public Consultoria getConsultoria() {
        return consultoria;
    }

    public SolicitacaoAnalise consultoria(Consultoria consultoria) {
        this.consultoria = consultoria;
        return this;
    }

    public void setConsultoria(Consultoria consultoria) {
        this.consultoria = consultoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "SolicitacaoAnalise{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
