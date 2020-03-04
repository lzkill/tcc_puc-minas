package com.xpto.consultoria.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.xpto.consultoria.domain.NaoConformidade;
import com.xpto.consultoria.domain.SolicitacaoAnalise;
import com.xpto.consultoria.domain.enumeration.StatusSolicitacaoAnalise;
import com.xpto.consultoria.service.AcaoSGQService;
import com.xpto.consultoria.service.AnexoService;
import com.xpto.consultoria.service.NaoConformidadeService;
import com.xpto.consultoria.service.SolicitacaoAnaliseQueryService;
import com.xpto.consultoria.service.SolicitacaoAnaliseService;
import com.xpto.consultoria.service.dto.SolicitacaoAnaliseCriteria;
import com.xpto.consultoria.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.xpto.consultoria.domain.SolicitacaoAnalise}.
 */
@RestController
@RequestMapping("/api")
public class SolicitacaoAnaliseResource {

	private final Logger log = LoggerFactory.getLogger(SolicitacaoAnaliseResource.class);

	private static final String ENTITY_NAME = "consultoriaSolicitacaoAnalise";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SolicitacaoAnaliseService solicitacaoAnaliseService;
	private final SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService;
	private final NaoConformidadeService naoConformidadeService;
	private final AcaoSGQService acaoService;
	private final AnexoService anexoService;

	public SolicitacaoAnaliseResource(SolicitacaoAnaliseService solicitacaoAnaliseService,
			SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService,
			NaoConformidadeService naoConformidadeService, AcaoSGQService acaoService, AnexoService anexoService) {
		this.solicitacaoAnaliseService = solicitacaoAnaliseService;
		this.solicitacaoAnaliseQueryService = solicitacaoAnaliseQueryService;
		this.naoConformidadeService = naoConformidadeService;
		this.acaoService = acaoService;
		this.anexoService = anexoService;
	}

	/**
	 * {@code POST  /solicitacao-analises} : Create a new solicitacaoAnalise.
	 *
	 * @param solicitacaoAnalise the solicitacaoAnalise to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new solicitacaoAnalise, or with status
	 *         {@code 400 (Bad Request)} if the solicitacaoAnalise has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/solicitacao-analises")
	public ResponseEntity<SolicitacaoAnalise> createSolicitacaoAnalise(
			@Valid @RequestBody SolicitacaoAnalise solicitacaoAnalise) throws URISyntaxException {
		log.debug("REST request to save SolicitacaoAnalise : {}", solicitacaoAnalise);
		if (solicitacaoAnalise.getId() != null) {
			throw new BadRequestAlertException("A new solicitacaoAnalise cannot already have an ID", ENTITY_NAME,
					"idexists");
		}

		SolicitacaoAnalise result = saveSolicitacaoAnaliseCascade(solicitacaoAnalise);

		return ResponseEntity
				.created(new URI("/api/solicitacao-analises/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	// Forgive them,Father. They don't know what they're doing.
	private SolicitacaoAnalise saveSolicitacaoAnaliseCascade(SolicitacaoAnalise solicitacaoAnalise) {
		NaoConformidade naoConformidade = solicitacaoAnalise.getNaoConformidade();
		naoConformidade.getAcaoSGQS().forEach(a -> {
			anexoService.saveAll(a.getAnexos());
			acaoService.save(a);
		});

		anexoService.saveAll(naoConformidade.getAnexos());

		naoConformidadeService.save(naoConformidade);

		naoConformidade.getAcaoSGQS().forEach(a -> {
			a.setNaoConformidade(naoConformidade);
			acaoService.save(a);
		});

		solicitacaoAnalise.setStatus(StatusSolicitacaoAnalise.PENDENTE);
		solicitacaoAnalise.setDataSolicitacao(Instant.now());

		solicitacaoAnaliseService.save(solicitacaoAnalise);

		naoConformidade.getAcaoSGQS().forEach(a -> {
			a.setNaoConformidade(null);
		});

		return solicitacaoAnalise;
	}

	/**
	 * {@code PUT  /solicitacao-analises} : Updates an existing solicitacaoAnalise.
	 *
	 * @param solicitacaoAnalise the solicitacaoAnalise to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated solicitacaoAnalise, or with status
	 *         {@code 400 (Bad Request)} if the solicitacaoAnalise is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         solicitacaoAnalise couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/solicitacao-analises")
	public ResponseEntity<SolicitacaoAnalise> updateSolicitacaoAnalise(
			@Valid @RequestBody SolicitacaoAnalise solicitacaoAnalise) throws URISyntaxException {
		log.debug("REST request to update SolicitacaoAnalise : {}", solicitacaoAnalise);
		if (solicitacaoAnalise.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		SolicitacaoAnalise result = solicitacaoAnaliseService.save(solicitacaoAnalise);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				solicitacaoAnalise.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /solicitacao-analises} : get all the solicitacaoAnalises.
	 *
	 *
	 * @param pageable the pagination information.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of solicitacaoAnalises in body.
	 */
	@GetMapping("/solicitacao-analises")
	public ResponseEntity<List<SolicitacaoAnalise>> getAllSolicitacaoAnalises(SolicitacaoAnaliseCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get SolicitacaoAnalises by criteria: {}", criteria);
		Page<SolicitacaoAnalise> page = solicitacaoAnaliseQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /solicitacao-analises/count} : count all the solicitacaoAnalises.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/solicitacao-analises/count")
	public ResponseEntity<Long> countSolicitacaoAnalises(SolicitacaoAnaliseCriteria criteria) {
		log.debug("REST request to count SolicitacaoAnalises by criteria: {}", criteria);
		return ResponseEntity.ok().body(solicitacaoAnaliseQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /solicitacao-analises/:id} : get the "id" solicitacaoAnalise.
	 *
	 * @param id the id of the solicitacaoAnalise to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the solicitacaoAnalise, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/solicitacao-analises/{id}")
	public ResponseEntity<SolicitacaoAnalise> getSolicitacaoAnalise(@PathVariable Long id) {
		log.debug("REST request to get SolicitacaoAnalise : {}", id);
		Optional<SolicitacaoAnalise> solicitacaoAnalise = solicitacaoAnaliseService.findOne(id);
		return ResponseUtil.wrapOrNotFound(solicitacaoAnalise);
	}

	/**
	 * {@code DELETE  /solicitacao-analises/:id} : delete the "id"
	 * solicitacaoAnalise.
	 *
	 * @param id the id of the solicitacaoAnalise to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/solicitacao-analises/{id}")
	public ResponseEntity<Void> deleteSolicitacaoAnalise(@PathVariable Long id) {
		log.debug("REST request to delete SolicitacaoAnalise : {}", id);
		solicitacaoAnaliseService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
}
