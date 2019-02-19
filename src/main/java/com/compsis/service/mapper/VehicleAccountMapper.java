package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleAccount and its DTO VehicleAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface VehicleAccountMapper extends EntityMapper<VehicleAccountDTO, VehicleAccount> {

    @Mapping(source = "idVehicleClass.id", target = "idVehicleClassId")
    VehicleAccountDTO toDto(VehicleAccount vehicleAccount);

    @Mapping(source = "idVehicleClassId", target = "idVehicleClass")
    VehicleAccount toEntity(VehicleAccountDTO vehicleAccountDTO);

    default VehicleAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleAccount vehicleAccount = new VehicleAccount();
        vehicleAccount.setId(id);
        return vehicleAccount;
    }
}
