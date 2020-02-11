import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IItemPlanoAuditoria, ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';
import { ItemPlanoAuditoriaService } from './item-plano-auditoria.service';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from 'app/entities/sgq/auditoria/auditoria.service';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';
import { PlanoAuditoriaService } from 'app/entities/sgq/plano-auditoria/plano-auditoria.service';

type SelectableEntity = IAuditoria | IPlanoAuditoria;

@Component({
  selector: 'jhi-item-plano-auditoria-update',
  templateUrl: './item-plano-auditoria-update.component.html'
})
export class ItemPlanoAuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  auditorias: IAuditoria[] = [];

  planoauditorias: IPlanoAuditoria[] = [];

  editForm = this.fb.group({
    id: [],
    dataInicioPrevisto: [],
    dataFimPrevisto: [],
    auditoria: [null, Validators.required],
    plano: [null, Validators.required]
  });

  constructor(
    protected itemPlanoAuditoriaService: ItemPlanoAuditoriaService,
    protected auditoriaService: AuditoriaService,
    protected planoAuditoriaService: PlanoAuditoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemPlanoAuditoria }) => {
      this.updateForm(itemPlanoAuditoria);

      this.auditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAuditoria[]) => (this.auditorias = resBody));

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
      dataInicioPrevisto:
        itemPlanoAuditoria.dataInicioPrevisto != null ? itemPlanoAuditoria.dataInicioPrevisto.format(DATE_TIME_FORMAT) : null,
      dataFimPrevisto: itemPlanoAuditoria.dataFimPrevisto != null ? itemPlanoAuditoria.dataFimPrevisto.format(DATE_TIME_FORMAT) : null,
      auditoria: itemPlanoAuditoria.auditoria,
      plano: itemPlanoAuditoria.plano
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
      dataInicioPrevisto:
        this.editForm.get(['dataInicioPrevisto'])!.value != null
          ? moment(this.editForm.get(['dataInicioPrevisto'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataFimPrevisto:
        this.editForm.get(['dataFimPrevisto'])!.value != null
          ? moment(this.editForm.get(['dataFimPrevisto'])!.value, DATE_TIME_FORMAT)
          : undefined,
      auditoria: this.editForm.get(['auditoria'])!.value,
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
}
