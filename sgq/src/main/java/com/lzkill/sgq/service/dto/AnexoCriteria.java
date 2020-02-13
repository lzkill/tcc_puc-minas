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

    private LongFilter acaoSGQId;

    private LongFilter analiseConsultoriaId;

    private LongFilter checklistId;

    private LongFilter boletimInformativoId;

    private LongFilter campanhaRecallId;

    private LongFilter eventoOperacionalId;

    private LongFilter itemChecklistId;

    private LongFilter itemPlanoAuditoriaId;

    private LongFilter naoConformidadeId;

    private LongFilter processoId;

    private LongFilter produtoId;

    private LongFilter planoAuditoriaId;

    private LongFilter produtoNaoConformeId;

    private LongFilter publicacaoFeedId;

    private LongFilter resultadoChecklistId;

    private LongFilter resultadoItemChecklistId;

    public AnexoCriteria(){
    }

    public AnexoCriteria(AnexoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nomeArquivo = other.nomeArquivo == null ? null : other.nomeArquivo.copy();
        this.acaoSGQId = other.acaoSGQId == null ? null : other.acaoSGQId.copy();
        this.analiseConsultoriaId = other.analiseConsultoriaId == null ? null : other.analiseConsultoriaId.copy();
        this.checklistId = other.checklistId == null ? null : other.checklistId.copy();
        this.boletimInformativoId = other.boletimInformativoId == null ? null : other.boletimInformativoId.copy();
        this.campanhaRecallId = other.campanhaRecallId == null ? null : other.campanhaRecallId.copy();
        this.eventoOperacionalId = other.eventoOperacionalId == null ? null : other.eventoOperacionalId.copy();
        this.itemChecklistId = other.itemChecklistId == null ? null : other.itemChecklistId.copy();
        this.itemPlanoAuditoriaId = other.itemPlanoAuditoriaId == null ? null : other.itemPlanoAuditoriaId.copy();
        this.naoConformidadeId = other.naoConformidadeId == null ? null : other.naoConformidadeId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.planoAuditoriaId = other.planoAuditoriaId == null ? null : other.planoAuditoriaId.copy();
        this.produtoNaoConformeId = other.produtoNaoConformeId == null ? null : other.produtoNaoConformeId.copy();
        this.publicacaoFeedId = other.publicacaoFeedId == null ? null : other.publicacaoFeedId.copy();
        this.resultadoChecklistId = other.resultadoChecklistId == null ? null : other.resultadoChecklistId.copy();
        this.resultadoItemChecklistId = other.resultadoItemChecklistId == null ? null : other.resultadoItemChecklistId.copy();
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

    public LongFilter getAcaoSGQId() {
        return acaoSGQId;
    }

    public void setAcaoSGQId(LongFilter acaoSGQId) {
        this.acaoSGQId = acaoSGQId;
    }

    public LongFilter getAnaliseConsultoriaId() {
        return analiseConsultoriaId;
    }

    public void setAnaliseConsultoriaId(LongFilter analiseConsultoriaId) {
        this.analiseConsultoriaId = analiseConsultoriaId;
    }

    public LongFilter getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(LongFilter checklistId) {
        this.checklistId = checklistId;
    }

    public LongFilter getBoletimInformativoId() {
        return boletimInformativoId;
    }

    public void setBoletimInformativoId(LongFilter boletimInformativoId) {
        this.boletimInformativoId = boletimInformativoId;
    }

    public LongFilter getCampanhaRecallId() {
        return campanhaRecallId;
    }

    public void setCampanhaRecallId(LongFilter campanhaRecallId) {
        this.campanhaRecallId = campanhaRecallId;
    }

    public LongFilter getEventoOperacionalId() {
        return eventoOperacionalId;
    }

    public void setEventoOperacionalId(LongFilter eventoOperacionalId) {
        this.eventoOperacionalId = eventoOperacionalId;
    }

    public LongFilter getItemChecklistId() {
        return itemChecklistId;
    }

    public void setItemChecklistId(LongFilter itemChecklistId) {
        this.itemChecklistId = itemChecklistId;
    }

    public LongFilter getItemPlanoAuditoriaId() {
        return itemPlanoAuditoriaId;
    }

    public void setItemPlanoAuditoriaId(LongFilter itemPlanoAuditoriaId) {
        this.itemPlanoAuditoriaId = itemPlanoAuditoriaId;
    }

    public LongFilter getNaoConformidadeId() {
        return naoConformidadeId;
    }

    public void setNaoConformidadeId(LongFilter naoConformidadeId) {
        this.naoConformidadeId = naoConformidadeId;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getPlanoAuditoriaId() {
        return planoAuditoriaId;
    }

    public void setPlanoAuditoriaId(LongFilter planoAuditoriaId) {
        this.planoAuditoriaId = planoAuditoriaId;
    }

    public LongFilter getProdutoNaoConformeId() {
        return produtoNaoConformeId;
    }

    public void setProdutoNaoConformeId(LongFilter produtoNaoConformeId) {
        this.produtoNaoConformeId = produtoNaoConformeId;
    }

    public LongFilter getPublicacaoFeedId() {
        return publicacaoFeedId;
    }

    public void setPublicacaoFeedId(LongFilter publicacaoFeedId) {
        this.publicacaoFeedId = publicacaoFeedId;
    }

    public LongFilter getResultadoChecklistId() {
        return resultadoChecklistId;
    }

    public void setResultadoChecklistId(LongFilter resultadoChecklistId) {
        this.resultadoChecklistId = resultadoChecklistId;
    }

    public LongFilter getResultadoItemChecklistId() {
        return resultadoItemChecklistId;
    }

    public void setResultadoItemChecklistId(LongFilter resultadoItemChecklistId) {
        this.resultadoItemChecklistId = resultadoItemChecklistId;
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
            Objects.equals(nomeArquivo, that.nomeArquivo) &&
            Objects.equals(acaoSGQId, that.acaoSGQId) &&
            Objects.equals(analiseConsultoriaId, that.analiseConsultoriaId) &&
            Objects.equals(checklistId, that.checklistId) &&
            Objects.equals(boletimInformativoId, that.boletimInformativoId) &&
            Objects.equals(campanhaRecallId, that.campanhaRecallId) &&
            Objects.equals(eventoOperacionalId, that.eventoOperacionalId) &&
            Objects.equals(itemChecklistId, that.itemChecklistId) &&
            Objects.equals(itemPlanoAuditoriaId, that.itemPlanoAuditoriaId) &&
            Objects.equals(naoConformidadeId, that.naoConformidadeId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(planoAuditoriaId, that.planoAuditoriaId) &&
            Objects.equals(produtoNaoConformeId, that.produtoNaoConformeId) &&
            Objects.equals(publicacaoFeedId, that.publicacaoFeedId) &&
            Objects.equals(resultadoChecklistId, that.resultadoChecklistId) &&
            Objects.equals(resultadoItemChecklistId, that.resultadoItemChecklistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeArquivo,
        acaoSGQId,
        analiseConsultoriaId,
        checklistId,
        boletimInformativoId,
        campanhaRecallId,
        eventoOperacionalId,
        itemChecklistId,
        itemPlanoAuditoriaId,
        naoConformidadeId,
        processoId,
        produtoId,
        planoAuditoriaId,
        produtoNaoConformeId,
        publicacaoFeedId,
        resultadoChecklistId,
        resultadoItemChecklistId
        );
    }

    @Override
    public String toString() {
        return "AnexoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeArquivo != null ? "nomeArquivo=" + nomeArquivo + ", " : "") +
                (acaoSGQId != null ? "acaoSGQId=" + acaoSGQId + ", " : "") +
                (analiseConsultoriaId != null ? "analiseConsultoriaId=" + analiseConsultoriaId + ", " : "") +
                (checklistId != null ? "checklistId=" + checklistId + ", " : "") +
                (boletimInformativoId != null ? "boletimInformativoId=" + boletimInformativoId + ", " : "") +
                (campanhaRecallId != null ? "campanhaRecallId=" + campanhaRecallId + ", " : "") +
                (eventoOperacionalId != null ? "eventoOperacionalId=" + eventoOperacionalId + ", " : "") +
                (itemChecklistId != null ? "itemChecklistId=" + itemChecklistId + ", " : "") +
                (itemPlanoAuditoriaId != null ? "itemPlanoAuditoriaId=" + itemPlanoAuditoriaId + ", " : "") +
                (naoConformidadeId != null ? "naoConformidadeId=" + naoConformidadeId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (planoAuditoriaId != null ? "planoAuditoriaId=" + planoAuditoriaId + ", " : "") +
                (produtoNaoConformeId != null ? "produtoNaoConformeId=" + produtoNaoConformeId + ", " : "") +
                (publicacaoFeedId != null ? "publicacaoFeedId=" + publicacaoFeedId + ", " : "") +
                (resultadoChecklistId != null ? "resultadoChecklistId=" + resultadoChecklistId + ", " : "") +
                (resultadoItemChecklistId != null ? "resultadoItemChecklistId=" + resultadoItemChecklistId + ", " : "") +
            "}";
    }

}
