package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.ItemChecklist;
import com.lzkill.sgq.service.ItemChecklistService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.ItemChecklistCriteria;
import com.lzkill.sgq.service.ItemChecklistQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.ItemChecklist}.
 */
@RestController
@RequestMapping("/api")
public class ItemChecklistResource {

    private final Logger log = LoggerFactory.getLogger(ItemChecklistResource.class);

    private static final String ENTITY_NAME = "sgqItemChecklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemChecklistService itemChecklistService;

    private final ItemChecklistQueryService itemChecklistQueryService;

    public ItemChecklistResource(ItemChecklistService itemChecklistService, ItemChecklistQueryService itemChecklistQueryService) {
        this.itemChecklistService = itemChecklistService;
        this.itemChecklistQueryService = itemChecklistQueryService;
    }

    /**
     * {@code POST  /item-checklists} : Create a new itemChecklist.
     *
     * @param itemChecklist the itemChecklist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemChecklist, or with status {@code 400 (Bad Request)} if the itemChecklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-checklists")
    public ResponseEntity<ItemChecklist> createItemChecklist(@Valid @RequestBody ItemChecklist itemChecklist) throws URISyntaxException {
        log.debug("REST request to save ItemChecklist : {}", itemChecklist);
        if (itemChecklist.getId() != null) {
            throw new BadRequestAlertException("A new itemChecklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemChecklist result = itemChecklistService.save(itemChecklist);
        return ResponseEntity.created(new URI("/api/item-checklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-checklists} : Updates an existing itemChecklist.
     *
     * @param itemChecklist the itemChecklist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemChecklist,
     * or with status {@code 400 (Bad Request)} if the itemChecklist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemChecklist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-checklists")
    public ResponseEntity<ItemChecklist> updateItemChecklist(@Valid @RequestBody ItemChecklist itemChecklist) throws URISyntaxException {
        log.debug("REST request to update ItemChecklist : {}", itemChecklist);
        if (itemChecklist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemChecklist result = itemChecklistService.save(itemChecklist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemChecklist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-checklists} : get all the itemChecklists.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemChecklists in body.
     */
    @GetMapping("/item-checklists")
    public ResponseEntity<List<ItemChecklist>> getAllItemChecklists(ItemChecklistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemChecklists by criteria: {}", criteria);
        Page<ItemChecklist> page = itemChecklistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /item-checklists/count} : count all the itemChecklists.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/item-checklists/count")
    public ResponseEntity<Long> countItemChecklists(ItemChecklistCriteria criteria) {
        log.debug("REST request to count ItemChecklists by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemChecklistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-checklists/:id} : get the "id" itemChecklist.
     *
     * @param id the id of the itemChecklist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemChecklist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-checklists/{id}")
    public ResponseEntity<ItemChecklist> getItemChecklist(@PathVariable Long id) {
        log.debug("REST request to get ItemChecklist : {}", id);
        Optional<ItemChecklist> itemChecklist = itemChecklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemChecklist);
    }

    /**
     * {@code DELETE  /item-checklists/:id} : delete the "id" itemChecklist.
     *
     * @param id the id of the itemChecklist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-checklists/{id}")
    public ResponseEntity<Void> deleteItemChecklist(@PathVariable Long id) {
        log.debug("REST request to delete ItemChecklist : {}", id);
        itemChecklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
