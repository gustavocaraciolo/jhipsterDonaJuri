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

    @OneToOne(mappedBy = "advogadoCorrente")
    @JsonIgnore
    private Processo processoAdvogadoCorrente;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processoClientes = new HashSet<>();

    @ManyToMany(mappedBy = "advogados")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processoAdvogados = new HashSet<>();

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

    public Processo getProcessoAdvogadoCorrente() {
        return processoAdvogadoCorrente;
    }

    public UserExtra processoAdvogadoCorrente(Processo processo) {
        this.processoAdvogadoCorrente = processo;
        return this;
    }

    public void setProcessoAdvogadoCorrente(Processo processo) {
        this.processoAdvogadoCorrente = processo;
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
