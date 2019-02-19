package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import com.compsis.domain.enumeration.VehicleStatus;

/**
 * A DTO for the Vehicle entity.
 */
@ApiModel(description = "Veiculo")
public class VehicleDTO implements Serializable {

    private Long id;

    private String color;

    private String manufacturer;

    private String model;

    private String plate;

    private VehicleStatus vehicleStatus;


    private Long idVehicleClassId;

    private Long idMediaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Long getIdVehicleClassId() {
        return idVehicleClassId;
    }

    public void setIdVehicleClassId(Long vehicleClassId) {
        this.idVehicleClassId = vehicleClassId;
    }

    public Long getIdMediaId() {
        return idMediaId;
    }

    public void setIdMediaId(Long mediaId) {
        this.idMediaId = mediaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", model='" + getModel() + "'" +
            ", plate='" + getPlate() + "'" +
            ", vehicleStatus='" + getVehicleStatus() + "'" +
            ", idVehicleClass=" + getIdVehicleClassId() +
            ", idMedia=" + getIdMediaId() +
            "}";
    }
}
