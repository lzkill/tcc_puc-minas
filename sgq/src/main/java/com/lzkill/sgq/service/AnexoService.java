package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.repository.AnexoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Anexo}.
 */
@Service
@Transactional
public class AnexoService {

    private final Logger log = LoggerFactory.getLogger(AnexoService.class);

    private final AnexoRepository anexoRepository;

    public AnexoService(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    /**
     * Save a anexo.
     *
     * @param anexo the entity to save.
     * @return the persisted entity.
     */
    public Anexo save(Anexo anexo) {
        log.debug("Request to save Anexo : {}", anexo);
        return anexoRepository.save(anexo);
    }

    /**
     * Get all the anexos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Anexo> findAll(Pageable pageable) {
        log.debug("Request to get all Anexos");
        return anexoRepository.findAll(pageable);
    }


    /**
     * Get one anexo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Anexo> findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        return anexoRepository.findById(id);
    }

    /**
     * Delete the anexo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.deleteById(id);
    }
}
