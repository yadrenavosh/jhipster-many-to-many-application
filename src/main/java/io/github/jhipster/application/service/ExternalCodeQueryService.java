package io.github.jhipster.application.service;

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

import io.github.jhipster.application.domain.ExternalCode;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ExternalCodeRepository;
import io.github.jhipster.application.service.dto.ExternalCodeCriteria;

/**
 * Service for executing complex queries for ExternalCode entities in the database.
 * The main input is a {@link ExternalCodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExternalCode} or a {@link Page} of {@link ExternalCode} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExternalCodeQueryService extends QueryService<ExternalCode> {

    private final Logger log = LoggerFactory.getLogger(ExternalCodeQueryService.class);

    private final ExternalCodeRepository externalCodeRepository;

    public ExternalCodeQueryService(ExternalCodeRepository externalCodeRepository) {
        this.externalCodeRepository = externalCodeRepository;
    }

    /**
     * Return a {@link List} of {@link ExternalCode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExternalCode> findByCriteria(ExternalCodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExternalCode> specification = createSpecification(criteria);
        return externalCodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExternalCode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExternalCode> findByCriteria(ExternalCodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExternalCode> specification = createSpecification(criteria);
        return externalCodeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExternalCodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExternalCode> specification = createSpecification(criteria);
        return externalCodeRepository.count(specification);
    }

    /**
     * Function to convert ExternalCodeCriteria to a {@link Specification}
     */
    private Specification<ExternalCode> createSpecification(ExternalCodeCriteria criteria) {
        Specification<ExternalCode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExternalCode_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ExternalCode_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ExternalCode_.description));
            }
            if (criteria.getInternalId() != null) {
                specification = specification.and(buildSpecification(criteria.getInternalId(),
                    root -> root.join(ExternalCode_.internals, JoinType.LEFT).get(InternalCode_.id)));
            }
        }
        return specification;
    }
}
