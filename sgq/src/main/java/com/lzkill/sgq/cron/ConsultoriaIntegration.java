package com.lzkill.sgq.cron;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.lzkill.sgq.domain.AcaoSGQ;
import com.lzkill.sgq.domain.AnaliseConsultoria;
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
			SolicitacaoAnalise deepClone = deepCloneIgnoringIds(solicitacaoAnaliseSGQ);

			String requestUrl = consultoria.getUrlIntegracao() + "/api/solicitacao-analises/";
			HttpEntity<SolicitacaoAnalise> request = new HttpEntity<>(deepClone);
			RestTemplate restTemplate = new RestTemplate();

			solicitacaoAnaliseConsultoria = restTemplate.postForObject(requestUrl, request, SolicitacaoAnalise.class);
		}

		return solicitacaoAnaliseConsultoria;
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
