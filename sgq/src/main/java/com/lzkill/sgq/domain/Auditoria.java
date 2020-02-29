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

import com.lzkill.sgq.domain.enumeration.ModalidadeAuditoria;

/**
 * A Auditoria.
 */
@Entity
@Table(name = "auditoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auditoria implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private ModalidadeAuditoria modalidade;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private Instant dataRegistro;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "auditor", length = 100, nullable = false)
    private String auditor;

    @OneToMany(mappedBy = "auditoria")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NaoConformidade> naoConformidades = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("auditorias")
    private Consultoria consultoria;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "auditoria_item_auditoria",
               joinColumns = @JoinColumn(name = "auditoria_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "item_auditoria_id", referencedColumnName = "id"))
    private Set<ItemAuditoria> itemAuditorias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "auditoria_anexo",
               joinColumns = @JoinColumn(name = "auditoria_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

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

    public Auditoria idUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
        return this;
    }

    public void setIdUsuarioRegistro(Integer idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public String getTitulo() {
        return titulo;
    }

    public Auditoria titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Auditoria descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ModalidadeAuditoria getModalidade() {
        return modalidade;
    }

    public Auditoria modalidade(ModalidadeAuditoria modalidade) {
        this.modalidade = modalidade;
        return this;
    }

    public void setModalidade(ModalidadeAuditoria modalidade) {
        this.modalidade = modalidade;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public Auditoria dataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public Auditoria dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public Auditoria dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public String getAuditor() {
        return auditor;
    }

    public Auditoria auditor(String auditor) {
        this.auditor = auditor;
        return this;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Set<NaoConformidade> getNaoConformidades() {
        return naoConformidades;
    }

    public Auditoria naoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
        return this;
    }

    public Auditoria addNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.add(naoConformidade);
        naoConformidade.setAuditoria(this);
        return this;
    }

    public Auditoria removeNaoConformidade(NaoConformidade naoConformidade) {
        this.naoConformidades.remove(naoConformidade);
        naoConformidade.setAuditoria(null);
        return this;
    }

    public void setNaoConformidades(Set<NaoConformidade> naoConformidades) {
        this.naoConformidades = naoConformidades;
    }

    public Consultoria getConsultoria() {
        return consultoria;
    }

    public Auditoria consultoria(Consultoria consultoria) {
        this.consultoria = consultoria;
        return this;
    }

    public void setConsultoria(Consultoria consultoria) {
        this.consultoria = consultoria;
    }

    public Set<ItemAuditoria> getItemAuditorias() {
        return itemAuditorias;
    }

    public Auditoria itemAuditorias(Set<ItemAuditoria> itemAuditorias) {
        this.itemAuditorias = itemAuditorias;
        return this;
    }

    public Auditoria addItemAuditoria(ItemAuditoria itemAuditoria) {
        this.itemAuditorias.add(itemAuditoria);
        itemAuditoria.getAuditorias().add(this);
        return this;
    }

    public Auditoria removeItemAuditoria(ItemAuditoria itemAuditoria) {
        this.itemAuditorias.remove(itemAuditoria);
        itemAuditoria.getAuditorias().remove(this);
        return this;
    }

    public void setItemAuditorias(Set<ItemAuditoria> itemAuditorias) {
        this.itemAuditorias = itemAuditorias;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public Auditoria anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public Auditoria addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getAuditorias().add(this);
        return this;
    }

    public Auditoria removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getAuditorias().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auditoria)) {
            return false;
        }
        return id != null && id.equals(((Auditoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
            "id=" + getId() +
            ", idUsuarioRegistro=" + getIdUsuarioRegistro() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", modalidade='" + getModalidade() + "'" +
            ", dataRegistro='" + getDataRegistro() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", auditor='" + getAuditor() + "'" +
            "}";
    }
}
