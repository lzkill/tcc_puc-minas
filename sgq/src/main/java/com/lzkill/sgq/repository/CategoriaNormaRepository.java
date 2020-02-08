package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.CategoriaNorma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaNorma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaNormaRepository extends JpaRepository<CategoriaNorma, Long>, JpaSpecificationExecutor<CategoriaNorma> {

}
