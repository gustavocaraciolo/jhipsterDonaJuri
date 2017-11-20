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
 * A Escritorio.
 */
@Entity
@Table(name = "escritorio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Escritorio extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Convite convite;

    @OneToMany(mappedBy = "escritorio")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExtra> userExtras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Escritorio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Escritorio telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public Escritorio email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Convite getConvite() {
        return convite;
    }

    public Escritorio convite(Convite convite) {
        this.convite = convite;
        return this;
    }

    public void setConvite(Convite convite) {
        this.convite = convite;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public Escritorio userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public Escritorio addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setEscritorio(this);
        return this;
    }

    public Escritorio removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setEscritorio(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
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
        Escritorio escritorio = (Escritorio) o;
        if (escritorio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escritorio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Escritorio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
