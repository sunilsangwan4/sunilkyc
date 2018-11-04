package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.TypeCdService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.TypeCdDTO;
import com.eagle.kyc.service.dto.TypeCdCriteria;
import com.eagle.kyc.service.TypeCdQueryService;
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
 * REST controller for managing TypeCd.
 */
@RestController
@RequestMapping("/api")
public class TypeCdResource {

    private final Logger log = LoggerFactory.getLogger(TypeCdResource.class);

    private static final String ENTITY_NAME = "typeCd";

    private TypeCdService typeCdService;

    private TypeCdQueryService typeCdQueryService;

    public TypeCdResource(TypeCdService typeCdService, TypeCdQueryService typeCdQueryService) {
        this.typeCdService = typeCdService;
        this.typeCdQueryService = typeCdQueryService;
    }

    /**
     * POST  /type-cds : Create a new typeCd.
     *
     * @param typeCdDTO the typeCdDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeCdDTO, or with status 400 (Bad Request) if the typeCd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-cds")
    @Timed
    public ResponseEntity<TypeCdDTO> createTypeCd(@Valid @RequestBody TypeCdDTO typeCdDTO) throws URISyntaxException {
        log.debug("REST request to save TypeCd : {}", typeCdDTO);
        if (typeCdDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCdDTO result = typeCdService.save(typeCdDTO);
        return ResponseEntity.created(new URI("/api/type-cds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-cds : Updates an existing typeCd.
     *
     * @param typeCdDTO the typeCdDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeCdDTO,
     * or with status 400 (Bad Request) if the typeCdDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeCdDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-cds")
    @Timed
    public ResponseEntity<TypeCdDTO> updateTypeCd(@Valid @RequestBody TypeCdDTO typeCdDTO) throws URISyntaxException {
        log.debug("REST request to update TypeCd : {}", typeCdDTO);
        if (typeCdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCdDTO result = typeCdService.save(typeCdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeCdDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-cds : get all the typeCds.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeCds in body
     */
    @GetMapping("/type-cds")
    @Timed
    public ResponseEntity<List<TypeCdDTO>> getAllTypeCds(TypeCdCriteria criteria) {
        log.debug("REST request to get TypeCds by criteria: {}", criteria);
        List<TypeCdDTO> entityList = typeCdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /type-cds/count : count all the typeCds.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-cds/count")
    @Timed
    public ResponseEntity<Long> countTypeCds(TypeCdCriteria criteria) {
        log.debug("REST request to count TypeCds by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeCdQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-cds/:id : get the "id" typeCd.
     *
     * @param id the id of the typeCdDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeCdDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-cds/{id}")
    @Timed
    public ResponseEntity<TypeCdDTO> getTypeCd(@PathVariable Long id) {
        log.debug("REST request to get TypeCd : {}", id);
        Optional<TypeCdDTO> typeCdDTO = typeCdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCdDTO);
    }

    /**
     * DELETE  /type-cds/:id : delete the "id" typeCd.
     *
     * @param id the id of the typeCdDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-cds/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeCd(@PathVariable Long id) {
        log.debug("REST request to delete TypeCd : {}", id);
        typeCdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
