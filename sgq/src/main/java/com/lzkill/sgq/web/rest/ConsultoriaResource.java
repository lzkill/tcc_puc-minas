package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.service.ConsultoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ConsultoriaCriteria;
import com.lzkill.sgq.service.ConsultoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Consultoria}.
 */
@RestController
@RequestMapping("/api")
public class ConsultoriaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultoriaResource.class);

    private static final String ENTITY_NAME = "sgqConsultoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultoriaService consultoriaService;

    private final ConsultoriaQueryService consultoriaQueryService;

    public ConsultoriaResource(ConsultoriaService consultoriaService, ConsultoriaQueryService consultoriaQueryService) {
        this.consultoriaService = consultoriaService;
        this.consultoriaQueryService = consultoriaQueryService;
    }

    /**
     * {@code POST  /consultorias} : Create a new consultoria.
     *
     * @param consultoria the consultoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultoria, or with status {@code 400 (Bad Request)} if the consultoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultorias")
    public ResponseEntity<Consultoria> createConsultoria(@Valid @RequestBody Consultoria consultoria) throws URISyntaxException {
        log.debug("REST request to save Consultoria : {}", consultoria);
        if (consultoria.getId() != null) {
            throw new BadRequestAlertException("A new consultoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consultoria result = consultoriaService.save(consultoria);
        return ResponseEntity.created(new URI("/api/consultorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consultorias} : Updates an existing consultoria.
     *
     * @param consultoria the consultoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultoria,
     * or with status {@code 400 (Bad Request)} if the consultoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultorias")
    public ResponseEntity<Consultoria> updateConsultoria(@Valid @RequestBody Consultoria consultoria) throws URISyntaxException {
        log.debug("REST request to update Consultoria : {}", consultoria);
        if (consultoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Consultoria result = consultoriaService.save(consultoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consultorias} : get all the consultorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultorias in body.
     */
    @GetMapping("/consultorias")
    public ResponseEntity<List<Consultoria>> getAllConsultorias(ConsultoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Consultorias by criteria: {}", criteria);
        Page<Consultoria> page = consultoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /consultorias/count} : count all the consultorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/consultorias/count")
    public ResponseEntity<Long> countConsultorias(ConsultoriaCriteria criteria) {
        log.debug("REST request to count Consultorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(consultoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consultorias/:id} : get the "id" consultoria.
     *
     * @param id the id of the consultoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultorias/{id}")
    public ResponseEntity<Consultoria> getConsultoria(@PathVariable Long id) {
        log.debug("REST request to get Consultoria : {}", id);
        Optional<Consultoria> consultoria = consultoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultoria);
    }

    /**
     * {@code DELETE  /consultorias/:id} : delete the "id" consultoria.
     *
     * @param id the id of the consultoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultorias/{id}")
    public ResponseEntity<Void> deleteConsultoria(@PathVariable Long id) {
        log.debug("REST request to delete Consultoria : {}", id);
        consultoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
