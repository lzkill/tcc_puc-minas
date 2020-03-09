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

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.AcaoSGQRepository;
import com.lzkill.sgq.service.dto.AcaoSGQCriteria;

/**
 * Service for executing complex queries for {@link AcaoSGQ} entities in the database.
 * The main input is a {@link AcaoSGQCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcaoSGQ} or a {@link Page} of {@link AcaoSGQ} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcaoSGQQueryService extends QueryService<AcaoSGQ> {

    private final Logger log = LoggerFactory.getLogger(AcaoSGQQueryService.class);

    private final AcaoSGQRepository acaoSGQRepository;

    public AcaoSGQQueryService(AcaoSGQRepository acaoSGQRepository) {
        this.acaoSGQRepository = acaoSGQRepository;
    }

    /**
     * Return a {@link List} of {@link AcaoSGQ} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcaoSGQ> findByCriteria(AcaoSGQCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AcaoSGQ> specification = createSpecification(criteria);
        return acaoSGQRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AcaoSGQ} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcaoSGQ> findByCriteria(AcaoSGQCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AcaoSGQ> specification = createSpecification(criteria);
        return acaoSGQRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcaoSGQCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AcaoSGQ> specification = createSpecification(criteria);
        return acaoSGQRepository.count(specification);
    }

    /**
     * Function to convert {@link AcaoSGQCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AcaoSGQ> createSpecification(AcaoSGQCriteria criteria) {
        Specification<AcaoSGQ> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AcaoSGQ_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), AcaoSGQ_.idUsuarioRegistro));
            }
            if (criteria.getIdUsuarioResponsavel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioResponsavel(), AcaoSGQ_.idUsuarioResponsavel));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), AcaoSGQ_.tipo));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), AcaoSGQ_.titulo));
            }
            if (criteria.getPrazoConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrazoConclusao(), AcaoSGQ_.prazoConclusao));
            }
            if (criteria.getNovoPrazoConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNovoPrazoConclusao(), AcaoSGQ_.novoPrazoConclusao));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), AcaoSGQ_.dataRegistro));
            }
            if (criteria.getDataConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataConclusao(), AcaoSGQ_.dataConclusao));
            }
            if (criteria.getStatusSGQ() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusSGQ(), AcaoSGQ_.statusSGQ));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(AcaoSGQ_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(AcaoSGQ_.naoConformidade, JoinType.LEFT).get(NaoConformidade_.id)));
            }
        }
        return specification;
    }
}
