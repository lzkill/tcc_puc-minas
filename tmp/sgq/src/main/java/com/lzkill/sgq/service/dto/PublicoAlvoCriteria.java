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
 * Criteria class for the {@link com.lzkill.sgq.domain.PublicoAlvo} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.PublicoAlvoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /publico-alvos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PublicoAlvoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter habilitado;

    public PublicoAlvoCriteria(){
    }

    public PublicoAlvoCriteria(PublicoAlvoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
    }

    @Override
    public PublicoAlvoCriteria copy() {
        return new PublicoAlvoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public BooleanFilter getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(BooleanFilter habilitado) {
        this.habilitado = habilitado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PublicoAlvoCriteria that = (PublicoAlvoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(habilitado, that.habilitado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        habilitado
        );
    }

    @Override
    public String toString() {
        return "PublicoAlvoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
            "}";
    }

}
