package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Localização (praça)
 */
@Entity
@Table(name = "billing_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillingLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(unique = true)
    private Passage id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public BillingLocation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public BillingLocation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Passage getId() {
        return id;
    }

    public BillingLocation id(Passage passage) {
        this.id = passage;
        return this;
    }

    public void setId(Passage passage) {
        this.id = passage;
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
        BillingLocation billingLocation = (BillingLocation) o;
        if (billingLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingLocation{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
