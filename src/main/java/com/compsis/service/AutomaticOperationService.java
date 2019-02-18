package com.compsis.service;

import com.compsis.service.dto.AutomaticOperationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AutomaticOperation.
 */
public interface AutomaticOperationService {

    /**
     * Save a automaticOperation.
     *
     * @param automaticOperationDTO the entity to save
     * @return the persisted entity
     */
    AutomaticOperationDTO save(AutomaticOperationDTO automaticOperationDTO);

    /**
     * Get all the automaticOperations.
     *
     * @return the list of entities
     */
    List<AutomaticOperationDTO> findAll();


    /**
     * Get the "id" automaticOperation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AutomaticOperationDTO> findOne(Long id);

    /**
     * Delete the "id" automaticOperation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
