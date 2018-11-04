package com.eagle.kyc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TokenVerification.
 */
@Entity
@Table(name = "token_verification")
public class TokenVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "mobile_no", length = 10, nullable = false)
    private String mobileNo;

    @Size(min = 6, max = 6)
    @Column(name = "token", length = 6)
    private String token;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public TokenVerification mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getToken() {
        return token;
    }

    public TokenVerification token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public TokenVerification status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        TokenVerification tokenVerification = (TokenVerification) o;
        if (tokenVerification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tokenVerification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TokenVerification{" +
            "id=" + getId() +
            ", mobileNo='" + getMobileNo() + "'" +
            ", token='" + getToken() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
