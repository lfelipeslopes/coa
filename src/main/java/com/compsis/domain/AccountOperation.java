package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Operação da conta
 */
@Entity
@Table(name = "account_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountOperation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "occurrence_date")
    private ZonedDateTime occurrenceDate;

    @ManyToOne
    @JsonIgnoreProperties("ids")
    private FinancialAccount financialAccount;

    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataChange> ids = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InformativeOperation> ids = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BalanceCalculation> ids = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operator> ids = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccountTransction> ids = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AutomaticOperation> ids = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public AccountOperation occurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
        return this;
    }

    public void setOccurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public AccountOperation financialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
        return this;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    public Set<DataChange> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<DataChange> dataChanges) {
        this.ids = dataChanges;
        return this;
    }

    public AccountOperation addId(DataChange dataChange) {
        this.ids.add(dataChange);
        dataChange.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(DataChange dataChange) {
        this.ids.remove(dataChange);
        dataChange.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<DataChange> dataChanges) {
        this.ids = dataChanges;
    }

    public Set<InformativeOperation> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<InformativeOperation> informativeOperations) {
        this.ids = informativeOperations;
        return this;
    }

    public AccountOperation addId(InformativeOperation informativeOperation) {
        this.ids.add(informativeOperation);
        informativeOperation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(InformativeOperation informativeOperation) {
        this.ids.remove(informativeOperation);
        informativeOperation.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<InformativeOperation> informativeOperations) {
        this.ids = informativeOperations;
    }

    public Set<BalanceCalculation> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<BalanceCalculation> balanceCalculations) {
        this.ids = balanceCalculations;
        return this;
    }

    public AccountOperation addId(BalanceCalculation balanceCalculation) {
        this.ids.add(balanceCalculation);
        balanceCalculation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(BalanceCalculation balanceCalculation) {
        this.ids.remove(balanceCalculation);
        balanceCalculation.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<BalanceCalculation> balanceCalculations) {
        this.ids = balanceCalculations;
    }

    public Set<Operator> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<Operator> operators) {
        this.ids = operators;
        return this;
    }

    public AccountOperation addId(Operator operator) {
        this.ids.add(operator);
        operator.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(Operator operator) {
        this.ids.remove(operator);
        operator.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<Operator> operators) {
        this.ids = operators;
    }

    public Set<AccountTransction> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<AccountTransction> accountTransctions) {
        this.ids = accountTransctions;
        return this;
    }

    public AccountOperation addId(AccountTransction accountTransction) {
        this.ids.add(accountTransction);
        accountTransction.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(AccountTransction accountTransction) {
        this.ids.remove(accountTransction);
        accountTransction.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<AccountTransction> accountTransctions) {
        this.ids = accountTransctions;
    }

    public Set<AutomaticOperation> getIds() {
        return ids;
    }

    public AccountOperation ids(Set<AutomaticOperation> automaticOperations) {
        this.ids = automaticOperations;
        return this;
    }

    public AccountOperation addId(AutomaticOperation automaticOperation) {
        this.ids.add(automaticOperation);
        automaticOperation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeId(AutomaticOperation automaticOperation) {
        this.ids.remove(automaticOperation);
        automaticOperation.setAccountOperation(null);
        return this;
    }

    public void setIds(Set<AutomaticOperation> automaticOperations) {
        this.ids = automaticOperations;
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
        AccountOperation accountOperation = (AccountOperation) o;
        if (accountOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountOperation{" +
            "id=" + getId() +
            ", occurrenceDate='" + getOccurrenceDate() + "'" +
            "}";
    }
}
