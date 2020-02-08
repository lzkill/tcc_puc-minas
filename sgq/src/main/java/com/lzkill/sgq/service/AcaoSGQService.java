package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.repository.AcaoSGQRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AcaoSGQ}.
 */
@Service
@Transactional
public class AcaoSGQService {

    private final Logger log = LoggerFactory.getLogger(AcaoSGQService.class);

    private final AcaoSGQRepository acaoSGQRepository;

    public AcaoSGQService(AcaoSGQRepository acaoSGQRepository) {
        this.acaoSGQRepository = acaoSGQRepository;
    }

    /**
     * Save a acaoSGQ.
     *
     * @param acaoSGQ the entity to save.
     * @return the persisted entity.
     */
    public AcaoSGQ save(AcaoSGQ acaoSGQ) {
        log.debug("Request to save AcaoSGQ : {}", acaoSGQ);
        return acaoSGQRepository.save(acaoSGQ);
    }

    /**
     * Get all the acaoSGQS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AcaoSGQ> findAll(Pageable pageable) {
        log.debug("Request to get all AcaoSGQS");
        return acaoSGQRepository.findAll(pageable);
    }


    /**
     * Get one acaoSGQ by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AcaoSGQ> findOne(Long id) {
        log.debug("Request to get AcaoSGQ : {}", id);
        return acaoSGQRepository.findById(id);
    }

    /**
     * Delete the acaoSGQ by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AcaoSGQ : {}", id);
        acaoSGQRepository.deleteById(id);
    }
}
