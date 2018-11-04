package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.EmailVerificationService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.EmailVerificationDTO;
import com.eagle.kyc.service.dto.EmailVerificationCriteria;
import com.eagle.kyc.service.EmailVerificationQueryService;
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
 * REST controller for managing EmailVerification.
 */
@RestController
@RequestMapping("/api")
public class EmailVerificationResource {

    private final Logger log = LoggerFactory.getLogger(EmailVerificationResource.class);

    private static final String ENTITY_NAME = "emailVerification";

    private EmailVerificationService emailVerificationService;

    private EmailVerificationQueryService emailVerificationQueryService;

    public EmailVerificationResource(EmailVerificationService emailVerificationService, EmailVerificationQueryService emailVerificationQueryService) {
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationQueryService = emailVerificationQueryService;
    }

    /**
     * POST  /email-verifications : Create a new emailVerification.
     *
     * @param emailVerificationDTO the emailVerificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailVerificationDTO, or with status 400 (Bad Request) if the emailVerification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-verifications")
    @Timed
    public ResponseEntity<EmailVerificationDTO> createEmailVerification(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) throws URISyntaxException {
        log.debug("REST request to save EmailVerification : {}", emailVerificationDTO);
        if (emailVerificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailVerificationDTO result = emailVerificationService.save(emailVerificationDTO);
        return ResponseEntity.created(new URI("/api/email-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-verifications : Updates an existing emailVerification.
     *
     * @param emailVerificationDTO the emailVerificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailVerificationDTO,
     * or with status 400 (Bad Request) if the emailVerificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailVerificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-verifications")
    @Timed
    public ResponseEntity<EmailVerificationDTO> updateEmailVerification(@Valid @RequestBody EmailVerificationDTO emailVerificationDTO) throws URISyntaxException {
        log.debug("REST request to update EmailVerification : {}", emailVerificationDTO);
        if (emailVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailVerificationDTO result = emailVerificationService.save(emailVerificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailVerificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-verifications : get all the emailVerifications.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of emailVerifications in body
     */
    @GetMapping("/email-verifications")
    @Timed
    public ResponseEntity<List<EmailVerificationDTO>> getAllEmailVerifications(EmailVerificationCriteria criteria) {
        log.debug("REST request to get EmailVerifications by criteria: {}", criteria);
        List<EmailVerificationDTO> entityList = emailVerificationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /email-verifications/count : count all the emailVerifications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/email-verifications/count")
    @Timed
    public ResponseEntity<Long> countEmailVerifications(EmailVerificationCriteria criteria) {
        log.debug("REST request to count EmailVerifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(emailVerificationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /email-verifications/:id : get the "id" emailVerification.
     *
     * @param id the id of the emailVerificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailVerificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/email-verifications/{id}")
    @Timed
    public ResponseEntity<EmailVerificationDTO> getEmailVerification(@PathVariable Long id) {
        log.debug("REST request to get EmailVerification : {}", id);
        Optional<EmailVerificationDTO> emailVerificationDTO = emailVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailVerificationDTO);
    }

    /**
     * DELETE  /email-verifications/:id : delete the "id" emailVerification.
     *
     * @param id the id of the emailVerificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-verifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailVerification(@PathVariable Long id) {
        log.debug("REST request to delete EmailVerification : {}", id);
        emailVerificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
