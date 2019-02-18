package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehicleClass entity.
 */
@ApiModel(description = "Classificação do veículo")
public class VehicleClassDTO implements Serializable {

    private Long id;

    private Integer axes;

    private String description;

    private Integer doubleWheel;


    private Long vehicleId;

    private Long idBillingTariffId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAxes() {
        return axes;
    }

    public void setAxes(Integer axes) {
        this.axes = axes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDoubleWheel() {
        return doubleWheel;
    }

    public void setDoubleWheel(Integer doubleWheel) {
        this.doubleWheel = doubleWheel;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getIdBillingTariffId() {
        return idBillingTariffId;
    }

    public void setIdBillingTariffId(Long billingTariffId) {
        this.idBillingTariffId = billingTariffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleClassDTO vehicleClassDTO = (VehicleClassDTO) o;
        if (vehicleClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleClassDTO{" +
            "id=" + getId() +
            ", axes=" + getAxes() +
            ", description='" + getDescription() + "'" +
            ", doubleWheel=" + getDoubleWheel() +
            ", vehicle=" + getVehicleId() +
            ", idBillingTariff=" + getIdBillingTariffId() +
            "}";
    }
}
