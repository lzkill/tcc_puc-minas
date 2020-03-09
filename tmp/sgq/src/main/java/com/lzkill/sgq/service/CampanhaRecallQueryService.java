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

import com.lzkill.sgq.domain.CampanhaRecall;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.CampanhaRecallRepository;
import com.lzkill.sgq.service.dto.CampanhaRecallCriteria;

/**
 * Service for executing complex queries for {@link CampanhaRecall} entities in the database.
 * The main input is a {@link CampanhaRecallCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CampanhaRecall} or a {@link Page} of {@link CampanhaRecall} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CampanhaRecallQueryService extends QueryService<CampanhaRecall> {

    private final Logger log = LoggerFactory.getLogger(CampanhaRecallQueryService.class);

    private final CampanhaRecallRepository campanhaRecallRepository;

    public CampanhaRecallQueryService(CampanhaRecallRepository campanhaRecallRepository) {
        this.campanhaRecallRepository = campanhaRecallRepository;
    }

    /**
     * Return a {@link List} of {@link CampanhaRecall} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CampanhaRecall> findByCriteria(CampanhaRecallCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CampanhaRecall> specification = createSpecification(criteria);
        return campanhaRecallRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CampanhaRecall} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CampanhaRecall> findByCriteria(CampanhaRecallCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CampanhaRecall> specification = createSpecification(criteria);
        return campanhaRecallRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CampanhaRecallCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CampanhaRecall> specification = createSpecification(criteria);
        return campanhaRecallRepository.count(specification);
    }

    /**
     * Function to convert {@link CampanhaRecallCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CampanhaRecall> createSpecification(CampanhaRecallCriteria criteria) {
        Specification<CampanhaRecall> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CampanhaRecall_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), CampanhaRecall_.idUsuarioRegistro));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), CampanhaRecall_.titulo));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), CampanhaRecall_.dataRegistro));
            }
            if (criteria.getDataInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicio(), CampanhaRecall_.dataInicio));
            }
            if (criteria.getDataFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFim(), CampanhaRecall_.dataFim));
            }
            if (criteria.getDataPublicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPublicacao(), CampanhaRecall_.dataPublicacao));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CampanhaRecall_.status));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(CampanhaRecall_.produto, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getSetorResponsavelId() != null) {
                specification = specification.and(buildSpecification(criteria.getSetorResponsavelId(),
                    root -> root.join(CampanhaRecall_.setorResponsavel, JoinType.LEFT).get(Setor_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(CampanhaRecall_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
