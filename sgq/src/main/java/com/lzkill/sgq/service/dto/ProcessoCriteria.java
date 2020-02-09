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

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.Processo} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private LongFilter anexoId;

    private LongFilter setorId;

    public ProcessoCriteria(){
    }

    public ProcessoCriteria(ProcessoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.setorId = other.setorId == null ? null : other.setorId.copy();
    }

    @Override
    public ProcessoCriteria copy() {
        return new ProcessoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getSetorId() {
        return setorId;
    }

    public void setSetorId(LongFilter setorId) {
        this.setorId = setorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessoCriteria that = (ProcessoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(setorId, that.setorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        anexoId,
        setorId
        );
    }

    @Override
    public String toString() {
        return "ProcessoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (setorId != null ? "setorId=" + setorId + ", " : "") +
            "}";
    }

}
