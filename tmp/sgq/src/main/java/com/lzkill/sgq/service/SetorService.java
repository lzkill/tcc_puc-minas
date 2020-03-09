package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.repository.SetorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Setor}.
 */
@Service
@Transactional
public class SetorService {

    private final Logger log = LoggerFactory.getLogger(SetorService.class);

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    /**
     * Save a setor.
     *
     * @param setor the entity to save.
     * @return the persisted entity.
     */
    public Setor save(Setor setor) {
        log.debug("Request to save Setor : {}", setor);
        return setorRepository.save(setor);
    }

    /**
     * Get all the setors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Setor> findAll(Pageable pageable) {
        log.debug("Request to get all Setors");
        return setorRepository.findAll(pageable);
    }


    /**
     * Get one setor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Setor> findOne(Long id) {
        log.debug("Request to get Setor : {}", id);
        return setorRepository.findById(id);
    }

    /**
     * Delete the setor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Setor : {}", id);
        setorRepository.deleteById(id);
    }
}
