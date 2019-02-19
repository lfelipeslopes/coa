package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.AutomaticOperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AutomaticOperation and its DTO AutomaticOperationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutomaticOperationMapper extends EntityMapper<AutomaticOperationDTO, AutomaticOperation> {



    default AutomaticOperation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutomaticOperation automaticOperation = new AutomaticOperation();
        automaticOperation.setId(id);
        return automaticOperation;
    }
}
