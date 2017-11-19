package com.br.discovery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anexo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToMany(mappedBy = "anexos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processos = new HashSet<>();

    @ManyToMany(mappedBy = "anexos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pendencia> pendencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public Anexo file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Anexo fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Anexo processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Anexo addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getAnexos().add(this);
        return this;
    }

    public Anexo removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getAnexos().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        this.processos = processos;
    }

    public Set<Pendencia> getPendencias() {
        return pendencias;
    }

    public Anexo pendencias(Set<Pendencia> pendencias) {
        this.pendencias = pendencias;
        return this;
    }

    public Anexo addPendencia(Pendencia pendencia) {
        this.pendencias.add(pendencia);
        pendencia.getAnexos().add(this);
        return this;
    }

    public Anexo removePendencia(Pendencia pendencia) {
        this.pendencias.remove(pendencia);
        pendencia.getAnexos().remove(this);
        return this;
    }

    public void setPendencias(Set<Pendencia> pendencias) {
        this.pendencias = pendencias;
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
        Anexo anexo = (Anexo) o;
        if (anexo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anexo{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + fileContentType + "'" +
            "}";
    }
}
