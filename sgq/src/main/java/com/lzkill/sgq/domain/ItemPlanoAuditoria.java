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

    @Column(name = "data_inicio_previsto")
    private Instant dataInicioPrevisto;

    @Column(name = "data_fim_previsto")
    private Instant dataFimPrevisto;

    @OneToMany(mappedBy = "itemPlanoAuditoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo> anexos = new HashSet<>();

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

    public Instant getDataInicioPrevisto() {
        return dataInicioPrevisto;
    }

    public ItemPlanoAuditoria dataInicioPrevisto(Instant dataInicioPrevisto) {
        this.dataInicioPrevisto = dataInicioPrevisto;
        return this;
    }

    public void setDataInicioPrevisto(Instant dataInicioPrevisto) {
        this.dataInicioPrevisto = dataInicioPrevisto;
    }

    public Instant getDataFimPrevisto() {
        return dataFimPrevisto;
    }

    public ItemPlanoAuditoria dataFimPrevisto(Instant dataFimPrevisto) {
        this.dataFimPrevisto = dataFimPrevisto;
        return this;
    }

    public void setDataFimPrevisto(Instant dataFimPrevisto) {
        this.dataFimPrevisto = dataFimPrevisto;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public ItemPlanoAuditoria anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public ItemPlanoAuditoria addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setItemPlanoAuditoria(this);
        return this;
    }

    public ItemPlanoAuditoria removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setItemPlanoAuditoria(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
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
            ", dataInicioPrevisto='" + getDataInicioPrevisto() + "'" +
            ", dataFimPrevisto='" + getDataFimPrevisto() + "'" +
            "}";
    }
}
