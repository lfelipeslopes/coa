package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BillingTariffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillingTariff and its DTO BillingTariffDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleClassMapper.class, BillingLocationMapper.class})
public interface BillingTariffMapper extends EntityMapper<BillingTariffDTO, BillingTariff> {

    @Mapping(source = "idBillingTariff.id", target = "idBillingTariffId")
    @Mapping(source = "billingLocation.id", target = "billingLocationId")
    BillingTariffDTO toDto(BillingTariff billingTariff);

    @Mapping(source = "idBillingTariffId", target = "idBillingTariff")
    @Mapping(source = "billingLocationId", target = "billingLocation")
    BillingTariff toEntity(BillingTariffDTO billingTariffDTO);

    default BillingTariff fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillingTariff billingTariff = new BillingTariff();
        billingTariff.setId(id);
        return billingTariff;
    }
}
