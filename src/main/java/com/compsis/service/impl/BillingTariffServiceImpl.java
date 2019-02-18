package com.compsis.service.impl;

import com.compsis.service.BillingTariffService;
import com.compsis.domain.BillingTariff;
import com.compsis.repository.BillingTariffRepository;
import com.compsis.service.dto.BillingTariffDTO;
import com.compsis.service.mapper.BillingTariffMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BillingTariff.
 */
@Service
@Transactional
public class BillingTariffServiceImpl implements BillingTariffService {

    private final Logger log = LoggerFactory.getLogger(BillingTariffServiceImpl.class);

    private final BillingTariffRepository billingTariffRepository;

    private final BillingTariffMapper billingTariffMapper;

    public BillingTariffServiceImpl(BillingTariffRepository billingTariffRepository, BillingTariffMapper billingTariffMapper) {
        this.billingTariffRepository = billingTariffRepository;
        this.billingTariffMapper = billingTariffMapper;
    }

    /**
     * Save a billingTariff.
     *
     * @param billingTariffDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BillingTariffDTO save(BillingTariffDTO billingTariffDTO) {
        log.debug("Request to save BillingTariff : {}", billingTariffDTO);
        BillingTariff billingTariff = billingTariffMapper.toEntity(billingTariffDTO);
        billingTariff = billingTariffRepository.save(billingTariff);
        return billingTariffMapper.toDto(billingTariff);
    }

    /**
     * Get all the billingTariffs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BillingTariffDTO> findAll() {
        log.debug("Request to get all BillingTariffs");
        return billingTariffRepository.findAll().stream()
            .map(billingTariffMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one billingTariff by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BillingTariffDTO> findOne(Long id) {
        log.debug("Request to get BillingTariff : {}", id);
        return billingTariffRepository.findById(id)
            .map(billingTariffMapper::toDto);
    }

    /**
     * Delete the billingTariff by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingTariff : {}", id);        billingTariffRepository.deleteById(id);
    }
}
