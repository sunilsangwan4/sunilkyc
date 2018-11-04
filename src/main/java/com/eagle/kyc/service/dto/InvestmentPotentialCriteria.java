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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the InvestmentPotential entity. This class is used in InvestmentPotentialResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /investment-potentials?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvestmentPotentialCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter educationQualification;

    private StringFilter occupation;

    private FloatFilter annualIncome;

    private DoubleFilter netWorth;

    private LocalDateFilter networthDeclarationDate;

    private StringFilter pepRelative;

    private StringFilter pmlaRiskCategory;

    private StringFilter pmlaRiskCategoryReason;

    private LongFilter applicationProspectId;

    public InvestmentPotentialCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(StringFilter educationQualification) {
        this.educationQualification = educationQualification;
    }

    public StringFilter getOccupation() {
        return occupation;
    }

    public void setOccupation(StringFilter occupation) {
        this.occupation = occupation;
    }

    public FloatFilter getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(FloatFilter annualIncome) {
        this.annualIncome = annualIncome;
    }

    public DoubleFilter getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(DoubleFilter netWorth) {
        this.netWorth = netWorth;
    }

    public LocalDateFilter getNetworthDeclarationDate() {
        return networthDeclarationDate;
    }

    public void setNetworthDeclarationDate(LocalDateFilter networthDeclarationDate) {
        this.networthDeclarationDate = networthDeclarationDate;
    }

    public StringFilter getPepRelative() {
        return pepRelative;
    }

    public void setPepRelative(StringFilter pepRelative) {
        this.pepRelative = pepRelative;
    }

    public StringFilter getPmlaRiskCategory() {
        return pmlaRiskCategory;
    }

    public void setPmlaRiskCategory(StringFilter pmlaRiskCategory) {
        this.pmlaRiskCategory = pmlaRiskCategory;
    }

    public StringFilter getPmlaRiskCategoryReason() {
        return pmlaRiskCategoryReason;
    }

    public void setPmlaRiskCategoryReason(StringFilter pmlaRiskCategoryReason) {
        this.pmlaRiskCategoryReason = pmlaRiskCategoryReason;
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
        final InvestmentPotentialCriteria that = (InvestmentPotentialCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(educationQualification, that.educationQualification) &&
            Objects.equals(occupation, that.occupation) &&
            Objects.equals(annualIncome, that.annualIncome) &&
            Objects.equals(netWorth, that.netWorth) &&
            Objects.equals(networthDeclarationDate, that.networthDeclarationDate) &&
            Objects.equals(pepRelative, that.pepRelative) &&
            Objects.equals(pmlaRiskCategory, that.pmlaRiskCategory) &&
            Objects.equals(pmlaRiskCategoryReason, that.pmlaRiskCategoryReason) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        educationQualification,
        occupation,
        annualIncome,
        netWorth,
        networthDeclarationDate,
        pepRelative,
        pmlaRiskCategory,
        pmlaRiskCategoryReason,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "InvestmentPotentialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (educationQualification != null ? "educationQualification=" + educationQualification + ", " : "") +
                (occupation != null ? "occupation=" + occupation + ", " : "") +
                (annualIncome != null ? "annualIncome=" + annualIncome + ", " : "") +
                (netWorth != null ? "netWorth=" + netWorth + ", " : "") +
                (networthDeclarationDate != null ? "networthDeclarationDate=" + networthDeclarationDate + ", " : "") +
                (pepRelative != null ? "pepRelative=" + pepRelative + ", " : "") +
                (pmlaRiskCategory != null ? "pmlaRiskCategory=" + pmlaRiskCategory + ", " : "") +
                (pmlaRiskCategoryReason != null ? "pmlaRiskCategoryReason=" + pmlaRiskCategoryReason + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
