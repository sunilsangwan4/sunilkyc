package com.eagle.kyc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the TradingInfo entity. This class is used in TradingInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /trading-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TradingInfoCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter segmentCd;

    private StringFilter planCdEquity;

    private StringFilter planCdCommodity;

    private StringFilter contractNoteMode;

    private StringFilter tradingMode;

    private BooleanFilter interestedInMobileTradeing;

    private StringFilter accountAuthFrequency;

    private IntegerFilter experienceYear;

    private IntegerFilter experienceMonth;

    private LongFilter applicationProspectId;

    public TradingInfoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSegmentCd() {
        return segmentCd;
    }

    public void setSegmentCd(StringFilter segmentCd) {
        this.segmentCd = segmentCd;
    }

    public StringFilter getPlanCdEquity() {
        return planCdEquity;
    }

    public void setPlanCdEquity(StringFilter planCdEquity) {
        this.planCdEquity = planCdEquity;
    }

    public StringFilter getPlanCdCommodity() {
        return planCdCommodity;
    }

    public void setPlanCdCommodity(StringFilter planCdCommodity) {
        this.planCdCommodity = planCdCommodity;
    }

    public StringFilter getContractNoteMode() {
        return contractNoteMode;
    }

    public void setContractNoteMode(StringFilter contractNoteMode) {
        this.contractNoteMode = contractNoteMode;
    }

    public StringFilter getTradingMode() {
        return tradingMode;
    }

    public void setTradingMode(StringFilter tradingMode) {
        this.tradingMode = tradingMode;
    }

    public BooleanFilter getInterestedInMobileTradeing() {
        return interestedInMobileTradeing;
    }

    public void setInterestedInMobileTradeing(BooleanFilter interestedInMobileTradeing) {
        this.interestedInMobileTradeing = interestedInMobileTradeing;
    }

    public StringFilter getAccountAuthFrequency() {
        return accountAuthFrequency;
    }

    public void setAccountAuthFrequency(StringFilter accountAuthFrequency) {
        this.accountAuthFrequency = accountAuthFrequency;
    }

    public IntegerFilter getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(IntegerFilter experienceYear) {
        this.experienceYear = experienceYear;
    }

    public IntegerFilter getExperienceMonth() {
        return experienceMonth;
    }

    public void setExperienceMonth(IntegerFilter experienceMonth) {
        this.experienceMonth = experienceMonth;
    }

    public LongFilter getApplicationProspectId() {
        return applicationProspectId;
    }

    public void setApplicationProspectId(LongFilter applicationProspectId) {
        this.applicationProspectId = applicationProspectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradingInfoCriteria that = (TradingInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(segmentCd, that.segmentCd) &&
            Objects.equals(planCdEquity, that.planCdEquity) &&
            Objects.equals(planCdCommodity, that.planCdCommodity) &&
            Objects.equals(contractNoteMode, that.contractNoteMode) &&
            Objects.equals(tradingMode, that.tradingMode) &&
            Objects.equals(interestedInMobileTradeing, that.interestedInMobileTradeing) &&
            Objects.equals(accountAuthFrequency, that.accountAuthFrequency) &&
            Objects.equals(experienceYear, that.experienceYear) &&
            Objects.equals(experienceMonth, that.experienceMonth) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        segmentCd,
        planCdEquity,
        planCdCommodity,
        contractNoteMode,
        tradingMode,
        interestedInMobileTradeing,
        accountAuthFrequency,
        experienceYear,
        experienceMonth,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "TradingInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (segmentCd != null ? "segmentCd=" + segmentCd + ", " : "") +
                (planCdEquity != null ? "planCdEquity=" + planCdEquity + ", " : "") +
                (planCdCommodity != null ? "planCdCommodity=" + planCdCommodity + ", " : "") +
                (contractNoteMode != null ? "contractNoteMode=" + contractNoteMode + ", " : "") +
                (tradingMode != null ? "tradingMode=" + tradingMode + ", " : "") +
                (interestedInMobileTradeing != null ? "interestedInMobileTradeing=" + interestedInMobileTradeing + ", " : "") +
                (accountAuthFrequency != null ? "accountAuthFrequency=" + accountAuthFrequency + ", " : "") +
                (experienceYear != null ? "experienceYear=" + experienceYear + ", " : "") +
                (experienceMonth != null ? "experienceMonth=" + experienceMonth + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
