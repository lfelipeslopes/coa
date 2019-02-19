package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.BillingTariffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillingTariff and its DTO BillingTariffDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleClassMapper.class})
public interface BillingTariffMapper extends EntityMapper<BillingTariffDTO, BillingTariff> {

    @Mapping(source = "idBillingTariff.id", target = "idBillingTariffId")
    BillingTariffDTO toDto(BillingTariff billingTariff);

    @Mapping(source = "idBillingTariffId", target = "idBillingTariff")
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
