package com.compsis.service.impl;

import com.compsis.service.AccountOperationService;
import com.compsis.domain.AccountOperation;
import com.compsis.repository.AccountOperationRepository;
import com.compsis.service.dto.AccountOperationDTO;
import com.compsis.service.mapper.AccountOperationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AccountOperation.
 */
@Service
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    private final Logger log = LoggerFactory.getLogger(AccountOperationServiceImpl.class);

    private final AccountOperationRepository accountOperationRepository;

    private final AccountOperationMapper accountOperationMapper;

    public AccountOperationServiceImpl(AccountOperationRepository accountOperationRepository, AccountOperationMapper accountOperationMapper) {
        this.accountOperationRepository = accountOperationRepository;
        this.accountOperationMapper = accountOperationMapper;
    }

    /**
     * Save a accountOperation.
     *
     * @param accountOperationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AccountOperationDTO save(AccountOperationDTO accountOperationDTO) {
        log.debug("Request to save AccountOperation : {}", accountOperationDTO);
        AccountOperation accountOperation = accountOperationMapper.toEntity(accountOperationDTO);
        accountOperation = accountOperationRepository.save(accountOperation);
        return accountOperationMapper.toDto(accountOperation);
    }

    /**
     * Get all the accountOperations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountOperationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountOperations");
        return accountOperationRepository.findAll(pageable)
            .map(accountOperationMapper::toDto);
    }


    /**
     * Get one accountOperation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountOperationDTO> findOne(Long id) {
        log.debug("Request to get AccountOperation : {}", id);
        return accountOperationRepository.findById(id)
            .map(accountOperationMapper::toDto);
    }

    /**
     * Delete the accountOperation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountOperation : {}", id);        accountOperationRepository.deleteById(id);
    }
}
