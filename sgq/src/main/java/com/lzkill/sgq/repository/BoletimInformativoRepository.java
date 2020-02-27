package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.BoletimInformativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the BoletimInformativo entity.
 */
@Repository
public interface BoletimInformativoRepository extends JpaRepository<BoletimInformativo, Long>, JpaSpecificationExecutor<BoletimInformativo> {

    @Query(value = "select distinct boletimInformativo from BoletimInformativo boletimInformativo left join fetch boletimInformativo.categorias left join fetch boletimInformativo.anexos",
        countQuery = "select count(distinct boletimInformativo) from BoletimInformativo boletimInformativo")
    Page<BoletimInformativo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct boletimInformativo from BoletimInformativo boletimInformativo left join fetch boletimInformativo.categorias left join fetch boletimInformativo.anexos")
    List<BoletimInformativo> findAllWithEagerRelationships();

    @Query("select boletimInformativo from BoletimInformativo boletimInformativo left join fetch boletimInformativo.categorias left join fetch boletimInformativo.anexos where boletimInformativo.id =:id")
    Optional<BoletimInformativo> findOneWithEagerRelationships(@Param("id") Long id);

}
