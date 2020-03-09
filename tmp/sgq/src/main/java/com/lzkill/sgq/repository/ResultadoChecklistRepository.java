package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ResultadoChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ResultadoChecklist entity.
 */
@Repository
public interface ResultadoChecklistRepository extends JpaRepository<ResultadoChecklist, Long>, JpaSpecificationExecutor<ResultadoChecklist> {

    @Query(value = "select distinct resultadoChecklist from ResultadoChecklist resultadoChecklist left join fetch resultadoChecklist.anexos",
        countQuery = "select count(distinct resultadoChecklist) from ResultadoChecklist resultadoChecklist")
    Page<ResultadoChecklist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct resultadoChecklist from ResultadoChecklist resultadoChecklist left join fetch resultadoChecklist.anexos")
    List<ResultadoChecklist> findAllWithEagerRelationships();

    @Query("select resultadoChecklist from ResultadoChecklist resultadoChecklist left join fetch resultadoChecklist.anexos where resultadoChecklist.id =:id")
    Optional<ResultadoChecklist> findOneWithEagerRelationships(@Param("id") Long id);

}
