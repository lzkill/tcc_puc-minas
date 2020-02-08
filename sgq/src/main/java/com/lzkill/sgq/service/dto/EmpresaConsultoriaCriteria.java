package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.EmpresaConsultoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.EmpresaConsultoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresa-consultorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaConsultoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter urlIntegracao;

    private StringFilter tokenAcesso;

    public EmpresaConsultoriaCriteria(){
    }

    public EmpresaConsultoriaCriteria(EmpresaConsultoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.urlIntegracao = other.urlIntegracao == null ? null : other.urlIntegracao.copy();
        this.tokenAcesso = other.tokenAcesso == null ? null : other.tokenAcesso.copy();
    }

    @Override
    public EmpresaConsultoriaCriteria copy() {
        return new EmpresaConsultoriaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getUrlIntegracao() {
        return urlIntegracao;
    }

    public void setUrlIntegracao(StringFilter urlIntegracao) {
        this.urlIntegracao = urlIntegracao;
    }

    public StringFilter getTokenAcesso() {
        return tokenAcesso;
    }

    public void setTokenAcesso(StringFilter tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmpresaConsultoriaCriteria that = (EmpresaConsultoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(urlIntegracao, that.urlIntegracao) &&
            Objects.equals(tokenAcesso, that.tokenAcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        urlIntegracao,
        tokenAcesso
        );
    }

    @Override
    public String toString() {
        return "EmpresaConsultoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (urlIntegracao != null ? "urlIntegracao=" + urlIntegracao + ", " : "") +
                (tokenAcesso != null ? "tokenAcesso=" + tokenAcesso + ", " : "") +
            "}";
    }

}
