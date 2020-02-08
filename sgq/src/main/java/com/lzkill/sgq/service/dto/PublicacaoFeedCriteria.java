package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.StatusPublicacao;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.PublicacaoFeed} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.PublicacaoFeedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /publicacao-feeds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PublicacaoFeedCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusPublicacao
     */
    public static class StatusPublicacaoFilter extends Filter<StatusPublicacao> {

        public StatusPublicacaoFilter() {
        }

        public StatusPublicacaoFilter(StatusPublicacaoFilter filter) {
            super(filter);
        }

        @Override
        public StatusPublicacaoFilter copy() {
            return new StatusPublicacaoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private StringFilter titulo;

    private StringFilter autor;

    private StringFilter uri;

    private StringFilter link;

    private InstantFilter dataRegistro;

    private InstantFilter dataPublicacao;

    private StatusPublicacaoFilter status;

    private LongFilter anexoId;

    private LongFilter feedId;

    private LongFilter categoriaId;

    public PublicacaoFeedCriteria(){
    }

    public PublicacaoFeedCriteria(PublicacaoFeedCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.autor = other.autor == null ? null : other.autor.copy();
        this.uri = other.uri == null ? null : other.uri.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.dataRegistro = other.dataRegistro == null ? null : other.dataRegistro.copy();
        this.dataPublicacao = other.dataPublicacao == null ? null : other.dataPublicacao.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.feedId = other.feedId == null ? null : other.feedId.copy();
        this.categoriaId = other.categoriaId == null ? null : other.categoriaId.copy();
    }

    @Override
    public PublicacaoFeedCriteria copy() {
        return new PublicacaoFeedCriteria(this);
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

    public StringFilter getAutor() {
        return autor;
    }

    public void setAutor(StringFilter autor) {
        this.autor = autor;
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

    public InstantFilter getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(InstantFilter dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public InstantFilter getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(InstantFilter dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusPublicacaoFilter getStatus() {
        return status;
    }

    public void setStatus(StatusPublicacaoFilter status) {
        this.status = status;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getFeedId() {
        return feedId;
    }

    public void setFeedId(LongFilter feedId) {
        this.feedId = feedId;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PublicacaoFeedCriteria that = (PublicacaoFeedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(autor, that.autor) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(link, that.link) &&
            Objects.equals(dataRegistro, that.dataRegistro) &&
            Objects.equals(dataPublicacao, that.dataPublicacao) &&
            Objects.equals(status, that.status) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(feedId, that.feedId) &&
            Objects.equals(categoriaId, that.categoriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        titulo,
        autor,
        uri,
        link,
        dataRegistro,
        dataPublicacao,
        status,
        anexoId,
        feedId,
        categoriaId
        );
    }

    @Override
    public String toString() {
        return "PublicacaoFeedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (autor != null ? "autor=" + autor + ", " : "") +
                (uri != null ? "uri=" + uri + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (dataRegistro != null ? "dataRegistro=" + dataRegistro + ", " : "") +
                (dataPublicacao != null ? "dataPublicacao=" + dataPublicacao + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (feedId != null ? "feedId=" + feedId + ", " : "") +
                (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
            "}";
    }

}
