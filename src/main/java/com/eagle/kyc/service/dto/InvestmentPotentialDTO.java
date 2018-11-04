package com.eagle.kyc.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InvestmentPotential entity.
 */
public class InvestmentPotentialDTO implements Serializable {

    private Long id;

    @NotNull
    private String educationQualification;

    @NotNull
    private String occupation;

    private Float annualIncome;

    @NotNull
    private Double netWorth;

    @NotNull
    private LocalDate networthDeclarationDate;

    @NotNull
    private String pepRelative;

    @NotNull
    private String pmlaRiskCategory;

    @NotNull
    private String pmlaRiskCategoryReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Float getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Float annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Double netWorth) {
        this.netWorth = netWorth;
    }

    public LocalDate getNetworthDeclarationDate() {
        return networthDeclarationDate;
    }

    public void setNetworthDeclarationDate(LocalDate networthDeclarationDate) {
        this.networthDeclarationDate = networthDeclarationDate;
    }

    public String getPepRelative() {
        return pepRelative;
    }

    public void setPepRelative(String pepRelative) {
        this.pepRelative = pepRelative;
    }

    public String getPmlaRiskCategory() {
        return pmlaRiskCategory;
    }

    public void setPmlaRiskCategory(String pmlaRiskCategory) {
        this.pmlaRiskCategory = pmlaRiskCategory;
    }

    public String getPmlaRiskCategoryReason() {
        return pmlaRiskCategoryReason;
    }

    public void setPmlaRiskCategoryReason(String pmlaRiskCategoryReason) {
        this.pmlaRiskCategoryReason = pmlaRiskCategoryReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvestmentPotentialDTO investmentPotentialDTO = (InvestmentPotentialDTO) o;
        if (investmentPotentialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), investmentPotentialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvestmentPotentialDTO{" +
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
