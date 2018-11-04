package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TokenVerification entity.
 */
public class TokenVerificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    private String mobileNo;

    @Size(min = 6, max = 6)
    private String token;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

        TokenVerificationDTO tokenVerificationDTO = (TokenVerificationDTO) o;
        if (tokenVerificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tokenVerificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TokenVerificationDTO{" +
            "id=" + getId() +
            ", mobileNo='" + getMobileNo() + "'" +
            ", token='" + getToken() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
