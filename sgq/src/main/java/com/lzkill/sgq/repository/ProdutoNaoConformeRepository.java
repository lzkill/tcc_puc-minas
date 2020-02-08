package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ProdutoNaoConforme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProdutoNaoConforme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoNaoConformeRepository extends JpaRepository<ProdutoNaoConforme, Long>, JpaSpecificationExecutor<ProdutoNaoConforme> {

}
