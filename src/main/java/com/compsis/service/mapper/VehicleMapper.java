package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {


    @Mapping(target = "idMedias", ignore = true)
    @Mapping(target = "idVehicleClasses", ignore = true)
    @Mapping(target = "idPassages", ignore = true)
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
