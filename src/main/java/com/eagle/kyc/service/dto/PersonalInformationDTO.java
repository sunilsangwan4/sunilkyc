package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PersonalInformation entity.
 */
public class PersonalInformationDTO implements Serializable {

    private Long id;

    @NotNull
    private String fatherName;

    @NotNull
    private String motherName;

    @NotNull
    private String gender;

    @NotNull
    private String nationality;

    @NotNull
    private String maritalStatus;

    @NotNull
    private String indianTaxPayer;

    @NotNull
    private String birthCountry;

    @NotNull
    private String birthCity;

    @NotNull
    private String jurisdictionCountry;

    @NotNull
    private String taxIdentificationNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIndianTaxPayer() {
        return indianTaxPayer;
    }

    public void setIndianTaxPayer(String indianTaxPayer) {
        this.indianTaxPayer = indianTaxPayer;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getJurisdictionCountry() {
        return jurisdictionCountry;
    }

    public void setJurisdictionCountry(String jurisdictionCountry) {
        this.jurisdictionCountry = jurisdictionCountry;
    }

    public String getTaxIdentificationNo() {
        return taxIdentificationNo;
    }

    public void setTaxIdentificationNo(String taxIdentificationNo) {
        this.taxIdentificationNo = taxIdentificationNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalInformationDTO personalInformationDTO = (PersonalInformationDTO) o;
        if (personalInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalInformationDTO{" +
            "id=" + getId() +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", indianTaxPayer='" + getIndianTaxPayer() + "'" +
            ", birthCountry='" + getBirthCountry() + "'" +
            ", birthCity='" + getBirthCity() + "'" +
            ", jurisdictionCountry='" + getJurisdictionCountry() + "'" +
            ", taxIdentificationNo='" + getTaxIdentificationNo() + "'" +
            "}";
    }
}
