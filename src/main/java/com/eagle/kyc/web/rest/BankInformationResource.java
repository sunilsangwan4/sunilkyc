package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.BankInformationService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.BankInformationDTO;
import com.eagle.kyc.service.dto.BankInformationCriteria;
import com.eagle.kyc.service.BankInformationQueryService;
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
 * REST controller for managing BankInformation.
 */
@RestController
@RequestMapping("/api")
public class BankInformationResource {

    private final Logger log = LoggerFactory.getLogger(BankInformationResource.class);

    private static final String ENTITY_NAME = "bankInformation";

    private BankInformationService bankInformationService;

    private BankInformationQueryService bankInformationQueryService;

    public BankInformationResource(BankInformationService bankInformationService, BankInformationQueryService bankInformationQueryService) {
        this.bankInformationService = bankInformationService;
        this.bankInformationQueryService = bankInformationQueryService;
    }

    /**
     * POST  /bank-informations : Create a new bankInformation.
     *
     * @param bankInformationDTO the bankInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankInformationDTO, or with status 400 (Bad Request) if the bankInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bank-informations")
    @Timed
    public ResponseEntity<BankInformationDTO> createBankInformation(@Valid @RequestBody BankInformationDTO bankInformationDTO) throws URISyntaxException {
        log.debug("REST request to save BankInformation : {}", bankInformationDTO);
        if (bankInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankInformationDTO result = bankInformationService.save(bankInformationDTO);
        return ResponseEntity.created(new URI("/api/bank-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bank-informations : Updates an existing bankInformation.
     *
     * @param bankInformationDTO the bankInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bankInformationDTO,
     * or with status 400 (Bad Request) if the bankInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the bankInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bank-informations")
    @Timed
    public ResponseEntity<BankInformationDTO> updateBankInformation(@Valid @RequestBody BankInformationDTO bankInformationDTO) throws URISyntaxException {
        log.debug("REST request to update BankInformation : {}", bankInformationDTO);
        if (bankInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankInformationDTO result = bankInformationService.save(bankInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bankInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bank-informations : get all the bankInformations.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bankInformations in body
     */
    @GetMapping("/bank-informations")
    @Timed
    public ResponseEntity<List<BankInformationDTO>> getAllBankInformations(BankInformationCriteria criteria) {
        log.debug("REST request to get BankInformations by criteria: {}", criteria);
        List<BankInformationDTO> entityList = bankInformationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /bank-informations/count : count all the bankInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/bank-informations/count")
    @Timed
    public ResponseEntity<Long> countBankInformations(BankInformationCriteria criteria) {
        log.debug("REST request to count BankInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /bank-informations/:id : get the "id" bankInformation.
     *
     * @param id the id of the bankInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bankInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bank-informations/{id}")
    @Timed
    public ResponseEntity<BankInformationDTO> getBankInformation(@PathVariable Long id) {
        log.debug("REST request to get BankInformation : {}", id);
        Optional<BankInformationDTO> bankInformationDTO = bankInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankInformationDTO);
    }

    /**
     * DELETE  /bank-informations/:id : delete the "id" bankInformation.
     *
     * @param id the id of the bankInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bank-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteBankInformation(@PathVariable Long id) {
        log.debug("REST request to delete BankInformation : {}", id);
        bankInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
