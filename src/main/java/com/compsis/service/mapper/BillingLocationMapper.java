package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BillingLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillingLocation and its DTO BillingLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {PassageMapper.class})
public interface BillingLocationMapper extends EntityMapper<BillingLocationDTO, BillingLocation> {

    @Mapping(source = "id.id", target = "idId")
    BillingLocationDTO toDto(BillingLocation billingLocation);

    @Mapping(source = "idId", target = "id")
    BillingLocation toEntity(BillingLocationDTO billingLocationDTO);

    default BillingLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillingLocation billingLocation = new BillingLocation();
        billingLocation.setId(id);
        return billingLocation;
    }
}
