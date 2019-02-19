package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.compsis.domain.enumeration.FinancialAccountStatus;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "financial_account_status")
    private FinancialAccountStatus financialAccountStatus;

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

    public FinancialAccountStatus getFinancialAccountStatus() {
        return financialAccountStatus;
    }

    public FinancialAccount financialAccountStatus(FinancialAccountStatus financialAccountStatus) {
        this.financialAccountStatus = financialAccountStatus;
        return this;
    }

    public void setFinancialAccountStatus(FinancialAccountStatus financialAccountStatus) {
        this.financialAccountStatus = financialAccountStatus;
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
            ", financialAccountStatus='" + getFinancialAccountStatus() + "'" +
            "}";
    }
}
