package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Norma;
import com.lzkill.sgq.service.NormaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.NormaCriteria;
import com.lzkill.sgq.service.NormaQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lzkill.sgq.domain.Norma}.
 */
@RestController
@RequestMapping("/api")
public class NormaResource {

    private final Logger log = LoggerFactory.getLogger(NormaResource.class);

    private final NormaService normaService;

    private final NormaQueryService normaQueryService;

    public NormaResource(NormaService normaService, NormaQueryService normaQueryService) {
        this.normaService = normaService;
        this.normaQueryService = normaQueryService;
    }

    /**
     * {@code GET  /normas} : get all the normas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normas in body.
     */
    @GetMapping("/normas")
    public ResponseEntity<List<Norma>> getAllNormas(NormaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Normas by criteria: {}", criteria);
        Page<Norma> page = normaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /normas/count} : count all the normas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/normas/count")
    public ResponseEntity<Long> countNormas(NormaCriteria criteria) {
        log.debug("REST request to count Normas by criteria: {}", criteria);
        return ResponseEntity.ok().body(normaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /normas/:id} : get the "id" norma.
     *
     * @param id the id of the norma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the norma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/normas/{id}")
    public ResponseEntity<Norma> getNorma(@PathVariable Long id) {
        log.debug("REST request to get Norma : {}", id);
        Optional<Norma> norma = normaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(norma);
    }
}
