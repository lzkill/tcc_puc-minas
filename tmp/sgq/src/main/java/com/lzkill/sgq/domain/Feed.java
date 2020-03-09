package com.lzkill.sgq.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.lzkill.sgq.domain.enumeration.TipoFeed;

/**
 * A Feed.
 */
@Entity
@Table(name = "feed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoFeed tipo;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "uri", length = 150, nullable = false, unique = true)
    private String uri;

    @Size(min = 1, max = 150)
    @Column(name = "link", length = 150)
    private String link;

    @Size(min = 1, max = 150)
    @Column(name = "url_imagem", length = 150)
    private String urlImagem;

    @Size(min = 1, max = 150)
    @Column(name = "titulo_imagem", length = 150)
    private String tituloImagem;

    @Min(value = 32)
    @Max(value = 1024)
    @Column(name = "altura_imagem")
    private Integer alturaImagem;

    @Min(value = 32)
    @Max(value = 1024)
    @Column(name = "largura_imagem")
    private Integer larguraImagem;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @NotNull
    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

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

    public Feed idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public TipoFeed getTipo() {
        return tipo;
    }

    public Feed tipo(TipoFeed tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoFeed tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Feed titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Feed descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUri() {
        return uri;
    }

    public Feed uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLink() {
        return link;
    }

    public Feed link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public Feed urlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
        return this;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTituloImagem() {
        return tituloImagem;
    }

    public Feed tituloImagem(String tituloImagem) {
        this.tituloImagem = tituloImagem;
        return this;
    }

    public void setTituloImagem(String tituloImagem) {
        this.tituloImagem = tituloImagem;
    }

    public Integer getAlturaImagem() {
        return alturaImagem;
    }

    public Feed alturaImagem(Integer alturaImagem) {
        this.alturaImagem = alturaImagem;
        return this;
    }

    public void setAlturaImagem(Integer alturaImagem) {
        this.alturaImagem = alturaImagem;
    }

    public Integer getLarguraImagem() {
        return larguraImagem;
    }

    public Feed larguraImagem(Integer larguraImagem) {
        this.larguraImagem = larguraImagem;
        return this;
    }

    public void setLarguraImagem(Integer larguraImagem) {
        this.larguraImagem = larguraImagem;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public Feed dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Boolean isHabilitado() {
        return habilitado;
    }

    public Feed habilitado(Boolean habilitado) {
        this.habilitado = habilitado;
        return this;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feed)) {
            return false;
        }
        return id != null && id.equals(((Feed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Feed{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", tipo='" + getTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", uri='" + getUri() + "'" +
            ", link='" + getLink() + "'" +
            ", urlImagem='" + getUrlImagem() + "'" +
            ", tituloImagem='" + getTituloImagem() + "'" +
            ", alturaImagem=" + getAlturaImagem() +
            ", larguraImagem=" + getLarguraImagem() +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", habilitado='" + isHabilitado() + "'" +
            "}";
    }
}
