package com.compsis.service;

import com.compsis.service.dto.AccountTransctionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AccountTransction.
 */
public interface AccountTransctionService {

    /**
     * Save a accountTransction.
     *
     * @param accountTransctionDTO the entity to save
     * @return the persisted entity
     */
    AccountTransctionDTO save(AccountTransctionDTO accountTransctionDTO);

    /**
     * Get all the accountTransctions.
     *
     * @return the list of entities
     */
    List<AccountTransctionDTO> findAll();


    /**
     * Get the "id" accountTransction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AccountTransctionDTO> findOne(Long id);

    /**
     * Delete the "id" accountTransction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
