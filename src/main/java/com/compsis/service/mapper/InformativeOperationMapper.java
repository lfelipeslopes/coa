package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.InformativeOperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InformativeOperation and its DTO InformativeOperationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InformativeOperationMapper extends EntityMapper<InformativeOperationDTO, InformativeOperation> {



    default InformativeOperation fromId(Long id) {
        if (id == null) {
            return null;
        }
        InformativeOperation informativeOperation = new InformativeOperation();
        informativeOperation.setId(id);
        return informativeOperation;
    }
}
