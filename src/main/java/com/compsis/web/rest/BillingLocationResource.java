package com.compsis.web.rest;
import com.compsis.service.BillingLocationService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.BillingLocationDTO;
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
 * REST controller for managing BillingLocation.
 */
@RestController
@RequestMapping("/api")
public class BillingLocationResource {

    private final Logger log = LoggerFactory.getLogger(BillingLocationResource.class);

    private static final String ENTITY_NAME = "billingLocation";

    private final BillingLocationService billingLocationService;

    public BillingLocationResource(BillingLocationService billingLocationService) {
        this.billingLocationService = billingLocationService;
    }

    /**
     * POST  /billing-locations : Create a new billingLocation.
     *
     * @param billingLocationDTO the billingLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingLocationDTO, or with status 400 (Bad Request) if the billingLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-locations")
    public ResponseEntity<BillingLocationDTO> createBillingLocation(@RequestBody BillingLocationDTO billingLocationDTO) throws URISyntaxException {
        log.debug("REST request to save BillingLocation : {}", billingLocationDTO);
        if (billingLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new billingLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingLocationDTO result = billingLocationService.save(billingLocationDTO);
        return ResponseEntity.created(new URI("/api/billing-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-locations : Updates an existing billingLocation.
     *
     * @param billingLocationDTO the billingLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingLocationDTO,
     * or with status 400 (Bad Request) if the billingLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the billingLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-locations")
    public ResponseEntity<BillingLocationDTO> updateBillingLocation(@RequestBody BillingLocationDTO billingLocationDTO) throws URISyntaxException {
        log.debug("REST request to update BillingLocation : {}", billingLocationDTO);
        if (billingLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingLocationDTO result = billingLocationService.save(billingLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-locations : get all the billingLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billingLocations in body
     */
    @GetMapping("/billing-locations")
    public List<BillingLocationDTO> getAllBillingLocations() {
        log.debug("REST request to get all BillingLocations");
        return billingLocationService.findAll();
    }

    /**
     * GET  /billing-locations/:id : get the "id" billingLocation.
     *
     * @param id the id of the billingLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/billing-locations/{id}")
    public ResponseEntity<BillingLocationDTO> getBillingLocation(@PathVariable Long id) {
        log.debug("REST request to get BillingLocation : {}", id);
        Optional<BillingLocationDTO> billingLocationDTO = billingLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingLocationDTO);
    }

    /**
     * DELETE  /billing-locations/:id : delete the "id" billingLocation.
     *
     * @param id the id of the billingLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-locations/{id}")
    public ResponseEntity<Void> deleteBillingLocation(@PathVariable Long id) {
        log.debug("REST request to delete BillingLocation : {}", id);
        billingLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
