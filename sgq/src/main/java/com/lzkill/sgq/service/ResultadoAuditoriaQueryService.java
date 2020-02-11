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

import com.lzkill.sgq.domain.ResultadoAuditoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ResultadoAuditoriaRepository;
import com.lzkill.sgq.service.dto.ResultadoAuditoriaCriteria;

/**
 * Service for executing complex queries for {@link ResultadoAuditoria} entities in the database.
 * The main input is a {@link ResultadoAuditoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResultadoAuditoria} or a {@link Page} of {@link ResultadoAuditoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultadoAuditoriaQueryService extends QueryService<ResultadoAuditoria> {

    private final Logger log = LoggerFactory.getLogger(ResultadoAuditoriaQueryService.class);

    private final ResultadoAuditoriaRepository resultadoAuditoriaRepository;

    public ResultadoAuditoriaQueryService(ResultadoAuditoriaRepository resultadoAuditoriaRepository) {
        this.resultadoAuditoriaRepository = resultadoAuditoriaRepository;
    }

    /**
     * Return a {@link List} of {@link ResultadoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResultadoAuditoria> findByCriteria(ResultadoAuditoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResultadoAuditoria> specification = createSpecification(criteria);
        return resultadoAuditoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ResultadoAuditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoAuditoria> findByCriteria(ResultadoAuditoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResultadoAuditoria> specification = createSpecification(criteria);
        return resultadoAuditoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultadoAuditoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResultadoAuditoria> specification = createSpecification(criteria);
        return resultadoAuditoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultadoAuditoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResultadoAuditoria> createSpecification(ResultadoAuditoriaCriteria criteria) {
        Specification<ResultadoAuditoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResultadoAuditoria_.id));
            }
            if (criteria.getIdUsuarioResponsavel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioResponsavel(), ResultadoAuditoria_.idUsuarioResponsavel));
            }
            if (criteria.getDataInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicio(), ResultadoAuditoria_.dataInicio));
            }
            if (criteria.getDataFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFim(), ResultadoAuditoria_.dataFim));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(ResultadoAuditoria_.naoConformidades, JoinType.LEFT).get(NaoConformidade_.id)));
            }
            if (criteria.getProdutoNaoConformeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoNaoConformeId(),
                    root -> root.join(ResultadoAuditoria_.produtoNaoConformes, JoinType.LEFT).get(ProdutoNaoConforme_.id)));
            }
            if (criteria.getAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuditoriaId(),
                    root -> root.join(ResultadoAuditoria_.auditoria, JoinType.LEFT).get(Auditoria_.id)));
            }
        }
        return specification;
    }
}
