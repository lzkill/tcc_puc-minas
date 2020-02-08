package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.EventoOperacional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventoOperacional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoOperacionalRepository extends JpaRepository<EventoOperacional, Long>, JpaSpecificationExecutor<EventoOperacional> {

}
