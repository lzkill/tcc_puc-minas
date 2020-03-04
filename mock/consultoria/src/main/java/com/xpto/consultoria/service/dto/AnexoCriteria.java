package com.xpto.consultoria.service.dto;

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
 * Criteria class for the {@link com.xpto.consultoria.domain.Anexo} entity. This class is used
 * in {@link com.xpto.consultoria.web.rest.AnexoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anexos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnexoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeArquivo;

    private LongFilter acaoSGQId;

    private LongFilter analiseConsultoriaId;

    private LongFilter naoConformidadeId;

    public AnexoCriteria(){
    }

    public AnexoCriteria(AnexoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nomeArquivo = other.nomeArquivo == null ? null : other.nomeArquivo.copy();
        this.acaoSGQId = other.acaoSGQId == null ? null : other.acaoSGQId.copy();
        this.analiseConsultoriaId = other.analiseConsultoriaId == null ? null : other.analiseConsultoriaId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
    }

    @Override
    public AnexoCriteria copy() {
        return new AnexoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(StringFilter nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public LongFilter getAcaoSGQId() {
        return acaoSGQId;
    }

    public void setAcaoSGQId(LongFilter acaoSGQId) {
        this.acaoSGQId = acaoSGQId;
    }

    public LongFilter getAnaliseConsultoriaId() {
        return analiseConsultoriaId;
    }

    public void setAnaliseConsultoriaId(LongFilter analiseConsultoriaId) {
        this.analiseConsultoriaId = analiseConsultoriaId;
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
        final AnexoCriteria that = (AnexoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomeArquivo, that.nomeArquivo) &&
            Objects.equals(acaoSGQId, that.acaoSGQId) &&
            Objects.equals(analiseConsultoriaId, that.analiseConsultoriaId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeArquivo,
        acaoSGQId,
        analiseConsultoriaId,
        naoConformidadeId
        );
    }

    @Override
    public String toString() {
        return "AnexoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeArquivo != null ? "nomeArquivo=" + nomeArquivo + ", " : "") +
                (acaoSGQId != null ? "acaoSGQId=" + acaoSGQId + ", " : "") +
                (analiseConsultoriaId != null ? "analiseConsultoriaId=" + analiseConsultoriaId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
            "}";
    }

}
