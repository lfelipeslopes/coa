package com.compsis.service;

import com.compsis.service.dto.VehicleClassDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing VehicleClass.
 */
public interface VehicleClassService {

    /**
     * Save a vehicleClass.
     *
     * @param vehicleClassDTO the entity to save
     * @return the persisted entity
     */
    VehicleClassDTO save(VehicleClassDTO vehicleClassDTO);

    /**
     * Get all the vehicleClasses.
     *
     * @return the list of entities
     */
    List<VehicleClassDTO> findAll();


    /**
     * Get the "id" vehicleClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleClassDTO> findOne(Long id);

    /**
     * Delete the "id" vehicleClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
