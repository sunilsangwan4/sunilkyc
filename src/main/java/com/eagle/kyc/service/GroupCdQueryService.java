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

import com.eagle.kyc.domain.GroupCd;
import com.eagle.kyc.domain.*; // for static metamodels
import com.eagle.kyc.repository.GroupCdRepository;
import com.eagle.kyc.service.dto.GroupCdCriteria;
import com.eagle.kyc.service.dto.GroupCdDTO;
import com.eagle.kyc.service.mapper.GroupCdMapper;

/**
 * Service for executing complex queries for GroupCd entities in the database.
 * The main input is a {@link GroupCdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupCdDTO} or a {@link Page} of {@link GroupCdDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupCdQueryService extends QueryService<GroupCd> {

    private final Logger log = LoggerFactory.getLogger(GroupCdQueryService.class);

    private GroupCdRepository groupCdRepository;

    private GroupCdMapper groupCdMapper;

    public GroupCdQueryService(GroupCdRepository groupCdRepository, GroupCdMapper groupCdMapper) {
        this.groupCdRepository = groupCdRepository;
        this.groupCdMapper = groupCdMapper;
    }

    /**
     * Return a {@link List} of {@link GroupCdDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupCdDTO> findByCriteria(GroupCdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupCd> specification = createSpecification(criteria);
        return groupCdMapper.toDto(groupCdRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupCdDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupCdDTO> findByCriteria(GroupCdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupCd> specification = createSpecification(criteria);
        return groupCdRepository.findAll(specification, page)
            .map(groupCdMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupCdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GroupCd> specification = createSpecification(criteria);
        return groupCdRepository.count(specification);
    }

    /**
     * Function to convert GroupCdCriteria to a {@link Specification}
     */
    private Specification<GroupCd> createSpecification(GroupCdCriteria criteria) {
        Specification<GroupCd> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GroupCd_.id));
            }
            if (criteria.getGroupCd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCd(), GroupCd_.groupCd));
            }
            if (criteria.getTypeCdId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeCdId(),
                    root -> root.join(GroupCd_.typeCds, JoinType.LEFT).get(TypeCd_.id)));
            }
        }
        return specification;
    }
}
