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

import com.eagle.kyc.domain.IdentityVerification;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.IdentityVerificationRepository;
import com.eagle.kyc.service.dto.IdentityVerificationCriteria;
import com.eagle.kyc.service.dto.IdentityVerificationDTO;
import com.eagle.kyc.service.mapper.IdentityVerificationMapper;

/**
 * Service for executing complex queries for IdentityVerification entities in the database.
 * The main input is a {@link IdentityVerificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IdentityVerificationDTO} or a {@link Page} of {@link IdentityVerificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IdentityVerificationQueryService extends QueryService<IdentityVerification> {

    private final Logger log = LoggerFactory.getLogger(IdentityVerificationQueryService.class);

    private IdentityVerificationRepository identityVerificationRepository;

    private IdentityVerificationMapper identityVerificationMapper;

    public IdentityVerificationQueryService(IdentityVerificationRepository identityVerificationRepository, IdentityVerificationMapper identityVerificationMapper) {
        this.identityVerificationRepository = identityVerificationRepository;
        this.identityVerificationMapper = identityVerificationMapper;
    }

    /**
     * Return a {@link List} of {@link IdentityVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IdentityVerificationDTO> findByCriteria(IdentityVerificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IdentityVerification> specification = createSpecification(criteria);
        return identityVerificationMapper.toDto(identityVerificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IdentityVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IdentityVerificationDTO> findByCriteria(IdentityVerificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IdentityVerification> specification = createSpecification(criteria);
        return identityVerificationRepository.findAll(specification, page)
            .map(identityVerificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IdentityVerificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IdentityVerification> specification = createSpecification(criteria);
        return identityVerificationRepository.count(specification);
    }

    /**
     * Function to convert IdentityVerificationCriteria to a {@link Specification}
     */
    private Specification<IdentityVerification> createSpecification(IdentityVerificationCriteria criteria) {
        Specification<IdentityVerification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), IdentityVerification_.id));
            }
            if (criteria.getAdhaarNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdhaarNo(), IdentityVerification_.adhaarNo));
            }
            if (criteria.getAadharNoVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getAadharNoVerified(), IdentityVerification_.aadharNoVerified));
            }
            if (criteria.getPanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanNo(), IdentityVerification_.panNo));
            }
            if (criteria.getPanNoVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getPanNoVerified(), IdentityVerification_.panNoVerified));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), IdentityVerification_.dateOfBirth));
            }
        }
        return specification;
    }
}
