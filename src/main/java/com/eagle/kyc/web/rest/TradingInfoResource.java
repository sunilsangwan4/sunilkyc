package com.eagle.kyc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eagle.kyc.service.TradingInfoService;
import com.eagle.kyc.web.rest.errors.BadRequestAlertException;
import com.eagle.kyc.web.rest.util.HeaderUtil;
import com.eagle.kyc.service.dto.TradingInfoDTO;
import com.eagle.kyc.service.dto.TradingInfoCriteria;
import com.eagle.kyc.service.TradingInfoQueryService;
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
 * REST controller for managing TradingInfo.
 */
@RestController
@RequestMapping("/api")
public class TradingInfoResource {

    private final Logger log = LoggerFactory.getLogger(TradingInfoResource.class);

    private static final String ENTITY_NAME = "tradingInfo";

    private TradingInfoService tradingInfoService;

    private TradingInfoQueryService tradingInfoQueryService;

    public TradingInfoResource(TradingInfoService tradingInfoService, TradingInfoQueryService tradingInfoQueryService) {
        this.tradingInfoService = tradingInfoService;
        this.tradingInfoQueryService = tradingInfoQueryService;
    }

    /**
     * POST  /trading-infos : Create a new tradingInfo.
     *
     * @param tradingInfoDTO the tradingInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tradingInfoDTO, or with status 400 (Bad Request) if the tradingInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trading-infos")
    @Timed
    public ResponseEntity<TradingInfoDTO> createTradingInfo(@Valid @RequestBody TradingInfoDTO tradingInfoDTO) throws URISyntaxException {
        log.debug("REST request to save TradingInfo : {}", tradingInfoDTO);
        if (tradingInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tradingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradingInfoDTO result = tradingInfoService.save(tradingInfoDTO);
        return ResponseEntity.created(new URI("/api/trading-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trading-infos : Updates an existing tradingInfo.
     *
     * @param tradingInfoDTO the tradingInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tradingInfoDTO,
     * or with status 400 (Bad Request) if the tradingInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tradingInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trading-infos")
    @Timed
    public ResponseEntity<TradingInfoDTO> updateTradingInfo(@Valid @RequestBody TradingInfoDTO tradingInfoDTO) throws URISyntaxException {
        log.debug("REST request to update TradingInfo : {}", tradingInfoDTO);
        if (tradingInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TradingInfoDTO result = tradingInfoService.save(tradingInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tradingInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trading-infos : get all the tradingInfos.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tradingInfos in body
     */
    @GetMapping("/trading-infos")
    @Timed
    public ResponseEntity<List<TradingInfoDTO>> getAllTradingInfos(TradingInfoCriteria criteria) {
        log.debug("REST request to get TradingInfos by criteria: {}", criteria);
        List<TradingInfoDTO> entityList = tradingInfoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /trading-infos/count : count all the tradingInfos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/trading-infos/count")
    @Timed
    public ResponseEntity<Long> countTradingInfos(TradingInfoCriteria criteria) {
        log.debug("REST request to count TradingInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tradingInfoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /trading-infos/:id : get the "id" tradingInfo.
     *
     * @param id the id of the tradingInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tradingInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trading-infos/{id}")
    @Timed
    public ResponseEntity<TradingInfoDTO> getTradingInfo(@PathVariable Long id) {
        log.debug("REST request to get TradingInfo : {}", id);
        Optional<TradingInfoDTO> tradingInfoDTO = tradingInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradingInfoDTO);
    }

    /**
     * DELETE  /trading-infos/:id : delete the "id" tradingInfo.
     *
     * @param id the id of the tradingInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trading-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTradingInfo(@PathVariable Long id) {
        log.debug("REST request to delete TradingInfo : {}", id);
        tradingInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
