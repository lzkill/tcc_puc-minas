package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.service.SolicitacaoAnaliseService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.SolicitacaoAnaliseCriteria;
import com.lzkill.sgq.service.SolicitacaoAnaliseQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lzkill.sgq.domain.SolicitacaoAnalise}.
 */
@RestController
@RequestMapping("/api")
public class SolicitacaoAnaliseResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoAnaliseResource.class);

    private static final String ENTITY_NAME = "sgqSolicitacaoAnalise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoAnaliseService solicitacaoAnaliseService;

    private final SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService;

    public SolicitacaoAnaliseResource(SolicitacaoAnaliseService solicitacaoAnaliseService, SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService) {
        this.solicitacaoAnaliseService = solicitacaoAnaliseService;
        this.solicitacaoAnaliseQueryService = solicitacaoAnaliseQueryService;
    }

    /**
     * {@code POST  /solicitacao-analises} : Create a new solicitacaoAnalise.
     *
     * @param solicitacaoAnalise the solicitacaoAnalise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacaoAnalise, or with status {@code 400 (Bad Request)} if the solicitacaoAnalise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacao-analises")
    public ResponseEntity<SolicitacaoAnalise> createSolicitacaoAnalise(@Valid @RequestBody SolicitacaoAnalise solicitacaoAnalise) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoAnalise : {}", solicitacaoAnalise);
        if (solicitacaoAnalise.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoAnalise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoAnalise result = solicitacaoAnaliseService.save(solicitacaoAnalise);
        return ResponseEntity.created(new URI("/api/solicitacao-analises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-analises} : Updates an existing solicitacaoAnalise.
     *
     * @param solicitacaoAnalise the solicitacaoAnalise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoAnalise,
     * or with status {@code 400 (Bad Request)} if the solicitacaoAnalise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoAnalise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacao-analises")
    public ResponseEntity<SolicitacaoAnalise> updateSolicitacaoAnalise(@Valid @RequestBody SolicitacaoAnalise solicitacaoAnalise) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoAnalise : {}", solicitacaoAnalise);
        if (solicitacaoAnalise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoAnalise result = solicitacaoAnaliseService.save(solicitacaoAnalise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoAnalise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicitacao-analises} : get all the solicitacaoAnalises.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaoAnalises in body.
     */
    @GetMapping("/solicitacao-analises")
    public ResponseEntity<List<SolicitacaoAnalise>> getAllSolicitacaoAnalises(SolicitacaoAnaliseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SolicitacaoAnalises by criteria: {}", criteria);
        Page<SolicitacaoAnalise> page = solicitacaoAnaliseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /solicitacao-analises/count} : count all the solicitacaoAnalises.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacaoAnalise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacao-analises/{id}")
    public ResponseEntity<SolicitacaoAnalise> getSolicitacaoAnalise(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoAnalise : {}", id);
        Optional<SolicitacaoAnalise> solicitacaoAnalise = solicitacaoAnaliseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoAnalise);
    }

    /**
     * {@code DELETE  /solicitacao-analises/:id} : delete the "id" solicitacaoAnalise.
     *
     * @param id the id of the solicitacaoAnalise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacao-analises/{id}")
    public ResponseEntity<Void> deleteSolicitacaoAnalise(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoAnalise : {}", id);
        solicitacaoAnaliseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}