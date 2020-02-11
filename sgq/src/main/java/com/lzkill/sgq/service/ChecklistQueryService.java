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

import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ChecklistRepository;
import com.lzkill.sgq.service.dto.ChecklistCriteria;

/**
 * Service for executing complex queries for {@link Checklist} entities in the database.
 * The main input is a {@link ChecklistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Checklist} or a {@link Page} of {@link Checklist} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChecklistQueryService extends QueryService<Checklist> {

    private final Logger log = LoggerFactory.getLogger(ChecklistQueryService.class);

    private final ChecklistRepository checklistRepository;

    public ChecklistQueryService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    /**
     * Return a {@link List} of {@link Checklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Checklist> findByCriteria(ChecklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Checklist> specification = createSpecification(criteria);
        return checklistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Checklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Checklist> findByCriteria(ChecklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Checklist> specification = createSpecification(criteria);
        return checklistRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChecklistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Checklist> specification = createSpecification(criteria);
        return checklistRepository.count(specification);
    }

    /**
     * Function to convert {@link ChecklistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Checklist> createSpecification(ChecklistCriteria criteria) {
        Specification<Checklist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Checklist_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Checklist_.titulo));
            }
            if (criteria.getPeriodicidade() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodicidade(), Checklist_.periodicidade));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Checklist_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(Checklist_.items, JoinType.LEFT).get(ItemChecklist_.id)));
            }
            if (criteria.getSetorId() != null) {
                specification = specification.and(buildSpecification(criteria.getSetorId(),
                    root -> root.join(Checklist_.setor, JoinType.LEFT).get(Setor_.id)));
            }
        }
        return specification;
    }
}
