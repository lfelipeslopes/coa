package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleClass and its DTO VehicleClassDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleClassMapper extends EntityMapper<VehicleClassDTO, VehicleClass> {



    default VehicleClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleClass vehicleClass = new VehicleClass();
        vehicleClass.setId(id);
        return vehicleClass;
    }
}
