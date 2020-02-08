package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ResultadoItemChecklist.
 */
@Entity
@Table(name = "resultado_item_checklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadoItemChecklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "conforme", nullable = false)
    private Boolean conforme;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadoItemChecklists")
    private ItemChecklist item;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("resultadoItems")
    private ResultadoChecklist resultado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isConforme() {
        return conforme;
    }

    public ResultadoItemChecklist conforme(Boolean conforme) {
        this.conforme = conforme;
        return this;
    }

    public void setConforme(Boolean conforme) {
        this.conforme = conforme;
    }

    public String getDescricao() {
        return descricao;
    }

    public ResultadoItemChecklist descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public ResultadoItemChecklist anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public ItemChecklist getItem() {
        return item;
    }

    public ResultadoItemChecklist item(ItemChecklist itemChecklist) {
        this.item = itemChecklist;
        return this;
    }

    public void setItem(ItemChecklist itemChecklist) {
        this.item = itemChecklist;
    }

    public ResultadoChecklist getResultado() {
        return resultado;
    }

    public ResultadoItemChecklist resultado(ResultadoChecklist resultadoChecklist) {
        this.resultado = resultadoChecklist;
        return this;
    }

    public void setResultado(ResultadoChecklist resultadoChecklist) {
        this.resultado = resultadoChecklist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultadoItemChecklist)) {
            return false;
        }
        return id != null && id.equals(((ResultadoItemChecklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultadoItemChecklist{" +
            "id=" + getId() +
            ", conforme='" + isConforme() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
