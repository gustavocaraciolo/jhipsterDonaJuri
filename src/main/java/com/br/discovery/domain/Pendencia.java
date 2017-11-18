package com.br.discovery.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.br.discovery.domain.enumeration.Status;

/**
 * A Pendencia.
 */
@Entity
@Table(name = "pendencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pendencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "providencia")
    private String providencia;

    @Lob
    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "data_inicial")
    private LocalDate dataInicial;

    @Column(name = "data_final")
    private LocalDate dataFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    private Processo processo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pendencia_advogado",
               joinColumns = @JoinColumn(name="pendencias_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="advogados_id", referencedColumnName="id"))
    private Set<UserExtra> advogados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvidencia() {
        return providencia;
    }

    public Pendencia providencia(String providencia) {
        this.providencia = providencia;
        return this;
    }

    public void setProvidencia(String providencia) {
        this.providencia = providencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Pendencia observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public Pendencia dataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
        return this;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public Pendencia dataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
        return this;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Status getStatus() {
        return status;
    }

    public Pendencia status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Pendencia processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Set<UserExtra> getAdvogados() {
        return advogados;
    }

    public Pendencia advogados(Set<UserExtra> userExtras) {
        this.advogados = userExtras;
        return this;
    }

    public Pendencia addAdvogado(UserExtra userExtra) {
        this.advogados.add(userExtra);
        userExtra.getPendenciaAdvogados().add(this);
        return this;
    }

    public Pendencia removeAdvogado(UserExtra userExtra) {
        this.advogados.remove(userExtra);
        userExtra.getPendenciaAdvogados().remove(this);
        return this;
    }

    public void setAdvogados(Set<UserExtra> userExtras) {
        this.advogados = userExtras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pendencia pendencia = (Pendencia) o;
        if (pendencia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pendencia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pendencia{" +
            "id=" + getId() +
            ", providencia='" + getProvidencia() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
