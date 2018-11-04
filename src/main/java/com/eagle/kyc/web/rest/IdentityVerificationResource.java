package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.IdentityVerificationService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.IdentityVerificationDTO;
import com.eagle.kyc.service.dto.IdentityVerificationCriteria;
import com.eagle.kyc.service.IdentityVerificationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IdentityVerification.
 */
@RestController
@RequestMapping("/api")
public class IdentityVerificationResource {

    private final Logger log = LoggerFactory.getLogger(IdentityVerificationResource.class);

    private static final String ENTITY_NAME = "identityVerification";

    private IdentityVerificationService identityVerificationService;

    private IdentityVerificationQueryService identityVerificationQueryService;

    public IdentityVerificationResource(IdentityVerificationService identityVerificationService, IdentityVerificationQueryService identityVerificationQueryService) {
        this.identityVerificationService = identityVerificationService;
        this.identityVerificationQueryService = identityVerificationQueryService;
    }

    /**
     * POST  /identity-verifications : Create a new identityVerification.
     *
     * @param identityVerificationDTO the identityVerificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new identityVerificationDTO, or with status 400 (Bad Request) if the identityVerification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/identity-verifications")
    @Timed
    public ResponseEntity<IdentityVerificationDTO> createIdentityVerification(@Valid @RequestBody IdentityVerificationDTO identityVerificationDTO) throws URISyntaxException {
        log.debug("REST request to save IdentityVerification : {}", identityVerificationDTO);
        if (identityVerificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new identityVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdentityVerificationDTO result = identityVerificationService.save(identityVerificationDTO);
        return ResponseEntity.created(new URI("/api/identity-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /identity-verifications : Updates an existing identityVerification.
     *
     * @param identityVerificationDTO the identityVerificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated identityVerificationDTO,
     * or with status 400 (Bad Request) if the identityVerificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the identityVerificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/identity-verifications")
    @Timed
    public ResponseEntity<IdentityVerificationDTO> updateIdentityVerification(@Valid @RequestBody IdentityVerificationDTO identityVerificationDTO) throws URISyntaxException {
        log.debug("REST request to update IdentityVerification : {}", identityVerificationDTO);
        if (identityVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdentityVerificationDTO result = identityVerificationService.save(identityVerificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, identityVerificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /identity-verifications : get all the identityVerifications.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of identityVerifications in body
     */
    @GetMapping("/identity-verifications")
    @Timed
    public ResponseEntity<List<IdentityVerificationDTO>> getAllIdentityVerifications(IdentityVerificationCriteria criteria) {
        log.debug("REST request to get IdentityVerifications by criteria: {}", criteria);
        List<IdentityVerificationDTO> entityList = identityVerificationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /identity-verifications/count : count all the identityVerifications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/identity-verifications/count")
    @Timed
    public ResponseEntity<Long> countIdentityVerifications(IdentityVerificationCriteria criteria) {
        log.debug("REST request to count IdentityVerifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(identityVerificationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /identity-verifications/:id : get the "id" identityVerification.
     *
     * @param id the id of the identityVerificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the identityVerificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/identity-verifications/{id}")
    @Timed
    public ResponseEntity<IdentityVerificationDTO> getIdentityVerification(@PathVariable Long id) {
        log.debug("REST request to get IdentityVerification : {}", id);
        Optional<IdentityVerificationDTO> identityVerificationDTO = identityVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(identityVerificationDTO);
    }

    /**
     * DELETE  /identity-verifications/:id : delete the "id" identityVerification.
     *
     * @param id the id of the identityVerificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/identity-verifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdentityVerification(@PathVariable Long id) {
        log.debug("REST request to delete IdentityVerification : {}", id);
        identityVerificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
