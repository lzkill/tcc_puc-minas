package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ResultadoItemChecklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultadoItemChecklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoItemChecklistRepository extends JpaRepository<ResultadoItemChecklist, Long>, JpaSpecificationExecutor<ResultadoItemChecklist> {

}
