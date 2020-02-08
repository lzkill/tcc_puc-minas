package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.CampanhaRecall;
import com.lzkill.sgq.repository.CampanhaRecallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CampanhaRecall}.
 */
@Service
@Transactional
public class CampanhaRecallService {

    private final Logger log = LoggerFactory.getLogger(CampanhaRecallService.class);

    private final CampanhaRecallRepository campanhaRecallRepository;

    public CampanhaRecallService(CampanhaRecallRepository campanhaRecallRepository) {
        this.campanhaRecallRepository = campanhaRecallRepository;
    }

    /**
     * Save a campanhaRecall.
     *
     * @param campanhaRecall the entity to save.
     * @return the persisted entity.
     */
    public CampanhaRecall save(CampanhaRecall campanhaRecall) {
        log.debug("Request to save CampanhaRecall : {}", campanhaRecall);
        return campanhaRecallRepository.save(campanhaRecall);
    }

    /**
     * Get all the campanhaRecalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CampanhaRecall> findAll(Pageable pageable) {
        log.debug("Request to get all CampanhaRecalls");
        return campanhaRecallRepository.findAll(pageable);
    }


    /**
     * Get one campanhaRecall by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CampanhaRecall> findOne(Long id) {
        log.debug("Request to get CampanhaRecall : {}", id);
        return campanhaRecallRepository.findById(id);
    }

    /**
     * Delete the campanhaRecall by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CampanhaRecall : {}", id);
        campanhaRecallRepository.deleteById(id);
    }
}
