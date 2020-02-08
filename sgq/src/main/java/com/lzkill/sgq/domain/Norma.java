package com.lzkill.sgq.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Do trecho\n\n<i>\"O repositório de normas está disponível em nuvem como um serviço (SaaS)...\"</i>\n\nentende-se que a aplicação deve consumir uma API cuja interface é definida\npela empresa fornecedora de SaaS.
 */
@ApiModel(description = "Do trecho\n\n<i>\"O repositório de normas está disponível em nuvem como um serviço (SaaS)...\"</i>\n\nentende-se que a aplicação deve consumir uma API cuja interface é definida\npela empresa fornecedora de SaaS.")
@Entity
@Table(name = "norma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Norma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orgao")
    private String orgao;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "versao")
    private String versao;

    @Column(name = "numero_edicao")
    private Integer numeroEdicao;

    @Column(name = "data_edicao")
    private Instant dataEdicao;

    @Column(name = "data_inicio_validade")
    private Instant dataInicioValidade;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "norma_categoria",
               joinColumns = @JoinColumn(name = "norma_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<CategoriaNorma> categorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgao() {
        return orgao;
    }

    public Norma orgao(String orgao) {
        this.orgao = orgao;
        return this;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getTitulo() {
        return titulo;
    }

    public Norma titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Norma descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVersao() {
        return versao;
    }

    public Norma versao(String versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Integer getNumeroEdicao() {
        return numeroEdicao;
    }

    public Norma numeroEdicao(Integer numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
        return this;
    }

    public void setNumeroEdicao(Integer numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public Instant getDataEdicao() {
        return dataEdicao;
    }

    public Norma dataEdicao(Instant dataEdicao) {
        this.dataEdicao = dataEdicao;
        return this;
    }

    public void setDataEdicao(Instant dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public Instant getDataInicioValidade() {
        return dataInicioValidade;
    }

    public Norma dataInicioValidade(Instant dataInicioValidade) {
        this.dataInicioValidade = dataInicioValidade;
        return this;
    }

    public void setDataInicioValidade(Instant dataInicioValidade) {
        this.dataInicioValidade = dataInicioValidade;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public Norma anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Set<CategoriaNorma> getCategorias() {
        return categorias;
    }

    public Norma categorias(Set<CategoriaNorma> categoriaNormas) {
        this.categorias = categoriaNormas;
        return this;
    }

    public Norma addCategoria(CategoriaNorma categoriaNorma) {
        this.categorias.add(categoriaNorma);
        categoriaNorma.getNormas().add(this);
        return this;
    }

    public Norma removeCategoria(CategoriaNorma categoriaNorma) {
        this.categorias.remove(categoriaNorma);
        categoriaNorma.getNormas().remove(this);
        return this;
    }

    public void setCategorias(Set<CategoriaNorma> categoriaNormas) {
        this.categorias = categoriaNormas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Norma)) {
            return false;
        }
        return id != null && id.equals(((Norma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Norma{" +
            "id=" + getId() +
            ", orgao='" + getOrgao() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", versao='" + getVersao() + "'" +
            ", numeroEdicao=" + getNumeroEdicao() +
            ", dataEdicao='" + getDataEdicao() + "'" +
            ", dataInicioValidade='" + getDataInicioValidade() + "'" +
            "}";
    }
}
