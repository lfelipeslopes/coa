package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BalanceCalculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BalanceCalculation and its DTO BalanceCalculationDTO.
 */
@Mapper(componentModel = "spring", uses = {AccountOperationMapper.class})
public interface BalanceCalculationMapper extends EntityMapper<BalanceCalculationDTO, BalanceCalculation> {

    @Mapping(source = "accountOperation.id", target = "accountOperationId")
    BalanceCalculationDTO toDto(BalanceCalculation balanceCalculation);

    @Mapping(source = "accountOperationId", target = "accountOperation")
    BalanceCalculation toEntity(BalanceCalculationDTO balanceCalculationDTO);

    default BalanceCalculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        BalanceCalculation balanceCalculation = new BalanceCalculation();
        balanceCalculation.setId(id);
        return balanceCalculation;
    }
}
