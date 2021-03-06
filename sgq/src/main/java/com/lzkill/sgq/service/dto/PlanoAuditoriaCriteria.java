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
 * Criteria class for the {@link com.lzkill.sgq.domain.PlanoAuditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.PlanoAuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlanoAuditoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private BooleanFilter habilitado;

    private LongFilter itemId;

    private LongFilter anexoId;

    public PlanoAuditoriaCriteria(){
    }

    public PlanoAuditoriaCriteria(PlanoAuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
    }

    @Override
    public PlanoAuditoriaCriteria copy() {
        return new PlanoAuditoriaCriteria(this);
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

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
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
        final PlanoAuditoriaCriteria that = (PlanoAuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(habilitado, that.habilitado) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(anexoId, that.anexoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        habilitado,
        itemId,
        anexoId
        );
    }

    @Override
    public String toString() {
        return "PlanoAuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
            "}";
    }

}
