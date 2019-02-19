package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleClassMapper.class, MediaMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "idVehicleClass.id", target = "idVehicleClassId")
    @Mapping(source = "idMedia.id", target = "idMediaId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "idVehicleClassId", target = "idVehicleClass")
    @Mapping(source = "idMediaId", target = "idMedia")
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
