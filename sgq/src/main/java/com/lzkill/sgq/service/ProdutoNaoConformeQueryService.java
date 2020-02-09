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

import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ProdutoNaoConformeRepository;
import com.lzkill.sgq.service.dto.ProdutoNaoConformeCriteria;

/**
 * Service for executing complex queries for {@link ProdutoNaoConforme} entities in the database.
 * The main input is a {@link ProdutoNaoConformeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProdutoNaoConforme} or a {@link Page} of {@link ProdutoNaoConforme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoNaoConformeQueryService extends QueryService<ProdutoNaoConforme> {

    private final Logger log = LoggerFactory.getLogger(ProdutoNaoConformeQueryService.class);

    private final ProdutoNaoConformeRepository produtoNaoConformeRepository;

    public ProdutoNaoConformeQueryService(ProdutoNaoConformeRepository produtoNaoConformeRepository) {
        this.produtoNaoConformeRepository = produtoNaoConformeRepository;
    }

    /**
     * Return a {@link List} of {@link ProdutoNaoConforme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProdutoNaoConforme> findByCriteria(ProdutoNaoConformeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProdutoNaoConforme> specification = createSpecification(criteria);
        return produtoNaoConformeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProdutoNaoConforme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoNaoConforme> findByCriteria(ProdutoNaoConformeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProdutoNaoConforme> specification = createSpecification(criteria);
        return produtoNaoConformeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdutoNaoConformeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProdutoNaoConforme> specification = createSpecification(criteria);
        return produtoNaoConformeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdutoNaoConformeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProdutoNaoConforme> createSpecification(ProdutoNaoConformeCriteria criteria) {
        Specification<ProdutoNaoConforme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProdutoNaoConforme_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), ProdutoNaoConforme_.idUsuarioRegistro));
            }
            if (criteria.getIdUsuarioResponsavel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioResponsavel(), ProdutoNaoConforme_.idUsuarioResponsavel));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), ProdutoNaoConforme_.titulo));
            }
            if (criteria.getProcedente() != null) {
                specification = specification.and(buildSpecification(criteria.getProcedente(), ProdutoNaoConforme_.procedente));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), ProdutoNaoConforme_.dataRegistro));
            }
            if (criteria.getStatusSGQ() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusSGQ(), ProdutoNaoConforme_.statusSGQ));
            }
            if (criteria.getAcaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcaoId(),
                    root -> root.join(ProdutoNaoConforme_.acao, JoinType.LEFT).get(AcaoSGQ_.id)));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(ProdutoNaoConforme_.naoConformidade, JoinType.LEFT).get(NaoConformidade_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(ProdutoNaoConforme_.anexo, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(ProdutoNaoConforme_.produto, JoinType.LEFT).get(Produto_.id)));
            }
        }
        return specification;
    }
}
