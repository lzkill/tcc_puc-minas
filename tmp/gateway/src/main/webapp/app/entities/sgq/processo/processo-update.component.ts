import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProcesso, Processo } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from './processo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { SetorService } from 'app/entities/sgq/setor/setor.service';

type SelectableEntity = IAnexo | ISetor;

@Component({
  selector: 'jhi-processo-update',
  templateUrl: './processo-update.component.html'
})
export class ProcessoUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  setors: ISetor[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    habilitado: [null, [Validators.required]],
    anexos: [],
    setor: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected processoService: ProcessoService,
    protected anexoService: AnexoService,
    protected setorService: SetorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processo }) => {
      this.updateForm(processo);

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

  updateForm(processo: IProcesso): void {
    this.editForm.patchValue({
      id: processo.id,
      titulo: processo.titulo,
      descricao: processo.descricao,
      habilitado: processo.habilitado,
      anexos: processo.anexos,
      setor: processo.setor
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
    const processo = this.createFromForm();
    if (processo.id !== undefined) {
      this.subscribeToSaveResponse(this.processoService.update(processo));
    } else {
      this.subscribeToSaveResponse(this.processoService.create(processo));
    }
  }

  private createFromForm(): IProcesso {
    return {
      ...new Processo(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      setor: this.editForm.get(['setor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcesso>>): void {
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
