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
 * Criteria class for the {@link com.lzkill.sgq.domain.ItemAuditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ItemAuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemAuditoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private BooleanFilter habilitado;

    private LongFilter processoId;

    private LongFilter anexoId;

    private LongFilter auditoriaId;

    public ItemAuditoriaCriteria(){
    }

    public ItemAuditoriaCriteria(ItemAuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.auditoriaId = other.auditoriaId == null ? null : other.auditoriaId.copy();
    }

    @Override
    public ItemAuditoriaCriteria copy() {
        return new ItemAuditoriaCriteria(this);
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

    public LongFilter getProcessoId() {
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemAuditoriaCriteria that = (ItemAuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(habilitado, that.habilitado) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(auditoriaId, that.auditoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        habilitado,
        processoId,
        anexoId,
        auditoriaId
        );
    }

    @Override
    public String toString() {
        return "ItemAuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (auditoriaId != null ? "auditoriaId=" + auditoriaId + ", " : "") +
            "}";
    }

}
