package com.compsis.service.impl;

import com.compsis.service.VehicleAccountService;
import com.compsis.domain.VehicleAccount;
import com.compsis.repository.VehicleAccountRepository;
import com.compsis.service.dto.VehicleAccountDTO;
import com.compsis.service.mapper.VehicleAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehicleAccount.
 */
@Service
@Transactional
public class VehicleAccountServiceImpl implements VehicleAccountService {

    private final Logger log = LoggerFactory.getLogger(VehicleAccountServiceImpl.class);

    private final VehicleAccountRepository vehicleAccountRepository;

    private final VehicleAccountMapper vehicleAccountMapper;

    public VehicleAccountServiceImpl(VehicleAccountRepository vehicleAccountRepository, VehicleAccountMapper vehicleAccountMapper) {
        this.vehicleAccountRepository = vehicleAccountRepository;
        this.vehicleAccountMapper = vehicleAccountMapper;
    }

    /**
     * Save a vehicleAccount.
     *
     * @param vehicleAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleAccountDTO save(VehicleAccountDTO vehicleAccountDTO) {
        log.debug("Request to save VehicleAccount : {}", vehicleAccountDTO);
        VehicleAccount vehicleAccount = vehicleAccountMapper.toEntity(vehicleAccountDTO);
        vehicleAccount = vehicleAccountRepository.save(vehicleAccount);
        return vehicleAccountMapper.toDto(vehicleAccount);
    }

    /**
     * Get all the vehicleAccounts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleAccountDTO> findAll() {
        log.debug("Request to get all VehicleAccounts");
        return vehicleAccountRepository.findAll().stream()
            .map(vehicleAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleAccountDTO> findOne(Long id) {
        log.debug("Request to get VehicleAccount : {}", id);
        return vehicleAccountRepository.findById(id)
            .map(vehicleAccountMapper::toDto);
    }

    /**
     * Delete the vehicleAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleAccount : {}", id);        vehicleAccountRepository.deleteById(id);
    }
}
