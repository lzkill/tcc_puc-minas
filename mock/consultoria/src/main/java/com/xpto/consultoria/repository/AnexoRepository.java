package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.Anexo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Anexo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long>, JpaSpecificationExecutor<Anexo> {

}
