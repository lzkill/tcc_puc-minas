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

import { IPlanoAuditoria, PlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from './plano-auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

@Component({
  selector: 'jhi-plano-auditoria-update',
  templateUrl: './plano-auditoria-update.component.html'
})
export class PlanoAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    dataInicio: [],
    dataFim: [],
    anexo: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected planoAuditoriaService: PlanoAuditoriaService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAuditoria }) => {
      this.updateForm(planoAuditoria);

      this.anexoService
        .query({ 'planoAuditoriaId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => {
          if (!planoAuditoria.anexo || !planoAuditoria.anexo.id) {
            this.anexos = resBody;
          } else {
            this.anexoService
              .find(planoAuditoria.anexo.id)
              .pipe(
                map((subRes: HttpResponse<IAnexo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAnexo[]) => {
                this.anexos = concatRes;
              });
          }
        });
    });
  }

  updateForm(planoAuditoria: IPlanoAuditoria): void {
    this.editForm.patchValue({
      id: planoAuditoria.id,
      titulo: planoAuditoria.titulo,
      descricao: planoAuditoria.descricao,
      dataInicio: planoAuditoria.dataInicio != null ? planoAuditoria.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFim: planoAuditoria.dataFim != null ? planoAuditoria.dataFim.format(DATE_TIME_FORMAT) : null,
      anexo: planoAuditoria.anexo
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
    const planoAuditoria = this.createFromForm();
    if (planoAuditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.planoAuditoriaService.update(planoAuditoria));
    } else {
      this.subscribeToSaveResponse(this.planoAuditoriaService.create(planoAuditoria));
    }
  }

  private createFromForm(): IPlanoAuditoria {
    return {
      ...new PlanoAuditoria(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataInicio:
        this.editForm.get(['dataInicio'])!.value != null ? moment(this.editForm.get(['dataInicio'])!.value, DATE_TIME_FORMAT) : undefined,
      dataFim: this.editForm.get(['dataFim'])!.value != null ? moment(this.editForm.get(['dataFim'])!.value, DATE_TIME_FORMAT) : undefined,
      anexo: this.editForm.get(['anexo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoAuditoria>>): void {
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

  trackById(index: number, item: IAnexo): any {
    return item.id;
  }
}
