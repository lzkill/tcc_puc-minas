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

import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ItemChecklistRepository;
import com.lzkill.sgq.service.dto.ItemChecklistCriteria;

/**
 * Service for executing complex queries for {@link ItemChecklist} entities in the database.
 * The main input is a {@link ItemChecklistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemChecklist} or a {@link Page} of {@link ItemChecklist} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemChecklistQueryService extends QueryService<ItemChecklist> {

    private final Logger log = LoggerFactory.getLogger(ItemChecklistQueryService.class);

    private final ItemChecklistRepository itemChecklistRepository;

    public ItemChecklistQueryService(ItemChecklistRepository itemChecklistRepository) {
        this.itemChecklistRepository = itemChecklistRepository;
    }

    /**
     * Return a {@link List} of {@link ItemChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemChecklist> findByCriteria(ItemChecklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemChecklist> specification = createSpecification(criteria);
        return itemChecklistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemChecklist> findByCriteria(ItemChecklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemChecklist> specification = createSpecification(criteria);
        return itemChecklistRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemChecklistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemChecklist> specification = createSpecification(criteria);
        return itemChecklistRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemChecklistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemChecklist> createSpecification(ItemChecklistCriteria criteria) {
        Specification<ItemChecklist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemChecklist_.id));
            }
            if (criteria.getOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdem(), ItemChecklist_.ordem));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), ItemChecklist_.titulo));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ItemChecklist_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getChecklistId(),
                    root -> root.join(ItemChecklist_.checklist, JoinType.LEFT).get(Checklist_.id)));
            }
        }
        return specification;
    }
}
