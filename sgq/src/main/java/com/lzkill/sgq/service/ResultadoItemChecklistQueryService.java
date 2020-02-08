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

import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ResultadoItemChecklistRepository;
import com.lzkill.sgq.service.dto.ResultadoItemChecklistCriteria;

/**
 * Service for executing complex queries for {@link ResultadoItemChecklist} entities in the database.
 * The main input is a {@link ResultadoItemChecklistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResultadoItemChecklist} or a {@link Page} of {@link ResultadoItemChecklist} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultadoItemChecklistQueryService extends QueryService<ResultadoItemChecklist> {

    private final Logger log = LoggerFactory.getLogger(ResultadoItemChecklistQueryService.class);

    private final ResultadoItemChecklistRepository resultadoItemChecklistRepository;

    public ResultadoItemChecklistQueryService(ResultadoItemChecklistRepository resultadoItemChecklistRepository) {
        this.resultadoItemChecklistRepository = resultadoItemChecklistRepository;
    }

    /**
     * Return a {@link List} of {@link ResultadoItemChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResultadoItemChecklist> findByCriteria(ResultadoItemChecklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResultadoItemChecklist> specification = createSpecification(criteria);
        return resultadoItemChecklistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ResultadoItemChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoItemChecklist> findByCriteria(ResultadoItemChecklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResultadoItemChecklist> specification = createSpecification(criteria);
        return resultadoItemChecklistRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultadoItemChecklistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResultadoItemChecklist> specification = createSpecification(criteria);
        return resultadoItemChecklistRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultadoItemChecklistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResultadoItemChecklist> createSpecification(ResultadoItemChecklistCriteria criteria) {
        Specification<ResultadoItemChecklist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResultadoItemChecklist_.id));
            }
            if (criteria.getConforme() != null) {
                specification = specification.and(buildSpecification(criteria.getConforme(), ResultadoItemChecklist_.conforme));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ResultadoItemChecklist_.anexo, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ResultadoItemChecklist_.item, JoinType.LEFT).get(ItemChecklist_.id)));
            }
            if (criteria.getResultadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultadoId(),
                    root -> root.join(ResultadoItemChecklist_.resultado, JoinType.LEFT).get(ResultadoChecklist_.id)));
            }
        }
        return specification;
    }
}
