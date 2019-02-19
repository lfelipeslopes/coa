package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classificação do veículo
 */
@Entity
@Table(name = "vehicle_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleClass implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "axes")
    private Integer axes;

    @Column(name = "description")
    private String description;

    @Column(name = "double_wheel")
    private Integer doubleWheel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAxes() {
        return axes;
    }

    public VehicleClass axes(Integer axes) {
        this.axes = axes;
        return this;
    }

    public void setAxes(Integer axes) {
        this.axes = axes;
    }

    public String getDescription() {
        return description;
    }

    public VehicleClass description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDoubleWheel() {
        return doubleWheel;
    }

    public VehicleClass doubleWheel(Integer doubleWheel) {
        this.doubleWheel = doubleWheel;
        return this;
    }

    public void setDoubleWheel(Integer doubleWheel) {
        this.doubleWheel = doubleWheel;
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
        VehicleClass vehicleClass = (VehicleClass) o;
        if (vehicleClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleClass.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleClass{" +
            "id=" + getId() +
            ", axes=" + getAxes() +
            ", description='" + getDescription() + "'" +
            ", doubleWheel=" + getDoubleWheel() +
            "}";
    }
}
