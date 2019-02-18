package com.compsis.service;

import com.compsis.service.dto.AccountOperationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AccountOperation.
 */
public interface AccountOperationService {

    /**
     * Save a accountOperation.
     *
     * @param accountOperationDTO the entity to save
     * @return the persisted entity
     */
    AccountOperationDTO save(AccountOperationDTO accountOperationDTO);

    /**
     * Get all the accountOperations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AccountOperationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountOperation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AccountOperationDTO> findOne(Long id);

    /**
     * Delete the "id" accountOperation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
