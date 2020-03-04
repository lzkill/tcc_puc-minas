package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.AnaliseConsultoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AnaliseConsultoria entity.
 */
@Repository
public interface AnaliseConsultoriaRepository extends JpaRepository<AnaliseConsultoria, Long>, JpaSpecificationExecutor<AnaliseConsultoria> {

    @Query(value = "select distinct analiseConsultoria from AnaliseConsultoria analiseConsultoria left join fetch analiseConsultoria.anexos",
        countQuery = "select count(distinct analiseConsultoria) from AnaliseConsultoria analiseConsultoria")
    Page<AnaliseConsultoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct analiseConsultoria from AnaliseConsultoria analiseConsultoria left join fetch analiseConsultoria.anexos")
    List<AnaliseConsultoria> findAllWithEagerRelationships();

    @Query("select analiseConsultoria from AnaliseConsultoria analiseConsultoria left join fetch analiseConsultoria.anexos where analiseConsultoria.id =:id")
    Optional<AnaliseConsultoria> findOneWithEagerRelationships(@Param("id") Long id);

}
