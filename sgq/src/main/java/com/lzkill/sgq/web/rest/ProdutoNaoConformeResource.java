package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ProdutoNaoConforme;
import com.lzkill.sgq.service.ProdutoNaoConformeService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ProdutoNaoConformeCriteria;
import com.lzkill.sgq.service.ProdutoNaoConformeQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ProdutoNaoConforme}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoNaoConformeResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoNaoConformeResource.class);

    private static final String ENTITY_NAME = "sgqProdutoNaoConforme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdutoNaoConformeService produtoNaoConformeService;

    private final ProdutoNaoConformeQueryService produtoNaoConformeQueryService;

    public ProdutoNaoConformeResource(ProdutoNaoConformeService produtoNaoConformeService, ProdutoNaoConformeQueryService produtoNaoConformeQueryService) {
        this.produtoNaoConformeService = produtoNaoConformeService;
        this.produtoNaoConformeQueryService = produtoNaoConformeQueryService;
    }

    /**
     * {@code POST  /produto-nao-conformes} : Create a new produtoNaoConforme.
     *
     * @param produtoNaoConforme the produtoNaoConforme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produtoNaoConforme, or with status {@code 400 (Bad Request)} if the produtoNaoConforme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produto-nao-conformes")
    public ResponseEntity<ProdutoNaoConforme> createProdutoNaoConforme(@Valid @RequestBody ProdutoNaoConforme produtoNaoConforme) throws URISyntaxException {
        log.debug("REST request to save ProdutoNaoConforme : {}", produtoNaoConforme);
        if (produtoNaoConforme.getId() != null) {
            throw new BadRequestAlertException("A new produtoNaoConforme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutoNaoConforme result = produtoNaoConformeService.save(produtoNaoConforme);
        return ResponseEntity.created(new URI("/api/produto-nao-conformes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produto-nao-conformes} : Updates an existing produtoNaoConforme.
     *
     * @param produtoNaoConforme the produtoNaoConforme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produtoNaoConforme,
     * or with status {@code 400 (Bad Request)} if the produtoNaoConforme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produtoNaoConforme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produto-nao-conformes")
    public ResponseEntity<ProdutoNaoConforme> updateProdutoNaoConforme(@Valid @RequestBody ProdutoNaoConforme produtoNaoConforme) throws URISyntaxException {
        log.debug("REST request to update ProdutoNaoConforme : {}", produtoNaoConforme);
        if (produtoNaoConforme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProdutoNaoConforme result = produtoNaoConformeService.save(produtoNaoConforme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produtoNaoConforme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produto-nao-conformes} : get all the produtoNaoConformes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtoNaoConformes in body.
     */
    @GetMapping("/produto-nao-conformes")
    public ResponseEntity<List<ProdutoNaoConforme>> getAllProdutoNaoConformes(ProdutoNaoConformeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProdutoNaoConformes by criteria: {}", criteria);
        Page<ProdutoNaoConforme> page = produtoNaoConformeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /produto-nao-conformes/count} : count all the produtoNaoConformes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/produto-nao-conformes/count")
    public ResponseEntity<Long> countProdutoNaoConformes(ProdutoNaoConformeCriteria criteria) {
        log.debug("REST request to count ProdutoNaoConformes by criteria: {}", criteria);
        return ResponseEntity.ok().body(produtoNaoConformeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produto-nao-conformes/:id} : get the "id" produtoNaoConforme.
     *
     * @param id the id of the produtoNaoConforme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produtoNaoConforme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produto-nao-conformes/{id}")
    public ResponseEntity<ProdutoNaoConforme> getProdutoNaoConforme(@PathVariable Long id) {
        log.debug("REST request to get ProdutoNaoConforme : {}", id);
        Optional<ProdutoNaoConforme> produtoNaoConforme = produtoNaoConformeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produtoNaoConforme);
    }

    /**
     * {@code DELETE  /produto-nao-conformes/:id} : delete the "id" produtoNaoConforme.
     *
     * @param id the id of the produtoNaoConforme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produto-nao-conformes/{id}")
    public ResponseEntity<Void> deleteProdutoNaoConforme(@PathVariable Long id) {
        log.debug("REST request to delete ProdutoNaoConforme : {}", id);
        produtoNaoConformeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
