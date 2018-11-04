package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.DepositoryInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepositoryInfo and its DTO DepositoryInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepositoryInfoMapper extends EntityMapper<DepositoryInfoDTO, DepositoryInfo> {


    @Mapping(target = "applicationProspect", ignore = true)
    DepositoryInfo toEntity(DepositoryInfoDTO depositoryInfoDTO);

    default DepositoryInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepositoryInfo depositoryInfo = new DepositoryInfo();
        depositoryInfo.setId(id);
        return depositoryInfo;
    }
}
