package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.AcaoSGQ;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AcaoSGQ entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcaoSGQRepository extends JpaRepository<AcaoSGQ, Long>, JpaSpecificationExecutor<AcaoSGQ> {

}
