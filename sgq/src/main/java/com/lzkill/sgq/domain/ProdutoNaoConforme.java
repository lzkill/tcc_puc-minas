package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.lzkill.sgq.domain.enumeration.StatusSGQ;

/**
 * Representa os produtos produzidos fora dos parâmetros de qualidade estabelecidos
 */
@ApiModel(description = "Representa os produtos produzidos fora dos parâmetros de qualidade estabelecidos")
@Entity
@Table(name = "produto_nao_conforme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProdutoNaoConforme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Column(name = "id_usuario_responsavel", nullable = false)
    private Integer idUsuarioResponsavel;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    /**
     * Análise de eficácia
     */
    @ApiModelProperty(value = "Análise de eficácia")
    @Lob
    @Column(name = "analise_final")
    private String analiseFinal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_sgq", nullable = false)
    private StatusSGQ statusSGQ;

    /**
     * PNCs são tratados com ações únicas e pontuais
     */
    @ApiModelProperty(value = "PNCs são tratados com ações únicas e pontuais")
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private AcaoSGQ acao;

    /**
     * Quando o problema identificado é sistêmico deve-se abrir uma NC
     */
    @ApiModelProperty(value = "Quando o problema identificado é sistêmico deve-se abrir uma NC")
    @OneToOne
    @JoinColumn(unique = true)
    private NaoConformidade naoConformidade;

    @OneToOne
    @JoinColumn(unique = true)
    private Anexo anexo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produtoNaoConformes")
    private Produto produto;

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

    public ProdutoNaoConforme idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public Integer getIdUsuarioResponsavel() {
        return idUsuarioResponsavel;
    }

    public ProdutoNaoConforme idUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
        return this;
    }

    public void setIdUsuarioResponsavel(Integer idUsuarioResponsavel) {
        this.idUsuarioResponsavel = idUsuarioResponsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProdutoNaoConforme titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public ProdutoNaoConforme descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public ProdutoNaoConforme dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getAnaliseFinal() {
        return analiseFinal;
    }

    public ProdutoNaoConforme analiseFinal(String analiseFinal) {
        this.analiseFinal = analiseFinal;
        return this;
    }

    public void setAnaliseFinal(String analiseFinal) {
        this.analiseFinal = analiseFinal;
    }

    public StatusSGQ getStatusSGQ() {
        return statusSGQ;
    }

    public ProdutoNaoConforme statusSGQ(StatusSGQ statusSGQ) {
        this.statusSGQ = statusSGQ;
        return this;
    }

    public void setStatusSGQ(StatusSGQ statusSGQ) {
        this.statusSGQ = statusSGQ;
    }

    public AcaoSGQ getAcao() {
        return acao;
    }

    public ProdutoNaoConforme acao(AcaoSGQ acaoSGQ) {
        this.acao = acaoSGQ;
        return this;
    }

    public void setAcao(AcaoSGQ acaoSGQ) {
        this.acao = acaoSGQ;
    }

    public NaoConformidade getNaoConformidade() {
        return naoConformidade;
    }

    public ProdutoNaoConforme naoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
        return this;
    }

    public void setNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidade = naoConformidade;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public ProdutoNaoConforme anexo(Anexo anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoNaoConforme produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoNaoConforme)) {
            return false;
        }
        return id != null && id.equals(((ProdutoNaoConforme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProdutoNaoConforme{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", idUsuarioResponsavel=" + getIdUsuarioResponsavel() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", analiseFinal='" + getAnaliseFinal() + "'" +
            ", statusSGQ='" + getStatusSGQ() + "'" +
            "}";
    }
}
