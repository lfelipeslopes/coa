package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BillingLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillingLocation and its DTO BillingLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillingLocationMapper extends EntityMapper<BillingLocationDTO, BillingLocation> {


    @Mapping(target = "idBillingLocations", ignore = true)
    @Mapping(target = "idBillingLocations", ignore = true)
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
