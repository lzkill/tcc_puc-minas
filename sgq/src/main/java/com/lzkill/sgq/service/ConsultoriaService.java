package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.repository.ConsultoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consultoria}.
 */
@Service
@Transactional
public class ConsultoriaService {

    private final Logger log = LoggerFactory.getLogger(ConsultoriaService.class);

    private final ConsultoriaRepository consultoriaRepository;

    public ConsultoriaService(ConsultoriaRepository consultoriaRepository) {
        this.consultoriaRepository = consultoriaRepository;
    }

    /**
     * Save a consultoria.
     *
     * @param consultoria the entity to save.
     * @return the persisted entity.
     */
    public Consultoria save(Consultoria consultoria) {
        log.debug("Request to save Consultoria : {}", consultoria);
        return consultoriaRepository.save(consultoria);
    }

    /**
     * Get all the consultorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Consultoria> findAll(Pageable pageable) {
        log.debug("Request to get all Consultorias");
        return consultoriaRepository.findAll(pageable);
    }


    /**
     * Get one consultoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Consultoria> findOne(Long id) {
        log.debug("Request to get Consultoria : {}", id);
        return consultoriaRepository.findById(id);
    }

    /**
     * Delete the consultoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Consultoria : {}", id);
        consultoriaRepository.deleteById(id);
    }
}
