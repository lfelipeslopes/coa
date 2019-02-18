package com.compsis.service.impl;

import com.compsis.service.AccountTransctionService;
import com.compsis.domain.AccountTransction;
import com.compsis.repository.AccountTransctionRepository;
import com.compsis.service.dto.AccountTransctionDTO;
import com.compsis.service.mapper.AccountTransctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AccountTransction.
 */
@Service
@Transactional
public class AccountTransctionServiceImpl implements AccountTransctionService {

    private final Logger log = LoggerFactory.getLogger(AccountTransctionServiceImpl.class);

    private final AccountTransctionRepository accountTransctionRepository;

    private final AccountTransctionMapper accountTransctionMapper;

    public AccountTransctionServiceImpl(AccountTransctionRepository accountTransctionRepository, AccountTransctionMapper accountTransctionMapper) {
        this.accountTransctionRepository = accountTransctionRepository;
        this.accountTransctionMapper = accountTransctionMapper;
    }

    /**
     * Save a accountTransction.
     *
     * @param accountTransctionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AccountTransctionDTO save(AccountTransctionDTO accountTransctionDTO) {
        log.debug("Request to save AccountTransction : {}", accountTransctionDTO);
        AccountTransction accountTransction = accountTransctionMapper.toEntity(accountTransctionDTO);
        accountTransction = accountTransctionRepository.save(accountTransction);
        return accountTransctionMapper.toDto(accountTransction);
    }

    /**
     * Get all the accountTransctions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountTransctionDTO> findAll() {
        log.debug("Request to get all AccountTransctions");
        return accountTransctionRepository.findAll().stream()
            .map(accountTransctionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accountTransction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTransctionDTO> findOne(Long id) {
        log.debug("Request to get AccountTransction : {}", id);
        return accountTransctionRepository.findById(id)
            .map(accountTransctionMapper::toDto);
    }

    /**
     * Delete the accountTransction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountTransction : {}", id);        accountTransctionRepository.deleteById(id);
    }
}
