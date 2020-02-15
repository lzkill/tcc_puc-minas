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

import { IBoletimInformativo, BoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';
import { BoletimInformativoService } from './boletim-informativo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';
import { PublicoAlvoService } from 'app/entities/sgq/publico-alvo/publico-alvo.service';
import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { CategoriaPublicacaoService } from 'app/entities/sgq/categoria-publicacao/categoria-publicacao.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IPublicoAlvo | ICategoriaPublicacao;

@Component({
  selector: 'jhi-boletim-informativo-update',
  templateUrl: './boletim-informativo-update.component.html'
})
export class BoletimInformativoUpdateComponent implements OnInit {
  isSaving = false;

  publicoalvos: IPublicoAlvo[] = [];
  categoriapublicacaos: ICategoriaPublicacao[] = [];
  usuarios: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    dataRegistro: [null, [Validators.required]],
    dataPublicacao: [],
    status: [null, [Validators.required]],
    publicoAlvo: [null, Validators.required],
    categorias: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected boletimInformativoService: BoletimInformativoService,
    protected publicoAlvoService: PublicoAlvoService,
    protected categoriaPublicacaoService: CategoriaPublicacaoService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boletimInformativo }) => {
      this.updateForm(boletimInformativo);

      this.publicoAlvoService
        .query()
        .pipe(
          map((res: HttpResponse<IPublicoAlvo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPublicoAlvo[]) => (this.publicoalvos = resBody));

      this.categoriaPublicacaoService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoriaPublicacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoriaPublicacao[]) => (this.categoriapublicacaos = resBody));

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

  updateForm(boletimInformativo: IBoletimInformativo): void {
    this.editForm.patchValue({
      id: boletimInformativo.id,
      idUsuarioRegistro: boletimInformativo.idUsuarioRegistro,
      titulo: boletimInformativo.titulo,
      descricao: boletimInformativo.descricao,
      dataRegistro: boletimInformativo.dataRegistro != null ? boletimInformativo.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataPublicacao: boletimInformativo.dataPublicacao != null ? boletimInformativo.dataPublicacao.format(DATE_TIME_FORMAT) : null,
      status: boletimInformativo.status,
      publicoAlvo: boletimInformativo.publicoAlvo,
      categorias: boletimInformativo.categorias
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
    const boletimInformativo = this.createFromForm();
    if (boletimInformativo.id !== undefined) {
      this.subscribeToSaveResponse(this.boletimInformativoService.update(boletimInformativo));
    } else {
      this.subscribeToSaveResponse(this.boletimInformativoService.create(boletimInformativo));
    }
  }

  private createFromForm(): IBoletimInformativo {
    return {
      ...new BoletimInformativo(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataPublicacao:
        this.editForm.get(['dataPublicacao'])!.value != null
          ? moment(this.editForm.get(['dataPublicacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status'])!.value,
      publicoAlvo: this.editForm.get(['publicoAlvo'])!.value,
      categorias: this.editForm.get(['categorias'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoletimInformativo>>): void {
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

  getSelected(selectedVals: ICategoriaPublicacao[], option: ICategoriaPublicacao): ICategoriaPublicacao {
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
