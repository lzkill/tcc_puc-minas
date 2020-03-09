package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.Feed;
import com.lzkill.sgq.repository.FeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Feed}.
 */
@Service
@Transactional
public class FeedService {

    private final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    /**
     * Save a feed.
     *
     * @param feed the entity to save.
     * @return the persisted entity.
     */
    public Feed save(Feed feed) {
        log.debug("Request to save Feed : {}", feed);
        return feedRepository.save(feed);
    }

    /**
     * Get all the feeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Feed> findAll(Pageable pageable) {
        log.debug("Request to get all Feeds");
        return feedRepository.findAll(pageable);
    }


    /**
     * Get one feed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Feed> findOne(Long id) {
        log.debug("Request to get Feed : {}", id);
        return feedRepository.findById(id);
    }

    /**
     * Delete the feed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feed : {}", id);
        feedRepository.deleteById(id);
    }
}
