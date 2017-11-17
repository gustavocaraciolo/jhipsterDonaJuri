package com.br.discovery.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.br.discovery.domain.enumeration.Status;

/**
 * A DTO for the Processo entity.
 */
public class ProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private Status status;

    private String parteadversa;

    private Long advogadoCorrenteId;

    private Long clienteId;

    private Set<UserExtraDTO> advogados = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getParteadversa() {
        return parteadversa;
    }

    public void setParteadversa(String parteadversa) {
        this.parteadversa = parteadversa;
    }

    public Long getAdvogadoCorrenteId() {
        return advogadoCorrenteId;
    }

    public void setAdvogadoCorrenteId(Long userExtraId) {
        this.advogadoCorrenteId = userExtraId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long userExtraId) {
        this.clienteId = userExtraId;
    }

    public Set<UserExtraDTO> getAdvogados() {
        return advogados;
    }

    public void setAdvogados(Set<UserExtraDTO> userExtras) {
        this.advogados = userExtras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcessoDTO processoDTO = (ProcessoDTO) o;
        if(processoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcessoDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", status='" + getStatus() + "'" +
            ", parteadversa='" + getParteadversa() + "'" +
            "}";
    }
}
