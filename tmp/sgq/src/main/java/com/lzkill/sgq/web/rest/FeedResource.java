package com.lzkill.sgq.web.rest;

import com.lzkill.sgq.domain.Feed;
import com.lzkill.sgq.service.FeedService;
import com.lzkill.sgq.web.rest.errors.BadRequestAlertException;
import com.lzkill.sgq.service.dto.FeedCriteria;
import com.lzkill.sgq.service.FeedQueryService;

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
 * REST controller for managing {@link com.lzkill.sgq.domain.Feed}.
 */
@RestController
@RequestMapping("/api")
public class FeedResource {

    private final Logger log = LoggerFactory.getLogger(FeedResource.class);

    private static final String ENTITY_NAME = "sgqFeed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedService feedService;

    private final FeedQueryService feedQueryService;

    public FeedResource(FeedService feedService, FeedQueryService feedQueryService) {
        this.feedService = feedService;
        this.feedQueryService = feedQueryService;
    }

    /**
     * {@code POST  /feeds} : Create a new feed.
     *
     * @param feed the feed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feed, or with status {@code 400 (Bad Request)} if the feed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feeds")
    public ResponseEntity<Feed> createFeed(@Valid @RequestBody Feed feed) throws URISyntaxException {
        log.debug("REST request to save Feed : {}", feed);
        if (feed.getId() != null) {
            throw new BadRequestAlertException("A new feed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feed result = feedService.save(feed);
        return ResponseEntity.created(new URI("/api/feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feeds} : Updates an existing feed.
     *
     * @param feed the feed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feed,
     * or with status {@code 400 (Bad Request)} if the feed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feeds")
    public ResponseEntity<Feed> updateFeed(@Valid @RequestBody Feed feed) throws URISyntaxException {
        log.debug("REST request to update Feed : {}", feed);
        if (feed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Feed result = feedService.save(feed);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feed.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feeds} : get all the feeds.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feeds in body.
     */
    @GetMapping("/feeds")
    public ResponseEntity<List<Feed>> getAllFeeds(FeedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Feeds by criteria: {}", criteria);
        Page<Feed> page = feedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /feeds/count} : count all the feeds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/feeds/count")
    public ResponseEntity<Long> countFeeds(FeedCriteria criteria) {
        log.debug("REST request to count Feeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feeds/:id} : get the "id" feed.
     *
     * @param id the id of the feed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feeds/{id}")
    public ResponseEntity<Feed> getFeed(@PathVariable Long id) {
        log.debug("REST request to get Feed : {}", id);
        Optional<Feed> feed = feedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feed);
    }

    /**
     * {@code DELETE  /feeds/:id} : delete the "id" feed.
     *
     * @param id the id of the feed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feeds/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable Long id) {
        log.debug("REST request to delete Feed : {}", id);
        feedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
