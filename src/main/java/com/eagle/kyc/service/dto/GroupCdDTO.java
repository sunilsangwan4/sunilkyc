package com.eagle.kyc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GroupCd entity.
 */
public class GroupCdDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    private String groupCd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCd() {
        return groupCd;
    }

    public void setGroupCd(String groupCd) {
        this.groupCd = groupCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupCdDTO groupCdDTO = (GroupCdDTO) o;
        if (groupCdDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupCdDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupCdDTO{" +
            "id=" + getId() +
            ", groupCd='" + getGroupCd() + "'" +
            "}";
    }
}
