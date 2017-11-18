package com.br.discovery.service;

import com.br.discovery.service.dto.EscritorioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Escritorio.
 */
public interface EscritorioService {

    /**
     * Save a escritorio.
     *
     * @param escritorioDTO the entity to save
     * @return the persisted entity
     */
    EscritorioDTO save(EscritorioDTO escritorioDTO);

    /**
     *  Get all the escritorios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EscritorioDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" escritorio.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EscritorioDTO findOne(Long id);

    /**
     *  Delete the "id" escritorio.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
