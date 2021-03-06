package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Veiculo
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "plate")
    private String plate;

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleClass idVehicleClass;

    @OneToOne
    @JoinColumn(unique = true)
    private Media idMedia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public Vehicle color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Vehicle manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Vehicle model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public Vehicle plate(String plate) {
        this.plate = plate;
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public VehicleClass getIdVehicleClass() {
        return idVehicleClass;
    }

    public Vehicle idVehicleClass(VehicleClass vehicleClass) {
        this.idVehicleClass = vehicleClass;
        return this;
    }

    public void setIdVehicleClass(VehicleClass vehicleClass) {
        this.idVehicleClass = vehicleClass;
    }

    public Media getIdMedia() {
        return idMedia;
    }

    public Vehicle idMedia(Media media) {
        this.idMedia = media;
        return this;
    }

    public void setIdMedia(Media media) {
        this.idMedia = media;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", model='" + getModel() + "'" +
            ", plate='" + getPlate() + "'" +
            "}";
    }
}
