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
 * A ResultadoChecklist.
 */
@Entity
@Table(name = "resultado_checklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadoChecklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @NotNull
    @Column(name = "data_verificacao", nullable = false)
    private Instant dataVerificacao;

    @OneToMany(mappedBy = "resultado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultadoItemChecklist> resultadoItems = new HashSet<>();

    @OneToMany(mappedBy = "resultadoChecklist")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NaoConformidade> naoConformidades = new HashSet<>();

    @OneToMany(mappedBy = "resultadoChecklist")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProdutoNaoConforme> produtoNaoConformes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadoChecklists")
    private Checklist checklist;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "resultado_checklist_anexo",
               joinColumns = @JoinColumn(name = "resultado_checklist_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public ResultadoChecklist idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public ResultadoChecklist dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataVerificacao() {
        return dataVerificacao;
    }

    public ResultadoChecklist dataVerificacao(Instant dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
        return this;
    }

    public void setDataVerificacao(Instant dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }

    public Set<ResultadoItemChecklist> getResultadoItems() {
        return resultadoItems;
    }

    public ResultadoChecklist resultadoItems(Set<ResultadoItemChecklist> resultadoItemChecklists) {
        this.resultadoItems = resultadoItemChecklists;
        return this;
    }

    public ResultadoChecklist addResultadoItem(ResultadoItemChecklist resultadoItemChecklist) {
        this.resultadoItems.add(resultadoItemChecklist);
        resultadoItemChecklist.setResultado(this);
        return this;
    }

    public ResultadoChecklist removeResultadoItem(ResultadoItemChecklist resultadoItemChecklist) {
        this.resultadoItems.remove(resultadoItemChecklist);
        resultadoItemChecklist.setResultado(null);
        return this;
    }

    public void setResultadoItems(Set<ResultadoItemChecklist> resultadoItemChecklists) {
        this.resultadoItems = resultadoItemChecklists;
    }

    public Set<NaoConformidade> getNaoConformidades() {
        return naoConformidades;
    }

    public ResultadoChecklist naoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
        return this;
    }

    public ResultadoChecklist addNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.add(naoConformidade);
        naoConformidade.setResultadoChecklist(this);
        return this;
    }

    public ResultadoChecklist removeNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.remove(naoConformidade);
        naoConformidade.setResultadoChecklist(null);
        return this;
    }

    public void setNaoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
    }

    public Set<ProdutoNaoConforme> getProdutoNaoConformes() {
        return produtoNaoConformes;
    }

    public ResultadoChecklist produtoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
        this.produtoNaoConformes = produtoNaoConformes;
        return this;
    }

    public ResultadoChecklist addProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConformes.add(produtoNaoConforme);
        produtoNaoConforme.setResultadoChecklist(this);
        return this;
    }

    public ResultadoChecklist removeProdutoNaoConforme(ProdutoNaoConforme produtoNaoConforme) {
        this.produtoNaoConformes.remove(produtoNaoConforme);
        produtoNaoConforme.setResultadoChecklist(null);
        return this;
    }

    public void setProdutoNaoConformes(Set<ProdutoNaoConforme> produtoNaoConformes) {
        this.produtoNaoConformes = produtoNaoConformes;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public ResultadoChecklist checklist(Checklist checklist) {
        this.checklist = checklist;
        return this;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public ResultadoChecklist anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public ResultadoChecklist addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getResultadoChecklists().add(this);
        return this;
    }

    public ResultadoChecklist removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getResultadoChecklists().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultadoChecklist)) {
            return false;
        }
        return id != null && id.equals(((ResultadoChecklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultadoChecklist{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataVerificacao='" + getDataVerificacao() + "'" +
            "}";
    }
}
