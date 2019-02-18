package com.compsis.service;

import com.compsis.service.dto.BillingLocationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BillingLocation.
 */
public interface BillingLocationService {

    /**
     * Save a billingLocation.
     *
     * @param billingLocationDTO the entity to save
     * @return the persisted entity
     */
    BillingLocationDTO save(BillingLocationDTO billingLocationDTO);

    /**
     * Get all the billingLocations.
     *
     * @return the list of entities
     */
    List<BillingLocationDTO> findAll();


    /**
     * Get the "id" billingLocation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BillingLocationDTO> findOne(Long id);

    /**
     * Delete the "id" billingLocation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
