package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Tarifa
 */
@Entity
@Table(name = "billing_tariff")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillingTariff implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "started_in")
    private ZonedDateTime startedIn;

    @Column(name = "jhi_value", precision = 10, scale = 2)
    private BigDecimal value;

    @OneToOne
    @JoinColumn(unique = true)
    private BillingLocation idBillingLocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public BillingTariff dayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ZonedDateTime getStartedIn() {
        return startedIn;
    }

    public BillingTariff startedIn(ZonedDateTime startedIn) {
        this.startedIn = startedIn;
        return this;
    }

    public void setStartedIn(ZonedDateTime startedIn) {
        this.startedIn = startedIn;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BillingTariff value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BillingLocation getIdBillingLocation() {
        return idBillingLocation;
    }

    public BillingTariff idBillingLocation(BillingLocation billingLocation) {
        this.idBillingLocation = billingLocation;
        return this;
    }

    public void setIdBillingLocation(BillingLocation billingLocation) {
        this.idBillingLocation = billingLocation;
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
        BillingTariff billingTariff = (BillingTariff) o;
        if (billingTariff.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingTariff.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingTariff{" +
            "id=" + getId() +
            ", dayOfWeek=" + getDayOfWeek() +
            ", startedIn='" + getStartedIn() + "'" +
            ", value=" + getValue() +
            "}";
    }
}
