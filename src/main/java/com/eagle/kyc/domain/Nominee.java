package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Nominee.
 */
@Entity
@Table(name = "nominee")
public class Nominee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nominee_name", nullable = false)
    private String nomineeName;

    @NotNull
    @Column(name = "relation_ship", nullable = false)
    private String relationShip;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @OneToMany(mappedBy = "nominee")
    private Set<Address> addresses = new HashSet<>();
    @OneToOne(mappedBy = "nominee")
    @JsonIgnore
    private ApplicationProspect applicationProspect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public Nominee nomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
        return this;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public Nominee relationShip(String relationShip) {
        this.relationShip = relationShip;
        return this;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Nominee dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public Nominee guardianName(String guardianName) {
        this.guardianName = guardianName;
        return this;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Nominee addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Nominee addAddress(Address address) {
        this.addresses.add(address);
        address.setNominee(this);
        return this;
    }

    public Nominee removeAddress(Address address) {
        this.addresses.remove(address);
        address.setNominee(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public ApplicationProspect getApplicationProspect() {
        return applicationProspect;
    }

    public Nominee applicationProspect(ApplicationProspect applicationProspect) {
        this.applicationProspect = applicationProspect;
        return this;
    }

    public void setApplicationProspect(ApplicationProspect applicationProspect) {
        this.applicationProspect = applicationProspect;
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
        Nominee nominee = (Nominee) o;
        if (nominee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nominee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Nominee{" +
            "id=" + getId() +
            ", nomineeName='" + getNomineeName() + "'" +
            ", relationShip='" + getRelationShip() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", guardianName='" + getGuardianName() + "'" +
            "}";
    }
}
