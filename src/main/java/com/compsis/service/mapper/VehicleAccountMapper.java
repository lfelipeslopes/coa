package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.VehicleAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleAccount and its DTO VehicleAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountMapper.class, VehicleClassMapper.class})
public interface VehicleAccountMapper extends EntityMapper<VehicleAccountDTO, VehicleAccount> {

    @Mapping(source = "financialAccount.id", target = "financialAccountId")
    @Mapping(source = "id.id", target = "idId")
    VehicleAccountDTO toDto(VehicleAccount vehicleAccount);

    @Mapping(source = "financialAccountId", target = "financialAccount")
    @Mapping(source = "idId", target = "id")
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
