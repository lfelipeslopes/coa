package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "accountOperation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operator> idOperators = new HashSet<>();
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
