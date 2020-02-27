package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.TipoFeed;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.Feed} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.FeedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feeds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeedCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoFeed
     */
    public static class TipoFeedFilter extends Filter<TipoFeed> {

        public TipoFeedFilter() {
        }

        public TipoFeedFilter(TipoFeedFilter filter) {
            super(filter);
        }

        @Override
        public TipoFeedFilter copy() {
            return new TipoFeedFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private TipoFeedFilter tipo;

    private StringFilter titulo;

    private StringFilter descricao;

    private StringFilter uri;

    private StringFilter link;

    private StringFilter urlImagem;

    private StringFilter tituloImagem;

    private IntegerFilter alturaImagem;

    private IntegerFilter larguraImagem;

    private InstantFilter dataRegistro;

    private BooleanFilter habilitado;

    public FeedCriteria(){
    }

    public FeedCriteria(FeedCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.uri = other.uri == null ? null : other.uri.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.urlImagem = other.urlImagem == null ? null : other.urlImagem.copy();
        this.tituloImagem = other.tituloImagem == null ? null : other.tituloImagem.copy();
        this.alturaImagem = other.alturaImagem == null ? null : other.alturaImagem.copy();
        this.larguraImagem = other.larguraImagem == null ? null : other.larguraImagem.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
    }

    @Override
    public FeedCriteria copy() {
        return new FeedCriteria(this);
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

    public TipoFeedFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoFeedFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getUri() {
        return uri;
    }

    public void setUri(StringFilter uri) {
        this.uri = uri;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(StringFilter urlImagem) {
        this.urlImagem = urlImagem;
    }

    public StringFilter getTituloImagem() {
        return tituloImagem;
    }

    public void setTituloImagem(StringFilter tituloImagem) {
        this.tituloImagem = tituloImagem;
    }

    public IntegerFilter getAlturaImagem() {
        return alturaImagem;
    }

    public void setAlturaImagem(IntegerFilter alturaImagem) {
        this.alturaImagem = alturaImagem;
    }

    public IntegerFilter getLarguraImagem() {
        return larguraImagem;
    }

    public void setLarguraImagem(IntegerFilter larguraImagem) {
        this.larguraImagem = larguraImagem;
    }

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
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
        final FeedCriteria that = (FeedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(link, that.link) &&
            Objects.equals(urlImagem, that.urlImagem) &&
            Objects.equals(tituloImagem, that.tituloImagem) &&
            Objects.equals(alturaImagem, that.alturaImagem) &&
            Objects.equals(larguraImagem, that.larguraImagem) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(habilitado, that.habilitado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        tipo,
        titulo,
        descricao,
        uri,
        link,
        urlImagem,
        tituloImagem,
        alturaImagem,
        larguraImagem,
        dataRegistro,
        habilitado
        );
    }

    @Override
    public String toString() {
        return "FeedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (uri != null ? "uri=" + uri + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (urlImagem != null ? "urlImagem=" + urlImagem + ", " : "") +
                (tituloImagem != null ? "tituloImagem=" + tituloImagem + ", " : "") +
                (alturaImagem != null ? "alturaImagem=" + alturaImagem + ", " : "") +
                (larguraImagem != null ? "larguraImagem=" + larguraImagem + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
            "}";
    }

}
