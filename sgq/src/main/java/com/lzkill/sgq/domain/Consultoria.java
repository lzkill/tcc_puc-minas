package com.lzkill.sgq.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Do trecho\n\n<i>\"Os serviços prestados por esses órgãos devem gerar insumos para o\nplanejamento e controle das atividades da empresa, devendo ser compatibilizado\ncom o SGQ por meio de integrações providas por APIs de serviços.\"</i>\n\nentende-se que a prestadora de serviço deve disponibilizar alguns serviços\natravés de API cuja interface é definida pela empresa dona do SGQ.
 */
@ApiModel(description = "Do trecho\n\n<i>\"Os serviços prestados por esses órgãos devem gerar insumos para o\nplanejamento e controle das atividades da empresa, devendo ser compatibilizado\ncom o SGQ por meio de integrações providas por APIs de serviços.\"</i>\n\nentende-se que a prestadora de serviço deve disponibilizar alguns serviços\natravés de API cuja interface é definida pela empresa dona do SGQ.")
@Entity
@Table(name = "consultoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Consultoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "url_integracao", length = 150, nullable = false)
    private String urlIntegracao;

    /**
     * Token JWT
     */
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "Token JWT", required = true)
    @Column(name = "token_acesso", nullable = false)
    private String tokenAcesso;

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

    public String getNome() {
        return nome;
    }

    public Consultoria nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlIntegracao() {
        return urlIntegracao;
    }

    public Consultoria urlIntegracao(String urlIntegracao) {
        this.urlIntegracao = urlIntegracao;
        return this;
    }

    public void setUrlIntegracao(String urlIntegracao) {
        this.urlIntegracao = urlIntegracao;
    }

    public String getTokenAcesso() {
        return tokenAcesso;
    }

    public Consultoria tokenAcesso(String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
        return this;
    }

    public void setTokenAcesso(String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }

    public Boolean isHabilitado() {
        return habilitado;
    }

    public Consultoria habilitado(Boolean habilitado) {
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
        if (!(o instanceof Consultoria)) {
            return false;
        }
        return id != null && id.equals(((Consultoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consultoria{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", urlIntegracao='" + getUrlIntegracao() + "'" +
            ", tokenAcesso='" + getTokenAcesso() + "'" +
            ", habilitado='" + isHabilitado() + "'" +
            "}";
    }
}
