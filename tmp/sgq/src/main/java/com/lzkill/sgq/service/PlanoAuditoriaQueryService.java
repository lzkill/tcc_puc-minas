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

import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.PlanoAuditoriaRepository;
import com.lzkill.sgq.service.dto.PlanoAuditoriaCriteria;

/**
 * Service for executing complex queries for {@link PlanoAuditoria} entities in the database.
 * The main input is a {@link PlanoAuditoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoAuditoria} or a {@link Page} of {@link PlanoAuditoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoAuditoriaQueryService extends QueryService<PlanoAuditoria> {

    private final Logger log = LoggerFactory.getLogger(PlanoAuditoriaQueryService.class);

    private final PlanoAuditoriaRepository planoAuditoriaRepository;

    public PlanoAuditoriaQueryService(PlanoAuditoriaRepository planoAuditoriaRepository) {
        this.planoAuditoriaRepository = planoAuditoriaRepository;
    }

    /**
     * Return a {@link List} of {@link PlanoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoAuditoria> findByCriteria(PlanoAuditoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoAuditoria> specification = createSpecification(criteria);
        return planoAuditoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PlanoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoAuditoria> findByCriteria(PlanoAuditoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoAuditoria> specification = createSpecification(criteria);
        return planoAuditoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoAuditoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoAuditoria> specification = createSpecification(criteria);
        return planoAuditoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoAuditoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoAuditoria> createSpecification(PlanoAuditoriaCriteria criteria) {
        Specification<PlanoAuditoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoAuditoria_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), PlanoAuditoria_.titulo));
            }
            if (criteria.getHabilitado() != null) {
                specification = specification.and(buildSpecification(criteria.getHabilitado(), PlanoAuditoria_.habilitado));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(PlanoAuditoria_.items, JoinType.LEFT).get(ItemPlanoAuditoria_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(PlanoAuditoria_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
