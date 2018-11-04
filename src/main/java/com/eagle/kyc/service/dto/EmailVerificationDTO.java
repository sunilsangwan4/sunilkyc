package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmailVerification entity.
 */
public class EmailVerificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String emailId;

    @Size(min = 6, max = 6)
    private String token;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailVerificationDTO emailVerificationDTO = (EmailVerificationDTO) o;
        if (emailVerificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailVerificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailVerificationDTO{" +
            "id=" + getId() +
            ", emailId='" + getEmailId() + "'" +
            ", token='" + getToken() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
