package com.lzkill.sgq.service;

import com.lzkill.sgq.domain.PublicacaoFeed;
import com.lzkill.sgq.repository.PublicacaoFeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PublicacaoFeed}.
 */
@Service
@Transactional
public class PublicacaoFeedService {

    private final Logger log = LoggerFactory.getLogger(PublicacaoFeedService.class);

    private final PublicacaoFeedRepository publicacaoFeedRepository;

    public PublicacaoFeedService(PublicacaoFeedRepository publicacaoFeedRepository) {
        this.publicacaoFeedRepository = publicacaoFeedRepository;
    }

    /**
     * Save a publicacaoFeed.
     *
     * @param publicacaoFeed the entity to save.
     * @return the persisted entity.
     */
    public PublicacaoFeed save(PublicacaoFeed publicacaoFeed) {
        log.debug("Request to save PublicacaoFeed : {}", publicacaoFeed);
        return publicacaoFeedRepository.save(publicacaoFeed);
    }

    /**
     * Get all the publicacaoFeeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicacaoFeed> findAll(Pageable pageable) {
        log.debug("Request to get all PublicacaoFeeds");
        return publicacaoFeedRepository.findAll(pageable);
    }

    /**
     * Get all the publicacaoFeeds with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PublicacaoFeed> findAllWithEagerRelationships(Pageable pageable) {
        return publicacaoFeedRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one publicacaoFeed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicacaoFeed> findOne(Long id) {
        log.debug("Request to get PublicacaoFeed : {}", id);
        return publicacaoFeedRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the publicacaoFeed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicacaoFeed : {}", id);
        publicacaoFeedRepository.deleteById(id);
    }
}
