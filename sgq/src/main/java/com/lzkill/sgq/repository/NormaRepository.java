package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.Norma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Norma entity.
 */
@Repository
public interface NormaRepository extends JpaRepository<Norma, Long>, JpaSpecificationExecutor<Norma> {

    @Query(value = "select distinct norma from Norma norma left join fetch norma.categorias",
        countQuery = "select count(distinct norma) from Norma norma")
    Page<Norma> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct norma from Norma norma left join fetch norma.categorias")
    List<Norma> findAllWithEagerRelationships();

    @Query("select norma from Norma norma left join fetch norma.categorias where norma.id =:id")
    Optional<Norma> findOneWithEagerRelationships(@Param("id") Long id);

}
