package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BankInformation entity.
 */
public class BankInformationDTO implements Serializable {

    private Long id;

    @NotNull
    private String bankName;

    @NotNull
    @Size(min = 11, max = 11)
    private String ifscCode;

    @NotNull
    private String micrCode;

    @NotNull
    private String branchName;

    @NotNull
    private String accountType;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountHolderName;

    private Boolean bankAccountCommon;

    private String segementTypeCd;

    private Long applicationProspectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Boolean isBankAccountCommon() {
        return bankAccountCommon;
    }

    public void setBankAccountCommon(Boolean bankAccountCommon) {
        this.bankAccountCommon = bankAccountCommon;
    }

    public String getSegementTypeCd() {
        return segementTypeCd;
    }

    public void setSegementTypeCd(String segementTypeCd) {
        this.segementTypeCd = segementTypeCd;
    }

    public Long getApplicationProspectId() {
        return applicationProspectId;
    }

    public void setApplicationProspectId(Long applicationProspectId) {
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

        BankInformationDTO bankInformationDTO = (BankInformationDTO) o;
        if (bankInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankInformationDTO{" +
            "id=" + getId() +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", micrCode='" + getMicrCode() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", bankAccountCommon='" + isBankAccountCommon() + "'" +
            ", segementTypeCd='" + getSegementTypeCd() + "'" +
            ", applicationProspect=" + getApplicationProspectId() +
            "}";
    }
}
