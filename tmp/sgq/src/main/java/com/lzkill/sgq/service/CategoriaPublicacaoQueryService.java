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

import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.CategoriaPublicacaoRepository;
import com.lzkill.sgq.service.dto.CategoriaPublicacaoCriteria;

/**
 * Service for executing complex queries for {@link CategoriaPublicacao} entities in the database.
 * The main input is a {@link CategoriaPublicacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaPublicacao} or a {@link Page} of {@link CategoriaPublicacao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaPublicacaoQueryService extends QueryService<CategoriaPublicacao> {

    private final Logger log = LoggerFactory.getLogger(CategoriaPublicacaoQueryService.class);

    private final CategoriaPublicacaoRepository categoriaPublicacaoRepository;

    public CategoriaPublicacaoQueryService(CategoriaPublicacaoRepository categoriaPublicacaoRepository) {
        this.categoriaPublicacaoRepository = categoriaPublicacaoRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaPublicacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaPublicacao> findByCriteria(CategoriaPublicacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaPublicacao> specification = createSpecification(criteria);
        return categoriaPublicacaoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaPublicacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaPublicacao> findByCriteria(CategoriaPublicacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaPublicacao> specification = createSpecification(criteria);
        return categoriaPublicacaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaPublicacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaPublicacao> specification = createSpecification(criteria);
        return categoriaPublicacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaPublicacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaPublicacao> createSpecification(CategoriaPublicacaoCriteria criteria) {
        Specification<CategoriaPublicacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaPublicacao_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), CategoriaPublicacao_.titulo));
            }
            if (criteria.getHabilitado() != null) {
                specification = specification.and(buildSpecification(criteria.getHabilitado(), CategoriaPublicacao_.habilitado));
            }
            if (criteria.getBoletimId() != null) {
                specification = specification.and(buildSpecification(criteria.getBoletimId(),
                    root -> root.join(CategoriaPublicacao_.boletims, JoinType.LEFT).get(BoletimInformativo_.id)));
            }
            if (criteria.getPublicacaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicacaoId(),
                    root -> root.join(CategoriaPublicacao_.publicacaos, JoinType.LEFT).get(PublicacaoFeed_.id)));
            }
        }
        return specification;
    }
}
