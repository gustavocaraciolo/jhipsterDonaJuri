package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.PendenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pendencia and its DTO PendenciaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProcessoMapper.class, UserExtraMapper.class})
public interface PendenciaMapper extends EntityMapper<PendenciaDTO, Pendencia> {

    @Mapping(source = "processo.id", target = "processoId")
    PendenciaDTO toDto(Pendencia pendencia); 

    @Mapping(source = "processoId", target = "processo")
    Pendencia toEntity(PendenciaDTO pendenciaDTO);

    default Pendencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pendencia pendencia = new Pendencia();
        pendencia.setId(id);
        return pendencia;
    }
}
