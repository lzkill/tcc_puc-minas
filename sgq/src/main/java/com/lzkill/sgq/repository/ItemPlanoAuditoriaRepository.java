package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.ItemPlanoAuditoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPlanoAuditoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPlanoAuditoriaRepository extends JpaRepository<ItemPlanoAuditoria, Long>, JpaSpecificationExecutor<ItemPlanoAuditoria> {

}
