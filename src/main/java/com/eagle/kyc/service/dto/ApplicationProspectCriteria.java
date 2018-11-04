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
 * Criteria class for the ApplicationProspect entity. This class is used in ApplicationProspectResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /application-prospects?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationProspectCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter mobileNo;

    private StringFilter email;

    private StringFilter password;

    private StringFilter confirmPassword;

    private LongFilter personalInformationId;

    private LongFilter investmentPotentialId;

    private LongFilter nomineeId;

    private LongFilter tradingInfoId;

    private LongFilter depositoryId;

    private LongFilter addressesId;

    private LongFilter bankInformationId;

    public ApplicationProspectCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(StringFilter confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public LongFilter getPersonalInformationId() {
        return personalInformationId;
    }

    public void setPersonalInformationId(LongFilter personalInformationId) {
        this.personalInformationId = personalInformationId;
    }

    public LongFilter getInvestmentPotentialId() {
        return investmentPotentialId;
    }

    public void setInvestmentPotentialId(LongFilter investmentPotentialId) {
        this.investmentPotentialId = investmentPotentialId;
    }

    public LongFilter getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(LongFilter nomineeId) {
        this.nomineeId = nomineeId;
    }

    public LongFilter getTradingInfoId() {
        return tradingInfoId;
    }

    public void setTradingInfoId(LongFilter tradingInfoId) {
        this.tradingInfoId = tradingInfoId;
    }

    public LongFilter getDepositoryId() {
        return depositoryId;
    }

    public void setDepositoryId(LongFilter depositoryId) {
        this.depositoryId = depositoryId;
    }

    public LongFilter getAddressesId() {
        return addressesId;
    }

    public void setAddressesId(LongFilter addressesId) {
        this.addressesId = addressesId;
    }

    public LongFilter getBankInformationId() {
        return bankInformationId;
    }

    public void setBankInformationId(LongFilter bankInformationId) {
        this.bankInformationId = bankInformationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicationProspectCriteria that = (ApplicationProspectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(password, that.password) &&
            Objects.equals(confirmPassword, that.confirmPassword) &&
            Objects.equals(personalInformationId, that.personalInformationId) &&
            Objects.equals(investmentPotentialId, that.investmentPotentialId) &&
            Objects.equals(nomineeId, that.nomineeId) &&
            Objects.equals(tradingInfoId, that.tradingInfoId) &&
            Objects.equals(depositoryId, that.depositoryId) &&
            Objects.equals(addressesId, that.addressesId) &&
            Objects.equals(bankInformationId, that.bankInformationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        mobileNo,
        email,
        password,
        confirmPassword,
        personalInformationId,
        investmentPotentialId,
        nomineeId,
        tradingInfoId,
        depositoryId,
        addressesId,
        bankInformationId
        );
    }

    @Override
    public String toString() {
        return "ApplicationProspectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (confirmPassword != null ? "confirmPassword=" + confirmPassword + ", " : "") +
                (personalInformationId != null ? "personalInformationId=" + personalInformationId + ", " : "") +
                (investmentPotentialId != null ? "investmentPotentialId=" + investmentPotentialId + ", " : "") +
                (nomineeId != null ? "nomineeId=" + nomineeId + ", " : "") +
                (tradingInfoId != null ? "tradingInfoId=" + tradingInfoId + ", " : "") +
                (depositoryId != null ? "depositoryId=" + depositoryId + ", " : "") +
                (addressesId != null ? "addressesId=" + addressesId + ", " : "") +
                (bankInformationId != null ? "bankInformationId=" + bankInformationId + ", " : "") +
            "}";
    }

}
