package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ResultadoItemChecklist;
import com.lzkill.sgq.service.ResultadoItemChecklistService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ResultadoItemChecklistCriteria;
import com.lzkill.sgq.service.ResultadoItemChecklistQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ResultadoItemChecklist}.
 */
@RestController
@RequestMapping("/api")
public class ResultadoItemChecklistResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoItemChecklistResource.class);

    private static final String ENTITY_NAME = "sgqResultadoItemChecklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadoItemChecklistService resultadoItemChecklistService;

    private final ResultadoItemChecklistQueryService resultadoItemChecklistQueryService;

    public ResultadoItemChecklistResource(ResultadoItemChecklistService resultadoItemChecklistService, ResultadoItemChecklistQueryService resultadoItemChecklistQueryService) {
        this.resultadoItemChecklistService = resultadoItemChecklistService;
        this.resultadoItemChecklistQueryService = resultadoItemChecklistQueryService;
    }

    /**
     * {@code POST  /resultado-item-checklists} : Create a new resultadoItemChecklist.
     *
     * @param resultadoItemChecklist the resultadoItemChecklist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadoItemChecklist, or with status {@code 400 (Bad Request)} if the resultadoItemChecklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultado-item-checklists")
    public ResponseEntity<ResultadoItemChecklist> createResultadoItemChecklist(@Valid @RequestBody ResultadoItemChecklist resultadoItemChecklist) throws URISyntaxException {
        log.debug("REST request to save ResultadoItemChecklist : {}", resultadoItemChecklist);
        if (resultadoItemChecklist.getId() != null) {
            throw new BadRequestAlertException("A new resultadoItemChecklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadoItemChecklist result = resultadoItemChecklistService.save(resultadoItemChecklist);
        return ResponseEntity.created(new URI("/api/resultado-item-checklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultado-item-checklists} : Updates an existing resultadoItemChecklist.
     *
     * @param resultadoItemChecklist the resultadoItemChecklist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoItemChecklist,
     * or with status {@code 400 (Bad Request)} if the resultadoItemChecklist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadoItemChecklist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultado-item-checklists")
    public ResponseEntity<ResultadoItemChecklist> updateResultadoItemChecklist(@Valid @RequestBody ResultadoItemChecklist resultadoItemChecklist) throws URISyntaxException {
        log.debug("REST request to update ResultadoItemChecklist : {}", resultadoItemChecklist);
        if (resultadoItemChecklist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadoItemChecklist result = resultadoItemChecklistService.save(resultadoItemChecklist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultadoItemChecklist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultado-item-checklists} : get all the resultadoItemChecklists.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadoItemChecklists in body.
     */
    @GetMapping("/resultado-item-checklists")
    public ResponseEntity<List<ResultadoItemChecklist>> getAllResultadoItemChecklists(ResultadoItemChecklistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResultadoItemChecklists by criteria: {}", criteria);
        Page<ResultadoItemChecklist> page = resultadoItemChecklistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /resultado-item-checklists/count} : count all the resultadoItemChecklists.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/resultado-item-checklists/count")
    public ResponseEntity<Long> countResultadoItemChecklists(ResultadoItemChecklistCriteria criteria) {
        log.debug("REST request to count ResultadoItemChecklists by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultadoItemChecklistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resultado-item-checklists/:id} : get the "id" resultadoItemChecklist.
     *
     * @param id the id of the resultadoItemChecklist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadoItemChecklist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultado-item-checklists/{id}")
    public ResponseEntity<ResultadoItemChecklist> getResultadoItemChecklist(@PathVariable Long id) {
        log.debug("REST request to get ResultadoItemChecklist : {}", id);
        Optional<ResultadoItemChecklist> resultadoItemChecklist = resultadoItemChecklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultadoItemChecklist);
    }

    /**
     * {@code DELETE  /resultado-item-checklists/:id} : delete the "id" resultadoItemChecklist.
     *
     * @param id the id of the resultadoItemChecklist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultado-item-checklists/{id}")
    public ResponseEntity<Void> deleteResultadoItemChecklist(@PathVariable Long id) {
        log.debug("REST request to delete ResultadoItemChecklist : {}", id);
        resultadoItemChecklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
