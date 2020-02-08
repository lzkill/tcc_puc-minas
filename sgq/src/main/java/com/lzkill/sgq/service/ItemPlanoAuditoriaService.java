package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import com.lzkill.sgq.repository.ItemPlanoAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemPlanoAuditoria}.
 */
@Service
@Transactional
public class ItemPlanoAuditoriaService {

    private final Logger log = LoggerFactory.getLogger(ItemPlanoAuditoriaService.class);

    private final ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository;

    public ItemPlanoAuditoriaService(ItemPlanoAuditoriaRepository itemPlanoAuditoriaRepository) {
        this.itemPlanoAuditoriaRepository = itemPlanoAuditoriaRepository;
    }

    /**
     * Save a itemPlanoAuditoria.
     *
     * @param itemPlanoAuditoria the entity to save.
     * @return the persisted entity.
     */
    public ItemPlanoAuditoria save(ItemPlanoAuditoria itemPlanoAuditoria) {
        log.debug("Request to save ItemPlanoAuditoria : {}", itemPlanoAuditoria);
        return itemPlanoAuditoriaRepository.save(itemPlanoAuditoria);
    }

    /**
     * Get all the itemPlanoAuditorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPlanoAuditoria> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPlanoAuditorias");
        return itemPlanoAuditoriaRepository.findAll(pageable);
    }


    /**
     * Get one itemPlanoAuditoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPlanoAuditoria> findOne(Long id) {
        log.debug("Request to get ItemPlanoAuditoria : {}", id);
        return itemPlanoAuditoriaRepository.findById(id);
    }

    /**
     * Delete the itemPlanoAuditoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPlanoAuditoria : {}", id);
        itemPlanoAuditoriaRepository.deleteById(id);
    }
}
