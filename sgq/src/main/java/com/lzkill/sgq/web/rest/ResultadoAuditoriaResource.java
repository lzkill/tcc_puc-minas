package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ResultadoAuditoria;
import com.lzkill.sgq.service.ResultadoAuditoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ResultadoAuditoriaCriteria;
import com.lzkill.sgq.service.ResultadoAuditoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ResultadoAuditoria}.
 */
@RestController
@RequestMapping("/api")
public class ResultadoAuditoriaResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoAuditoriaResource.class);

    private static final String ENTITY_NAME = "sgqResultadoAuditoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadoAuditoriaService resultadoAuditoriaService;

    private final ResultadoAuditoriaQueryService resultadoAuditoriaQueryService;

    public ResultadoAuditoriaResource(ResultadoAuditoriaService resultadoAuditoriaService, ResultadoAuditoriaQueryService resultadoAuditoriaQueryService) {
        this.resultadoAuditoriaService = resultadoAuditoriaService;
        this.resultadoAuditoriaQueryService = resultadoAuditoriaQueryService;
    }

    /**
     * {@code POST  /resultado-auditorias} : Create a new resultadoAuditoria.
     *
     * @param resultadoAuditoria the resultadoAuditoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadoAuditoria, or with status {@code 400 (Bad Request)} if the resultadoAuditoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultado-auditorias")
    public ResponseEntity<ResultadoAuditoria> createResultadoAuditoria(@Valid @RequestBody ResultadoAuditoria resultadoAuditoria) throws URISyntaxException {
        log.debug("REST request to save ResultadoAuditoria : {}", resultadoAuditoria);
        if (resultadoAuditoria.getId() != null) {
            throw new BadRequestAlertException("A new resultadoAuditoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadoAuditoria result = resultadoAuditoriaService.save(resultadoAuditoria);
        return ResponseEntity.created(new URI("/api/resultado-auditorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultado-auditorias} : Updates an existing resultadoAuditoria.
     *
     * @param resultadoAuditoria the resultadoAuditoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoAuditoria,
     * or with status {@code 400 (Bad Request)} if the resultadoAuditoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadoAuditoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultado-auditorias")
    public ResponseEntity<ResultadoAuditoria> updateResultadoAuditoria(@Valid @RequestBody ResultadoAuditoria resultadoAuditoria) throws URISyntaxException {
        log.debug("REST request to update ResultadoAuditoria : {}", resultadoAuditoria);
        if (resultadoAuditoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadoAuditoria result = resultadoAuditoriaService.save(resultadoAuditoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultadoAuditoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultado-auditorias} : get all the resultadoAuditorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadoAuditorias in body.
     */
    @GetMapping("/resultado-auditorias")
    public ResponseEntity<List<ResultadoAuditoria>> getAllResultadoAuditorias(ResultadoAuditoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResultadoAuditorias by criteria: {}", criteria);
        Page<ResultadoAuditoria> page = resultadoAuditoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /resultado-auditorias/count} : count all the resultadoAuditorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/resultado-auditorias/count")
    public ResponseEntity<Long> countResultadoAuditorias(ResultadoAuditoriaCriteria criteria) {
        log.debug("REST request to count ResultadoAuditorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultadoAuditoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resultado-auditorias/:id} : get the "id" resultadoAuditoria.
     *
     * @param id the id of the resultadoAuditoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadoAuditoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultado-auditorias/{id}")
    public ResponseEntity<ResultadoAuditoria> getResultadoAuditoria(@PathVariable Long id) {
        log.debug("REST request to get ResultadoAuditoria : {}", id);
        Optional<ResultadoAuditoria> resultadoAuditoria = resultadoAuditoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultadoAuditoria);
    }

    /**
     * {@code DELETE  /resultado-auditorias/:id} : delete the "id" resultadoAuditoria.
     *
     * @param id the id of the resultadoAuditoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultado-auditorias/{id}")
    public ResponseEntity<Void> deleteResultadoAuditoria(@PathVariable Long id) {
        log.debug("REST request to delete ResultadoAuditoria : {}", id);
        resultadoAuditoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
