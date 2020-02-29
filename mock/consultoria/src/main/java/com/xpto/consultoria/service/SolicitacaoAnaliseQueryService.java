package com.xpto.consultoria.service;

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

import com.xpto.consultoria.domain.SolicitacaoAnalise;
import com.xpto.consultoria.domain.*; // for static metamodels
import com.xpto.consultoria.repository.SolicitacaoAnaliseRepository;
import com.xpto.consultoria.service.dto.SolicitacaoAnaliseCriteria;

/**
 * Service for executing complex queries for {@link SolicitacaoAnalise} entities in the database.
 * The main input is a {@link SolicitacaoAnaliseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SolicitacaoAnalise} or a {@link Page} of {@link SolicitacaoAnalise} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SolicitacaoAnaliseQueryService extends QueryService<SolicitacaoAnalise> {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoAnaliseQueryService.class);

    private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;

    public SolicitacaoAnaliseQueryService(SolicitacaoAnaliseRepository solicitacaoAnaliseRepository) {
        this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
    }

    /**
     * Return a {@link List} of {@link SolicitacaoAnalise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoAnalise> findByCriteria(SolicitacaoAnaliseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SolicitacaoAnalise> specification = createSpecification(criteria);
        return solicitacaoAnaliseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SolicitacaoAnalise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoAnalise> findByCriteria(SolicitacaoAnaliseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SolicitacaoAnalise> specification = createSpecification(criteria);
        return solicitacaoAnaliseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SolicitacaoAnaliseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SolicitacaoAnalise> specification = createSpecification(criteria);
        return solicitacaoAnaliseRepository.count(specification);
    }

    /**
     * Function to convert {@link SolicitacaoAnaliseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SolicitacaoAnalise> createSpecification(SolicitacaoAnaliseCriteria criteria) {
        Specification<SolicitacaoAnalise> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SolicitacaoAnalise_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), SolicitacaoAnalise_.idUsuarioRegistro));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), SolicitacaoAnalise_.dataRegistro));
            }
            if (criteria.getDataSolicitacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataSolicitacao(), SolicitacaoAnalise_.dataSolicitacao));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SolicitacaoAnalise_.status));
            }
            if (criteria.getAnaliseConsultoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnaliseConsultoriaId(),
                    root -> root.join(SolicitacaoAnalise_.analiseConsultoria, JoinType.LEFT).get(AnaliseConsultoria_.id)));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(SolicitacaoAnalise_.naoConformidade, JoinType.LEFT).get(NaoConformidade_.id)));
            }
        }
        return specification;
    }
}
