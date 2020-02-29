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

    private IntegerFilter idUsuarioRegistro;

    private StringFilter titulo;

    private ModalidadeAuditoriaFilter modalidade;

    private InstantFilter dataRegistro;

    private InstantFilter dataInicio;

    private InstantFilter dataFim;

    private StringFilter auditor;

    private LongFilter naoConformidadeId;

    private LongFilter consultoriaId;

    private LongFilter itemAuditoriaId;

    private LongFilter anexoId;

    public AuditoriaCriteria(){
    }

    public AuditoriaCriteria(AuditoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.modalidade = other.modalidade == null ? null : other.modalidade.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataInicio = other.dataInicio == null ? null : other.dataInicio.copy();
        this.dataFim = other.dataFim == null ? null : other.dataFim.copy();
        this.auditor = other.auditor == null ? null : other.auditor.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.consultoriaId = other.consultoriaId == null ? null : other.consultoriaId.copy();
        this.itemAuditoriaId = other.itemAuditoriaId == null ? null : other.itemAuditoriaId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
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

    public IntegerFilter getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(IntegerFilter idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
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

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public InstantFilter getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(InstantFilter dataInicio) {
        this.dataInicio = dataInicio;
    }

    public InstantFilter getDataFim() {
        return dataFim;
    }

    public void setDataFim(InstantFilter dataFim) {
        this.dataFim = dataFim;
    }

    public StringFilter getAuditor() {
        return auditor;
    }

    public void setAuditor(StringFilter auditor) {
        this.auditor = auditor;
    }

    public LongFilter getNaoConformidadeId() {
        return naoConformidadeId;
    }

    public void setNaoConformidadeId(LongFilter naoConformidadeId) {
        this.naoConformidadeId = naoConformidadeId;
    }

    public LongFilter getConsultoriaId() {
        return consultoriaId;
    }

    public void setConsultoriaId(LongFilter consultoriaId) {
        this.consultoriaId = consultoriaId;
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
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(modalidade, that.modalidade) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataInicio, that.dataInicio) &&
            Objects.equals(dataFim, that.dataFim) &&
            Objects.equals(auditor, that.auditor) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(consultoriaId, that.consultoriaId) &&
            Objects.equals(itemAuditoriaId, that.itemAuditoriaId) &&
            Objects.equals(anexoId, that.anexoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        titulo,
        modalidade,
        dataRegistro,
        dataInicio,
        dataFim,
        auditor,
        naoConformidadeId,
        consultoriaId,
        itemAuditoriaId,
        anexoId
        );
    }

    @Override
    public String toString() {
        return "AuditoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (modalidade != null ? "modalidade=" + modalidade + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataInicio != null ? "dataInicio=" + dataInicio + ", " : "") +
                (dataFim != null ? "dataFim=" + dataFim + ", " : "") +
                (auditor != null ? "auditor=" + auditor + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (consultoriaId != null ? "consultoriaId=" + consultoriaId + ", " : "") +
                (itemAuditoriaId != null ? "itemAuditoriaId=" + itemAuditoriaId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
            "}";
    }

}
