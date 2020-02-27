package com.xpto.consultoria.repository;

import com.xpto.consultoria.domain.NaoConformidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NaoConformidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaoConformidadeRepository extends JpaRepository<NaoConformidade, Long>, JpaSpecificationExecutor<NaoConformidade> {

}
