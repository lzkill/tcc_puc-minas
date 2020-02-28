package com.xpto.consultoria.web.rest;

import com.xpto.consultoria.domain.NaoConformidade;
import com.xpto.consultoria.service.NaoConformidadeService;
import com.xpto.consultoria.web.rest.errors.BadRequestAlertException;
import com.xpto.consultoria.service.dto.NaoConformidadeCriteria;
import com.xpto.consultoria.service.NaoConformidadeQueryService;

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
 * REST controller for managing {@link com.xpto.consultoria.domain.NaoConformidade}.
 */
@RestController
@RequestMapping("/api")
public class NaoConformidadeResource {

    private final Logger log = LoggerFactory.getLogger(NaoConformidadeResource.class);

    private static final String ENTITY_NAME = "consultoriaNaoConformidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaoConformidadeService naoConformidadeService;

    private final NaoConformidadeQueryService naoConformidadeQueryService;

    public NaoConformidadeResource(NaoConformidadeService naoConformidadeService, NaoConformidadeQueryService naoConformidadeQueryService) {
        this.naoConformidadeService = naoConformidadeService;
        this.naoConformidadeQueryService = naoConformidadeQueryService;
    }

    /**
     * {@code POST  /nao-conformidades} : Create a new naoConformidade.
     *
     * @param naoConformidade the naoConformidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naoConformidade, or with status {@code 400 (Bad Request)} if the naoConformidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nao-conformidades")
    public ResponseEntity<NaoConformidade> createNaoConformidade(@Valid @RequestBody NaoConformidade naoConformidade) throws URISyntaxException {
        log.debug("REST request to save NaoConformidade : {}", naoConformidade);
        if (naoConformidade.getId() != null) {
            throw new BadRequestAlertException("A new naoConformidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NaoConformidade result = naoConformidadeService.save(naoConformidade);
        return ResponseEntity.created(new URI("/api/nao-conformidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nao-conformidades} : Updates an existing naoConformidade.
     *
     * @param naoConformidade the naoConformidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naoConformidade,
     * or with status {@code 400 (Bad Request)} if the naoConformidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naoConformidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nao-conformidades")
    public ResponseEntity<NaoConformidade> updateNaoConformidade(@Valid @RequestBody NaoConformidade naoConformidade) throws URISyntaxException {
        log.debug("REST request to update NaoConformidade : {}", naoConformidade);
        if (naoConformidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NaoConformidade result = naoConformidadeService.save(naoConformidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naoConformidade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nao-conformidades} : get all the naoConformidades.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naoConformidades in body.
     */
    @GetMapping("/nao-conformidades")
    public ResponseEntity<List<NaoConformidade>> getAllNaoConformidades(NaoConformidadeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NaoConformidades by criteria: {}", criteria);
        Page<NaoConformidade> page = naoConformidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /nao-conformidades/count} : count all the naoConformidades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/nao-conformidades/count")
    public ResponseEntity<Long> countNaoConformidades(NaoConformidadeCriteria criteria) {
        log.debug("REST request to count NaoConformidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(naoConformidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nao-conformidades/:id} : get the "id" naoConformidade.
     *
     * @param id the id of the naoConformidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naoConformidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nao-conformidades/{id}")
    public ResponseEntity<NaoConformidade> getNaoConformidade(@PathVariable Long id) {
        log.debug("REST request to get NaoConformidade : {}", id);
        Optional<NaoConformidade> naoConformidade = naoConformidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(naoConformidade);
    }

    /**
     * {@code DELETE  /nao-conformidades/:id} : delete the "id" naoConformidade.
     *
     * @param id the id of the naoConformidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nao-conformidades/{id}")
    public ResponseEntity<Void> deleteNaoConformidade(@PathVariable Long id) {
        log.debug("REST request to delete NaoConformidade : {}", id);
        naoConformidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
