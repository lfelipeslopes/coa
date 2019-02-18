package com.compsis.service;

import com.compsis.service.dto.VehicleAccountDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VehicleAccount.
 */
public interface VehicleAccountService {

    /**
     * Save a vehicleAccount.
     *
     * @param vehicleAccountDTO the entity to save
     * @return the persisted entity
     */
    VehicleAccountDTO save(VehicleAccountDTO vehicleAccountDTO);

    /**
     * Get all the vehicleAccounts.
     *
     * @return the list of entities
     */
    List<VehicleAccountDTO> findAll();


    /**
     * Get the "id" vehicleAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleAccountDTO> findOne(Long id);

    /**
     * Delete the "id" vehicleAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
