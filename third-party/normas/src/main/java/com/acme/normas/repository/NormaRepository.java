package com.acme.normas.repository;

import com.acme.normas.domain.Norma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Norma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaRepository extends JpaRepository<Norma, Long>, JpaSpecificationExecutor<Norma> {

}
