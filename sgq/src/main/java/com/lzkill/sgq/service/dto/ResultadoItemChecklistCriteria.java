package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.ResultadoItemChecklist} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ResultadoItemChecklistResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultado-item-checklists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultadoItemChecklistCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter conforme;

    private LongFilter anexoId;

    private LongFilter naoConformidadeId;

    private LongFilter produtoNaoConformeId;

    private LongFilter itemId;

    private LongFilter resultadoId;

    public ResultadoItemChecklistCriteria(){
    }

    public ResultadoItemChecklistCriteria(ResultadoItemChecklistCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.conforme = other.conforme == null ? null : other.conforme.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.produtoNaoConformeId = other.produtoNaoConformeId == null ? null : other.produtoNaoConformeId.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
        this.resultadoId = other.resultadoId == null ? null : other.resultadoId.copy();
    }

    @Override
    public ResultadoItemChecklistCriteria copy() {
        return new ResultadoItemChecklistCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getConforme() {
        return conforme;
    }

    public void setConforme(BooleanFilter conforme) {
        this.conforme = conforme;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getNaoConformidadeId() {
        return naoConformidadeId;
    }

    public void setNaoConformidadeId(LongFilter naoConformidadeId) {
        this.naoConformidadeId = naoConformidadeId;
    }

    public LongFilter getProdutoNaoConformeId() {
        return produtoNaoConformeId;
    }

    public void setProdutoNaoConformeId(LongFilter produtoNaoConformeId) {
        this.produtoNaoConformeId = produtoNaoConformeId;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public LongFilter getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(LongFilter resultadoId) {
        this.resultadoId = resultadoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultadoItemChecklistCriteria that = (ResultadoItemChecklistCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(conforme, that.conforme) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(produtoNaoConformeId, that.produtoNaoConformeId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(resultadoId, that.resultadoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        conforme,
        anexoId,
        naoConformidadeId,
        produtoNaoConformeId,
        itemId,
        resultadoId
        );
    }

    @Override
    public String toString() {
        return "ResultadoItemChecklistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (conforme != null ? "conforme=" + conforme + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (produtoNaoConformeId != null ? "produtoNaoConformeId=" + produtoNaoConformeId + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
                (resultadoId != null ? "resultadoId=" + resultadoId + ", " : "") +
            "}";
    }

}
