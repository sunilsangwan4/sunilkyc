package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.TypeCdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeCd and its DTO TypeCdDTO.
 */
@Mapper(componentModel = "spring", uses = {GroupCdMapper.class})
public interface TypeCdMapper extends EntityMapper<TypeCdDTO, TypeCd> {

    @Mapping(source = "groupCd.id", target = "groupCdId")
    TypeCdDTO toDto(TypeCd typeCd);

    @Mapping(source = "groupCdId", target = "groupCd")
    TypeCd toEntity(TypeCdDTO typeCdDTO);

    default TypeCd fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeCd typeCd = new TypeCd();
        typeCd.setId(id);
        return typeCd;
    }
}
