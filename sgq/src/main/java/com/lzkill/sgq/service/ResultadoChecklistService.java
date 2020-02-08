package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.repository.ResultadoChecklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultadoChecklist}.
 */
@Service
@Transactional
public class ResultadoChecklistService {

    private final Logger log = LoggerFactory.getLogger(ResultadoChecklistService.class);

    private final ResultadoChecklistRepository resultadoChecklistRepository;

    public ResultadoChecklistService(ResultadoChecklistRepository resultadoChecklistRepository) {
        this.resultadoChecklistRepository = resultadoChecklistRepository;
    }

    /**
     * Save a resultadoChecklist.
     *
     * @param resultadoChecklist the entity to save.
     * @return the persisted entity.
     */
    public ResultadoChecklist save(ResultadoChecklist resultadoChecklist) {
        log.debug("Request to save ResultadoChecklist : {}", resultadoChecklist);
        return resultadoChecklistRepository.save(resultadoChecklist);
    }

    /**
     * Get all the resultadoChecklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoChecklist> findAll(Pageable pageable) {
        log.debug("Request to get all ResultadoChecklists");
        return resultadoChecklistRepository.findAll(pageable);
    }


    /**
     * Get one resultadoChecklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultadoChecklist> findOne(Long id) {
        log.debug("Request to get ResultadoChecklist : {}", id);
        return resultadoChecklistRepository.findById(id);
    }

    /**
     * Delete the resultadoChecklist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResultadoChecklist : {}", id);
        resultadoChecklistRepository.deleteById(id);
    }
}
