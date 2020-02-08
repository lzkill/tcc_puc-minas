package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.CampanhaRecall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CampanhaRecall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampanhaRecallRepository extends JpaRepository<CampanhaRecall, Long>, JpaSpecificationExecutor<CampanhaRecall> {

}
