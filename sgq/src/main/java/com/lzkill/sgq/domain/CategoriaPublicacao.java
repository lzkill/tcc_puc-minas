package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategoriaPublicacao.
 */
@Entity
@Table(name = "categoria_publicacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoriaPublicacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "categorias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<BoletimInformativo> boletims = new HashSet<>();

    @ManyToMany(mappedBy = "categorias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PublicacaoFeed> publicacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public CategoriaPublicacao titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaPublicacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<BoletimInformativo> getBoletims() {
        return boletims;
    }

    public CategoriaPublicacao boletims(Set<BoletimInformativo> boletimInformativos) {
        this.boletims = boletimInformativos;
        return this;
    }

    public CategoriaPublicacao addBoletim(BoletimInformativo boletimInformativo) {
        this.boletims.add(boletimInformativo);
        boletimInformativo.getCategorias().add(this);
        return this;
    }

    public CategoriaPublicacao removeBoletim(BoletimInformativo boletimInformativo) {
        this.boletims.remove(boletimInformativo);
        boletimInformativo.getCategorias().remove(this);
        return this;
    }

    public void setBoletims(Set<BoletimInformativo> boletimInformativos) {
        this.boletims = boletimInformativos;
    }

    public Set<PublicacaoFeed> getPublicacaos() {
        return publicacaos;
    }

    public CategoriaPublicacao publicacaos(Set<PublicacaoFeed> publicacaoFeeds) {
        this.publicacaos = publicacaoFeeds;
        return this;
    }

    public CategoriaPublicacao addPublicacao(PublicacaoFeed publicacaoFeed) {
        this.publicacaos.add(publicacaoFeed);
        publicacaoFeed.getCategorias().add(this);
        return this;
    }

    public CategoriaPublicacao removePublicacao(PublicacaoFeed publicacaoFeed) {
        this.publicacaos.remove(publicacaoFeed);
        publicacaoFeed.getCategorias().remove(this);
        return this;
    }

    public void setPublicacaos(Set<PublicacaoFeed> publicacaoFeeds) {
        this.publicacaos = publicacaoFeeds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaPublicacao)) {
            return false;
        }
        return id != null && id.equals(((CategoriaPublicacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaPublicacao{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
