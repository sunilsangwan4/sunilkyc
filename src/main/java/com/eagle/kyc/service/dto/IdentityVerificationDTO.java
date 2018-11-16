package com.eagle.kyc.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the IdentityVerification entity.
 */
public class IdentityVerificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 12, max = 12)
    private String adhaarNo;

    private Boolean aadharNoVerified;

    @NotNull
    @Size(min = 10, max = 10)
    private String panNo;

    private Boolean panNoVerified;

    @NotNull
    private LocalDate dateOfBirth;
    
    private Long applicationProspectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public Boolean isAadharNoVerified() {
        return aadharNoVerified;
    }

    public void setAadharNoVerified(Boolean aadharNoVerified) {
        this.aadharNoVerified = aadharNoVerified;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public Boolean isPanNoVerified() {
        return panNoVerified;
    }

    public void setPanNoVerified(Boolean panNoVerified) {
        this.panNoVerified = panNoVerified;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    public Long getApplicationProspectId() {
		return applicationProspectId;
	}

	public void setApplicationProspectId(Long applicationProspectId) {
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

        IdentityVerificationDTO identityVerificationDTO = (IdentityVerificationDTO) o;
        if (identityVerificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), identityVerificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdentityVerificationDTO{" +
            "id=" + getId() +
            ", adhaarNo='" + getAdhaarNo() + "'" +
            ", aadharNoVerified='" + isAadharNoVerified() + "'" +
            ", panNo='" + getPanNo() + "'" +
            ", panNoVerified='" + isPanNoVerified() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            "}";
    }
}
