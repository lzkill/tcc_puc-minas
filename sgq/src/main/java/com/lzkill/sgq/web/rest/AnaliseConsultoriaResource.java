package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.service.AnaliseConsultoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.AnaliseConsultoriaCriteria;
import com.lzkill.sgq.service.AnaliseConsultoriaQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.lzkill.sgq.domain.AnaliseConsultoria}.
 */
@RestController
@RequestMapping("/api")
public class AnaliseConsultoriaResource {

    private final Logger log = LoggerFactory.getLogger(AnaliseConsultoriaResource.class);

    private static final String ENTITY_NAME = "sgqAnaliseConsultoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnaliseConsultoriaService analiseConsultoriaService;

    private final AnaliseConsultoriaQueryService analiseConsultoriaQueryService;

    public AnaliseConsultoriaResource(AnaliseConsultoriaService analiseConsultoriaService, AnaliseConsultoriaQueryService analiseConsultoriaQueryService) {
        this.analiseConsultoriaService = analiseConsultoriaService;
        this.analiseConsultoriaQueryService = analiseConsultoriaQueryService;
    }

    /**
     * {@code POST  /analise-consultorias} : Create a new analiseConsultoria.
     *
     * @param analiseConsultoria the analiseConsultoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analiseConsultoria, or with status {@code 400 (Bad Request)} if the analiseConsultoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analise-consultorias")
    public ResponseEntity<AnaliseConsultoria> createAnaliseConsultoria(@Valid @RequestBody AnaliseConsultoria analiseConsultoria) throws URISyntaxException {
        log.debug("REST request to save AnaliseConsultoria : {}", analiseConsultoria);
        if (analiseConsultoria.getId() != null) {
            throw new BadRequestAlertException("A new analiseConsultoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnaliseConsultoria result = analiseConsultoriaService.save(analiseConsultoria);
        return ResponseEntity.created(new URI("/api/analise-consultorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analise-consultorias} : Updates an existing analiseConsultoria.
     *
     * @param analiseConsultoria the analiseConsultoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analiseConsultoria,
     * or with status {@code 400 (Bad Request)} if the analiseConsultoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analiseConsultoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analise-consultorias")
    public ResponseEntity<AnaliseConsultoria> updateAnaliseConsultoria(@Valid @RequestBody AnaliseConsultoria analiseConsultoria) throws URISyntaxException {
        log.debug("REST request to update AnaliseConsultoria : {}", analiseConsultoria);
        if (analiseConsultoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnaliseConsultoria result = analiseConsultoriaService.save(analiseConsultoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analiseConsultoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /analise-consultorias} : get all the analiseConsultorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analiseConsultorias in body.
     */
    @GetMapping("/analise-consultorias")
    public ResponseEntity<List<AnaliseConsultoria>> getAllAnaliseConsultorias(AnaliseConsultoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnaliseConsultorias by criteria: {}", criteria);
        Page<AnaliseConsultoria> page = analiseConsultoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /analise-consultorias/count} : count all the analiseConsultorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/analise-consultorias/count")
    public ResponseEntity<Long> countAnaliseConsultorias(AnaliseConsultoriaCriteria criteria) {
        log.debug("REST request to count AnaliseConsultorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(analiseConsultoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /analise-consultorias/:id} : get the "id" analiseConsultoria.
     *
     * @param id the id of the analiseConsultoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analiseConsultoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analise-consultorias/{id}")
    public ResponseEntity<AnaliseConsultoria> getAnaliseConsultoria(@PathVariable Long id) {
        log.debug("REST request to get AnaliseConsultoria : {}", id);
        Optional<AnaliseConsultoria> analiseConsultoria = analiseConsultoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analiseConsultoria);
    }

    /**
     * {@code DELETE  /analise-consultorias/:id} : delete the "id" analiseConsultoria.
     *
     * @param id the id of the analiseConsultoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analise-consultorias/{id}")
    public ResponseEntity<Void> deleteAnaliseConsultoria(@PathVariable Long id) {
        log.debug("REST request to delete AnaliseConsultoria : {}", id);
        analiseConsultoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
