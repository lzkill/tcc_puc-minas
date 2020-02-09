package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Norma;
import com.lzkill.sgq.repository.NormaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Norma}.
 */
@Service
@Transactional
public class NormaService {

    private final Logger log = LoggerFactory.getLogger(NormaService.class);

    private final NormaRepository normaRepository;

    public NormaService(NormaRepository normaRepository) {
        this.normaRepository = normaRepository;
    }

    /**
     * Save a norma.
     *
     * @param norma the entity to save.
     * @return the persisted entity.
     */
    public Norma save(Norma norma) {
        log.debug("Request to save Norma : {}", norma);
        return normaRepository.save(norma);
    }

    /**
     * Get all the normas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Norma> findAll(Pageable pageable) {
        log.debug("Request to get all Normas");
        return normaRepository.findAll(pageable);
    }

    /**
     * Get all the normas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Norma> findAllWithEagerRelationships(Pageable pageable) {
        return normaRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one norma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Norma> findOne(Long id) {
        log.debug("Request to get Norma : {}", id);
        return normaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the norma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Norma : {}", id);
        normaRepository.deleteById(id);
    }
}