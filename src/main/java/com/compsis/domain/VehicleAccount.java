package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.compsis.domain.enumeration.VehicleStatus;

/**
 * Conta do veiculo
 */
@Entity
@Table(name = "vehicle_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status")
    private VehicleStatus vehicleStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicle idVehicle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public VehicleAccount vehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
        return this;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Vehicle getIdVehicle() {
        return idVehicle;
    }

    public VehicleAccount idVehicle(Vehicle vehicle) {
        this.idVehicle = vehicle;
        return this;
    }

    public void setIdVehicle(Vehicle vehicle) {
        this.idVehicle = vehicle;
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
        VehicleAccount vehicleAccount = (VehicleAccount) o;
        if (vehicleAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleAccount{" +
            "id=" + getId() +
            ", vehicleStatus='" + getVehicleStatus() + "'" +
            "}";
    }
}
