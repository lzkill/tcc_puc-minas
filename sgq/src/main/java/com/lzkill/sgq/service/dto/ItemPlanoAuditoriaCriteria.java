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
 * Criteria class for the {@link com.lzkill.sgq.domain.ItemPlanoAuditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ItemPlanoAuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-plano-auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemPlanoAuditoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataInicioPrevisto;

    private InstantFilter dataFimPrevisto;

    private LongFilter anexoId;

    private LongFilter auditoriaId;

    private LongFilter planoId;

    public ItemPlanoAuditoriaCriteria(){
    }

    public ItemPlanoAuditoriaCriteria(ItemPlanoAuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataInicioPrevisto = other.dataInicioPrevisto == null ? null : other.dataInicioPrevisto.copy();
        this.dataFimPrevisto = other.dataFimPrevisto == null ? null : other.dataFimPrevisto.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.auditoriaId = other.auditoriaId == null ? null : other.auditoriaId.copy();
        this.planoId = other.planoId == null ? null : other.planoId.copy();
    }

    @Override
    public ItemPlanoAuditoriaCriteria copy() {
        return new ItemPlanoAuditoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDataInicioPrevisto() {
        return dataInicioPrevisto;
    }

    public void setDataInicioPrevisto(InstantFilter dataInicioPrevisto) {
        this.dataInicioPrevisto = dataInicioPrevisto;
    }

    public InstantFilter getDataFimPrevisto() {
        return dataFimPrevisto;
    }

    public void setDataFimPrevisto(InstantFilter dataFimPrevisto) {
        this.dataFimPrevisto = dataFimPrevisto;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getAuditoriaId() {
        return auditoriaId;
    }

    public void setAuditoriaId(LongFilter auditoriaId) {
        this.auditoriaId = auditoriaId;
    }

    public LongFilter getPlanoId() {
        return planoId;
    }

    public void setPlanoId(LongFilter planoId) {
        this.planoId = planoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemPlanoAuditoriaCriteria that = (ItemPlanoAuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataInicioPrevisto, that.dataInicioPrevisto) &&
            Objects.equals(dataFimPrevisto, that.dataFimPrevisto) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(auditoriaId, that.auditoriaId) &&
            Objects.equals(planoId, that.planoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataInicioPrevisto,
        dataFimPrevisto,
        anexoId,
        auditoriaId,
        planoId
        );
    }

    @Override
    public String toString() {
        return "ItemPlanoAuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataInicioPrevisto != null ? "dataInicioPrevisto=" + dataInicioPrevisto + ", " : "") +
                (dataFimPrevisto != null ? "dataFimPrevisto=" + dataFimPrevisto + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (auditoriaId != null ? "auditoriaId=" + auditoriaId + ", " : "") +
                (planoId != null ? "planoId=" + planoId + ", " : "") +
            "}";
    }

}
