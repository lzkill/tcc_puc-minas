import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IResultadoChecklist, ResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from './resultado-checklist.service';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from 'app/entities/sgq/checklist/checklist.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

type SelectableEntity = IChecklist | IAnexo;

@Component({
  selector: 'jhi-resultado-checklist-update',
  templateUrl: './resultado-checklist-update.component.html'
})
export class ResultadoChecklistUpdateComponent implements OnInit {
  isSaving = false;

  checklists: IChecklist[] = [];

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    dataVerificacao: [null, [Validators.required]],
    checklist: [null, Validators.required],
    anexos: []
  });

  constructor(
    protected resultadoChecklistService: ResultadoChecklistService,
    protected checklistService: ChecklistService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoChecklist }) => {
      this.updateForm(resultadoChecklist);

      this.checklistService
        .query()
        .pipe(
          map((res: HttpResponse<IChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IChecklist[]) => (this.checklists = resBody));

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

  updateForm(resultadoChecklist: IResultadoChecklist): void {
    this.editForm.patchValue({
      id: resultadoChecklist.id,
      idUsuarioRegistro: resultadoChecklist.idUsuarioRegistro,
      dataRegistro: resultadoChecklist.dataRegistro != null ? resultadoChecklist.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataVerificacao: resultadoChecklist.dataVerificacao != null ? resultadoChecklist.dataVerificacao.format(DATE_TIME_FORMAT) : null,
      checklist: resultadoChecklist.checklist,
      anexos: resultadoChecklist.anexos
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultadoChecklist = this.createFromForm();
    if (resultadoChecklist.id !== undefined) {
      this.subscribeToSaveResponse(this.resultadoChecklistService.update(resultadoChecklist));
    } else {
      this.subscribeToSaveResponse(this.resultadoChecklistService.create(resultadoChecklist));
    }
  }

  private createFromForm(): IResultadoChecklist {
    return {
      ...new ResultadoChecklist(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataVerificacao:
        this.editForm.get(['dataVerificacao'])!.value != null
          ? moment(this.editForm.get(['dataVerificacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      checklist: this.editForm.get(['checklist'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadoChecklist>>): void {
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
