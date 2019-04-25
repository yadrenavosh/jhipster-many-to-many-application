package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ExternalCode;
import io.github.jhipster.application.repository.ExternalCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ExternalCode.
 */
@Service
@Transactional
public class ExternalCodeService {

    private final Logger log = LoggerFactory.getLogger(ExternalCodeService.class);

    private final ExternalCodeRepository externalCodeRepository;

    public ExternalCodeService(ExternalCodeRepository externalCodeRepository) {
        this.externalCodeRepository = externalCodeRepository;
    }

    /**
     * Save a externalCode.
     *
     * @param externalCode the entity to save
     * @return the persisted entity
     */
    public ExternalCode save(ExternalCode externalCode) {
        log.debug("Request to save ExternalCode : {}", externalCode);
        return externalCodeRepository.save(externalCode);
    }

    /**
     * Get all the externalCodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExternalCode> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalCodes");
        return externalCodeRepository.findAll(pageable);
    }


    /**
     * Get one externalCode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExternalCode> findOne(Long id) {
        log.debug("Request to get ExternalCode : {}", id);
        return externalCodeRepository.findById(id);
    }

    /**
     * Delete the externalCode by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExternalCode : {}", id);
        externalCodeRepository.deleteById(id);
    }
}
