package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BalanceCalculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BalanceCalculation and its DTO BalanceCalculationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BalanceCalculationMapper extends EntityMapper<BalanceCalculationDTO, BalanceCalculation> {



    default BalanceCalculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        BalanceCalculation balanceCalculation = new BalanceCalculation();
        balanceCalculation.setId(id);
        return balanceCalculation;
    }
}
