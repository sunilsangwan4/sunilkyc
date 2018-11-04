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
 * Criteria class for the BankInformation entity. This class is used in BankInformationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bank-informations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BankInformationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bankName;

    private StringFilter ifscCode;

    private StringFilter micrCode;

    private StringFilter branchName;

    private StringFilter accountType;

    private StringFilter accountNumber;

    private StringFilter accountHolderName;

    private BooleanFilter bankAccountCommon;

    private StringFilter segementTypeCd;

    private LongFilter applicationProspectId;

    public BankInformationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public StringFilter getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(StringFilter micrCode) {
        this.micrCode = micrCode;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getAccountType() {
        return accountType;
    }

    public void setAccountType(StringFilter accountType) {
        this.accountType = accountType;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(StringFilter accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BooleanFilter getBankAccountCommon() {
        return bankAccountCommon;
    }

    public void setBankAccountCommon(BooleanFilter bankAccountCommon) {
        this.bankAccountCommon = bankAccountCommon;
    }

    public StringFilter getSegementTypeCd() {
        return segementTypeCd;
    }

    public void setSegementTypeCd(StringFilter segementTypeCd) {
        this.segementTypeCd = segementTypeCd;
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
        final BankInformationCriteria that = (BankInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(micrCode, that.micrCode) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accountHolderName, that.accountHolderName) &&
            Objects.equals(bankAccountCommon, that.bankAccountCommon) &&
            Objects.equals(segementTypeCd, that.segementTypeCd) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        bankName,
        ifscCode,
        micrCode,
        branchName,
        accountType,
        accountNumber,
        accountHolderName,
        bankAccountCommon,
        segementTypeCd,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "BankInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
                (micrCode != null ? "micrCode=" + micrCode + ", " : "") +
                (branchName != null ? "branchName=" + branchName + ", " : "") +
                (accountType != null ? "accountType=" + accountType + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (accountHolderName != null ? "accountHolderName=" + accountHolderName + ", " : "") +
                (bankAccountCommon != null ? "bankAccountCommon=" + bankAccountCommon + ", " : "") +
                (segementTypeCd != null ? "segementTypeCd=" + segementTypeCd + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
