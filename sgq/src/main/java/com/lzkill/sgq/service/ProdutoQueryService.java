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

import com.lzkill.sgq.domain.Produto;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ProdutoRepository;
import com.lzkill.sgq.service.dto.ProdutoCriteria;

/**
 * Service for executing complex queries for {@link Produto} entities in the database.
 * The main input is a {@link ProdutoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Produto} or a {@link Page} of {@link Produto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoQueryService extends QueryService<Produto> {

    private final Logger log = LoggerFactory.getLogger(ProdutoQueryService.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoQueryService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Return a {@link List} of {@link Produto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Produto> findByCriteria(ProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Produto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Produto> findByCriteria(ProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdutoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdutoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produto> createSpecification(ProdutoCriteria criteria) {
        Specification<Produto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Produto_.nome));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Produto_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Produto_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
