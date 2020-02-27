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

import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ResultadoChecklistRepository;
import com.lzkill.sgq.service.dto.ResultadoChecklistCriteria;

/**
 * Service for executing complex queries for {@link ResultadoChecklist} entities in the database.
 * The main input is a {@link ResultadoChecklistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResultadoChecklist} or a {@link Page} of {@link ResultadoChecklist} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultadoChecklistQueryService extends QueryService<ResultadoChecklist> {

    private final Logger log = LoggerFactory.getLogger(ResultadoChecklistQueryService.class);

    private final ResultadoChecklistRepository resultadoChecklistRepository;

    public ResultadoChecklistQueryService(ResultadoChecklistRepository resultadoChecklistRepository) {
        this.resultadoChecklistRepository = resultadoChecklistRepository;
    }

    /**
     * Return a {@link List} of {@link ResultadoChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResultadoChecklist> findByCriteria(ResultadoChecklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResultadoChecklist> specification = createSpecification(criteria);
        return resultadoChecklistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ResultadoChecklist} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoChecklist> findByCriteria(ResultadoChecklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResultadoChecklist> specification = createSpecification(criteria);
        return resultadoChecklistRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultadoChecklistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResultadoChecklist> specification = createSpecification(criteria);
        return resultadoChecklistRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultadoChecklistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResultadoChecklist> createSpecification(ResultadoChecklistCriteria criteria) {
        Specification<ResultadoChecklist> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResultadoChecklist_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), ResultadoChecklist_.idUsuarioRegistro));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), ResultadoChecklist_.dataRegistro));
            }
            if (criteria.getDataVerificacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVerificacao(), ResultadoChecklist_.dataVerificacao));
            }
            if (criteria.getResultadoItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultadoItemId(),
                    root -> root.join(ResultadoChecklist_.resultadoItems, JoinType.LEFT).get(ResultadoItemChecklist_.id)));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(ResultadoChecklist_.naoConformidades, JoinType.LEFT).get(NaoConformidade_.id)));
            }
            if (criteria.getProdutoNaoConformeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoNaoConformeId(),
                    root -> root.join(ResultadoChecklist_.produtoNaoConformes, JoinType.LEFT).get(ProdutoNaoConforme_.id)));
            }
            if (criteria.getChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getChecklistId(),
                    root -> root.join(ResultadoChecklist_.checklist, JoinType.LEFT).get(Checklist_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ResultadoChecklist_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
