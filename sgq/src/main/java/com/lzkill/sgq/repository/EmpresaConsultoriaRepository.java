package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.EmpresaConsultoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmpresaConsultoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaConsultoriaRepository extends JpaRepository<EmpresaConsultoria, Long>, JpaSpecificationExecutor<EmpresaConsultoria> {

}
