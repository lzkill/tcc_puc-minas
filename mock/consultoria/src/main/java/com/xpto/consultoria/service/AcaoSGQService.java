package com.xpto.consultoria.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xpto.consultoria.domain.AcaoSGQ;
import com.xpto.consultoria.repository.AcaoSGQRepository;

/**
 * Service Implementation for managing {@link AcaoSGQ}.
 */
@Service
@Transactional
public class AcaoSGQService {

	private final Logger log = LoggerFactory.getLogger(AcaoSGQService.class);

	private final AcaoSGQRepository acaoSGQRepository;

	public AcaoSGQService(AcaoSGQRepository acaoSGQRepository) {
		this.acaoSGQRepository = acaoSGQRepository;
	}

	/**
	 * Save a acaoSGQ.
	 *
	 * @param acaoSGQ the entity to save.
	 * @return the persisted entity.
	 */
	public AcaoSGQ save(AcaoSGQ acaoSGQ) {
		log.debug("Request to save AcaoSGQ : {}", acaoSGQ);
		return acaoSGQRepository.save(acaoSGQ);
	}

	public Set<AcaoSGQ> saveAll(Set<AcaoSGQ> acoes) {
		acoes.forEach(a -> save(a));
		return acoes;
	}

	/**
	 * Get all the acaoSGQS.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<AcaoSGQ> findAll(Pageable pageable) {
		log.debug("Request to get all AcaoSGQS");
		return acaoSGQRepository.findAll(pageable);
	}

	/**
	 * Get all the acaoSGQS with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<AcaoSGQ> findAllWithEagerRelationships(Pageable pageable) {
		return acaoSGQRepository.findAllWithEagerRelationships(pageable);
	}

	/**
	 * Get one acaoSGQ by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<AcaoSGQ> findOne(Long id) {
		log.debug("Request to get AcaoSGQ : {}", id);
		return acaoSGQRepository.findOneWithEagerRelationships(id);
	}

	/**
	 * Delete the acaoSGQ by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete AcaoSGQ : {}", id);
		acaoSGQRepository.deleteById(id);
	}
}
