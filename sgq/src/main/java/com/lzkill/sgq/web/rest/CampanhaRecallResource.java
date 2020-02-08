package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.CampanhaRecall;
import com.lzkill.sgq.service.CampanhaRecallService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.CampanhaRecallCriteria;
import com.lzkill.sgq.service.CampanhaRecallQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.CampanhaRecall}.
 */
@RestController
@RequestMapping("/api")
public class CampanhaRecallResource {

    private final Logger log = LoggerFactory.getLogger(CampanhaRecallResource.class);

    private static final String ENTITY_NAME = "sgqCampanhaRecall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampanhaRecallService campanhaRecallService;

    private final CampanhaRecallQueryService campanhaRecallQueryService;

    public CampanhaRecallResource(CampanhaRecallService campanhaRecallService, CampanhaRecallQueryService campanhaRecallQueryService) {
        this.campanhaRecallService = campanhaRecallService;
        this.campanhaRecallQueryService = campanhaRecallQueryService;
    }

    /**
     * {@code POST  /campanha-recalls} : Create a new campanhaRecall.
     *
     * @param campanhaRecall the campanhaRecall to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campanhaRecall, or with status {@code 400 (Bad Request)} if the campanhaRecall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campanha-recalls")
    public ResponseEntity<CampanhaRecall> createCampanhaRecall(@Valid @RequestBody CampanhaRecall campanhaRecall) throws URISyntaxException {
        log.debug("REST request to save CampanhaRecall : {}", campanhaRecall);
        if (campanhaRecall.getId() != null) {
            throw new BadRequestAlertException("A new campanhaRecall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampanhaRecall result = campanhaRecallService.save(campanhaRecall);
        return ResponseEntity.created(new URI("/api/campanha-recalls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campanha-recalls} : Updates an existing campanhaRecall.
     *
     * @param campanhaRecall the campanhaRecall to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campanhaRecall,
     * or with status {@code 400 (Bad Request)} if the campanhaRecall is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campanhaRecall couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campanha-recalls")
    public ResponseEntity<CampanhaRecall> updateCampanhaRecall(@Valid @RequestBody CampanhaRecall campanhaRecall) throws URISyntaxException {
        log.debug("REST request to update CampanhaRecall : {}", campanhaRecall);
        if (campanhaRecall.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampanhaRecall result = campanhaRecallService.save(campanhaRecall);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, campanhaRecall.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campanha-recalls} : get all the campanhaRecalls.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campanhaRecalls in body.
     */
    @GetMapping("/campanha-recalls")
    public ResponseEntity<List<CampanhaRecall>> getAllCampanhaRecalls(CampanhaRecallCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CampanhaRecalls by criteria: {}", criteria);
        Page<CampanhaRecall> page = campanhaRecallQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /campanha-recalls/count} : count all the campanhaRecalls.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/campanha-recalls/count")
    public ResponseEntity<Long> countCampanhaRecalls(CampanhaRecallCriteria criteria) {
        log.debug("REST request to count CampanhaRecalls by criteria: {}", criteria);
        return ResponseEntity.ok().body(campanhaRecallQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /campanha-recalls/:id} : get the "id" campanhaRecall.
     *
     * @param id the id of the campanhaRecall to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campanhaRecall, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campanha-recalls/{id}")
    public ResponseEntity<CampanhaRecall> getCampanhaRecall(@PathVariable Long id) {
        log.debug("REST request to get CampanhaRecall : {}", id);
        Optional<CampanhaRecall> campanhaRecall = campanhaRecallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campanhaRecall);
    }

    /**
     * {@code DELETE  /campanha-recalls/:id} : delete the "id" campanhaRecall.
     *
     * @param id the id of the campanhaRecall to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campanha-recalls/{id}")
    public ResponseEntity<Void> deleteCampanhaRecall(@PathVariable Long id) {
        log.debug("REST request to delete CampanhaRecall : {}", id);
        campanhaRecallService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
