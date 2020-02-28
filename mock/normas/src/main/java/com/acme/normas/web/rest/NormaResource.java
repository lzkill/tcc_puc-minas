package com.acme.normas.web.rest;

import com.acme.normas.domain.Norma;
import com.acme.normas.service.NormaService;
import com.acme.normas.web.rest.errors.BadRequestAlertException;
import com.acme.normas.service.dto.NormaCriteria;
import com.acme.normas.service.NormaQueryService;

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
 * REST controller for managing {@link com.acme.normas.domain.Norma}.
 */
@RestController
@RequestMapping("/api")
public class NormaResource {

    private final Logger log = LoggerFactory.getLogger(NormaResource.class);

    private static final String ENTITY_NAME = "normasNorma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormaService normaService;

    private final NormaQueryService normaQueryService;

    public NormaResource(NormaService normaService, NormaQueryService normaQueryService) {
        this.normaService = normaService;
        this.normaQueryService = normaQueryService;
    }

    /**
     * {@code POST  /normas} : Create a new norma.
     *
     * @param norma the norma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new norma, or with status {@code 400 (Bad Request)} if the norma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/normas")
    public ResponseEntity<Norma> createNorma(@Valid @RequestBody Norma norma) throws URISyntaxException {
        log.debug("REST request to save Norma : {}", norma);
        if (norma.getId() != null) {
            throw new BadRequestAlertException("A new norma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Norma result = normaService.save(norma);
        return ResponseEntity.created(new URI("/api/normas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /normas} : Updates an existing norma.
     *
     * @param norma the norma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated norma,
     * or with status {@code 400 (Bad Request)} if the norma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the norma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/normas")
    public ResponseEntity<Norma> updateNorma(@Valid @RequestBody Norma norma) throws URISyntaxException {
        log.debug("REST request to update Norma : {}", norma);
        if (norma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Norma result = normaService.save(norma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, norma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /normas} : get all the normas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normas in body.
     */
    @GetMapping("/normas")
    public ResponseEntity<List<Norma>> getAllNormas(NormaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Normas by criteria: {}", criteria);
        Page<Norma> page = normaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /normas/count} : count all the normas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/normas/count")
    public ResponseEntity<Long> countNormas(NormaCriteria criteria) {
        log.debug("REST request to count Normas by criteria: {}", criteria);
        return ResponseEntity.ok().body(normaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /normas/:id} : get the "id" norma.
     *
     * @param id the id of the norma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the norma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/normas/{id}")
    public ResponseEntity<Norma> getNorma(@PathVariable Long id) {
        log.debug("REST request to get Norma : {}", id);
        Optional<Norma> norma = normaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(norma);
    }

    /**
     * {@code DELETE  /normas/:id} : delete the "id" norma.
     *
     * @param id the id of the norma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/normas/{id}")
    public ResponseEntity<Void> deleteNorma(@PathVariable Long id) {
        log.debug("REST request to delete Norma : {}", id);
        normaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
