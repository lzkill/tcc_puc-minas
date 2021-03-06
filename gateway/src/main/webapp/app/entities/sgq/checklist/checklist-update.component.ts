import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IChecklist, Checklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from './checklist.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { SetorService } from 'app/entities/sgq/setor/setor.service';

type SelectableEntity = IAnexo | ISetor;

@Component({
  selector: 'jhi-checklist-update',
  templateUrl: './checklist-update.component.html'
})
export class ChecklistUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  setors: ISetor[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    periodicidade: [],
    habilitado: [null, [Validators.required]],
    anexos: [],
    setor: [null, Validators.required]
  });

  constructor(
    protected checklistService: ChecklistService,
    protected anexoService: AnexoService,
    protected setorService: SetorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checklist }) => {
      this.updateForm(checklist);

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

      this.setorService
        .query()
        .pipe(
          map((res: HttpResponse<ISetor[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISetor[]) => (this.setors = resBody));
    });
  }

  updateForm(checklist: IChecklist): void {
    this.editForm.patchValue({
      id: checklist.id,
      titulo: checklist.titulo,
      periodicidade: checklist.periodicidade,
      habilitado: checklist.habilitado,
      anexos: checklist.anexos,
      setor: checklist.setor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checklist = this.createFromForm();
    if (checklist.id !== undefined) {
      this.subscribeToSaveResponse(this.checklistService.update(checklist));
    } else {
      this.subscribeToSaveResponse(this.checklistService.create(checklist));
    }
  }

  private createFromForm(): IChecklist {
    return {
      ...new Checklist(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      periodicidade: this.editForm.get(['periodicidade'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      setor: this.editForm.get(['setor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChecklist>>): void {
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
