package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleClassMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "idVehicleClass.id", target = "idVehicleClassId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "idVehicleClassId", target = "idVehicleClass")
    @Mapping(target = "idVehicles", ignore = true)
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
