package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SolicitacaoAnalise}.
 */
@Service
@Transactional
public class SolicitacaoAnaliseService {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoAnaliseService.class);

    private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;

    public SolicitacaoAnaliseService(SolicitacaoAnaliseRepository solicitacaoAnaliseRepository) {
        this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
    }

    /**
     * Save a solicitacaoAnalise.
     *
     * @param solicitacaoAnalise the entity to save.
     * @return the persisted entity.
     */
    public SolicitacaoAnalise save(SolicitacaoAnalise solicitacaoAnalise) {
        log.debug("Request to save SolicitacaoAnalise : {}", solicitacaoAnalise);
        return solicitacaoAnaliseRepository.save(solicitacaoAnalise);
    }

    /**
     * Get all the solicitacaoAnalises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoAnalise> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitacaoAnalises");
        return solicitacaoAnaliseRepository.findAll(pageable);
    }


    /**
     * Get one solicitacaoAnalise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SolicitacaoAnalise> findOne(Long id) {
        log.debug("Request to get SolicitacaoAnalise : {}", id);
        return solicitacaoAnaliseRepository.findById(id);
    }

    /**
     * Delete the solicitacaoAnalise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoAnalise : {}", id);
        solicitacaoAnaliseRepository.deleteById(id);
    }
}
