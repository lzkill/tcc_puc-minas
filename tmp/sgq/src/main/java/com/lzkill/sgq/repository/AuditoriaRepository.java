package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Auditoria entity.
 */
@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>, JpaSpecificationExecutor<Auditoria> {

    @Query(value = "select distinct auditoria from Auditoria auditoria left join fetch auditoria.itemAuditorias left join fetch auditoria.anexos",
        countQuery = "select count(distinct auditoria) from Auditoria auditoria")
    Page<Auditoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct auditoria from Auditoria auditoria left join fetch auditoria.itemAuditorias left join fetch auditoria.anexos")
    List<Auditoria> findAllWithEagerRelationships();

    @Query("select auditoria from Auditoria auditoria left join fetch auditoria.itemAuditorias left join fetch auditoria.anexos where auditoria.id =:id")
    Optional<Auditoria> findOneWithEagerRelationships(@Param("id") Long id);

}
