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

import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ItemPlanoAuditoriaRepository;
import com.lzkill.sgq.service.dto.ItemPlanoAuditoriaCriteria;

/**
 * Service for executing complex queries for {@link ItemPlanoAuditoria} entities in the database.
 * The main input is a {@link ItemPlanoAuditoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemPlanoAuditoria} or a {@link Page} of {@link ItemPlanoAuditoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemPlanoAuditoriaQueryService extends QueryService<ItemPlanoAuditoria> {

    private final Logger log = LoggerFactory.getLogger(ItemPlanoAuditoriaQueryService.class);

    private final ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository;

    public ItemPlanoAuditoriaQueryService(ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository) {
        this.itemPlanoAuditoriaRepository = itemPlanoAuditoriaRepository;
    }

    /**
     * Return a {@link List} of {@link ItemPlanoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemPlanoAuditoria> findByCriteria(ItemPlanoAuditoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemPlanoAuditoria> specification = createSpecification(criteria);
        return itemPlanoAuditoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ItemPlanoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPlanoAuditoria> findByCriteria(ItemPlanoAuditoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemPlanoAuditoria> specification = createSpecification(criteria);
        return itemPlanoAuditoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemPlanoAuditoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemPlanoAuditoria> specification = createSpecification(criteria);
        return itemPlanoAuditoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemPlanoAuditoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemPlanoAuditoria> createSpecification(ItemPlanoAuditoriaCriteria criteria) {
        Specification<ItemPlanoAuditoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemPlanoAuditoria_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), ItemPlanoAuditoria_.titulo));
            }
            if (criteria.getModalidade() != null) {
                specification = specification.and(buildSpecification(criteria.getModalidade(), ItemPlanoAuditoria_.modalidade));
            }
            if (criteria.getDataInicioPrevisto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicioPrevisto(), ItemPlanoAuditoria_.dataInicioPrevisto));
            }
            if (criteria.getDataFimPrevisto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFimPrevisto(), ItemPlanoAuditoria_.dataFimPrevisto));
            }
            if (criteria.getItemAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemAuditoriaId(),
                    root -> root.join(ItemPlanoAuditoria_.itemAuditoria, JoinType.LEFT).get(ItemAuditoria_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ItemPlanoAuditoria_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getPlanoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoId(),
                    root -> root.join(ItemPlanoAuditoria_.plano, JoinType.LEFT).get(PlanoAuditoria_.id)));
            }
        }
        return specification;
    }
}
