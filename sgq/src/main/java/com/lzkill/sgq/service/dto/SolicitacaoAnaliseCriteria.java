package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.SolicitacaoAnalise} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.SolicitacaoAnaliseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /solicitacao-analises?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SolicitacaoAnaliseCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusSolicitacaoAnalise
     */
    public static class StatusSolicitacaoAnaliseFilter extends Filter<StatusSolicitacaoAnalise> {

        public StatusSolicitacaoAnaliseFilter() {
        }

        public StatusSolicitacaoAnaliseFilter(StatusSolicitacaoAnaliseFilter filter) {
            super(filter);
        }

        @Override
        public StatusSolicitacaoAnaliseFilter copy() {
            return new StatusSolicitacaoAnaliseFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private InstantFilter dataRegistro;

    private InstantFilter dataSolicitacao;

    private StatusSolicitacaoAnaliseFilter status;

    private LongFilter naoConformidadeId;

    private LongFilter analiseConsultoriaId;

    private LongFilter consultoriaId;

    public SolicitacaoAnaliseCriteria(){
    }

    public SolicitacaoAnaliseCriteria(SolicitacaoAnaliseCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataSolicitacao = other.dataSolicitacao == null ? null : other.dataSolicitacao.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.analiseConsultoriaId = other.analiseConsultoriaId == null ? null : other.analiseConsultoriaId.copy();
        this.consultoriaId = other.consultoriaId == null ? null : other.consultoriaId.copy();
    }

    @Override
    public SolicitacaoAnaliseCriteria copy() {
        return new SolicitacaoAnaliseCriteria(this);
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

    public InstantFilter getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(InstantFilter dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public StatusSolicitacaoAnaliseFilter getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacaoAnaliseFilter status) {
        this.status = status;
    }

    public LongFilter getNaoConformidadeId() {
        return naoConformidadeId;
    }

    public void setNaoConformidadeId(LongFilter naoConformidadeId) {
        this.naoConformidadeId = naoConformidadeId;
    }

    public LongFilter getAnaliseConsultoriaId() {
        return analiseConsultoriaId;
    }

    public void setAnaliseConsultoriaId(LongFilter analiseConsultoriaId) {
        this.analiseConsultoriaId = analiseConsultoriaId;
    }

    public LongFilter getConsultoriaId() {
        return consultoriaId;
    }

    public void setConsultoriaId(LongFilter consultoriaId) {
        this.consultoriaId = consultoriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SolicitacaoAnaliseCriteria that = (SolicitacaoAnaliseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataSolicitacao, that.dataSolicitacao) &&
            Objects.equals(status, that.status) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(analiseConsultoriaId, that.analiseConsultoriaId) &&
            Objects.equals(consultoriaId, that.consultoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        dataRegistro,
        dataSolicitacao,
        status,
        naoConformidadeId,
        analiseConsultoriaId,
        consultoriaId
        );
    }

    @Override
    public String toString() {
        return "SolicitacaoAnaliseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataSolicitacao != null ? "dataSolicitacao=" + dataSolicitacao + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (analiseConsultoriaId != null ? "analiseConsultoriaId=" + analiseConsultoriaId + ", " : "") +
                (consultoriaId != null ? "consultoriaId=" + consultoriaId + ", " : "") +
            "}";
    }

}
