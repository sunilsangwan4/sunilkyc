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
 * Criteria class for the DepositoryInfo entity. This class is used in DepositoryInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /depository-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepositoryInfoCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter haveAccountWithOtherDP;

    private BooleanFilter haveSMSEnabled;

    private StringFilter statementFrequency;

    private StringFilter dpScheme;

    private StringFilter depositoryName;

    private StringFilter brokerName;

    private StringFilter nameAsPerDemat;

    private LongFilter applicationProspectId;

    public DepositoryInfoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getHaveAccountWithOtherDP() {
        return haveAccountWithOtherDP;
    }

    public void setHaveAccountWithOtherDP(BooleanFilter haveAccountWithOtherDP) {
        this.haveAccountWithOtherDP = haveAccountWithOtherDP;
    }

    public BooleanFilter getHaveSMSEnabled() {
        return haveSMSEnabled;
    }

    public void setHaveSMSEnabled(BooleanFilter haveSMSEnabled) {
        this.haveSMSEnabled = haveSMSEnabled;
    }

    public StringFilter getStatementFrequency() {
        return statementFrequency;
    }

    public void setStatementFrequency(StringFilter statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public StringFilter getDpScheme() {
        return dpScheme;
    }

    public void setDpScheme(StringFilter dpScheme) {
        this.dpScheme = dpScheme;
    }

    public StringFilter getDepositoryName() {
        return depositoryName;
    }

    public void setDepositoryName(StringFilter depositoryName) {
        this.depositoryName = depositoryName;
    }

    public StringFilter getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(StringFilter brokerName) {
        this.brokerName = brokerName;
    }

    public StringFilter getNameAsPerDemat() {
        return nameAsPerDemat;
    }

    public void setNameAsPerDemat(StringFilter nameAsPerDemat) {
        this.nameAsPerDemat = nameAsPerDemat;
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
        final DepositoryInfoCriteria that = (DepositoryInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(haveAccountWithOtherDP, that.haveAccountWithOtherDP) &&
            Objects.equals(haveSMSEnabled, that.haveSMSEnabled) &&
            Objects.equals(statementFrequency, that.statementFrequency) &&
            Objects.equals(dpScheme, that.dpScheme) &&
            Objects.equals(depositoryName, that.depositoryName) &&
            Objects.equals(brokerName, that.brokerName) &&
            Objects.equals(nameAsPerDemat, that.nameAsPerDemat) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        haveAccountWithOtherDP,
        haveSMSEnabled,
        statementFrequency,
        dpScheme,
        depositoryName,
        brokerName,
        nameAsPerDemat,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "DepositoryInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (haveAccountWithOtherDP != null ? "haveAccountWithOtherDP=" + haveAccountWithOtherDP + ", " : "") +
                (haveSMSEnabled != null ? "haveSMSEnabled=" + haveSMSEnabled + ", " : "") +
                (statementFrequency != null ? "statementFrequency=" + statementFrequency + ", " : "") +
                (dpScheme != null ? "dpScheme=" + dpScheme + ", " : "") +
                (depositoryName != null ? "depositoryName=" + depositoryName + ", " : "") +
                (brokerName != null ? "brokerName=" + brokerName + ", " : "") +
                (nameAsPerDemat != null ? "nameAsPerDemat=" + nameAsPerDemat + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
