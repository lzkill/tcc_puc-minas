import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IItemChecklist, ItemChecklist } from 'app/shared/model/sgq/item-checklist.model';
import { ItemChecklistService } from './item-checklist.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';
import { ChecklistService } from 'app/entities/sgq/checklist/checklist.service';

@Component({
  selector: 'jhi-item-checklist-update',
  templateUrl: './item-checklist-update.component.html'
})
export class ItemChecklistUpdateComponent implements OnInit {
  isSaving = false;

  checklists: IChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    ordem: [null, [Validators.required, Validators.min(1)]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    checklist: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected itemChecklistService: ItemChecklistService,
    protected checklistService: ChecklistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemChecklist }) => {
      this.updateForm(itemChecklist);

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

  updateForm(itemChecklist: IItemChecklist): void {
    this.editForm.patchValue({
      id: itemChecklist.id,
      ordem: itemChecklist.ordem,
      titulo: itemChecklist.titulo,
      descricao: itemChecklist.descricao,
      checklist: itemChecklist.checklist
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
    const itemChecklist = this.createFromForm();
    if (itemChecklist.id !== undefined) {
      this.subscribeToSaveResponse(this.itemChecklistService.update(itemChecklist));
    } else {
      this.subscribeToSaveResponse(this.itemChecklistService.create(itemChecklist));
    }
  }

  private createFromForm(): IItemChecklist {
    return {
      ...new ItemChecklist(),
      id: this.editForm.get(['id'])!.value,
      ordem: this.editForm.get(['ordem'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      checklist: this.editForm.get(['checklist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemChecklist>>): void {
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

  trackById(index: number, item: IChecklist): any {
    return item.id;
  }
}
