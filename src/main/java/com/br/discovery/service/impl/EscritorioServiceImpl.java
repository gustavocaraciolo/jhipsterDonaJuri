package com.br.discovery.service.impl;

import com.br.discovery.service.EscritorioService;
import com.br.discovery.domain.Escritorio;
import com.br.discovery.repository.EscritorioRepository;
import com.br.discovery.service.dto.EscritorioDTO;
import com.br.discovery.service.mapper.EscritorioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Escritorio.
 */
@Service
@Transactional
public class EscritorioServiceImpl implements EscritorioService{

    private final Logger log = LoggerFactory.getLogger(EscritorioServiceImpl.class);

    private final EscritorioRepository escritorioRepository;

    private final EscritorioMapper escritorioMapper;

    public EscritorioServiceImpl(EscritorioRepository escritorioRepository, EscritorioMapper escritorioMapper) {
        this.escritorioRepository = escritorioRepository;
        this.escritorioMapper = escritorioMapper;
    }

    /**
     * Save a escritorio.
     *
     * @param escritorioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EscritorioDTO save(EscritorioDTO escritorioDTO) {
        log.debug("Request to save Escritorio : {}", escritorioDTO);
        Escritorio escritorio = escritorioMapper.toEntity(escritorioDTO);
        escritorio = escritorioRepository.save(escritorio);
        return escritorioMapper.toDto(escritorio);
    }

    /**
     *  Get all the escritorios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EscritorioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Escritorios");
        return escritorioRepository.findAll(pageable)
            .map(escritorioMapper::toDto);
    }

    /**
     *  Get one escritorio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EscritorioDTO findOne(Long id) {
        log.debug("Request to get Escritorio : {}", id);
        Escritorio escritorio = escritorioRepository.findOne(id);
        return escritorioMapper.toDto(escritorio);
    }

    /**
     *  Delete the  escritorio by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Escritorio : {}", id);
        escritorioRepository.delete(id);
    }
}
