package com.br.discovery.service.impl;

import com.br.discovery.service.UserExtraService;
import com.br.discovery.domain.UserExtra;
import com.br.discovery.repository.UserExtraRepository;
import com.br.discovery.service.dto.UserExtraDTO;
import com.br.discovery.service.mapper.UserExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserExtraServiceImpl implements UserExtraService{

    private final Logger log = LoggerFactory.getLogger(UserExtraServiceImpl.class);

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    public UserExtraServiceImpl(UserExtraRepository userExtraRepository, UserExtraMapper userExtraMapper) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraMapper = userExtraMapper;
    }

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserExtraDTO save(UserExtraDTO userExtraDTO) {
        log.debug("Request to save UserExtra : {}", userExtraDTO);
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        return userExtraMapper.toDto(userExtra);
    }

    /**
     *  Get all the userExtras.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserExtraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll(pageable)
            .map(userExtraMapper::toDto);
    }


    /**
     *  get all the userExtras where ProcessoAdvogadoCorrente is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserExtraDTO> findAllWhereProcessoAdvogadoCorrenteIsNull() {
        log.debug("Request to get all userExtras where ProcessoAdvogadoCorrente is null");
        return StreamSupport
            .stream(userExtraRepository.findAll().spliterator(), false)
            .filter(userExtra -> userExtra.getProcessoAdvogadoCorrente() == null)
            .map(userExtraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the userExtras where Escritorio is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserExtraDTO> findAllWhereEscritorioIsNull() {
        log.debug("Request to get all userExtras where Escritorio is null");
        return StreamSupport
            .stream(userExtraRepository.findAll().spliterator(), false)
            .filter(userExtra -> userExtra.getEscritorio() == null)
            .map(userExtraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one userExtra by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserExtraDTO findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findOne(id);
        return userExtraMapper.toDto(userExtra);
    }

    /**
     *  Delete the  userExtra by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.delete(id);
    }
}
