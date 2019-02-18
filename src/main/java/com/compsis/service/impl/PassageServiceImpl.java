package com.compsis.service.impl;

import com.compsis.service.PassageService;
import com.compsis.domain.Passage;
import com.compsis.repository.PassageRepository;
import com.compsis.service.dto.PassageDTO;
import com.compsis.service.mapper.PassageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Passage.
 */
@Service
@Transactional
public class PassageServiceImpl implements PassageService {

    private final Logger log = LoggerFactory.getLogger(PassageServiceImpl.class);

    private final PassageRepository passageRepository;

    private final PassageMapper passageMapper;

    public PassageServiceImpl(PassageRepository passageRepository, PassageMapper passageMapper) {
        this.passageRepository = passageRepository;
        this.passageMapper = passageMapper;
    }

    /**
     * Save a passage.
     *
     * @param passageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PassageDTO save(PassageDTO passageDTO) {
        log.debug("Request to save Passage : {}", passageDTO);
        Passage passage = passageMapper.toEntity(passageDTO);
        passage = passageRepository.save(passage);
        return passageMapper.toDto(passage);
    }

    /**
     * Get all the passages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PassageDTO> findAll() {
        log.debug("Request to get all Passages");
        return passageRepository.findAll().stream()
            .map(passageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one passage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PassageDTO> findOne(Long id) {
        log.debug("Request to get Passage : {}", id);
        return passageRepository.findById(id)
            .map(passageMapper::toDto);
    }

    /**
     * Delete the passage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passage : {}", id);        passageRepository.deleteById(id);
    }
}
