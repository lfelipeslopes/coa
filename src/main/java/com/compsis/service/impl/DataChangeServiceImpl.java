package com.compsis.service.impl;

import com.compsis.service.DataChangeService;
import com.compsis.domain.DataChange;
import com.compsis.repository.DataChangeRepository;
import com.compsis.service.dto.DataChangeDTO;
import com.compsis.service.mapper.DataChangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DataChange.
 */
@Service
@Transactional
public class DataChangeServiceImpl implements DataChangeService {

    private final Logger log = LoggerFactory.getLogger(DataChangeServiceImpl.class);

    private final DataChangeRepository dataChangeRepository;

    private final DataChangeMapper dataChangeMapper;

    public DataChangeServiceImpl(DataChangeRepository dataChangeRepository, DataChangeMapper dataChangeMapper) {
        this.dataChangeRepository = dataChangeRepository;
        this.dataChangeMapper = dataChangeMapper;
    }

    /**
     * Save a dataChange.
     *
     * @param dataChangeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DataChangeDTO save(DataChangeDTO dataChangeDTO) {
        log.debug("Request to save DataChange : {}", dataChangeDTO);
        DataChange dataChange = dataChangeMapper.toEntity(dataChangeDTO);
        dataChange = dataChangeRepository.save(dataChange);
        return dataChangeMapper.toDto(dataChange);
    }

    /**
     * Get all the dataChanges.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataChangeDTO> findAll() {
        log.debug("Request to get all DataChanges");
        return dataChangeRepository.findAll().stream()
            .map(dataChangeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dataChange by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataChangeDTO> findOne(Long id) {
        log.debug("Request to get DataChange : {}", id);
        return dataChangeRepository.findById(id)
            .map(dataChangeMapper::toDto);
    }

    /**
     * Delete the dataChange by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataChange : {}", id);        dataChangeRepository.deleteById(id);
    }
}
