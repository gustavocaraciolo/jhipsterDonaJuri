package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.ConviteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Convite and its DTO ConviteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConviteMapper extends EntityMapper<ConviteDTO, Convite> {

    

    @Mapping(target = "escritorios", ignore = true)
    @Mapping(target = "advogados", ignore = true)
    Convite toEntity(ConviteDTO conviteDTO);

    default Convite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Convite convite = new Convite();
        convite.setId(id);
        return convite;
    }
}
