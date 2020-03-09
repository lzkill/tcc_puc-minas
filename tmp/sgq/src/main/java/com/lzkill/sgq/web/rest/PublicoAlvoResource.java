package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.PublicoAlvo;
import com.lzkill.sgq.service.PublicoAlvoService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.PublicoAlvoCriteria;
import com.lzkill.sgq.service.PublicoAlvoQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.PublicoAlvo}.
 */
@RestController
@RequestMapping("/api")
public class PublicoAlvoResource {

    private final Logger log = LoggerFactory.getLogger(PublicoAlvoResource.class);

    private static final String ENTITY_NAME = "sgqPublicoAlvo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicoAlvoService publicoAlvoService;

    private final PublicoAlvoQueryService publicoAlvoQueryService;

    public PublicoAlvoResource(PublicoAlvoService publicoAlvoService, PublicoAlvoQueryService publicoAlvoQueryService) {
        this.publicoAlvoService = publicoAlvoService;
        this.publicoAlvoQueryService = publicoAlvoQueryService;
    }

    /**
     * {@code POST  /publico-alvos} : Create a new publicoAlvo.
     *
     * @param publicoAlvo the publicoAlvo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicoAlvo, or with status {@code 400 (Bad Request)} if the publicoAlvo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publico-alvos")
    public ResponseEntity<PublicoAlvo> createPublicoAlvo(@Valid @RequestBody PublicoAlvo publicoAlvo) throws URISyntaxException {
        log.debug("REST request to save PublicoAlvo : {}", publicoAlvo);
        if (publicoAlvo.getId() != null) {
            throw new BadRequestAlertException("A new publicoAlvo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicoAlvo result = publicoAlvoService.save(publicoAlvo);
        return ResponseEntity.created(new URI("/api/publico-alvos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publico-alvos} : Updates an existing publicoAlvo.
     *
     * @param publicoAlvo the publicoAlvo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicoAlvo,
     * or with status {@code 400 (Bad Request)} if the publicoAlvo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicoAlvo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publico-alvos")
    public ResponseEntity<PublicoAlvo> updatePublicoAlvo(@Valid @RequestBody PublicoAlvo publicoAlvo) throws URISyntaxException {
        log.debug("REST request to update PublicoAlvo : {}", publicoAlvo);
        if (publicoAlvo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicoAlvo result = publicoAlvoService.save(publicoAlvo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publicoAlvo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /publico-alvos} : get all the publicoAlvos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicoAlvos in body.
     */
    @GetMapping("/publico-alvos")
    public ResponseEntity<List<PublicoAlvo>> getAllPublicoAlvos(PublicoAlvoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PublicoAlvos by criteria: {}", criteria);
        Page<PublicoAlvo> page = publicoAlvoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /publico-alvos/count} : count all the publicoAlvos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/publico-alvos/count")
    public ResponseEntity<Long> countPublicoAlvos(PublicoAlvoCriteria criteria) {
        log.debug("REST request to count PublicoAlvos by criteria: {}", criteria);
        return ResponseEntity.ok().body(publicoAlvoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /publico-alvos/:id} : get the "id" publicoAlvo.
     *
     * @param id the id of the publicoAlvo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicoAlvo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publico-alvos/{id}")
    public ResponseEntity<PublicoAlvo> getPublicoAlvo(@PathVariable Long id) {
        log.debug("REST request to get PublicoAlvo : {}", id);
        Optional<PublicoAlvo> publicoAlvo = publicoAlvoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicoAlvo);
    }

    /**
     * {@code DELETE  /publico-alvos/:id} : delete the "id" publicoAlvo.
     *
     * @param id the id of the publicoAlvo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publico-alvos/{id}")
    public ResponseEntity<Void> deletePublicoAlvo(@PathVariable Long id) {
        log.debug("REST request to delete PublicoAlvo : {}", id);
        publicoAlvoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
