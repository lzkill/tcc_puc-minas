package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.repository.ItemChecklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemChecklist}.
 */
@Service
@Transactional
public class ItemChecklistService {

    private final Logger log = LoggerFactory.getLogger(ItemChecklistService.class);

    private final ItemChecklistRepository itemChecklistRepository;

    public ItemChecklistService(ItemChecklistRepository itemChecklistRepository) {
        this.itemChecklistRepository = itemChecklistRepository;
    }

    /**
     * Save a itemChecklist.
     *
     * @param itemChecklist the entity to save.
     * @return the persisted entity.
     */
    public ItemChecklist save(ItemChecklist itemChecklist) {
        log.debug("Request to save ItemChecklist : {}", itemChecklist);
        return itemChecklistRepository.save(itemChecklist);
    }

    /**
     * Get all the itemChecklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemChecklist> findAll(Pageable pageable) {
        log.debug("Request to get all ItemChecklists");
        return itemChecklistRepository.findAll(pageable);
    }


    /**
     * Get one itemChecklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemChecklist> findOne(Long id) {
        log.debug("Request to get ItemChecklist : {}", id);
        return itemChecklistRepository.findById(id);
    }

    /**
     * Delete the itemChecklist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemChecklist : {}", id);
        itemChecklistRepository.deleteById(id);
    }
}
