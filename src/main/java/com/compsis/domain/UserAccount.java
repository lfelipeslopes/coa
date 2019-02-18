package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Conta Usuário
 */
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("idUserAccounts")
    private FinancialAccount financialAccount;

    @ManyToOne
    @JsonIgnoreProperties("userAccounts")
    private Person accountable;

    @ManyToOne
    @JsonIgnoreProperties("userAccounts")
    private Person contacts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public UserAccount financialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
        return this;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    public Person getAccountable() {
        return accountable;
    }

    public UserAccount accountable(Person person) {
        this.accountable = person;
        return this;
    }

    public void setAccountable(Person person) {
        this.accountable = person;
    }

    public Person getContacts() {
        return contacts;
    }

    public UserAccount contacts(Person person) {
        this.contacts = person;
        return this;
    }

    public void setContacts(Person person) {
        this.contacts = person;
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
        UserAccount userAccount = (UserAccount) o;
        if (userAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            "}";
    }
}
