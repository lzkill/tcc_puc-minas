package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ResultadoAuditoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultadoAuditoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoAuditoriaRepository extends JpaRepository<ResultadoAuditoria, Long>, JpaSpecificationExecutor<ResultadoAuditoria> {

}
