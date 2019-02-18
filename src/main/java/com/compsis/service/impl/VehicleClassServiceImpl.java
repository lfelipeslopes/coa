package com.compsis.service.impl;

import com.compsis.service.VehicleClassService;
import com.compsis.domain.VehicleClass;
import com.compsis.repository.VehicleClassRepository;
import com.compsis.service.dto.VehicleClassDTO;
import com.compsis.service.mapper.VehicleClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehicleClass.
 */
@Service
@Transactional
public class VehicleClassServiceImpl implements VehicleClassService {

    private final Logger log = LoggerFactory.getLogger(VehicleClassServiceImpl.class);

    private final VehicleClassRepository vehicleClassRepository;

    private final VehicleClassMapper vehicleClassMapper;

    public VehicleClassServiceImpl(VehicleClassRepository vehicleClassRepository, VehicleClassMapper vehicleClassMapper) {
        this.vehicleClassRepository = vehicleClassRepository;
        this.vehicleClassMapper = vehicleClassMapper;
    }

    /**
     * Save a vehicleClass.
     *
     * @param vehicleClassDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleClassDTO save(VehicleClassDTO vehicleClassDTO) {
        log.debug("Request to save VehicleClass : {}", vehicleClassDTO);
        VehicleClass vehicleClass = vehicleClassMapper.toEntity(vehicleClassDTO);
        vehicleClass = vehicleClassRepository.save(vehicleClass);
        return vehicleClassMapper.toDto(vehicleClass);
    }

    /**
     * Get all the vehicleClasses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleClassDTO> findAll() {
        log.debug("Request to get all VehicleClasses");
        return vehicleClassRepository.findAll().stream()
            .map(vehicleClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleClassDTO> findOne(Long id) {
        log.debug("Request to get VehicleClass : {}", id);
        return vehicleClassRepository.findById(id)
            .map(vehicleClassMapper::toDto);
    }

    /**
     * Delete the vehicleClass by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleClass : {}", id);        vehicleClassRepository.deleteById(id);
    }
}
