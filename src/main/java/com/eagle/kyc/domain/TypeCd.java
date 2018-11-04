package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeCd.
 */
@Entity
@Table(name = "type_cd")
public class TypeCd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "type_cd", length = 20, nullable = false)
    private String typeCd;

    @NotNull
    @Size(max = 250)
    @Column(name = "type_description", length = 250, nullable = false)
    private String typeDescription;

    @ManyToOne
    @JsonIgnoreProperties("typeCds")
    private GroupCd groupCd;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public TypeCd typeCd(String typeCd) {
        this.typeCd = typeCd;
        return this;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public TypeCd typeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
        return this;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public GroupCd getGroupCd() {
        return groupCd;
    }

    public TypeCd groupCd(GroupCd groupCd) {
        this.groupCd = groupCd;
        return this;
    }

    public void setGroupCd(GroupCd groupCd) {
        this.groupCd = groupCd;
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
        TypeCd typeCd = (TypeCd) o;
        if (typeCd.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCd{" +
            "id=" + getId() +
            ", typeCd='" + getTypeCd() + "'" +
            ", typeDescription='" + getTypeDescription() + "'" +
            "}";
    }
}
