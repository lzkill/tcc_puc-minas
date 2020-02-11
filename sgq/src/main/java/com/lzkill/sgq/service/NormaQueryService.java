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

import com.lzkill.sgq.domain.Norma;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.NormaRepository;
import com.lzkill.sgq.service.dto.NormaCriteria;

/**
 * Service for executing complex queries for {@link Norma} entities in the database.
 * The main input is a {@link NormaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Norma} or a {@link Page} of {@link Norma} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NormaQueryService extends QueryService<Norma> {

    private final Logger log = LoggerFactory.getLogger(NormaQueryService.class);

    private final NormaRepository normaRepository;

    public NormaQueryService(NormaRepository normaRepository) {
        this.normaRepository = normaRepository;
    }

    /**
     * Return a {@link List} of {@link Norma} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Norma> findByCriteria(NormaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Norma> specification = createSpecification(criteria);
        return normaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Norma} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Norma> findByCriteria(NormaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Norma> specification = createSpecification(criteria);
        return normaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NormaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Norma> specification = createSpecification(criteria);
        return normaRepository.count(specification);
    }

    /**
     * Function to convert {@link NormaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Norma> createSpecification(NormaCriteria criteria) {
        Specification<Norma> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Norma_.id));
            }
            if (criteria.getOrgao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgao(), Norma_.orgao));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Norma_.titulo));
            }
            if (criteria.getVersao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVersao(), Norma_.versao));
            }
            if (criteria.getNumeroEdicao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroEdicao(), Norma_.numeroEdicao));
            }
            if (criteria.getDataEdicao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEdicao(), Norma_.dataEdicao));
            }
            if (criteria.getDataInicioValidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicioValidade(), Norma_.dataInicioValidade));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Norma_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaId(),
                    root -> root.join(Norma_.categorias, JoinType.LEFT).get(CategoriaNorma_.id)));
            }
        }
        return specification;
    }
}
