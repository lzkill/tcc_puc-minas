package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusAprovacao;
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
     * Class for filtering StatusAprovacao
     */
    public static class StatusAprovacaoFilter extends Filter<StatusAprovacao> {

        public StatusAprovacaoFilter() {
        }

        public StatusAprovacaoFilter(StatusAprovacaoFilter filter) {
            super(filter);
        }

        @Override
        public StatusAprovacaoFilter copy() {
            return new StatusAprovacaoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataAnalise;

    private StringFilter responsavel;

    private StatusAprovacaoFilter status;

    private LongFilter anexoId;

    private LongFilter solicitacaoAnaliseId;

    public AnaliseConsultoriaCriteria(){
    }

    public AnaliseConsultoriaCriteria(AnaliseConsultoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataAnalise = other.dataAnalise == null ? null : other.dataAnalise.copy();
        this.responsavel = other.responsavel == null ? null : other.responsavel.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.solicitacaoAnaliseId = other.solicitacaoAnaliseId == null ? null : other.solicitacaoAnaliseId.copy();
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

    public InstantFilter getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(InstantFilter dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public StringFilter getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(StringFilter responsavel) {
        this.responsavel = responsavel;
    }

    public StatusAprovacaoFilter getStatus() {
        return status;
    }

    public void setStatus(StatusAprovacaoFilter status) {
        this.status = status;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getSolicitacaoAnaliseId() {
        return solicitacaoAnaliseId;
    }

    public void setSolicitacaoAnaliseId(LongFilter solicitacaoAnaliseId) {
        this.solicitacaoAnaliseId = solicitacaoAnaliseId;
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
            Objects.equals(dataAnalise, that.dataAnalise) &&
            Objects.equals(responsavel, that.responsavel) &&
            Objects.equals(status, that.status) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(solicitacaoAnaliseId, that.solicitacaoAnaliseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataAnalise,
        responsavel,
        status,
        anexoId,
        solicitacaoAnaliseId
        );
    }

    @Override
    public String toString() {
        return "AnaliseConsultoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataAnalise != null ? "dataAnalise=" + dataAnalise + ", " : "") +
                (responsavel != null ? "responsavel=" + responsavel + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (solicitacaoAnaliseId != null ? "solicitacaoAnaliseId=" + solicitacaoAnaliseId + ", " : "") +
            "}";
    }

}
