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
 * Criteria class for the {@link com.lzkill.sgq.domain.ItemChecklist} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ItemChecklistResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-checklists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemChecklistCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter ordem;

    private StringFilter titulo;

    private LongFilter anexoId;

    private LongFilter checklistId;

    public ItemChecklistCriteria(){
    }

    public ItemChecklistCriteria(ItemChecklistCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.ordem = other.ordem == null ? null : other.ordem.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.checklistId = other.checklistId == null ? null : other.checklistId.copy();
    }

    @Override
    public ItemChecklistCriteria copy() {
        return new ItemChecklistCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrdem() {
        return ordem;
    }

    public void setOrdem(IntegerFilter ordem) {
        this.ordem = ordem;
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

    public LongFilter getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(LongFilter checklistId) {
        this.checklistId = checklistId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemChecklistCriteria that = (ItemChecklistCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ordem, that.ordem) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(checklistId, that.checklistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ordem,
        titulo,
        anexoId,
        checklistId
        );
    }

    @Override
    public String toString() {
        return "ItemChecklistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ordem != null ? "ordem=" + ordem + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (checklistId != null ? "checklistId=" + checklistId + ", " : "") +
            "}";
    }

}
