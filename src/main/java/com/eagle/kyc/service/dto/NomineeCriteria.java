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
 * Criteria class for the Nominee entity. This class is used in NomineeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nominees?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NomineeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomineeName;

    private StringFilter relationShip;

    private LocalDateFilter dateOfBirth;

    private StringFilter guardianName;

    private LongFilter addressId;

    private LongFilter applicationProspectId;

    public NomineeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(StringFilter nomineeName) {
        this.nomineeName = nomineeName;
    }

    public StringFilter getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(StringFilter relationShip) {
        this.relationShip = relationShip;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(StringFilter guardianName) {
        this.guardianName = guardianName;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getApplicationProspectId() {
        return applicationProspectId;
    }

    public void setApplicationProspectId(LongFilter applicationProspectId) {
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
        final NomineeCriteria that = (NomineeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomineeName, that.nomineeName) &&
            Objects.equals(relationShip, that.relationShip) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(guardianName, that.guardianName) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(applicationProspectId, that.applicationProspectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomineeName,
        relationShip,
        dateOfBirth,
        guardianName,
        addressId,
        applicationProspectId
        );
    }

    @Override
    public String toString() {
        return "NomineeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomineeName != null ? "nomineeName=" + nomineeName + ", " : "") +
                (relationShip != null ? "relationShip=" + relationShip + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (guardianName != null ? "guardianName=" + guardianName + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (applicationProspectId != null ? "applicationProspectId=" + applicationProspectId + ", " : "") +
            "}";
    }

}
