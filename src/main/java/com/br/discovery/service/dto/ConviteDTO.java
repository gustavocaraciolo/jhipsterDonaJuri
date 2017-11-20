package com.br.discovery.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Convite entity.
 */
public class ConviteDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    private ZonedDateTime dataEnvio;

    private ZonedDateTime dataAceitado;

    private ZonedDateTime dataRejeitado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(ZonedDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public ZonedDateTime getDataAceitado() {
        return dataAceitado;
    }

    public void setDataAceitado(ZonedDateTime dataAceitado) {
        this.dataAceitado = dataAceitado;
    }

    public ZonedDateTime getDataRejeitado() {
        return dataRejeitado;
    }

    public void setDataRejeitado(ZonedDateTime dataRejeitado) {
        this.dataRejeitado = dataRejeitado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConviteDTO conviteDTO = (ConviteDTO) o;
        if(conviteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conviteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConviteDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", dataEnvio='" + getDataEnvio() + "'" +
            ", dataAceitado='" + getDataAceitado() + "'" +
            ", dataRejeitado='" + getDataRejeitado() + "'" +
            "}";
    }
}
