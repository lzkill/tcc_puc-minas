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

import { IAcaoSGQ, AcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from './acao-sgq.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';

@Component({
  selector: 'jhi-acao-sgq-update',
  templateUrl: './acao-sgq-update.component.html'
})
export class AcaoSGQUpdateComponent implements OnInit {
  isSaving = false;

  naoconformidades: INaoConformidade[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    idUsuarioResponsavel: [null, [Validators.required]],
    tipo: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [null, [Validators.required]],
    prazoConclusao: [null, [Validators.required]],
    novoPrazoConclusao: [],
    dataRegistro: [null, [Validators.required]],
    dataConclusao: [],
    resultado: [],
    statusSGQ: [null, [Validators.required]],
    naoConformidade: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected acaoSGQService: AcaoSGQService,
    protected naoConformidadeService: NaoConformidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acaoSGQ }) => {
      this.updateForm(acaoSGQ);

      this.naoConformidadeService
        .query()
        .pipe(
          map((res: HttpResponse<INaoConformidade[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INaoConformidade[]) => (this.naoconformidades = resBody));
    });
  }

  updateForm(acaoSGQ: IAcaoSGQ): void {
    this.editForm.patchValue({
      id: acaoSGQ.id,
      idUsuarioRegistro: acaoSGQ.idUsuarioRegistro,
      idUsuarioResponsavel: acaoSGQ.idUsuarioResponsavel,
      tipo: acaoSGQ.tipo,
      titulo: acaoSGQ.titulo,
      descricao: acaoSGQ.descricao,
      prazoConclusao: acaoSGQ.prazoConclusao != null ? acaoSGQ.prazoConclusao.format(DATE_TIME_FORMAT) : null,
      novoPrazoConclusao: acaoSGQ.novoPrazoConclusao != null ? acaoSGQ.novoPrazoConclusao.format(DATE_TIME_FORMAT) : null,
      dataRegistro: acaoSGQ.dataRegistro != null ? acaoSGQ.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataConclusao: acaoSGQ.dataConclusao != null ? acaoSGQ.dataConclusao.format(DATE_TIME_FORMAT) : null,
      resultado: acaoSGQ.resultado,
      statusSGQ: acaoSGQ.statusSGQ,
      naoConformidade: acaoSGQ.naoConformidade
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
    const acaoSGQ = this.createFromForm();
    if (acaoSGQ.id !== undefined) {
      this.subscribeToSaveResponse(this.acaoSGQService.update(acaoSGQ));
    } else {
      this.subscribeToSaveResponse(this.acaoSGQService.create(acaoSGQ));
    }
  }

  private createFromForm(): IAcaoSGQ {
    return {
      ...new AcaoSGQ(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      idUsuarioResponsavel: this.editForm.get(['idUsuarioResponsavel'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
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
      resultado: this.editForm.get(['resultado'])!.value,
      statusSGQ: this.editForm.get(['statusSGQ'])!.value,
      naoConformidade: this.editForm.get(['naoConformidade'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcaoSGQ>>): void {
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

  trackById(index: number, item: INaoConformidade): any {
    return item.id;
  }
}
