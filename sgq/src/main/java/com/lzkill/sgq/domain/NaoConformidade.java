package com.lzkill.sgq.domain;
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

    @NotNull
    @Column(name = "id_usuario_responsavel", nullable = false)
    private Integer idUsuarioResponsavel;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "fato", nullable = false)
    private String fato;

    /**
     * O fechamento da NC só é possível se houver identificação de causa
     */
    @ApiModelProperty(value = "O fechamento da NC só é possível se houver identificação de causa")
    @Lob
    @Column(name = "causa")
    private String causa;

    @NotNull
    @Column(name = "prazo_conclusao", nullable = false)
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

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @OneToMany(mappedBy = "naoConformidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AcaoSGQ> acaos = new HashSet<>();

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

    public String getFato() {
        return fato;
    }

    public NaoConformidade fato(String fato) {
        this.fato = fato;
        return this;
    }

    public void setFato(String fato) {
        this.fato = fato;
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

    public Anexo getAnexo() {
        return anexo;
    }

    public NaoConformidade anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Set<AcaoSGQ> getAcaos() {
        return acaos;
    }

    public NaoConformidade acaos(Set<AcaoSGQ> acaoSGQS) {
        this.acaos = acaoSGQS;
        return this;
    }

    public NaoConformidade addAcao(AcaoSGQ acaoSGQ) {
        this.acaos.add(acaoSGQ);
        acaoSGQ.setNaoConformidade(this);
        return this;
    }

    public NaoConformidade removeAcao(AcaoSGQ acaoSGQ) {
        this.acaos.remove(acaoSGQ);
        acaoSGQ.setNaoConformidade(null);
        return this;
    }

    public void setAcaos(Set<AcaoSGQ> acaoSGQS) {
        this.acaos = acaoSGQS;
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
            ", fato='" + getFato() + "'" +
            ", causa='" + getCausa() + "'" +
            ", prazoConclusao='" + getPrazoConclusao() + "'" +
            ", novoPrazoConclusao='" + getNovoPrazoConclusao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataConclusao='" + getDataConclusao() + "'" +
            ", analiseFinal='" + getAnaliseFinal() + "'" +
            ", statusSGQ='" + getStatusSGQ() + "'" +
            "}";
    }
}