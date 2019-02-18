package com.compsis.web.rest;
import com.compsis.service.AutomaticOperationService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.AutomaticOperationDTO;
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
 * REST controller for managing AutomaticOperation.
 */
@RestController
@RequestMapping("/api")
public class AutomaticOperationResource {

    private final Logger log = LoggerFactory.getLogger(AutomaticOperationResource.class);

    private static final String ENTITY_NAME = "automaticOperation";

    private final AutomaticOperationService automaticOperationService;

    public AutomaticOperationResource(AutomaticOperationService automaticOperationService) {
        this.automaticOperationService = automaticOperationService;
    }

    /**
     * POST  /automatic-operations : Create a new automaticOperation.
     *
     * @param automaticOperationDTO the automaticOperationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new automaticOperationDTO, or with status 400 (Bad Request) if the automaticOperation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/automatic-operations")
    public ResponseEntity<AutomaticOperationDTO> createAutomaticOperation(@RequestBody AutomaticOperationDTO automaticOperationDTO) throws URISyntaxException {
        log.debug("REST request to save AutomaticOperation : {}", automaticOperationDTO);
        if (automaticOperationDTO.getId() != null) {
            throw new BadRequestAlertException("A new automaticOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutomaticOperationDTO result = automaticOperationService.save(automaticOperationDTO);
        return ResponseEntity.created(new URI("/api/automatic-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /automatic-operations : Updates an existing automaticOperation.
     *
     * @param automaticOperationDTO the automaticOperationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated automaticOperationDTO,
     * or with status 400 (Bad Request) if the automaticOperationDTO is not valid,
     * or with status 500 (Internal Server Error) if the automaticOperationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/automatic-operations")
    public ResponseEntity<AutomaticOperationDTO> updateAutomaticOperation(@RequestBody AutomaticOperationDTO automaticOperationDTO) throws URISyntaxException {
        log.debug("REST request to update AutomaticOperation : {}", automaticOperationDTO);
        if (automaticOperationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutomaticOperationDTO result = automaticOperationService.save(automaticOperationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, automaticOperationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /automatic-operations : get all the automaticOperations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of automaticOperations in body
     */
    @GetMapping("/automatic-operations")
    public List<AutomaticOperationDTO> getAllAutomaticOperations() {
        log.debug("REST request to get all AutomaticOperations");
        return automaticOperationService.findAll();
    }

    /**
     * GET  /automatic-operations/:id : get the "id" automaticOperation.
     *
     * @param id the id of the automaticOperationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the automaticOperationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/automatic-operations/{id}")
    public ResponseEntity<AutomaticOperationDTO> getAutomaticOperation(@PathVariable Long id) {
        log.debug("REST request to get AutomaticOperation : {}", id);
        Optional<AutomaticOperationDTO> automaticOperationDTO = automaticOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(automaticOperationDTO);
    }

    /**
     * DELETE  /automatic-operations/:id : delete the "id" automaticOperation.
     *
     * @param id the id of the automaticOperationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/automatic-operations/{id}")
    public ResponseEntity<Void> deleteAutomaticOperation(@PathVariable Long id) {
        log.debug("REST request to delete AutomaticOperation : {}", id);
        automaticOperationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
