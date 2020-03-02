package com.lzkill.sgq.cron;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
import com.lzkill.sgq.service.AnaliseConsultoriaService;
import com.lzkill.sgq.service.SolicitacaoAnaliseQueryService;
import com.lzkill.sgq.service.SolicitacaoAnaliseService;
import com.lzkill.sgq.service.dto.SolicitacaoAnaliseCriteria;

import reactor.core.publisher.Mono;

@Component
public class ConsultoriaIntegration {

	private final Logger log = LoggerFactory.getLogger(ConsultoriaIntegration.class);

	private final SolicitacaoAnaliseService solicitacaoAnaliseService;
	private final SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService;
	private final AnaliseConsultoriaService analiseConsultoriaService;

	@Autowired
	public ConsultoriaIntegration(SolicitacaoAnaliseService solicitacaoAnaliseService,
			SolicitacaoAnaliseQueryService solicitacaoAnaliseQueryService,
			AnaliseConsultoriaService analiseConsultoriaService) {
		this.solicitacaoAnaliseService = solicitacaoAnaliseService;
		this.solicitacaoAnaliseQueryService = solicitacaoAnaliseQueryService;
		this.analiseConsultoriaService = analiseConsultoriaService;
	}

	@Scheduled(cron = "${application.cron.sync-solicitacao-analise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado();
		updateSolicitacoesWithStatusPendente();
	}

	private void sendSolicitacoesWithStatusRegistrado() {
		SolicitacaoAnaliseCriteria queryCriteria = getStatusRegistradoCriteria();
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseQueryService.findByCriteria(queryCriteria);
		log.debug("send SolicitacaoAnalise to consultoria : {}", solicitacoes.size());
		solicitacoes.forEach(s -> sendSolicitacaoAnalise(s));
	}

	private void sendSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnalise) {
		Consultoria consultoria = solicitacaoAnalise.getConsultoria();
		if (consultoria.isHabilitado()) {
			// Como o mock de consultoria é um microserviço jhipster baseado na mesma
			// modelagem é preciso limpar os ids antes do post
			SolicitacaoAnalise deepClone = deepCloneWithNoIds(solicitacaoAnalise);

			Mono<SolicitacaoAnalise> mono = WebClient.builder().baseUrl(consultoria.getUrlIntegracao()).build().post()
					.uri("/api/solicitacao-analises/").body(BodyInserters.fromObject(deepClone)).retrieve()
					.bodyToMono(SolicitacaoAnalise.class);

			mono.subscribe(r -> {
				log.debug("SolicitacaoAnalise received by consultoria : {}", r.getId());
				solicitacaoAnalise.setStatus(r.getStatus());
				solicitacaoAnalise.setDataSolicitacao(r.getDataSolicitacao());
				solicitacaoAnalise.setIdAcompanhamento(r.getId());
				solicitacaoAnaliseService.save(solicitacaoAnalise);
			});
		}
	}

	private SolicitacaoAnaliseCriteria getStatusRegistradoCriteria() {
		SolicitacaoAnaliseCriteria queryCriteria = new SolicitacaoAnaliseCriteria();
		SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter statusFilter = new SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter();
		statusFilter.setEquals(StatusSolicitacaoAnalise.REGISTRADO);
		queryCriteria.setStatus(statusFilter);
		return queryCriteria;
	}

	// TODO Possivelmente há formas mais elegantes para essa operação
	private SolicitacaoAnalise deepCloneWithNoIds(SolicitacaoAnalise source) {
		SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise();

		solicitacaoAnalise.setIdUsuarioRegistro(source.getIdUsuarioRegistro());
		solicitacaoAnalise.setDataRegistro(source.getDataRegistro());
		solicitacaoAnalise.setDataSolicitacao(source.getDataSolicitacao());
		solicitacaoAnalise.setStatus(source.getStatus());

		NaoConformidade naoConformidade = new NaoConformidade();
		naoConformidade.setTitulo(source.getNaoConformidade().getTitulo());
		naoConformidade.setDescricao(source.getNaoConformidade().getDescricao());
		naoConformidade.setProcedente(source.getNaoConformidade().isProcedente());
		naoConformidade.setCausa(source.getNaoConformidade().getCausa());
		naoConformidade.setPrazoConclusao(source.getNaoConformidade().getPrazoConclusao());
		naoConformidade.setNovoPrazoConclusao(source.getNaoConformidade().getNovoPrazoConclusao());
		naoConformidade.setDataRegistro(source.getNaoConformidade().getDataRegistro());

		Set<AcaoSGQ> acoes = new HashSet<>();
		source.getNaoConformidade().getAcaoSGQS().forEach(a -> {
			AcaoSGQ acao = new AcaoSGQ();
			acao.setTipo(a.getTipo());
			acao.setTitulo(a.getTitulo());
			acao.setDescricao(a.getDescricao());
			acao.setPrazoConclusao(a.getPrazoConclusao());
			acao.setNovoPrazoConclusao(a.getNovoPrazoConclusao());
			acao.setDataRegistro(a.getDataRegistro());
			acoes.add(acao);
		});

		naoConformidade.setAcaoSGQS(acoes);
		solicitacaoAnalise.setNaoConformidade(naoConformidade);

		return solicitacaoAnalise;
	}

	private void updateSolicitacoesWithStatusPendente() {
		SolicitacaoAnaliseCriteria queryCriteria = getStatusPendenteCriteria();
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseQueryService.findByCriteria(queryCriteria);
		log.debug("query SolicitacaoAnalise on consultoria : {}", solicitacoes.size());
		solicitacoes.forEach(s -> updateSolicitacaoAnalise(s));
	}

	private void updateSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnalise) {
		Consultoria consultoria = solicitacaoAnalise.getConsultoria();
		if (consultoria.isHabilitado()) {
			Mono<SolicitacaoAnalise> mono = WebClient.builder().baseUrl(consultoria.getUrlIntegracao()).build().get()
					.uri("/api/solicitacao-analises/").attribute("id", solicitacaoAnalise.getIdAcompanhamento())
					.retrieve().bodyToMono(SolicitacaoAnalise.class);

			mono.subscribe(r -> {
				if (r.getStatus() == StatusSolicitacaoAnalise.CONCLUIDO) {
					log.debug("SolicitacaoAnalise reviewed by consultoria : {}", solicitacaoAnalise.getId());
					solicitacaoAnalise.setStatus(r.getStatus());

					AnaliseConsultoria analiseConsultoria = r.getAnaliseConsultoria();
					analiseConsultoria.setId(null);
					analiseConsultoriaService.save(analiseConsultoria);

					solicitacaoAnalise.setAnaliseConsultoria(analiseConsultoria);
					solicitacaoAnaliseService.save(solicitacaoAnalise);
				}
			});
		}
	}

	private SolicitacaoAnaliseCriteria getStatusPendenteCriteria() {
		SolicitacaoAnaliseCriteria queryCriteria = new SolicitacaoAnaliseCriteria();
		SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter statusFilter = new SolicitacaoAnaliseCriteria.StatusSolicitacaoAnaliseFilter();
		statusFilter.setEquals(StatusSolicitacaoAnalise.PENDENTE);
		queryCriteria.setStatus(statusFilter);
		return queryCriteria;
	}

}
