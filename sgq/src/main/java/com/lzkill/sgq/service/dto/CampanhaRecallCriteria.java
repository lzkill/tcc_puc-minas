package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusPublicacao;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.CampanhaRecall} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.CampanhaRecallResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /campanha-recalls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CampanhaRecallCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusPublicacao
     */
    public static class StatusPublicacaoFilter extends Filter<StatusPublicacao> {

        public StatusPublicacaoFilter() {
        }

        public StatusPublicacaoFilter(StatusPublicacaoFilter filter) {
            super(filter);
        }

        @Override
        public StatusPublicacaoFilter copy() {
            return new StatusPublicacaoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private StringFilter titulo;

    private InstantFilter dataRegistro;

    private InstantFilter dataInicio;

    private InstantFilter dataFim;

    private InstantFilter dataPublicacao;

    private StatusPublicacaoFilter status;

    private LongFilter produtoId;

    private LongFilter setorResponsavelId;

    private LongFilter anexoId;

    public CampanhaRecallCriteria(){
    }

    public CampanhaRecallCriteria(CampanhaRecallCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataInicio = other.dataInicio == null ? null : other.dataInicio.copy();
        this.dataFim = other.dataFim == null ? null : other.dataFim.copy();
        this.dataPublicacao = other.dataPublicacao == null ? null : other.dataPublicacao.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.setorResponsavelId = other.setorResponsavelId == null ? null : other.setorResponsavelId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
    }

    @Override
    public CampanhaRecallCriteria copy() {
        return new CampanhaRecallCriteria(this);
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

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
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

    public InstantFilter getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(InstantFilter dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusPublicacaoFilter getStatus() {
        return status;
    }

    public void setStatus(StatusPublicacaoFilter status) {
        this.status = status;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getSetorResponsavelId() {
        return setorResponsavelId;
    }

    public void setSetorResponsavelId(LongFilter setorResponsavelId) {
        this.setorResponsavelId = setorResponsavelId;
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
        final CampanhaRecallCriteria that = (CampanhaRecallCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataInicio, that.dataInicio) &&
            Objects.equals(dataFim, that.dataFim) &&
            Objects.equals(dataPublicacao, that.dataPublicacao) &&
            Objects.equals(status, that.status) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(setorResponsavelId, that.setorResponsavelId) &&
            Objects.equals(anexoId, that.anexoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        titulo,
        dataRegistro,
        dataInicio,
        dataFim,
        dataPublicacao,
        status,
        produtoId,
        setorResponsavelId,
        anexoId
        );
    }

    @Override
    public String toString() {
        return "CampanhaRecallCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataInicio != null ? "dataInicio=" + dataInicio + ", " : "") +
                (dataFim != null ? "dataFim=" + dataFim + ", " : "") +
                (dataPublicacao != null ? "dataPublicacao=" + dataPublicacao + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (setorResponsavelId != null ? "setorResponsavelId=" + setorResponsavelId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
            "}";
    }

}
