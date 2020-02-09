package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ItemChecklist.
 */
@Entity
@Table(name = "item_checklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemChecklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("items")
    private Checklist checklist;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public ItemChecklist ordem(Integer ordem) {
        this.ordem = ordem;
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getTitulo() {
        return titulo;
    }

    public ItemChecklist titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ItemChecklist descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public ItemChecklist anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public ItemChecklist checklist(Checklist checklist) {
        this.checklist = checklist;
        return this;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemChecklist)) {
            return false;
        }
        return id != null && id.equals(((ItemChecklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemChecklist{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}