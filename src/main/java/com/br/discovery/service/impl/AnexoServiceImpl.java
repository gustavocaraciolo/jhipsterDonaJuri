package com.br.discovery.service.impl;

import com.br.discovery.service.AnexoService;
import com.br.discovery.domain.Anexo;
import com.br.discovery.repository.AnexoRepository;
import com.br.discovery.service.dto.AnexoDTO;
import com.br.discovery.service.mapper.AnexoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Anexo.
 */
@Service
@Transactional
public class AnexoServiceImpl implements AnexoService{

    private final Logger log = LoggerFactory.getLogger(AnexoServiceImpl.class);

    private final AnexoRepository anexoRepository;

    private final AnexoMapper anexoMapper;

    public AnexoServiceImpl(AnexoRepository anexoRepository, AnexoMapper anexoMapper) {
        this.anexoRepository = anexoRepository;
        this.anexoMapper = anexoMapper;
    }

    /**
     * Save a anexo.
     *
     * @param anexoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnexoDTO save(AnexoDTO anexoDTO) {
        log.debug("Request to save Anexo : {}", anexoDTO);
        Anexo anexo = anexoMapper.toEntity(anexoDTO);
        anexo = anexoRepository.save(anexo);
        return anexoMapper.toDto(anexo);
    }

    /**
     *  Get all the anexos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnexoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anexos");
        return anexoRepository.findAll(pageable)
            .map(anexoMapper::toDto);
    }

    /**
     *  Get one anexo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnexoDTO findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        Anexo anexo = anexoRepository.findOne(id);
        return anexoMapper.toDto(anexo);
    }

    /**
     *  Delete the  anexo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.delete(id);
    }
}
