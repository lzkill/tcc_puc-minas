package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.BoletimInformativo;
import com.lzkill.sgq.service.BoletimInformativoService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.BoletimInformativoCriteria;
import com.lzkill.sgq.service.BoletimInformativoQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.BoletimInformativo}.
 */
@RestController
@RequestMapping("/api")
public class BoletimInformativoResource {

    private final Logger log = LoggerFactory.getLogger(BoletimInformativoResource.class);

    private static final String ENTITY_NAME = "sgqBoletimInformativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoletimInformativoService boletimInformativoService;

    private final BoletimInformativoQueryService boletimInformativoQueryService;

    public BoletimInformativoResource(BoletimInformativoService boletimInformativoService, BoletimInformativoQueryService boletimInformativoQueryService) {
        this.boletimInformativoService = boletimInformativoService;
        this.boletimInformativoQueryService = boletimInformativoQueryService;
    }

    /**
     * {@code POST  /boletim-informativos} : Create a new boletimInformativo.
     *
     * @param boletimInformativo the boletimInformativo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boletimInformativo, or with status {@code 400 (Bad Request)} if the boletimInformativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boletim-informativos")
    public ResponseEntity<BoletimInformativo> createBoletimInformativo(@Valid @RequestBody BoletimInformativo boletimInformativo) throws URISyntaxException {
        log.debug("REST request to save BoletimInformativo : {}", boletimInformativo);
        if (boletimInformativo.getId() != null) {
            throw new BadRequestAlertException("A new boletimInformativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoletimInformativo result = boletimInformativoService.save(boletimInformativo);
        return ResponseEntity.created(new URI("/api/boletim-informativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boletim-informativos} : Updates an existing boletimInformativo.
     *
     * @param boletimInformativo the boletimInformativo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boletimInformativo,
     * or with status {@code 400 (Bad Request)} if the boletimInformativo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boletimInformativo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boletim-informativos")
    public ResponseEntity<BoletimInformativo> updateBoletimInformativo(@Valid @RequestBody BoletimInformativo boletimInformativo) throws URISyntaxException {
        log.debug("REST request to update BoletimInformativo : {}", boletimInformativo);
        if (boletimInformativo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoletimInformativo result = boletimInformativoService.save(boletimInformativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boletimInformativo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boletim-informativos} : get all the boletimInformativos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boletimInformativos in body.
     */
    @GetMapping("/boletim-informativos")
    public ResponseEntity<List<BoletimInformativo>> getAllBoletimInformativos(BoletimInformativoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BoletimInformativos by criteria: {}", criteria);
        Page<BoletimInformativo> page = boletimInformativoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /boletim-informativos/count} : count all the boletimInformativos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/boletim-informativos/count")
    public ResponseEntity<Long> countBoletimInformativos(BoletimInformativoCriteria criteria) {
        log.debug("REST request to count BoletimInformativos by criteria: {}", criteria);
        return ResponseEntity.ok().body(boletimInformativoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /boletim-informativos/:id} : get the "id" boletimInformativo.
     *
     * @param id the id of the boletimInformativo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boletimInformativo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boletim-informativos/{id}")
    public ResponseEntity<BoletimInformativo> getBoletimInformativo(@PathVariable Long id) {
        log.debug("REST request to get BoletimInformativo : {}", id);
        Optional<BoletimInformativo> boletimInformativo = boletimInformativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boletimInformativo);
    }

    /**
     * {@code DELETE  /boletim-informativos/:id} : delete the "id" boletimInformativo.
     *
     * @param id the id of the boletimInformativo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boletim-informativos/{id}")
    public ResponseEntity<Void> deleteBoletimInformativo(@PathVariable Long id) {
        log.debug("REST request to delete BoletimInformativo : {}", id);
        boletimInformativoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
