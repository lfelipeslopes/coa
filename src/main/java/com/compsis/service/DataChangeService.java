package com.compsis.service;

import com.compsis.service.dto.DataChangeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DataChange.
 */
public interface DataChangeService {

    /**
     * Save a dataChange.
     *
     * @param dataChangeDTO the entity to save
     * @return the persisted entity
     */
    DataChangeDTO save(DataChangeDTO dataChangeDTO);

    /**
     * Get all the dataChanges.
     *
     * @return the list of entities
     */
    List<DataChangeDTO> findAll();


    /**
     * Get the "id" dataChange.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DataChangeDTO> findOne(Long id);

    /**
     * Delete the "id" dataChange.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
