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

import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.SetorRepository;
import com.lzkill.sgq.service.dto.SetorCriteria;

/**
 * Service for executing complex queries for {@link Setor} entities in the database.
 * The main input is a {@link SetorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Setor} or a {@link Page} of {@link Setor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SetorQueryService extends QueryService<Setor> {

    private final Logger log = LoggerFactory.getLogger(SetorQueryService.class);

    private final SetorRepository setorRepository;

    public SetorQueryService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    /**
     * Return a {@link List} of {@link Setor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Setor> findByCriteria(SetorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Setor> specification = createSpecification(criteria);
        return setorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Setor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Setor> findByCriteria(SetorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Setor> specification = createSpecification(criteria);
        return setorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SetorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Setor> specification = createSpecification(criteria);
        return setorRepository.count(specification);
    }

    /**
     * Function to convert {@link SetorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Setor> createSpecification(SetorCriteria criteria) {
        Specification<Setor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Setor_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Setor_.nome));
            }
            if (criteria.getHabilitado() != null) {
                specification = specification.and(buildSpecification(criteria.getHabilitado(), Setor_.habilitado));
            }
            if (criteria.getChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getChecklistId(),
                    root -> root.join(Setor_.checklists, JoinType.LEFT).get(Checklist_.id)));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(Setor_.processos, JoinType.LEFT).get(Processo_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Setor_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
