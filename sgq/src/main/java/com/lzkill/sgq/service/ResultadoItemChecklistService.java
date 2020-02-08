package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.repository.ResultadoItemChecklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultadoItemChecklist}.
 */
@Service
@Transactional
public class ResultadoItemChecklistService {

    private final Logger log = LoggerFactory.getLogger(ResultadoItemChecklistService.class);

    private final ResultadoItemChecklistRepository resultadoItemChecklistRepository;

    public ResultadoItemChecklistService(ResultadoItemChecklistRepository resultadoItemChecklistRepository) {
        this.resultadoItemChecklistRepository = resultadoItemChecklistRepository;
    }

    /**
     * Save a resultadoItemChecklist.
     *
     * @param resultadoItemChecklist the entity to save.
     * @return the persisted entity.
     */
    public ResultadoItemChecklist save(ResultadoItemChecklist resultadoItemChecklist) {
        log.debug("Request to save ResultadoItemChecklist : {}", resultadoItemChecklist);
        return resultadoItemChecklistRepository.save(resultadoItemChecklist);
    }

    /**
     * Get all the resultadoItemChecklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoItemChecklist> findAll(Pageable pageable) {
        log.debug("Request to get all ResultadoItemChecklists");
        return resultadoItemChecklistRepository.findAll(pageable);
    }


    /**
     * Get one resultadoItemChecklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultadoItemChecklist> findOne(Long id) {
        log.debug("Request to get ResultadoItemChecklist : {}", id);
        return resultadoItemChecklistRepository.findById(id);
    }

    /**
     * Delete the resultadoItemChecklist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResultadoItemChecklist : {}", id);
        resultadoItemChecklistRepository.deleteById(id);
    }
}
