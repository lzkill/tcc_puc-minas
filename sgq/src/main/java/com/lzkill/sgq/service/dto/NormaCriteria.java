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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.Norma} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.NormaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /normas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NormaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orgao;

    private StringFilter titulo;

    private StringFilter versao;

    private IntegerFilter numeroEdicao;

    private InstantFilter dataEdicao;

    private InstantFilter dataInicioValidade;

    private StringFilter categoria;

    private StringFilter urlDownload;

    public NormaCriteria(){
    }

    public NormaCriteria(NormaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.orgao = other.orgao == null ? null : other.orgao.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.versao = other.versao == null ? null : other.versao.copy();
        this.numeroEdicao = other.numeroEdicao == null ? null : other.numeroEdicao.copy();
        this.dataEdicao = other.dataEdicao == null ? null : other.dataEdicao.copy();
        this.dataInicioValidade = other.dataInicioValidade == null ? null : other.dataInicioValidade.copy();
        this.categoria = other.categoria == null ? null : other.categoria.copy();
        this.urlDownload = other.urlDownload == null ? null : other.urlDownload.copy();
    }

    @Override
    public NormaCriteria copy() {
        return new NormaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrgao() {
        return orgao;
    }

    public void setOrgao(StringFilter orgao) {
        this.orgao = orgao;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getVersao() {
        return versao;
    }

    public void setVersao(StringFilter versao) {
        this.versao = versao;
    }

    public IntegerFilter getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(IntegerFilter numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public InstantFilter getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(InstantFilter dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public InstantFilter getDataInicioValidade() {
        return dataInicioValidade;
    }

    public void setDataInicioValidade(InstantFilter dataInicioValidade) {
        this.dataInicioValidade = dataInicioValidade;
    }

    public StringFilter getCategoria() {
        return categoria;
    }

    public void setCategoria(StringFilter categoria) {
        this.categoria = categoria;
    }

    public StringFilter getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(StringFilter urlDownload) {
        this.urlDownload = urlDownload;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NormaCriteria that = (NormaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orgao, that.orgao) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(versao, that.versao) &&
            Objects.equals(numeroEdicao, that.numeroEdicao) &&
            Objects.equals(dataEdicao, that.dataEdicao) &&
            Objects.equals(dataInicioValidade, that.dataInicioValidade) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(urlDownload, that.urlDownload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orgao,
        titulo,
        versao,
        numeroEdicao,
        dataEdicao,
        dataInicioValidade,
        categoria,
        urlDownload
        );
    }

    @Override
    public String toString() {
        return "NormaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orgao != null ? "orgao=" + orgao + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (versao != null ? "versao=" + versao + ", " : "") +
                (numeroEdicao != null ? "numeroEdicao=" + numeroEdicao + ", " : "") +
                (dataEdicao != null ? "dataEdicao=" + dataEdicao + ", " : "") +
                (dataInicioValidade != null ? "dataInicioValidade=" + dataInicioValidade + ", " : "") +
                (categoria != null ? "categoria=" + categoria + ", " : "") +
                (urlDownload != null ? "urlDownload=" + urlDownload + ", " : "") +
            "}";
    }

}
