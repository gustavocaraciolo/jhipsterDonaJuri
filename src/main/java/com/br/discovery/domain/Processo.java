package com.br.discovery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.br.discovery.domain.enumeration.Status;

/**
 * A Processo.
 */
@Entity
@Table(name = "processo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "parteadversa")
    private String parteadversa;

    @ManyToOne(optional = false)
    @NotNull
    private UserExtra cliente;

    @ManyToOne(optional = false)
    @NotNull
    private UserExtra advogadoCorrente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "processo_advogado",
               joinColumns = @JoinColumn(name="processos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="advogados_id", referencedColumnName="id"))
    private Set<UserExtra> advogados = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "processo_anexo",
               joinColumns = @JoinColumn(name="processos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="anexos_id", referencedColumnName="id"))
    private Set<Anexo> anexos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pendencia> pendencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Processo numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Status getStatus() {
        return status;
    }

    public Processo status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getParteadversa() {
        return parteadversa;
    }

    public Processo parteadversa(String parteadversa) {
        this.parteadversa = parteadversa;
        return this;
    }

    public void setParteadversa(String parteadversa) {
        this.parteadversa = parteadversa;
    }

    public UserExtra getCliente() {
        return cliente;
    }

    public Processo cliente(UserExtra userExtra) {
        this.cliente = userExtra;
        return this;
    }

    public void setCliente(UserExtra userExtra) {
        this.cliente = userExtra;
    }

    public UserExtra getAdvogadoCorrente() {
        return advogadoCorrente;
    }

    public Processo advogadoCorrente(UserExtra userExtra) {
        this.advogadoCorrente = userExtra;
        return this;
    }

    public void setAdvogadoCorrente(UserExtra userExtra) {
        this.advogadoCorrente = userExtra;
    }

    public Set<UserExtra> getAdvogados() {
        return advogados;
    }

    public Processo advogados(Set<UserExtra> userExtras) {
        this.advogados = userExtras;
        return this;
    }

    public Processo addAdvogado(UserExtra userExtra) {
        this.advogados.add(userExtra);
        userExtra.getProcessoAdvogados().add(this);
        return this;
    }

    public Processo removeAdvogado(UserExtra userExtra) {
        this.advogados.remove(userExtra);
        userExtra.getProcessoAdvogados().remove(this);
        return this;
    }

    public void setAdvogados(Set<UserExtra> userExtras) {
        this.advogados = userExtras;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public Processo anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public Processo addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getProcessos().add(this);
        return this;
    }

    public Processo removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getProcessos().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Set<Pendencia> getPendencias() {
        return pendencias;
    }

    public Processo pendencias(Set<Pendencia> pendencias) {
        this.pendencias = pendencias;
        return this;
    }

    public Processo addPendencia(Pendencia pendencia) {
        this.pendencias.add(pendencia);
        pendencia.setProcesso(this);
        return this;
    }

    public Processo removePendencia(Pendencia pendencia) {
        this.pendencias.remove(pendencia);
        pendencia.setProcesso(null);
        return this;
    }

    public void setPendencias(Set<Pendencia> pendencias) {
        this.pendencias = pendencias;
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
        Processo processo = (Processo) o;
        if (processo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", status='" + getStatus() + "'" +
            ", parteadversa='" + getParteadversa() + "'" +
            "}";
    }
}
