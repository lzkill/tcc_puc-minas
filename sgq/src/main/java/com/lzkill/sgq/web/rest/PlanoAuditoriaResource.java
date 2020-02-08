package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.PlanoAuditoria;
import com.lzkill.sgq.service.PlanoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.PlanoAuditoriaCriteria;
import com.lzkill.sgq.service.PlanoAuditoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.PlanoAuditoria}.
 */
@RestController
@RequestMapping("/api")
public class PlanoAuditoriaResource {

    private final Logger log = LoggerFactory.getLogger(PlanoAuditoriaResource.class);

    private static final String ENTITY_NAME = "sgqPlanoAuditoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoAuditoriaService planoAuditoriaService;

    private final PlanoAuditoriaQueryService planoAuditoriaQueryService;

    public PlanoAuditoriaResource(PlanoAuditoriaService planoAuditoriaService, PlanoAuditoriaQueryService planoAuditoriaQueryService) {
        this.planoAuditoriaService = planoAuditoriaService;
        this.planoAuditoriaQueryService = planoAuditoriaQueryService;
    }

    /**
     * {@code POST  /plano-auditorias} : Create a new planoAuditoria.
     *
     * @param planoAuditoria the planoAuditoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoAuditoria, or with status {@code 400 (Bad Request)} if the planoAuditoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-auditorias")
    public ResponseEntity<PlanoAuditoria> createPlanoAuditoria(@Valid @RequestBody PlanoAuditoria planoAuditoria) throws URISyntaxException {
        log.debug("REST request to save PlanoAuditoria : {}", planoAuditoria);
        if (planoAuditoria.getId() != null) {
            throw new BadRequestAlertException("A new planoAuditoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoAuditoria result = planoAuditoriaService.save(planoAuditoria);
        return ResponseEntity.created(new URI("/api/plano-auditorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-auditorias} : Updates an existing planoAuditoria.
     *
     * @param planoAuditoria the planoAuditoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAuditoria,
     * or with status {@code 400 (Bad Request)} if the planoAuditoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoAuditoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-auditorias")
    public ResponseEntity<PlanoAuditoria> updatePlanoAuditoria(@Valid @RequestBody PlanoAuditoria planoAuditoria) throws URISyntaxException {
        log.debug("REST request to update PlanoAuditoria : {}", planoAuditoria);
        if (planoAuditoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoAuditoria result = planoAuditoriaService.save(planoAuditoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planoAuditoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-auditorias} : get all the planoAuditorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoAuditorias in body.
     */
    @GetMapping("/plano-auditorias")
    public ResponseEntity<List<PlanoAuditoria>> getAllPlanoAuditorias(PlanoAuditoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PlanoAuditorias by criteria: {}", criteria);
        Page<PlanoAuditoria> page = planoAuditoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /plano-auditorias/count} : count all the planoAuditorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/plano-auditorias/count")
    public ResponseEntity<Long> countPlanoAuditorias(PlanoAuditoriaCriteria criteria) {
        log.debug("REST request to count PlanoAuditorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoAuditoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-auditorias/:id} : get the "id" planoAuditoria.
     *
     * @param id the id of the planoAuditoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoAuditoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-auditorias/{id}")
    public ResponseEntity<PlanoAuditoria> getPlanoAuditoria(@PathVariable Long id) {
        log.debug("REST request to get PlanoAuditoria : {}", id);
        Optional<PlanoAuditoria> planoAuditoria = planoAuditoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoAuditoria);
    }

    /**
     * {@code DELETE  /plano-auditorias/:id} : delete the "id" planoAuditoria.
     *
     * @param id the id of the planoAuditoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-auditorias/{id}")
    public ResponseEntity<Void> deletePlanoAuditoria(@PathVariable Long id) {
        log.debug("REST request to delete PlanoAuditoria : {}", id);
        planoAuditoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
