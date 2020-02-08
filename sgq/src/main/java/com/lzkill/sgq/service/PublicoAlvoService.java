package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.PublicoAlvo;
import com.lzkill.sgq.repository.PublicoAlvoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PublicoAlvo}.
 */
@Service
@Transactional
public class PublicoAlvoService {

    private final Logger log = LoggerFactory.getLogger(PublicoAlvoService.class);

    private final PublicoAlvoRepository publicoAlvoRepository;

    public PublicoAlvoService(PublicoAlvoRepository publicoAlvoRepository) {
        this.publicoAlvoRepository = publicoAlvoRepository;
    }

    /**
     * Save a publicoAlvo.
     *
     * @param publicoAlvo the entity to save.
     * @return the persisted entity.
     */
    public PublicoAlvo save(PublicoAlvo publicoAlvo) {
        log.debug("Request to save PublicoAlvo : {}", publicoAlvo);
        return publicoAlvoRepository.save(publicoAlvo);
    }

    /**
     * Get all the publicoAlvos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicoAlvo> findAll(Pageable pageable) {
        log.debug("Request to get all PublicoAlvos");
        return publicoAlvoRepository.findAll(pageable);
    }


    /**
     * Get one publicoAlvo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicoAlvo> findOne(Long id) {
        log.debug("Request to get PublicoAlvo : {}", id);
        return publicoAlvoRepository.findById(id);
    }

    /**
     * Delete the publicoAlvo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicoAlvo : {}", id);
        publicoAlvoRepository.deleteById(id);
    }
}
