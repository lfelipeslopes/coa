package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleClass and its DTO VehicleClassDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface VehicleClassMapper extends EntityMapper<VehicleClassDTO, VehicleClass> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    VehicleClassDTO toDto(VehicleClass vehicleClass);

    @Mapping(source = "vehicleId", target = "vehicle")
    VehicleClass toEntity(VehicleClassDTO vehicleClassDTO);

    default VehicleClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleClass vehicleClass = new VehicleClass();
        vehicleClass.setId(id);
        return vehicleClass;
    }
}
