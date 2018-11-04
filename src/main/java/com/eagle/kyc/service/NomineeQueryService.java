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

import com.eagle.kyc.domain.Nominee;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.NomineeRepository;
import com.eagle.kyc.service.dto.NomineeCriteria;
import com.eagle.kyc.service.dto.NomineeDTO;
import com.eagle.kyc.service.mapper.NomineeMapper;

/**
 * Service for executing complex queries for Nominee entities in the database.
 * The main input is a {@link NomineeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NomineeDTO} or a {@link Page} of {@link NomineeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NomineeQueryService extends QueryService<Nominee> {

    private final Logger log = LoggerFactory.getLogger(NomineeQueryService.class);

    private NomineeRepository nomineeRepository;

    private NomineeMapper nomineeMapper;

    public NomineeQueryService(NomineeRepository nomineeRepository, NomineeMapper nomineeMapper) {
        this.nomineeRepository = nomineeRepository;
        this.nomineeMapper = nomineeMapper;
    }

    /**
     * Return a {@link List} of {@link NomineeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NomineeDTO> findByCriteria(NomineeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeMapper.toDto(nomineeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NomineeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NomineeDTO> findByCriteria(NomineeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeRepository.findAll(specification, page)
            .map(nomineeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NomineeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeRepository.count(specification);
    }

    /**
     * Function to convert NomineeCriteria to a {@link Specification}
     */
    private Specification<Nominee> createSpecification(NomineeCriteria criteria) {
        Specification<Nominee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Nominee_.id));
            }
            if (criteria.getNomineeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomineeName(), Nominee_.nomineeName));
            }
            if (criteria.getRelationShip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelationShip(), Nominee_.relationShip));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Nominee_.dateOfBirth));
            }
            if (criteria.getGuardianName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuardianName(), Nominee_.guardianName));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(Nominee_.addresses, JoinType.LEFT).get(Address_.id)));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(Nominee_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
