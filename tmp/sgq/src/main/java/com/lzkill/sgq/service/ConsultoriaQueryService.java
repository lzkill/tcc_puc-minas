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

import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.ConsultoriaRepository;
import com.lzkill.sgq.service.dto.ConsultoriaCriteria;

/**
 * Service for executing complex queries for {@link Consultoria} entities in the database.
 * The main input is a {@link ConsultoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Consultoria} or a {@link Page} of {@link Consultoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsultoriaQueryService extends QueryService<Consultoria> {

    private final Logger log = LoggerFactory.getLogger(ConsultoriaQueryService.class);

    private final ConsultoriaRepository consultoriaRepository;

    public ConsultoriaQueryService(ConsultoriaRepository consultoriaRepository) {
        this.consultoriaRepository = consultoriaRepository;
    }

    /**
     * Return a {@link List} of {@link Consultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Consultoria> findByCriteria(ConsultoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consultoria> specification = createSpecification(criteria);
        return consultoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Consultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Consultoria> findByCriteria(ConsultoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consultoria> specification = createSpecification(criteria);
        return consultoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsultoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consultoria> specification = createSpecification(criteria);
        return consultoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsultoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consultoria> createSpecification(ConsultoriaCriteria criteria) {
        Specification<Consultoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consultoria_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Consultoria_.nome));
            }
            if (criteria.getUrlIntegracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlIntegracao(), Consultoria_.urlIntegracao));
            }
            if (criteria.getTokenAcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTokenAcesso(), Consultoria_.tokenAcesso));
            }
            if (criteria.getHabilitado() != null) {
                specification = specification.and(buildSpecification(criteria.getHabilitado(), Consultoria_.habilitado));
            }
        }
        return specification;
    }
}
