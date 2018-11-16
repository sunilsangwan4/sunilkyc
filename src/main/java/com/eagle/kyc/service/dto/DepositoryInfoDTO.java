package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DepositoryInfo entity.
 */
public class DepositoryInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean haveAccountWithOtherDP;

    private Boolean haveSMSEnabled;

    private String statementFrequency;

    private String dpScheme;

    private String depositoryName;

    private String brokerName;

    private String nameAsPerDemat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHaveAccountWithOtherDP() {
        return haveAccountWithOtherDP;
    }

    public void setHaveAccountWithOtherDP(Boolean haveAccountWithOtherDP) {
        this.haveAccountWithOtherDP = haveAccountWithOtherDP;
    }

    public Boolean isHaveSMSEnabled() {
        return haveSMSEnabled;
    }

    public void setHaveSMSEnabled(Boolean haveSMSEnabled) {
        this.haveSMSEnabled = haveSMSEnabled;
    }

    public String getStatementFrequency() {
        return statementFrequency;
    }

    public void setStatementFrequency(String statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public String getDpScheme() {
        return dpScheme;
    }

    public void setDpScheme(String dpScheme) {
        this.dpScheme = dpScheme;
    }

    public String getDepositoryName() {
        return depositoryName;
    }

    public void setDepositoryName(String depositoryName) {
        this.depositoryName = depositoryName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getNameAsPerDemat() {
        return nameAsPerDemat;
    }

    public void setNameAsPerDemat(String nameAsPerDemat) {
        this.nameAsPerDemat = nameAsPerDemat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepositoryInfoDTO depositoryInfoDTO = (DepositoryInfoDTO) o;
        if (depositoryInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depositoryInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepositoryInfoDTO{" +
            "id=" + getId() +
            ", haveAccountWithOtherDP='" + isHaveAccountWithOtherDP() + "'" +
            ", haveSMSEnabled='" + isHaveSMSEnabled() + "'" +
            ", statementFrequency='" + getStatementFrequency() + "'" +
            ", dpScheme='" + getDpScheme() + "'" +
            ", depositoryName='" + getDepositoryName() + "'" +
            ", brokerName='" + getBrokerName() + "'" +
            ", nameAsPerDemat='" + getNameAsPerDemat() + "'" +
            "}";
    }
}
