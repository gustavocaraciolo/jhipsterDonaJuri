package com.br.discovery.service;

import com.br.discovery.service.dto.AnexoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Anexo.
 */
public interface AnexoService {

    /**
     * Save a anexo.
     *
     * @param anexoDTO the entity to save
     * @return the persisted entity
     */
    AnexoDTO save(AnexoDTO anexoDTO);

    /**
     *  Get all the anexos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AnexoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" anexo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AnexoDTO findOne(Long id);

    /**
     *  Delete the "id" anexo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
