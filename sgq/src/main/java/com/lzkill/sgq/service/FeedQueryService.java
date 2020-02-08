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

import com.lzkill.sgq.domain.Feed;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.FeedRepository;
import com.lzkill.sgq.service.dto.FeedCriteria;

/**
 * Service for executing complex queries for {@link Feed} entities in the database.
 * The main input is a {@link FeedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Feed} or a {@link Page} of {@link Feed} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedQueryService extends QueryService<Feed> {

    private final Logger log = LoggerFactory.getLogger(FeedQueryService.class);

    private final FeedRepository feedRepository;

    public FeedQueryService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    /**
     * Return a {@link List} of {@link Feed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Feed> findByCriteria(FeedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Feed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Feed> findByCriteria(FeedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.count(specification);
    }

    /**
     * Function to convert {@link FeedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Feed> createSpecification(FeedCriteria criteria) {
        Specification<Feed> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Feed_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), Feed_.idUsuarioRegistro));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), Feed_.tipo));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Feed_.titulo));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Feed_.descricao));
            }
            if (criteria.getUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUri(), Feed_.uri));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Feed_.link));
            }
            if (criteria.getUrlImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlImagem(), Feed_.urlImagem));
            }
            if (criteria.getTituloImagem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTituloImagem(), Feed_.tituloImagem));
            }
            if (criteria.getAlturaImagem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlturaImagem(), Feed_.alturaImagem));
            }
            if (criteria.getLarguraImagem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLarguraImagem(), Feed_.larguraImagem));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), Feed_.dataRegistro));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Feed_.status));
            }
        }
        return specification;
    }
}
