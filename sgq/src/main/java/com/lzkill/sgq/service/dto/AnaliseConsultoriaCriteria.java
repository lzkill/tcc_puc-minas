package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusAnaliseExterna;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.AnaliseConsultoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.AnaliseConsultoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /analise-consultorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnaliseConsultoriaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusAnaliseExterna
     */
    public static class StatusAnaliseExternaFilter extends Filter<StatusAnaliseExterna> {

        public StatusAnaliseExternaFilter() {
        }

        public StatusAnaliseExternaFilter(StatusAnaliseExternaFilter filter) {
            super(filter);
        }

        @Override
        public StatusAnaliseExternaFilter copy() {
            return new StatusAnaliseExternaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataSolicitacaoAnalise;

    private InstantFilter dataAnalise;

    private StringFilter responsavelAnalise;

    private StatusAnaliseExternaFilter status;

    private LongFilter acaoId;

    private LongFilter anexoId;

    private LongFilter empresaId;

    public AnaliseConsultoriaCriteria(){
    }

    public AnaliseConsultoriaCriteria(AnaliseConsultoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataSolicitacaoAnalise = other.dataSolicitacaoAnalise == null ? null : other.dataSolicitacaoAnalise.copy();
        this.dataAnalise = other.dataAnalise == null ? null : other.dataAnalise.copy();
        this.responsavelAnalise = other.responsavelAnalise == null ? null : other.responsavelAnalise.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.acaoId = other.acaoId == null ? null : other.acaoId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public AnaliseConsultoriaCriteria copy() {
        return new AnaliseConsultoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDataSolicitacaoAnalise() {
        return dataSolicitacaoAnalise;
    }

    public void setDataSolicitacaoAnalise(InstantFilter dataSolicitacaoAnalise) {
        this.dataSolicitacaoAnalise = dataSolicitacaoAnalise;
    }

    public InstantFilter getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(InstantFilter dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public StringFilter getResponsavelAnalise() {
        return responsavelAnalise;
    }

    public void setResponsavelAnalise(StringFilter responsavelAnalise) {
        this.responsavelAnalise = responsavelAnalise;
    }

    public StatusAnaliseExternaFilter getStatus() {
        return status;
    }

    public void setStatus(StatusAnaliseExternaFilter status) {
        this.status = status;
    }

    public LongFilter getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(LongFilter acaoId) {
        this.acaoId = acaoId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnaliseConsultoriaCriteria that = (AnaliseConsultoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataSolicitacaoAnalise, that.dataSolicitacaoAnalise) &&
            Objects.equals(dataAnalise, that.dataAnalise) &&
            Objects.equals(responsavelAnalise, that.responsavelAnalise) &&
            Objects.equals(status, that.status) &&
            Objects.equals(acaoId, that.acaoId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataSolicitacaoAnalise,
        dataAnalise,
        responsavelAnalise,
        status,
        acaoId,
        anexoId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "AnaliseConsultoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataSolicitacaoAnalise != null ? "dataSolicitacaoAnalise=" + dataSolicitacaoAnalise + ", " : "") +
                (dataAnalise != null ? "dataAnalise=" + dataAnalise + ", " : "") +
                (responsavelAnalise != null ? "responsavelAnalise=" + responsavelAnalise + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (acaoId != null ? "acaoId=" + acaoId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
