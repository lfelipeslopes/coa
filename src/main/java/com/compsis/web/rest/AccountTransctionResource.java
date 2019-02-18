package com.compsis.web.rest;
import com.compsis.service.AccountTransctionService;
import com.compsis.web.rest.errors.BadRequestAlertException;
import com.compsis.web.rest.util.HeaderUtil;
import com.compsis.service.dto.AccountTransctionDTO;
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
 * REST controller for managing AccountTransction.
 */
@RestController
@RequestMapping("/api")
public class AccountTransctionResource {

    private final Logger log = LoggerFactory.getLogger(AccountTransctionResource.class);

    private static final String ENTITY_NAME = "accountTransction";

    private final AccountTransctionService accountTransctionService;

    public AccountTransctionResource(AccountTransctionService accountTransctionService) {
        this.accountTransctionService = accountTransctionService;
    }

    /**
     * POST  /account-transctions : Create a new accountTransction.
     *
     * @param accountTransctionDTO the accountTransctionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountTransctionDTO, or with status 400 (Bad Request) if the accountTransction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-transctions")
    public ResponseEntity<AccountTransctionDTO> createAccountTransction(@RequestBody AccountTransctionDTO accountTransctionDTO) throws URISyntaxException {
        log.debug("REST request to save AccountTransction : {}", accountTransctionDTO);
        if (accountTransctionDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountTransction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountTransctionDTO result = accountTransctionService.save(accountTransctionDTO);
        return ResponseEntity.created(new URI("/api/account-transctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-transctions : Updates an existing accountTransction.
     *
     * @param accountTransctionDTO the accountTransctionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountTransctionDTO,
     * or with status 400 (Bad Request) if the accountTransctionDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountTransctionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-transctions")
    public ResponseEntity<AccountTransctionDTO> updateAccountTransction(@RequestBody AccountTransctionDTO accountTransctionDTO) throws URISyntaxException {
        log.debug("REST request to update AccountTransction : {}", accountTransctionDTO);
        if (accountTransctionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountTransctionDTO result = accountTransctionService.save(accountTransctionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountTransctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-transctions : get all the accountTransctions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accountTransctions in body
     */
    @GetMapping("/account-transctions")
    public List<AccountTransctionDTO> getAllAccountTransctions() {
        log.debug("REST request to get all AccountTransctions");
        return accountTransctionService.findAll();
    }

    /**
     * GET  /account-transctions/:id : get the "id" accountTransction.
     *
     * @param id the id of the accountTransctionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountTransctionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/account-transctions/{id}")
    public ResponseEntity<AccountTransctionDTO> getAccountTransction(@PathVariable Long id) {
        log.debug("REST request to get AccountTransction : {}", id);
        Optional<AccountTransctionDTO> accountTransctionDTO = accountTransctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountTransctionDTO);
    }

    /**
     * DELETE  /account-transctions/:id : delete the "id" accountTransction.
     *
     * @param id the id of the accountTransctionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-transctions/{id}")
    public ResponseEntity<Void> deleteAccountTransction(@PathVariable Long id) {
        log.debug("REST request to delete AccountTransction : {}", id);
        accountTransctionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
