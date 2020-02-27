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
 * Criteria class for the {@link com.lzkill.sgq.domain.Consultoria} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ConsultoriaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consultorias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsultoriaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter urlIntegracao;

    private StringFilter tokenAcesso;

    private BooleanFilter habilitado;

    public ConsultoriaCriteria(){
    }

    public ConsultoriaCriteria(ConsultoriaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.urlIntegracao = other.urlIntegracao == null ? null : other.urlIntegracao.copy();
        this.tokenAcesso = other.tokenAcesso == null ? null : other.tokenAcesso.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
    }

    @Override
    public ConsultoriaCriteria copy() {
        return new ConsultoriaCriteria(this);
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

    public BooleanFilter getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(BooleanFilter habilitado) {
        this.habilitado = habilitado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsultoriaCriteria that = (ConsultoriaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(urlIntegracao, that.urlIntegracao) &&
            Objects.equals(tokenAcesso, that.tokenAcesso) &&
            Objects.equals(habilitado, that.habilitado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        urlIntegracao,
        tokenAcesso,
        habilitado
        );
    }

    @Override
    public String toString() {
        return "ConsultoriaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (urlIntegracao != null ? "urlIntegracao=" + urlIntegracao + ", " : "") +
                (tokenAcesso != null ? "tokenAcesso=" + tokenAcesso + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
            "}";
    }

}
