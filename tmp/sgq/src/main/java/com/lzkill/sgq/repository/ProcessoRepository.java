package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.Processo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Processo entity.
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>, JpaSpecificationExecutor<Processo> {

    @Query(value = "select distinct processo from Processo processo left join fetch processo.anexos",
        countQuery = "select count(distinct processo) from Processo processo")
    Page<Processo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct processo from Processo processo left join fetch processo.anexos")
    List<Processo> findAllWithEagerRelationships();

    @Query("select processo from Processo processo left join fetch processo.anexos where processo.id =:id")
    Optional<Processo> findOneWithEagerRelationships(@Param("id") Long id);

}
