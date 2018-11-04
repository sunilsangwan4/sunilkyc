package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.GroupCdService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.GroupCdDTO;
import com.eagle.kyc.service.dto.GroupCdCriteria;
import com.eagle.kyc.service.GroupCdQueryService;
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
 * REST controller for managing GroupCd.
 */
@RestController
@RequestMapping("/api")
public class GroupCdResource {

    private final Logger log = LoggerFactory.getLogger(GroupCdResource.class);

    private static final String ENTITY_NAME = "groupCd";

    private GroupCdService groupCdService;

    private GroupCdQueryService groupCdQueryService;

    public GroupCdResource(GroupCdService groupCdService, GroupCdQueryService groupCdQueryService) {
        this.groupCdService = groupCdService;
        this.groupCdQueryService = groupCdQueryService;
    }

    /**
     * POST  /group-cds : Create a new groupCd.
     *
     * @param groupCdDTO the groupCdDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupCdDTO, or with status 400 (Bad Request) if the groupCd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-cds")
    @Timed
    public ResponseEntity<GroupCdDTO> createGroupCd(@Valid @RequestBody GroupCdDTO groupCdDTO) throws URISyntaxException {
        log.debug("REST request to save GroupCd : {}", groupCdDTO);
        if (groupCdDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupCd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupCdDTO result = groupCdService.save(groupCdDTO);
        return ResponseEntity.created(new URI("/api/group-cds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-cds : Updates an existing groupCd.
     *
     * @param groupCdDTO the groupCdDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupCdDTO,
     * or with status 400 (Bad Request) if the groupCdDTO is not valid,
     * or with status 500 (Internal Server Error) if the groupCdDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-cds")
    @Timed
    public ResponseEntity<GroupCdDTO> updateGroupCd(@Valid @RequestBody GroupCdDTO groupCdDTO) throws URISyntaxException {
        log.debug("REST request to update GroupCd : {}", groupCdDTO);
        if (groupCdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupCdDTO result = groupCdService.save(groupCdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupCdDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-cds : get all the groupCds.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of groupCds in body
     */
    @GetMapping("/group-cds")
    @Timed
    public ResponseEntity<List<GroupCdDTO>> getAllGroupCds(GroupCdCriteria criteria) {
        log.debug("REST request to get GroupCds by criteria: {}", criteria);
        List<GroupCdDTO> entityList = groupCdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /group-cds/count : count all the groupCds.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/group-cds/count")
    @Timed
    public ResponseEntity<Long> countGroupCds(GroupCdCriteria criteria) {
        log.debug("REST request to count GroupCds by criteria: {}", criteria);
        return ResponseEntity.ok().body(groupCdQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /group-cds/:id : get the "id" groupCd.
     *
     * @param id the id of the groupCdDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupCdDTO, or with status 404 (Not Found)
     */
    @GetMapping("/group-cds/{id}")
    @Timed
    public ResponseEntity<GroupCdDTO> getGroupCd(@PathVariable Long id) {
        log.debug("REST request to get GroupCd : {}", id);
        Optional<GroupCdDTO> groupCdDTO = groupCdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupCdDTO);
    }

    /**
     * DELETE  /group-cds/:id : delete the "id" groupCd.
     *
     * @param id the id of the groupCdDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-cds/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupCd(@PathVariable Long id) {
        log.debug("REST request to delete GroupCd : {}", id);
        groupCdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
