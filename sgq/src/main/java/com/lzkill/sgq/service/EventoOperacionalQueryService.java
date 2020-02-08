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

import com.lzkill.sgq.domain.EventoOperacional;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.EventoOperacionalRepository;
import com.lzkill.sgq.service.dto.EventoOperacionalCriteria;

/**
 * Service for executing complex queries for {@link EventoOperacional} entities in the database.
 * The main input is a {@link EventoOperacionalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventoOperacional} or a {@link Page} of {@link EventoOperacional} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventoOperacionalQueryService extends QueryService<EventoOperacional> {

    private final Logger log = LoggerFactory.getLogger(EventoOperacionalQueryService.class);

    private final EventoOperacionalRepository eventoOperacionalRepository;

    public EventoOperacionalQueryService(EventoOperacionalRepository eventoOperacionalRepository) {
        this.eventoOperacionalRepository = eventoOperacionalRepository;
    }

    /**
     * Return a {@link List} of {@link EventoOperacional} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventoOperacional> findByCriteria(EventoOperacionalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventoOperacional> specification = createSpecification(criteria);
        return eventoOperacionalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EventoOperacional} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoOperacional> findByCriteria(EventoOperacionalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventoOperacional> specification = createSpecification(criteria);
        return eventoOperacionalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventoOperacionalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventoOperacional> specification = createSpecification(criteria);
        return eventoOperacionalRepository.count(specification);
    }

    /**
     * Function to convert {@link EventoOperacionalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventoOperacional> createSpecification(EventoOperacionalCriteria criteria) {
        Specification<EventoOperacional> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventoOperacional_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), EventoOperacional_.idUsuarioRegistro));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), EventoOperacional_.tipo));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), EventoOperacional_.titulo));
            }
            if (criteria.getDataEvento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEvento(), EventoOperacional_.dataEvento));
            }
            if (criteria.getDuracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuracao(), EventoOperacional_.duracao));
            }
            if (criteria.getHouveParadaProducao() != null) {
                specification = specification.and(buildSpecification(criteria.getHouveParadaProducao(), EventoOperacional_.houveParadaProducao));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(EventoOperacional_.anexo, JoinType.LEFT).get(Anexo_.id)));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(EventoOperacional_.processo, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
