package com.br.discovery.service;

import com.br.discovery.service.dto.ProcessoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Processo.
 */
public interface ProcessoService {

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save
     * @return the persisted entity
     */
    ProcessoDTO save(ProcessoDTO processoDTO);

    /**
     *  Get all the processos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProcessoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" processo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProcessoDTO findOne(Long id);

    /**
     *  Delete the "id" processo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
