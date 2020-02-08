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
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "data_verificacao", nullable = false)
    private Instant dataVerificacao;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @OneToMany(mappedBy = "resultado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultadoItemChecklist> resultadoItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadoChecklists")
    private Checklist checklist;

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

    public String getTitulo() {
        return titulo;
    }

    public ResultadoChecklist titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Anexo getAnexo() {
        return anexo;
    }

    public ResultadoChecklist anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
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
            ", titulo='" + getTitulo() + "'" +
            ", dataVerificacao='" + getDataVerificacao() + "'" +
            "}";
    }
}
