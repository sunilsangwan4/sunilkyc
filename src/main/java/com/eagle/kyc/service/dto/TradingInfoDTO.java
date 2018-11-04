package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TradingInfo entity.
 */
public class TradingInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String segmentCd;

    @NotNull
    private String planCdEquity;

    @NotNull
    private String planCdCommodity;

    @NotNull
    private String contractNoteMode;

    @NotNull
    private String tradingMode;

    @NotNull
    private Boolean interestedInMobileTradeing;

    @NotNull
    private String accountAuthFrequency;

    @NotNull
    private Integer experienceYear;

    @NotNull
    private Integer experienceMonth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegmentCd() {
        return segmentCd;
    }

    public void setSegmentCd(String segmentCd) {
        this.segmentCd = segmentCd;
    }

    public String getPlanCdEquity() {
        return planCdEquity;
    }

    public void setPlanCdEquity(String planCdEquity) {
        this.planCdEquity = planCdEquity;
    }

    public String getPlanCdCommodity() {
        return planCdCommodity;
    }

    public void setPlanCdCommodity(String planCdCommodity) {
        this.planCdCommodity = planCdCommodity;
    }

    public String getContractNoteMode() {
        return contractNoteMode;
    }

    public void setContractNoteMode(String contractNoteMode) {
        this.contractNoteMode = contractNoteMode;
    }

    public String getTradingMode() {
        return tradingMode;
    }

    public void setTradingMode(String tradingMode) {
        this.tradingMode = tradingMode;
    }

    public Boolean isInterestedInMobileTradeing() {
        return interestedInMobileTradeing;
    }

    public void setInterestedInMobileTradeing(Boolean interestedInMobileTradeing) {
        this.interestedInMobileTradeing = interestedInMobileTradeing;
    }

    public String getAccountAuthFrequency() {
        return accountAuthFrequency;
    }

    public void setAccountAuthFrequency(String accountAuthFrequency) {
        this.accountAuthFrequency = accountAuthFrequency;
    }

    public Integer getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
    }

    public Integer getExperienceMonth() {
        return experienceMonth;
    }

    public void setExperienceMonth(Integer experienceMonth) {
        this.experienceMonth = experienceMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TradingInfoDTO tradingInfoDTO = (TradingInfoDTO) o;
        if (tradingInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tradingInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TradingInfoDTO{" +
            "id=" + getId() +
            ", segmentCd='" + getSegmentCd() + "'" +
            ", planCdEquity='" + getPlanCdEquity() + "'" +
            ", planCdCommodity='" + getPlanCdCommodity() + "'" +
            ", contractNoteMode='" + getContractNoteMode() + "'" +
            ", tradingMode='" + getTradingMode() + "'" +
            ", interestedInMobileTradeing='" + isInterestedInMobileTradeing() + "'" +
            ", accountAuthFrequency='" + getAccountAuthFrequency() + "'" +
            ", experienceYear=" + getExperienceYear() +
            ", experienceMonth=" + getExperienceMonth() +
            "}";
    }
}
