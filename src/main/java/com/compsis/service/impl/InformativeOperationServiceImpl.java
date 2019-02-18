package com.compsis.service.impl;

import com.compsis.service.InformativeOperationService;
import com.compsis.domain.InformativeOperation;
import com.compsis.repository.InformativeOperationRepository;
import com.compsis.service.dto.InformativeOperationDTO;
import com.compsis.service.mapper.InformativeOperationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InformativeOperation.
 */
@Service
@Transactional
public class InformativeOperationServiceImpl implements InformativeOperationService {

    private final Logger log = LoggerFactory.getLogger(InformativeOperationServiceImpl.class);

    private final InformativeOperationRepository informativeOperationRepository;

    private final InformativeOperationMapper informativeOperationMapper;

    public InformativeOperationServiceImpl(InformativeOperationRepository informativeOperationRepository, InformativeOperationMapper informativeOperationMapper) {
        this.informativeOperationRepository = informativeOperationRepository;
        this.informativeOperationMapper = informativeOperationMapper;
    }

    /**
     * Save a informativeOperation.
     *
     * @param informativeOperationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InformativeOperationDTO save(InformativeOperationDTO informativeOperationDTO) {
        log.debug("Request to save InformativeOperation : {}", informativeOperationDTO);
        InformativeOperation informativeOperation = informativeOperationMapper.toEntity(informativeOperationDTO);
        informativeOperation = informativeOperationRepository.save(informativeOperation);
        return informativeOperationMapper.toDto(informativeOperation);
    }

    /**
     * Get all the informativeOperations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InformativeOperationDTO> findAll() {
        log.debug("Request to get all InformativeOperations");
        return informativeOperationRepository.findAll().stream()
            .map(informativeOperationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one informativeOperation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InformativeOperationDTO> findOne(Long id) {
        log.debug("Request to get InformativeOperation : {}", id);
        return informativeOperationRepository.findById(id)
            .map(informativeOperationMapper::toDto);
    }

    /**
     * Delete the informativeOperation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InformativeOperation : {}", id);        informativeOperationRepository.deleteById(id);
    }
}
