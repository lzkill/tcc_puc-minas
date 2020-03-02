package com.lzkill.sgq.cron;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;
import com.lzkill.sgq.service.SolicitacaoAnaliseQueryService;
import com.lzkill.sgq.service.dto.SolicitacaoAnaliseCriteria;

import reactor.core.publisher.Flux;

@Component
public class ConsultoriaIntegration {

	private final Logger log = LoggerFactory.getLogger(ConsultoriaIntegration.class);

	private final SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService;
	private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;

	@Autowired
	public ConsultoriaIntegration(SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService,
			SolicitacaoAnaliseRepository solicitacaoAnaliseRepository) {
		this.solicitacaoAnaliseQueryService = solicitacaoAnaliseQueryService;
		this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
	}

	@Scheduled(cron = "${application.cron.sync-solicitacao-analise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado();
		querySolicitacoesWithStatusPendente();
	}

	private void sendSolicitacoesWithStatusRegistrado() {
		SolicitacaoAnaliseCriteria queryCriteria = getStatusRegistradoCriteria();
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseQueryService.findByCriteria(queryCriteria);

		log.debug("send SolicitacaoAnalise to consultoria : {}", solicitacoes.size());

		solicitacoes.forEach(s -> {
			Consultoria consultoria = s.getConsultoria();
			if (consultoria.isHabilitado()) {
				s.setId(null);

				Flux<SolicitacaoAnalise> flux = WebClient.create(consultoria.getUrlIntegracao()).post()
						.uri("/api/solicitacao-analises/").body(BodyInserters.fromObject(s)).retrieve()
						.bodyToFlux(SolicitacaoAnalise.class);

				flux.subscribe(r -> {
					log.debug("SolicitacaoAnalise received by consultoria : {}", r.getId());
					s.setStatus(r.getStatus());
					s.setDataSolicitacao(r.getDataSolicitacao());
					solicitacaoAnaliseRepository.save(s);
				});
			}
		});
	}

	private SolicitacaoAnaliseCriteria getStatusRegistradoCriteria() {
		SolicitacaoAnaliseCriteria queryCriteria = new SolicitacaoAnaliseCriteria();
		SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter statusFilter = new SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter();
		statusFilter.setEquals(StatusSolicitacaoAnalise.REGISTRADO);
		queryCriteria.setStatus(statusFilter);
		return queryCriteria;
	}

	private void querySolicitacoesWithStatusPendente() {
		SolicitacaoAnaliseCriteria queryCriteria = getStatusPendenteCriteria();
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseQueryService.findByCriteria(queryCriteria);

		log.debug("query SolicitacaoAnalise on consultoria : {}", solicitacoes.size());

		solicitacoes.forEach(s -> {
			Consultoria consultoria = s.getConsultoria();
			if (consultoria.isHabilitado()) {
				Flux<SolicitacaoAnalise> flux = WebClient.create(consultoria.getUrlIntegracao()).get()
						.uri("/api/solicitacao-analises/").attribute("id", s.getId()).retrieve()
						.bodyToFlux(SolicitacaoAnalise.class);

				flux.subscribe(r -> {
					if (r.getStatus() == StatusSolicitacaoAnalise.CONCLUIDO) {
						log.debug("SolicitacaoAnalise reviewed by consultoria : {}", r.getId());
						s.setStatus(r.getStatus());
						s.setAnaliseConsultoria(r.getAnaliseConsultoria());
						solicitacaoAnaliseRepository.save(s);
					}
				});
			}
		});
	}

	private SolicitacaoAnaliseCriteria getStatusPendenteCriteria() {
		SolicitacaoAnaliseCriteria queryCriteria = new SolicitacaoAnaliseCriteria();
		SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter statusFilter = new SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter();
		statusFilter.setEquals(StatusSolicitacaoAnalise.PENDENTE);
		queryCriteria.setStatus(statusFilter);
		return queryCriteria;
	}

}
