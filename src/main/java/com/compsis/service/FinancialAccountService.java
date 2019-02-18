package com.compsis.service;

import com.compsis.service.dto.FinancialAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FinancialAccount.
 */
public interface FinancialAccountService {

    /**
     * Save a financialAccount.
     *
     * @param financialAccountDTO the entity to save
     * @return the persisted entity
     */
    FinancialAccountDTO save(FinancialAccountDTO financialAccountDTO);

    /**
     * Get all the financialAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinancialAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" financialAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FinancialAccountDTO> findOne(Long id);

    /**
     * Delete the "id" financialAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
