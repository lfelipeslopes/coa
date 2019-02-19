package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehicleAccount entity.
 */
@ApiModel(description = "Conta do veiculo")
public class VehicleAccountDTO implements Serializable {

    private Long id;


    private Long idVehicleClassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVehicleClassId() {
        return idVehicleClassId;
    }

    public void setIdVehicleClassId(Long vehicleId) {
        this.idVehicleClassId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleAccountDTO vehicleAccountDTO = (VehicleAccountDTO) o;
        if (vehicleAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleAccountDTO{" +
            "id=" + getId() +
            ", idVehicleClass=" + getIdVehicleClassId() +
            "}";
    }
}
