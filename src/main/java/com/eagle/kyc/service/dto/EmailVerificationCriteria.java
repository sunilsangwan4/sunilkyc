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
 * Criteria class for the EmailVerification entity. This class is used in EmailVerificationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /email-verifications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmailVerificationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter emailId;

    private StringFilter token;

    private StringFilter status;

    public EmailVerificationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmailId() {
        return emailId;
    }

    public void setEmailId(StringFilter emailId) {
        this.emailId = emailId;
    }

    public StringFilter getToken() {
        return token;
    }

    public void setToken(StringFilter token) {
        this.token = token;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
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
        final EmailVerificationCriteria that = (EmailVerificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(token, that.token) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        emailId,
        token,
        status
        );
    }

    @Override
    public String toString() {
        return "EmailVerificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (emailId != null ? "emailId=" + emailId + ", " : "") +
                (token != null ? "token=" + token + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
