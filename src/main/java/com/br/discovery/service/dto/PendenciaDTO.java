package com.br.discovery.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.br.discovery.domain.enumeration.Status;

/**
 * A DTO for the Pendencia entity.
 */
public class PendenciaDTO implements Serializable {

    private Long id;

    private String providencia;

    @Lob
    private String observacoes;

    private LocalDate dataInicial;

    private LocalDate dataFinal;

    private Status status;

    private Long processoId;

    private Set<UserExtraDTO> advogados = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvidencia() {
        return providencia;
    }

    public void setProvidencia(String providencia) {
        this.providencia = providencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
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

        PendenciaDTO pendenciaDTO = (PendenciaDTO) o;
        if(pendenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pendenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PendenciaDTO{" +
            "id=" + getId() +
            ", providencia='" + getProvidencia() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", dataInicial='" + getDataInicial() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
