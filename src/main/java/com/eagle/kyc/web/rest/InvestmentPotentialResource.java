package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.InvestmentPotentialService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.InvestmentPotentialDTO;
import com.eagle.kyc.service.dto.InvestmentPotentialCriteria;
import com.eagle.kyc.service.InvestmentPotentialQueryService;
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
 * REST controller for managing InvestmentPotential.
 */
@RestController
@RequestMapping("/api")
public class InvestmentPotentialResource {

    private final Logger log = LoggerFactory.getLogger(InvestmentPotentialResource.class);

    private static final String ENTITY_NAME = "investmentPotential";

    private InvestmentPotentialService investmentPotentialService;

    private InvestmentPotentialQueryService investmentPotentialQueryService;

    public InvestmentPotentialResource(InvestmentPotentialService investmentPotentialService, InvestmentPotentialQueryService investmentPotentialQueryService) {
        this.investmentPotentialService = investmentPotentialService;
        this.investmentPotentialQueryService = investmentPotentialQueryService;
    }

    /**
     * POST  /investment-potentials : Create a new investmentPotential.
     *
     * @param investmentPotentialDTO the investmentPotentialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investmentPotentialDTO, or with status 400 (Bad Request) if the investmentPotential has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investment-potentials")
    @Timed
    public ResponseEntity<InvestmentPotentialDTO> createInvestmentPotential(@Valid @RequestBody InvestmentPotentialDTO investmentPotentialDTO) throws URISyntaxException {
        log.debug("REST request to save InvestmentPotential : {}", investmentPotentialDTO);
        if (investmentPotentialDTO.getId() != null) {
            throw new BadRequestAlertException("A new investmentPotential cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvestmentPotentialDTO result = investmentPotentialService.save(investmentPotentialDTO);
        return ResponseEntity.created(new URI("/api/investment-potentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investment-potentials : Updates an existing investmentPotential.
     *
     * @param investmentPotentialDTO the investmentPotentialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investmentPotentialDTO,
     * or with status 400 (Bad Request) if the investmentPotentialDTO is not valid,
     * or with status 500 (Internal Server Error) if the investmentPotentialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investment-potentials")
    @Timed
    public ResponseEntity<InvestmentPotentialDTO> updateInvestmentPotential(@Valid @RequestBody InvestmentPotentialDTO investmentPotentialDTO) throws URISyntaxException {
        log.debug("REST request to update InvestmentPotential : {}", investmentPotentialDTO);
        if (investmentPotentialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvestmentPotentialDTO result = investmentPotentialService.save(investmentPotentialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, investmentPotentialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investment-potentials : get all the investmentPotentials.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of investmentPotentials in body
     */
    @GetMapping("/investment-potentials")
    @Timed
    public ResponseEntity<List<InvestmentPotentialDTO>> getAllInvestmentPotentials(InvestmentPotentialCriteria criteria) {
        log.debug("REST request to get InvestmentPotentials by criteria: {}", criteria);
        List<InvestmentPotentialDTO> entityList = investmentPotentialQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /investment-potentials/count : count all the investmentPotentials.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/investment-potentials/count")
    @Timed
    public ResponseEntity<Long> countInvestmentPotentials(InvestmentPotentialCriteria criteria) {
        log.debug("REST request to count InvestmentPotentials by criteria: {}", criteria);
        return ResponseEntity.ok().body(investmentPotentialQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /investment-potentials/:id : get the "id" investmentPotential.
     *
     * @param id the id of the investmentPotentialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investmentPotentialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/investment-potentials/{id}")
    @Timed
    public ResponseEntity<InvestmentPotentialDTO> getInvestmentPotential(@PathVariable Long id) {
        log.debug("REST request to get InvestmentPotential : {}", id);
        Optional<InvestmentPotentialDTO> investmentPotentialDTO = investmentPotentialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(investmentPotentialDTO);
    }

    /**
     * DELETE  /investment-potentials/:id : delete the "id" investmentPotential.
     *
     * @param id the id of the investmentPotentialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investment-potentials/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestmentPotential(@PathVariable Long id) {
        log.debug("REST request to delete InvestmentPotential : {}", id);
        investmentPotentialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
