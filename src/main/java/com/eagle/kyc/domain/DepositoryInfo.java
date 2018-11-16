package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DepositoryInfo.
 */
@Entity
@Table(name = "depository_info")
public class DepositoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "have_account_with_other_dp", nullable = false)
    private Boolean haveAccountWithOtherDP;

    @Column(name = "have_sms_enabled", nullable = false)
    private Boolean haveSMSEnabled;

    @Column(name = "statement_frequency", nullable = false)
    private String statementFrequency;

    @Column(name = "dp_scheme", nullable = false)
    private String dpScheme;

    @Column(name = "depository_name", nullable = false)
    private String depositoryName;

    @Column(name = "broker_name", nullable = false)
    private String brokerName;

    @Column(name = "name_as_per_demat", nullable = false)
    private String nameAsPerDemat;

    @OneToOne(mappedBy = "depository")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHaveAccountWithOtherDP() {
        return haveAccountWithOtherDP;
    }

    public DepositoryInfo haveAccountWithOtherDP(Boolean haveAccountWithOtherDP) {
        this.haveAccountWithOtherDP = haveAccountWithOtherDP;
        return this;
    }

    public void setHaveAccountWithOtherDP(Boolean haveAccountWithOtherDP) {
        this.haveAccountWithOtherDP = haveAccountWithOtherDP;
    }

    public Boolean isHaveSMSEnabled() {
        return haveSMSEnabled;
    }

    public DepositoryInfo haveSMSEnabled(Boolean haveSMSEnabled) {
        this.haveSMSEnabled = haveSMSEnabled;
        return this;
    }

    public void setHaveSMSEnabled(Boolean haveSMSEnabled) {
        this.haveSMSEnabled = haveSMSEnabled;
    }

    public String getStatementFrequency() {
        return statementFrequency;
    }

    public DepositoryInfo statementFrequency(String statementFrequency) {
        this.statementFrequency = statementFrequency;
        return this;
    }

    public void setStatementFrequency(String statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public String getDpScheme() {
        return dpScheme;
    }

    public DepositoryInfo dpScheme(String dpScheme) {
        this.dpScheme = dpScheme;
        return this;
    }

    public void setDpScheme(String dpScheme) {
        this.dpScheme = dpScheme;
    }

    public String getDepositoryName() {
        return depositoryName;
    }

    public DepositoryInfo depositoryName(String depositoryName) {
        this.depositoryName = depositoryName;
        return this;
    }

    public void setDepositoryName(String depositoryName) {
        this.depositoryName = depositoryName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public DepositoryInfo brokerName(String brokerName) {
        this.brokerName = brokerName;
        return this;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getNameAsPerDemat() {
        return nameAsPerDemat;
    }

    public DepositoryInfo nameAsPerDemat(String nameAsPerDemat) {
        this.nameAsPerDemat = nameAsPerDemat;
        return this;
    }

    public void setNameAsPerDemat(String nameAsPerDemat) {
        this.nameAsPerDemat = nameAsPerDemat;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public DepositoryInfo applicationProspect(ApplicationProspect applicationProspect) {
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
        DepositoryInfo depositoryInfo = (DepositoryInfo) o;
        if (depositoryInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depositoryInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepositoryInfo{" +
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
