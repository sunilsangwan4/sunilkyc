package com.eagle.kyc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ApplicationProspect.
 */
@Entity
@Table(name = "application_prospect")
public class ApplicationProspect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 32)
    @Column(name = "full_name", length = 32, nullable = false)
    private String fullName;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "mobile_no", length = 10, nullable = false)
    private String mobileNo;

    @NotNull
    @Size(min = 5, max = 32)
    @Column(name = "email", length = 32, nullable = false)
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    @Column(name = "jhi_password", length = 20, nullable = false)
    private String password;

    @NotNull
    @Size(min = 8, max = 20)
    @Column(name = "confirm_password", length = 20, nullable = false)
    private String confirmPassword;

    @OneToOne    @JoinColumn(unique = true)
    private PersonalInformation personalInformation;

    @OneToOne    @JoinColumn(unique = true)
    private InvestmentPotential investmentPotential;

    @OneToOne    @JoinColumn(unique = true)
    private Nominee nominee;

    @OneToOne    @JoinColumn(unique = true)
    private TradingInfo tradingInfo;

    @OneToOne    @JoinColumn(unique = true)
    private DepositoryInfo depository;

    @OneToMany(mappedBy = "applicationProspect")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(mappedBy = "applicationProspect")
    private Set<BankInformation> bankInformations = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public ApplicationProspect fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public ApplicationProspect mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public ApplicationProspect email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public ApplicationProspect password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public ApplicationProspect confirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public ApplicationProspect personalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
        return this;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public InvestmentPotential getInvestmentPotential() {
        return investmentPotential;
    }

    public ApplicationProspect investmentPotential(InvestmentPotential investmentPotential) {
        this.investmentPotential = investmentPotential;
        return this;
    }

    public void setInvestmentPotential(InvestmentPotential investmentPotential) {
        this.investmentPotential = investmentPotential;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public ApplicationProspect nominee(Nominee nominee) {
        this.nominee = nominee;
        return this;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

    public TradingInfo getTradingInfo() {
        return tradingInfo;
    }

    public ApplicationProspect tradingInfo(TradingInfo tradingInfo) {
        this.tradingInfo = tradingInfo;
        return this;
    }

    public void setTradingInfo(TradingInfo tradingInfo) {
        this.tradingInfo = tradingInfo;
    }

    public DepositoryInfo getDepository() {
        return depository;
    }

    public ApplicationProspect depository(DepositoryInfo depositoryInfo) {
        this.depository = depositoryInfo;
        return this;
    }

    public void setDepository(DepositoryInfo depositoryInfo) {
        this.depository = depositoryInfo;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public ApplicationProspect addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public ApplicationProspect addAddresses(Address address) {
        this.addresses.add(address);
        address.setApplicationProspect(this);
        return this;
    }

    public ApplicationProspect removeAddresses(Address address) {
        this.addresses.remove(address);
        address.setApplicationProspect(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<BankInformation> getBankInformations() {
        return bankInformations;
    }

    public ApplicationProspect bankInformations(Set<BankInformation> bankInformations) {
        this.bankInformations = bankInformations;
        return this;
    }

    public ApplicationProspect addBankInformation(BankInformation bankInformation) {
        this.bankInformations.add(bankInformation);
        bankInformation.setApplicationProspect(this);
        return this;
    }

    public ApplicationProspect removeBankInformation(BankInformation bankInformation) {
        this.bankInformations.remove(bankInformation);
        bankInformation.setApplicationProspect(null);
        return this;
    }

    public void setBankInformations(Set<BankInformation> bankInformations) {
        this.bankInformations = bankInformations;
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
        ApplicationProspect applicationProspect = (ApplicationProspect) o;
        if (applicationProspect.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationProspect.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationProspect{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", confirmPassword='" + getConfirmPassword() + "'" +
            "}";
    }
}
