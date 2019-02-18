package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Conta
 */
@Entity
@Table(name = "financial_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinancialAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "financialAccount")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAccount> ids = new HashSet<>();
    @OneToMany(mappedBy = "financialAccount")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VehicleAccount> ids = new HashSet<>();
    @OneToMany(mappedBy = "financialAccount")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccountOperation> ids = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public FinancialAccount alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public FinancialAccount balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<UserAccount> getIds() {
        return ids;
    }

    public FinancialAccount ids(Set<UserAccount> userAccounts) {
        this.ids = userAccounts;
        return this;
    }

    public FinancialAccount addId(UserAccount userAccount) {
        this.ids.add(userAccount);
        userAccount.setFinancialAccount(this);
        return this;
    }

    public FinancialAccount removeId(UserAccount userAccount) {
        this.ids.remove(userAccount);
        userAccount.setFinancialAccount(null);
        return this;
    }

    public void setIds(Set<UserAccount> userAccounts) {
        this.ids = userAccounts;
    }

    public Set<VehicleAccount> getIds() {
        return ids;
    }

    public FinancialAccount ids(Set<VehicleAccount> vehicleAccounts) {
        this.ids = vehicleAccounts;
        return this;
    }

    public FinancialAccount addId(VehicleAccount vehicleAccount) {
        this.ids.add(vehicleAccount);
        vehicleAccount.setFinancialAccount(this);
        return this;
    }

    public FinancialAccount removeId(VehicleAccount vehicleAccount) {
        this.ids.remove(vehicleAccount);
        vehicleAccount.setFinancialAccount(null);
        return this;
    }

    public void setIds(Set<VehicleAccount> vehicleAccounts) {
        this.ids = vehicleAccounts;
    }

    public Set<AccountOperation> getIds() {
        return ids;
    }

    public FinancialAccount ids(Set<AccountOperation> accountOperations) {
        this.ids = accountOperations;
        return this;
    }

    public FinancialAccount addId(AccountOperation accountOperation) {
        this.ids.add(accountOperation);
        accountOperation.setFinancialAccount(this);
        return this;
    }

    public FinancialAccount removeId(AccountOperation accountOperation) {
        this.ids.remove(accountOperation);
        accountOperation.setFinancialAccount(null);
        return this;
    }

    public void setIds(Set<AccountOperation> accountOperations) {
        this.ids = accountOperations;
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
        FinancialAccount financialAccount = (FinancialAccount) o;
        if (financialAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialAccount{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
