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

import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.BoletimInformativoRepository;
import com.lzkill.sgq.service.dto.BoletimInformativoCriteria;

/**
 * Service for executing complex queries for {@link BoletimInformativo} entities in the database.
 * The main input is a {@link BoletimInformativoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BoletimInformativo} or a {@link Page} of {@link BoletimInformativo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BoletimInformativoQueryService extends QueryService<BoletimInformativo> {

    private final Logger log = LoggerFactory.getLogger(BoletimInformativoQueryService.class);

    private final BoletimInformativoRepository boletimInformativoRepository;

    public BoletimInformativoQueryService(BoletimInformativoRepository boletimInformativoRepository) {
        this.boletimInformativoRepository = boletimInformativoRepository;
    }

    /**
     * Return a {@link List} of {@link BoletimInformativo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BoletimInformativo> findByCriteria(BoletimInformativoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BoletimInformativo> specification = createSpecification(criteria);
        return boletimInformativoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BoletimInformativo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimInformativo> findByCriteria(BoletimInformativoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BoletimInformativo> specification = createSpecification(criteria);
        return boletimInformativoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BoletimInformativoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BoletimInformativo> specification = createSpecification(criteria);
        return boletimInformativoRepository.count(specification);
    }

    /**
     * Function to convert {@link BoletimInformativoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BoletimInformativo> createSpecification(BoletimInformativoCriteria criteria) {
        Specification<BoletimInformativo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BoletimInformativo_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), BoletimInformativo_.idUsuarioRegistro));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), BoletimInformativo_.titulo));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), BoletimInformativo_.dataRegistro));
            }
            if (criteria.getDataPublicacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPublicacao(), BoletimInformativo_.dataPublicacao));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), BoletimInformativo_.status));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(BoletimInformativo_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getPublicoAlvoId() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicoAlvoId(),
                    root -> root.join(BoletimInformativo_.publicoAlvo, JoinType.LEFT).get(PublicoAlvo_.id)));
            }
            if (criteria.getCategoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaId(),
                    root -> root.join(BoletimInformativo_.categorias, JoinType.LEFT).get(CategoriaPublicacao_.id)));
            }
        }
        return specification;
    }
}
