import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnexo, Anexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from './anexo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from 'app/entities/sgq/acao-sgq/acao-sgq.service';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from 'app/entities/sgq/checklist/checklist.service';
import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { BoletimInformativoService } from 'app/entities/sgq/boletim-informativo/boletim-informativo.service';
import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { CampanhaRecallService } from 'app/entities/sgq/campanha-recall/campanha-recall.service';
import { IEventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { EventoOperacionalService } from 'app/entities/sgq/evento-operacional/evento-operacional.service';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ItemChecklistService } from 'app/entities/sgq/item-checklist/item-checklist.service';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria.service';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { INorma } from 'app/shared/model/sgq/norma.model';
import { NormaService } from 'app/entities/sgq/norma/norma.service';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from 'app/entities/sgq/processo/processo.service';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from 'app/entities/sgq/produto/produto.service';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from 'app/entities/sgq/plano-auditoria/plano-auditoria.service';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { ProdutoNaoConformeService } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.service';
import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { PublicacaoFeedService } from 'app/entities/sgq/publicacao-feed/publicacao-feed.service';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from 'app/entities/sgq/resultado-checklist/resultado-checklist.service';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { ResultadoItemChecklistService } from 'app/entities/sgq/resultado-item-checklist/resultado-item-checklist.service';

type SelectableEntity =
  | IAcaoSGQ
  | IAnaliseConsultoria
  | IChecklist
  | IBoletimInformativo
  | ICampanhaRecall
  | IEventoOperacional
  | IItemChecklist
  | IItemPlanoAuditoria
  | INaoConformidade
  | INorma
  | IProcesso
  | IProduto
  | IPlanoAuditoria
  | IProdutoNaoConforme
  | IPublicacaoFeed
  | IResultadoChecklist
  | IResultadoItemChecklist;

@Component({
  selector: 'jhi-anexo-update',
  templateUrl: './anexo-update.component.html'
})
export class AnexoUpdateComponent implements OnInit {
  isSaving = false;

  acaosgqs: IAcaoSGQ[] = [];

  analiseconsultorias: IAnaliseConsultoria[] = [];

  checklists: IChecklist[] = [];

  boletiminformativos: IBoletimInformativo[] = [];

  campanharecalls: ICampanhaRecall[] = [];

  eventooperacionals: IEventoOperacional[] = [];

  itemchecklists: IItemChecklist[] = [];

  itemplanoauditorias: IItemPlanoAuditoria[] = [];

  naoconformidades: INaoConformidade[] = [];

  normas: INorma[] = [];

  processos: IProcesso[] = [];

  produtos: IProduto[] = [];

  planoauditorias: IPlanoAuditoria[] = [];

  produtonaoconformes: IProdutoNaoConforme[] = [];

  publicacaofeeds: IPublicacaoFeed[] = [];

  resultadochecklists: IResultadoChecklist[] = [];

  resultadoitemchecklists: IResultadoItemChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    nomeArquivo: [null, [Validators.required]],
    conteudo: [null, [Validators.required]],
    conteudoContentType: [],
    acaoSGQ: [],
    analiseConsultoria: [],
    checklist: [],
    boletimInformativo: [],
    campanhaRecall: [],
    eventoOperacional: [],
    itemChecklist: [],
    itemPlanoAuditoria: [],
    naoConformidade: [],
    norma: [],
    processo: [],
    produto: [],
    planoAuditoria: [],
    produtoNaoConforme: [],
    publicacaoFeed: [],
    resultadoChecklist: [],
    resultadoItemChecklist: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected anexoService: AnexoService,
    protected acaoSGQService: AcaoSGQService,
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    protected checklistService: ChecklistService,
    protected boletimInformativoService: BoletimInformativoService,
    protected campanhaRecallService: CampanhaRecallService,
    protected eventoOperacionalService: EventoOperacionalService,
    protected itemChecklistService: ItemChecklistService,
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    protected naoConformidadeService: NaoConformidadeService,
    protected normaService: NormaService,
    protected processoService: ProcessoService,
    protected produtoService: ProdutoService,
    protected planoAuditoriaService: PlanoAuditoriaService,
    protected produtoNaoConformeService: ProdutoNaoConformeService,
    protected publicacaoFeedService: PublicacaoFeedService,
    protected resultadoChecklistService: ResultadoChecklistService,
    protected resultadoItemChecklistService: ResultadoItemChecklistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexo }) => {
      this.updateForm(anexo);

      this.acaoSGQService
        .query()
        .pipe(
          map((res: HttpResponse<IAcaoSGQ[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAcaoSGQ[]) => (this.acaosgqs = resBody));

      this.analiseConsultoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IAnaliseConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnaliseConsultoria[]) => (this.analiseconsultorias = resBody));

      this.checklistService
        .query()
        .pipe(
          map((res: HttpResponse<IChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IChecklist[]) => (this.checklists = resBody));

      this.boletimInformativoService
        .query()
        .pipe(
          map((res: HttpResponse<IBoletimInformativo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBoletimInformativo[]) => (this.boletiminformativos = resBody));

      this.campanhaRecallService
        .query()
        .pipe(
          map((res: HttpResponse<ICampanhaRecall[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICampanhaRecall[]) => (this.campanharecalls = resBody));

      this.eventoOperacionalService
        .query()
        .pipe(
          map((res: HttpResponse<IEventoOperacional[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEventoOperacional[]) => (this.eventooperacionals = resBody));

      this.itemChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IItemChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IItemChecklist[]) => (this.itemchecklists = resBody));

      this.itemPlanoAuditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IItemPlanoAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IItemPlanoAuditoria[]) => (this.itemplanoauditorias = resBody));

      this.naoConformidadeService
        .query()
        .pipe(
          map((res: HttpResponse<INaoConformidade[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INaoConformidade[]) => (this.naoconformidades = resBody));

      this.normaService
        .query()
        .pipe(
          map((res: HttpResponse<INorma[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INorma[]) => (this.normas = resBody));

      this.processoService
        .query()
        .pipe(
          map((res: HttpResponse<IProcesso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProcesso[]) => (this.processos = resBody));

      this.produtoService
        .query()
        .pipe(
          map((res: HttpResponse<IProduto[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProduto[]) => (this.produtos = resBody));

      this.planoAuditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IPlanoAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlanoAuditoria[]) => (this.planoauditorias = resBody));

      this.produtoNaoConformeService
        .query()
        .pipe(
          map((res: HttpResponse<IProdutoNaoConforme[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProdutoNaoConforme[]) => (this.produtonaoconformes = resBody));

      this.publicacaoFeedService
        .query()
        .pipe(
          map((res: HttpResponse<IPublicacaoFeed[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPublicacaoFeed[]) => (this.publicacaofeeds = resBody));

      this.resultadoChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IResultadoChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResultadoChecklist[]) => (this.resultadochecklists = resBody));

      this.resultadoItemChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IResultadoItemChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResultadoItemChecklist[]) => (this.resultadoitemchecklists = resBody));
    });
  }

  updateForm(anexo: IAnexo): void {
    this.editForm.patchValue({
      id: anexo.id,
      nomeArquivo: anexo.nomeArquivo,
      conteudo: anexo.conteudo,
      conteudoContentType: anexo.conteudoContentType,
      acaoSGQ: anexo.acaoSGQ,
      analiseConsultoria: anexo.analiseConsultoria,
      checklist: anexo.checklist,
      boletimInformativo: anexo.boletimInformativo,
      campanhaRecall: anexo.campanhaRecall,
      eventoOperacional: anexo.eventoOperacional,
      itemChecklist: anexo.itemChecklist,
      itemPlanoAuditoria: anexo.itemPlanoAuditoria,
      naoConformidade: anexo.naoConformidade,
      norma: anexo.norma,
      processo: anexo.processo,
      produto: anexo.produto,
      planoAuditoria: anexo.planoAuditoria,
      produtoNaoConforme: anexo.produtoNaoConforme,
      publicacaoFeed: anexo.publicacaoFeed,
      resultadoChecklist: anexo.resultadoChecklist,
      resultadoItemChecklist: anexo.resultadoItemChecklist
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gatewayApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anexo = this.createFromForm();
    if (anexo.id !== undefined) {
      this.subscribeToSaveResponse(this.anexoService.update(anexo));
    } else {
      this.subscribeToSaveResponse(this.anexoService.create(anexo));
    }
  }

  private createFromForm(): IAnexo {
    return {
      ...new Anexo(),
      id: this.editForm.get(['id'])!.value,
      nomeArquivo: this.editForm.get(['nomeArquivo'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      acaoSGQ: this.editForm.get(['acaoSGQ'])!.value,
      analiseConsultoria: this.editForm.get(['analiseConsultoria'])!.value,
      checklist: this.editForm.get(['checklist'])!.value,
      boletimInformativo: this.editForm.get(['boletimInformativo'])!.value,
      campanhaRecall: this.editForm.get(['campanhaRecall'])!.value,
      eventoOperacional: this.editForm.get(['eventoOperacional'])!.value,
      itemChecklist: this.editForm.get(['itemChecklist'])!.value,
      itemPlanoAuditoria: this.editForm.get(['itemPlanoAuditoria'])!.value,
      naoConformidade: this.editForm.get(['naoConformidade'])!.value,
      norma: this.editForm.get(['norma'])!.value,
      processo: this.editForm.get(['processo'])!.value,
      produto: this.editForm.get(['produto'])!.value,
      planoAuditoria: this.editForm.get(['planoAuditoria'])!.value,
      produtoNaoConforme: this.editForm.get(['produtoNaoConforme'])!.value,
      publicacaoFeed: this.editForm.get(['publicacaoFeed'])!.value,
      resultadoChecklist: this.editForm.get(['resultadoChecklist'])!.value,
      resultadoItemChecklist: this.editForm.get(['resultadoItemChecklist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
