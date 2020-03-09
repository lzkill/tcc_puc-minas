package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Checklist;
import com.lzkill.sgq.service.ChecklistService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ChecklistCriteria;
import com.lzkill.sgq.service.ChecklistQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Checklist}.
 */
@RestController
@RequestMapping("/api")
public class ChecklistResource {

    private final Logger log = LoggerFactory.getLogger(ChecklistResource.class);

    private static final String ENTITY_NAME = "sgqChecklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChecklistService checklistService;

    private final ChecklistQueryService checklistQueryService;

    public ChecklistResource(ChecklistService checklistService, ChecklistQueryService checklistQueryService) {
        this.checklistService = checklistService;
        this.checklistQueryService = checklistQueryService;
    }

    /**
     * {@code POST  /checklists} : Create a new checklist.
     *
     * @param checklist the checklist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checklist, or with status {@code 400 (Bad Request)} if the checklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/checklists")
    public ResponseEntity<Checklist> createChecklist(@Valid @RequestBody Checklist checklist) throws URISyntaxException {
        log.debug("REST request to save Checklist : {}", checklist);
        if (checklist.getId() != null) {
            throw new BadRequestAlertException("A new checklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Checklist result = checklistService.save(checklist);
        return ResponseEntity.created(new URI("/api/checklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /checklists} : Updates an existing checklist.
     *
     * @param checklist the checklist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checklist,
     * or with status {@code 400 (Bad Request)} if the checklist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checklist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/checklists")
    public ResponseEntity<Checklist> updateChecklist(@Valid @RequestBody Checklist checklist) throws URISyntaxException {
        log.debug("REST request to update Checklist : {}", checklist);
        if (checklist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Checklist result = checklistService.save(checklist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checklist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /checklists} : get all the checklists.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checklists in body.
     */
    @GetMapping("/checklists")
    public ResponseEntity<List<Checklist>> getAllChecklists(ChecklistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Checklists by criteria: {}", criteria);
        Page<Checklist> page = checklistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /checklists/count} : count all the checklists.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/checklists/count")
    public ResponseEntity<Long> countChecklists(ChecklistCriteria criteria) {
        log.debug("REST request to count Checklists by criteria: {}", criteria);
        return ResponseEntity.ok().body(checklistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /checklists/:id} : get the "id" checklist.
     *
     * @param id the id of the checklist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checklist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checklists/{id}")
    public ResponseEntity<Checklist> getChecklist(@PathVariable Long id) {
        log.debug("REST request to get Checklist : {}", id);
        Optional<Checklist> checklist = checklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checklist);
    }

    /**
     * {@code DELETE  /checklists/:id} : delete the "id" checklist.
     *
     * @param id the id of the checklist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/checklists/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        log.debug("REST request to delete Checklist : {}", id);
        checklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
