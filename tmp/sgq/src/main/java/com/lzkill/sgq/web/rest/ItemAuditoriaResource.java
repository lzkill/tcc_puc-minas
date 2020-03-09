package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ItemAuditoria;
import com.lzkill.sgq.service.ItemAuditoriaService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ItemAuditoriaCriteria;
import com.lzkill.sgq.service.ItemAuditoriaQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ItemAuditoria}.
 */
@RestController
@RequestMapping("/api")
public class ItemAuditoriaResource {

    private final Logger log = LoggerFactory.getLogger(ItemAuditoriaResource.class);

    private static final String ENTITY_NAME = "sgqItemAuditoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemAuditoriaService itemAuditoriaService;

    private final ItemAuditoriaQueryService itemAuditoriaQueryService;

    public ItemAuditoriaResource(ItemAuditoriaService itemAuditoriaService, ItemAuditoriaQueryService itemAuditoriaQueryService) {
        this.itemAuditoriaService = itemAuditoriaService;
        this.itemAuditoriaQueryService = itemAuditoriaQueryService;
    }

    /**
     * {@code POST  /item-auditorias} : Create a new itemAuditoria.
     *
     * @param itemAuditoria the itemAuditoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemAuditoria, or with status {@code 400 (Bad Request)} if the itemAuditoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-auditorias")
    public ResponseEntity<ItemAuditoria> createItemAuditoria(@Valid @RequestBody ItemAuditoria itemAuditoria) throws URISyntaxException {
        log.debug("REST request to save ItemAuditoria : {}", itemAuditoria);
        if (itemAuditoria.getId() != null) {
            throw new BadRequestAlertException("A new itemAuditoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemAuditoria result = itemAuditoriaService.save(itemAuditoria);
        return ResponseEntity.created(new URI("/api/item-auditorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-auditorias} : Updates an existing itemAuditoria.
     *
     * @param itemAuditoria the itemAuditoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemAuditoria,
     * or with status {@code 400 (Bad Request)} if the itemAuditoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemAuditoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-auditorias")
    public ResponseEntity<ItemAuditoria> updateItemAuditoria(@Valid @RequestBody ItemAuditoria itemAuditoria) throws URISyntaxException {
        log.debug("REST request to update ItemAuditoria : {}", itemAuditoria);
        if (itemAuditoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemAuditoria result = itemAuditoriaService.save(itemAuditoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemAuditoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-auditorias} : get all the itemAuditorias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemAuditorias in body.
     */
    @GetMapping("/item-auditorias")
    public ResponseEntity<List<ItemAuditoria>> getAllItemAuditorias(ItemAuditoriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemAuditorias by criteria: {}", criteria);
        Page<ItemAuditoria> page = itemAuditoriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /item-auditorias/count} : count all the itemAuditorias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/item-auditorias/count")
    public ResponseEntity<Long> countItemAuditorias(ItemAuditoriaCriteria criteria) {
        log.debug("REST request to count ItemAuditorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemAuditoriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-auditorias/:id} : get the "id" itemAuditoria.
     *
     * @param id the id of the itemAuditoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemAuditoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-auditorias/{id}")
    public ResponseEntity<ItemAuditoria> getItemAuditoria(@PathVariable Long id) {
        log.debug("REST request to get ItemAuditoria : {}", id);
        Optional<ItemAuditoria> itemAuditoria = itemAuditoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemAuditoria);
    }

    /**
     * {@code DELETE  /item-auditorias/:id} : delete the "id" itemAuditoria.
     *
     * @param id the id of the itemAuditoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-auditorias/{id}")
    public ResponseEntity<Void> deleteItemAuditoria(@PathVariable Long id) {
        log.debug("REST request to delete ItemAuditoria : {}", id);
        itemAuditoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
