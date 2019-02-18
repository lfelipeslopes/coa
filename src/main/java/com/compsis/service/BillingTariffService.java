package com.compsis.service;

import com.compsis.service.dto.BillingTariffDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BillingTariff.
 */
public interface BillingTariffService {

    /**
     * Save a billingTariff.
     *
     * @param billingTariffDTO the entity to save
     * @return the persisted entity
     */
    BillingTariffDTO save(BillingTariffDTO billingTariffDTO);

    /**
     * Get all the billingTariffs.
     *
     * @return the list of entities
     */
    List<BillingTariffDTO> findAll();


    /**
     * Get the "id" billingTariff.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BillingTariffDTO> findOne(Long id);

    /**
     * Delete the "id" billingTariff.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
