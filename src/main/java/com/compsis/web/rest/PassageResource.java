package com.compsis.web.rest;
import com.compsis.service.PassageService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.PassageDTO;
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
 * REST controller for managing Passage.
 */
@RestController
@RequestMapping("/api")
public class PassageResource {

    private final Logger log = LoggerFactory.getLogger(PassageResource.class);

    private static final String ENTITY_NAME = "passage";

    private final PassageService passageService;

    public PassageResource(PassageService passageService) {
        this.passageService = passageService;
    }

    /**
     * POST  /passages : Create a new passage.
     *
     * @param passageDTO the passageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passageDTO, or with status 400 (Bad Request) if the passage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passages")
    public ResponseEntity<PassageDTO> createPassage(@RequestBody PassageDTO passageDTO) throws URISyntaxException {
        log.debug("REST request to save Passage : {}", passageDTO);
        if (passageDTO.getId() != null) {
            throw new BadRequestAlertException("A new passage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PassageDTO result = passageService.save(passageDTO);
        return ResponseEntity.created(new URI("/api/passages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passages : Updates an existing passage.
     *
     * @param passageDTO the passageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passageDTO,
     * or with status 400 (Bad Request) if the passageDTO is not valid,
     * or with status 500 (Internal Server Error) if the passageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passages")
    public ResponseEntity<PassageDTO> updatePassage(@RequestBody PassageDTO passageDTO) throws URISyntaxException {
        log.debug("REST request to update Passage : {}", passageDTO);
        if (passageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PassageDTO result = passageService.save(passageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, passageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passages : get all the passages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of passages in body
     */
    @GetMapping("/passages")
    public List<PassageDTO> getAllPassages() {
        log.debug("REST request to get all Passages");
        return passageService.findAll();
    }

    /**
     * GET  /passages/:id : get the "id" passage.
     *
     * @param id the id of the passageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/passages/{id}")
    public ResponseEntity<PassageDTO> getPassage(@PathVariable Long id) {
        log.debug("REST request to get Passage : {}", id);
        Optional<PassageDTO> passageDTO = passageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(passageDTO);
    }

    /**
     * DELETE  /passages/:id : delete the "id" passage.
     *
     * @param id the id of the passageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passages/{id}")
    public ResponseEntity<Void> deletePassage(@PathVariable Long id) {
        log.debug("REST request to delete Passage : {}", id);
        passageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
