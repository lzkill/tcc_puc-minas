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

/**
 * A CampanhaRecall.
 */
@Entity
@Table(name = "campanha_recall")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CampanhaRecall implements Serializable {

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

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @Lob
    @Column(name = "resultado")
    private String resultado;

    @OneToMany(mappedBy = "campanhaRecall")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo> anexos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("campanhaRecalls")
    private Produto produto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("campanhaRecalls")
    private Setor setorResponsavel;

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

    public CampanhaRecall idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public String getTitulo() {
        return titulo;
    }

    public CampanhaRecall titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public CampanhaRecall descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public CampanhaRecall dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public CampanhaRecall dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public CampanhaRecall dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public String getResultado() {
        return resultado;
    }

    public CampanhaRecall resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public CampanhaRecall anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public CampanhaRecall addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setCampanhaRecall(this);
        return this;
    }

    public CampanhaRecall removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setCampanhaRecall(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Produto getProduto() {
        return produto;
    }

    public CampanhaRecall produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Setor getSetorResponsavel() {
        return setorResponsavel;
    }

    public CampanhaRecall setorResponsavel(Setor setor) {
        this.setorResponsavel = setor;
        return this;
    }

    public void setSetorResponsavel(Setor setor) {
        this.setorResponsavel = setor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampanhaRecall)) {
            return false;
        }
        return id != null && id.equals(((CampanhaRecall) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampanhaRecall{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", resultado='" + getResultado() + "'" +
            "}";
    }
}
