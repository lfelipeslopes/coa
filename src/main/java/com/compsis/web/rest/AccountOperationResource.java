package com.compsis.web.rest;
import com.compsis.service.AccountOperationService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.web.rest.util.PaginationUtil;
import com.compsis.service.dto.AccountOperationDTO;
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
 * REST controller for managing AccountOperation.
 */
@RestController
@RequestMapping("/api")
public class AccountOperationResource {

    private final Logger log = LoggerFactory.getLogger(AccountOperationResource.class);

    private static final String ENTITY_NAME = "accountOperation";

    private final AccountOperationService accountOperationService;

    public AccountOperationResource(AccountOperationService accountOperationService) {
        this.accountOperationService = accountOperationService;
    }

    /**
     * POST  /account-operations : Create a new accountOperation.
     *
     * @param accountOperationDTO the accountOperationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountOperationDTO, or with status 400 (Bad Request) if the accountOperation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-operations")
    public ResponseEntity<AccountOperationDTO> createAccountOperation(@RequestBody AccountOperationDTO accountOperationDTO) throws URISyntaxException {
        log.debug("REST request to save AccountOperation : {}", accountOperationDTO);
        if (accountOperationDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountOperationDTO result = accountOperationService.save(accountOperationDTO);
        return ResponseEntity.created(new URI("/api/account-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-operations : Updates an existing accountOperation.
     *
     * @param accountOperationDTO the accountOperationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountOperationDTO,
     * or with status 400 (Bad Request) if the accountOperationDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountOperationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-operations")
    public ResponseEntity<AccountOperationDTO> updateAccountOperation(@RequestBody AccountOperationDTO accountOperationDTO) throws URISyntaxException {
        log.debug("REST request to update AccountOperation : {}", accountOperationDTO);
        if (accountOperationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountOperationDTO result = accountOperationService.save(accountOperationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountOperationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-operations : get all the accountOperations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accountOperations in body
     */
    @GetMapping("/account-operations")
    public ResponseEntity<List<AccountOperationDTO>> getAllAccountOperations(Pageable pageable) {
        log.debug("REST request to get a page of AccountOperations");
        Page<AccountOperationDTO> page = accountOperationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account-operations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /account-operations/:id : get the "id" accountOperation.
     *
     * @param id the id of the accountOperationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountOperationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/account-operations/{id}")
    public ResponseEntity<AccountOperationDTO> getAccountOperation(@PathVariable Long id) {
        log.debug("REST request to get AccountOperation : {}", id);
        Optional<AccountOperationDTO> accountOperationDTO = accountOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountOperationDTO);
    }

    /**
     * DELETE  /account-operations/:id : delete the "id" accountOperation.
     *
     * @param id the id of the accountOperationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-operations/{id}")
    public ResponseEntity<Void> deleteAccountOperation(@PathVariable Long id) {
        log.debug("REST request to delete AccountOperation : {}", id);
        accountOperationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
