package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InvestmentPotential.
 */
@Entity
@Table(name = "investment_potential")
public class InvestmentPotential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "education_qualification", nullable = false)
    private String educationQualification;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "annual_income")
    private Float annualIncome;

    @NotNull
    @Column(name = "net_worth", nullable = false)
    private Double netWorth;

    @NotNull
    @Column(name = "networth_declaration_date", nullable = false)
    private LocalDate networthDeclarationDate;

    @NotNull
    @Column(name = "pep_relative", nullable = false)
    private String pepRelative;

    @NotNull
    @Column(name = "pmla_risk_category", nullable = false)
    private String pmlaRiskCategory;

    @NotNull
    @Column(name = "pmla_risk_category_reason", nullable = false)
    private String pmlaRiskCategoryReason;

    @OneToOne(mappedBy = "investmentPotential")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public InvestmentPotential educationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
        return this;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public InvestmentPotential occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Float getAnnualIncome() {
        return annualIncome;
    }

    public InvestmentPotential annualIncome(Float annualIncome) {
        this.annualIncome = annualIncome;
        return this;
    }

    public void setAnnualIncome(Float annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getNetWorth() {
        return netWorth;
    }

    public InvestmentPotential netWorth(Double netWorth) {
        this.netWorth = netWorth;
        return this;
    }

    public void setNetWorth(Double netWorth) {
        this.netWorth = netWorth;
    }

    public LocalDate getNetworthDeclarationDate() {
        return networthDeclarationDate;
    }

    public InvestmentPotential networthDeclarationDate(LocalDate networthDeclarationDate) {
        this.networthDeclarationDate = networthDeclarationDate;
        return this;
    }

    public void setNetworthDeclarationDate(LocalDate networthDeclarationDate) {
        this.networthDeclarationDate = networthDeclarationDate;
    }

    public String getPepRelative() {
        return pepRelative;
    }

    public InvestmentPotential pepRelative(String pepRelative) {
        this.pepRelative = pepRelative;
        return this;
    }

    public void setPepRelative(String pepRelative) {
        this.pepRelative = pepRelative;
    }

    public String getPmlaRiskCategory() {
        return pmlaRiskCategory;
    }

    public InvestmentPotential pmlaRiskCategory(String pmlaRiskCategory) {
        this.pmlaRiskCategory = pmlaRiskCategory;
        return this;
    }

    public void setPmlaRiskCategory(String pmlaRiskCategory) {
        this.pmlaRiskCategory = pmlaRiskCategory;
    }

    public String getPmlaRiskCategoryReason() {
        return pmlaRiskCategoryReason;
    }

    public InvestmentPotential pmlaRiskCategoryReason(String pmlaRiskCategoryReason) {
        this.pmlaRiskCategoryReason = pmlaRiskCategoryReason;
        return this;
    }

    public void setPmlaRiskCategoryReason(String pmlaRiskCategoryReason) {
        this.pmlaRiskCategoryReason = pmlaRiskCategoryReason;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public InvestmentPotential applicationProspect(ApplicationProspect applicationProspect) {
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
        InvestmentPotential investmentPotential = (InvestmentPotential) o;
        if (investmentPotential.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), investmentPotential.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvestmentPotential{" +
            "id=" + getId() +
            ", educationQualification='" + getEducationQualification() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", annualIncome=" + getAnnualIncome() +
            ", netWorth=" + getNetWorth() +
            ", networthDeclarationDate='" + getNetworthDeclarationDate() + "'" +
            ", pepRelative='" + getPepRelative() + "'" +
            ", pmlaRiskCategory='" + getPmlaRiskCategory() + "'" +
            ", pmlaRiskCategoryReason='" + getPmlaRiskCategoryReason() + "'" +
            "}";
    }
}
