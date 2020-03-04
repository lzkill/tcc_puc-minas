package com.xpto.consultoria.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.xpto.consultoria.domain.enumeration.TipoAcaoSGQ;
import com.xpto.consultoria.domain.enumeration.StatusSGQ;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.xpto.consultoria.domain.AcaoSGQ} entity. This class is used
 * in {@link com.xpto.consultoria.web.rest.AcaoSGQResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /acao-sgqs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AcaoSGQCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoAcaoSGQ
     */
    public static class TipoAcaoSGQFilter extends Filter<TipoAcaoSGQ> {

        public TipoAcaoSGQFilter() {
        }

        public TipoAcaoSGQFilter(TipoAcaoSGQFilter filter) {
            super(filter);
        }

        @Override
        public TipoAcaoSGQFilter copy() {
            return new TipoAcaoSGQFilter(this);
        }

    }
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoAcaoSGQFilter tipo;

    private StringFilter titulo;

    private InstantFilter prazoConclusao;

    private InstantFilter novoPrazoConclusao;

    private InstantFilter dataRegistro;

    private InstantFilter dataConclusao;

    private StatusSGQFilter statusSGQ;

    private LongFilter anexoId;

    private LongFilter naoConformidadeId;

    public AcaoSGQCriteria(){
    }

    public AcaoSGQCriteria(AcaoSGQCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.prazoConclusao = other.prazoConclusao == null ? null : other.prazoConclusao.copy();
        this.novoPrazoConclusao = other.novoPrazoConclusao == null ? null : other.novoPrazoConclusao.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataConclusao = other.dataConclusao == null ? null : other.dataConclusao.copy();
        this.statusSGQ = other.statusSGQ == null ? null : other.statusSGQ.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
    }

    @Override
    public AcaoSGQCriteria copy() {
        return new AcaoSGQCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoAcaoSGQFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoAcaoSGQFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AcaoSGQCriteria that = (AcaoSGQCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(prazoConclusao, that.prazoConclusao) &&
            Objects.equals(novoPrazoConclusao, that.novoPrazoConclusao) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataConclusao, that.dataConclusao) &&
            Objects.equals(statusSGQ, that.statusSGQ) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        titulo,
        prazoConclusao,
        novoPrazoConclusao,
        dataRegistro,
        dataConclusao,
        statusSGQ,
        anexoId,
        naoConformidadeId
        );
    }

    @Override
    public String toString() {
        return "AcaoSGQCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (prazoConclusao != null ? "prazoConclusao=" + prazoConclusao + ", " : "") +
                (novoPrazoConclusao != null ? "novoPrazoConclusao=" + novoPrazoConclusao + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataConclusao != null ? "dataConclusao=" + dataConclusao + ", " : "") +
                (statusSGQ != null ? "statusSGQ=" + statusSGQ + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
            "}";
    }

}
