package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.repository.ProdutoNaoConformeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProdutoNaoConforme}.
 */
@Service
@Transactional
public class ProdutoNaoConformeService {

    private final Logger log = LoggerFactory.getLogger(ProdutoNaoConformeService.class);

    private final ProdutoNaoConformeRepository produtoNaoConformeRepository;

    public ProdutoNaoConformeService(ProdutoNaoConformeRepository produtoNaoConformeRepository) {
        this.produtoNaoConformeRepository = produtoNaoConformeRepository;
    }

    /**
     * Save a produtoNaoConforme.
     *
     * @param produtoNaoConforme the entity to save.
     * @return the persisted entity.
     */
    public ProdutoNaoConforme save(ProdutoNaoConforme produtoNaoConforme) {
        log.debug("Request to save ProdutoNaoConforme : {}", produtoNaoConforme);
        return produtoNaoConformeRepository.save(produtoNaoConforme);
    }

    /**
     * Get all the produtoNaoConformes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoNaoConforme> findAll(Pageable pageable) {
        log.debug("Request to get all ProdutoNaoConformes");
        return produtoNaoConformeRepository.findAll(pageable);
    }

    /**
     * Get all the produtoNaoConformes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProdutoNaoConforme> findAllWithEagerRelationships(Pageable pageable) {
        return produtoNaoConformeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one produtoNaoConforme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdutoNaoConforme> findOne(Long id) {
        log.debug("Request to get ProdutoNaoConforme : {}", id);
        return produtoNaoConformeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the produtoNaoConforme by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProdutoNaoConforme : {}", id);
        produtoNaoConformeRepository.deleteById(id);
    }
}
