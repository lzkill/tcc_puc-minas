package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.AnaliseConsultoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnaliseConsultoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnaliseConsultoriaRepository extends JpaRepository<AnaliseConsultoria, Long>, JpaSpecificationExecutor<AnaliseConsultoria> {

}
