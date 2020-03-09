package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Setor;
import com.lzkill.sgq.service.SetorService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.SetorCriteria;
import com.lzkill.sgq.service.SetorQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Setor}.
 */
@RestController
@RequestMapping("/api")
public class SetorResource {

    private final Logger log = LoggerFactory.getLogger(SetorResource.class);

    private static final String ENTITY_NAME = "sgqSetor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetorService setorService;

    private final SetorQueryService setorQueryService;

    public SetorResource(SetorService setorService, SetorQueryService setorQueryService) {
        this.setorService = setorService;
        this.setorQueryService = setorQueryService;
    }

    /**
     * {@code POST  /setors} : Create a new setor.
     *
     * @param setor the setor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setor, or with status {@code 400 (Bad Request)} if the setor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/setors")
    public ResponseEntity<Setor> createSetor(@Valid @RequestBody Setor setor) throws URISyntaxException {
        log.debug("REST request to save Setor : {}", setor);
        if (setor.getId() != null) {
            throw new BadRequestAlertException("A new setor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Setor result = setorService.save(setor);
        return ResponseEntity.created(new URI("/api/setors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /setors} : Updates an existing setor.
     *
     * @param setor the setor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setor,
     * or with status {@code 400 (Bad Request)} if the setor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/setors")
    public ResponseEntity<Setor> updateSetor(@Valid @RequestBody Setor setor) throws URISyntaxException {
        log.debug("REST request to update Setor : {}", setor);
        if (setor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Setor result = setorService.save(setor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, setor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /setors} : get all the setors.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setors in body.
     */
    @GetMapping("/setors")
    public ResponseEntity<List<Setor>> getAllSetors(SetorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Setors by criteria: {}", criteria);
        Page<Setor> page = setorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /setors/count} : count all the setors.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/setors/count")
    public ResponseEntity<Long> countSetors(SetorCriteria criteria) {
        log.debug("REST request to count Setors by criteria: {}", criteria);
        return ResponseEntity.ok().body(setorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /setors/:id} : get the "id" setor.
     *
     * @param id the id of the setor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/setors/{id}")
    public ResponseEntity<Setor> getSetor(@PathVariable Long id) {
        log.debug("REST request to get Setor : {}", id);
        Optional<Setor> setor = setorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(setor);
    }

    /**
     * {@code DELETE  /setors/:id} : delete the "id" setor.
     *
     * @param id the id of the setor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/setors/{id}")
    public ResponseEntity<Void> deleteSetor(@PathVariable Long id) {
        log.debug("REST request to delete Setor : {}", id);
        setorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
