package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ItemPlanoAuditoria entity.
 */
@Repository
public interface ItemPlanoAuditoriaRepository extends JpaRepository<ItemPlanoAuditoria, Long>, JpaSpecificationExecutor<ItemPlanoAuditoria> {

    @Query(value = "select distinct itemPlanoAuditoria from ItemPlanoAuditoria itemPlanoAuditoria left join fetch itemPlanoAuditoria.anexos",
        countQuery = "select count(distinct itemPlanoAuditoria) from ItemPlanoAuditoria itemPlanoAuditoria")
    Page<ItemPlanoAuditoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct itemPlanoAuditoria from ItemPlanoAuditoria itemPlanoAuditoria left join fetch itemPlanoAuditoria.anexos")
    List<ItemPlanoAuditoria> findAllWithEagerRelationships();

    @Query("select itemPlanoAuditoria from ItemPlanoAuditoria itemPlanoAuditoria left join fetch itemPlanoAuditoria.anexos where itemPlanoAuditoria.id =:id")
    Optional<ItemPlanoAuditoria> findOneWithEagerRelationships(@Param("id") Long id);

}
