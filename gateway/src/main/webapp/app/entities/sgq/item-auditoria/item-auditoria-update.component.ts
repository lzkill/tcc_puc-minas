import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IItemAuditoria, ItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { ItemAuditoriaService } from './item-auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from 'app/entities/sgq/processo/processo.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

type SelectableEntity = IProcesso | IAnexo;

@Component({
  selector: 'jhi-item-auditoria-update',
  templateUrl: './item-auditoria-update.component.html'
})
export class ItemAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  processos: IProcesso[] = [];

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    habilitado: [null, [Validators.required]],
    processo: [],
    anexos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected itemAuditoriaService: ItemAuditoriaService,
    protected processoService: ProcessoService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemAuditoria }) => {
      this.updateForm(itemAuditoria);

      this.processoService
        .query()
        .pipe(
          map((res: HttpResponse<IProcesso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProcesso[]) => (this.processos = resBody));

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));
    });
  }

  updateForm(itemAuditoria: IItemAuditoria): void {
    this.editForm.patchValue({
      id: itemAuditoria.id,
      titulo: itemAuditoria.titulo,
      descricao: itemAuditoria.descricao,
      habilitado: itemAuditoria.habilitado,
      processo: itemAuditoria.processo,
      anexos: itemAuditoria.anexos
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
    const itemAuditoria = this.createFromForm();
    if (itemAuditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.itemAuditoriaService.update(itemAuditoria));
    } else {
      this.subscribeToSaveResponse(this.itemAuditoriaService.create(itemAuditoria));
    }
  }

  private createFromForm(): IItemAuditoria {
    return {
      ...new ItemAuditoria(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value,
      processo: this.editForm.get(['processo'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemAuditoria>>): void {
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

  getSelected(selectedVals: IAnexo[], option: IAnexo): IAnexo {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
