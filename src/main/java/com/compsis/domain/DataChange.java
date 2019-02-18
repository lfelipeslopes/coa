package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data alteração
 */
@Entity
@Table(name = "data_change")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataChange implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "change_detail")
    private String changeDetail;

    @ManyToOne
    @JsonIgnoreProperties("idDataChanges")
    private AccountOperation accountOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeDetail() {
        return changeDetail;
    }

    public DataChange changeDetail(String changeDetail) {
        this.changeDetail = changeDetail;
        return this;
    }

    public void setChangeDetail(String changeDetail) {
        this.changeDetail = changeDetail;
    }

    public AccountOperation getAccountOperation() {
        return accountOperation;
    }

    public DataChange accountOperation(AccountOperation accountOperation) {
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
        DataChange dataChange = (DataChange) o;
        if (dataChange.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataChange.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataChange{" +
            "id=" + getId() +
            ", changeDetail='" + getChangeDetail() + "'" +
            "}";
    }
}
