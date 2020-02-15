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

import { IEventoOperacional, EventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { EventoOperacionalService } from './evento-operacional.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProcesso } from 'app/shared/model/sgq/processo.model';
import { ProcessoService } from 'app/entities/sgq/processo/processo.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-evento-operacional-update',
  templateUrl: './evento-operacional-update.component.html'
})
export class EventoOperacionalUpdateComponent implements OnInit {
  isSaving = false;

  processos: IProcesso[] = [];
  usuarios: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    tipo: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [null, [Validators.required]],
    dataEvento: [null, [Validators.required]],
    duracao: [],
    houveParadaProducao: [null, [Validators.required]],
    processo: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected eventoOperacionalService: EventoOperacionalService,
    protected processoService: ProcessoService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventoOperacional }) => {
      this.updateForm(eventoOperacional);

      this.processoService
        .query()
        .pipe(
          map((res: HttpResponse<IProcesso[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProcesso[]) => (this.processos = resBody));

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!item.isAdmin()) this.usuarios.push(item);
          })
        );
    });
  }

  updateForm(eventoOperacional: IEventoOperacional): void {
    this.editForm.patchValue({
      id: eventoOperacional.id,
      idUsuarioRegistro: eventoOperacional.idUsuarioRegistro,
      tipo: eventoOperacional.tipo,
      titulo: eventoOperacional.titulo,
      descricao: eventoOperacional.descricao,
      dataEvento: eventoOperacional.dataEvento != null ? eventoOperacional.dataEvento.format(DATE_TIME_FORMAT) : null,
      duracao: eventoOperacional.duracao,
      houveParadaProducao: eventoOperacional.houveParadaProducao,
      processo: eventoOperacional.processo
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
    const eventoOperacional = this.createFromForm();
    if (eventoOperacional.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoOperacionalService.update(eventoOperacional));
    } else {
      this.subscribeToSaveResponse(this.eventoOperacionalService.create(eventoOperacional));
    }
  }

  private createFromForm(): IEventoOperacional {
    return {
      ...new EventoOperacional(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataEvento:
        this.editForm.get(['dataEvento'])!.value != null ? moment(this.editForm.get(['dataEvento'])!.value, DATE_TIME_FORMAT) : undefined,
      duracao: this.editForm.get(['duracao'])!.value,
      houveParadaProducao: this.editForm.get(['houveParadaProducao'])!.value,
      processo: this.editForm.get(['processo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventoOperacional>>): void {
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

  trackById(index: number, item: IProcesso): any {
    return item.id;
  }
}
