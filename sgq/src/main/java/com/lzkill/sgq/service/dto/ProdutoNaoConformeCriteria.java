package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusSGQ;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.ProdutoNaoConforme} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ProdutoNaoConformeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produto-nao-conformes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdutoNaoConformeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusSGQ
     */
    public static class StatusSGQFilter extends Filter<StatusSGQ> {

        public StatusSGQFilter() {
        }

        public StatusSGQFilter(StatusSGQFilter filter) {
            super(filter);
        }

        @Override
        public StatusSGQFilter copy() {
            return new StatusSGQFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private IntegerFilter idUsuarioResponsavel;

    private StringFilter titulo;

    private BooleanFilter procedente;

    private InstantFilter dataRegistro;

    private StatusSGQFilter statusSGQ;

    private LongFilter acaoId;

    private LongFilter naoConformidadeId;

    private LongFilter anexoId;

    private LongFilter produtoId;

    public ProdutoNaoConformeCriteria(){
    }

    public ProdutoNaoConformeCriteria(ProdutoNaoConformeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.idUsuarioResponsavel = other.idUsuarioResponsavel == null ? null : other.idUsuarioResponsavel.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.procedente = other.procedente == null ? null : other.procedente.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.statusSGQ = other.statusSGQ == null ? null : other.statusSGQ.copy();
        this.acaoId = other.acaoId == null ? null : other.acaoId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
    }

    @Override
    public ProdutoNaoConformeCriteria copy() {
        return new ProdutoNaoConformeCriteria(this);
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

    public IntegerFilter getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public void setIdUsuarioResponsavel(IntegerFilter idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public BooleanFilter getProcedente() {
        return procedente;
    }

    public void setProcedente(BooleanFilter procedente) {
        this.procedente = procedente;
    }

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public StatusSGQFilter getStatusSGQ() {
        return statusSGQ;
    }

    public void setStatusSGQ(StatusSGQFilter statusSGQ) {
        this.statusSGQ = statusSGQ;
    }

    public LongFilter getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(LongFilter acaoId) {
        this.acaoId = acaoId;
    }

    public LongFilter getNaoConformidadeId() {
        return naoConformidadeId;
    }

    public void setNaoConformidadeId(LongFilter naoConformidadeId) {
        this.naoConformidadeId = naoConformidadeId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProdutoNaoConformeCriteria that = (ProdutoNaoConformeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(idUsuarioResponsavel, that.idUsuarioResponsavel) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(procedente, that.procedente) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(statusSGQ, that.statusSGQ) &&
            Objects.equals(acaoId, that.acaoId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        idUsuarioResponsavel,
        titulo,
        procedente,
        dataRegistro,
        statusSGQ,
        acaoId,
        naoConformidadeId,
        anexoId,
        produtoId
        );
    }

    @Override
    public String toString() {
        return "ProdutoNaoConformeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (idUsuarioResponsavel != null ? "idUsuarioResponsavel=" + idUsuarioResponsavel + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (procedente != null ? "procedente=" + procedente + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (statusSGQ != null ? "statusSGQ=" + statusSGQ + ", " : "") +
                (acaoId != null ? "acaoId=" + acaoId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
            "}";
    }

}
