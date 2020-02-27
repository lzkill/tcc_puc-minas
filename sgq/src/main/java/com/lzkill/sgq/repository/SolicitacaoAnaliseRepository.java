package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.SolicitacaoAnalise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolicitacaoAnalise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitacaoAnaliseRepository extends JpaRepository<SolicitacaoAnalise, Long>, JpaSpecificationExecutor<SolicitacaoAnalise> {

}
