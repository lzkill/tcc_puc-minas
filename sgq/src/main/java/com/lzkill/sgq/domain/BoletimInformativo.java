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
 * A BoletimInformativo.
 */
@Entity
@Table(name = "boletim_informativo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BoletimInformativo implements Serializable {

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

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_publicacao")
    private Instant dataPublicacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPublicacao status;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("boletimInformativos")
    private PublicoAlvo publicoAlvo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "boletim_informativo_categoria",
               joinColumns = @JoinColumn(name = "boletim_informativo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<CategoriaPublicacao> categorias = new HashSet<>();

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

    public BoletimInformativo idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public String getTitulo() {
        return titulo;
    }

    public BoletimInformativo titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BoletimInformativo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public BoletimInformativo dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataPublicacao() {
        return dataPublicacao;
    }

    public BoletimInformativo dataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusPublicacao getStatus() {
        return status;
    }

    public BoletimInformativo status(StatusPublicacao status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusPublicacao status) {
        this.status = status;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public BoletimInformativo anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public PublicoAlvo getPublicoAlvo() {
        return publicoAlvo;
    }

    public BoletimInformativo publicoAlvo(PublicoAlvo publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
        return this;
    }

    public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public Set<CategoriaPublicacao> getCategorias() {
        return categorias;
    }

    public BoletimInformativo categorias(Set<CategoriaPublicacao> categoriaPublicacaos) {
        this.categorias = categoriaPublicacaos;
        return this;
    }

    public BoletimInformativo addCategoria(CategoriaPublicacao categoriaPublicacao) {
        this.categorias.add(categoriaPublicacao);
        categoriaPublicacao.getBoletims().add(this);
        return this;
    }

    public BoletimInformativo removeCategoria(CategoriaPublicacao categoriaPublicacao) {
        this.categorias.remove(categoriaPublicacao);
        categoriaPublicacao.getBoletims().remove(this);
        return this;
    }

    public void setCategorias(Set<CategoriaPublicacao> categoriaPublicacaos) {
        this.categorias = categoriaPublicacaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoletimInformativo)) {
            return false;
        }
        return id != null && id.equals(((BoletimInformativo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BoletimInformativo{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
