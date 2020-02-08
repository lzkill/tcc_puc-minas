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

import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.AnexoRepository;
import com.lzkill.sgq.service.dto.AnexoCriteria;

/**
 * Service for executing complex queries for {@link Anexo} entities in the database.
 * The main input is a {@link AnexoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Anexo} or a {@link Page} of {@link Anexo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnexoQueryService extends QueryService<Anexo> {

    private final Logger log = LoggerFactory.getLogger(AnexoQueryService.class);

    private final AnexoRepository anexoRepository;

    public AnexoQueryService(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    /**
     * Return a {@link List} of {@link Anexo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Anexo> findByCriteria(AnexoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Anexo> specification = createSpecification(criteria);
        return anexoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Anexo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Anexo> findByCriteria(AnexoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Anexo> specification = createSpecification(criteria);
        return anexoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnexoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Anexo> specification = createSpecification(criteria);
        return anexoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnexoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Anexo> createSpecification(AnexoCriteria criteria) {
        Specification<Anexo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Anexo_.id));
            }
            if (criteria.getNomeArquivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeArquivo(), Anexo_.nomeArquivo));
            }
        }
        return specification;
    }
}
