package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.compsis.domain.enumeration.OperatorStatus;

/**
 * A Operator.
 */
@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator_status")
    private OperatorStatus operatorStatus;

    @ManyToOne
    @JsonIgnoreProperties("idOperators")
    private AccountOperation accountOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Operator email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Operator name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Operator password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OperatorStatus getOperatorStatus() {
        return operatorStatus;
    }

    public Operator operatorStatus(OperatorStatus operatorStatus) {
        this.operatorStatus = operatorStatus;
        return this;
    }

    public void setOperatorStatus(OperatorStatus operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public AccountOperation getAccountOperation() {
        return accountOperation;
    }

    public Operator accountOperation(AccountOperation accountOperation) {
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
        Operator operator = (Operator) o;
        if (operator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", operatorStatus='" + getOperatorStatus() + "'" +
            "}";
    }
}
