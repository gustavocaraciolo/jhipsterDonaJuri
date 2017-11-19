package com.br.discovery.service.mapper;

import com.br.discovery.domain.*;
import com.br.discovery.service.dto.UserExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExtra and its DTO UserExtraDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EscritorioMapper.class, ConviteMapper.class})
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "escritorio.id", target = "escritorioId")
    @Mapping(source = "convite.id", target = "conviteId")
    UserExtraDTO toDto(UserExtra userExtra); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "escritorioId", target = "escritorio")
    @Mapping(source = "conviteId", target = "convite")
    @Mapping(target = "processoClientes", ignore = true)
    @Mapping(target = "processoAdvogadoCorrentes", ignore = true)
    @Mapping(target = "processoAdvogados", ignore = true)
    @Mapping(target = "pendenciaAdvogados", ignore = true)
    UserExtra toEntity(UserExtraDTO userExtraDTO);

    default UserExtra fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtra userExtra = new UserExtra();
        userExtra.setId(id);
        return userExtra;
    }
}
