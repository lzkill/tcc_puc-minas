package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ItemChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ItemChecklist entity.
 */
@Repository
public interface ItemChecklistRepository extends JpaRepository<ItemChecklist, Long>, JpaSpecificationExecutor<ItemChecklist> {

    @Query(value = "select distinct itemChecklist from ItemChecklist itemChecklist left join fetch itemChecklist.anexos",
        countQuery = "select count(distinct itemChecklist) from ItemChecklist itemChecklist")
    Page<ItemChecklist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct itemChecklist from ItemChecklist itemChecklist left join fetch itemChecklist.anexos")
    List<ItemChecklist> findAllWithEagerRelationships();

    @Query("select itemChecklist from ItemChecklist itemChecklist left join fetch itemChecklist.anexos where itemChecklist.id =:id")
    Optional<ItemChecklist> findOneWithEagerRelationships(@Param("id") Long id);

}
