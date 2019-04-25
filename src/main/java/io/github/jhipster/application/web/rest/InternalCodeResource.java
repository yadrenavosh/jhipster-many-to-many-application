package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.InternalCode;
import io.github.jhipster.application.service.InternalCodeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.InternalCodeCriteria;
import io.github.jhipster.application.service.InternalCodeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InternalCode.
 */
@RestController
@RequestMapping("/api")
public class InternalCodeResource {

    private final Logger log = LoggerFactory.getLogger(InternalCodeResource.class);

    private static final String ENTITY_NAME = "internalCode";

    private final InternalCodeService internalCodeService;

    private final InternalCodeQueryService internalCodeQueryService;

    public InternalCodeResource(InternalCodeService internalCodeService, InternalCodeQueryService internalCodeQueryService) {
        this.internalCodeService = internalCodeService;
        this.internalCodeQueryService = internalCodeQueryService;
    }

    /**
     * POST  /internal-codes : Create a new internalCode.
     *
     * @param internalCode the internalCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new internalCode, or with status 400 (Bad Request) if the internalCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/internal-codes")
    public ResponseEntity<InternalCode> createInternalCode(@Valid @RequestBody InternalCode internalCode) throws URISyntaxException {
        log.debug("REST request to save InternalCode : {}", internalCode);
        if (internalCode.getId() != null) {
            throw new BadRequestAlertException("A new internalCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalCode result = internalCodeService.save(internalCode);
        return ResponseEntity.created(new URI("/api/internal-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /internal-codes : Updates an existing internalCode.
     *
     * @param internalCode the internalCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated internalCode,
     * or with status 400 (Bad Request) if the internalCode is not valid,
     * or with status 500 (Internal Server Error) if the internalCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/internal-codes")
    public ResponseEntity<InternalCode> updateInternalCode(@Valid @RequestBody InternalCode internalCode) throws URISyntaxException {
        log.debug("REST request to update InternalCode : {}", internalCode);
        if (internalCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternalCode result = internalCodeService.save(internalCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, internalCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /internal-codes : get all the internalCodes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of internalCodes in body
     */
    @GetMapping("/internal-codes")
    public ResponseEntity<List<InternalCode>> getAllInternalCodes(InternalCodeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InternalCodes by criteria: {}", criteria);
        Page<InternalCode> page = internalCodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/internal-codes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /internal-codes/count : count all the internalCodes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/internal-codes/count")
    public ResponseEntity<Long> countInternalCodes(InternalCodeCriteria criteria) {
        log.debug("REST request to count InternalCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(internalCodeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /internal-codes/:id : get the "id" internalCode.
     *
     * @param id the id of the internalCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the internalCode, or with status 404 (Not Found)
     */
    @GetMapping("/internal-codes/{id}")
    public ResponseEntity<InternalCode> getInternalCode(@PathVariable Long id) {
        log.debug("REST request to get InternalCode : {}", id);
        Optional<InternalCode> internalCode = internalCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalCode);
    }

    /**
     * DELETE  /internal-codes/:id : delete the "id" internalCode.
     *
     * @param id the id of the internalCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/internal-codes/{id}")
    public ResponseEntity<Void> deleteInternalCode(@PathVariable Long id) {
        log.debug("REST request to delete InternalCode : {}", id);
        internalCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
