package com.br.discovery.service;

import com.br.discovery.service.dto.UserExtraDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing UserExtra.
 */
public interface UserExtraService {

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save
     * @return the persisted entity
     */
    UserExtraDTO save(UserExtraDTO userExtraDTO);

    /**
     *  Get all the userExtras.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserExtraDTO> findAll(Pageable pageable);
    /**
     *  Get all the UserExtraDTO where ProcessoAdvogadoCorrente is null.
     *
     *  @return the list of entities
     */
    List<UserExtraDTO> findAllWhereProcessoAdvogadoCorrenteIsNull();

    /**
     *  Get the "id" userExtra.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserExtraDTO findOne(Long id);

    /**
     *  Delete the "id" userExtra.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
