package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.CampanhaRecall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CampanhaRecall entity.
 */
@Repository
public interface CampanhaRecallRepository extends JpaRepository<CampanhaRecall, Long>, JpaSpecificationExecutor<CampanhaRecall> {

    @Query(value = "select distinct campanhaRecall from CampanhaRecall campanhaRecall left join fetch campanhaRecall.anexos",
        countQuery = "select count(distinct campanhaRecall) from CampanhaRecall campanhaRecall")
    Page<CampanhaRecall> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct campanhaRecall from CampanhaRecall campanhaRecall left join fetch campanhaRecall.anexos")
    List<CampanhaRecall> findAllWithEagerRelationships();

    @Query("select campanhaRecall from CampanhaRecall campanhaRecall left join fetch campanhaRecall.anexos where campanhaRecall.id =:id")
    Optional<CampanhaRecall> findOneWithEagerRelationships(@Param("id") Long id);

}
