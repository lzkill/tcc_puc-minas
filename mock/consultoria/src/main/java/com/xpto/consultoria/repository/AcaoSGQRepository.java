package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.AcaoSGQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AcaoSGQ entity.
 */
@Repository
public interface AcaoSGQRepository extends JpaRepository<AcaoSGQ, Long>, JpaSpecificationExecutor<AcaoSGQ> {

    @Query(value = "select distinct acaoSGQ from AcaoSGQ acaoSGQ left join fetch acaoSGQ.anexos",
        countQuery = "select count(distinct acaoSGQ) from AcaoSGQ acaoSGQ")
    Page<AcaoSGQ> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct acaoSGQ from AcaoSGQ acaoSGQ left join fetch acaoSGQ.anexos")
    List<AcaoSGQ> findAllWithEagerRelationships();

    @Query("select acaoSGQ from AcaoSGQ acaoSGQ left join fetch acaoSGQ.anexos where acaoSGQ.id =:id")
    Optional<AcaoSGQ> findOneWithEagerRelationships(@Param("id") Long id);

}
