package com.lzkill.sgq.repository;

import com.lzkill.sgq.domain.EventoOperacional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EventoOperacional entity.
 */
@Repository
public interface EventoOperacionalRepository extends JpaRepository<EventoOperacional, Long>, JpaSpecificationExecutor<EventoOperacional> {

    @Query(value = "select distinct eventoOperacional from EventoOperacional eventoOperacional left join fetch eventoOperacional.anexos",
        countQuery = "select count(distinct eventoOperacional) from EventoOperacional eventoOperacional")
    Page<EventoOperacional> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct eventoOperacional from EventoOperacional eventoOperacional left join fetch eventoOperacional.anexos")
    List<EventoOperacional> findAllWithEagerRelationships();

    @Query("select eventoOperacional from EventoOperacional eventoOperacional left join fetch eventoOperacional.anexos where eventoOperacional.id =:id")
    Optional<EventoOperacional> findOneWithEagerRelationships(@Param("id") Long id);

}
