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

import com.eagle.kyc.domain.BankInformation;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.BankInformationRepository;
import com.eagle.kyc.service.dto.BankInformationCriteria;
import com.eagle.kyc.service.dto.BankInformationDTO;
import com.eagle.kyc.service.mapper.BankInformationMapper;

/**
 * Service for executing complex queries for BankInformation entities in the database.
 * The main input is a {@link BankInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankInformationDTO} or a {@link Page} of {@link BankInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankInformationQueryService extends QueryService<BankInformation> {

    private final Logger log = LoggerFactory.getLogger(BankInformationQueryService.class);

    private BankInformationRepository bankInformationRepository;

    private BankInformationMapper bankInformationMapper;

    public BankInformationQueryService(BankInformationRepository bankInformationRepository, BankInformationMapper bankInformationMapper) {
        this.bankInformationRepository = bankInformationRepository;
        this.bankInformationMapper = bankInformationMapper;
    }

    /**
     * Return a {@link List} of {@link BankInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankInformationDTO> findByCriteria(BankInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BankInformation> specification = createSpecification(criteria);
        return bankInformationMapper.toDto(bankInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankInformationDTO> findByCriteria(BankInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BankInformation> specification = createSpecification(criteria);
        return bankInformationRepository.findAll(specification, page)
            .map(bankInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BankInformation> specification = createSpecification(criteria);
        return bankInformationRepository.count(specification);
    }

    /**
     * Function to convert BankInformationCriteria to a {@link Specification}
     */
    private Specification<BankInformation> createSpecification(BankInformationCriteria criteria) {
        Specification<BankInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BankInformation_.id));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), BankInformation_.bankName));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), BankInformation_.ifscCode));
            }
            if (criteria.getMicrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMicrCode(), BankInformation_.micrCode));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), BankInformation_.branchName));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), BankInformation_.accountType));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), BankInformation_.accountNumber));
            }
            if (criteria.getAccountHolderName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountHolderName(), BankInformation_.accountHolderName));
            }
            if (criteria.getBankAccountCommon() != null) {
                specification = specification.and(buildSpecification(criteria.getBankAccountCommon(), BankInformation_.bankAccountCommon));
            }
            if (criteria.getSegementTypeCd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegementTypeCd(), BankInformation_.segementTypeCd));
            }
            if (criteria.getApplicationProspectId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationProspectId(),
                    root -> root.join(BankInformation_.applicationProspect, JoinType.LEFT).get(ApplicationProspect_.id)));
            }
        }
        return specification;
    }
}
