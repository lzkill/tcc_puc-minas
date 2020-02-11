package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ResultadoAuditoria;
import com.lzkill.sgq.repository.ResultadoAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultadoAuditoria}.
 */
@Service
@Transactional
public class ResultadoAuditoriaService {

    private final Logger log = LoggerFactory.getLogger(ResultadoAuditoriaService.class);

    private final ResultadoAuditoriaRepository resultadoAuditoriaRepository;

    public ResultadoAuditoriaService(ResultadoAuditoriaRepository resultadoAuditoriaRepository) {
        this.resultadoAuditoriaRepository = resultadoAuditoriaRepository;
    }

    /**
     * Save a resultadoAuditoria.
     *
     * @param resultadoAuditoria the entity to save.
     * @return the persisted entity.
     */
    public ResultadoAuditoria save(ResultadoAuditoria resultadoAuditoria) {
        log.debug("Request to save ResultadoAuditoria : {}", resultadoAuditoria);
        return resultadoAuditoriaRepository.save(resultadoAuditoria);
    }

    /**
     * Get all the resultadoAuditorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoAuditoria> findAll(Pageable pageable) {
        log.debug("Request to get all ResultadoAuditorias");
        return resultadoAuditoriaRepository.findAll(pageable);
    }


    /**
     * Get one resultadoAuditoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultadoAuditoria> findOne(Long id) {
        log.debug("Request to get ResultadoAuditoria : {}", id);
        return resultadoAuditoriaRepository.findById(id);
    }

    /**
     * Delete the resultadoAuditoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResultadoAuditoria : {}", id);
        resultadoAuditoriaRepository.deleteById(id);
    }
}
