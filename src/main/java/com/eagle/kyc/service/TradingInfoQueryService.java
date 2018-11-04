package com.eagle.kyc.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.eagle.kyc.domain.TradingInfo;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.TradingInfoRepository;
import com.eagle.kyc.service.dto.TradingInfoCriteria;
import com.eagle.kyc.service.dto.TradingInfoDTO;
import com.eagle.kyc.service.mapper.TradingInfoMapper;

/**
 * Service for executing complex queries for TradingInfo entities in the database.
 * The main input is a {@link TradingInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TradingInfoDTO} or a {@link Page} of {@link TradingInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TradingInfoQueryService extends QueryService<TradingInfo> {

    private final Logger log = LoggerFactory.getLogger(TradingInfoQueryService.class);

    private TradingInfoRepository tradingInfoRepository;

    private TradingInfoMapper tradingInfoMapper;

    public TradingInfoQueryService(TradingInfoRepository tradingInfoRepository, TradingInfoMapper tradingInfoMapper) {
        this.tradingInfoRepository = tradingInfoRepository;
        this.tradingInfoMapper = tradingInfoMapper;
    }

    /**
     * Return a {@link List} of {@link TradingInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TradingInfoDTO> findByCriteria(TradingInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TradingInfo> specification = createSpecification(criteria);
        return tradingInfoMapper.toDto(tradingInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TradingInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TradingInfoDTO> findByCriteria(TradingInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TradingInfo> specification = createSpecification(criteria);
        return tradingInfoRepository.findAll(specification, page)
            .map(tradingInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TradingInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TradingInfo> specification = createSpecification(criteria);
        return tradingInfoRepository.count(specification);
    }

    /**
     * Function to convert TradingInfoCriteria to a {@link Specification}
     */
    private Specification<TradingInfo> createSpecification(TradingInfoCriteria criteria) {
        Specification<TradingInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TradingInfo_.id));
            }
            if (criteria.getSegmentCd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegmentCd(), TradingInfo_.segmentCd));
            }
            if (criteria.getPlanCdEquity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlanCdEquity(), TradingInfo_.planCdEquity));
            }
            if (criteria.getPlanCdCommodity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlanCdCommodity(), TradingInfo_.planCdCommodity));
            }
            if (criteria.getContractNoteMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractNoteMode(), TradingInfo_.contractNoteMode));
            }
            if (criteria.getTradingMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradingMode(), TradingInfo_.tradingMode));
            }
            if (criteria.getInterestedInMobileTradeing() != null) {
                specification = specification.and(buildSpecification(criteria.getInterestedInMobileTradeing(), TradingInfo_.interestedInMobileTradeing));
            }
            if (criteria.getAccountAuthFrequency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountAuthFrequency(), TradingInfo_.accountAuthFrequency));
            }
            if (criteria.getExperienceYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExperienceYear(), TradingInfo_.experienceYear));
            }
            if (criteria.getExperienceMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExperienceMonth(), TradingInfo_.experienceMonth));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(TradingInfo_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
