package com.lzkill.sgq.cron;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
import com.lzkill.sgq.repository.AnaliseConsultoriaRepository;
import com.lzkill.sgq.repository.AnexoRepository;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ConsultoriaReactiveIntegration {

	private final Logger log = LoggerFactory.getLogger(ConsultoriaReactiveIntegration.class);

	private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;
	private final AnaliseConsultoriaRepository analiseConsultoriaRepository;
	private final AnexoRepository anexoRepository;

	@Autowired
	public ConsultoriaReactiveIntegration(SolicitacaoAnaliseRepository solicitacaoAnaliseRepository,
			AnaliseConsultoriaRepository analiseConsultoriaRepository, AnexoRepository anexoRepository) {
		this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
		this.analiseConsultoriaRepository = analiseConsultoriaRepository;
		this.anexoRepository = anexoRepository;
	}

	@Scheduled(cron = "${application.cron.sync-solicitacao-analise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado().subscribe();
		querySolicitacoesWithStatusPendente().subscribe();
	}

	private Flux<SolicitacaoAnalise> sendSolicitacoesWithStatusRegistrado() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.REGISTRADO);
		if (!solicitacoes.isEmpty())
			log.debug("Sending SolicitacaoAnalise to consultoria: {} items", solicitacoes.size());
		return Flux.fromIterable(solicitacoes).flatMap(s -> sendSolicitacaoAnalise(s));
	}

	private Mono<SolicitacaoAnalise> sendSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();
		Mono<SolicitacaoAnalise> mono = Mono.empty();
		if (consultoria.isHabilitado()) {
			SolicitacaoAnalise clone = cloneSolicitacaoAnalise(solicitacaoAnaliseSGQ);
			String requestUrl = consultoria.getUrlIntegracao() + "/api/solicitacao-analises/";

			mono = WebClient.create(requestUrl).post().body(BodyInserters.fromObject(clone)).retrieve()
					.bodyToMono(SolicitacaoAnalise.class)
					.doOnSuccess(solicitacaoAnaliseConsultoria -> updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ,
							solicitacaoAnaliseConsultoria))
					.doOnError(e -> {
						log.error("Error on sending SolicitacaoAnalise to consultoria: id {}",
								solicitacaoAnaliseSGQ.getId(), e);
					});
		}

		return mono;
	}

	private SolicitacaoAnalise cloneSolicitacaoAnalise(SolicitacaoAnalise source) {
		SolicitacaoAnalise solicitacaoAnalise = new SolicitacaoAnalise();
		solicitacaoAnalise.setDataRegistro(source.getDataRegistro());
		solicitacaoAnalise.setDataSolicitacao(source.getDataSolicitacao());
		solicitacaoAnalise.setStatus(source.getStatus());

		NaoConformidade naoConformidade = cloneNaoConformidade(source.getNaoConformidade());
		solicitacaoAnalise.setNaoConformidade(naoConformidade);

		return solicitacaoAnalise;
	}

	private NaoConformidade cloneNaoConformidade(NaoConformidade source) {
		NaoConformidade naoConformidade = new NaoConformidade();
		naoConformidade.setTitulo(source.getTitulo());
		naoConformidade.setDescricao(source.getDescricao());
		naoConformidade.setProcedente(source.isProcedente());
		naoConformidade.setCausa(source.getCausa());
		naoConformidade.setPrazoConclusao(source.getPrazoConclusao());
		naoConformidade.setNovoPrazoConclusao(source.getNovoPrazoConclusao());
		naoConformidade.setDataRegistro(source.getDataRegistro());
		naoConformidade.setDataRegistro(source.getDataRegistro());
		naoConformidade.setDataConclusao(source.getDataConclusao());
		naoConformidade.setAnaliseFinal(source.getAnaliseFinal());
		naoConformidade.setStatusSGQ(source.getStatusSGQ());

		Set<Anexo> anexos = cloneAnexos(source.getAnexos());
		naoConformidade.setAnexos(anexos);

		Set<AcaoSGQ> acoes = cloneAcoesSGQ(source.getAcaoSGQS());
		naoConformidade.setAcaoSGQS(acoes);

		return naoConformidade;
	}

	private Set<Anexo> cloneAnexos(Set<Anexo> source) {
		HashSet<Anexo> anexos = new HashSet<>();
		source.forEach(a -> {
			Anexo anexo = cloneAnexo(a);
			anexos.add(anexo);
		});
		return anexos;
	}

	private Anexo cloneAnexo(Anexo source) {
		Anexo anexo = new Anexo();
		anexo.setNomeArquivo(source.getNomeArquivo());
		anexo.setConteudo(source.getConteudo());
		anexo.conteudoContentType(source.getConteudoContentType());
		return anexo;
	}

	private Set<AcaoSGQ> cloneAcoesSGQ(Set<AcaoSGQ> source) {
		Set<AcaoSGQ> acoes = new HashSet<>();
		source.forEach(a -> {
			AcaoSGQ acao = cloneAcaoSGQ(a);
			acoes.add(acao);
		});

		return acoes;
	}

	private AcaoSGQ cloneAcaoSGQ(AcaoSGQ source) {
		AcaoSGQ acao = new AcaoSGQ();
		acao.setTipo(source.getTipo());
		acao.setTitulo(source.getTitulo());
		acao.setDescricao(source.getDescricao());
		acao.setPrazoConclusao(source.getPrazoConclusao());
		acao.setNovoPrazoConclusao(source.getNovoPrazoConclusao());
		acao.setDataRegistro(source.getDataRegistro());
		acao.setDataConclusao(source.getDataConclusao());
		acao.setResultado(source.getResultado());
		acao.setStatusSGQ(source.getStatusSGQ());

		Set<Anexo> anexosAcaoSGQ = cloneAnexos(source.getAnexos());
		acao.setAnexos(anexosAcaoSGQ);

		return acao;
	}

	private Flux<SolicitacaoAnalise> querySolicitacoesWithStatusPendente() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.PENDENTE);
		if (!solicitacoes.isEmpty())
			log.debug("Querying SolicitacaoAnalise on consultoria: {} items", solicitacoes.size());
		return Flux.fromIterable(solicitacoes).flatMap(s -> querySolicitacaoAnalise(s));
	}

	private Mono<SolicitacaoAnalise> querySolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();
		Mono<SolicitacaoAnalise> mono = Mono.empty();
		if (consultoria.isHabilitado()) {
			String requestUrl = consultoria.getUrlIntegracao() + "/api/solicitacao-analises/"
					+ solicitacaoAnaliseSGQ.getIdAcompanhamento();
			mono = WebClient.create(requestUrl).get().retrieve().bodyToMono(SolicitacaoAnalise.class)
					.doOnSuccess(solicitacaoAnaliseConsultoria -> updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ,
							solicitacaoAnaliseConsultoria))
					.doOnError(e -> {
						log.error("Error on querying SolicitacaoAnalise to consultoria: id {}",
								solicitacaoAnaliseSGQ.getId(), e);
					});
		}

		return mono;
	}

	private Mono<SolicitacaoAnalise> updateSolicitacaoAnaliseSGQ(SolicitacaoAnalise solicitacaoAnaliseSGQ,
			SolicitacaoAnalise solicitacaoAnaliseConsultoria) {
		if (solicitacaoAnaliseSGQ.getStatus() == StatusSolicitacaoAnalise.REGISTRADO
				&& solicitacaoAnaliseConsultoria.getStatus() == StatusSolicitacaoAnalise.PENDENTE) {
			log.debug("SolicitacaoAnalise received by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());
			solicitacaoAnaliseSGQ.setDataSolicitacao(solicitacaoAnaliseConsultoria.getDataSolicitacao());
			solicitacaoAnaliseSGQ.setIdAcompanhamento(solicitacaoAnaliseConsultoria.getId());
		}

		if (solicitacaoAnaliseSGQ.getStatus() == StatusSolicitacaoAnalise.PENDENTE
				&& solicitacaoAnaliseConsultoria.getStatus() == StatusSolicitacaoAnalise.CONCLUIDO) {
			log.debug("SolicitacaoAnalise reviewed by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());
			AnaliseConsultoria analiseConsultoria = solicitacaoAnaliseConsultoria.getAnaliseConsultoria().id(null);

			Set<Anexo> anexos = analiseConsultoria.getAnexos();
			if (anexos != null) {
				anexos = anexos.stream().map(a -> a.id(null)).collect(Collectors.toSet());
				anexoRepository.saveAll(anexos);
			}

			analiseConsultoriaRepository.save(analiseConsultoria);

			solicitacaoAnaliseSGQ.setAnaliseConsultoria(analiseConsultoria);
		}

		solicitacaoAnaliseRepository.save(solicitacaoAnaliseSGQ);

		return Mono.just(solicitacaoAnaliseSGQ);
	}
}
