package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    MediaDTO toDto(Media media);

    @Mapping(source = "vehicleId", target = "vehicle")
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
