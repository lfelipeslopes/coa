package com.compsis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Passagem
 */
@Entity
@Table(name = "passage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Passage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "automatic_passage")
    private Boolean automaticPassage;

    @Column(name = "lane")
    private Integer lane;

    @Column(name = "occurrence_date")
    private ZonedDateTime occurrenceDate;

    @Column(name = "passage_identification")
    private Long passageIdentification;

    @Column(name = "plate")
    private String plate;

    @Column(name = "processed_at")
    private ZonedDateTime processedAt;

    @ManyToOne
    @JsonIgnoreProperties("idPassages")
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("passages")
    private VehicleClass classifiedClass;

    @ManyToOne
    @JsonIgnoreProperties("passages")
    private VehicleClass detectClass;

    @ManyToOne
    @JsonIgnoreProperties("passages")
    private VehicleClass chargedClass;

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

    public Passage amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isAutomaticPassage() {
        return automaticPassage;
    }

    public Passage automaticPassage(Boolean automaticPassage) {
        this.automaticPassage = automaticPassage;
        return this;
    }

    public void setAutomaticPassage(Boolean automaticPassage) {
        this.automaticPassage = automaticPassage;
    }

    public Integer getLane() {
        return lane;
    }

    public Passage lane(Integer lane) {
        this.lane = lane;
        return this;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public ZonedDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public Passage occurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
        return this;
    }

    public void setOccurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public Long getPassageIdentification() {
        return passageIdentification;
    }

    public Passage passageIdentification(Long passageIdentification) {
        this.passageIdentification = passageIdentification;
        return this;
    }

    public void setPassageIdentification(Long passageIdentification) {
        this.passageIdentification = passageIdentification;
    }

    public String getPlate() {
        return plate;
    }

    public Passage plate(String plate) {
        this.plate = plate;
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public ZonedDateTime getProcessedAt() {
        return processedAt;
    }

    public Passage processedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
        return this;
    }

    public void setProcessedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Passage vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleClass getClassifiedClass() {
        return classifiedClass;
    }

    public Passage classifiedClass(VehicleClass vehicleClass) {
        this.classifiedClass = vehicleClass;
        return this;
    }

    public void setClassifiedClass(VehicleClass vehicleClass) {
        this.classifiedClass = vehicleClass;
    }

    public VehicleClass getDetectClass() {
        return detectClass;
    }

    public Passage detectClass(VehicleClass vehicleClass) {
        this.detectClass = vehicleClass;
        return this;
    }

    public void setDetectClass(VehicleClass vehicleClass) {
        this.detectClass = vehicleClass;
    }

    public VehicleClass getChargedClass() {
        return chargedClass;
    }

    public Passage chargedClass(VehicleClass vehicleClass) {
        this.chargedClass = vehicleClass;
        return this;
    }

    public void setChargedClass(VehicleClass vehicleClass) {
        this.chargedClass = vehicleClass;
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
        Passage passage = (Passage) o;
        if (passage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), passage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Passage{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", automaticPassage='" + isAutomaticPassage() + "'" +
            ", lane=" + getLane() +
            ", occurrenceDate='" + getOccurrenceDate() + "'" +
            ", passageIdentification=" + getPassageIdentification() +
            ", plate='" + getPlate() + "'" +
            ", processedAt='" + getProcessedAt() + "'" +
            "}";
    }
}
