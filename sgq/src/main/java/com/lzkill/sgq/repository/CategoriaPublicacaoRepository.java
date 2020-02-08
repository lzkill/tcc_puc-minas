package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.CategoriaPublicacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaPublicacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaPublicacaoRepository extends JpaRepository<CategoriaPublicacao, Long>, JpaSpecificationExecutor<CategoriaPublicacao> {

}
