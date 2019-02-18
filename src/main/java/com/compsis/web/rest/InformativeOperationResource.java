package com.compsis.web.rest;
import com.compsis.service.InformativeOperationService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.InformativeOperationDTO;
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
 * REST controller for managing InformativeOperation.
 */
@RestController
@RequestMapping("/api")
public class InformativeOperationResource {

    private final Logger log = LoggerFactory.getLogger(InformativeOperationResource.class);

    private static final String ENTITY_NAME = "informativeOperation";

    private final InformativeOperationService informativeOperationService;

    public InformativeOperationResource(InformativeOperationService informativeOperationService) {
        this.informativeOperationService = informativeOperationService;
    }

    /**
     * POST  /informative-operations : Create a new informativeOperation.
     *
     * @param informativeOperationDTO the informativeOperationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new informativeOperationDTO, or with status 400 (Bad Request) if the informativeOperation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/informative-operations")
    public ResponseEntity<InformativeOperationDTO> createInformativeOperation(@RequestBody InformativeOperationDTO informativeOperationDTO) throws URISyntaxException {
        log.debug("REST request to save InformativeOperation : {}", informativeOperationDTO);
        if (informativeOperationDTO.getId() != null) {
            throw new BadRequestAlertException("A new informativeOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformativeOperationDTO result = informativeOperationService.save(informativeOperationDTO);
        return ResponseEntity.created(new URI("/api/informative-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /informative-operations : Updates an existing informativeOperation.
     *
     * @param informativeOperationDTO the informativeOperationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated informativeOperationDTO,
     * or with status 400 (Bad Request) if the informativeOperationDTO is not valid,
     * or with status 500 (Internal Server Error) if the informativeOperationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/informative-operations")
    public ResponseEntity<InformativeOperationDTO> updateInformativeOperation(@RequestBody InformativeOperationDTO informativeOperationDTO) throws URISyntaxException {
        log.debug("REST request to update InformativeOperation : {}", informativeOperationDTO);
        if (informativeOperationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InformativeOperationDTO result = informativeOperationService.save(informativeOperationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, informativeOperationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /informative-operations : get all the informativeOperations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of informativeOperations in body
     */
    @GetMapping("/informative-operations")
    public List<InformativeOperationDTO> getAllInformativeOperations() {
        log.debug("REST request to get all InformativeOperations");
        return informativeOperationService.findAll();
    }

    /**
     * GET  /informative-operations/:id : get the "id" informativeOperation.
     *
     * @param id the id of the informativeOperationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the informativeOperationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/informative-operations/{id}")
    public ResponseEntity<InformativeOperationDTO> getInformativeOperation(@PathVariable Long id) {
        log.debug("REST request to get InformativeOperation : {}", id);
        Optional<InformativeOperationDTO> informativeOperationDTO = informativeOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informativeOperationDTO);
    }

    /**
     * DELETE  /informative-operations/:id : delete the "id" informativeOperation.
     *
     * @param id the id of the informativeOperationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/informative-operations/{id}")
    public ResponseEntity<Void> deleteInformativeOperation(@PathVariable Long id) {
        log.debug("REST request to delete InformativeOperation : {}", id);
        informativeOperationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
