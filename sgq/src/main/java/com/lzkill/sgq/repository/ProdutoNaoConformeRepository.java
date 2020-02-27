package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ProdutoNaoConforme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProdutoNaoConforme entity.
 */
@Repository
public interface ProdutoNaoConformeRepository extends JpaRepository<ProdutoNaoConforme, Long>, JpaSpecificationExecutor<ProdutoNaoConforme> {

    @Query(value = "select distinct produtoNaoConforme from ProdutoNaoConforme produtoNaoConforme left join fetch produtoNaoConforme.anexos",
        countQuery = "select count(distinct produtoNaoConforme) from ProdutoNaoConforme produtoNaoConforme")
    Page<ProdutoNaoConforme> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct produtoNaoConforme from ProdutoNaoConforme produtoNaoConforme left join fetch produtoNaoConforme.anexos")
    List<ProdutoNaoConforme> findAllWithEagerRelationships();

    @Query("select produtoNaoConforme from ProdutoNaoConforme produtoNaoConforme left join fetch produtoNaoConforme.anexos where produtoNaoConforme.id =:id")
    Optional<ProdutoNaoConforme> findOneWithEagerRelationships(@Param("id") Long id);

}
