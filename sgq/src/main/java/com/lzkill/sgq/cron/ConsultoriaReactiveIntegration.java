package com.lzkill.sgq.cron;

import java.time.Instant;
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

	// TODO Obter o ID do usuário system de forma menos hardcoded
	private final static int SYSTEM_USER_ID = 1;

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

	@Scheduled(cron = "${application.cron.syncsolicitacaoanalise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado().subscribe();
		querySolicitacoesWithStatusPendente().subscribe();
	}

	private Flux<SolicitacaoAnalise> sendSolicitacoesWithStatusRegistrado() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.REGISTRADO);
		if (!solicitacoes.isEmpty())
			log.info("Sending SolicitacaoAnalise to consultoria: {} items", solicitacoes.size());
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
			log.info("Querying SolicitacaoAnalise on consultoria: {} items", solicitacoes.size());
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
			log.info("SolicitacaoAnalise received by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());
			solicitacaoAnaliseSGQ.setDataSolicitacao(solicitacaoAnaliseConsultoria.getDataSolicitacao());
			solicitacaoAnaliseSGQ.setIdAcompanhamento(solicitacaoAnaliseConsultoria.getId());
		}

		if (solicitacaoAnaliseSGQ.getStatus() == StatusSolicitacaoAnalise.PENDENTE
				&& solicitacaoAnaliseConsultoria.getStatus() == StatusSolicitacaoAnalise.CONCLUIDO) {
			log.info("SolicitacaoAnalise reviewed by consultoria: id {}", solicitacaoAnaliseSGQ.getId());
			solicitacaoAnaliseSGQ.setStatus(solicitacaoAnaliseConsultoria.getStatus());

			AnaliseConsultoria analiseConsultoria = saveAnaliseConsultoriaCascade(
					solicitacaoAnaliseConsultoria.getAnaliseConsultoria());

			solicitacaoAnaliseSGQ.setAnaliseConsultoria(analiseConsultoria);
		}

		solicitacaoAnaliseRepository.save(solicitacaoAnaliseSGQ);

		return Mono.just(solicitacaoAnaliseSGQ);
	}

	private AnaliseConsultoria saveAnaliseConsultoriaCascade(AnaliseConsultoria analiseConsultoria) {
		Set<Anexo> anexos = analiseConsultoria.getAnexos();
		if (anexos != null) {
			Instant now = Instant.now();
			anexos.forEach(a -> {
				a.setId(null);
				a.setIdUsuarioRegistro(SYSTEM_USER_ID);
				a.setDataRegistro(now);
			});
			anexoRepository.saveAll(anexos);
		}

		analiseConsultoria.setId(null);
		analiseConsultoriaRepository.save(analiseConsultoria);

		return analiseConsultoria;
	}
}
