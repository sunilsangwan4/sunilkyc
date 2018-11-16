package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.ApplicationProspectService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.web.rest.util.PaginationUtil;
import com.eagle.kyc.service.dto.ApplicationProspectDTO;
import com.eagle.kyc.service.dto.ApplicationProspectCriteria;
import com.eagle.kyc.service.ApplicationProspectQueryService;
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
 * REST controller for managing ApplicationProspect.
 */
@RestController
@RequestMapping("/api")
public class ApplicationProspectResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationProspectResource.class);

    private static final String ENTITY_NAME = "applicationProspect";

    private ApplicationProspectService applicationProspectService;

    private ApplicationProspectQueryService applicationProspectQueryService;

    public ApplicationProspectResource(ApplicationProspectService applicationProspectService, ApplicationProspectQueryService applicationProspectQueryService) {
        this.applicationProspectService = applicationProspectService;
        this.applicationProspectQueryService = applicationProspectQueryService;
    }

    /**
     * POST  /application-prospects : Create a new applicationProspect.
     *
     * @param applicationProspectDTO the applicationProspectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationProspectDTO, or with status 400 (Bad Request) if the applicationProspect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-prospects")
    @Timed
    public ResponseEntity<ApplicationProspectDTO> createApplicationProspect(@Valid @RequestBody ApplicationProspectDTO applicationProspectDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationProspect : {}", applicationProspectDTO);
        if (applicationProspectDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationProspect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationProspectDTO result = applicationProspectService.save(applicationProspectDTO);
        return ResponseEntity.created(new URI("/api/application-prospects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-prospects : Updates an existing applicationProspect.
     *
     * @param applicationProspectDTO the applicationProspectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationProspectDTO,
     * or with status 400 (Bad Request) if the applicationProspectDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationProspectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-prospects")
    @Timed
    public ResponseEntity<ApplicationProspectDTO> updateApplicationProspect(@Valid @RequestBody ApplicationProspectDTO applicationProspectDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationProspect : {}", applicationProspectDTO);
        if (applicationProspectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationProspectDTO result = applicationProspectService.save(applicationProspectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationProspectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-prospects : get all the applicationProspects.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of applicationProspects in body
     */
    @GetMapping("/application-prospects")
    @Timed
    public ResponseEntity<List<ApplicationProspectDTO>> getAllApplicationProspects(ApplicationProspectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicationProspects by criteria: {}", criteria);
        Page<ApplicationProspectDTO> page = applicationProspectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/application-prospects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /application-prospects/count : count all the applicationProspects.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/application-prospects/count")
    @Timed
    public ResponseEntity<Long> countApplicationProspects(ApplicationProspectCriteria criteria) {
        log.debug("REST request to count ApplicationProspects by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationProspectQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /application-prospects/:id : get the "id" applicationProspect.
     *
     * @param id the id of the applicationProspectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationProspectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-prospects/{id}")
    @Timed
    public ResponseEntity<ApplicationProspectDTO> getApplicationProspect(@PathVariable Long id) {
        log.debug("REST request to get ApplicationProspect : {}", id);
        Optional<ApplicationProspectDTO> applicationProspectDTO = applicationProspectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationProspectDTO);
    }

    /**
     * DELETE  /application-prospects/:id : delete the "id" applicationProspect.
     *
     * @param id the id of the applicationProspectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-prospects/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationProspect(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationProspect : {}", id);
        applicationProspectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
