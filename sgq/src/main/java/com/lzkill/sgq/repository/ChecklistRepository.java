package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Checklist entity.
 */
@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long>, JpaSpecificationExecutor<Checklist> {

    @Query(value = "select distinct checklist from Checklist checklist left join fetch checklist.anexos",
        countQuery = "select count(distinct checklist) from Checklist checklist")
    Page<Checklist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct checklist from Checklist checklist left join fetch checklist.anexos")
    List<Checklist> findAllWithEagerRelationships();

    @Query("select checklist from Checklist checklist left join fetch checklist.anexos where checklist.id =:id")
    Optional<Checklist> findOneWithEagerRelationships(@Param("id") Long id);

}
