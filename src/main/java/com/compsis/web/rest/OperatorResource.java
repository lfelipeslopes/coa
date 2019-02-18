package com.compsis.web.rest;
import com.compsis.service.OperatorService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.web.rest.util.PaginationUtil;
import com.compsis.service.dto.OperatorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Operator.
 */
@RestController
@RequestMapping("/api")
public class OperatorResource {

    private final Logger log = LoggerFactory.getLogger(OperatorResource.class);

    private static final String ENTITY_NAME = "operator";

    private final OperatorService operatorService;

    public OperatorResource(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /**
     * POST  /operators : Create a new operator.
     *
     * @param operatorDTO the operatorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operatorDTO, or with status 400 (Bad Request) if the operator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operators")
    public ResponseEntity<OperatorDTO> createOperator(@RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to save Operator : {}", operatorDTO);
        if (operatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new operator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperatorDTO result = operatorService.save(operatorDTO);
        return ResponseEntity.created(new URI("/api/operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operators : Updates an existing operator.
     *
     * @param operatorDTO the operatorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operatorDTO,
     * or with status 400 (Bad Request) if the operatorDTO is not valid,
     * or with status 500 (Internal Server Error) if the operatorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operators")
    public ResponseEntity<OperatorDTO> updateOperator(@RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to update Operator : {}", operatorDTO);
        if (operatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperatorDTO result = operatorService.save(operatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operators : get all the operators.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operators in body
     */
    @GetMapping("/operators")
    public ResponseEntity<List<OperatorDTO>> getAllOperators(Pageable pageable) {
        log.debug("REST request to get a page of Operators");
        Page<OperatorDTO> page = operatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operators");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /operators/:id : get the "id" operator.
     *
     * @param id the id of the operatorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operatorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operators/{id}")
    public ResponseEntity<OperatorDTO> getOperator(@PathVariable Long id) {
        log.debug("REST request to get Operator : {}", id);
        Optional<OperatorDTO> operatorDTO = operatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operatorDTO);
    }

    /**
     * DELETE  /operators/:id : delete the "id" operator.
     *
     * @param id the id of the operatorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operators/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        log.debug("REST request to delete Operator : {}", id);
        operatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
