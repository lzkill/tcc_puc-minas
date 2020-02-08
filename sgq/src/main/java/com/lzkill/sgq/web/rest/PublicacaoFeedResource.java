package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.service.PublicacaoFeedService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.PublicacaoFeedCriteria;
import com.lzkill.sgq.service.PublicacaoFeedQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.PublicacaoFeed}.
 */
@RestController
@RequestMapping("/api")
public class PublicacaoFeedResource {

    private final Logger log = LoggerFactory.getLogger(PublicacaoFeedResource.class);

    private static final String ENTITY_NAME = "sgqPublicacaoFeed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicacaoFeedService publicacaoFeedService;

    private final PublicacaoFeedQueryService publicacaoFeedQueryService;

    public PublicacaoFeedResource(PublicacaoFeedService publicacaoFeedService, PublicacaoFeedQueryService publicacaoFeedQueryService) {
        this.publicacaoFeedService = publicacaoFeedService;
        this.publicacaoFeedQueryService = publicacaoFeedQueryService;
    }

    /**
     * {@code POST  /publicacao-feeds} : Create a new publicacaoFeed.
     *
     * @param publicacaoFeed the publicacaoFeed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicacaoFeed, or with status {@code 400 (Bad Request)} if the publicacaoFeed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publicacao-feeds")
    public ResponseEntity<PublicacaoFeed> createPublicacaoFeed(@Valid @RequestBody PublicacaoFeed publicacaoFeed) throws URISyntaxException {
        log.debug("REST request to save PublicacaoFeed : {}", publicacaoFeed);
        if (publicacaoFeed.getId() != null) {
            throw new BadRequestAlertException("A new publicacaoFeed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicacaoFeed result = publicacaoFeedService.save(publicacaoFeed);
        return ResponseEntity.created(new URI("/api/publicacao-feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publicacao-feeds} : Updates an existing publicacaoFeed.
     *
     * @param publicacaoFeed the publicacaoFeed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicacaoFeed,
     * or with status {@code 400 (Bad Request)} if the publicacaoFeed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicacaoFeed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publicacao-feeds")
    public ResponseEntity<PublicacaoFeed> updatePublicacaoFeed(@Valid @RequestBody PublicacaoFeed publicacaoFeed) throws URISyntaxException {
        log.debug("REST request to update PublicacaoFeed : {}", publicacaoFeed);
        if (publicacaoFeed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicacaoFeed result = publicacaoFeedService.save(publicacaoFeed);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publicacaoFeed.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /publicacao-feeds} : get all the publicacaoFeeds.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicacaoFeeds in body.
     */
    @GetMapping("/publicacao-feeds")
    public ResponseEntity<List<PublicacaoFeed>> getAllPublicacaoFeeds(PublicacaoFeedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PublicacaoFeeds by criteria: {}", criteria);
        Page<PublicacaoFeed> page = publicacaoFeedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /publicacao-feeds/count} : count all the publicacaoFeeds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/publicacao-feeds/count")
    public ResponseEntity<Long> countPublicacaoFeeds(PublicacaoFeedCriteria criteria) {
        log.debug("REST request to count PublicacaoFeeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(publicacaoFeedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /publicacao-feeds/:id} : get the "id" publicacaoFeed.
     *
     * @param id the id of the publicacaoFeed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicacaoFeed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publicacao-feeds/{id}")
    public ResponseEntity<PublicacaoFeed> getPublicacaoFeed(@PathVariable Long id) {
        log.debug("REST request to get PublicacaoFeed : {}", id);
        Optional<PublicacaoFeed> publicacaoFeed = publicacaoFeedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicacaoFeed);
    }

    /**
     * {@code DELETE  /publicacao-feeds/:id} : delete the "id" publicacaoFeed.
     *
     * @param id the id of the publicacaoFeed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publicacao-feeds/{id}")
    public ResponseEntity<Void> deletePublicacaoFeed(@PathVariable Long id) {
        log.debug("REST request to delete PublicacaoFeed : {}", id);
        publicacaoFeedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
