package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ItemPlanoAuditoria.
 */
@Entity
@Table(name = "item_plano_auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemPlanoAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_responsavel", nullable = false)
    private Integer idUsuarioResponsavel;

    @NotNull
    @Column(name = "data_auditoria", nullable = false)
    private Instant dataAuditoria;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemPlanoAuditorias")
    private Auditoria auditoria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("items")
    private PlanoAuditoria plano;

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

    public ItemPlanoAuditoria idUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
        return this;
    }

    public void setIdUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public Instant getDataAuditoria() {
        return dataAuditoria;
    }

    public ItemPlanoAuditoria dataAuditoria(Instant dataAuditoria) {
        this.dataAuditoria = dataAuditoria;
        return this;
    }

    public void setDataAuditoria(Instant dataAuditoria) {
        this.dataAuditoria = dataAuditoria;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public ItemPlanoAuditoria anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public ItemPlanoAuditoria auditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
        return this;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public PlanoAuditoria getPlano() {
        return plano;
    }

    public ItemPlanoAuditoria plano(PlanoAuditoria planoAuditoria) {
        this.plano = planoAuditoria;
        return this;
    }

    public void setPlano(PlanoAuditoria planoAuditoria) {
        this.plano = planoAuditoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPlanoAuditoria)) {
            return false;
        }
        return id != null && id.equals(((ItemPlanoAuditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemPlanoAuditoria{" +
            "id=" + getId() +
            ", idUsuarioResponsavel=" + getIdUsuarioResponsavel() +
            ", dataAuditoria='" + getDataAuditoria() + "'" +
            "}";
    }
}
