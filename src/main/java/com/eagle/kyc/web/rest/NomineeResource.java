package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.NomineeService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.NomineeDTO;
import com.eagle.kyc.service.dto.NomineeCriteria;
import com.eagle.kyc.service.NomineeQueryService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Nominee.
 */
@RestController
@RequestMapping("/api")
public class NomineeResource {

    private final Logger log = LoggerFactory.getLogger(NomineeResource.class);

    private static final String ENTITY_NAME = "nominee";

    private NomineeService nomineeService;

    private NomineeQueryService nomineeQueryService;

    public NomineeResource(NomineeService nomineeService, NomineeQueryService nomineeQueryService) {
        this.nomineeService = nomineeService;
        this.nomineeQueryService = nomineeQueryService;
    }

    /**
     * POST  /nominees : Create a new nominee.
     *
     * @param nomineeDTO the nomineeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nomineeDTO, or with status 400 (Bad Request) if the nominee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nominees")
    @Timed
    public ResponseEntity<NomineeDTO> createNominee(@Valid @RequestBody NomineeDTO nomineeDTO) throws URISyntaxException {
        log.debug("REST request to save Nominee : {}", nomineeDTO);
        if (nomineeDTO.getId() != null) {
            throw new BadRequestAlertException("A new nominee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NomineeDTO result = nomineeService.save(nomineeDTO);
        return ResponseEntity.created(new URI("/api/nominees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nominees : Updates an existing nominee.
     *
     * @param nomineeDTO the nomineeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nomineeDTO,
     * or with status 400 (Bad Request) if the nomineeDTO is not valid,
     * or with status 500 (Internal Server Error) if the nomineeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nominees")
    @Timed
    public ResponseEntity<NomineeDTO> updateNominee(@Valid @RequestBody NomineeDTO nomineeDTO) throws URISyntaxException {
        log.debug("REST request to update Nominee : {}", nomineeDTO);
        if (nomineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NomineeDTO result = nomineeService.save(nomineeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nomineeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nominees : get all the nominees.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of nominees in body
     */
    @GetMapping("/nominees")
    @Timed
    public ResponseEntity<List<NomineeDTO>> getAllNominees(NomineeCriteria criteria) {
        log.debug("REST request to get Nominees by criteria: {}", criteria);
        List<NomineeDTO> entityList = nomineeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /nominees/count : count all the nominees.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/nominees/count")
    @Timed
    public ResponseEntity<Long> countNominees(NomineeCriteria criteria) {
        log.debug("REST request to count Nominees by criteria: {}", criteria);
        return ResponseEntity.ok().body(nomineeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /nominees/:id : get the "id" nominee.
     *
     * @param id the id of the nomineeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nomineeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/nominees/{id}")
    @Timed
    public ResponseEntity<NomineeDTO> getNominee(@PathVariable Long id) {
        log.debug("REST request to get Nominee : {}", id);
        Optional<NomineeDTO> nomineeDTO = nomineeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nomineeDTO);
    }

    /**
     * DELETE  /nominees/:id : delete the "id" nominee.
     *
     * @param id the id of the nomineeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nominees/{id}")
    @Timed
    public ResponseEntity<Void> deleteNominee(@PathVariable Long id) {
        log.debug("REST request to delete Nominee : {}", id);
        nomineeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
