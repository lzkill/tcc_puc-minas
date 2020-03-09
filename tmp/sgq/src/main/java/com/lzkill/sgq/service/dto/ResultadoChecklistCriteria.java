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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.ResultadoChecklist} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ResultadoChecklistResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultado-checklists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultadoChecklistCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private InstantFilter dataRegistro;

    private InstantFilter dataVerificacao;

    private LongFilter resultadoItemId;

    private LongFilter naoConformidadeId;

    private LongFilter produtoNaoConformeId;

    private LongFilter checklistId;

    private LongFilter anexoId;

    public ResultadoChecklistCriteria(){
    }

    public ResultadoChecklistCriteria(ResultadoChecklistCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataVerificacao = other.dataVerificacao == null ? null : other.dataVerificacao.copy();
        this.resultadoItemId = other.resultadoItemId == null ? null : other.resultadoItemId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.produtoNaoConformeId = other.produtoNaoConformeId == null ? null : other.produtoNaoConformeId.copy();
        this.checklistId = other.checklistId == null ? null : other.checklistId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
    }

    @Override
    public ResultadoChecklistCriteria copy() {
        return new ResultadoChecklistCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(IntegerFilter idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public InstantFilter getDataVerificacao() {
        return dataVerificacao;
    }

    public void setDataVerificacao(InstantFilter dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }

    public LongFilter getResultadoItemId() {
        return resultadoItemId;
    }

    public void setResultadoItemId(LongFilter resultadoItemId) {
        this.resultadoItemId = resultadoItemId;
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

    public LongFilter getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(LongFilter checklistId) {
        this.checklistId = checklistId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultadoChecklistCriteria that = (ResultadoChecklistCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataVerificacao, that.dataVerificacao) &&
            Objects.equals(resultadoItemId, that.resultadoItemId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(produtoNaoConformeId, that.produtoNaoConformeId) &&
            Objects.equals(checklistId, that.checklistId) &&
            Objects.equals(anexoId, that.anexoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        dataRegistro,
        dataVerificacao,
        resultadoItemId,
        naoConformidadeId,
        produtoNaoConformeId,
        checklistId,
        anexoId
        );
    }

    @Override
    public String toString() {
        return "ResultadoChecklistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataVerificacao != null ? "dataVerificacao=" + dataVerificacao + ", " : "") +
                (resultadoItemId != null ? "resultadoItemId=" + resultadoItemId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (produtoNaoConformeId != null ? "produtoNaoConformeId=" + produtoNaoConformeId + ", " : "") +
                (checklistId != null ? "checklistId=" + checklistId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
            "}";
    }

}
