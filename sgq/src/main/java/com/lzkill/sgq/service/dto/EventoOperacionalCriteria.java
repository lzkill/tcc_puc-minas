package com.lzkill.sgq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lzkill.sgq.domain.enumeration.TipoEventoOperacional;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.DurationFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lzkill.sgq.domain.EventoOperacional} entity. This class is used
 * in {@link com.lzkill.sgq.web.rest.EventoOperacionalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evento-operacionals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventoOperacionalCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoEventoOperacional
     */
    public static class TipoEventoOperacionalFilter extends Filter<TipoEventoOperacional> {

        public TipoEventoOperacionalFilter() {
        }

        public TipoEventoOperacionalFilter(TipoEventoOperacionalFilter filter) {
            super(filter);
        }

        @Override
        public TipoEventoOperacionalFilter copy() {
            return new TipoEventoOperacionalFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idUsuarioRegistro;

    private TipoEventoOperacionalFilter tipo;

    private StringFilter titulo;

    private InstantFilter dataEvento;

    private DurationFilter duracao;

    private BooleanFilter houveParadaProducao;

    private LongFilter anexoId;

    private LongFilter processoId;

    public EventoOperacionalCriteria(){
    }

    public EventoOperacionalCriteria(EventoOperacionalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.idUsuarioRegistro = other.idUsuarioRegistro == null ? null : other.idUsuarioRegistro.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.dataEvento = other.dataEvento == null ? null : other.dataEvento.copy();
        this.duracao = other.duracao == null ? null : other.duracao.copy();
        this.houveParadaProducao = other.houveParadaProducao == null ? null : other.houveParadaProducao.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public EventoOperacionalCriteria copy() {
        return new EventoOperacionalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(IntegerFilter idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public TipoEventoOperacionalFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoEventoOperacionalFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public InstantFilter getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(InstantFilter dataEvento) {
        this.dataEvento = dataEvento;
    }

    public DurationFilter getDuracao() {
        return duracao;
    }

    public void setDuracao(DurationFilter duracao) {
        this.duracao = duracao;
    }

    public BooleanFilter getHouveParadaProducao() {
        return houveParadaProducao;
    }

    public void setHouveParadaProducao(BooleanFilter houveParadaProducao) {
        this.houveParadaProducao = houveParadaProducao;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventoOperacionalCriteria that = (EventoOperacionalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idUsuarioRegistro, that.idUsuarioRegistro) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(dataEvento, that.dataEvento) &&
            Objects.equals(duracao, that.duracao) &&
            Objects.equals(houveParadaProducao, that.houveParadaProducao) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idUsuarioRegistro,
        tipo,
        titulo,
        dataEvento,
        duracao,
        houveParadaProducao,
        anexoId,
        processoId
        );
    }

    @Override
    public String toString() {
        return "EventoOperacionalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idUsuarioRegistro != null ? "idUsuarioRegistro=" + idUsuarioRegistro + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (dataEvento != null ? "dataEvento=" + dataEvento + ", " : "") +
                (duracao != null ? "duracao=" + duracao + ", " : "") +
                (houveParadaProducao != null ? "houveParadaProducao=" + houveParadaProducao + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
