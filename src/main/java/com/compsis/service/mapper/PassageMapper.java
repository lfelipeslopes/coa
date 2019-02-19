package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.PassageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Passage and its DTO PassageDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleClassMapper.class, VehicleMapper.class})
public interface PassageMapper extends EntityMapper<PassageDTO, Passage> {

    @Mapping(source = "classifiedClass.id", target = "classifiedClassId")
    @Mapping(source = "detectClass.id", target = "detectClassId")
    @Mapping(source = "chargedClass.id", target = "chargedClassId")
    @Mapping(source = "idVehicle.id", target = "idVehicleId")
    PassageDTO toDto(Passage passage);

    @Mapping(source = "classifiedClassId", target = "classifiedClass")
    @Mapping(source = "detectClassId", target = "detectClass")
    @Mapping(source = "chargedClassId", target = "chargedClass")
    @Mapping(source = "idVehicleId", target = "idVehicle")
    Passage toEntity(PassageDTO passageDTO);

    default Passage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Passage passage = new Passage();
        passage.setId(id);
        return passage;
    }
}
