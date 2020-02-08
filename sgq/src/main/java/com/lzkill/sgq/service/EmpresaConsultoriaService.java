package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.EmpresaConsultoria;
import com.lzkill.sgq.repository.EmpresaConsultoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmpresaConsultoria}.
 */
@Service
@Transactional
public class EmpresaConsultoriaService {

    private final Logger log = LoggerFactory.getLogger(EmpresaConsultoriaService.class);

    private final EmpresaConsultoriaRepository empresaConsultoriaRepository;

    public EmpresaConsultoriaService(EmpresaConsultoriaRepository empresaConsultoriaRepository) {
        this.empresaConsultoriaRepository = empresaConsultoriaRepository;
    }

    /**
     * Save a empresaConsultoria.
     *
     * @param empresaConsultoria the entity to save.
     * @return the persisted entity.
     */
    public EmpresaConsultoria save(EmpresaConsultoria empresaConsultoria) {
        log.debug("Request to save EmpresaConsultoria : {}", empresaConsultoria);
        return empresaConsultoriaRepository.save(empresaConsultoria);
    }

    /**
     * Get all the empresaConsultorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaConsultoria> findAll(Pageable pageable) {
        log.debug("Request to get all EmpresaConsultorias");
        return empresaConsultoriaRepository.findAll(pageable);
    }


    /**
     * Get one empresaConsultoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaConsultoria> findOne(Long id) {
        log.debug("Request to get EmpresaConsultoria : {}", id);
        return empresaConsultoriaRepository.findById(id);
    }

    /**
     * Delete the empresaConsultoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmpresaConsultoria : {}", id);
        empresaConsultoriaRepository.deleteById(id);
    }
}
