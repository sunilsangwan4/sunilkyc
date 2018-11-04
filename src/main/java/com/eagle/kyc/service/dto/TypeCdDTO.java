package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeCd entity.
 */
public class TypeCdDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String typeCd;

    @NotNull
    @Size(max = 250)
    private String typeDescription;

    private Long groupCdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Long getGroupCdId() {
        return groupCdId;
    }

    public void setGroupCdId(Long groupCdId) {
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

        TypeCdDTO typeCdDTO = (TypeCdDTO) o;
        if (typeCdDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCdDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCdDTO{" +
            "id=" + getId() +
            ", typeCd='" + getTypeCd() + "'" +
            ", typeDescription='" + getTypeDescription() + "'" +
            ", groupCd=" + getGroupCdId() +
            "}";
    }
}
