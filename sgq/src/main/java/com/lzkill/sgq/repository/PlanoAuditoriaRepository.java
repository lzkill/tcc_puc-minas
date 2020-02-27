package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.PlanoAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PlanoAuditoria entity.
 */
@Repository
public interface PlanoAuditoriaRepository extends JpaRepository<PlanoAuditoria, Long>, JpaSpecificationExecutor<PlanoAuditoria> {

    @Query(value = "select distinct planoAuditoria from PlanoAuditoria planoAuditoria left join fetch planoAuditoria.anexos",
        countQuery = "select count(distinct planoAuditoria) from PlanoAuditoria planoAuditoria")
    Page<PlanoAuditoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct planoAuditoria from PlanoAuditoria planoAuditoria left join fetch planoAuditoria.anexos")
    List<PlanoAuditoria> findAllWithEagerRelationships();

    @Query("select planoAuditoria from PlanoAuditoria planoAuditoria left join fetch planoAuditoria.anexos where planoAuditoria.id =:id")
    Optional<PlanoAuditoria> findOneWithEagerRelationships(@Param("id") Long id);

}
