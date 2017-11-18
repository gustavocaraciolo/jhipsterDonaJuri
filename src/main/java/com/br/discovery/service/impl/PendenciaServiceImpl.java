package com.br.discovery.service.impl;

import com.br.discovery.service.PendenciaService;
import com.br.discovery.domain.Pendencia;
import com.br.discovery.repository.PendenciaRepository;
import com.br.discovery.service.dto.PendenciaDTO;
import com.br.discovery.service.mapper.PendenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pendencia.
 */
@Service
@Transactional
public class PendenciaServiceImpl implements PendenciaService{

    private final Logger log = LoggerFactory.getLogger(PendenciaServiceImpl.class);

    private final PendenciaRepository pendenciaRepository;

    private final PendenciaMapper pendenciaMapper;

    public PendenciaServiceImpl(PendenciaRepository pendenciaRepository, PendenciaMapper pendenciaMapper) {
        this.pendenciaRepository = pendenciaRepository;
        this.pendenciaMapper = pendenciaMapper;
    }

    /**
     * Save a pendencia.
     *
     * @param pendenciaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PendenciaDTO save(PendenciaDTO pendenciaDTO) {
        log.debug("Request to save Pendencia : {}", pendenciaDTO);
        Pendencia pendencia = pendenciaMapper.toEntity(pendenciaDTO);
        pendencia = pendenciaRepository.save(pendencia);
        return pendenciaMapper.toDto(pendencia);
    }

    /**
     *  Get all the pendencias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PendenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pendencias");
        return pendenciaRepository.findAll(pageable)
            .map(pendenciaMapper::toDto);
    }

    /**
     *  Get one pendencia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PendenciaDTO findOne(Long id) {
        log.debug("Request to get Pendencia : {}", id);
        Pendencia pendencia = pendenciaRepository.findOneWithEagerRelationships(id);
        return pendenciaMapper.toDto(pendencia);
    }

    /**
     *  Delete the  pendencia by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pendencia : {}", id);
        pendenciaRepository.delete(id);
    }
}
