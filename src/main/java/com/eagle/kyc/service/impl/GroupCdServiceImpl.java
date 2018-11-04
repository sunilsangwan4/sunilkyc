package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.GroupCdService;
import com.eagle.kyc.domain.GroupCd;
import com.eagle.kyc.repository.GroupCdRepository;
import com.eagle.kyc.service.dto.GroupCdDTO;
import com.eagle.kyc.service.mapper.GroupCdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GroupCd.
 */
@Service
@Transactional
public class GroupCdServiceImpl implements GroupCdService {

    private final Logger log = LoggerFactory.getLogger(GroupCdServiceImpl.class);

    private GroupCdRepository groupCdRepository;

    private GroupCdMapper groupCdMapper;

    public GroupCdServiceImpl(GroupCdRepository groupCdRepository, GroupCdMapper groupCdMapper) {
        this.groupCdRepository = groupCdRepository;
        this.groupCdMapper = groupCdMapper;
    }

    /**
     * Save a groupCd.
     *
     * @param groupCdDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GroupCdDTO save(GroupCdDTO groupCdDTO) {
        log.debug("Request to save GroupCd : {}", groupCdDTO);

        GroupCd groupCd = groupCdMapper.toEntity(groupCdDTO);
        groupCd = groupCdRepository.save(groupCd);
        return groupCdMapper.toDto(groupCd);
    }

    /**
     * Get all the groupCds.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroupCdDTO> findAll() {
        log.debug("Request to get all GroupCds");
        return groupCdRepository.findAll().stream()
            .map(groupCdMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one groupCd by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupCdDTO> findOne(Long id) {
        log.debug("Request to get GroupCd : {}", id);
        return groupCdRepository.findById(id)
            .map(groupCdMapper::toDto);
    }

    /**
     * Delete the groupCd by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupCd : {}", id);
        groupCdRepository.deleteById(id);
    }
}
