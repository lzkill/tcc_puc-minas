import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAuditoria, Auditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from './auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from 'app/entities/sgq/processo/processo.service';

@Component({
  selector: 'jhi-auditoria-update',
  templateUrl: './auditoria-update.component.html'
})
export class AuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  processos: IProcesso[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    processo: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected auditoriaService: AuditoriaService,
    protected processoService: ProcessoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditoria }) => {
      this.updateForm(auditoria);

      this.processoService
        .query()
        .pipe(
          map((res: HttpResponse<IProcesso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProcesso[]) => (this.processos = resBody));
    });
  }

  updateForm(auditoria: IAuditoria): void {
    this.editForm.patchValue({
      id: auditoria.id,
      tipo: auditoria.tipo,
      titulo: auditoria.titulo,
      descricao: auditoria.descricao,
      processo: auditoria.processo
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
    const auditoria = this.createFromForm();
    if (auditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.auditoriaService.update(auditoria));
    } else {
      this.subscribeToSaveResponse(this.auditoriaService.create(auditoria));
    }
  }

  private createFromForm(): IAuditoria {
    return {
      ...new Auditoria(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      processo: this.editForm.get(['processo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditoria>>): void {
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

  trackById(index: number, item: IProcesso): any {
    return item.id;
  }
}
