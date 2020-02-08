package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.EventoOperacional;
import com.lzkill.sgq.service.EventoOperacionalService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.EventoOperacionalCriteria;
import com.lzkill.sgq.service.EventoOperacionalQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.EventoOperacional}.
 */
@RestController
@RequestMapping("/api")
public class EventoOperacionalResource {

    private final Logger log = LoggerFactory.getLogger(EventoOperacionalResource.class);

    private static final String ENTITY_NAME = "sgqEventoOperacional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoOperacionalService eventoOperacionalService;

    private final EventoOperacionalQueryService eventoOperacionalQueryService;

    public EventoOperacionalResource(EventoOperacionalService eventoOperacionalService, EventoOperacionalQueryService eventoOperacionalQueryService) {
        this.eventoOperacionalService = eventoOperacionalService;
        this.eventoOperacionalQueryService = eventoOperacionalQueryService;
    }

    /**
     * {@code POST  /evento-operacionals} : Create a new eventoOperacional.
     *
     * @param eventoOperacional the eventoOperacional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventoOperacional, or with status {@code 400 (Bad Request)} if the eventoOperacional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evento-operacionals")
    public ResponseEntity<EventoOperacional> createEventoOperacional(@Valid @RequestBody EventoOperacional eventoOperacional) throws URISyntaxException {
        log.debug("REST request to save EventoOperacional : {}", eventoOperacional);
        if (eventoOperacional.getId() != null) {
            throw new BadRequestAlertException("A new eventoOperacional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventoOperacional result = eventoOperacionalService.save(eventoOperacional);
        return ResponseEntity.created(new URI("/api/evento-operacionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evento-operacionals} : Updates an existing eventoOperacional.
     *
     * @param eventoOperacional the eventoOperacional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventoOperacional,
     * or with status {@code 400 (Bad Request)} if the eventoOperacional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventoOperacional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evento-operacionals")
    public ResponseEntity<EventoOperacional> updateEventoOperacional(@Valid @RequestBody EventoOperacional eventoOperacional) throws URISyntaxException {
        log.debug("REST request to update EventoOperacional : {}", eventoOperacional);
        if (eventoOperacional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventoOperacional result = eventoOperacionalService.save(eventoOperacional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventoOperacional.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evento-operacionals} : get all the eventoOperacionals.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventoOperacionals in body.
     */
    @GetMapping("/evento-operacionals")
    public ResponseEntity<List<EventoOperacional>> getAllEventoOperacionals(EventoOperacionalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EventoOperacionals by criteria: {}", criteria);
        Page<EventoOperacional> page = eventoOperacionalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /evento-operacionals/count} : count all the eventoOperacionals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/evento-operacionals/count")
    public ResponseEntity<Long> countEventoOperacionals(EventoOperacionalCriteria criteria) {
        log.debug("REST request to count EventoOperacionals by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventoOperacionalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evento-operacionals/:id} : get the "id" eventoOperacional.
     *
     * @param id the id of the eventoOperacional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventoOperacional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evento-operacionals/{id}")
    public ResponseEntity<EventoOperacional> getEventoOperacional(@PathVariable Long id) {
        log.debug("REST request to get EventoOperacional : {}", id);
        Optional<EventoOperacional> eventoOperacional = eventoOperacionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoOperacional);
    }

    /**
     * {@code DELETE  /evento-operacionals/:id} : delete the "id" eventoOperacional.
     *
     * @param id the id of the eventoOperacional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evento-operacionals/{id}")
    public ResponseEntity<Void> deleteEventoOperacional(@PathVariable Long id) {
        log.debug("REST request to delete EventoOperacional : {}", id);
        eventoOperacionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
