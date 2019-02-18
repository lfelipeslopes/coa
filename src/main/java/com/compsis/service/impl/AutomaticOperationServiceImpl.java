package com.compsis.service.impl;

import com.compsis.service.AutomaticOperationService;
import com.compsis.domain.AutomaticOperation;
import com.compsis.repository.AutomaticOperationRepository;
import com.compsis.service.dto.AutomaticOperationDTO;
import com.compsis.service.mapper.AutomaticOperationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AutomaticOperation.
 */
@Service
@Transactional
public class AutomaticOperationServiceImpl implements AutomaticOperationService {

    private final Logger log = LoggerFactory.getLogger(AutomaticOperationServiceImpl.class);

    private final AutomaticOperationRepository automaticOperationRepository;

    private final AutomaticOperationMapper automaticOperationMapper;

    public AutomaticOperationServiceImpl(AutomaticOperationRepository automaticOperationRepository, AutomaticOperationMapper automaticOperationMapper) {
        this.automaticOperationRepository = automaticOperationRepository;
        this.automaticOperationMapper = automaticOperationMapper;
    }

    /**
     * Save a automaticOperation.
     *
     * @param automaticOperationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AutomaticOperationDTO save(AutomaticOperationDTO automaticOperationDTO) {
        log.debug("Request to save AutomaticOperation : {}", automaticOperationDTO);
        AutomaticOperation automaticOperation = automaticOperationMapper.toEntity(automaticOperationDTO);
        automaticOperation = automaticOperationRepository.save(automaticOperation);
        return automaticOperationMapper.toDto(automaticOperation);
    }

    /**
     * Get all the automaticOperations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AutomaticOperationDTO> findAll() {
        log.debug("Request to get all AutomaticOperations");
        return automaticOperationRepository.findAll().stream()
            .map(automaticOperationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one automaticOperation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AutomaticOperationDTO> findOne(Long id) {
        log.debug("Request to get AutomaticOperation : {}", id);
        return automaticOperationRepository.findById(id)
            .map(automaticOperationMapper::toDto);
    }

    /**
     * Delete the automaticOperation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AutomaticOperation : {}", id);        automaticOperationRepository.deleteById(id);
    }
}
