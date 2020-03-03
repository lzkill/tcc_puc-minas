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
import com.lzkill.sgq.repository.AnaliseConsultoriaRepository;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ConsultoriaReactiveIntegration {

	private final Logger log = LoggerFactory.getLogger(ConsultoriaReactiveIntegration.class);

	private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;
	private final AnaliseConsultoriaRepository analiseConsultoriaRepository;

	@Autowired
	public ConsultoriaReactiveIntegration(SolicitacaoAnaliseRepository solicitacaoAnaliseRepository,
			AnaliseConsultoriaRepository analiseConsultoriaRepository) {
		this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
		this.analiseConsultoriaRepository = analiseConsultoriaRepository;
	}

	@Scheduled(cron = "${application.cron.sync-solicitacao-analise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado().subscribe();
		querySolicitacoesWithStatusPendente().subscribe();
	}

	private Flux<SolicitacaoAnalise> sendSolicitacoesWithStatusRegistrado() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.REGISTRADO);
		log.debug("Sending SolicitacaoAnalise to consultoria: {} items", solicitacoes.size());
		return Flux.fromIterable(solicitacoes).flatMap(s -> sendSolicitacaoAnalise(s));
	}

	private Mono<SolicitacaoAnalise> sendSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();
		Mono<SolicitacaoAnalise> mono = Mono.empty();
		if (consultoria.isHabilitado()) {
			SolicitacaoAnalise deepClone = deepCloneIgnoringIds(solicitacaoAnaliseSGQ);

			mono = WebClient.builder().baseUrl(consultoria.getUrlIntegracao()).build().post()
					.uri("/api/solicitacao-analises/").body(BodyInserters.fromObject(deepClone)).retrieve()
					.bodyToMono(SolicitacaoAnalise.class)
					.doOnSuccess(solicitacaoAnaliseConsultoria -> updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ,
							solicitacaoAnaliseConsultoria))
					.doOnError(e -> {
						log.error("Error on sending SolicitacaoAnalise to consultoria: id {}",
								solicitacaoAnaliseSGQ.getId());
						e.printStackTrace();
					});
		}

		return mono;
	}

	private SolicitacaoAnalise deepCloneIgnoringIds(SolicitacaoAnalise source) {
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

	private Flux<SolicitacaoAnalise> querySolicitacoesWithStatusPendente() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.PENDENTE);
		log.debug("Querying SolicitacaoAnalise on consultoria: {} items", solicitacoes.size());
		return Flux.fromIterable(solicitacoes).flatMap(s -> querySolicitacaoAnalise(s));
	}

	private Mono<SolicitacaoAnalise> querySolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();
		Mono<SolicitacaoAnalise> mono = Mono.empty();
		if (consultoria.isHabilitado()) {
			mono = WebClient.builder().baseUrl(consultoria.getUrlIntegracao()).build().get()
					.uri("/api/solicitacao-analises/").attribute("id", solicitacaoAnaliseSGQ.getIdAcompanhamento())
					.retrieve().bodyToMono(SolicitacaoAnalise.class)
					.doOnSuccess(solicitacaoAnaliseConsultoria -> updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ,
							solicitacaoAnaliseConsultoria))
					.doOnError(e -> {
						log.error("Error on querying SolicitacaoAnalise to consultoria: id {}",
								solicitacaoAnaliseSGQ.getId());
						e.printStackTrace();
					});
		}

		return mono;
	}

	private Mono<SolicitacaoAnalise> updateSolicitacaoAnaliseSGQ(SolicitacaoAnalise solicitacaoAnaliseSGQ,
			SolicitacaoAnalise solicitacaoAnaliseConsultoria) {
		if (solicitacaoAnaliseConsultoria.getStatus() == StatusSolicitacaoAnalise.PENDENTE) {
			log.debug("SolicitacaoAnalise received by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());
			solicitacaoAnaliseSGQ.setDataSolicitacao(solicitacaoAnaliseConsultoria.getDataSolicitacao());
			solicitacaoAnaliseSGQ.setIdAcompanhamento(solicitacaoAnaliseConsultoria.getId());
		}

		if (solicitacaoAnaliseConsultoria.getStatus() == StatusSolicitacaoAnalise.CONCLUIDO) {
			log.debug("SolicitacaoAnalise reviewed by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());
			AnaliseConsultoria analiseConsultoria = solicitacaoAnaliseConsultoria.getAnaliseConsultoria();
			analiseConsultoriaRepository.save(analiseConsultoria.id(null));
			solicitacaoAnaliseSGQ.setAnaliseConsultoria(analiseConsultoria);
		}

		solicitacaoAnaliseRepository.save(solicitacaoAnaliseSGQ);

		return Mono.just(solicitacaoAnaliseSGQ);
	}
}
