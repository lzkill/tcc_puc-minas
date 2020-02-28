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

import com.lzkill.sgq.domain.enumeration.ModalidadeAuditoria;

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
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private ModalidadeAuditoria modalidade;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @ManyToOne
    @JsonIgnoreProperties("itemPlanoAuditorias")
    private ItemAuditoria itemAuditoria;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "item_plano_auditoria_anexo",
               joinColumns = @JoinColumn(name = "item_plano_auditoria_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

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

    public String getTitulo() {
        return titulo;
    }

    public ItemPlanoAuditoria titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ItemPlanoAuditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ModalidadeAuditoria getModalidade() {
        return modalidade;
    }

    public ItemPlanoAuditoria modalidade(ModalidadeAuditoria modalidade) {
        this.modalidade = modalidade;
        return this;
    }

    public void setModalidade(ModalidadeAuditoria modalidade) {
        this.modalidade = modalidade;
    }

    public Instant getdataInicio() {
        return dataInicio;
    }

    public ItemPlanoAuditoria dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setdataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getdataFim() {
        return dataFim;
    }

    public ItemPlanoAuditoria dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setdataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public ItemAuditoria getItemAuditoria() {
        return itemAuditoria;
    }

    public ItemPlanoAuditoria itemAuditoria(ItemAuditoria itemAuditoria) {
        this.itemAuditoria = itemAuditoria;
        return this;
    }

    public void setItemAuditoria(ItemAuditoria itemAuditoria) {
        this.itemAuditoria = itemAuditoria;
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
        anexo.getItemPlanoAuditorias().add(this);
        return this;
    }

    public ItemPlanoAuditoria removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getItemPlanoAuditorias().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
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
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", modalidade='" + getModalidade() + "'" +
            ", dataInicio='" + getdataInicio() + "'" +
            ", dataFim='" + getdataFim() + "'" +
            "}";
    }
}
