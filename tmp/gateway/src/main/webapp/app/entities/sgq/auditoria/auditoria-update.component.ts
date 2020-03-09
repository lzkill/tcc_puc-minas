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

import { IAuditoria, Auditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from './auditoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from 'app/entities/sgq/consultoria/consultoria.service';
import { IItemAuditoria } from 'app/shared/model/sgq/item-auditoria.model';
import { ItemAuditoriaService } from 'app/entities/sgq/item-auditoria/item-auditoria.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

type SelectableEntity = IConsultoria | IItemAuditoria | IAnexo;

type SelectableManyToManyEntity = IItemAuditoria | IAnexo;

@Component({
  selector: 'jhi-auditoria-update',
  templateUrl: './auditoria-update.component.html'
})
export class AuditoriaUpdateComponent implements OnInit {
  isSaving = false;

  consultorias: IConsultoria[] = [];

  itemauditorias: IItemAuditoria[] = [];

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    dataRegistro: [null, [Validators.required]],
    dataInicio: [],
    dataFim: [],
    auditor: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    consultoria: [],
    itemAuditorias: [],
    anexos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected auditoriaService: AuditoriaService,
    protected consultoriaService: ConsultoriaService,
    protected itemAuditoriaService: ItemAuditoriaService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditoria }) => {
      this.updateForm(auditoria);

      this.consultoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConsultoria[]) => (this.consultorias = resBody));

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
    });
  }

  updateForm(auditoria: IAuditoria): void {
    this.editForm.patchValue({
      id: auditoria.id,
      idUsuarioRegistro: auditoria.idUsuarioRegistro,
      titulo: auditoria.titulo,
      descricao: auditoria.descricao,
      dataRegistro: auditoria.dataRegistro != null ? auditoria.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataInicio: auditoria.dataInicio != null ? auditoria.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFim: auditoria.dataFim != null ? auditoria.dataFim.format(DATE_TIME_FORMAT) : null,
      auditor: auditoria.auditor,
      consultoria: auditoria.consultoria,
      itemAuditorias: auditoria.itemAuditorias,
      anexos: auditoria.anexos
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
    const auditoria = this.createFromForm();
    if (auditoria.id !== undefined) {
      this.subscribeToSaveResponse(this.auditoriaService.update(auditoria));
    } else {
      this.subscribeToSaveResponse(this.auditoriaService.create(auditoria));
    }
  }

  private createFromForm(): IAuditoria {
    return {
      ...new Auditoria(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataInicio:
        this.editForm.get(['dataInicio'])!.value != null ? moment(this.editForm.get(['dataInicio'])!.value, DATE_TIME_FORMAT) : undefined,
      dataFim: this.editForm.get(['dataFim'])!.value != null ? moment(this.editForm.get(['dataFim'])!.value, DATE_TIME_FORMAT) : undefined,
      auditor: this.editForm.get(['auditor'])!.value,
      consultoria: this.editForm.get(['consultoria'])!.value,
      itemAuditorias: this.editForm.get(['itemAuditorias'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditoria>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
