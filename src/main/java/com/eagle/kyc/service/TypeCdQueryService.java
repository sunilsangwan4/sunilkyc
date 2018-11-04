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

import com.eagle.kyc.domain.TypeCd;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.TypeCdRepository;
import com.eagle.kyc.service.dto.TypeCdCriteria;
import com.eagle.kyc.service.dto.TypeCdDTO;
import com.eagle.kyc.service.mapper.TypeCdMapper;

/**
 * Service for executing complex queries for TypeCd entities in the database.
 * The main input is a {@link TypeCdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeCdDTO} or a {@link Page} of {@link TypeCdDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeCdQueryService extends QueryService<TypeCd> {

    private final Logger log = LoggerFactory.getLogger(TypeCdQueryService.class);

    private TypeCdRepository typeCdRepository;

    private TypeCdMapper typeCdMapper;

    public TypeCdQueryService(TypeCdRepository typeCdRepository, TypeCdMapper typeCdMapper) {
        this.typeCdRepository = typeCdRepository;
        this.typeCdMapper = typeCdMapper;
    }

    /**
     * Return a {@link List} of {@link TypeCdDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeCdDTO> findByCriteria(TypeCdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeCd> specification = createSpecification(criteria);
        return typeCdMapper.toDto(typeCdRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeCdDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeCdDTO> findByCriteria(TypeCdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeCd> specification = createSpecification(criteria);
        return typeCdRepository.findAll(specification, page)
            .map(typeCdMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeCdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeCd> specification = createSpecification(criteria);
        return typeCdRepository.count(specification);
    }

    /**
     * Function to convert TypeCdCriteria to a {@link Specification}
     */
    private Specification<TypeCd> createSpecification(TypeCdCriteria criteria) {
        Specification<TypeCd> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeCd_.id));
            }
            if (criteria.getTypeCd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeCd(), TypeCd_.typeCd));
            }
            if (criteria.getTypeDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeDescription(), TypeCd_.typeDescription));
            }
            if (criteria.getGroupCdId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupCdId(),
                    root -> root.join(TypeCd_.groupCd, JoinType.LEFT).get(GroupCd_.id)));
            }
        }
        return specification;
    }
}
