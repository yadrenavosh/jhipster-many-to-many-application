package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.ExternalCode;
import io.github.jhipster.application.service.ExternalCodeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ExternalCodeCriteria;
import io.github.jhipster.application.service.ExternalCodeQueryService;
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
 * REST controller for managing ExternalCode.
 */
@RestController
@RequestMapping("/api")
public class ExternalCodeResource {

    private final Logger log = LoggerFactory.getLogger(ExternalCodeResource.class);

    private static final String ENTITY_NAME = "externalCode";

    private final ExternalCodeService externalCodeService;

    private final ExternalCodeQueryService externalCodeQueryService;

    public ExternalCodeResource(ExternalCodeService externalCodeService, ExternalCodeQueryService externalCodeQueryService) {
        this.externalCodeService = externalCodeService;
        this.externalCodeQueryService = externalCodeQueryService;
    }

    /**
     * POST  /external-codes : Create a new externalCode.
     *
     * @param externalCode the externalCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new externalCode, or with status 400 (Bad Request) if the externalCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/external-codes")
    public ResponseEntity<ExternalCode> createExternalCode(@Valid @RequestBody ExternalCode externalCode) throws URISyntaxException {
        log.debug("REST request to save ExternalCode : {}", externalCode);
        if (externalCode.getId() != null) {
            throw new BadRequestAlertException("A new externalCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalCode result = externalCodeService.save(externalCode);
        return ResponseEntity.created(new URI("/api/external-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /external-codes : Updates an existing externalCode.
     *
     * @param externalCode the externalCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated externalCode,
     * or with status 400 (Bad Request) if the externalCode is not valid,
     * or with status 500 (Internal Server Error) if the externalCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-codes")
    public ResponseEntity<ExternalCode> updateExternalCode(@Valid @RequestBody ExternalCode externalCode) throws URISyntaxException {
        log.debug("REST request to update ExternalCode : {}", externalCode);
        if (externalCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExternalCode result = externalCodeService.save(externalCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, externalCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /external-codes : get all the externalCodes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of externalCodes in body
     */
    @GetMapping("/external-codes")
    public ResponseEntity<List<ExternalCode>> getAllExternalCodes(ExternalCodeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExternalCodes by criteria: {}", criteria);
        Page<ExternalCode> page = externalCodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/external-codes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /external-codes/count : count all the externalCodes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/external-codes/count")
    public ResponseEntity<Long> countExternalCodes(ExternalCodeCriteria criteria) {
        log.debug("REST request to count ExternalCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(externalCodeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /external-codes/:id : get the "id" externalCode.
     *
     * @param id the id of the externalCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the externalCode, or with status 404 (Not Found)
     */
    @GetMapping("/external-codes/{id}")
    public ResponseEntity<ExternalCode> getExternalCode(@PathVariable Long id) {
        log.debug("REST request to get ExternalCode : {}", id);
        Optional<ExternalCode> externalCode = externalCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(externalCode);
    }

    /**
     * DELETE  /external-codes/:id : delete the "id" externalCode.
     *
     * @param id the id of the externalCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/external-codes/{id}")
    public ResponseEntity<Void> deleteExternalCode(@PathVariable Long id) {
        log.debug("REST request to delete ExternalCode : {}", id);
        externalCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
