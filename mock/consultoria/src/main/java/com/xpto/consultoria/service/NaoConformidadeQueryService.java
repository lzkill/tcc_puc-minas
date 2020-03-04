package com.xpto.consultoria.service;

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

import com.xpto.consultoria.domain.NaoConformidade;
import com.xpto.consultoria.domain.*; // for static metamodels
import com.xpto.consultoria.repository.NaoConformidadeRepository;
import com.xpto.consultoria.service.dto.NaoConformidadeCriteria;

/**
 * Service for executing complex queries for {@link NaoConformidade} entities in the database.
 * The main input is a {@link NaoConformidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NaoConformidade} or a {@link Page} of {@link NaoConformidade} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NaoConformidadeQueryService extends QueryService<NaoConformidade> {

    private final Logger log = LoggerFactory.getLogger(NaoConformidadeQueryService.class);

    private final NaoConformidadeRepository naoConformidadeRepository;

    public NaoConformidadeQueryService(NaoConformidadeRepository naoConformidadeRepository) {
        this.naoConformidadeRepository = naoConformidadeRepository;
    }

    /**
     * Return a {@link List} of {@link NaoConformidade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NaoConformidade> findByCriteria(NaoConformidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NaoConformidade> specification = createSpecification(criteria);
        return naoConformidadeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NaoConformidade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NaoConformidade> findByCriteria(NaoConformidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NaoConformidade> specification = createSpecification(criteria);
        return naoConformidadeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NaoConformidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NaoConformidade> specification = createSpecification(criteria);
        return naoConformidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link NaoConformidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NaoConformidade> createSpecification(NaoConformidadeCriteria criteria) {
        Specification<NaoConformidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NaoConformidade_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), NaoConformidade_.titulo));
            }
            if (criteria.getProcedente() != null) {
                specification = specification.and(buildSpecification(criteria.getProcedente(), NaoConformidade_.procedente));
            }
            if (criteria.getPrazoConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrazoConclusao(), NaoConformidade_.prazoConclusao));
            }
            if (criteria.getNovoPrazoConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNovoPrazoConclusao(), NaoConformidade_.novoPrazoConclusao));
            }
            if (criteria.getDataRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataRegistro(), NaoConformidade_.dataRegistro));
            }
            if (criteria.getDataConclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataConclusao(), NaoConformidade_.dataConclusao));
            }
            if (criteria.getStatusSGQ() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusSGQ(), NaoConformidade_.statusSGQ));
            }
            if (criteria.getAcaoSGQId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcaoSGQId(),
                    root -> root.join(NaoConformidade_.acaoSGQS, JoinType.LEFT).get(AcaoSGQ_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(NaoConformidade_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
