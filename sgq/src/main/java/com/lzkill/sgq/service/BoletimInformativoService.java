package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.repository.BoletimInformativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BoletimInformativo}.
 */
@Service
@Transactional
public class BoletimInformativoService {

    private final Logger log = LoggerFactory.getLogger(BoletimInformativoService.class);

    private final BoletimInformativoRepository boletimInformativoRepository;

    public BoletimInformativoService(BoletimInformativoRepository boletimInformativoRepository) {
        this.boletimInformativoRepository = boletimInformativoRepository;
    }

    /**
     * Save a boletimInformativo.
     *
     * @param boletimInformativo the entity to save.
     * @return the persisted entity.
     */
    public BoletimInformativo save(BoletimInformativo boletimInformativo) {
        log.debug("Request to save BoletimInformativo : {}", boletimInformativo);
        return boletimInformativoRepository.save(boletimInformativo);
    }

    /**
     * Get all the boletimInformativos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BoletimInformativo> findAll(Pageable pageable) {
        log.debug("Request to get all BoletimInformativos");
        return boletimInformativoRepository.findAll(pageable);
    }

    /**
     * Get all the boletimInformativos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BoletimInformativo> findAllWithEagerRelationships(Pageable pageable) {
        return boletimInformativoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one boletimInformativo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BoletimInformativo> findOne(Long id) {
        log.debug("Request to get BoletimInformativo : {}", id);
        return boletimInformativoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the boletimInformativo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BoletimInformativo : {}", id);
        boletimInformativoRepository.deleteById(id);
    }
}
