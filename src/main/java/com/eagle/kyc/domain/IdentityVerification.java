package com.eagle.kyc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A IdentityVerification.
 */
@Entity
@Table(name = "identity_verification")
public class IdentityVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 12, max = 12)
    @Column(name = "adhaar_no", length = 12, nullable = false)
    private String adhaarNo;

    @Column(name = "aadhar_no_verified")
    private Boolean aadharNoVerified;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "pan_no", length = 10, nullable = false)
    private String panNo;

    @Column(name = "pan_no_verified")
    private Boolean panNoVerified;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @OneToOne(mappedBy = "identityVerification")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public IdentityVerification adhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
        return this;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public Boolean isAadharNoVerified() {
        return aadharNoVerified;
    }

    public IdentityVerification aadharNoVerified(Boolean aadharNoVerified) {
        this.aadharNoVerified = aadharNoVerified;
        return this;
    }

    public void setAadharNoVerified(Boolean aadharNoVerified) {
        this.aadharNoVerified = aadharNoVerified;
    }

    public String getPanNo() {
        return panNo;
    }

    
    public ApplicationProspect getApplicationProspect() {
		return applicationProspect;
	}

	public void setApplicationProspect(ApplicationProspect applicationProspect) {
		this.applicationProspect = applicationProspect;
	}

	public IdentityVerification panNo(String panNo) {
        this.panNo = panNo;
        return this;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public Boolean isPanNoVerified() {
        return panNoVerified;
    }

    public IdentityVerification panNoVerified(Boolean panNoVerified) {
        this.panNoVerified = panNoVerified;
        return this;
    }

    public void setPanNoVerified(Boolean panNoVerified) {
        this.panNoVerified = panNoVerified;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public IdentityVerification dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        IdentityVerification identityVerification = (IdentityVerification) o;
        if (identityVerification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), identityVerification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdentityVerification{" +
            "id=" + getId() +
            ", adhaarNo='" + getAdhaarNo() + "'" +
            ", aadharNoVerified='" + isAadharNoVerified() + "'" +
            ", panNo='" + getPanNo() + "'" +
            ", panNoVerified='" + isPanNoVerified() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            "}";
    }
}
