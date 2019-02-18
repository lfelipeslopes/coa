package com.compsis.web.rest;
import com.compsis.service.BalanceCalculationService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.BalanceCalculationDTO;
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
 * REST controller for managing BalanceCalculation.
 */
@RestController
@RequestMapping("/api")
public class BalanceCalculationResource {

    private final Logger log = LoggerFactory.getLogger(BalanceCalculationResource.class);

    private static final String ENTITY_NAME = "balanceCalculation";

    private final BalanceCalculationService balanceCalculationService;

    public BalanceCalculationResource(BalanceCalculationService balanceCalculationService) {
        this.balanceCalculationService = balanceCalculationService;
    }

    /**
     * POST  /balance-calculations : Create a new balanceCalculation.
     *
     * @param balanceCalculationDTO the balanceCalculationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balanceCalculationDTO, or with status 400 (Bad Request) if the balanceCalculation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balance-calculations")
    public ResponseEntity<BalanceCalculationDTO> createBalanceCalculation(@RequestBody BalanceCalculationDTO balanceCalculationDTO) throws URISyntaxException {
        log.debug("REST request to save BalanceCalculation : {}", balanceCalculationDTO);
        if (balanceCalculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new balanceCalculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceCalculationDTO result = balanceCalculationService.save(balanceCalculationDTO);
        return ResponseEntity.created(new URI("/api/balance-calculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balance-calculations : Updates an existing balanceCalculation.
     *
     * @param balanceCalculationDTO the balanceCalculationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balanceCalculationDTO,
     * or with status 400 (Bad Request) if the balanceCalculationDTO is not valid,
     * or with status 500 (Internal Server Error) if the balanceCalculationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balance-calculations")
    public ResponseEntity<BalanceCalculationDTO> updateBalanceCalculation(@RequestBody BalanceCalculationDTO balanceCalculationDTO) throws URISyntaxException {
        log.debug("REST request to update BalanceCalculation : {}", balanceCalculationDTO);
        if (balanceCalculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BalanceCalculationDTO result = balanceCalculationService.save(balanceCalculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balanceCalculationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balance-calculations : get all the balanceCalculations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of balanceCalculations in body
     */
    @GetMapping("/balance-calculations")
    public List<BalanceCalculationDTO> getAllBalanceCalculations() {
        log.debug("REST request to get all BalanceCalculations");
        return balanceCalculationService.findAll();
    }

    /**
     * GET  /balance-calculations/:id : get the "id" balanceCalculation.
     *
     * @param id the id of the balanceCalculationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balanceCalculationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/balance-calculations/{id}")
    public ResponseEntity<BalanceCalculationDTO> getBalanceCalculation(@PathVariable Long id) {
        log.debug("REST request to get BalanceCalculation : {}", id);
        Optional<BalanceCalculationDTO> balanceCalculationDTO = balanceCalculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(balanceCalculationDTO);
    }

    /**
     * DELETE  /balance-calculations/:id : delete the "id" balanceCalculation.
     *
     * @param id the id of the balanceCalculationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balance-calculations/{id}")
    public ResponseEntity<Void> deleteBalanceCalculation(@PathVariable Long id) {
        log.debug("REST request to delete BalanceCalculation : {}", id);
        balanceCalculationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
