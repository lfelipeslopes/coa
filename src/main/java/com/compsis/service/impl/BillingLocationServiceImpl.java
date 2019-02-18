package com.compsis.service.impl;

import com.compsis.service.BillingLocationService;
import com.compsis.domain.BillingLocation;
import com.compsis.repository.BillingLocationRepository;
import com.compsis.service.dto.BillingLocationDTO;
import com.compsis.service.mapper.BillingLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BillingLocation.
 */
@Service
@Transactional
public class BillingLocationServiceImpl implements BillingLocationService {

    private final Logger log = LoggerFactory.getLogger(BillingLocationServiceImpl.class);

    private final BillingLocationRepository billingLocationRepository;

    private final BillingLocationMapper billingLocationMapper;

    public BillingLocationServiceImpl(BillingLocationRepository billingLocationRepository, BillingLocationMapper billingLocationMapper) {
        this.billingLocationRepository = billingLocationRepository;
        this.billingLocationMapper = billingLocationMapper;
    }

    /**
     * Save a billingLocation.
     *
     * @param billingLocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BillingLocationDTO save(BillingLocationDTO billingLocationDTO) {
        log.debug("Request to save BillingLocation : {}", billingLocationDTO);
        BillingLocation billingLocation = billingLocationMapper.toEntity(billingLocationDTO);
        billingLocation = billingLocationRepository.save(billingLocation);
        return billingLocationMapper.toDto(billingLocation);
    }

    /**
     * Get all the billingLocations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BillingLocationDTO> findAll() {
        log.debug("Request to get all BillingLocations");
        return billingLocationRepository.findAll().stream()
            .map(billingLocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one billingLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BillingLocationDTO> findOne(Long id) {
        log.debug("Request to get BillingLocation : {}", id);
        return billingLocationRepository.findById(id)
            .map(billingLocationMapper::toDto);
    }

    /**
     * Delete the billingLocation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingLocation : {}", id);        billingLocationRepository.deleteById(id);
    }
}
