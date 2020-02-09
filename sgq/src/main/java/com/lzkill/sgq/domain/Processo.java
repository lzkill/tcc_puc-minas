package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Processo.
 */
@Entity
@Table(name = "processo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("processos")
    private Setor setor;

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

    public Processo titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Processo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Setor getSetor() {
        return setor;
    }

    public Processo setor(Setor setor) {
        this.setor = setor;
        return this;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processo)) {
            return false;
        }
        return id != null && id.equals(((Processo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}