package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.NaoConformidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the NaoConformidade entity.
 */
@Repository
public interface NaoConformidadeRepository extends JpaRepository<NaoConformidade, Long>, JpaSpecificationExecutor<NaoConformidade> {

    @Query(value = "select distinct naoConformidade from NaoConformidade naoConformidade left join fetch naoConformidade.anexos",
        countQuery = "select count(distinct naoConformidade) from NaoConformidade naoConformidade")
    Page<NaoConformidade> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct naoConformidade from NaoConformidade naoConformidade left join fetch naoConformidade.anexos")
    List<NaoConformidade> findAllWithEagerRelationships();

    @Query("select naoConformidade from NaoConformidade naoConformidade left join fetch naoConformidade.anexos where naoConformidade.id =:id")
    Optional<NaoConformidade> findOneWithEagerRelationships(@Param("id") Long id);

}
