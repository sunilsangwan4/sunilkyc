package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.GroupCdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GroupCd and its DTO GroupCdDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupCdMapper extends EntityMapper<GroupCdDTO, GroupCd> {


    @Mapping(target = "typeCds", ignore = true)
    GroupCd toEntity(GroupCdDTO groupCdDTO);

    default GroupCd fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupCd groupCd = new GroupCd();
        groupCd.setId(id);
        return groupCd;
    }
}
