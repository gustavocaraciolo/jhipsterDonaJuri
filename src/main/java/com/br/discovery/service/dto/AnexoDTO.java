package com.br.discovery.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Anexo entity.
 */
public class AnexoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] file;
    private String fileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnexoDTO anexoDTO = (AnexoDTO) o;
        if(anexoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnexoDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            "}";
    }
}
