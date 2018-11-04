package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.PersonalInformationService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.PersonalInformationDTO;
import com.eagle.kyc.service.dto.PersonalInformationCriteria;
import com.eagle.kyc.service.PersonalInformationQueryService;
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
 * REST controller for managing PersonalInformation.
 */
@RestController
@RequestMapping("/api")
public class PersonalInformationResource {

    private final Logger log = LoggerFactory.getLogger(PersonalInformationResource.class);

    private static final String ENTITY_NAME = "personalInformation";

    private PersonalInformationService personalInformationService;

    private PersonalInformationQueryService personalInformationQueryService;

    public PersonalInformationResource(PersonalInformationService personalInformationService, PersonalInformationQueryService personalInformationQueryService) {
        this.personalInformationService = personalInformationService;
        this.personalInformationQueryService = personalInformationQueryService;
    }

    /**
     * POST  /personal-informations : Create a new personalInformation.
     *
     * @param personalInformationDTO the personalInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalInformationDTO, or with status 400 (Bad Request) if the personalInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personal-informations")
    @Timed
    public ResponseEntity<PersonalInformationDTO> createPersonalInformation(@Valid @RequestBody PersonalInformationDTO personalInformationDTO) throws URISyntaxException {
        log.debug("REST request to save PersonalInformation : {}", personalInformationDTO);
        if (personalInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalInformationDTO result = personalInformationService.save(personalInformationDTO);
        return ResponseEntity.created(new URI("/api/personal-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personal-informations : Updates an existing personalInformation.
     *
     * @param personalInformationDTO the personalInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalInformationDTO,
     * or with status 400 (Bad Request) if the personalInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the personalInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personal-informations")
    @Timed
    public ResponseEntity<PersonalInformationDTO> updatePersonalInformation(@Valid @RequestBody PersonalInformationDTO personalInformationDTO) throws URISyntaxException {
        log.debug("REST request to update PersonalInformation : {}", personalInformationDTO);
        if (personalInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalInformationDTO result = personalInformationService.save(personalInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personalInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personal-informations : get all the personalInformations.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of personalInformations in body
     */
    @GetMapping("/personal-informations")
    @Timed
    public ResponseEntity<List<PersonalInformationDTO>> getAllPersonalInformations(PersonalInformationCriteria criteria) {
        log.debug("REST request to get PersonalInformations by criteria: {}", criteria);
        List<PersonalInformationDTO> entityList = personalInformationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /personal-informations/count : count all the personalInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/personal-informations/count")
    @Timed
    public ResponseEntity<Long> countPersonalInformations(PersonalInformationCriteria criteria) {
        log.debug("REST request to count PersonalInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(personalInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /personal-informations/:id : get the "id" personalInformation.
     *
     * @param id the id of the personalInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personal-informations/{id}")
    @Timed
    public ResponseEntity<PersonalInformationDTO> getPersonalInformation(@PathVariable Long id) {
        log.debug("REST request to get PersonalInformation : {}", id);
        Optional<PersonalInformationDTO> personalInformationDTO = personalInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalInformationDTO);
    }

    /**
     * DELETE  /personal-informations/:id : delete the "id" personalInformation.
     *
     * @param id the id of the personalInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personal-informations/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonalInformation(@PathVariable Long id) {
        log.debug("REST request to delete PersonalInformation : {}", id);
        personalInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
