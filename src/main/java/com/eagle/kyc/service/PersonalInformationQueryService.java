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

import com.eagle.kyc.domain.PersonalInformation;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.PersonalInformationRepository;
import com.eagle.kyc.service.dto.PersonalInformationCriteria;
import com.eagle.kyc.service.dto.PersonalInformationDTO;
import com.eagle.kyc.service.mapper.PersonalInformationMapper;

/**
 * Service for executing complex queries for PersonalInformation entities in the database.
 * The main input is a {@link PersonalInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonalInformationDTO} or a {@link Page} of {@link PersonalInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonalInformationQueryService extends QueryService<PersonalInformation> {

    private final Logger log = LoggerFactory.getLogger(PersonalInformationQueryService.class);

    private PersonalInformationRepository personalInformationRepository;

    private PersonalInformationMapper personalInformationMapper;

    public PersonalInformationQueryService(PersonalInformationRepository personalInformationRepository, PersonalInformationMapper personalInformationMapper) {
        this.personalInformationRepository = personalInformationRepository;
        this.personalInformationMapper = personalInformationMapper;
    }

    /**
     * Return a {@link List} of {@link PersonalInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalInformationDTO> findByCriteria(PersonalInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonalInformation> specification = createSpecification(criteria);
        return personalInformationMapper.toDto(personalInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PersonalInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalInformationDTO> findByCriteria(PersonalInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonalInformation> specification = createSpecification(criteria);
        return personalInformationRepository.findAll(specification, page)
            .map(personalInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonalInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonalInformation> specification = createSpecification(criteria);
        return personalInformationRepository.count(specification);
    }

    /**
     * Function to convert PersonalInformationCriteria to a {@link Specification}
     */
    private Specification<PersonalInformation> createSpecification(PersonalInformationCriteria criteria) {
        Specification<PersonalInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PersonalInformation_.id));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), PersonalInformation_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), PersonalInformation_.motherName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), PersonalInformation_.gender));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), PersonalInformation_.nationality));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), PersonalInformation_.maritalStatus));
            }
            if (criteria.getIndianTaxPayer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndianTaxPayer(), PersonalInformation_.indianTaxPayer));
            }
            if (criteria.getBirthCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthCountry(), PersonalInformation_.birthCountry));
            }
            if (criteria.getBirthCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthCity(), PersonalInformation_.birthCity));
            }
            if (criteria.getJurisdictionCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJurisdictionCountry(), PersonalInformation_.jurisdictionCountry));
            }
            if (criteria.getTaxIdentificationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxIdentificationNo(), PersonalInformation_.taxIdentificationNo));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(PersonalInformation_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
