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
 * Criteria class for the {@link com.lzkill.sgq.domain.Produto} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.ProdutoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produtos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdutoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter habilitado;

    private LongFilter anexoId;

    private LongFilter empresaId;

    public ProdutoCriteria(){
    }

    public ProdutoCriteria(ProdutoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.habilitado = other.habilitado == null ? null : other.habilitado.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public ProdutoCriteria copy() {
        return new ProdutoCriteria(this);
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

    public BooleanFilter getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(BooleanFilter habilitado) {
        this.habilitado = habilitado;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProdutoCriteria that = (ProdutoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(habilitado, that.habilitado) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        habilitado,
        anexoId,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "ProdutoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (habilitado != null ? "habilitado=" + habilitado + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
