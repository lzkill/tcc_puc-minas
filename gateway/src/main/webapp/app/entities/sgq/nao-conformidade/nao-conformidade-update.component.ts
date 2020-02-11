import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INaoConformidade, NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from './nao-conformidade.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { ResultadoAuditoriaService } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.service';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { ResultadoItemChecklistService } from 'app/entities/sgq/resultado-item-checklist/resultado-item-checklist.service';

type SelectableEntity = IResultadoAuditoria | IResultadoItemChecklist;

@Component({
  selector: 'jhi-nao-conformidade-update',
  templateUrl: './nao-conformidade-update.component.html'
})
export class NaoConformidadeUpdateComponent implements OnInit {
  isSaving = false;

  resultadoauditorias: IResultadoAuditoria[] = [];

  resultadoitemchecklists: IResultadoItemChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    idUsuarioResponsavel: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    fato: [null, [Validators.required]],
    procedente: [null, [Validators.required]],
    causa: [],
    prazoConclusao: [null, [Validators.required]],
    novoPrazoConclusao: [],
    dataRegistro: [null, [Validators.required]],
    dataConclusao: [],
    analiseFinal: [],
    statusSGQ: [null, [Validators.required]],
    resultadoAuditoria: [],
    resultadoItemChecklist: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected naoConformidadeService: NaoConformidadeService,
    protected resultadoAuditoriaService: ResultadoAuditoriaService,
    protected resultadoItemChecklistService: ResultadoItemChecklistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naoConformidade }) => {
      this.updateForm(naoConformidade);

      this.resultadoAuditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IResultadoAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResultadoAuditoria[]) => (this.resultadoauditorias = resBody));

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

  updateForm(naoConformidade: INaoConformidade): void {
    this.editForm.patchValue({
      id: naoConformidade.id,
      idUsuarioRegistro: naoConformidade.idUsuarioRegistro,
      idUsuarioResponsavel: naoConformidade.idUsuarioResponsavel,
      titulo: naoConformidade.titulo,
      fato: naoConformidade.fato,
      procedente: naoConformidade.procedente,
      causa: naoConformidade.causa,
      prazoConclusao: naoConformidade.prazoConclusao != null ? naoConformidade.prazoConclusao.format(DATE_TIME_FORMAT) : null,
      novoPrazoConclusao: naoConformidade.novoPrazoConclusao != null ? naoConformidade.novoPrazoConclusao.format(DATE_TIME_FORMAT) : null,
      dataRegistro: naoConformidade.dataRegistro != null ? naoConformidade.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataConclusao: naoConformidade.dataConclusao != null ? naoConformidade.dataConclusao.format(DATE_TIME_FORMAT) : null,
      analiseFinal: naoConformidade.analiseFinal,
      statusSGQ: naoConformidade.statusSGQ,
      resultadoAuditoria: naoConformidade.resultadoAuditoria,
      resultadoItemChecklist: naoConformidade.resultadoItemChecklist
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
    const naoConformidade = this.createFromForm();
    if (naoConformidade.id !== undefined) {
      this.subscribeToSaveResponse(this.naoConformidadeService.update(naoConformidade));
    } else {
      this.subscribeToSaveResponse(this.naoConformidadeService.create(naoConformidade));
    }
  }

  private createFromForm(): INaoConformidade {
    return {
      ...new NaoConformidade(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      idUsuarioResponsavel: this.editForm.get(['idUsuarioResponsavel'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      fato: this.editForm.get(['fato'])!.value,
      procedente: this.editForm.get(['procedente'])!.value,
      causa: this.editForm.get(['causa'])!.value,
      prazoConclusao:
        this.editForm.get(['prazoConclusao'])!.value != null
          ? moment(this.editForm.get(['prazoConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      novoPrazoConclusao:
        this.editForm.get(['novoPrazoConclusao'])!.value != null
          ? moment(this.editForm.get(['novoPrazoConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataConclusao:
        this.editForm.get(['dataConclusao'])!.value != null
          ? moment(this.editForm.get(['dataConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      analiseFinal: this.editForm.get(['analiseFinal'])!.value,
      statusSGQ: this.editForm.get(['statusSGQ'])!.value,
      resultadoAuditoria: this.editForm.get(['resultadoAuditoria'])!.value,
      resultadoItemChecklist: this.editForm.get(['resultadoItemChecklist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INaoConformidade>>): void {
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
