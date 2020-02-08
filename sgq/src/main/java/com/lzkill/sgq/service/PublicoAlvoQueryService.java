package com.lzkill.sgq.service;

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

import com.lzkill.sgq.domain.PublicoAlvo;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.PublicoAlvoRepository;
import com.lzkill.sgq.service.dto.PublicoAlvoCriteria;

/**
 * Service for executing complex queries for {@link PublicoAlvo} entities in the database.
 * The main input is a {@link PublicoAlvoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PublicoAlvo} or a {@link Page} of {@link PublicoAlvo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PublicoAlvoQueryService extends QueryService<PublicoAlvo> {

    private final Logger log = LoggerFactory.getLogger(PublicoAlvoQueryService.class);

    private final PublicoAlvoRepository publicoAlvoRepository;

    public PublicoAlvoQueryService(PublicoAlvoRepository publicoAlvoRepository) {
        this.publicoAlvoRepository = publicoAlvoRepository;
    }

    /**
     * Return a {@link List} of {@link PublicoAlvo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PublicoAlvo> findByCriteria(PublicoAlvoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PublicoAlvo> specification = createSpecification(criteria);
        return publicoAlvoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PublicoAlvo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicoAlvo> findByCriteria(PublicoAlvoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PublicoAlvo> specification = createSpecification(criteria);
        return publicoAlvoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PublicoAlvoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PublicoAlvo> specification = createSpecification(criteria);
        return publicoAlvoRepository.count(specification);
    }

    /**
     * Function to convert {@link PublicoAlvoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PublicoAlvo> createSpecification(PublicoAlvoCriteria criteria) {
        Specification<PublicoAlvo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PublicoAlvo_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PublicoAlvo_.nome));
            }
        }
        return specification;
    }
}
