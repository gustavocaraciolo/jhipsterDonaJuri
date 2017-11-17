package com.br.discovery.domain;

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

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UserExtra advogadoCorrente;

    @ManyToOne(optional = false)
    @NotNull
    private UserExtra cliente;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "processo_advogado",
               joinColumns = @JoinColumn(name="processos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="advogados_id", referencedColumnName="id"))
    private Set<UserExtra> advogados = new HashSet<>();

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
