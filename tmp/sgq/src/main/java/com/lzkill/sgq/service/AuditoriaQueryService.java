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

import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.AuditoriaRepository;
import com.lzkill.sgq.service.dto.AuditoriaCriteria;

/**
 * Service for executing complex queries for {@link Auditoria} entities in the database.
 * The main input is a {@link AuditoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Auditoria} or a {@link Page} of {@link Auditoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuditoriaQueryService extends QueryService<Auditoria> {

    private final Logger log = LoggerFactory.getLogger(AuditoriaQueryService.class);

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaQueryService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    /**
     * Return a {@link List} of {@link Auditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Auditoria> findByCriteria(AuditoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Auditoria> specification = createSpecification(criteria);
        return auditoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Auditoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Auditoria> findByCriteria(AuditoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Auditoria> specification = createSpecification(criteria);
        return auditoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuditoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Auditoria> specification = createSpecification(criteria);
        return auditoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link AuditoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Auditoria> createSpecification(AuditoriaCriteria criteria) {
        Specification<Auditoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Auditoria_.id));
            }
            if (criteria.getIdUsuarioRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdUsuarioRegistro(), Auditoria_.idUsuarioRegistro));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Auditoria_.titulo));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), Auditoria_.dataRegistro));
            }
            if (criteria.getDataInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicio(), Auditoria_.dataInicio));
            }
            if (criteria.getDataFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFim(), Auditoria_.dataFim));
            }
            if (criteria.getAuditor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditor(), Auditoria_.auditor));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(Auditoria_.naoConformidades, JoinType.LEFT).get(NaoConformidade_.id)));
            }
            if (criteria.getConsultoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getConsultoriaId(),
                    root -> root.join(Auditoria_.consultoria, JoinType.LEFT).get(Consultoria_.id)));
            }
            if (criteria.getItemAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemAuditoriaId(),
                    root -> root.join(Auditoria_.itemAuditorias, JoinType.LEFT).get(ItemAuditoria_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Auditoria_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
