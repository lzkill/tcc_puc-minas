package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.repository.ChecklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Checklist}.
 */
@Service
@Transactional
public class ChecklistService {

    private final Logger log = LoggerFactory.getLogger(ChecklistService.class);

    private final ChecklistRepository checklistRepository;

    public ChecklistService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    /**
     * Save a checklist.
     *
     * @param checklist the entity to save.
     * @return the persisted entity.
     */
    public Checklist save(Checklist checklist) {
        log.debug("Request to save Checklist : {}", checklist);
        return checklistRepository.save(checklist);
    }

    /**
     * Get all the checklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Checklist> findAll(Pageable pageable) {
        log.debug("Request to get all Checklists");
        return checklistRepository.findAll(pageable);
    }


    /**
     * Get one checklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Checklist> findOne(Long id) {
        log.debug("Request to get Checklist : {}", id);
        return checklistRepository.findById(id);
    }

    /**
     * Delete the checklist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Checklist : {}", id);
        checklistRepository.deleteById(id);
    }
}
