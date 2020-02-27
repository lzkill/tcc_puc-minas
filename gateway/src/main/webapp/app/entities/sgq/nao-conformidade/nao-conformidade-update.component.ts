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

import { INaoConformidade, NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from './nao-conformidade.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';
import { AuditoriaService } from 'app/entities/sgq/auditoria/auditoria.service';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';
import { ResultadoChecklistService } from 'app/entities/sgq/resultado-checklist/resultado-checklist.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IAnexo | IAuditoria | IResultadoChecklist;

@Component({
  selector: 'jhi-nao-conformidade-update',
  templateUrl: './nao-conformidade-update.component.html'
})
export class NaoConformidadeUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];
  usuarios: IUser[] = [];

  auditorias: IAuditoria[] = [];

  resultadochecklists: IResultadoChecklist[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    idUsuarioResponsavel: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [null, [Validators.required]],
    procedente: [],
    causa: [],
    prazoConclusao: [],
    novoPrazoConclusao: [],
    dataRegistro: [null, [Validators.required]],
    dataConclusao: [],
    analiseFinal: [],
    statusSGQ: [null, [Validators.required]],
    anexos: [],
    auditoria: [],
    resultadoChecklist: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected naoConformidadeService: NaoConformidadeService,
    protected anexoService: AnexoService,
    protected auditoriaService: AuditoriaService,
    protected resultadoChecklistService: ResultadoChecklistService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naoConformidade }) => {
      this.updateForm(naoConformidade);

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

      this.auditoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IAuditoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAuditoria[]) => (this.auditorias = resBody));

      this.resultadoChecklistService
        .query()
        .pipe(
          map((res: HttpResponse<IResultadoChecklist[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResultadoChecklist[]) => (this.resultadochecklists = resBody));

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!this.userService.isAdmin(item)) this.usuarios.push(item);
          })
        );
    });
  }

  updateForm(naoConformidade: INaoConformidade): void {
    this.editForm.patchValue({
      id: naoConformidade.id,
      idUsuarioRegistro: naoConformidade.idUsuarioRegistro,
      idUsuarioResponsavel: naoConformidade.idUsuarioResponsavel,
      titulo: naoConformidade.titulo,
      descricao: naoConformidade.descricao,
      procedente: naoConformidade.procedente,
      causa: naoConformidade.causa,
      prazoConclusao: naoConformidade.prazoConclusao != null ? naoConformidade.prazoConclusao.format(DATE_TIME_FORMAT) : null,
      novoPrazoConclusao: naoConformidade.novoPrazoConclusao != null ? naoConformidade.novoPrazoConclusao.format(DATE_TIME_FORMAT) : null,
      dataRegistro: naoConformidade.dataRegistro != null ? naoConformidade.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataConclusao: naoConformidade.dataConclusao != null ? naoConformidade.dataConclusao.format(DATE_TIME_FORMAT) : null,
      analiseFinal: naoConformidade.analiseFinal,
      statusSGQ: naoConformidade.statusSGQ,
      anexos: naoConformidade.anexos,
      auditoria: naoConformidade.auditoria,
      resultadoChecklist: naoConformidade.resultadoChecklist
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
    const naoConformidade = this.createFromForm();
    if (naoConformidade.id !== undefined) {
      this.subscribeToSaveResponse(this.naoConformidadeService.update(naoConformidade));
    } else {
      this.subscribeToSaveResponse(this.naoConformidadeService.create(naoConformidade));
    }
  }

  private createFromForm(): INaoConformidade {
    return {
      ...new NaoConformidade(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      idUsuarioResponsavel: this.editForm.get(['idUsuarioResponsavel'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      procedente: this.editForm.get(['procedente'])!.value,
      causa: this.editForm.get(['causa'])!.value,
      prazoConclusao:
        this.editForm.get(['prazoConclusao'])!.value != null
          ? moment(this.editForm.get(['prazoConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      novoPrazoConclusao:
        this.editForm.get(['novoPrazoConclusao'])!.value != null
          ? moment(this.editForm.get(['novoPrazoConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataConclusao:
        this.editForm.get(['dataConclusao'])!.value != null
          ? moment(this.editForm.get(['dataConclusao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      analiseFinal: this.editForm.get(['analiseFinal'])!.value,
      statusSGQ: this.editForm.get(['statusSGQ'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      auditoria: this.editForm.get(['auditoria'])!.value,
      resultadoChecklist: this.editForm.get(['resultadoChecklist'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INaoConformidade>>): void {
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
