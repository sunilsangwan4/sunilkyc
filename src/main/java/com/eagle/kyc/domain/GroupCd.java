package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupCd.
 */
@Entity
@Table(name = "group_cd")
public class GroupCd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "group_cd", length = 10, nullable = false)
    private String groupCd;

    @OneToMany(mappedBy = "groupCd")
    private Set<TypeCd> typeCds = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCd() {
        return groupCd;
    }

    public GroupCd groupCd(String groupCd) {
        this.groupCd = groupCd;
        return this;
    }

    public void setGroupCd(String groupCd) {
        this.groupCd = groupCd;
    }

    public Set<TypeCd> getTypeCds() {
        return typeCds;
    }

    public GroupCd typeCds(Set<TypeCd> typeCds) {
        this.typeCds = typeCds;
        return this;
    }

    public GroupCd addTypeCd(TypeCd typeCd) {
        this.typeCds.add(typeCd);
        typeCd.setGroupCd(this);
        return this;
    }

    public GroupCd removeTypeCd(TypeCd typeCd) {
        this.typeCds.remove(typeCd);
        typeCd.setGroupCd(null);
        return this;
    }

    public void setTypeCds(Set<TypeCd> typeCds) {
        this.typeCds = typeCds;
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
        GroupCd groupCd = (GroupCd) o;
        if (groupCd.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupCd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupCd{" +
            "id=" + getId() +
            ", groupCd='" + getGroupCd() + "'" +
            "}";
    }
}
