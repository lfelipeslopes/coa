package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "billingLocation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillingTariff> idBillingLocations = new HashSet<>();
    @OneToMany(mappedBy = "billingLocation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Passage> idBillingLocations = new HashSet<>();
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

    public Set<BillingTariff> getIdBillingLocations() {
        return idBillingLocations;
    }

    public BillingLocation idBillingLocations(Set<BillingTariff> billingTariffs) {
        this.idBillingLocations = billingTariffs;
        return this;
    }

    public BillingLocation addIdBillingLocation(BillingTariff billingTariff) {
        this.idBillingLocations.add(billingTariff);
        billingTariff.setBillingLocation(this);
        return this;
    }

    public BillingLocation removeIdBillingLocation(BillingTariff billingTariff) {
        this.idBillingLocations.remove(billingTariff);
        billingTariff.setBillingLocation(null);
        return this;
    }

    public void setIdBillingLocations(Set<BillingTariff> billingTariffs) {
        this.idBillingLocations = billingTariffs;
    }

    public Set<Passage> getIdBillingLocations() {
        return idBillingLocations;
    }

    public BillingLocation idBillingLocations(Set<Passage> passages) {
        this.idBillingLocations = passages;
        return this;
    }

    public BillingLocation addIdBillingLocation(Passage passage) {
        this.idBillingLocations.add(passage);
        passage.setBillingLocation(this);
        return this;
    }

    public BillingLocation removeIdBillingLocation(Passage passage) {
        this.idBillingLocations.remove(passage);
        passage.setBillingLocation(null);
        return this;
    }

    public void setIdBillingLocations(Set<Passage> passages) {
        this.idBillingLocations = passages;
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
