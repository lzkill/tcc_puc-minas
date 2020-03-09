package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Auditoria;
import com.lzkill.sgq.service.AuditoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.AuditoriaCriteria;
import com.lzkill.sgq.service.AuditoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Auditoria}.
 */
@RestController
@RequestMapping("/api")
public class AuditoriaResource {

    private final Logger log = LoggerFactory.getLogger(AuditoriaResource.class);

    private static final String ENTITY_NAME = "sgqAuditoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditoriaService auditoriaService;

    private final AuditoriaQueryService auditoriaQueryService;

    public AuditoriaResource(AuditoriaService auditoriaService, AuditoriaQueryService auditoriaQueryService) {
        this.auditoriaService = auditoriaService;
        this.auditoriaQueryService = auditoriaQueryService;
    }

    /**
     * {@code POST  /auditorias} : Create a new auditoria.
     *
     * @param auditoria the auditoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoria, or with status {@code 400 (Bad Request)} if the auditoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditorias")
    public ResponseEntity<Auditoria> createAuditoria(@Valid @RequestBody Auditoria auditoria) throws URISyntaxException {
        log.debug("REST request to save Auditoria : {}", auditoria);
        if (auditoria.getId() != null) {
            throw new BadRequestAlertException("A new auditoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auditoria result = auditoriaService.save(auditoria);
        return ResponseEntity.created(new URI("/api/auditorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditorias} : Updates an existing auditoria.
     *
     * @param auditoria the auditoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoria,
     * or with status {@code 400 (Bad Request)} if the auditoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditorias")
    public ResponseEntity<Auditoria> updateAuditoria(@Valid @RequestBody Auditoria auditoria) throws URISyntaxException {
        log.debug("REST request to update Auditoria : {}", auditoria);
        if (auditoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Auditoria result = auditoriaService.save(auditoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, auditoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /auditorias} : get all the auditorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditorias in body.
     */
    @GetMapping("/auditorias")
    public ResponseEntity<List<Auditoria>> getAllAuditorias(AuditoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Auditorias by criteria: {}", criteria);
        Page<Auditoria> page = auditoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /auditorias/count} : count all the auditorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/auditorias/count")
    public ResponseEntity<Long> countAuditorias(AuditoriaCriteria criteria) {
        log.debug("REST request to count Auditorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /auditorias/:id} : get the "id" auditoria.
     *
     * @param id the id of the auditoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditorias/{id}")
    public ResponseEntity<Auditoria> getAuditoria(@PathVariable Long id) {
        log.debug("REST request to get Auditoria : {}", id);
        Optional<Auditoria> auditoria = auditoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditoria);
    }

    /**
     * {@code DELETE  /auditorias/:id} : delete the "id" auditoria.
     *
     * @param id the id of the auditoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditorias/{id}")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable Long id) {
        log.debug("REST request to delete Auditoria : {}", id);
        auditoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
