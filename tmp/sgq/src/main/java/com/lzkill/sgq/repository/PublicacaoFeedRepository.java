package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.PublicacaoFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PublicacaoFeed entity.
 */
@Repository
public interface PublicacaoFeedRepository extends JpaRepository<PublicacaoFeed, Long>, JpaSpecificationExecutor<PublicacaoFeed> {

    @Query(value = "select distinct publicacaoFeed from PublicacaoFeed publicacaoFeed left join fetch publicacaoFeed.categorias left join fetch publicacaoFeed.anexos",
        countQuery = "select count(distinct publicacaoFeed) from PublicacaoFeed publicacaoFeed")
    Page<PublicacaoFeed> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct publicacaoFeed from PublicacaoFeed publicacaoFeed left join fetch publicacaoFeed.categorias left join fetch publicacaoFeed.anexos")
    List<PublicacaoFeed> findAllWithEagerRelationships();

    @Query("select publicacaoFeed from PublicacaoFeed publicacaoFeed left join fetch publicacaoFeed.categorias left join fetch publicacaoFeed.anexos where publicacaoFeed.id =:id")
    Optional<PublicacaoFeed> findOneWithEagerRelationships(@Param("id") Long id);

}
