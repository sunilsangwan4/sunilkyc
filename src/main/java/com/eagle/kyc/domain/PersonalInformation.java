package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonalInformation.
 */
@Entity
@Table(name = "personal_information")
public class PersonalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @NotNull
    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @NotNull
    @Column(name = "indian_tax_payer", nullable = false)
    private String indianTaxPayer;

    @NotNull
    @Column(name = "birth_country", nullable = false)
    private String birthCountry;

    @NotNull
    @Column(name = "birth_city", nullable = false)
    private String birthCity;

    @NotNull
    @Column(name = "jurisdiction_country", nullable = false)
    private String jurisdictionCountry;

    @NotNull
    @Column(name = "tax_identification_no", nullable = false)
    private String taxIdentificationNo;

    @OneToOne(mappedBy = "personalInformation")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFatherName() {
        return fatherName;
    }

    public PersonalInformation fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public PersonalInformation motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public PersonalInformation gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public PersonalInformation nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public PersonalInformation maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIndianTaxPayer() {
        return indianTaxPayer;
    }

    public PersonalInformation indianTaxPayer(String indianTaxPayer) {
        this.indianTaxPayer = indianTaxPayer;
        return this;
    }

    public void setIndianTaxPayer(String indianTaxPayer) {
        this.indianTaxPayer = indianTaxPayer;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public PersonalInformation birthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
        return this;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public PersonalInformation birthCity(String birthCity) {
        this.birthCity = birthCity;
        return this;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getJurisdictionCountry() {
        return jurisdictionCountry;
    }

    public PersonalInformation jurisdictionCountry(String jurisdictionCountry) {
        this.jurisdictionCountry = jurisdictionCountry;
        return this;
    }

    public void setJurisdictionCountry(String jurisdictionCountry) {
        this.jurisdictionCountry = jurisdictionCountry;
    }

    public String getTaxIdentificationNo() {
        return taxIdentificationNo;
    }

    public PersonalInformation taxIdentificationNo(String taxIdentificationNo) {
        this.taxIdentificationNo = taxIdentificationNo;
        return this;
    }

    public void setTaxIdentificationNo(String taxIdentificationNo) {
        this.taxIdentificationNo = taxIdentificationNo;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public PersonalInformation applicationProspect(ApplicationProspect applicationProspect) {
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
        PersonalInformation personalInformation = (PersonalInformation) o;
        if (personalInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
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
