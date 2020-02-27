package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ItemAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ItemAuditoria entity.
 */
@Repository
public interface ItemAuditoriaRepository extends JpaRepository<ItemAuditoria, Long>, JpaSpecificationExecutor<ItemAuditoria> {

    @Query(value = "select distinct itemAuditoria from ItemAuditoria itemAuditoria left join fetch itemAuditoria.anexos",
        countQuery = "select count(distinct itemAuditoria) from ItemAuditoria itemAuditoria")
    Page<ItemAuditoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct itemAuditoria from ItemAuditoria itemAuditoria left join fetch itemAuditoria.anexos")
    List<ItemAuditoria> findAllWithEagerRelationships();

    @Query("select itemAuditoria from ItemAuditoria itemAuditoria left join fetch itemAuditoria.anexos where itemAuditoria.id =:id")
    Optional<ItemAuditoria> findOneWithEagerRelationships(@Param("id") Long id);

}
