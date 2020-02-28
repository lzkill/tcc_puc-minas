package com.xpto.consultoria.service;

import com.xpto.consultoria.domain.NaoConformidade;
import com.xpto.consultoria.repository.NaoConformidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NaoConformidade}.
 */
@Service
@Transactional
public class NaoConformidadeService {

    private final Logger log = LoggerFactory.getLogger(NaoConformidadeService.class);

    private final NaoConformidadeRepository naoConformidadeRepository;

    public NaoConformidadeService(NaoConformidadeRepository naoConformidadeRepository) {
        this.naoConformidadeRepository = naoConformidadeRepository;
    }

    /**
     * Save a naoConformidade.
     *
     * @param naoConformidade the entity to save.
     * @return the persisted entity.
     */
    public NaoConformidade save(NaoConformidade naoConformidade) {
        log.debug("Request to save NaoConformidade : {}", naoConformidade);
        return naoConformidadeRepository.save(naoConformidade);
    }

    /**
     * Get all the naoConformidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NaoConformidade> findAll(Pageable pageable) {
        log.debug("Request to get all NaoConformidades");
        return naoConformidadeRepository.findAll(pageable);
    }


    /**
     * Get one naoConformidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NaoConformidade> findOne(Long id) {
        log.debug("Request to get NaoConformidade : {}", id);
        return naoConformidadeRepository.findById(id);
    }

    /**
     * Delete the naoConformidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NaoConformidade : {}", id);
        naoConformidadeRepository.deleteById(id);
    }
}
