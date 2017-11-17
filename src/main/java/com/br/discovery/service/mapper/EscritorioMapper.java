package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.EscritorioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Escritorio and its DTO EscritorioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EscritorioMapper extends EntityMapper<EscritorioDTO, Escritorio> {

    

    

    default Escritorio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Escritorio escritorio = new Escritorio();
        escritorio.setId(id);
        return escritorio;
    }
}
