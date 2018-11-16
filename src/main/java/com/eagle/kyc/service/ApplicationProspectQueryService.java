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

import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.ApplicationProspectRepository;
import com.eagle.kyc.service.dto.ApplicationProspectCriteria;
import com.eagle.kyc.service.dto.ApplicationProspectDTO;
import com.eagle.kyc.service.mapper.ApplicationProspectMapper;

/**
 * Service for executing complex queries for ApplicationProspect entities in the database.
 * The main input is a {@link ApplicationProspectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationProspectDTO} or a {@link Page} of {@link ApplicationProspectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationProspectQueryService extends QueryService<ApplicationProspect> {

    private final Logger log = LoggerFactory.getLogger(ApplicationProspectQueryService.class);

    private ApplicationProspectRepository applicationProspectRepository;

    private ApplicationProspectMapper applicationProspectMapper;

    public ApplicationProspectQueryService(ApplicationProspectRepository applicationProspectRepository, ApplicationProspectMapper applicationProspectMapper) {
        this.applicationProspectRepository = applicationProspectRepository;
        this.applicationProspectMapper = applicationProspectMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationProspectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationProspectDTO> findByCriteria(ApplicationProspectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationProspect> specification = createSpecification(criteria);
        return applicationProspectMapper.toDto(applicationProspectRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationProspectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationProspectDTO> findByCriteria(ApplicationProspectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationProspect> specification = createSpecification(criteria);
        return applicationProspectRepository.findAll(specification, page)
            .map(applicationProspectMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationProspectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationProspect> specification = createSpecification(criteria);
        return applicationProspectRepository.count(specification);
    }

    /**
     * Function to convert ApplicationProspectCriteria to a {@link Specification}
     */
    private Specification<ApplicationProspect> createSpecification(ApplicationProspectCriteria criteria) {
        Specification<ApplicationProspect> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ApplicationProspect_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), ApplicationProspect_.fullName));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), ApplicationProspect_.mobileNo));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ApplicationProspect_.email));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), ApplicationProspect_.password));
            }
            if (criteria.getConfirmPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfirmPassword(), ApplicationProspect_.confirmPassword));
            }
            if (criteria.getPersonalInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonalInformationId(),
                    root -> root.join(ApplicationProspect_.personalInformation, JoinType.LEFT).get(PersonalInformation_.id)));
            }
            if (criteria.getInvestmentPotentialId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvestmentPotentialId(),
                    root -> root.join(ApplicationProspect_.investmentPotential, JoinType.LEFT).get(InvestmentPotential_.id)));
            }
            if (criteria.getNomineeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNomineeId(),
                    root -> root.join(ApplicationProspect_.nominee, JoinType.LEFT).get(Nominee_.id)));
            }
            if (criteria.getTradingInfoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTradingInfoId(),
                    root -> root.join(ApplicationProspect_.tradingInfo, JoinType.LEFT).get(TradingInfo_.id)));
            }
            if (criteria.getDepositoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepositoryId(),
                    root -> root.join(ApplicationProspect_.depository, JoinType.LEFT).get(DepositoryInfo_.id)));
            }
            if (criteria.getAddressesId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressesId(),
                    root -> root.join(ApplicationProspect_.addresses, JoinType.LEFT).get(Address_.id)));
            }
            if (criteria.getBankInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBankInformationId(),
                    root -> root.join(ApplicationProspect_.bankInformations, JoinType.LEFT).get(BankInformation_.id)));
            }
            if (criteria.getIdentityVerificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdentityVerificationId(),
                    root -> root.join(ApplicationProspect_.identityVerification, JoinType.LEFT).get(IdentityVerification_.id)));
            }
        }
        return specification;
    }
}
