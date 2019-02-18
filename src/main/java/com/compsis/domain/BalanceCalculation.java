package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Balanço
 */
@Entity
@Table(name = "balance_calculation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BalanceCalculation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JsonIgnoreProperties("idBalanceCalculations")
    private AccountOperation accountOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BalanceCalculation balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountOperation getAccountOperation() {
        return accountOperation;
    }

    public BalanceCalculation accountOperation(AccountOperation accountOperation) {
        this.accountOperation = accountOperation;
        return this;
    }

    public void setAccountOperation(AccountOperation accountOperation) {
        this.accountOperation = accountOperation;
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
        BalanceCalculation balanceCalculation = (BalanceCalculation) o;
        if (balanceCalculation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceCalculation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceCalculation{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            "}";
    }
}
