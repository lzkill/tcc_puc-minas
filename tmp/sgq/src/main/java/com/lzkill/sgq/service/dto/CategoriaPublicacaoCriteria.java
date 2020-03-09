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
 * Criteria class for the {@link com.lzkill.sgq.domain.CategoriaPublicacao} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.CategoriaPublicacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categoria-publicacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriaPublicacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private BooleanFilter habilitado;

    private LongFilter boletimId;

    private LongFilter publicacaoId;

    public CategoriaPublicacaoCriteria(){
    }

    public CategoriaPublicacaoCriteria(CategoriaPublicacaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
        this.boletimId = other.boletimId == null ? null : other.boletimId.copy();
        this.publicacaoId = other.publicacaoId == null ? null : other.publicacaoId.copy();
    }

    @Override
    public CategoriaPublicacaoCriteria copy() {
        return new CategoriaPublicacaoCriteria(this);
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

    public BooleanFilter getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(BooleanFilter habilitado) {
        this.habilitado = habilitado;
    }

    public LongFilter getBoletimId() {
        return boletimId;
    }

    public void setBoletimId(LongFilter boletimId) {
        this.boletimId = boletimId;
    }

    public LongFilter getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(LongFilter publicacaoId) {
        this.publicacaoId = publicacaoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoriaPublicacaoCriteria that = (CategoriaPublicacaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(habilitado, that.habilitado) &&
            Objects.equals(boletimId, that.boletimId) &&
            Objects.equals(publicacaoId, that.publicacaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        habilitado,
        boletimId,
        publicacaoId
        );
    }

    @Override
    public String toString() {
        return "CategoriaPublicacaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
                (boletimId != null ? "boletimId=" + boletimId + ", " : "") +
                (publicacaoId != null ? "publicacaoId=" + publicacaoId + ", " : "") +
            "}";
    }

}
