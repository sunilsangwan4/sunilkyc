package com.eagle.kyc.service.impl;

import com.eagle.kyc.service.TypeCdService;
import com.eagle.kyc.domain.TypeCd;
import com.eagle.kyc.repository.TypeCdRepository;
import com.eagle.kyc.service.dto.TypeCdDTO;
import com.eagle.kyc.service.mapper.TypeCdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TypeCd.
 */
@Service
@Transactional
public class TypeCdServiceImpl implements TypeCdService {

    private final Logger log = LoggerFactory.getLogger(TypeCdServiceImpl.class);

    private TypeCdRepository typeCdRepository;

    private TypeCdMapper typeCdMapper;

    public TypeCdServiceImpl(TypeCdRepository typeCdRepository, TypeCdMapper typeCdMapper) {
        this.typeCdRepository = typeCdRepository;
        this.typeCdMapper = typeCdMapper;
    }

    /**
     * Save a typeCd.
     *
     * @param typeCdDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeCdDTO save(TypeCdDTO typeCdDTO) {
        log.debug("Request to save TypeCd : {}", typeCdDTO);

        TypeCd typeCd = typeCdMapper.toEntity(typeCdDTO);
        typeCd = typeCdRepository.save(typeCd);
        return typeCdMapper.toDto(typeCd);
    }

    /**
     * Get all the typeCds.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeCdDTO> findAll() {
        log.debug("Request to get all TypeCds");
        return typeCdRepository.findAll().stream()
            .map(typeCdMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one typeCd by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeCdDTO> findOne(Long id) {
        log.debug("Request to get TypeCd : {}", id);
        return typeCdRepository.findById(id)
            .map(typeCdMapper::toDto);
    }

    /**
     * Delete the typeCd by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeCd : {}", id);
        typeCdRepository.deleteById(id);
    }
}
