package com.compsis.web.rest;
import com.compsis.service.VehicleClassService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.VehicleClassDTO;
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
 * REST controller for managing VehicleClass.
 */
@RestController
@RequestMapping("/api")
public class VehicleClassResource {

    private final Logger log = LoggerFactory.getLogger(VehicleClassResource.class);

    private static final String ENTITY_NAME = "vehicleClass";

    private final VehicleClassService vehicleClassService;

    public VehicleClassResource(VehicleClassService vehicleClassService) {
        this.vehicleClassService = vehicleClassService;
    }

    /**
     * POST  /vehicle-classes : Create a new vehicleClass.
     *
     * @param vehicleClassDTO the vehicleClassDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleClassDTO, or with status 400 (Bad Request) if the vehicleClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-classes")
    public ResponseEntity<VehicleClassDTO> createVehicleClass(@RequestBody VehicleClassDTO vehicleClassDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleClass : {}", vehicleClassDTO);
        if (vehicleClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleClassDTO result = vehicleClassService.save(vehicleClassDTO);
        return ResponseEntity.created(new URI("/api/vehicle-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-classes : Updates an existing vehicleClass.
     *
     * @param vehicleClassDTO the vehicleClassDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleClassDTO,
     * or with status 400 (Bad Request) if the vehicleClassDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleClassDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-classes")
    public ResponseEntity<VehicleClassDTO> updateVehicleClass(@RequestBody VehicleClassDTO vehicleClassDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleClass : {}", vehicleClassDTO);
        if (vehicleClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleClassDTO result = vehicleClassService.save(vehicleClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-classes : get all the vehicleClasses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleClasses in body
     */
    @GetMapping("/vehicle-classes")
    public List<VehicleClassDTO> getAllVehicleClasses() {
        log.debug("REST request to get all VehicleClasses");
        return vehicleClassService.findAll();
    }

    /**
     * GET  /vehicle-classes/:id : get the "id" vehicleClass.
     *
     * @param id the id of the vehicleClassDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleClassDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-classes/{id}")
    public ResponseEntity<VehicleClassDTO> getVehicleClass(@PathVariable Long id) {
        log.debug("REST request to get VehicleClass : {}", id);
        Optional<VehicleClassDTO> vehicleClassDTO = vehicleClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleClassDTO);
    }

    /**
     * DELETE  /vehicle-classes/:id : delete the "id" vehicleClass.
     *
     * @param id the id of the vehicleClassDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-classes/{id}")
    public ResponseEntity<Void> deleteVehicleClass(@PathVariable Long id) {
        log.debug("REST request to delete VehicleClass : {}", id);
        vehicleClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
