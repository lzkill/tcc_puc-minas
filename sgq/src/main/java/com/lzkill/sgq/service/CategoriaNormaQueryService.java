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

import com.lzkill.sgq.domain.CategoriaNorma;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.CategoriaNormaRepository;
import com.lzkill.sgq.service.dto.CategoriaNormaCriteria;

/**
 * Service for executing complex queries for {@link CategoriaNorma} entities in the database.
 * The main input is a {@link CategoriaNormaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaNorma} or a {@link Page} of {@link CategoriaNorma} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaNormaQueryService extends QueryService<CategoriaNorma> {

    private final Logger log = LoggerFactory.getLogger(CategoriaNormaQueryService.class);

    private final CategoriaNormaRepository categoriaNormaRepository;

    public CategoriaNormaQueryService(CategoriaNormaRepository categoriaNormaRepository) {
        this.categoriaNormaRepository = categoriaNormaRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaNorma} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaNorma> findByCriteria(CategoriaNormaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaNorma> specification = createSpecification(criteria);
        return categoriaNormaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaNorma} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaNorma> findByCriteria(CategoriaNormaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaNorma> specification = createSpecification(criteria);
        return categoriaNormaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaNormaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaNorma> specification = createSpecification(criteria);
        return categoriaNormaRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaNormaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaNorma> createSpecification(CategoriaNormaCriteria criteria) {
        Specification<CategoriaNorma> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaNorma_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), CategoriaNorma_.titulo));
            }
            if (criteria.getNormaId() != null) {
                specification = specification.and(buildSpecification(criteria.getNormaId(),
                    root -> root.join(CategoriaNorma_.normas, JoinType.LEFT).get(Norma_.id)));
            }
        }
        return specification;
    }
}
