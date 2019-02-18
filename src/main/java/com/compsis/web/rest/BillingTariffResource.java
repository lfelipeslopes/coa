package com.compsis.web.rest;
import com.compsis.service.BillingTariffService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.BillingTariffDTO;
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
 * REST controller for managing BillingTariff.
 */
@RestController
@RequestMapping("/api")
public class BillingTariffResource {

    private final Logger log = LoggerFactory.getLogger(BillingTariffResource.class);

    private static final String ENTITY_NAME = "billingTariff";

    private final BillingTariffService billingTariffService;

    public BillingTariffResource(BillingTariffService billingTariffService) {
        this.billingTariffService = billingTariffService;
    }

    /**
     * POST  /billing-tariffs : Create a new billingTariff.
     *
     * @param billingTariffDTO the billingTariffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingTariffDTO, or with status 400 (Bad Request) if the billingTariff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-tariffs")
    public ResponseEntity<BillingTariffDTO> createBillingTariff(@RequestBody BillingTariffDTO billingTariffDTO) throws URISyntaxException {
        log.debug("REST request to save BillingTariff : {}", billingTariffDTO);
        if (billingTariffDTO.getId() != null) {
            throw new BadRequestAlertException("A new billingTariff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingTariffDTO result = billingTariffService.save(billingTariffDTO);
        return ResponseEntity.created(new URI("/api/billing-tariffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-tariffs : Updates an existing billingTariff.
     *
     * @param billingTariffDTO the billingTariffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingTariffDTO,
     * or with status 400 (Bad Request) if the billingTariffDTO is not valid,
     * or with status 500 (Internal Server Error) if the billingTariffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-tariffs")
    public ResponseEntity<BillingTariffDTO> updateBillingTariff(@RequestBody BillingTariffDTO billingTariffDTO) throws URISyntaxException {
        log.debug("REST request to update BillingTariff : {}", billingTariffDTO);
        if (billingTariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingTariffDTO result = billingTariffService.save(billingTariffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingTariffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-tariffs : get all the billingTariffs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billingTariffs in body
     */
    @GetMapping("/billing-tariffs")
    public List<BillingTariffDTO> getAllBillingTariffs() {
        log.debug("REST request to get all BillingTariffs");
        return billingTariffService.findAll();
    }

    /**
     * GET  /billing-tariffs/:id : get the "id" billingTariff.
     *
     * @param id the id of the billingTariffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingTariffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/billing-tariffs/{id}")
    public ResponseEntity<BillingTariffDTO> getBillingTariff(@PathVariable Long id) {
        log.debug("REST request to get BillingTariff : {}", id);
        Optional<BillingTariffDTO> billingTariffDTO = billingTariffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingTariffDTO);
    }

    /**
     * DELETE  /billing-tariffs/:id : delete the "id" billingTariff.
     *
     * @param id the id of the billingTariffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-tariffs/{id}")
    public ResponseEntity<Void> deleteBillingTariff(@PathVariable Long id) {
        log.debug("REST request to delete BillingTariff : {}", id);
        billingTariffService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
