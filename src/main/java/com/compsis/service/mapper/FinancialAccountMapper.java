package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.FinancialAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FinancialAccount and its DTO FinancialAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FinancialAccountMapper extends EntityMapper<FinancialAccountDTO, FinancialAccount> {


    @Mapping(target = "idUserAccounts", ignore = true)
    @Mapping(target = "idVehicleAccounts", ignore = true)
    @Mapping(target = "idAccountOperations", ignore = true)
    FinancialAccount toEntity(FinancialAccountDTO financialAccountDTO);

    default FinancialAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setId(id);
        return financialAccount;
    }
}
