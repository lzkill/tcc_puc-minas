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
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from 'app/entities/sgq/checklist/checklist.service';

type SelectableEntity = IAnexo | IChecklist;

@Component({
  selector: 'jhi-resultado-checklist-update',
  templateUrl: './resultado-checklist-update.component.html'
})
export class ResultadoChecklistUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  checklists: IChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    dataVerificacao: [null, [Validators.required]],
    anexo: [],
    checklist: [null, Validators.required]
  });

  constructor(
    protected resultadoChecklistService: ResultadoChecklistService,
    protected anexoService: AnexoService,
    protected checklistService: ChecklistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoChecklist }) => {
      this.updateForm(resultadoChecklist);

      this.anexoService
        .query({ 'resultadoChecklistId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => {
          if (!resultadoChecklist.anexo || !resultadoChecklist.anexo.id) {
            this.anexos = resBody;
          } else {
            this.anexoService
              .find(resultadoChecklist.anexo.id)
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

      this.checklistService
        .query()
        .pipe(
          map((res: HttpResponse<IChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IChecklist[]) => (this.checklists = resBody));
    });
  }

  updateForm(resultadoChecklist: IResultadoChecklist): void {
    this.editForm.patchValue({
      id: resultadoChecklist.id,
      idUsuarioRegistro: resultadoChecklist.idUsuarioRegistro,
      titulo: resultadoChecklist.titulo,
      dataVerificacao: resultadoChecklist.dataVerificacao != null ? resultadoChecklist.dataVerificacao.format(DATE_TIME_FORMAT) : null,
      anexo: resultadoChecklist.anexo,
      checklist: resultadoChecklist.checklist
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
      titulo: this.editForm.get(['titulo'])!.value,
      dataVerificacao:
        this.editForm.get(['dataVerificacao'])!.value != null
          ? moment(this.editForm.get(['dataVerificacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      anexo: this.editForm.get(['anexo'])!.value,
      checklist: this.editForm.get(['checklist'])!.value
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
}