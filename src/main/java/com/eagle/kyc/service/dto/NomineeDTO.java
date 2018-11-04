package com.eagle.kyc.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Nominee entity.
 */
public class NomineeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomineeName;

    @NotNull
    private String relationShip;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String guardianName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NomineeDTO nomineeDTO = (NomineeDTO) o;
        if (nomineeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nomineeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NomineeDTO{" +
            "id=" + getId() +
            ", nomineeName='" + getNomineeName() + "'" +
            ", relationShip='" + getRelationShip() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", guardianName='" + getGuardianName() + "'" +
            "}";
    }
}
