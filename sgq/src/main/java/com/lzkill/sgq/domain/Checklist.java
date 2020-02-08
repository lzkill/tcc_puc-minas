package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.lzkill.sgq.domain.enumeration.Periodicidade;

/**
 * A Checklist.
 */
@Entity
@Table(name = "checklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Checklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicidade")
    private Periodicidade periodicidade;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @OneToMany(mappedBy = "checklist")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemChecklist> items = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("checklists")
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

    public Checklist titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public Checklist periodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
        return this;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public Checklist anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Set<ItemChecklist> getItems() {
        return items;
    }

    public Checklist items(Set<ItemChecklist> itemChecklists) {
        this.items = itemChecklists;
        return this;
    }

    public Checklist addItem(ItemChecklist itemChecklist) {
        this.items.add(itemChecklist);
        itemChecklist.setChecklist(this);
        return this;
    }

    public Checklist removeItem(ItemChecklist itemChecklist) {
        this.items.remove(itemChecklist);
        itemChecklist.setChecklist(null);
        return this;
    }

    public void setItems(Set<ItemChecklist> itemChecklists) {
        this.items = itemChecklists;
    }

    public Setor getSetor() {
        return setor;
    }

    public Checklist setor(Setor setor) {
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
        if (!(o instanceof Checklist)) {
            return false;
        }
        return id != null && id.equals(((Checklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Checklist{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", periodicidade='" + getPeriodicidade() + "'" +
            "}";
    }
}
