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

import com.lzkill.sgq.domain.EmpresaConsultoria;
import com.lzkill.sgq.domain.*; // for static metamodels
import com.lzkill.sgq.repository.EmpresaConsultoriaRepository;
import com.lzkill.sgq.service.dto.EmpresaConsultoriaCriteria;

/**
 * Service for executing complex queries for {@link EmpresaConsultoria} entities in the database.
 * The main input is a {@link EmpresaConsultoriaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpresaConsultoria} or a {@link Page} of {@link EmpresaConsultoria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpresaConsultoriaQueryService extends QueryService<EmpresaConsultoria> {

    private final Logger log = LoggerFactory.getLogger(EmpresaConsultoriaQueryService.class);

    private final EmpresaConsultoriaRepository empresaConsultoriaRepository;

    public EmpresaConsultoriaQueryService(EmpresaConsultoriaRepository empresaConsultoriaRepository) {
        this.empresaConsultoriaRepository = empresaConsultoriaRepository;
    }

    /**
     * Return a {@link List} of {@link EmpresaConsultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpresaConsultoria> findByCriteria(EmpresaConsultoriaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmpresaConsultoria> specification = createSpecification(criteria);
        return empresaConsultoriaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmpresaConsultoria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaConsultoria> findByCriteria(EmpresaConsultoriaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpresaConsultoria> specification = createSpecification(criteria);
        return empresaConsultoriaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpresaConsultoriaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmpresaConsultoria> specification = createSpecification(criteria);
        return empresaConsultoriaRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpresaConsultoriaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpresaConsultoria> createSpecification(EmpresaConsultoriaCriteria criteria) {
        Specification<EmpresaConsultoria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmpresaConsultoria_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), EmpresaConsultoria_.nome));
            }
            if (criteria.getUrlIntegracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlIntegracao(), EmpresaConsultoria_.urlIntegracao));
            }
            if (criteria.getTokenAcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTokenAcesso(), EmpresaConsultoria_.tokenAcesso));
            }
        }
        return specification;
    }
}
