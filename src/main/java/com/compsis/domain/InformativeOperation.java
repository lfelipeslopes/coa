package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Comentários do usuário
 */
@Entity
@Table(name = "informative_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InformativeOperation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties("idInformativeOperations")
    private AccountOperation accountOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public InformativeOperation comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public AccountOperation getAccountOperation() {
        return accountOperation;
    }

    public InformativeOperation accountOperation(AccountOperation accountOperation) {
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
        InformativeOperation informativeOperation = (InformativeOperation) o;
        if (informativeOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), informativeOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InformativeOperation{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
