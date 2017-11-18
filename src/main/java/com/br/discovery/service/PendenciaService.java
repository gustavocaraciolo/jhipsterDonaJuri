package com.br.discovery.service;

import com.br.discovery.service.dto.PendenciaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Pendencia.
 */
public interface PendenciaService {

    /**
     * Save a pendencia.
     *
     * @param pendenciaDTO the entity to save
     * @return the persisted entity
     */
    PendenciaDTO save(PendenciaDTO pendenciaDTO);

    /**
     *  Get all the pendencias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PendenciaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pendencia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PendenciaDTO findOne(Long id);

    /**
     *  Delete the "id" pendencia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
