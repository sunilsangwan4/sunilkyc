package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BankInformation.
 */
@Entity
@Table(name = "bank_information")
public class BankInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "ifsc_code", length = 11, nullable = false)
    private String ifscCode;

    @NotNull
    @Column(name = "micr_code", nullable = false)
    private String micrCode;

    @NotNull
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @NotNull
    @Column(name = "account_type", nullable = false)
    private String accountType;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @Column(name = "bank_account_common")
    private Boolean bankAccountCommon;

    @Column(name = "segement_type_cd")
    private String segementTypeCd;

    @ManyToOne
    @JsonIgnoreProperties("bankInformations")
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public BankInformation bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public BankInformation ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public BankInformation micrCode(String micrCode) {
        this.micrCode = micrCode;
        return this;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public BankInformation branchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountType() {
        return accountType;
    }

    public BankInformation accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankInformation accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BankInformation accountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Boolean isBankAccountCommon() {
        return bankAccountCommon;
    }

    public BankInformation bankAccountCommon(Boolean bankAccountCommon) {
        this.bankAccountCommon = bankAccountCommon;
        return this;
    }

    public void setBankAccountCommon(Boolean bankAccountCommon) {
        this.bankAccountCommon = bankAccountCommon;
    }

    public String getSegementTypeCd() {
        return segementTypeCd;
    }

    public BankInformation segementTypeCd(String segementTypeCd) {
        this.segementTypeCd = segementTypeCd;
        return this;
    }

    public void setSegementTypeCd(String segementTypeCd) {
        this.segementTypeCd = segementTypeCd;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public BankInformation applicationProspect(ApplicationProspect applicationProspect) {
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
        BankInformation bankInformation = (BankInformation) o;
        if (bankInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankInformation{" +
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
            "}";
    }
}
