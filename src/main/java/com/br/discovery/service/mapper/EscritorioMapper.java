package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.EscritorioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Escritorio and its DTO EscritorioDTO.
 */
@Mapper(componentModel = "spring", uses = {ConviteMapper.class})
public interface EscritorioMapper extends EntityMapper<EscritorioDTO, Escritorio> {

    @Mapping(source = "convite.id", target = "conviteId")
    EscritorioDTO toDto(Escritorio escritorio); 

    @Mapping(source = "conviteId", target = "convite")
    @Mapping(target = "userExtras", ignore = true)
    Escritorio toEntity(EscritorioDTO escritorioDTO);

    default Escritorio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Escritorio escritorio = new Escritorio();
        escritorio.setId(id);
        return escritorio;
    }
}
