package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ItemAuditoria.
 */
@Entity
@Table(name = "item_auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    /**
     * Requisitos abordados, objetivos, etc
     */
    @ApiModelProperty(value = "Requisitos abordados, objetivos, etc")
    @Lob
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    @ManyToOne
    @JsonIgnoreProperties("itemAuditorias")
    private Processo processo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "item_auditoria_anexo",
               joinColumns = @JoinColumn(name = "item_auditoria_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

    @ManyToMany(mappedBy = "itemAuditorias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Auditoria> auditorias = new HashSet<>();

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

    public ItemAuditoria titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ItemAuditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isHabilitado() {
        return habilitado;
    }

    public ItemAuditoria habilitado(Boolean habilitado) {
        this.habilitado = habilitado;
        return this;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Processo getProcesso() {
        return processo;
    }

    public ItemAuditoria processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public ItemAuditoria anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public ItemAuditoria addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getItemAuditorias().add(this);
        return this;
    }

    public ItemAuditoria removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getItemAuditorias().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Set<Auditoria> getAuditorias() {
        return auditorias;
    }

    public ItemAuditoria auditorias(Set<Auditoria> auditorias) {
        this.auditorias = auditorias;
        return this;
    }

    public ItemAuditoria addAuditoria(Auditoria auditoria) {
        this.auditorias.add(auditoria);
        auditoria.getItemAuditorias().add(this);
        return this;
    }

    public ItemAuditoria removeAuditoria(Auditoria auditoria) {
        this.auditorias.remove(auditoria);
        auditoria.getItemAuditorias().remove(this);
        return this;
    }

    public void setAuditorias(Set<Auditoria> auditorias) {
        this.auditorias = auditorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemAuditoria)) {
            return false;
        }
        return id != null && id.equals(((ItemAuditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemAuditoria{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", habilitado='" + isHabilitado() + "'" +
            "}";
    }
}
