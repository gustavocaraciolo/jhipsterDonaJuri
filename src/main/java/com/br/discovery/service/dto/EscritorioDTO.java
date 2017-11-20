package com.br.discovery.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Escritorio entity.
 */
public class EscritorioDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String telefone;

    private String email;

    private Long conviteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getConviteId() {
        return conviteId;
    }

    public void setConviteId(Long conviteId) {
        this.conviteId = conviteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EscritorioDTO escritorioDTO = (EscritorioDTO) o;
        if(escritorioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escritorioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EscritorioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
