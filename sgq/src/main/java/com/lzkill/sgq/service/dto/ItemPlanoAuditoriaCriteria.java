package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.ModalidadeAuditoria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.ItemPlanoAuditoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ItemPlanoAuditoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-plano-auditorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ItemPlanoAuditoriaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ModalidadeAuditoria
     */
    public static class ModalidadeAuditoriaFilter extends Filter<ModalidadeAuditoria> {

        public ModalidadeAuditoriaFilter() {
        }

        public ModalidadeAuditoriaFilter(ModalidadeAuditoriaFilter filter) {
            super(filter);
        }

        @Override
        public ModalidadeAuditoriaFilter copy() {
            return new ModalidadeAuditoriaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private ModalidadeAuditoriaFilter modalidade;

    private InstantFilter dataInicioPrevisto;

    private InstantFilter dataFimPrevisto;

    private LongFilter itemAuditoriaId;

    private LongFilter anexoId;

    private LongFilter planoId;

    public ItemPlanoAuditoriaCriteria(){
    }

    public ItemPlanoAuditoriaCriteria(ItemPlanoAuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.modalidade = other.modalidade == null ? null : other.modalidade.copy();
        this.dataInicioPrevisto = other.dataInicioPrevisto == null ? null : other.dataInicioPrevisto.copy();
        this.dataFimPrevisto = other.dataFimPrevisto == null ? null : other.dataFimPrevisto.copy();
        this.itemAuditoriaId = other.itemAuditoriaId == null ? null : other.itemAuditoriaId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.planoId = other.planoId == null ? null : other.planoId.copy();
    }

    @Override
    public ItemPlanoAuditoriaCriteria copy() {
        return new ItemPlanoAuditoriaCriteria(this);
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

    public ModalidadeAuditoriaFilter getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeAuditoriaFilter modalidade) {
        this.modalidade = modalidade;
    }

    public InstantFilter getDataInicioPrevisto() {
        return dataInicioPrevisto;
    }

    public void setDataInicioPrevisto(InstantFilter dataInicioPrevisto) {
        this.dataInicioPrevisto = dataInicioPrevisto;
    }

    public InstantFilter getDataFimPrevisto() {
        return dataFimPrevisto;
    }

    public void setDataFimPrevisto(InstantFilter dataFimPrevisto) {
        this.dataFimPrevisto = dataFimPrevisto;
    }

    public LongFilter getItemAuditoriaId() {
        return itemAuditoriaId;
    }

    public void setItemAuditoriaId(LongFilter itemAuditoriaId) {
        this.itemAuditoriaId = itemAuditoriaId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getPlanoId() {
        return planoId;
    }

    public void setPlanoId(LongFilter planoId) {
        this.planoId = planoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemPlanoAuditoriaCriteria that = (ItemPlanoAuditoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(modalidade, that.modalidade) &&
            Objects.equals(dataInicioPrevisto, that.dataInicioPrevisto) &&
            Objects.equals(dataFimPrevisto, that.dataFimPrevisto) &&
            Objects.equals(itemAuditoriaId, that.itemAuditoriaId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(planoId, that.planoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        modalidade,
        dataInicioPrevisto,
        dataFimPrevisto,
        itemAuditoriaId,
        anexoId,
        planoId
        );
    }

    @Override
    public String toString() {
        return "ItemPlanoAuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (modalidade != null ? "modalidade=" + modalidade + ", " : "") +
                (dataInicioPrevisto != null ? "dataInicioPrevisto=" + dataInicioPrevisto + ", " : "") +
                (dataFimPrevisto != null ? "dataFimPrevisto=" + dataFimPrevisto + ", " : "") +
                (itemAuditoriaId != null ? "itemAuditoriaId=" + itemAuditoriaId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (planoId != null ? "planoId=" + planoId + ", " : "") +
            "}";
    }

}
