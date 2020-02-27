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

import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ItemAuditoriaRepository;
import com.lzkill.sgq.service.dto.ItemAuditoriaCriteria;

/**
 * Service for executing complex queries for {@link ItemAuditoria} entities in the database.
 * The main input is a {@link ItemAuditoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemAuditoria} or a {@link Page} of {@link ItemAuditoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemAuditoriaQueryService extends QueryService<ItemAuditoria> {

    private final Logger log = LoggerFactory.getLogger(ItemAuditoriaQueryService.class);

    private final ItemAuditoriaRepository itemAuditoriaRepository;

    public ItemAuditoriaQueryService(ItemAuditoriaRepository itemAuditoriaRepository) {
        this.itemAuditoriaRepository = itemAuditoriaRepository;
    }

    /**
     * Return a {@link List} of {@link ItemAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemAuditoria> findByCriteria(ItemAuditoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemAuditoria> specification = createSpecification(criteria);
        return itemAuditoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemAuditoria> findByCriteria(ItemAuditoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemAuditoria> specification = createSpecification(criteria);
        return itemAuditoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemAuditoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemAuditoria> specification = createSpecification(criteria);
        return itemAuditoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemAuditoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemAuditoria> createSpecification(ItemAuditoriaCriteria criteria) {
        Specification<ItemAuditoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemAuditoria_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), ItemAuditoria_.titulo));
            }
            if (criteria.getHabilitado() != null) {
                specification = specification.and(buildSpecification(criteria.getHabilitado(), ItemAuditoria_.habilitado));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(ItemAuditoria_.processo, JoinType.LEFT).get(Processo_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ItemAuditoria_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuditoriaId(),
                    root -> root.join(ItemAuditoria_.auditorias, JoinType.LEFT).get(Auditoria_.id)));
            }
        }
        return specification;
    }
}
