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
 * Criteria class for the Address entity. This class is used in AddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter addressLine3;

    private StringFilter state;

    private StringFilter city;

    private StringFilter pinCode;

    private StringFilter country;

    private StringFilter addressType;

    private LongFilter applicationProspectId;

    private LongFilter nomineeId;

    public AddressCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(StringFilter addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getAddressType() {
        return addressType;
    }

    public void setAddressType(StringFilter addressType) {
        this.addressType = addressType;
    }

    public LongFilter getApplicationProspectId() {
        return applicationProspectId;
    }

    public void setApplicationProspectId(LongFilter applicationProspectId) {
        this.applicationProspectId = applicationProspectId;
    }

    public LongFilter getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(LongFilter nomineeId) {
        this.nomineeId = nomineeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressCriteria that = (AddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(addressLine3, that.addressLine3) &&
            Objects.equals(state, that.state) &&
            Objects.equals(city, that.city) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(country, that.country) &&
            Objects.equals(addressType, that.addressType) &&
            Objects.equals(applicationProspectId, that.applicationProspectId) &&
            Objects.equals(nomineeId, that.nomineeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        addressLine1,
        addressLine2,
        addressLine3,
        state,
        city,
        pinCode,
        country,
        addressType,
        applicationProspectId,
        nomineeId
        );
    }

    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
                (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
                (addressLine3 != null ? "addressLine3=" + addressLine3 + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (addressType != null ? "addressType=" + addressType + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
                (nomineeId != null ? "nomineeId=" + nomineeId + ", " : "") +
            "}";
    }

}
