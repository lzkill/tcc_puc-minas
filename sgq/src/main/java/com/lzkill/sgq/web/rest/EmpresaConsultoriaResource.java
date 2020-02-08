package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.EmpresaConsultoria;
import com.lzkill.sgq.service.EmpresaConsultoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.EmpresaConsultoriaCriteria;
import com.lzkill.sgq.service.EmpresaConsultoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.EmpresaConsultoria}.
 */
@RestController
@RequestMapping("/api")
public class EmpresaConsultoriaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaConsultoriaResource.class);

    private static final String ENTITY_NAME = "sgqEmpresaConsultoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaConsultoriaService empresaConsultoriaService;

    private final EmpresaConsultoriaQueryService empresaConsultoriaQueryService;

    public EmpresaConsultoriaResource(EmpresaConsultoriaService empresaConsultoriaService, EmpresaConsultoriaQueryService empresaConsultoriaQueryService) {
        this.empresaConsultoriaService = empresaConsultoriaService;
        this.empresaConsultoriaQueryService = empresaConsultoriaQueryService;
    }

    /**
     * {@code POST  /empresa-consultorias} : Create a new empresaConsultoria.
     *
     * @param empresaConsultoria the empresaConsultoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresaConsultoria, or with status {@code 400 (Bad Request)} if the empresaConsultoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empresa-consultorias")
    public ResponseEntity<EmpresaConsultoria> createEmpresaConsultoria(@Valid @RequestBody EmpresaConsultoria empresaConsultoria) throws URISyntaxException {
        log.debug("REST request to save EmpresaConsultoria : {}", empresaConsultoria);
        if (empresaConsultoria.getId() != null) {
            throw new BadRequestAlertException("A new empresaConsultoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpresaConsultoria result = empresaConsultoriaService.save(empresaConsultoria);
        return ResponseEntity.created(new URI("/api/empresa-consultorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empresa-consultorias} : Updates an existing empresaConsultoria.
     *
     * @param empresaConsultoria the empresaConsultoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaConsultoria,
     * or with status {@code 400 (Bad Request)} if the empresaConsultoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresaConsultoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empresa-consultorias")
    public ResponseEntity<EmpresaConsultoria> updateEmpresaConsultoria(@Valid @RequestBody EmpresaConsultoria empresaConsultoria) throws URISyntaxException {
        log.debug("REST request to update EmpresaConsultoria : {}", empresaConsultoria);
        if (empresaConsultoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpresaConsultoria result = empresaConsultoriaService.save(empresaConsultoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empresaConsultoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empresa-consultorias} : get all the empresaConsultorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresaConsultorias in body.
     */
    @GetMapping("/empresa-consultorias")
    public ResponseEntity<List<EmpresaConsultoria>> getAllEmpresaConsultorias(EmpresaConsultoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmpresaConsultorias by criteria: {}", criteria);
        Page<EmpresaConsultoria> page = empresaConsultoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /empresa-consultorias/count} : count all the empresaConsultorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/empresa-consultorias/count")
    public ResponseEntity<Long> countEmpresaConsultorias(EmpresaConsultoriaCriteria criteria) {
        log.debug("REST request to count EmpresaConsultorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(empresaConsultoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /empresa-consultorias/:id} : get the "id" empresaConsultoria.
     *
     * @param id the id of the empresaConsultoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresaConsultoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empresa-consultorias/{id}")
    public ResponseEntity<EmpresaConsultoria> getEmpresaConsultoria(@PathVariable Long id) {
        log.debug("REST request to get EmpresaConsultoria : {}", id);
        Optional<EmpresaConsultoria> empresaConsultoria = empresaConsultoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaConsultoria);
    }

    /**
     * {@code DELETE  /empresa-consultorias/:id} : delete the "id" empresaConsultoria.
     *
     * @param id the id of the empresaConsultoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empresa-consultorias/{id}")
    public ResponseEntity<Void> deleteEmpresaConsultoria(@PathVariable Long id) {
        log.debug("REST request to delete EmpresaConsultoria : {}", id);
        empresaConsultoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
