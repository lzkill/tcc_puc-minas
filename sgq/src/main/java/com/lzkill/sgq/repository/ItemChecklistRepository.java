package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ItemChecklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemChecklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemChecklistRepository extends JpaRepository<ItemChecklist, Long>, JpaSpecificationExecutor<ItemChecklist> {

}
