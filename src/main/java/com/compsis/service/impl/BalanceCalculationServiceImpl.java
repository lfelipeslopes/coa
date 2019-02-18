package com.compsis.service.impl;

import com.compsis.service.BalanceCalculationService;
import com.compsis.domain.BalanceCalculation;
import com.compsis.repository.BalanceCalculationRepository;
import com.compsis.service.dto.BalanceCalculationDTO;
import com.compsis.service.mapper.BalanceCalculationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BalanceCalculation.
 */
@Service
@Transactional
public class BalanceCalculationServiceImpl implements BalanceCalculationService {

    private final Logger log = LoggerFactory.getLogger(BalanceCalculationServiceImpl.class);

    private final BalanceCalculationRepository balanceCalculationRepository;

    private final BalanceCalculationMapper balanceCalculationMapper;

    public BalanceCalculationServiceImpl(BalanceCalculationRepository balanceCalculationRepository, BalanceCalculationMapper balanceCalculationMapper) {
        this.balanceCalculationRepository = balanceCalculationRepository;
        this.balanceCalculationMapper = balanceCalculationMapper;
    }

    /**
     * Save a balanceCalculation.
     *
     * @param balanceCalculationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BalanceCalculationDTO save(BalanceCalculationDTO balanceCalculationDTO) {
        log.debug("Request to save BalanceCalculation : {}", balanceCalculationDTO);
        BalanceCalculation balanceCalculation = balanceCalculationMapper.toEntity(balanceCalculationDTO);
        balanceCalculation = balanceCalculationRepository.save(balanceCalculation);
        return balanceCalculationMapper.toDto(balanceCalculation);
    }

    /**
     * Get all the balanceCalculations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BalanceCalculationDTO> findAll() {
        log.debug("Request to get all BalanceCalculations");
        return balanceCalculationRepository.findAll().stream()
            .map(balanceCalculationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one balanceCalculation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BalanceCalculationDTO> findOne(Long id) {
        log.debug("Request to get BalanceCalculation : {}", id);
        return balanceCalculationRepository.findById(id)
            .map(balanceCalculationMapper::toDto);
    }

    /**
     * Delete the balanceCalculation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BalanceCalculation : {}", id);        balanceCalculationRepository.deleteById(id);
    }
}
