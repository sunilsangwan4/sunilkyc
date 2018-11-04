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

import com.eagle.kyc.domain.EmailVerification;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.EmailVerificationRepository;
import com.eagle.kyc.service.dto.EmailVerificationCriteria;
import com.eagle.kyc.service.dto.EmailVerificationDTO;
import com.eagle.kyc.service.mapper.EmailVerificationMapper;

/**
 * Service for executing complex queries for EmailVerification entities in the database.
 * The main input is a {@link EmailVerificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailVerificationDTO} or a {@link Page} of {@link EmailVerificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailVerificationQueryService extends QueryService<EmailVerification> {

    private final Logger log = LoggerFactory.getLogger(EmailVerificationQueryService.class);

    private EmailVerificationRepository emailVerificationRepository;

    private EmailVerificationMapper emailVerificationMapper;

    public EmailVerificationQueryService(EmailVerificationRepository emailVerificationRepository, EmailVerificationMapper emailVerificationMapper) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationMapper = emailVerificationMapper;
    }

    /**
     * Return a {@link List} of {@link EmailVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailVerificationDTO> findByCriteria(EmailVerificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmailVerification> specification = createSpecification(criteria);
        return emailVerificationMapper.toDto(emailVerificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailVerificationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailVerificationDTO> findByCriteria(EmailVerificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmailVerification> specification = createSpecification(criteria);
        return emailVerificationRepository.findAll(specification, page)
            .map(emailVerificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailVerificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmailVerification> specification = createSpecification(criteria);
        return emailVerificationRepository.count(specification);
    }

    /**
     * Function to convert EmailVerificationCriteria to a {@link Specification}
     */
    private Specification<EmailVerification> createSpecification(EmailVerificationCriteria criteria) {
        Specification<EmailVerification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EmailVerification_.id));
            }
            if (criteria.getEmailId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailId(), EmailVerification_.emailId));
            }
            if (criteria.getToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToken(), EmailVerification_.token));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), EmailVerification_.status));
            }
        }
        return specification;
    }
}
