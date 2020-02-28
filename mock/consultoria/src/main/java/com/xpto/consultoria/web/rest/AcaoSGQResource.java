package com.xpto.consultoria.web.rest;

import com.xpto.consultoria.domain.AcaoSGQ;
import com.xpto.consultoria.service.AcaoSGQService;
import com.xpto.consultoria.web.rest.errors.BadRequestAlertException;
import com.xpto.consultoria.service.dto.AcaoSGQCriteria;
import com.xpto.consultoria.service.AcaoSGQQueryService;

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
 * REST controller for managing {@link com.xpto.consultoria.domain.AcaoSGQ}.
 */
@RestController
@RequestMapping("/api")
public class AcaoSGQResource {

    private final Logger log = LoggerFactory.getLogger(AcaoSGQResource.class);

    private static final String ENTITY_NAME = "consultoriaAcaoSgq";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcaoSGQService acaoSGQService;

    private final AcaoSGQQueryService acaoSGQQueryService;

    public AcaoSGQResource(AcaoSGQService acaoSGQService, AcaoSGQQueryService acaoSGQQueryService) {
        this.acaoSGQService = acaoSGQService;
        this.acaoSGQQueryService = acaoSGQQueryService;
    }

    /**
     * {@code POST  /acao-sgqs} : Create a new acaoSGQ.
     *
     * @param acaoSGQ the acaoSGQ to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acaoSGQ, or with status {@code 400 (Bad Request)} if the acaoSGQ has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acao-sgqs")
    public ResponseEntity<AcaoSGQ> createAcaoSGQ(@Valid @RequestBody AcaoSGQ acaoSGQ) throws URISyntaxException {
        log.debug("REST request to save AcaoSGQ : {}", acaoSGQ);
        if (acaoSGQ.getId() != null) {
            throw new BadRequestAlertException("A new acaoSGQ cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcaoSGQ result = acaoSGQService.save(acaoSGQ);
        return ResponseEntity.created(new URI("/api/acao-sgqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acao-sgqs} : Updates an existing acaoSGQ.
     *
     * @param acaoSGQ the acaoSGQ to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acaoSGQ,
     * or with status {@code 400 (Bad Request)} if the acaoSGQ is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acaoSGQ couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acao-sgqs")
    public ResponseEntity<AcaoSGQ> updateAcaoSGQ(@Valid @RequestBody AcaoSGQ acaoSGQ) throws URISyntaxException {
        log.debug("REST request to update AcaoSGQ : {}", acaoSGQ);
        if (acaoSGQ.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcaoSGQ result = acaoSGQService.save(acaoSGQ);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acaoSGQ.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /acao-sgqs} : get all the acaoSGQS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acaoSGQS in body.
     */
    @GetMapping("/acao-sgqs")
    public ResponseEntity<List<AcaoSGQ>> getAllAcaoSGQS(AcaoSGQCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AcaoSGQS by criteria: {}", criteria);
        Page<AcaoSGQ> page = acaoSGQQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /acao-sgqs/count} : count all the acaoSGQS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/acao-sgqs/count")
    public ResponseEntity<Long> countAcaoSGQS(AcaoSGQCriteria criteria) {
        log.debug("REST request to count AcaoSGQS by criteria: {}", criteria);
        return ResponseEntity.ok().body(acaoSGQQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /acao-sgqs/:id} : get the "id" acaoSGQ.
     *
     * @param id the id of the acaoSGQ to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acaoSGQ, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acao-sgqs/{id}")
    public ResponseEntity<AcaoSGQ> getAcaoSGQ(@PathVariable Long id) {
        log.debug("REST request to get AcaoSGQ : {}", id);
        Optional<AcaoSGQ> acaoSGQ = acaoSGQService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acaoSGQ);
    }

    /**
     * {@code DELETE  /acao-sgqs/:id} : delete the "id" acaoSGQ.
     *
     * @param id the id of the acaoSGQ to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acao-sgqs/{id}")
    public ResponseEntity<Void> deleteAcaoSGQ(@PathVariable Long id) {
        log.debug("REST request to delete AcaoSGQ : {}", id);
        acaoSGQService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
