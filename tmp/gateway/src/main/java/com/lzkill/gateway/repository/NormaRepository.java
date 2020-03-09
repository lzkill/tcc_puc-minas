package com.lzkill.gateway.repository;

import com.lzkill.gateway.domain.Norma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Norma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaRepository extends JpaRepository<Norma, Long>, JpaSpecificationExecutor<Norma> {

}
