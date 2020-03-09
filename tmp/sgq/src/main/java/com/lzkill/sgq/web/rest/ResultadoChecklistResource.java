package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ResultadoChecklist;
import com.lzkill.sgq.service.ResultadoChecklistService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ResultadoChecklistCriteria;
import com.lzkill.sgq.service.ResultadoChecklistQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ResultadoChecklist}.
 */
@RestController
@RequestMapping("/api")
public class ResultadoChecklistResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoChecklistResource.class);

    private static final String ENTITY_NAME = "sgqResultadoChecklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadoChecklistService resultadoChecklistService;

    private final ResultadoChecklistQueryService resultadoChecklistQueryService;

    public ResultadoChecklistResource(ResultadoChecklistService resultadoChecklistService, ResultadoChecklistQueryService resultadoChecklistQueryService) {
        this.resultadoChecklistService = resultadoChecklistService;
        this.resultadoChecklistQueryService = resultadoChecklistQueryService;
    }

    /**
     * {@code POST  /resultado-checklists} : Create a new resultadoChecklist.
     *
     * @param resultadoChecklist the resultadoChecklist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadoChecklist, or with status {@code 400 (Bad Request)} if the resultadoChecklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultado-checklists")
    public ResponseEntity<ResultadoChecklist> createResultadoChecklist(@Valid @RequestBody ResultadoChecklist resultadoChecklist) throws URISyntaxException {
        log.debug("REST request to save ResultadoChecklist : {}", resultadoChecklist);
        if (resultadoChecklist.getId() != null) {
            throw new BadRequestAlertException("A new resultadoChecklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadoChecklist result = resultadoChecklistService.save(resultadoChecklist);
        return ResponseEntity.created(new URI("/api/resultado-checklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultado-checklists} : Updates an existing resultadoChecklist.
     *
     * @param resultadoChecklist the resultadoChecklist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoChecklist,
     * or with status {@code 400 (Bad Request)} if the resultadoChecklist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadoChecklist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultado-checklists")
    public ResponseEntity<ResultadoChecklist> updateResultadoChecklist(@Valid @RequestBody ResultadoChecklist resultadoChecklist) throws URISyntaxException {
        log.debug("REST request to update ResultadoChecklist : {}", resultadoChecklist);
        if (resultadoChecklist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadoChecklist result = resultadoChecklistService.save(resultadoChecklist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultadoChecklist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultado-checklists} : get all the resultadoChecklists.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadoChecklists in body.
     */
    @GetMapping("/resultado-checklists")
    public ResponseEntity<List<ResultadoChecklist>> getAllResultadoChecklists(ResultadoChecklistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResultadoChecklists by criteria: {}", criteria);
        Page<ResultadoChecklist> page = resultadoChecklistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /resultado-checklists/count} : count all the resultadoChecklists.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/resultado-checklists/count")
    public ResponseEntity<Long> countResultadoChecklists(ResultadoChecklistCriteria criteria) {
        log.debug("REST request to count ResultadoChecklists by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultadoChecklistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resultado-checklists/:id} : get the "id" resultadoChecklist.
     *
     * @param id the id of the resultadoChecklist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadoChecklist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultado-checklists/{id}")
    public ResponseEntity<ResultadoChecklist> getResultadoChecklist(@PathVariable Long id) {
        log.debug("REST request to get ResultadoChecklist : {}", id);
        Optional<ResultadoChecklist> resultadoChecklist = resultadoChecklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultadoChecklist);
    }

    /**
     * {@code DELETE  /resultado-checklists/:id} : delete the "id" resultadoChecklist.
     *
     * @param id the id of the resultadoChecklist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultado-checklists/{id}")
    public ResponseEntity<Void> deleteResultadoChecklist(@PathVariable Long id) {
        log.debug("REST request to delete ResultadoChecklist : {}", id);
        resultadoChecklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
