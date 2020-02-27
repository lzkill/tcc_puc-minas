package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.repository.ItemAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemAuditoria}.
 */
@Service
@Transactional
public class ItemAuditoriaService {

    private final Logger log = LoggerFactory.getLogger(ItemAuditoriaService.class);

    private final ItemAuditoriaRepository itemAuditoriaRepository;

    public ItemAuditoriaService(ItemAuditoriaRepository itemAuditoriaRepository) {
        this.itemAuditoriaRepository = itemAuditoriaRepository;
    }

    /**
     * Save a itemAuditoria.
     *
     * @param itemAuditoria the entity to save.
     * @return the persisted entity.
     */
    public ItemAuditoria save(ItemAuditoria itemAuditoria) {
        log.debug("Request to save ItemAuditoria : {}", itemAuditoria);
        return itemAuditoriaRepository.save(itemAuditoria);
    }

    /**
     * Get all the itemAuditorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemAuditoria> findAll(Pageable pageable) {
        log.debug("Request to get all ItemAuditorias");
        return itemAuditoriaRepository.findAll(pageable);
    }

    /**
     * Get all the itemAuditorias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ItemAuditoria> findAllWithEagerRelationships(Pageable pageable) {
        return itemAuditoriaRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one itemAuditoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemAuditoria> findOne(Long id) {
        log.debug("Request to get ItemAuditoria : {}", id);
        return itemAuditoriaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the itemAuditoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemAuditoria : {}", id);
        itemAuditoriaRepository.deleteById(id);
    }
}
