package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.TipoAuditoria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.Auditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.AuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuditoriaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoAuditoria
     */
    public static class TipoAuditoriaFilter extends Filter<TipoAuditoria> {

        public TipoAuditoriaFilter() {
        }

        public TipoAuditoriaFilter(TipoAuditoriaFilter filter) {
            super(filter);
        }

        @Override
        public TipoAuditoriaFilter copy() {
            return new TipoAuditoriaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoAuditoriaFilter tipo;

    private StringFilter titulo;

    private LongFilter processoId;

    public AuditoriaCriteria(){
    }

    public AuditoriaCriteria(AuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public AuditoriaCriteria copy() {
        return new AuditoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoAuditoriaFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoAuditoriaFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuditoriaCriteria that = (AuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        titulo,
        processoId
        );
    }

    @Override
    public String toString() {
        return "AuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
