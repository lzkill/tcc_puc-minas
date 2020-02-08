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
 * Criteria class for the {@link com.lzkill.sgq.domain.Anexo} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.AnexoResource} to receive all the possible filtering options from
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

    public AnexoCriteria(){
    }

    public AnexoCriteria(AnexoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nomeArquivo = other.nomeArquivo == null ? null : other.nomeArquivo.copy();
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
            Objects.equals(nomeArquivo, that.nomeArquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeArquivo
        );
    }

    @Override
    public String toString() {
        return "AnexoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeArquivo != null ? "nomeArquivo=" + nomeArquivo + ", " : "") +
            "}";
    }

}
