package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.compsis.domain.enumeration.AutomaticOperationStatus;

import com.compsis.domain.enumeration.AutomaticEvent;

import com.compsis.domain.enumeration.TransactionType;

/**
 * A AutomaticOperation.
 */
@Entity
@Table(name = "automatic_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutomaticOperation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "recurrent")
    private Boolean recurrent;

    @Enumerated(EnumType.STRING)
    @Column(name = "automatict_operation_status")
    private AutomaticOperationStatus automatictOperationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "automatict_event")
    private AutomaticEvent automatictEvent;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AutomaticOperation amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isRecurrent() {
        return recurrent;
    }

    public AutomaticOperation recurrent(Boolean recurrent) {
        this.recurrent = recurrent;
        return this;
    }

    public void setRecurrent(Boolean recurrent) {
        this.recurrent = recurrent;
    }

    public AutomaticOperationStatus getAutomatictOperationStatus() {
        return automatictOperationStatus;
    }

    public AutomaticOperation automatictOperationStatus(AutomaticOperationStatus automatictOperationStatus) {
        this.automatictOperationStatus = automatictOperationStatus;
        return this;
    }

    public void setAutomatictOperationStatus(AutomaticOperationStatus automatictOperationStatus) {
        this.automatictOperationStatus = automatictOperationStatus;
    }

    public AutomaticEvent getAutomatictEvent() {
        return automatictEvent;
    }

    public AutomaticOperation automatictEvent(AutomaticEvent automatictEvent) {
        this.automatictEvent = automatictEvent;
        return this;
    }

    public void setAutomatictEvent(AutomaticEvent automatictEvent) {
        this.automatictEvent = automatictEvent;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public AutomaticOperation transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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
        AutomaticOperation automaticOperation = (AutomaticOperation) o;
        if (automaticOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automaticOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomaticOperation{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", recurrent='" + isRecurrent() + "'" +
            ", automatictOperationStatus='" + getAutomatictOperationStatus() + "'" +
            ", automatictEvent='" + getAutomatictEvent() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            "}";
    }
}
