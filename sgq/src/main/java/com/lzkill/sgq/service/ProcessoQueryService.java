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

import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ProcessoRepository;
import com.lzkill.sgq.service.dto.ProcessoCriteria;

/**
 * Service for executing complex queries for {@link Processo} entities in the database.
 * The main input is a {@link ProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Processo} or a {@link Page} of {@link Processo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoQueryService extends QueryService<Processo> {

    private final Logger log = LoggerFactory.getLogger(ProcessoQueryService.class);

    private final ProcessoRepository processoRepository;

    public ProcessoQueryService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    /**
     * Return a {@link List} of {@link Processo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Processo> findByCriteria(ProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Processo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Processo> findByCriteria(ProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Processo> createSpecification(ProcessoCriteria criteria) {
        Specification<Processo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Processo_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Processo_.titulo));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Processo_.anexo, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getSetorId() != null) {
                specification = specification.and(buildSpecification(criteria.getSetorId(),
                    root -> root.join(Processo_.setor, JoinType.LEFT).get(Setor_.id)));
            }
        }
        return specification;
    }
}
