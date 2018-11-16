package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApplicationProspect entity.
 */
public class ApplicationProspectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 32)
    private String fullName;

    @NotNull
    @Size(min = 10, max = 10)
    private String mobileNo;

    @NotNull
    @Size(min = 5, max = 32)
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 8, max = 20)
    private String confirmPassword;

    private Long personalInformationId;

    private Long investmentPotentialId;

    private Long nomineeId;

    private Long tradingInfoId;

    private Long depositoryId;

    private Long identityVerificationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getPersonalInformationId() {
        return personalInformationId;
    }

    public void setPersonalInformationId(Long personalInformationId) {
        this.personalInformationId = personalInformationId;
    }

    public Long getInvestmentPotentialId() {
        return investmentPotentialId;
    }

    public void setInvestmentPotentialId(Long investmentPotentialId) {
        this.investmentPotentialId = investmentPotentialId;
    }

    public Long getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(Long nomineeId) {
        this.nomineeId = nomineeId;
    }

    public Long getTradingInfoId() {
        return tradingInfoId;
    }

    public void setTradingInfoId(Long tradingInfoId) {
        this.tradingInfoId = tradingInfoId;
    }

    public Long getDepositoryId() {
        return depositoryId;
    }

    public void setDepositoryId(Long depositoryInfoId) {
        this.depositoryId = depositoryInfoId;
    }

    public Long getIdentityVerificationId() {
        return identityVerificationId;
    }

    public void setIdentityVerificationId(Long identityVerificationId) {
        this.identityVerificationId = identityVerificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationProspectDTO applicationProspectDTO = (ApplicationProspectDTO) o;
        if (applicationProspectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationProspectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationProspectDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", confirmPassword='" + getConfirmPassword() + "'" +
            ", personalInformation=" + getPersonalInformationId() +
            ", investmentPotential=" + getInvestmentPotentialId() +
            ", nominee=" + getNomineeId() +
            ", tradingInfo=" + getTradingInfoId() +
            ", depository=" + getDepositoryId() +
            ", identityVerification=" + getIdentityVerificationId() +
            "}";
    }
}
