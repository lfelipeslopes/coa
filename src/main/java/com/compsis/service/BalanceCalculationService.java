package com.compsis.service;

import com.compsis.service.dto.BalanceCalculationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BalanceCalculation.
 */
public interface BalanceCalculationService {

    /**
     * Save a balanceCalculation.
     *
     * @param balanceCalculationDTO the entity to save
     * @return the persisted entity
     */
    BalanceCalculationDTO save(BalanceCalculationDTO balanceCalculationDTO);

    /**
     * Get all the balanceCalculations.
     *
     * @return the list of entities
     */
    List<BalanceCalculationDTO> findAll();


    /**
     * Get the "id" balanceCalculation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BalanceCalculationDTO> findOne(Long id);

    /**
     * Delete the "id" balanceCalculation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
