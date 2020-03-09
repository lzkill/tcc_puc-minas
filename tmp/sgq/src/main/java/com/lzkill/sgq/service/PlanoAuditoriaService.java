package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.repository.PlanoAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoAuditoria}.
 */
@Service
@Transactional
public class PlanoAuditoriaService {

    private final Logger log = LoggerFactory.getLogger(PlanoAuditoriaService.class);

    private final PlanoAuditoriaRepository planoAuditoriaRepository;

    public PlanoAuditoriaService(PlanoAuditoriaRepository planoAuditoriaRepository) {
        this.planoAuditoriaRepository = planoAuditoriaRepository;
    }

    /**
     * Save a planoAuditoria.
     *
     * @param planoAuditoria the entity to save.
     * @return the persisted entity.
     */
    public PlanoAuditoria save(PlanoAuditoria planoAuditoria) {
        log.debug("Request to save PlanoAuditoria : {}", planoAuditoria);
        return planoAuditoriaRepository.save(planoAuditoria);
    }

    /**
     * Get all the planoAuditorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoAuditoria> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoAuditorias");
        return planoAuditoriaRepository.findAll(pageable);
    }

    /**
     * Get all the planoAuditorias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlanoAuditoria> findAllWithEagerRelationships(Pageable pageable) {
        return planoAuditoriaRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one planoAuditoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoAuditoria> findOne(Long id) {
        log.debug("Request to get PlanoAuditoria : {}", id);
        return planoAuditoriaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the planoAuditoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoAuditoria : {}", id);
        planoAuditoriaRepository.deleteById(id);
    }
}
