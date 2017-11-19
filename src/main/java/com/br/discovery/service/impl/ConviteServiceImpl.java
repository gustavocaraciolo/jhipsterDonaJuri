package com.br.discovery.service.impl;

import com.br.discovery.service.ConviteService;
import com.br.discovery.domain.Convite;
import com.br.discovery.repository.ConviteRepository;
import com.br.discovery.service.dto.ConviteDTO;
import com.br.discovery.service.mapper.ConviteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Convite.
 */
@Service
@Transactional
public class ConviteServiceImpl implements ConviteService{

    private final Logger log = LoggerFactory.getLogger(ConviteServiceImpl.class);

    private final ConviteRepository conviteRepository;

    private final ConviteMapper conviteMapper;

    public ConviteServiceImpl(ConviteRepository conviteRepository, ConviteMapper conviteMapper) {
        this.conviteRepository = conviteRepository;
        this.conviteMapper = conviteMapper;
    }

    /**
     * Save a convite.
     *
     * @param conviteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConviteDTO save(ConviteDTO conviteDTO) {
        log.debug("Request to save Convite : {}", conviteDTO);
        Convite convite = conviteMapper.toEntity(conviteDTO);
        convite = conviteRepository.save(convite);
        return conviteMapper.toDto(convite);
    }

    /**
     *  Get all the convites.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConviteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Convites");
        return conviteRepository.findAll(pageable)
            .map(conviteMapper::toDto);
    }

    /**
     *  Get one convite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConviteDTO findOne(Long id) {
        log.debug("Request to get Convite : {}", id);
        Convite convite = conviteRepository.findOne(id);
        return conviteMapper.toDto(convite);
    }

    /**
     *  Delete the  convite by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Convite : {}", id);
        conviteRepository.delete(id);
    }
}
