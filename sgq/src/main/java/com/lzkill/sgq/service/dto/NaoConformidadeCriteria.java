package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusSGQ;
import com.lzkill.sgq.domain.enumeration.OrigemNaoConformidade;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.NaoConformidade} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.NaoConformidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nao-conformidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NaoConformidadeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusSGQ
     */
    public static class StatusSGQFilter extends Filter<StatusSGQ> {

        public StatusSGQFilter() {
        }

        public StatusSGQFilter(StatusSGQFilter filter) {
            super(filter);
        }

        @Override
        public StatusSGQFilter copy() {
            return new StatusSGQFilter(this);
        }

    }
    /**
     * Class for filtering OrigemNaoConformidade
     */
    public static class OrigemNaoConformidadeFilter extends Filter<OrigemNaoConformidade> {

        public OrigemNaoConformidadeFilter() {
        }

        public OrigemNaoConformidadeFilter(OrigemNaoConformidadeFilter filter) {
            super(filter);
        }

        @Override
        public OrigemNaoConformidadeFilter copy() {
            return new OrigemNaoConformidadeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private IntegerFilter idUsuarioResponsavel;

    private StringFilter titulo;

    private BooleanFilter procedente;

    private InstantFilter prazoConclusao;

    private InstantFilter novoPrazoConclusao;

    private InstantFilter dataRegistro;

    private InstantFilter dataConclusao;

    private StatusSGQFilter statusSGQ;

    private OrigemNaoConformidadeFilter origem;

    private LongFilter acaoSGQId;

    private LongFilter anexoId;

    private LongFilter auditoriaId;

    private LongFilter resultadoChecklistId;

    public NaoConformidadeCriteria(){
    }

    public NaoConformidadeCriteria(NaoConformidadeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.idUsuarioResponsavel = other.idUsuarioResponsavel == null ? null : other.idUsuarioResponsavel.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.procedente = other.procedente == null ? null : other.procedente.copy();
        this.prazoConclusao = other.prazoConclusao == null ? null : other.prazoConclusao.copy();
        this.novoPrazoConclusao = other.novoPrazoConclusao == null ? null : other.novoPrazoConclusao.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataConclusao = other.dataConclusao == null ? null : other.dataConclusao.copy();
        this.statusSGQ = other.statusSGQ == null ? null : other.statusSGQ.copy();
        this.origem = other.origem == null ? null : other.origem.copy();
        this.acaoSGQId = other.acaoSGQId == null ? null : other.acaoSGQId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.auditoriaId = other.auditoriaId == null ? null : other.auditoriaId.copy();
        this.resultadoChecklistId = other.resultadoChecklistId == null ? null : other.resultadoChecklistId.copy();
    }

    @Override
    public NaoConformidadeCriteria copy() {
        return new NaoConformidadeCriteria(this);
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

    public IntegerFilter getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public void setIdUsuarioResponsavel(IntegerFilter idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public BooleanFilter getProcedente() {
        return procedente;
    }

    public void setProcedente(BooleanFilter procedente) {
        this.procedente = procedente;
    }

    public InstantFilter getPrazoConclusao() {
        return prazoConclusao;
    }

    public void setPrazoConclusao(InstantFilter prazoConclusao) {
        this.prazoConclusao = prazoConclusao;
    }

    public InstantFilter getNovoPrazoConclusao() {
        return novoPrazoConclusao;
    }

    public void setNovoPrazoConclusao(InstantFilter novoPrazoConclusao) {
        this.novoPrazoConclusao = novoPrazoConclusao;
    }

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public InstantFilter getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(InstantFilter dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public StatusSGQFilter getStatusSGQ() {
        return statusSGQ;
    }

    public void setStatusSGQ(StatusSGQFilter statusSGQ) {
        this.statusSGQ = statusSGQ;
    }

    public OrigemNaoConformidadeFilter getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemNaoConformidadeFilter origem) {
        this.origem = origem;
    }

    public LongFilter getAcaoSGQId() {
        return acaoSGQId;
    }

    public void setAcaoSGQId(LongFilter acaoSGQId) {
        this.acaoSGQId = acaoSGQId;
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

    public LongFilter getResultadoChecklistId() {
        return resultadoChecklistId;
    }

    public void setResultadoChecklistId(LongFilter resultadoChecklistId) {
        this.resultadoChecklistId = resultadoChecklistId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NaoConformidadeCriteria that = (NaoConformidadeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(idUsuarioResponsavel, that.idUsuarioResponsavel) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(procedente, that.procedente) &&
            Objects.equals(prazoConclusao, that.prazoConclusao) &&
            Objects.equals(novoPrazoConclusao, that.novoPrazoConclusao) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataConclusao, that.dataConclusao) &&
            Objects.equals(statusSGQ, that.statusSGQ) &&
            Objects.equals(origem, that.origem) &&
            Objects.equals(acaoSGQId, that.acaoSGQId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(auditoriaId, that.auditoriaId) &&
            Objects.equals(resultadoChecklistId, that.resultadoChecklistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        idUsuarioResponsavel,
        titulo,
        procedente,
        prazoConclusao,
        novoPrazoConclusao,
        dataRegistro,
        dataConclusao,
        statusSGQ,
        origem,
        acaoSGQId,
        anexoId,
        auditoriaId,
        resultadoChecklistId
        );
    }

    @Override
    public String toString() {
        return "NaoConformidadeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (idUsuarioResponsavel != null ? "idUsuarioResponsavel=" + idUsuarioResponsavel + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (procedente != null ? "procedente=" + procedente + ", " : "") +
                (prazoConclusao != null ? "prazoConclusao=" + prazoConclusao + ", " : "") +
                (novoPrazoConclusao != null ? "novoPrazoConclusao=" + novoPrazoConclusao + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataConclusao != null ? "dataConclusao=" + dataConclusao + ", " : "") +
                (statusSGQ != null ? "statusSGQ=" + statusSGQ + ", " : "") +
                (origem != null ? "origem=" + origem + ", " : "") +
                (acaoSGQId != null ? "acaoSGQId=" + acaoSGQId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (auditoriaId != null ? "auditoriaId=" + auditoriaId + ", " : "") +
                (resultadoChecklistId != null ? "resultadoChecklistId=" + resultadoChecklistId + ", " : "") +
            "}";
    }

}
