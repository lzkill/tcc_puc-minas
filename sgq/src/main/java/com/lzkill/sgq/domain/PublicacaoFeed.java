package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.lzkill.sgq.domain.enumeration.StatusPublicacao;

/**
 * A PublicacaoFeed.
 */
@Entity
@Table(name = "publicacao_feed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PublicacaoFeed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "autor", length = 100, nullable = false)
    private String autor;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "uri", length = 150, nullable = false, unique = true)
    private String uri;

    @Size(min = 1, max = 150)
    @Column(name = "link", length = 150)
    private String link;

    
    @Lob
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_publicacao")
    private Instant dataPublicacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPublicacao status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("publicacaoFeeds")
    private Feed feed;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "publicacao_feed_categoria",
               joinColumns = @JoinColumn(name = "publicacao_feed_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<CategoriaPublicacao> categorias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publicacao_feed_anexo",
               joinColumns = @JoinColumn(name = "publicacao_feed_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public PublicacaoFeed idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public String getTitulo() {
        return titulo;
    }

    public PublicacaoFeed titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public PublicacaoFeed autor(String autor) {
        this.autor = autor;
        return this;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUri() {
        return uri;
    }

    public PublicacaoFeed uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLink() {
        return link;
    }

    public PublicacaoFeed link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getConteudo() {
        return conteudo;
    }

    public PublicacaoFeed conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public PublicacaoFeed dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataPublicacao() {
        return dataPublicacao;
    }

    public PublicacaoFeed dataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusPublicacao getStatus() {
        return status;
    }

    public PublicacaoFeed status(StatusPublicacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusPublicacao status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public PublicacaoFeed feed(Feed feed) {
        this.feed = feed;
        return this;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Set<CategoriaPublicacao> getCategorias() {
        return categorias;
    }

    public PublicacaoFeed categorias(Set<CategoriaPublicacao> categoriaPublicacaos) {
        this.categorias = categoriaPublicacaos;
        return this;
    }

    public PublicacaoFeed addCategoria(CategoriaPublicacao categoriaPublicacao) {
        this.categorias.add(categoriaPublicacao);
        categoriaPublicacao.getPublicacaos().add(this);
        return this;
    }

    public PublicacaoFeed removeCategoria(CategoriaPublicacao categoriaPublicacao) {
        this.categorias.remove(categoriaPublicacao);
        categoriaPublicacao.getPublicacaos().remove(this);
        return this;
    }

    public void setCategorias(Set<CategoriaPublicacao> categoriaPublicacaos) {
        this.categorias = categoriaPublicacaos;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public PublicacaoFeed anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public PublicacaoFeed addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getPublicacaoFeeds().add(this);
        return this;
    }

    public PublicacaoFeed removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getPublicacaoFeeds().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicacaoFeed)) {
            return false;
        }
        return id != null && id.equals(((PublicacaoFeed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PublicacaoFeed{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", titulo='" + getTitulo() + "'" +
            ", autor='" + getAutor() + "'" +
            ", uri='" + getUri() + "'" +
            ", link='" + getLink() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
