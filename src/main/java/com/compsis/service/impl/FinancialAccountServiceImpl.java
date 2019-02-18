package com.compsis.service.impl;

import com.compsis.service.FinancialAccountService;
import com.compsis.domain.FinancialAccount;
import com.compsis.repository.FinancialAccountRepository;
import com.compsis.service.dto.FinancialAccountDTO;
import com.compsis.service.mapper.FinancialAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing FinancialAccount.
 */
@Service
@Transactional
public class FinancialAccountServiceImpl implements FinancialAccountService {

    private final Logger log = LoggerFactory.getLogger(FinancialAccountServiceImpl.class);

    private final FinancialAccountRepository financialAccountRepository;

    private final FinancialAccountMapper financialAccountMapper;

    public FinancialAccountServiceImpl(FinancialAccountRepository financialAccountRepository, FinancialAccountMapper financialAccountMapper) {
        this.financialAccountRepository = financialAccountRepository;
        this.financialAccountMapper = financialAccountMapper;
    }

    /**
     * Save a financialAccount.
     *
     * @param financialAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FinancialAccountDTO save(FinancialAccountDTO financialAccountDTO) {
        log.debug("Request to save FinancialAccount : {}", financialAccountDTO);
        FinancialAccount financialAccount = financialAccountMapper.toEntity(financialAccountDTO);
        financialAccount = financialAccountRepository.save(financialAccount);
        return financialAccountMapper.toDto(financialAccount);
    }

    /**
     * Get all the financialAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinancialAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinancialAccounts");
        return financialAccountRepository.findAll(pageable)
            .map(financialAccountMapper::toDto);
    }


    /**
     * Get one financialAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialAccountDTO> findOne(Long id) {
        log.debug("Request to get FinancialAccount : {}", id);
        return financialAccountRepository.findById(id)
            .map(financialAccountMapper::toDto);
    }

    /**
     * Delete the financialAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinancialAccount : {}", id);        financialAccountRepository.deleteById(id);
    }
}
