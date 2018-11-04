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

import com.eagle.kyc.domain.DepositoryInfo;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.DepositoryInfoRepository;
import com.eagle.kyc.service.dto.DepositoryInfoCriteria;
import com.eagle.kyc.service.dto.DepositoryInfoDTO;
import com.eagle.kyc.service.mapper.DepositoryInfoMapper;

/**
 * Service for executing complex queries for DepositoryInfo entities in the database.
 * The main input is a {@link DepositoryInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepositoryInfoDTO} or a {@link Page} of {@link DepositoryInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepositoryInfoQueryService extends QueryService<DepositoryInfo> {

    private final Logger log = LoggerFactory.getLogger(DepositoryInfoQueryService.class);

    private DepositoryInfoRepository depositoryInfoRepository;

    private DepositoryInfoMapper depositoryInfoMapper;

    public DepositoryInfoQueryService(DepositoryInfoRepository depositoryInfoRepository, DepositoryInfoMapper depositoryInfoMapper) {
        this.depositoryInfoRepository = depositoryInfoRepository;
        this.depositoryInfoMapper = depositoryInfoMapper;
    }

    /**
     * Return a {@link List} of {@link DepositoryInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepositoryInfoDTO> findByCriteria(DepositoryInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DepositoryInfo> specification = createSpecification(criteria);
        return depositoryInfoMapper.toDto(depositoryInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepositoryInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepositoryInfoDTO> findByCriteria(DepositoryInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepositoryInfo> specification = createSpecification(criteria);
        return depositoryInfoRepository.findAll(specification, page)
            .map(depositoryInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepositoryInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepositoryInfo> specification = createSpecification(criteria);
        return depositoryInfoRepository.count(specification);
    }

    /**
     * Function to convert DepositoryInfoCriteria to a {@link Specification}
     */
    private Specification<DepositoryInfo> createSpecification(DepositoryInfoCriteria criteria) {
        Specification<DepositoryInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DepositoryInfo_.id));
            }
            if (criteria.getHaveAccountWithOtherDP() != null) {
                specification = specification.and(buildSpecification(criteria.getHaveAccountWithOtherDP(), DepositoryInfo_.haveAccountWithOtherDP));
            }
            if (criteria.getHaveSMSEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getHaveSMSEnabled(), DepositoryInfo_.haveSMSEnabled));
            }
            if (criteria.getStatementFrequency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatementFrequency(), DepositoryInfo_.statementFrequency));
            }
            if (criteria.getDpScheme() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDpScheme(), DepositoryInfo_.dpScheme));
            }
            if (criteria.getDepositoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositoryName(), DepositoryInfo_.depositoryName));
            }
            if (criteria.getBrokerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrokerName(), DepositoryInfo_.brokerName));
            }
            if (criteria.getNameAsPerDemat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAsPerDemat(), DepositoryInfo_.nameAsPerDemat));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(DepositoryInfo_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
