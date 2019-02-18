package com.compsis.web.rest;
import com.compsis.service.DataChangeService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.DataChangeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataChange.
 */
@RestController
@RequestMapping("/api")
public class DataChangeResource {

    private final Logger log = LoggerFactory.getLogger(DataChangeResource.class);

    private static final String ENTITY_NAME = "dataChange";

    private final DataChangeService dataChangeService;

    public DataChangeResource(DataChangeService dataChangeService) {
        this.dataChangeService = dataChangeService;
    }

    /**
     * POST  /data-changes : Create a new dataChange.
     *
     * @param dataChangeDTO the dataChangeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataChangeDTO, or with status 400 (Bad Request) if the dataChange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-changes")
    public ResponseEntity<DataChangeDTO> createDataChange(@RequestBody DataChangeDTO dataChangeDTO) throws URISyntaxException {
        log.debug("REST request to save DataChange : {}", dataChangeDTO);
        if (dataChangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataChange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataChangeDTO result = dataChangeService.save(dataChangeDTO);
        return ResponseEntity.created(new URI("/api/data-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-changes : Updates an existing dataChange.
     *
     * @param dataChangeDTO the dataChangeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataChangeDTO,
     * or with status 400 (Bad Request) if the dataChangeDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataChangeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-changes")
    public ResponseEntity<DataChangeDTO> updateDataChange(@RequestBody DataChangeDTO dataChangeDTO) throws URISyntaxException {
        log.debug("REST request to update DataChange : {}", dataChangeDTO);
        if (dataChangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataChangeDTO result = dataChangeService.save(dataChangeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataChangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-changes : get all the dataChanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dataChanges in body
     */
    @GetMapping("/data-changes")
    public List<DataChangeDTO> getAllDataChanges() {
        log.debug("REST request to get all DataChanges");
        return dataChangeService.findAll();
    }

    /**
     * GET  /data-changes/:id : get the "id" dataChange.
     *
     * @param id the id of the dataChangeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataChangeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-changes/{id}")
    public ResponseEntity<DataChangeDTO> getDataChange(@PathVariable Long id) {
        log.debug("REST request to get DataChange : {}", id);
        Optional<DataChangeDTO> dataChangeDTO = dataChangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataChangeDTO);
    }

    /**
     * DELETE  /data-changes/:id : delete the "id" dataChange.
     *
     * @param id the id of the dataChangeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-changes/{id}")
    public ResponseEntity<Void> deleteDataChange(@PathVariable Long id) {
        log.debug("REST request to delete DataChange : {}", id);
        dataChangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
