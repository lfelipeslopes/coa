package com.compsis.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleClass idVehicleClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleClass getIdVehicleClass() {
        return idVehicleClass;
    }

    public VehicleAccount idVehicleClass(VehicleClass vehicleClass) {
        this.idVehicleClass = vehicleClass;
        return this;
    }

    public void setIdVehicleClass(VehicleClass vehicleClass) {
        this.idVehicleClass = vehicleClass;
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
            "}";
    }
}
