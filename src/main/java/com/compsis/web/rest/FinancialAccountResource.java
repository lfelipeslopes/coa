package com.compsis.web.rest;
import com.compsis.service.FinancialAccountService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.web.rest.util.PaginationUtil;
import com.compsis.service.dto.FinancialAccountDTO;
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
 * REST controller for managing FinancialAccount.
 */
@RestController
@RequestMapping("/api")
public class FinancialAccountResource {

    private final Logger log = LoggerFactory.getLogger(FinancialAccountResource.class);

    private static final String ENTITY_NAME = "financialAccount";

    private final FinancialAccountService financialAccountService;

    public FinancialAccountResource(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    /**
     * POST  /financial-accounts : Create a new financialAccount.
     *
     * @param financialAccountDTO the financialAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialAccountDTO, or with status 400 (Bad Request) if the financialAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-accounts")
    public ResponseEntity<FinancialAccountDTO> createFinancialAccount(@RequestBody FinancialAccountDTO financialAccountDTO) throws URISyntaxException {
        log.debug("REST request to save FinancialAccount : {}", financialAccountDTO);
        if (financialAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new financialAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialAccountDTO result = financialAccountService.save(financialAccountDTO);
        return ResponseEntity.created(new URI("/api/financial-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-accounts : Updates an existing financialAccount.
     *
     * @param financialAccountDTO the financialAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialAccountDTO,
     * or with status 400 (Bad Request) if the financialAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the financialAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-accounts")
    public ResponseEntity<FinancialAccountDTO> updateFinancialAccount(@RequestBody FinancialAccountDTO financialAccountDTO) throws URISyntaxException {
        log.debug("REST request to update FinancialAccount : {}", financialAccountDTO);
        if (financialAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinancialAccountDTO result = financialAccountService.save(financialAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /financial-accounts : get all the financialAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of financialAccounts in body
     */
    @GetMapping("/financial-accounts")
    public ResponseEntity<List<FinancialAccountDTO>> getAllFinancialAccounts(Pageable pageable) {
        log.debug("REST request to get a page of FinancialAccounts");
        Page<FinancialAccountDTO> page = financialAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/financial-accounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /financial-accounts/:id : get the "id" financialAccount.
     *
     * @param id the id of the financialAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financialAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/financial-accounts/{id}")
    public ResponseEntity<FinancialAccountDTO> getFinancialAccount(@PathVariable Long id) {
        log.debug("REST request to get FinancialAccount : {}", id);
        Optional<FinancialAccountDTO> financialAccountDTO = financialAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financialAccountDTO);
    }

    /**
     * DELETE  /financial-accounts/:id : delete the "id" financialAccount.
     *
     * @param id the id of the financialAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/financial-accounts/{id}")
    public ResponseEntity<Void> deleteFinancialAccount(@PathVariable Long id) {
        log.debug("REST request to delete FinancialAccount : {}", id);
        financialAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
