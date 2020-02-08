package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.CategoriaNorma;
import com.lzkill.sgq.repository.CategoriaNormaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriaNorma}.
 */
@Service
@Transactional
public class CategoriaNormaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaNormaService.class);

    private final CategoriaNormaRepository categoriaNormaRepository;

    public CategoriaNormaService(CategoriaNormaRepository categoriaNormaRepository) {
        this.categoriaNormaRepository = categoriaNormaRepository;
    }

    /**
     * Save a categoriaNorma.
     *
     * @param categoriaNorma the entity to save.
     * @return the persisted entity.
     */
    public CategoriaNorma save(CategoriaNorma categoriaNorma) {
        log.debug("Request to save CategoriaNorma : {}", categoriaNorma);
        return categoriaNormaRepository.save(categoriaNorma);
    }

    /**
     * Get all the categoriaNormas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaNorma> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaNormas");
        return categoriaNormaRepository.findAll(pageable);
    }


    /**
     * Get one categoriaNorma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaNorma> findOne(Long id) {
        log.debug("Request to get CategoriaNorma : {}", id);
        return categoriaNormaRepository.findById(id);
    }

    /**
     * Delete the categoriaNorma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaNorma : {}", id);
        categoriaNormaRepository.deleteById(id);
    }
}
