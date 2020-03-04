package com.lzkill.sgq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;

/**
 * Spring Data repository for the SolicitacaoAnalise entity.
 */
@Repository
public interface SolicitacaoAnaliseRepository
		extends JpaRepository<SolicitacaoAnalise, Long>, JpaSpecificationExecutor<SolicitacaoAnalise> {
	public List<SolicitacaoAnalise> findByStatus(StatusSolicitacaoAnalise status);
}
