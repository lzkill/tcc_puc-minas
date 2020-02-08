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

import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.PublicacaoFeedRepository;
import com.lzkill.sgq.service.dto.PublicacaoFeedCriteria;

/**
 * Service for executing complex queries for {@link PublicacaoFeed} entities in the database.
 * The main input is a {@link PublicacaoFeedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PublicacaoFeed} or a {@link Page} of {@link PublicacaoFeed} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PublicacaoFeedQueryService extends QueryService<PublicacaoFeed> {

    private final Logger log = LoggerFactory.getLogger(PublicacaoFeedQueryService.class);

    private final PublicacaoFeedRepository publicacaoFeedRepository;

    public PublicacaoFeedQueryService(PublicacaoFeedRepository publicacaoFeedRepository) {
        this.publicacaoFeedRepository = publicacaoFeedRepository;
    }

    /**
     * Return a {@link List} of {@link PublicacaoFeed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PublicacaoFeed> findByCriteria(PublicacaoFeedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PublicacaoFeed> specification = createSpecification(criteria);
        return publicacaoFeedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PublicacaoFeed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicacaoFeed> findByCriteria(PublicacaoFeedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PublicacaoFeed> specification = createSpecification(criteria);
        return publicacaoFeedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PublicacaoFeedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PublicacaoFeed> specification = createSpecification(criteria);
        return publicacaoFeedRepository.count(specification);
    }

    /**
     * Function to convert {@link PublicacaoFeedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PublicacaoFeed> createSpecification(PublicacaoFeedCriteria criteria) {
        Specification<PublicacaoFeed> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PublicacaoFeed_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), PublicacaoFeed_.idUsuarioRegistro));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), PublicacaoFeed_.titulo));
            }
            if (criteria.getAutor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAutor(), PublicacaoFeed_.autor));
            }
            if (criteria.getUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUri(), PublicacaoFeed_.uri));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), PublicacaoFeed_.link));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), PublicacaoFeed_.dataRegistro));
            }
            if (criteria.getDataPublicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPublicacao(), PublicacaoFeed_.dataPublicacao));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), PublicacaoFeed_.status));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(PublicacaoFeed_.anexo, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getFeedId() != null) {
                specification = specification.and(buildSpecification(criteria.getFeedId(),
                    root -> root.join(PublicacaoFeed_.feed, JoinType.LEFT).get(Feed_.id)));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaId(),
                    root -> root.join(PublicacaoFeed_.categorias, JoinType.LEFT).get(CategoriaPublicacao_.id)));
            }
        }
        return specification;
    }
}
