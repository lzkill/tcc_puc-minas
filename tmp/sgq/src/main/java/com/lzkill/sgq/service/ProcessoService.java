package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.repository.ProcessoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Processo}.
 */
@Service
@Transactional
public class ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoService.class);

    private final ProcessoRepository processoRepository;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    /**
     * Save a processo.
     *
     * @param processo the entity to save.
     * @return the persisted entity.
     */
    public Processo save(Processo processo) {
        log.debug("Request to save Processo : {}", processo);
        return processoRepository.save(processo);
    }

    /**
     * Get all the processos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Processo> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable);
    }

    /**
     * Get all the processos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Processo> findAllWithEagerRelationships(Pageable pageable) {
        return processoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Processo> findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.deleteById(id);
    }
}
