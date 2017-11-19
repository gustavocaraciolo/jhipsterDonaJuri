package com.br.discovery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Convite.
 */
@Entity
@Table(name = "convite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Convite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "data_envio")
    private ZonedDateTime dataEnvio;

    @Column(name = "data_aceitado")
    private ZonedDateTime dataAceitado;

    @Column(name = "data_rejeitado")
    private ZonedDateTime dataRejeitado;

    @OneToMany(mappedBy = "convite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Escritorio> escritorios = new HashSet<>();

    @OneToMany(mappedBy = "convite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExtra> advogados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Convite email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDataEnvio() {
        return dataEnvio;
    }

    public Convite dataEnvio(ZonedDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
        return this;
    }

    public void setDataEnvio(ZonedDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public ZonedDateTime getDataAceitado() {
        return dataAceitado;
    }

    public Convite dataAceitado(ZonedDateTime dataAceitado) {
        this.dataAceitado = dataAceitado;
        return this;
    }

    public void setDataAceitado(ZonedDateTime dataAceitado) {
        this.dataAceitado = dataAceitado;
    }

    public ZonedDateTime getDataRejeitado() {
        return dataRejeitado;
    }

    public Convite dataRejeitado(ZonedDateTime dataRejeitado) {
        this.dataRejeitado = dataRejeitado;
        return this;
    }

    public void setDataRejeitado(ZonedDateTime dataRejeitado) {
        this.dataRejeitado = dataRejeitado;
    }

    public Set<Escritorio> getEscritorios() {
        return escritorios;
    }

    public Convite escritorios(Set<Escritorio> escritorios) {
        this.escritorios = escritorios;
        return this;
    }

    public Convite addEscritorio(Escritorio escritorio) {
        this.escritorios.add(escritorio);
        escritorio.setConvite(this);
        return this;
    }

    public Convite removeEscritorio(Escritorio escritorio) {
        this.escritorios.remove(escritorio);
        escritorio.setConvite(null);
        return this;
    }

    public void setEscritorios(Set<Escritorio> escritorios) {
        this.escritorios = escritorios;
    }

    public Set<UserExtra> getAdvogados() {
        return advogados;
    }

    public Convite advogados(Set<UserExtra> userExtras) {
        this.advogados = userExtras;
        return this;
    }

    public Convite addAdvogado(UserExtra userExtra) {
        this.advogados.add(userExtra);
        userExtra.setConvite(this);
        return this;
    }

    public Convite removeAdvogado(UserExtra userExtra) {
        this.advogados.remove(userExtra);
        userExtra.setConvite(null);
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
        Convite convite = (Convite) o;
        if (convite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), convite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Convite{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", dataEnvio='" + getDataEnvio() + "'" +
            ", dataAceitado='" + getDataAceitado() + "'" +
            ", dataRejeitado='" + getDataRejeitado() + "'" +
            "}";
    }
}
