package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.DataChangeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataChange and its DTO DataChangeDTO.
 */
@Mapper(componentModel = "spring", uses = {AccountOperationMapper.class})
public interface DataChangeMapper extends EntityMapper<DataChangeDTO, DataChange> {

    @Mapping(source = "accountOperation.id", target = "accountOperationId")
    DataChangeDTO toDto(DataChange dataChange);

    @Mapping(source = "accountOperationId", target = "accountOperation")
    DataChange toEntity(DataChangeDTO dataChangeDTO);

    default DataChange fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataChange dataChange = new DataChange();
        dataChange.setId(id);
        return dataChange;
    }
}
