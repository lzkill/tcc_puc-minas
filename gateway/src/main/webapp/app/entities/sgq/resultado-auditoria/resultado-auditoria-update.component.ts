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

import { IResultadoAuditoria, ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';
import { ResultadoAuditoriaService } from './resultado-auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from 'app/entities/sgq/auditoria/auditoria.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-resultado-auditoria-update',
  templateUrl: './resultado-auditoria-update.component.html'
})
export class ResultadoAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  auditorias: IAuditoria[] = [];
  usuarios: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioResponsavel: [null, [Validators.required]],
    dataInicio: [null, [Validators.required]],
    dataFim: [null, [Validators.required]],
    descricao: [null, [Validators.required]],
    auditoria: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected resultadoAuditoriaService: ResultadoAuditoriaService,
    protected auditoriaService: AuditoriaService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoAuditoria }) => {
      this.updateForm(resultadoAuditoria);

      this.auditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAuditoria[]) => (this.auditorias = resBody));

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!this.userService.isAdmin(item)) this.usuarios.push(item);
          })
        );
    });
  }

  updateForm(resultadoAuditoria: IResultadoAuditoria): void {
    this.editForm.patchValue({
      id: resultadoAuditoria.id,
      idUsuarioResponsavel: resultadoAuditoria.idUsuarioResponsavel,
      dataInicio: resultadoAuditoria.dataInicio != null ? resultadoAuditoria.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFim: resultadoAuditoria.dataFim != null ? resultadoAuditoria.dataFim.format(DATE_TIME_FORMAT) : null,
      descricao: resultadoAuditoria.descricao,
      auditoria: resultadoAuditoria.auditoria
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
    const resultadoAuditoria = this.createFromForm();
    if (resultadoAuditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.resultadoAuditoriaService.update(resultadoAuditoria));
    } else {
      this.subscribeToSaveResponse(this.resultadoAuditoriaService.create(resultadoAuditoria));
    }
  }

  private createFromForm(): IResultadoAuditoria {
    return {
      ...new ResultadoAuditoria(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioResponsavel: this.editForm.get(['idUsuarioResponsavel'])!.value,
      dataInicio:
        this.editForm.get(['dataInicio'])!.value != null ? moment(this.editForm.get(['dataInicio'])!.value, DATE_TIME_FORMAT) : undefined,
      dataFim: this.editForm.get(['dataFim'])!.value != null ? moment(this.editForm.get(['dataFim'])!.value, DATE_TIME_FORMAT) : undefined,
      descricao: this.editForm.get(['descricao'])!.value,
      auditoria: this.editForm.get(['auditoria'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadoAuditoria>>): void {
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

  trackById(index: number, item: IAuditoria): any {
    return item.id;
  }
}
