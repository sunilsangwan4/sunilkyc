package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.DepositoryInfoService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.DepositoryInfoDTO;
import com.eagle.kyc.service.dto.DepositoryInfoCriteria;
import com.eagle.kyc.service.DepositoryInfoQueryService;
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
 * REST controller for managing DepositoryInfo.
 */
@RestController
@RequestMapping("/api")
public class DepositoryInfoResource {

    private final Logger log = LoggerFactory.getLogger(DepositoryInfoResource.class);

    private static final String ENTITY_NAME = "depositoryInfo";

    private DepositoryInfoService depositoryInfoService;

    private DepositoryInfoQueryService depositoryInfoQueryService;

    public DepositoryInfoResource(DepositoryInfoService depositoryInfoService, DepositoryInfoQueryService depositoryInfoQueryService) {
        this.depositoryInfoService = depositoryInfoService;
        this.depositoryInfoQueryService = depositoryInfoQueryService;
    }

    /**
     * POST  /depository-infos : Create a new depositoryInfo.
     *
     * @param depositoryInfoDTO the depositoryInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depositoryInfoDTO, or with status 400 (Bad Request) if the depositoryInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depository-infos")
    @Timed
    public ResponseEntity<DepositoryInfoDTO> createDepositoryInfo(@Valid @RequestBody DepositoryInfoDTO depositoryInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DepositoryInfo : {}", depositoryInfoDTO);
        if (depositoryInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new depositoryInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositoryInfoDTO result = depositoryInfoService.save(depositoryInfoDTO);
        return ResponseEntity.created(new URI("/api/depository-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depository-infos : Updates an existing depositoryInfo.
     *
     * @param depositoryInfoDTO the depositoryInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depositoryInfoDTO,
     * or with status 400 (Bad Request) if the depositoryInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the depositoryInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depository-infos")
    @Timed
    public ResponseEntity<DepositoryInfoDTO> updateDepositoryInfo(@Valid @RequestBody DepositoryInfoDTO depositoryInfoDTO) throws URISyntaxException {
        log.debug("REST request to update DepositoryInfo : {}", depositoryInfoDTO);
        if (depositoryInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepositoryInfoDTO result = depositoryInfoService.save(depositoryInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depositoryInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depository-infos : get all the depositoryInfos.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of depositoryInfos in body
     */
    @GetMapping("/depository-infos")
    @Timed
    public ResponseEntity<List<DepositoryInfoDTO>> getAllDepositoryInfos(DepositoryInfoCriteria criteria) {
        log.debug("REST request to get DepositoryInfos by criteria: {}", criteria);
        List<DepositoryInfoDTO> entityList = depositoryInfoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /depository-infos/count : count all the depositoryInfos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/depository-infos/count")
    @Timed
    public ResponseEntity<Long> countDepositoryInfos(DepositoryInfoCriteria criteria) {
        log.debug("REST request to count DepositoryInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(depositoryInfoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /depository-infos/:id : get the "id" depositoryInfo.
     *
     * @param id the id of the depositoryInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depositoryInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/depository-infos/{id}")
    @Timed
    public ResponseEntity<DepositoryInfoDTO> getDepositoryInfo(@PathVariable Long id) {
        log.debug("REST request to get DepositoryInfo : {}", id);
        Optional<DepositoryInfoDTO> depositoryInfoDTO = depositoryInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositoryInfoDTO);
    }

    /**
     * DELETE  /depository-infos/:id : delete the "id" depositoryInfo.
     *
     * @param id the id of the depositoryInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depository-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepositoryInfo(@PathVariable Long id) {
        log.debug("REST request to delete DepositoryInfo : {}", id);
        depositoryInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
