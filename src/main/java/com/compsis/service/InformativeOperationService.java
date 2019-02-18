package com.compsis.service;

import com.compsis.service.dto.InformativeOperationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing InformativeOperation.
 */
public interface InformativeOperationService {

    /**
     * Save a informativeOperation.
     *
     * @param informativeOperationDTO the entity to save
     * @return the persisted entity
     */
    InformativeOperationDTO save(InformativeOperationDTO informativeOperationDTO);

    /**
     * Get all the informativeOperations.
     *
     * @return the list of entities
     */
    List<InformativeOperationDTO> findAll();


    /**
     * Get the "id" informativeOperation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InformativeOperationDTO> findOne(Long id);

    /**
     * Delete the "id" informativeOperation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
