package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.repository.CategoriaPublicacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriaPublicacao}.
 */
@Service
@Transactional
public class CategoriaPublicacaoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaPublicacaoService.class);

    private final CategoriaPublicacaoRepository categoriaPublicacaoRepository;

    public CategoriaPublicacaoService(CategoriaPublicacaoRepository categoriaPublicacaoRepository) {
        this.categoriaPublicacaoRepository = categoriaPublicacaoRepository;
    }

    /**
     * Save a categoriaPublicacao.
     *
     * @param categoriaPublicacao the entity to save.
     * @return the persisted entity.
     */
    public CategoriaPublicacao save(CategoriaPublicacao categoriaPublicacao) {
        log.debug("Request to save CategoriaPublicacao : {}", categoriaPublicacao);
        return categoriaPublicacaoRepository.save(categoriaPublicacao);
    }

    /**
     * Get all the categoriaPublicacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaPublicacao> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaPublicacaos");
        return categoriaPublicacaoRepository.findAll(pageable);
    }


    /**
     * Get one categoriaPublicacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaPublicacao> findOne(Long id) {
        log.debug("Request to get CategoriaPublicacao : {}", id);
        return categoriaPublicacaoRepository.findById(id);
    }

    /**
     * Delete the categoriaPublicacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaPublicacao : {}", id);
        categoriaPublicacaoRepository.deleteById(id);
    }
}
