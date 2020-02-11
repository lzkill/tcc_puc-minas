package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ResultadoAuditoria.
 */
@Entity
@Table(name = "resultado_auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadoAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_responsavel", nullable = false)
    private Integer idUsuarioResponsavel;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private Instant dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private Instant dataFim;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "resultadoAuditoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NaoConformidade> naoConformidades = new HashSet<>();

    @OneToMany(mappedBy = "resultadoAuditoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProdutoNaoConforme> produtoNaoConformes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("resultadoAuditorias")
    private Auditoria auditoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public ResultadoAuditoria idUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
        return this;
    }

    public void setIdUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public ResultadoAuditoria dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public ResultadoAuditoria dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public ResultadoAuditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<NaoConformidade> getNaoConformidades() {
        return naoConformidades;
    }

    public ResultadoAuditoria naoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
        return this;
    }

    public ResultadoAuditoria addNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.add(naoConformidade);
        naoConformidade.setResultadoAuditoria(this);
        return this;
    }

    public ResultadoAuditoria removeNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.remove(naoConformidade);
        naoConformidade.setResultadoAuditoria(null);
        return this;
    }

    public void setNaoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
    }

    public Set<ProdutoNaoConforme> getProdutoNaoConformes() {
        return produtoNaoConformes;
    }

    public ResultadoAuditoria produtoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
        this.produtoNaoConformes = produtoNaoConformes;
        return this;
    }

    public ResultadoAuditoria addProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConformes.add(produtoNaoConforme);
        produtoNaoConforme.setResultadoAuditoria(this);
        return this;
    }

    public ResultadoAuditoria removeProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConformes.remove(produtoNaoConforme);
        produtoNaoConforme.setResultadoAuditoria(null);
        return this;
    }

    public void setProdutoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
        this.produtoNaoConformes = produtoNaoConformes;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public ResultadoAuditoria auditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
        return this;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultadoAuditoria)) {
            return false;
        }
        return id != null && id.equals(((ResultadoAuditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultadoAuditoria{" +
            "id=" + getId() +
            ", idUsuarioResponsavel=" + getIdUsuarioResponsavel() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
