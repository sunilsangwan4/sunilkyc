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
 * Criteria class for the TypeCd entity. This class is used in TypeCdResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /type-cds?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeCdCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter typeCd;

    private StringFilter typeDescription;

    private LongFilter groupCdId;

    public TypeCdCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(StringFilter typeCd) {
        this.typeCd = typeCd;
    }

    public StringFilter getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(StringFilter typeDescription) {
        this.typeDescription = typeDescription;
    }

    public LongFilter getGroupCdId() {
        return groupCdId;
    }

    public void setGroupCdId(LongFilter groupCdId) {
        this.groupCdId = groupCdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeCdCriteria that = (TypeCdCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(typeCd, that.typeCd) &&
            Objects.equals(typeDescription, that.typeDescription) &&
            Objects.equals(groupCdId, that.groupCdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        typeCd,
        typeDescription,
        groupCdId
        );
    }

    @Override
    public String toString() {
        return "TypeCdCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeCd != null ? "typeCd=" + typeCd + ", " : "") +
                (typeDescription != null ? "typeDescription=" + typeDescription + ", " : "") +
                (groupCdId != null ? "groupCdId=" + groupCdId + ", " : "") +
            "}";
    }

}
