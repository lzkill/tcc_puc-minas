package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.Auditoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Auditoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>, JpaSpecificationExecutor<Auditoria> {

}
