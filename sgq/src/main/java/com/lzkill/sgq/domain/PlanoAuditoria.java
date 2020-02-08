package com.lzkill.sgq.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A PlanoAuditoria.
 */
@Entity
@Table(name = "plano_auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanoAuditoria implements Serializable {

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

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @OneToMany(mappedBy = "plano")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ItemPlanoAuditoria> items = new HashSet<>();

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

    public PlanoAuditoria titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public PlanoAuditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public PlanoAuditoria dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public PlanoAuditoria dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public PlanoAuditoria anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Set<ItemPlanoAuditoria> getItems() {
        return items;
    }

    public PlanoAuditoria items(Set<ItemPlanoAuditoria> itemPlanoAuditorias) {
        this.items = itemPlanoAuditorias;
        return this;
    }

    public PlanoAuditoria addItem(ItemPlanoAuditoria itemPlanoAuditoria) {
        this.items.add(itemPlanoAuditoria);
        itemPlanoAuditoria.setPlano(this);
        return this;
    }

    public PlanoAuditoria removeItem(ItemPlanoAuditoria itemPlanoAuditoria) {
        this.items.remove(itemPlanoAuditoria);
        itemPlanoAuditoria.setPlano(null);
        return this;
    }

    public void setItems(Set<ItemPlanoAuditoria> itemPlanoAuditorias) {
        this.items = itemPlanoAuditorias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAuditoria)) {
            return false;
        }
        return id != null && id.equals(((PlanoAuditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlanoAuditoria{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }
}
