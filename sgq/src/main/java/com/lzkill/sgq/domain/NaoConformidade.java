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
import java.util.HashSet;
import java.util.Set;

import com.lzkill.sgq.domain.enumeration.StatusSGQ;

import com.lzkill.sgq.domain.enumeration.OrigemNaoConformidade;

/**
 * Representa a violação de algum requisito regulamentar (normas,\nprocedimentos internos, definições de processos, etc)
 */
@ApiModel(description = "Representa a violação de algum requisito regulamentar (normas,\nprocedimentos internos, definições de processos, etc)")
@Entity
@Table(name = "nao_conformidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NaoConformidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @Column(name = "id_usuario_responsavel")
    private Integer idUsuarioResponsavel;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    /**
     * O fato descrito implica de fato uma NC?
     */
    @ApiModelProperty(value = "O fato descrito implica de fato uma NC?")
    @Column(name = "procedente")
    private Boolean procedente;

    /**
     * O fechamento da NC só é possível se houver identificação de causa
     */
    @ApiModelProperty(value = "O fechamento da NC só é possível se houver identificação de causa")
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = true)
    private OrigemNaoConformidade origem;

    @OneToMany(mappedBy = "naoConformidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AcaoSGQ> acaoSGQS = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "nao_conformidade_anexo",
               joinColumns = @JoinColumn(name = "nao_conformidade_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("naoConformidades")
    private Auditoria auditoria;

    @ManyToOne
    @JsonIgnoreProperties("naoConformidades")
    private ResultadoChecklist resultadoChecklist;

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

    public NaoConformidade idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public Integer getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public NaoConformidade idUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
        return this;
    }

    public void setIdUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
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

    public OrigemNaoConformidade getOrigem() {
        return origem;
    }

    public NaoConformidade origem(OrigemNaoConformidade origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(OrigemNaoConformidade origem) {
        this.origem = origem;
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

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public NaoConformidade auditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
        return this;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public ResultadoChecklist getResultadoChecklist() {
        return resultadoChecklist;
    }

    public NaoConformidade resultadoChecklist(ResultadoChecklist resultadoChecklist) {
        this.resultadoChecklist = resultadoChecklist;
        return this;
    }

    public void setResultadoChecklist(ResultadoChecklist resultadoChecklist) {
        this.resultadoChecklist = resultadoChecklist;
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
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", idUsuarioResponsavel=" + getIdUsuarioResponsavel() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", procedente='" + isProcedente() + "'" +
            ", causa='" + getCausa() + "'" +
            ", prazoConclusao='" + getPrazoConclusao() + "'" +
            ", novoPrazoConclusao='" + getNovoPrazoConclusao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataConclusao='" + getDataConclusao() + "'" +
            ", analiseFinal='" + getAnaliseFinal() + "'" +
            ", statusSGQ='" + getStatusSGQ() + "'" +
            ", origem='" + getOrigem() + "'" +
            "}";
    }
}
