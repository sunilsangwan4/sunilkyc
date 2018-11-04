package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TradingInfo.
 */
@Entity
@Table(name = "trading_info")
public class TradingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "segment_cd", nullable = false)
    private String segmentCd;

    @NotNull
    @Column(name = "plan_cd_equity", nullable = false)
    private String planCdEquity;

    @NotNull
    @Column(name = "plan_cd_commodity", nullable = false)
    private String planCdCommodity;

    @NotNull
    @Column(name = "contract_note_mode", nullable = false)
    private String contractNoteMode;

    @NotNull
    @Column(name = "trading_mode", nullable = false)
    private String tradingMode;

    @NotNull
    @Column(name = "interested_in_mobile_tradeing", nullable = false)
    private Boolean interestedInMobileTradeing;

    @NotNull
    @Column(name = "account_auth_frequency", nullable = false)
    private String accountAuthFrequency;

    @NotNull
    @Column(name = "experience_year", nullable = false)
    private Integer experienceYear;

    @NotNull
    @Column(name = "experience_month", nullable = false)
    private Integer experienceMonth;

    @OneToOne(mappedBy = "tradingInfo")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegmentCd() {
        return segmentCd;
    }

    public TradingInfo segmentCd(String segmentCd) {
        this.segmentCd = segmentCd;
        return this;
    }

    public void setSegmentCd(String segmentCd) {
        this.segmentCd = segmentCd;
    }

    public String getPlanCdEquity() {
        return planCdEquity;
    }

    public TradingInfo planCdEquity(String planCdEquity) {
        this.planCdEquity = planCdEquity;
        return this;
    }

    public void setPlanCdEquity(String planCdEquity) {
        this.planCdEquity = planCdEquity;
    }

    public String getPlanCdCommodity() {
        return planCdCommodity;
    }

    public TradingInfo planCdCommodity(String planCdCommodity) {
        this.planCdCommodity = planCdCommodity;
        return this;
    }

    public void setPlanCdCommodity(String planCdCommodity) {
        this.planCdCommodity = planCdCommodity;
    }

    public String getContractNoteMode() {
        return contractNoteMode;
    }

    public TradingInfo contractNoteMode(String contractNoteMode) {
        this.contractNoteMode = contractNoteMode;
        return this;
    }

    public void setContractNoteMode(String contractNoteMode) {
        this.contractNoteMode = contractNoteMode;
    }

    public String getTradingMode() {
        return tradingMode;
    }

    public TradingInfo tradingMode(String tradingMode) {
        this.tradingMode = tradingMode;
        return this;
    }

    public void setTradingMode(String tradingMode) {
        this.tradingMode = tradingMode;
    }

    public Boolean isInterestedInMobileTradeing() {
        return interestedInMobileTradeing;
    }

    public TradingInfo interestedInMobileTradeing(Boolean interestedInMobileTradeing) {
        this.interestedInMobileTradeing = interestedInMobileTradeing;
        return this;
    }

    public void setInterestedInMobileTradeing(Boolean interestedInMobileTradeing) {
        this.interestedInMobileTradeing = interestedInMobileTradeing;
    }

    public String getAccountAuthFrequency() {
        return accountAuthFrequency;
    }

    public TradingInfo accountAuthFrequency(String accountAuthFrequency) {
        this.accountAuthFrequency = accountAuthFrequency;
        return this;
    }

    public void setAccountAuthFrequency(String accountAuthFrequency) {
        this.accountAuthFrequency = accountAuthFrequency;
    }

    public Integer getExperienceYear() {
        return experienceYear;
    }

    public TradingInfo experienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
        return this;
    }

    public void setExperienceYear(Integer experienceYear) {
        this.experienceYear = experienceYear;
    }

    public Integer getExperienceMonth() {
        return experienceMonth;
    }

    public TradingInfo experienceMonth(Integer experienceMonth) {
        this.experienceMonth = experienceMonth;
        return this;
    }

    public void setExperienceMonth(Integer experienceMonth) {
        this.experienceMonth = experienceMonth;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public TradingInfo applicationProspect(ApplicationProspect applicationProspect) {
        this.applicationProspect = applicationProspect;
        return this;
    }

    public void setApplicationProspect(ApplicationProspect applicationProspect) {
        this.applicationProspect = applicationProspect;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradingInfo tradingInfo = (TradingInfo) o;
        if (tradingInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tradingInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TradingInfo{" +
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
