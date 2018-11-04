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
 * Criteria class for the PersonalInformation entity. This class is used in PersonalInformationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /personal-informations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonalInformationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fatherName;

    private StringFilter motherName;

    private StringFilter gender;

    private StringFilter nationality;

    private StringFilter maritalStatus;

    private StringFilter indianTaxPayer;

    private StringFilter birthCountry;

    private StringFilter birthCity;

    private StringFilter jurisdictionCountry;

    private StringFilter taxIdentificationNo;

    private LongFilter applicationProspectId;

    public PersonalInformationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getIndianTaxPayer() {
        return indianTaxPayer;
    }

    public void setIndianTaxPayer(StringFilter indianTaxPayer) {
        this.indianTaxPayer = indianTaxPayer;
    }

    public StringFilter getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(StringFilter birthCountry) {
        this.birthCountry = birthCountry;
    }

    public StringFilter getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(StringFilter birthCity) {
        this.birthCity = birthCity;
    }

    public StringFilter getJurisdictionCountry() {
        return jurisdictionCountry;
    }

    public void setJurisdictionCountry(StringFilter jurisdictionCountry) {
        this.jurisdictionCountry = jurisdictionCountry;
    }

    public StringFilter getTaxIdentificationNo() {
        return taxIdentificationNo;
    }

    public void setTaxIdentificationNo(StringFilter taxIdentificationNo) {
        this.taxIdentificationNo = taxIdentificationNo;
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
        final PersonalInformationCriteria that = (PersonalInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(indianTaxPayer, that.indianTaxPayer) &&
            Objects.equals(birthCountry, that.birthCountry) &&
            Objects.equals(birthCity, that.birthCity) &&
            Objects.equals(jurisdictionCountry, that.jurisdictionCountry) &&
            Objects.equals(taxIdentificationNo, that.taxIdentificationNo) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fatherName,
        motherName,
        gender,
        nationality,
        maritalStatus,
        indianTaxPayer,
        birthCountry,
        birthCity,
        jurisdictionCountry,
        taxIdentificationNo,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "PersonalInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
                (motherName != null ? "motherName=" + motherName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (indianTaxPayer != null ? "indianTaxPayer=" + indianTaxPayer + ", " : "") +
                (birthCountry != null ? "birthCountry=" + birthCountry + ", " : "") +
                (birthCity != null ? "birthCity=" + birthCity + ", " : "") +
                (jurisdictionCountry != null ? "jurisdictionCountry=" + jurisdictionCountry + ", " : "") +
                (taxIdentificationNo != null ? "taxIdentificationNo=" + taxIdentificationNo + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
