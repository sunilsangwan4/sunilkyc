package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.TokenVerificationService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.TokenVerificationDTO;
import com.eagle.kyc.service.dto.TokenVerificationCriteria;
import com.eagle.kyc.service.TokenVerificationQueryService;
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
 * REST controller for managing TokenVerification.
 */
@RestController
@RequestMapping("/api")
public class TokenVerificationResource {

    private final Logger log = LoggerFactory.getLogger(TokenVerificationResource.class);

    private static final String ENTITY_NAME = "tokenVerification";

    private TokenVerificationService tokenVerificationService;

    private TokenVerificationQueryService tokenVerificationQueryService;

    public TokenVerificationResource(TokenVerificationService tokenVerificationService, TokenVerificationQueryService tokenVerificationQueryService) {
        this.tokenVerificationService = tokenVerificationService;
        this.tokenVerificationQueryService = tokenVerificationQueryService;
    }

    /**
     * POST  /token-verifications : Create a new tokenVerification.
     *
     * @param tokenVerificationDTO the tokenVerificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tokenVerificationDTO, or with status 400 (Bad Request) if the tokenVerification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/token-verifications")
    @Timed
    public ResponseEntity<TokenVerificationDTO> createTokenVerification(@Valid @RequestBody TokenVerificationDTO tokenVerificationDTO) throws URISyntaxException {
        log.debug("REST request to save TokenVerification : {}", tokenVerificationDTO);
        if (tokenVerificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new tokenVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TokenVerificationDTO result = tokenVerificationService.save(tokenVerificationDTO);
        return ResponseEntity.created(new URI("/api/token-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /token-verifications : Updates an existing tokenVerification.
     *
     * @param tokenVerificationDTO the tokenVerificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tokenVerificationDTO,
     * or with status 400 (Bad Request) if the tokenVerificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the tokenVerificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/token-verifications")
    @Timed
    public ResponseEntity<TokenVerificationDTO> updateTokenVerification(@Valid @RequestBody TokenVerificationDTO tokenVerificationDTO) throws URISyntaxException {
        log.debug("REST request to update TokenVerification : {}", tokenVerificationDTO);
        if (tokenVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TokenVerificationDTO result = tokenVerificationService.save(tokenVerificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tokenVerificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /token-verifications : get all the tokenVerifications.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tokenVerifications in body
     */
    @GetMapping("/token-verifications")
    @Timed
    public ResponseEntity<List<TokenVerificationDTO>> getAllTokenVerifications(TokenVerificationCriteria criteria) {
        log.debug("REST request to get TokenVerifications by criteria: {}", criteria);
        List<TokenVerificationDTO> entityList = tokenVerificationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /token-verifications/count : count all the tokenVerifications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/token-verifications/count")
    @Timed
    public ResponseEntity<Long> countTokenVerifications(TokenVerificationCriteria criteria) {
        log.debug("REST request to count TokenVerifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(tokenVerificationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /token-verifications/:id : get the "id" tokenVerification.
     *
     * @param id the id of the tokenVerificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tokenVerificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/token-verifications/{id}")
    @Timed
    public ResponseEntity<TokenVerificationDTO> getTokenVerification(@PathVariable Long id) {
        log.debug("REST request to get TokenVerification : {}", id);
        Optional<TokenVerificationDTO> tokenVerificationDTO = tokenVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tokenVerificationDTO);
    }

    /**
     * DELETE  /token-verifications/:id : delete the "id" tokenVerification.
     *
     * @param id the id of the tokenVerificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/token-verifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteTokenVerification(@PathVariable Long id) {
        log.debug("REST request to delete TokenVerification : {}", id);
        tokenVerificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
