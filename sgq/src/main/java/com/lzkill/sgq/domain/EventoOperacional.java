package com.lzkill.sgq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import com.lzkill.sgq.domain.enumeration.TipoEventoOperacional;

/**
 * A EventoOperacional.
 */
@Entity
@Table(name = "evento_operacional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventoOperacional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_usuario_registro", nullable = false)
    private Integer idUsuarioRegistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoEventoOperacional tipo;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "data_evento", nullable = false)
    private Instant dataEvento;

    @Column(name = "duracao")
    private Duration duracao;

    @NotNull
    @Column(name = "houve_parada_producao", nullable = false)
    private Boolean houveParadaProducao;

    @OneToMany(mappedBy = "eventoOperacional")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo> anexos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("eventoOperacionals")
    private Processo processo;

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

    public EventoOperacional idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public TipoEventoOperacional getTipo() {
        return tipo;
    }

    public EventoOperacional tipo(TipoEventoOperacional tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoEventoOperacional tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public EventoOperacional titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public EventoOperacional descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataEvento() {
        return dataEvento;
    }

    public EventoOperacional dataEvento(Instant dataEvento) {
        this.dataEvento = dataEvento;
        return this;
    }

    public void setDataEvento(Instant dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public EventoOperacional duracao(Duration duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public Boolean isHouveParadaProducao() {
        return houveParadaProducao;
    }

    public EventoOperacional houveParadaProducao(Boolean houveParadaProducao) {
        this.houveParadaProducao = houveParadaProducao;
        return this;
    }

    public void setHouveParadaProducao(Boolean houveParadaProducao) {
        this.houveParadaProducao = houveParadaProducao;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public EventoOperacional anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public EventoOperacional addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setEventoOperacional(this);
        return this;
    }

    public EventoOperacional removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setEventoOperacional(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Processo getProcesso() {
        return processo;
    }

    public EventoOperacional processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventoOperacional)) {
            return false;
        }
        return id != null && id.equals(((EventoOperacional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventoOperacional{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", tipo='" + getTipo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataEvento='" + getDataEvento() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", houveParadaProducao='" + isHouveParadaProducao() + "'" +
            "}";
    }
}
