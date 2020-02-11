package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.lzkill.sgq.domain.enumeration.StatusAnaliseExterna;

/**
 * Os itens do plano de ação podem ser analisados pela consultoria antes da\nimplementação na prática
 */
@ApiModel(description = "Os itens do plano de ação podem ser analisados pela consultoria antes da\nimplementação na prática")
@Entity
@Table(name = "analise_consultoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnaliseConsultoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_solicitacao_analise", nullable = false)
    private Instant dataSolicitacaoAnalise;

    @Column(name = "data_analise")
    private Instant dataAnalise;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "responsavel_analise", length = 100, nullable = false)
    private String responsavelAnalise;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAnaliseExterna status;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private AcaoSGQ acao;

    @OneToMany(mappedBy = "analiseConsultoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo> anexos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("analiseConsultorias")
    private EmpresaConsultoria empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataSolicitacaoAnalise() {
        return dataSolicitacaoAnalise;
    }

    public AnaliseConsultoria dataSolicitacaoAnalise(Instant dataSolicitacaoAnalise) {
        this.dataSolicitacaoAnalise = dataSolicitacaoAnalise;
        return this;
    }

    public void setDataSolicitacaoAnalise(Instant dataSolicitacaoAnalise) {
        this.dataSolicitacaoAnalise = dataSolicitacaoAnalise;
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

    public String getResponsavelAnalise() {
        return responsavelAnalise;
    }

    public AnaliseConsultoria responsavelAnalise(String responsavelAnalise) {
        this.responsavelAnalise = responsavelAnalise;
        return this;
    }

    public void setResponsavelAnalise(String responsavelAnalise) {
        this.responsavelAnalise = responsavelAnalise;
    }

    public StatusAnaliseExterna getStatus() {
        return status;
    }

    public AnaliseConsultoria status(StatusAnaliseExterna status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusAnaliseExterna status) {
        this.status = status;
    }

    public AcaoSGQ getAcao() {
        return acao;
    }

    public AnaliseConsultoria acao(AcaoSGQ acaoSGQ) {
        this.acao = acaoSGQ;
        return this;
    }

    public void setAcao(AcaoSGQ acaoSGQ) {
        this.acao = acaoSGQ;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public AnaliseConsultoria anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public AnaliseConsultoria addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setAnaliseConsultoria(this);
        return this;
    }

    public AnaliseConsultoria removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setAnaliseConsultoria(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public EmpresaConsultoria getEmpresa() {
        return empresa;
    }

    public AnaliseConsultoria empresa(EmpresaConsultoria empresaConsultoria) {
        this.empresa = empresaConsultoria;
        return this;
    }

    public void setEmpresa(EmpresaConsultoria empresaConsultoria) {
        this.empresa = empresaConsultoria;
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
            ", dataSolicitacaoAnalise='" + getDataSolicitacaoAnalise() + "'" +
            ", dataAnalise='" + getDataAnalise() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", responsavelAnalise='" + getResponsavelAnalise() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
