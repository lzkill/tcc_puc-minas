package com.lzkill.sgq.cron;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.AnaliseConsultoria;
import com.lzkill.sgq.domain.Anexo;
import com.lzkill.sgq.domain.Consultoria;
import com.lzkill.sgq.domain.NaoConformidade;
import com.lzkill.sgq.domain.SolicitacaoAnalise;
import com.lzkill.sgq.domain.enumeration.StatusSolicitacaoAnalise;
import com.lzkill.sgq.repository.AnaliseConsultoriaRepository;
import com.lzkill.sgq.repository.SolicitacaoAnaliseRepository;

//@Component
public class ConsultoriaIntegration {

	private final Logger log = LoggerFactory.getLogger(ConsultoriaIntegration.class);

	private final SolicitacaoAnaliseRepository solicitacaoAnaliseRepository;
	private final AnaliseConsultoriaRepository analiseConsultoriaRepository;

	@Autowired
	public ConsultoriaIntegration(SolicitacaoAnaliseRepository solicitacaoAnaliseRepository,
			AnaliseConsultoriaRepository analiseConsultoriaRepository) {
		this.solicitacaoAnaliseRepository = solicitacaoAnaliseRepository;
		this.analiseConsultoriaRepository = analiseConsultoriaRepository;
	}

	// @Scheduled(cron = "${application.cron.sync-solicitacao-analise}")
	public void syncSolicitacaoAnalise() {
		sendSolicitacoesWithStatusRegistrado();
		querySolicitacoesWithStatusPendente();
	}

	private void sendSolicitacoesWithStatusRegistrado() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.REGISTRADO);
		if (!solicitacoes.isEmpty())
			log.debug("Sending SolicitacaoAnalise to consultoria: {} items", solicitacoes.size());
		solicitacoes.forEach(solicitacaoAnaliseSGQ -> {
			SolicitacaoAnalise solicitacaoAnaliseConsultoria = sendSolicitacaoAnalise(solicitacaoAnaliseSGQ);
			updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ, solicitacaoAnaliseConsultoria);
		});
	}

	private SolicitacaoAnalise sendSolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();
		SolicitacaoAnalise solicitacaoAnaliseConsultoria = null;
		if (consultoria.isHabilitado()) {
			SolicitacaoAnalise clone = cloneSolicitacaoAnalise(solicitacaoAnaliseSGQ);

			String requestUrl = consultoria.getUrlIntegracao() + "/api/solicitacao-analises/";
			HttpEntity<SolicitacaoAnalise> request = new HttpEntity<>(clone);
			RestTemplate restTemplate = new RestTemplate();

			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptors.add(new LoggingRequestInterceptor());
			restTemplate.setInterceptors(interceptors);

			solicitacaoAnaliseConsultoria = restTemplate.postForObject(requestUrl, request, SolicitacaoAnalise.class);
		}

		return solicitacaoAnaliseConsultoria;
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

	private void querySolicitacoesWithStatusPendente() {
		List<SolicitacaoAnalise> solicitacoes = solicitacaoAnaliseRepository
				.findByStatus(StatusSolicitacaoAnalise.PENDENTE);
		if (!solicitacoes.isEmpty())
			log.debug("Querying SolicitacaoAnalise on consultoria: {} items", solicitacoes.size());
		solicitacoes.forEach(solicitacaoAnaliseSGQ -> {
			SolicitacaoAnalise solicitacaoAnaliseConsultoria = querySolicitacaoAnalise(solicitacaoAnaliseSGQ);
			updateSolicitacaoAnaliseSGQ(solicitacaoAnaliseSGQ, solicitacaoAnaliseConsultoria);
		});
	}

	private SolicitacaoAnalise querySolicitacaoAnalise(SolicitacaoAnalise solicitacaoAnaliseSGQ) {
		Consultoria consultoria = solicitacaoAnaliseSGQ.getConsultoria();

		SolicitacaoAnalise solicitacaoAnaliseConsultoria = null;
		if (consultoria.isHabilitado()) {
			String requestUrl = consultoria.getUrlIntegracao() + "/api/solicitacao-analises/"
					+ solicitacaoAnaliseSGQ.getIdAcompanhamento();
			RestTemplate restTemplate = new RestTemplate();
			solicitacaoAnaliseConsultoria = restTemplate.getForObject(requestUrl, SolicitacaoAnalise.class);
		}

		return solicitacaoAnaliseConsultoria;
	}

	private void updateSolicitacaoAnaliseSGQ(SolicitacaoAnalise solicitacaoAnaliseSGQ,
			SolicitacaoAnalise solicitacaoAnaliseConsultoria) {
		if (solicitacaoAnaliseSGQ != null && solicitacaoAnaliseConsultoria != null) {
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
				AnaliseConsultoria analiseConsultoria = solicitacaoAnaliseConsultoria.getAnaliseConsultoria();
				analiseConsultoriaRepository.save(analiseConsultoria.id(null));
				solicitacaoAnaliseSGQ.setAnaliseConsultoria(analiseConsultoria);
			}

			solicitacaoAnaliseRepository.save(solicitacaoAnaliseSGQ);
		}
	}
}
