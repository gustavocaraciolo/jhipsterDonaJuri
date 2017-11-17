package com.br.discovery.service.impl;

import com.br.discovery.service.ProcessoService;
import com.br.discovery.domain.Processo;
import com.br.discovery.repository.ProcessoRepository;
import com.br.discovery.service.dto.ProcessoDTO;
import com.br.discovery.service.mapper.ProcessoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Processo.
 */
@Service
@Transactional
public class ProcessoServiceImpl implements ProcessoService{

    private final Logger log = LoggerFactory.getLogger(ProcessoServiceImpl.class);

    private final ProcessoRepository processoRepository;

    private final ProcessoMapper processoMapper;

    public ProcessoServiceImpl(ProcessoRepository processoRepository, ProcessoMapper processoMapper) {
        this.processoRepository = processoRepository;
        this.processoMapper = processoMapper;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.toEntity(processoDTO);
        processo = processoRepository.save(processo);
        return processoMapper.toDto(processo);
    }

    /**
     *  Get all the processos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable)
            .map(processoMapper::toDto);
    }

    /**
     *  Get one processo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProcessoDTO findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        Processo processo = processoRepository.findOneWithEagerRelationships(id);
        return processoMapper.toDto(processo);
    }

    /**
     *  Delete the  processo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.delete(id);
    }
}
