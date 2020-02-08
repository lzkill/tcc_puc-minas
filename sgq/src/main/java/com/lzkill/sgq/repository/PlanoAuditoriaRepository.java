package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.PlanoAuditoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoAuditoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoAuditoriaRepository extends JpaRepository<PlanoAuditoria, Long>, JpaSpecificationExecutor<PlanoAuditoria> {

}
