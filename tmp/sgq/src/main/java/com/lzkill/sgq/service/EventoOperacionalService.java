package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.EventoOperacional;
import com.lzkill.sgq.repository.EventoOperacionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventoOperacional}.
 */
@Service
@Transactional
public class EventoOperacionalService {

    private final Logger log = LoggerFactory.getLogger(EventoOperacionalService.class);

    private final EventoOperacionalRepository eventoOperacionalRepository;

    public EventoOperacionalService(EventoOperacionalRepository eventoOperacionalRepository) {
        this.eventoOperacionalRepository = eventoOperacionalRepository;
    }

    /**
     * Save a eventoOperacional.
     *
     * @param eventoOperacional the entity to save.
     * @return the persisted entity.
     */
    public EventoOperacional save(EventoOperacional eventoOperacional) {
        log.debug("Request to save EventoOperacional : {}", eventoOperacional);
        return eventoOperacionalRepository.save(eventoOperacional);
    }

    /**
     * Get all the eventoOperacionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoOperacional> findAll(Pageable pageable) {
        log.debug("Request to get all EventoOperacionals");
        return eventoOperacionalRepository.findAll(pageable);
    }

    /**
     * Get all the eventoOperacionals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EventoOperacional> findAllWithEagerRelationships(Pageable pageable) {
        return eventoOperacionalRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one eventoOperacional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventoOperacional> findOne(Long id) {
        log.debug("Request to get EventoOperacional : {}", id);
        return eventoOperacionalRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the eventoOperacional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventoOperacional : {}", id);
        eventoOperacionalRepository.deleteById(id);
    }
}
