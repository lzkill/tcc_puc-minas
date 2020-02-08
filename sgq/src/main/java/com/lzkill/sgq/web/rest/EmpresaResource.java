package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Empresa;
import com.lzkill.sgq.service.EmpresaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.EmpresaCriteria;
import com.lzkill.sgq.service.EmpresaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Empresa}.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "sgqEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaService empresaService;

    private final EmpresaQueryService empresaQueryService;

    public EmpresaResource(EmpresaService empresaService, EmpresaQueryService empresaQueryService) {
        this.empresaService = empresaService;
        this.empresaQueryService = empresaQueryService;
    }

    /**
     * {@code POST  /empresas} : Create a new empresa.
     *
     * @param empresa the empresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresa, or with status {@code 400 (Bad Request)} if the empresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empresas")
    public ResponseEntity<Empresa> createEmpresa(@Valid @RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresa);
        if (empresa.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empresa result = empresaService.save(empresa);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empresas} : Updates an existing empresa.
     *
     * @param empresa the empresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresa,
     * or with status {@code 400 (Bad Request)} if the empresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empresas")
    public ResponseEntity<Empresa> updateEmpresa(@Valid @RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresa);
        if (empresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Empresa result = empresaService.save(empresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empresa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> getAllEmpresas(EmpresaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Empresas by criteria: {}", criteria);
        Page<Empresa> page = empresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /empresas/count} : count all the empresas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/empresas/count")
    public ResponseEntity<Long> countEmpresas(EmpresaCriteria criteria) {
        log.debug("REST request to count Empresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(empresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresa.
     *
     * @param id the id of the empresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empresas/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<Empresa> empresa = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresa);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresa.
     *
     * @param id the id of the empresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
