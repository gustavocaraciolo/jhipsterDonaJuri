package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.AnexoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Anexo and its DTO AnexoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {

    

    @Mapping(target = "processos", ignore = true)
    @Mapping(target = "pendencias", ignore = true)
    Anexo toEntity(AnexoDTO anexoDTO);

    default Anexo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anexo anexo = new Anexo();
        anexo.setId(id);
        return anexo;
    }
}
