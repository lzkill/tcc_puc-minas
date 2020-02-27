import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IResultadoItemChecklist, ResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';
import { ResultadoItemChecklistService } from './resultado-item-checklist.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ItemChecklistService } from 'app/entities/sgq/item-checklist/item-checklist.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from 'app/entities/sgq/resultado-checklist/resultado-checklist.service';

type SelectableEntity = IItemChecklist | IAnexo | IResultadoChecklist;

@Component({
  selector: 'jhi-resultado-item-checklist-update',
  templateUrl: './resultado-item-checklist-update.component.html'
})
export class ResultadoItemChecklistUpdateComponent implements OnInit {
  isSaving = false;

  itemchecklists: IItemChecklist[] = [];

  anexos: IAnexo[] = [];

  resultadochecklists: IResultadoChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    conforme: [null, [Validators.required]],
    descricao: [],
    item: [null, Validators.required],
    anexos: [],
    resultado: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected resultadoItemChecklistService: ResultadoItemChecklistService,
    protected itemChecklistService: ItemChecklistService,
    protected anexoService: AnexoService,
    protected resultadoChecklistService: ResultadoChecklistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoItemChecklist }) => {
      this.updateForm(resultadoItemChecklist);

      this.itemChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IItemChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IItemChecklist[]) => (this.itemchecklists = resBody));

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

      this.resultadoChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IResultadoChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResultadoChecklist[]) => (this.resultadochecklists = resBody));
    });
  }

  updateForm(resultadoItemChecklist: IResultadoItemChecklist): void {
    this.editForm.patchValue({
      id: resultadoItemChecklist.id,
      conforme: resultadoItemChecklist.conforme,
      descricao: resultadoItemChecklist.descricao,
      item: resultadoItemChecklist.item,
      anexos: resultadoItemChecklist.anexos,
      resultado: resultadoItemChecklist.resultado
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
    const resultadoItemChecklist = this.createFromForm();
    if (resultadoItemChecklist.id !== undefined) {
      this.subscribeToSaveResponse(this.resultadoItemChecklistService.update(resultadoItemChecklist));
    } else {
      this.subscribeToSaveResponse(this.resultadoItemChecklistService.create(resultadoItemChecklist));
    }
  }

  private createFromForm(): IResultadoItemChecklist {
    return {
      ...new ResultadoItemChecklist(),
      id: this.editForm.get(['id'])!.value,
      conforme: this.editForm.get(['conforme'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      item: this.editForm.get(['item'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      resultado: this.editForm.get(['resultado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadoItemChecklist>>): void {
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
