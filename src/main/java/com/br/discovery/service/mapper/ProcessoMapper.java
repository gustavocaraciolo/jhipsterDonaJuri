package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.ProcessoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Processo and its DTO ProcessoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserExtraMapper.class, AnexoMapper.class})
public interface ProcessoMapper extends EntityMapper<ProcessoDTO, Processo> {

    @Mapping(source = "advogadoCorrente.id", target = "advogadoCorrenteId")
    @Mapping(source = "cliente.id", target = "clienteId")
    ProcessoDTO toDto(Processo processo); 

    @Mapping(source = "advogadoCorrenteId", target = "advogadoCorrente")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(target = "pendencias", ignore = true)
    Processo toEntity(ProcessoDTO processoDTO);

    default Processo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Processo processo = new Processo();
        processo.setId(id);
        return processo;
    }
}
