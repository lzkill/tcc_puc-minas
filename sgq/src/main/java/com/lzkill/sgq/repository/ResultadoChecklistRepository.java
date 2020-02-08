package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ResultadoChecklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultadoChecklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoChecklistRepository extends JpaRepository<ResultadoChecklist, Long>, JpaSpecificationExecutor<ResultadoChecklist> {

}
