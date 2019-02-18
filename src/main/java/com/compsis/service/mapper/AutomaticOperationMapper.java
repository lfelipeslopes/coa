package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.AutomaticOperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AutomaticOperation and its DTO AutomaticOperationDTO.
 */
@Mapper(componentModel = "spring", uses = {AccountOperationMapper.class})
public interface AutomaticOperationMapper extends EntityMapper<AutomaticOperationDTO, AutomaticOperation> {

    @Mapping(source = "accountOperation.id", target = "accountOperationId")
    AutomaticOperationDTO toDto(AutomaticOperation automaticOperation);

    @Mapping(source = "accountOperationId", target = "accountOperation")
    AutomaticOperation toEntity(AutomaticOperationDTO automaticOperationDTO);

    default AutomaticOperation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutomaticOperation automaticOperation = new AutomaticOperation();
        automaticOperation.setId(id);
        return automaticOperation;
    }
}
