package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.repository.AuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Auditoria}.
 */
@Service
@Transactional
public class AuditoriaService {

    private final Logger log = LoggerFactory.getLogger(AuditoriaService.class);

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    /**
     * Save a auditoria.
     *
     * @param auditoria the entity to save.
     * @return the persisted entity.
     */
    public Auditoria save(Auditoria auditoria) {
        log.debug("Request to save Auditoria : {}", auditoria);
        return auditoriaRepository.save(auditoria);
    }

    /**
     * Get all the auditorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Auditoria> findAll(Pageable pageable) {
        log.debug("Request to get all Auditorias");
        return auditoriaRepository.findAll(pageable);
    }


    /**
     * Get one auditoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Auditoria> findOne(Long id) {
        log.debug("Request to get Auditoria : {}", id);
        return auditoriaRepository.findById(id);
    }

    /**
     * Delete the auditoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Auditoria : {}", id);
        auditoriaRepository.deleteById(id);
    }
}
