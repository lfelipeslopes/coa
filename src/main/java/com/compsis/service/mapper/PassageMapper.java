package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.PassageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Passage and its DTO PassageDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class, VehicleClassMapper.class})
public interface PassageMapper extends EntityMapper<PassageDTO, Passage> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "classifiedClass.id", target = "classifiedClassId")
    @Mapping(source = "detectClass.id", target = "detectClassId")
    @Mapping(source = "chargedClass.id", target = "chargedClassId")
    PassageDTO toDto(Passage passage);

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "classifiedClassId", target = "classifiedClass")
    @Mapping(source = "detectClassId", target = "detectClass")
    @Mapping(source = "chargedClassId", target = "chargedClass")
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
