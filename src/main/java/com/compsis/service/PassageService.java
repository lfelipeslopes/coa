package com.compsis.service;

import com.compsis.service.dto.PassageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Passage.
 */
public interface PassageService {

    /**
     * Save a passage.
     *
     * @param passageDTO the entity to save
     * @return the persisted entity
     */
    PassageDTO save(PassageDTO passageDTO);

    /**
     * Get all the passages.
     *
     * @return the list of entities
     */
    List<PassageDTO> findAll();


    /**
     * Get the "id" passage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PassageDTO> findOne(Long id);

    /**
     * Delete the "id" passage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
