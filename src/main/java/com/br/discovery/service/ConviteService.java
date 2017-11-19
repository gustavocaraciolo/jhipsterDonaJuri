package com.br.discovery.service;

import com.br.discovery.service.dto.ConviteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Convite.
 */
public interface ConviteService {

    /**
     * Save a convite.
     *
     * @param conviteDTO the entity to save
     * @return the persisted entity
     */
    ConviteDTO save(ConviteDTO conviteDTO);

    /**
     *  Get all the convites.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConviteDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" convite.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConviteDTO findOne(Long id);

    /**
     *  Delete the "id" convite.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
