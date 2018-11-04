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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the IdentityVerification entity. This class is used in IdentityVerificationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /identity-verifications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IdentityVerificationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter adhaarNo;

    private BooleanFilter aadharNoVerified;

    private StringFilter panNo;

    private BooleanFilter panNoVerified;

    private LocalDateFilter dateOfBirth;

    public IdentityVerificationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(StringFilter adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public BooleanFilter getAadharNoVerified() {
        return aadharNoVerified;
    }

    public void setAadharNoVerified(BooleanFilter aadharNoVerified) {
        this.aadharNoVerified = aadharNoVerified;
    }

    public StringFilter getPanNo() {
        return panNo;
    }

    public void setPanNo(StringFilter panNo) {
        this.panNo = panNo;
    }

    public BooleanFilter getPanNoVerified() {
        return panNoVerified;
    }

    public void setPanNoVerified(BooleanFilter panNoVerified) {
        this.panNoVerified = panNoVerified;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IdentityVerificationCriteria that = (IdentityVerificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(adhaarNo, that.adhaarNo) &&
            Objects.equals(aadharNoVerified, that.aadharNoVerified) &&
            Objects.equals(panNo, that.panNo) &&
            Objects.equals(panNoVerified, that.panNoVerified) &&
            Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        adhaarNo,
        aadharNoVerified,
        panNo,
        panNoVerified,
        dateOfBirth
        );
    }

    @Override
    public String toString() {
        return "IdentityVerificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (adhaarNo != null ? "adhaarNo=" + adhaarNo + ", " : "") +
                (aadharNoVerified != null ? "aadharNoVerified=" + aadharNoVerified + ", " : "") +
                (panNo != null ? "panNo=" + panNo + ", " : "") +
                (panNoVerified != null ? "panNoVerified=" + panNoVerified + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            "}";
    }

}
