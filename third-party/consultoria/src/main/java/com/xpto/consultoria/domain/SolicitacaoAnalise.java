package com.xpto.consultoria.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

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
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_solicitacao")
    private Instant dataSolicitacao;

    
    @Column(name = "uuid", unique = true)
    private String uuid;

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

    public String getUuid() {
        return uuid;
    }

    public SolicitacaoAnalise uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
            ", uuid='" + getUuid() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
