package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ResultadoItemChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ResultadoItemChecklist entity.
 */
@Repository
public interface ResultadoItemChecklistRepository extends JpaRepository<ResultadoItemChecklist, Long>, JpaSpecificationExecutor<ResultadoItemChecklist> {

    @Query(value = "select distinct resultadoItemChecklist from ResultadoItemChecklist resultadoItemChecklist left join fetch resultadoItemChecklist.anexos",
        countQuery = "select count(distinct resultadoItemChecklist) from ResultadoItemChecklist resultadoItemChecklist")
    Page<ResultadoItemChecklist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct resultadoItemChecklist from ResultadoItemChecklist resultadoItemChecklist left join fetch resultadoItemChecklist.anexos")
    List<ResultadoItemChecklist> findAllWithEagerRelationships();

    @Query("select resultadoItemChecklist from ResultadoItemChecklist resultadoItemChecklist left join fetch resultadoItemChecklist.anexos where resultadoItemChecklist.id =:id")
    Optional<ResultadoItemChecklist> findOneWithEagerRelationships(@Param("id") Long id);

}
