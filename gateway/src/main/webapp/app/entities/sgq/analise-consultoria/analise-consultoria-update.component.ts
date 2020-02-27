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

import { IAnaliseConsultoria, AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from './analise-consultoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

@Component({
  selector: 'jhi-analise-consultoria-update',
  templateUrl: './analise-consultoria-update.component.html'
})
export class AnaliseConsultoriaUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    dataAnalise: [null, [Validators.required]],
    conteudo: [null, [Validators.required]],
    responsavel: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    status: [null, [Validators.required]],
    anexos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analiseConsultoria }) => {
      this.updateForm(analiseConsultoria);

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

  updateForm(analiseConsultoria: IAnaliseConsultoria): void {
    this.editForm.patchValue({
      id: analiseConsultoria.id,
      dataAnalise: analiseConsultoria.dataAnalise != null ? analiseConsultoria.dataAnalise.format(DATE_TIME_FORMAT) : null,
      conteudo: analiseConsultoria.conteudo,
      responsavel: analiseConsultoria.responsavel,
      status: analiseConsultoria.status,
      anexos: analiseConsultoria.anexos
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
    const analiseConsultoria = this.createFromForm();
    if (analiseConsultoria.id !== undefined) {
      this.subscribeToSaveResponse(this.analiseConsultoriaService.update(analiseConsultoria));
    } else {
      this.subscribeToSaveResponse(this.analiseConsultoriaService.create(analiseConsultoria));
    }
  }

  private createFromForm(): IAnaliseConsultoria {
    return {
      ...new AnaliseConsultoria(),
      id: this.editForm.get(['id'])!.value,
      dataAnalise:
        this.editForm.get(['dataAnalise'])!.value != null ? moment(this.editForm.get(['dataAnalise'])!.value, DATE_TIME_FORMAT) : undefined,
      conteudo: this.editForm.get(['conteudo'])!.value,
      responsavel: this.editForm.get(['responsavel'])!.value,
      status: this.editForm.get(['status'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnaliseConsultoria>>): void {
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

  trackById(index: number, item: IAnexo): any {
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
