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

import io.github.jhipster.application.domain.InternalCode;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.InternalCodeRepository;
import io.github.jhipster.application.service.dto.InternalCodeCriteria;

/**
 * Service for executing complex queries for InternalCode entities in the database.
 * The main input is a {@link InternalCodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InternalCode} or a {@link Page} of {@link InternalCode} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InternalCodeQueryService extends QueryService<InternalCode> {

    private final Logger log = LoggerFactory.getLogger(InternalCodeQueryService.class);

    private final InternalCodeRepository internalCodeRepository;

    public InternalCodeQueryService(InternalCodeRepository internalCodeRepository) {
        this.internalCodeRepository = internalCodeRepository;
    }

    /**
     * Return a {@link List} of {@link InternalCode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InternalCode> findByCriteria(InternalCodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InternalCode> specification = createSpecification(criteria);
        return internalCodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InternalCode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InternalCode> findByCriteria(InternalCodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InternalCode> specification = createSpecification(criteria);
        return internalCodeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InternalCodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InternalCode> specification = createSpecification(criteria);
        return internalCodeRepository.count(specification);
    }

    /**
     * Function to convert InternalCodeCriteria to a {@link Specification}
     */
    private Specification<InternalCode> createSpecification(InternalCodeCriteria criteria) {
        Specification<InternalCode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InternalCode_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), InternalCode_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InternalCode_.description));
            }
            if (criteria.getExternalId() != null) {
                specification = specification.and(buildSpecification(criteria.getExternalId(),
                    root -> root.join(InternalCode_.externals, JoinType.LEFT).get(ExternalCode_.id)));
            }
        }
        return specification;
    }
}
