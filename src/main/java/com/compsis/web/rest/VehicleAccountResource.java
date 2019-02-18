package com.compsis.web.rest;
import com.compsis.service.VehicleAccountService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.VehicleAccountDTO;
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
 * REST controller for managing VehicleAccount.
 */
@RestController
@RequestMapping("/api")
public class VehicleAccountResource {

    private final Logger log = LoggerFactory.getLogger(VehicleAccountResource.class);

    private static final String ENTITY_NAME = "vehicleAccount";

    private final VehicleAccountService vehicleAccountService;

    public VehicleAccountResource(VehicleAccountService vehicleAccountService) {
        this.vehicleAccountService = vehicleAccountService;
    }

    /**
     * POST  /vehicle-accounts : Create a new vehicleAccount.
     *
     * @param vehicleAccountDTO the vehicleAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleAccountDTO, or with status 400 (Bad Request) if the vehicleAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-accounts")
    public ResponseEntity<VehicleAccountDTO> createVehicleAccount(@RequestBody VehicleAccountDTO vehicleAccountDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleAccount : {}", vehicleAccountDTO);
        if (vehicleAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleAccountDTO result = vehicleAccountService.save(vehicleAccountDTO);
        return ResponseEntity.created(new URI("/api/vehicle-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-accounts : Updates an existing vehicleAccount.
     *
     * @param vehicleAccountDTO the vehicleAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleAccountDTO,
     * or with status 400 (Bad Request) if the vehicleAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-accounts")
    public ResponseEntity<VehicleAccountDTO> updateVehicleAccount(@RequestBody VehicleAccountDTO vehicleAccountDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleAccount : {}", vehicleAccountDTO);
        if (vehicleAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleAccountDTO result = vehicleAccountService.save(vehicleAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-accounts : get all the vehicleAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleAccounts in body
     */
    @GetMapping("/vehicle-accounts")
    public List<VehicleAccountDTO> getAllVehicleAccounts() {
        log.debug("REST request to get all VehicleAccounts");
        return vehicleAccountService.findAll();
    }

    /**
     * GET  /vehicle-accounts/:id : get the "id" vehicleAccount.
     *
     * @param id the id of the vehicleAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-accounts/{id}")
    public ResponseEntity<VehicleAccountDTO> getVehicleAccount(@PathVariable Long id) {
        log.debug("REST request to get VehicleAccount : {}", id);
        Optional<VehicleAccountDTO> vehicleAccountDTO = vehicleAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleAccountDTO);
    }

    /**
     * DELETE  /vehicle-accounts/:id : delete the "id" vehicleAccount.
     *
     * @param id the id of the vehicleAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-accounts/{id}")
    public ResponseEntity<Void> deleteVehicleAccount(@PathVariable Long id) {
        log.debug("REST request to delete VehicleAccount : {}", id);
        vehicleAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
