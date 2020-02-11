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
            if (criteria.getAcaoSGQId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcaoSGQId(),
                    root -> root.join(Anexo_.acaoSGQ, JoinType.LEFT).get(AcaoSGQ_.id)));
            }
            if (criteria.getAnaliseConsultoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnaliseConsultoriaId(),
                    root -> root.join(Anexo_.analiseConsultoria, JoinType.LEFT).get(AnaliseConsultoria_.id)));
            }
            if (criteria.getChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getChecklistId(),
                    root -> root.join(Anexo_.checklist, JoinType.LEFT).get(Checklist_.id)));
            }
            if (criteria.getBoletimInformativoId() != null) {
                specification = specification.and(buildSpecification(criteria.getBoletimInformativoId(),
                    root -> root.join(Anexo_.boletimInformativo, JoinType.LEFT).get(BoletimInformativo_.id)));
            }
            if (criteria.getCampanhaRecallId() != null) {
                specification = specification.and(buildSpecification(criteria.getCampanhaRecallId(),
                    root -> root.join(Anexo_.campanhaRecall, JoinType.LEFT).get(CampanhaRecall_.id)));
            }
            if (criteria.getEventoOperacionalId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventoOperacionalId(),
                    root -> root.join(Anexo_.eventoOperacional, JoinType.LEFT).get(EventoOperacional_.id)));
            }
            if (criteria.getItemChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemChecklistId(),
                    root -> root.join(Anexo_.itemChecklist, JoinType.LEFT).get(ItemChecklist_.id)));
            }
            if (criteria.getItemPlanoAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPlanoAuditoriaId(),
                    root -> root.join(Anexo_.itemPlanoAuditoria, JoinType.LEFT).get(ItemPlanoAuditoria_.id)));
            }
            if (criteria.getNaoConformidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoConformidadeId(),
                    root -> root.join(Anexo_.naoConformidade, JoinType.LEFT).get(NaoConformidade_.id)));
            }
            if (criteria.getNormaId() != null) {
                specification = specification.and(buildSpecification(criteria.getNormaId(),
                    root -> root.join(Anexo_.norma, JoinType.LEFT).get(Norma_.id)));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(Anexo_.processo, JoinType.LEFT).get(Processo_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(Anexo_.produto, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getPlanoAuditoriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPlanoAuditoriaId(),
                    root -> root.join(Anexo_.planoAuditoria, JoinType.LEFT).get(PlanoAuditoria_.id)));
            }
            if (criteria.getProdutoNaoConformeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoNaoConformeId(),
                    root -> root.join(Anexo_.produtoNaoConforme, JoinType.LEFT).get(ProdutoNaoConforme_.id)));
            }
            if (criteria.getPublicacaoFeedId() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicacaoFeedId(),
                    root -> root.join(Anexo_.publicacaoFeed, JoinType.LEFT).get(PublicacaoFeed_.id)));
            }
            if (criteria.getResultadoChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultadoChecklistId(),
                    root -> root.join(Anexo_.resultadoChecklist, JoinType.LEFT).get(ResultadoChecklist_.id)));
            }
            if (criteria.getResultadoItemChecklistId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultadoItemChecklistId(),
                    root -> root.join(Anexo_.resultadoItemChecklist, JoinType.LEFT).get(ResultadoItemChecklist_.id)));
            }
        }
        return specification;
    }
}
