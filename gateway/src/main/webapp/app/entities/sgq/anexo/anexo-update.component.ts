import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAnexo, Anexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from './anexo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-anexo-update',
  templateUrl: './anexo-update.component.html'
})
export class AnexoUpdateComponent implements OnInit {
  isSaving = false;
  usuarios: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    nomeArquivo: [null, [Validators.required]],
    conteudo: [null, [Validators.required]],
    conteudoContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexo }) => {
      this.updateForm(anexo);

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

  updateForm(anexo: IAnexo): void {
    this.editForm.patchValue({
      id: anexo.id,
      idUsuarioRegistro: anexo.idUsuarioRegistro,
      dataRegistro: anexo.dataRegistro != null ? anexo.dataRegistro.format(DATE_TIME_FORMAT) : null,
      nomeArquivo: anexo.nomeArquivo,
      conteudo: anexo.conteudo,
      conteudoContentType: anexo.conteudoContentType
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
    const anexo = this.createFromForm();
    if (anexo.id !== undefined) {
      this.subscribeToSaveResponse(this.anexoService.update(anexo));
    } else {
      this.subscribeToSaveResponse(this.anexoService.create(anexo));
    }
  }

  private createFromForm(): IAnexo {
    return {
      ...new Anexo(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      nomeArquivo: this.editForm.get(['nomeArquivo'])!.value,
      conteudoContentType: this.editForm.get(['conteudoContentType'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexo>>): void {
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
}
