package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.CategoriaPublicacao;
import com.lzkill.sgq.service.CategoriaPublicacaoService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.CategoriaPublicacaoCriteria;
import com.lzkill.sgq.service.CategoriaPublicacaoQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.CategoriaPublicacao}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaPublicacaoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaPublicacaoResource.class);

    private static final String ENTITY_NAME = "sgqCategoriaPublicacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaPublicacaoService categoriaPublicacaoService;

    private final CategoriaPublicacaoQueryService categoriaPublicacaoQueryService;

    public CategoriaPublicacaoResource(CategoriaPublicacaoService categoriaPublicacaoService, CategoriaPublicacaoQueryService categoriaPublicacaoQueryService) {
        this.categoriaPublicacaoService = categoriaPublicacaoService;
        this.categoriaPublicacaoQueryService = categoriaPublicacaoQueryService;
    }

    /**
     * {@code POST  /categoria-publicacaos} : Create a new categoriaPublicacao.
     *
     * @param categoriaPublicacao the categoriaPublicacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaPublicacao, or with status {@code 400 (Bad Request)} if the categoriaPublicacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-publicacaos")
    public ResponseEntity<CategoriaPublicacao> createCategoriaPublicacao(@Valid @RequestBody CategoriaPublicacao categoriaPublicacao) throws URISyntaxException {
        log.debug("REST request to save CategoriaPublicacao : {}", categoriaPublicacao);
        if (categoriaPublicacao.getId() != null) {
            throw new BadRequestAlertException("A new categoriaPublicacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaPublicacao result = categoriaPublicacaoService.save(categoriaPublicacao);
        return ResponseEntity.created(new URI("/api/categoria-publicacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-publicacaos} : Updates an existing categoriaPublicacao.
     *
     * @param categoriaPublicacao the categoriaPublicacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaPublicacao,
     * or with status {@code 400 (Bad Request)} if the categoriaPublicacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaPublicacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-publicacaos")
    public ResponseEntity<CategoriaPublicacao> updateCategoriaPublicacao(@Valid @RequestBody CategoriaPublicacao categoriaPublicacao) throws URISyntaxException {
        log.debug("REST request to update CategoriaPublicacao : {}", categoriaPublicacao);
        if (categoriaPublicacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaPublicacao result = categoriaPublicacaoService.save(categoriaPublicacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoriaPublicacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-publicacaos} : get all the categoriaPublicacaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaPublicacaos in body.
     */
    @GetMapping("/categoria-publicacaos")
    public ResponseEntity<List<CategoriaPublicacao>> getAllCategoriaPublicacaos(CategoriaPublicacaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategoriaPublicacaos by criteria: {}", criteria);
        Page<CategoriaPublicacao> page = categoriaPublicacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /categoria-publicacaos/count} : count all the categoriaPublicacaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/categoria-publicacaos/count")
    public ResponseEntity<Long> countCategoriaPublicacaos(CategoriaPublicacaoCriteria criteria) {
        log.debug("REST request to count CategoriaPublicacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaPublicacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-publicacaos/:id} : get the "id" categoriaPublicacao.
     *
     * @param id the id of the categoriaPublicacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaPublicacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-publicacaos/{id}")
    public ResponseEntity<CategoriaPublicacao> getCategoriaPublicacao(@PathVariable Long id) {
        log.debug("REST request to get CategoriaPublicacao : {}", id);
        Optional<CategoriaPublicacao> categoriaPublicacao = categoriaPublicacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaPublicacao);
    }

    /**
     * {@code DELETE  /categoria-publicacaos/:id} : delete the "id" categoriaPublicacao.
     *
     * @param id the id of the categoriaPublicacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-publicacaos/{id}")
    public ResponseEntity<Void> deleteCategoriaPublicacao(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaPublicacao : {}", id);
        categoriaPublicacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
