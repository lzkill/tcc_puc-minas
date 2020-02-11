import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPlanoAuditoria, PlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from './plano-auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-plano-auditoria-update',
  templateUrl: './plano-auditoria-update.component.html'
})
export class PlanoAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected planoAuditoriaService: PlanoAuditoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAuditoria }) => {
      this.updateForm(planoAuditoria);
    });
  }

  updateForm(planoAuditoria: IPlanoAuditoria): void {
    this.editForm.patchValue({
      id: planoAuditoria.id,
      titulo: planoAuditoria.titulo,
      descricao: planoAuditoria.descricao
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
      descricao: this.editForm.get(['descricao'])!.value
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
}
