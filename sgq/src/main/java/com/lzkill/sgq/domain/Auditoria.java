package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.lzkill.sgq.domain.enumeration.TipoAuditoria;

/**
 * A Auditoria.
 */
@Entity
@Table(name = "auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAuditoria tipo;

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

    @ManyToOne
    @JsonIgnoreProperties("auditorias")
    private Processo processo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAuditoria getTipo() {
        return tipo;
    }

    public Auditoria tipo(TipoAuditoria tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoAuditoria tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Auditoria titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Auditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Auditoria processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auditoria)) {
            return false;
        }
        return id != null && id.equals(((Auditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
