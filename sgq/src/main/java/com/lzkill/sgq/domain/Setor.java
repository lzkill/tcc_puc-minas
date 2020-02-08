package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Setor.
 */
@Entity
@Table(name = "setor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Setor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "setor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Checklist> checklists = new HashSet<>();

    @OneToMany(mappedBy = "setor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("setors")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Setor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public Setor checklists(Set<Checklist> checklists) {
        this.checklists = checklists;
        return this;
    }

    public Setor addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
        checklist.setSetor(this);
        return this;
    }

    public Setor removeChecklist(Checklist checklist) {
        this.checklists.remove(checklist);
        checklist.setSetor(null);
        return this;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Setor processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Setor addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.setSetor(this);
        return this;
    }

    public Setor removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.setSetor(null);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        this.processos = processos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Setor empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setor)) {
            return false;
        }
        return id != null && id.equals(((Setor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Setor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
