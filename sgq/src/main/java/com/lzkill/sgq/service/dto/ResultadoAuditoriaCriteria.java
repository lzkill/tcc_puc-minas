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
 * Criteria class for the {@link com.lzkill.sgq.domain.ResultadoAuditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ResultadoAuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultado-auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultadoAuditoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioResponsavel;

    private InstantFilter dataInicio;

    private InstantFilter dataFim;

    private LongFilter naoConformidadeId;

    private LongFilter produtoNaoConformeId;

    private LongFilter auditoriaId;

    public ResultadoAuditoriaCriteria(){
    }

    public ResultadoAuditoriaCriteria(ResultadoAuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioResponsavel = other.idUsuarioResponsavel == null ? null : other.idUsuarioResponsavel.copy();
        this.dataInicio = other.dataInicio == null ? null : other.dataInicio.copy();
        this.dataFim = other.dataFim == null ? null : other.dataFim.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.produtoNaoConformeId = other.produtoNaoConformeId == null ? null : other.produtoNaoConformeId.copy();
        this.auditoriaId = other.auditoriaId == null ? null : other.auditoriaId.copy();
    }

    @Override
    public ResultadoAuditoriaCriteria copy() {
        return new ResultadoAuditoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public void setIdUsuarioResponsavel(IntegerFilter idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public InstantFilter getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(InstantFilter dataInicio) {
        this.dataInicio = dataInicio;
    }

    public InstantFilter getDataFim() {
        return dataFim;
    }

    public void setDataFim(InstantFilter dataFim) {
        this.dataFim = dataFim;
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

    public LongFilter getAuditoriaId() {
        return auditoriaId;
    }

    public void setAuditoriaId(LongFilter auditoriaId) {
        this.auditoriaId = auditoriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultadoAuditoriaCriteria that = (ResultadoAuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioResponsavel, that.idUsuarioResponsavel) &&
            Objects.equals(dataInicio, that.dataInicio) &&
            Objects.equals(dataFim, that.dataFim) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(produtoNaoConformeId, that.produtoNaoConformeId) &&
            Objects.equals(auditoriaId, that.auditoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioResponsavel,
        dataInicio,
        dataFim,
        naoConformidadeId,
        produtoNaoConformeId,
        auditoriaId
        );
    }

    @Override
    public String toString() {
        return "ResultadoAuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioResponsavel != null ? "idUsuarioResponsavel=" + idUsuarioResponsavel + ", " : "") +
                (dataInicio != null ? "dataInicio=" + dataInicio + ", " : "") +
                (dataFim != null ? "dataFim=" + dataFim + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (produtoNaoConformeId != null ? "produtoNaoConformeId=" + produtoNaoConformeId + ", " : "") +
                (auditoriaId != null ? "auditoriaId=" + auditoriaId + ", " : "") +
            "}";
    }

}
