package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Processo;
import com.lzkill.sgq.service.ProcessoService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ProcessoCriteria;
import com.lzkill.sgq.service.ProcessoQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Processo}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoResource.class);

    private static final String ENTITY_NAME = "sgqProcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessoService processoService;

    private final ProcessoQueryService processoQueryService;

    public ProcessoResource(ProcessoService processoService, ProcessoQueryService processoQueryService) {
        this.processoService = processoService;
        this.processoQueryService = processoQueryService;
    }

    /**
     * {@code POST  /processos} : Create a new processo.
     *
     * @param processo the processo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processo, or with status {@code 400 (Bad Request)} if the processo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processos")
    public ResponseEntity<Processo> createProcesso(@Valid @RequestBody Processo processo) throws URISyntaxException {
        log.debug("REST request to save Processo : {}", processo);
        if (processo.getId() != null) {
            throw new BadRequestAlertException("A new processo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Processo result = processoService.save(processo);
        return ResponseEntity.created(new URI("/api/processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processos} : Updates an existing processo.
     *
     * @param processo the processo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processo,
     * or with status {@code 400 (Bad Request)} if the processo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processos")
    public ResponseEntity<Processo> updateProcesso(@Valid @RequestBody Processo processo) throws URISyntaxException {
        log.debug("REST request to update Processo : {}", processo);
        if (processo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Processo result = processoService.save(processo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processos} : get all the processos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processos in body.
     */
    @GetMapping("/processos")
    public ResponseEntity<List<Processo>> getAllProcessos(ProcessoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Processos by criteria: {}", criteria);
        Page<Processo> page = processoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /processos/count} : count all the processos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/processos/count")
    public ResponseEntity<Long> countProcessos(ProcessoCriteria criteria) {
        log.debug("REST request to count Processos by criteria: {}", criteria);
        return ResponseEntity.ok().body(processoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /processos/:id} : get the "id" processo.
     *
     * @param id the id of the processo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processos/{id}")
    public ResponseEntity<Processo> getProcesso(@PathVariable Long id) {
        log.debug("REST request to get Processo : {}", id);
        Optional<Processo> processo = processoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processo);
    }

    /**
     * {@code DELETE  /processos/:id} : delete the "id" processo.
     *
     * @param id the id of the processo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processos/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        processoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
