package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.PublicoAlvo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PublicoAlvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicoAlvoRepository extends JpaRepository<PublicoAlvo, Long>, JpaSpecificationExecutor<PublicoAlvo> {

}
