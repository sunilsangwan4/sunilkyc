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

import com.eagle.kyc.domain.InvestmentPotential;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.InvestmentPotentialRepository;
import com.eagle.kyc.service.dto.InvestmentPotentialCriteria;
import com.eagle.kyc.service.dto.InvestmentPotentialDTO;
import com.eagle.kyc.service.mapper.InvestmentPotentialMapper;

/**
 * Service for executing complex queries for InvestmentPotential entities in the database.
 * The main input is a {@link InvestmentPotentialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvestmentPotentialDTO} or a {@link Page} of {@link InvestmentPotentialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvestmentPotentialQueryService extends QueryService<InvestmentPotential> {

    private final Logger log = LoggerFactory.getLogger(InvestmentPotentialQueryService.class);

    private InvestmentPotentialRepository investmentPotentialRepository;

    private InvestmentPotentialMapper investmentPotentialMapper;

    public InvestmentPotentialQueryService(InvestmentPotentialRepository investmentPotentialRepository, InvestmentPotentialMapper investmentPotentialMapper) {
        this.investmentPotentialRepository = investmentPotentialRepository;
        this.investmentPotentialMapper = investmentPotentialMapper;
    }

    /**
     * Return a {@link List} of {@link InvestmentPotentialDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvestmentPotentialDTO> findByCriteria(InvestmentPotentialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvestmentPotential> specification = createSpecification(criteria);
        return investmentPotentialMapper.toDto(investmentPotentialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvestmentPotentialDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvestmentPotentialDTO> findByCriteria(InvestmentPotentialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvestmentPotential> specification = createSpecification(criteria);
        return investmentPotentialRepository.findAll(specification, page)
            .map(investmentPotentialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvestmentPotentialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvestmentPotential> specification = createSpecification(criteria);
        return investmentPotentialRepository.count(specification);
    }

    /**
     * Function to convert InvestmentPotentialCriteria to a {@link Specification}
     */
    private Specification<InvestmentPotential> createSpecification(InvestmentPotentialCriteria criteria) {
        Specification<InvestmentPotential> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InvestmentPotential_.id));
            }
            if (criteria.getEducationQualification() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEducationQualification(), InvestmentPotential_.educationQualification));
            }
            if (criteria.getOccupation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOccupation(), InvestmentPotential_.occupation));
            }
            if (criteria.getAnnualIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualIncome(), InvestmentPotential_.annualIncome));
            }
            if (criteria.getNetWorth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetWorth(), InvestmentPotential_.netWorth));
            }
            if (criteria.getNetworthDeclarationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetworthDeclarationDate(), InvestmentPotential_.networthDeclarationDate));
            }
            if (criteria.getPepRelative() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPepRelative(), InvestmentPotential_.pepRelative));
            }
            if (criteria.getPmlaRiskCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPmlaRiskCategory(), InvestmentPotential_.pmlaRiskCategory));
            }
            if (criteria.getPmlaRiskCategoryReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPmlaRiskCategoryReason(), InvestmentPotential_.pmlaRiskCategoryReason));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(InvestmentPotential_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
