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
    @JsonIgnoreProperties("idAccountOperations")
    private FinancialAccount financialAccount;

    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataChange> idDataChanges = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InformativeOperation> idInformativeOperations = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BalanceCalculation> idBalanceCalculations = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operator> idOperators = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccountTransction> idAccountTransctions = new HashSet<>();
    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AutomaticOperation> idAutomaticOperations = new HashSet<>();
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

    public Set<DataChange> getIdDataChanges() {
        return idDataChanges;
    }

    public AccountOperation idDataChanges(Set<DataChange> dataChanges) {
        this.idDataChanges = dataChanges;
        return this;
    }

    public AccountOperation addIdDataChange(DataChange dataChange) {
        this.idDataChanges.add(dataChange);
        dataChange.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdDataChange(DataChange dataChange) {
        this.idDataChanges.remove(dataChange);
        dataChange.setAccountOperation(null);
        return this;
    }

    public void setIdDataChanges(Set<DataChange> dataChanges) {
        this.idDataChanges = dataChanges;
    }

    public Set<InformativeOperation> getIdInformativeOperations() {
        return idInformativeOperations;
    }

    public AccountOperation idInformativeOperations(Set<InformativeOperation> informativeOperations) {
        this.idInformativeOperations = informativeOperations;
        return this;
    }

    public AccountOperation addIdInformativeOperation(InformativeOperation informativeOperation) {
        this.idInformativeOperations.add(informativeOperation);
        informativeOperation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdInformativeOperation(InformativeOperation informativeOperation) {
        this.idInformativeOperations.remove(informativeOperation);
        informativeOperation.setAccountOperation(null);
        return this;
    }

    public void setIdInformativeOperations(Set<InformativeOperation> informativeOperations) {
        this.idInformativeOperations = informativeOperations;
    }

    public Set<BalanceCalculation> getIdBalanceCalculations() {
        return idBalanceCalculations;
    }

    public AccountOperation idBalanceCalculations(Set<BalanceCalculation> balanceCalculations) {
        this.idBalanceCalculations = balanceCalculations;
        return this;
    }

    public AccountOperation addIdBalanceCalculation(BalanceCalculation balanceCalculation) {
        this.idBalanceCalculations.add(balanceCalculation);
        balanceCalculation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdBalanceCalculation(BalanceCalculation balanceCalculation) {
        this.idBalanceCalculations.remove(balanceCalculation);
        balanceCalculation.setAccountOperation(null);
        return this;
    }

    public void setIdBalanceCalculations(Set<BalanceCalculation> balanceCalculations) {
        this.idBalanceCalculations = balanceCalculations;
    }

    public Set<Operator> getIdOperators() {
        return idOperators;
    }

    public AccountOperation idOperators(Set<Operator> operators) {
        this.idOperators = operators;
        return this;
    }

    public AccountOperation addIdOperator(Operator operator) {
        this.idOperators.add(operator);
        operator.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdOperator(Operator operator) {
        this.idOperators.remove(operator);
        operator.setAccountOperation(null);
        return this;
    }

    public void setIdOperators(Set<Operator> operators) {
        this.idOperators = operators;
    }

    public Set<AccountTransction> getIdAccountTransctions() {
        return idAccountTransctions;
    }

    public AccountOperation idAccountTransctions(Set<AccountTransction> accountTransctions) {
        this.idAccountTransctions = accountTransctions;
        return this;
    }

    public AccountOperation addIdAccountTransction(AccountTransction accountTransction) {
        this.idAccountTransctions.add(accountTransction);
        accountTransction.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdAccountTransction(AccountTransction accountTransction) {
        this.idAccountTransctions.remove(accountTransction);
        accountTransction.setAccountOperation(null);
        return this;
    }

    public void setIdAccountTransctions(Set<AccountTransction> accountTransctions) {
        this.idAccountTransctions = accountTransctions;
    }

    public Set<AutomaticOperation> getIdAutomaticOperations() {
        return idAutomaticOperations;
    }

    public AccountOperation idAutomaticOperations(Set<AutomaticOperation> automaticOperations) {
        this.idAutomaticOperations = automaticOperations;
        return this;
    }

    public AccountOperation addIdAutomaticOperation(AutomaticOperation automaticOperation) {
        this.idAutomaticOperations.add(automaticOperation);
        automaticOperation.setAccountOperation(this);
        return this;
    }

    public AccountOperation removeIdAutomaticOperation(AutomaticOperation automaticOperation) {
        this.idAutomaticOperations.remove(automaticOperation);
        automaticOperation.setAccountOperation(null);
        return this;
    }

    public void setIdAutomaticOperations(Set<AutomaticOperation> automaticOperations) {
        this.idAutomaticOperations = automaticOperations;
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
