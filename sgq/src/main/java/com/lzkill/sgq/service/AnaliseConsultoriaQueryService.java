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

import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.AnaliseConsultoriaRepository;
import com.lzkill.sgq.service.dto.AnaliseConsultoriaCriteria;

/**
 * Service for executing complex queries for {@link AnaliseConsultoria} entities in the database.
 * The main input is a {@link AnaliseConsultoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnaliseConsultoria} or a {@link Page} of {@link AnaliseConsultoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnaliseConsultoriaQueryService extends QueryService<AnaliseConsultoria> {

    private final Logger log = LoggerFactory.getLogger(AnaliseConsultoriaQueryService.class);

    private final AnaliseConsultoriaRepository analiseConsultoriaRepository;

    public AnaliseConsultoriaQueryService(AnaliseConsultoriaRepository analiseConsultoriaRepository) {
        this.analiseConsultoriaRepository = analiseConsultoriaRepository;
    }

    /**
     * Return a {@link List} of {@link AnaliseConsultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnaliseConsultoria> findByCriteria(AnaliseConsultoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnaliseConsultoria> specification = createSpecification(criteria);
        return analiseConsultoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AnaliseConsultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnaliseConsultoria> findByCriteria(AnaliseConsultoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnaliseConsultoria> specification = createSpecification(criteria);
        return analiseConsultoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnaliseConsultoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnaliseConsultoria> specification = createSpecification(criteria);
        return analiseConsultoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnaliseConsultoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnaliseConsultoria> createSpecification(AnaliseConsultoriaCriteria criteria) {
        Specification<AnaliseConsultoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnaliseConsultoria_.id));
            }
            if (criteria.getDataSolicitacaoAnalise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataSolicitacaoAnalise(), AnaliseConsultoria_.dataSolicitacaoAnalise));
            }
            if (criteria.getDataAnalise() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAnalise(), AnaliseConsultoria_.dataAnalise));
            }
            if (criteria.getResponsavelAnalise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsavelAnalise(), AnaliseConsultoria_.responsavelAnalise));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), AnaliseConsultoria_.status));
            }
            if (criteria.getAcaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcaoId(),
                    root -> root.join(AnaliseConsultoria_.acao, JoinType.LEFT).get(AcaoSGQ_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(AnaliseConsultoria_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(AnaliseConsultoria_.empresa, JoinType.LEFT).get(EmpresaConsultoria_.id)));
            }
        }
        return specification;
    }
}
