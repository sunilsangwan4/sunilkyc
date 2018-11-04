package com.eagle.kyc.service.mapper;

import com.eagle.kyc.domain.*;
import com.eagle.kyc.service.dto.TradingInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TradingInfo and its DTO TradingInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TradingInfoMapper extends EntityMapper<TradingInfoDTO, TradingInfo> {


    @Mapping(target = "applicationProspect", ignore = true)
    TradingInfo toEntity(TradingInfoDTO tradingInfoDTO);

    default TradingInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TradingInfo tradingInfo = new TradingInfo();
        tradingInfo.setId(id);
        return tradingInfo;
    }
}
