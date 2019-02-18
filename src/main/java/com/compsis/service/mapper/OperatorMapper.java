package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.OperatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operator and its DTO OperatorDTO.
 */
@Mapper(componentModel = "spring", uses = {AccountOperationMapper.class})
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {

    @Mapping(source = "accountOperation.id", target = "accountOperationId")
    OperatorDTO toDto(Operator operator);

    @Mapping(source = "accountOperationId", target = "accountOperation")
    Operator toEntity(OperatorDTO operatorDTO);

    default Operator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operator operator = new Operator();
        operator.setId(id);
        return operator;
    }
}
