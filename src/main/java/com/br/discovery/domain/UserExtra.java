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

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    private Escritorio escritorio;

    @ManyToOne
    private Convite convite;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processoClientes = new HashSet<>();

    @OneToMany(mappedBy = "advogadoCorrente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processoAdvogadoCorrentes = new HashSet<>();

    @ManyToMany(mappedBy = "advogados")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processoAdvogados = new HashSet<>();

    @ManyToMany(mappedBy = "advogados")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pendencia> pendenciaAdvogados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Escritorio getEscritorio() {
        return escritorio;
    }

    public UserExtra escritorio(Escritorio escritorio) {
        this.escritorio = escritorio;
        return this;
    }

    public void setEscritorio(Escritorio escritorio) {
        this.escritorio = escritorio;
    }

    public Convite getConvite() {
        return convite;
    }

    public UserExtra convite(Convite convite) {
        this.convite = convite;
        return this;
    }

    public void setConvite(Convite convite) {
        this.convite = convite;
    }

    public Set<Processo> getProcessoClientes() {
        return processoClientes;
    }

    public UserExtra processoClientes(Set<Processo> processos) {
        this.processoClientes = processos;
        return this;
    }

    public UserExtra addProcessoCliente(Processo processo) {
        this.processoClientes.add(processo);
        processo.setCliente(this);
        return this;
    }

    public UserExtra removeProcessoCliente(Processo processo) {
        this.processoClientes.remove(processo);
        processo.setCliente(null);
        return this;
    }

    public void setProcessoClientes(Set<Processo> processos) {
        this.processoClientes = processos;
    }

    public Set<Processo> getProcessoAdvogadoCorrentes() {
        return processoAdvogadoCorrentes;
    }

    public UserExtra processoAdvogadoCorrentes(Set<Processo> processos) {
        this.processoAdvogadoCorrentes = processos;
        return this;
    }

    public UserExtra addProcessoAdvogadoCorrente(Processo processo) {
        this.processoAdvogadoCorrentes.add(processo);
        processo.setAdvogadoCorrente(this);
        return this;
    }

    public UserExtra removeProcessoAdvogadoCorrente(Processo processo) {
        this.processoAdvogadoCorrentes.remove(processo);
        processo.setAdvogadoCorrente(null);
        return this;
    }

    public void setProcessoAdvogadoCorrentes(Set<Processo> processos) {
        this.processoAdvogadoCorrentes = processos;
    }

    public Set<Processo> getProcessoAdvogados() {
        return processoAdvogados;
    }

    public UserExtra processoAdvogados(Set<Processo> processos) {
        this.processoAdvogados = processos;
        return this;
    }

    public UserExtra addProcessoAdvogado(Processo processo) {
        this.processoAdvogados.add(processo);
        processo.getAdvogados().add(this);
        return this;
    }

    public UserExtra removeProcessoAdvogado(Processo processo) {
        this.processoAdvogados.remove(processo);
        processo.getAdvogados().remove(this);
        return this;
    }

    public void setProcessoAdvogados(Set<Processo> processos) {
        this.processoAdvogados = processos;
    }

    public Set<Pendencia> getPendenciaAdvogados() {
        return pendenciaAdvogados;
    }

    public UserExtra pendenciaAdvogados(Set<Pendencia> pendencias) {
        this.pendenciaAdvogados = pendencias;
        return this;
    }

    public UserExtra addPendenciaAdvogado(Pendencia pendencia) {
        this.pendenciaAdvogados.add(pendencia);
        pendencia.getAdvogados().add(this);
        return this;
    }

    public UserExtra removePendenciaAdvogado(Pendencia pendencia) {
        this.pendenciaAdvogados.remove(pendencia);
        pendencia.getAdvogados().remove(this);
        return this;
    }

    public void setPendenciaAdvogados(Set<Pendencia> pendencias) {
        this.pendenciaAdvogados = pendencias;
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
        UserExtra userExtra = (UserExtra) o;
        if (userExtra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            "}";
    }
}
