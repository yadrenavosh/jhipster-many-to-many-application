package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.InternalCode;
import io.github.jhipster.application.repository.InternalCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing InternalCode.
 */
@Service
@Transactional
public class InternalCodeService {

    private final Logger log = LoggerFactory.getLogger(InternalCodeService.class);

    private final InternalCodeRepository internalCodeRepository;

    public InternalCodeService(InternalCodeRepository internalCodeRepository) {
        this.internalCodeRepository = internalCodeRepository;
    }

    /**
     * Save a internalCode.
     *
     * @param internalCode the entity to save
     * @return the persisted entity
     */
    public InternalCode save(InternalCode internalCode) {
        log.debug("Request to save InternalCode : {}", internalCode);
        return internalCodeRepository.save(internalCode);
    }

    /**
     * Get all the internalCodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InternalCode> findAll(Pageable pageable) {
        log.debug("Request to get all InternalCodes");
        return internalCodeRepository.findAll(pageable);
    }

    /**
     * Get all the InternalCode with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<InternalCode> findAllWithEagerRelationships(Pageable pageable) {
        return internalCodeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one internalCode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InternalCode> findOne(Long id) {
        log.debug("Request to get InternalCode : {}", id);
        return internalCodeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the internalCode by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InternalCode : {}", id);
        internalCodeRepository.deleteById(id);
    }
}
