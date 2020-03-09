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

import { IItemPlanoAuditoria, ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from './item-plano-auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { ItemAuditoriaService } from 'app/entities/sgq/item-auditoria/item-auditoria.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from 'app/entities/sgq/plano-auditoria/plano-auditoria.service';

type SelectableEntity = IItemAuditoria | IAnexo | IPlanoAuditoria;

@Component({
  selector: 'jhi-item-plano-auditoria-update',
  templateUrl: './item-plano-auditoria-update.component.html'
})
export class ItemPlanoAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  itemauditorias: IItemAuditoria[] = [];

  anexos: IAnexo[] = [];

  planoauditorias: IPlanoAuditoria[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    modalidade: [null, [Validators.required]],
    dataInicio: [null, [Validators.required]],
    dataFim: [],
    itemAuditoria: [],
    anexos: [],
    plano: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    protected itemAuditoriaService: ItemAuditoriaService,
    protected anexoService: AnexoService,
    protected planoAuditoriaService: PlanoAuditoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemPlanoAuditoria }) => {
      this.updateForm(itemPlanoAuditoria);

      this.itemAuditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IItemAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IItemAuditoria[]) => (this.itemauditorias = resBody));

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

      this.planoAuditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IPlanoAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlanoAuditoria[]) => (this.planoauditorias = resBody));
    });
  }

  updateForm(itemPlanoAuditoria: IItemPlanoAuditoria): void {
    this.editForm.patchValue({
      id: itemPlanoAuditoria.id,
      titulo: itemPlanoAuditoria.titulo,
      descricao: itemPlanoAuditoria.descricao,
      modalidade: itemPlanoAuditoria.modalidade,
      dataInicio: itemPlanoAuditoria.dataInicio != null ? itemPlanoAuditoria.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFim: itemPlanoAuditoria.dataFim != null ? itemPlanoAuditoria.dataFim.format(DATE_TIME_FORMAT) : null,
      itemAuditoria: itemPlanoAuditoria.itemAuditoria,
      anexos: itemPlanoAuditoria.anexos,
      plano: itemPlanoAuditoria.plano
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
    const itemPlanoAuditoria = this.createFromForm();
    if (itemPlanoAuditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.itemPlanoAuditoriaService.update(itemPlanoAuditoria));
    } else {
      this.subscribeToSaveResponse(this.itemPlanoAuditoriaService.create(itemPlanoAuditoria));
    }
  }

  private createFromForm(): IItemPlanoAuditoria {
    return {
      ...new ItemPlanoAuditoria(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      modalidade: this.editForm.get(['modalidade'])!.value,
      dataInicio:
        this.editForm.get(['dataInicio'])!.value != null ? moment(this.editForm.get(['dataInicio'])!.value, DATE_TIME_FORMAT) : undefined,
      dataFim: this.editForm.get(['dataFim'])!.value != null ? moment(this.editForm.get(['dataFim'])!.value, DATE_TIME_FORMAT) : undefined,
      itemAuditoria: this.editForm.get(['itemAuditoria'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      plano: this.editForm.get(['plano'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemPlanoAuditoria>>): void {
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
