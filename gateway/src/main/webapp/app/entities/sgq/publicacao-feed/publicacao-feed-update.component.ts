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

import { IPublicacaoFeed, PublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { PublicacaoFeedService } from './publicacao-feed.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFeed } from 'app/shared/model/sgq/feed.model';
import { FeedService } from 'app/entities/sgq/feed/feed.service';
import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { CategoriaPublicacaoService } from 'app/entities/sgq/categoria-publicacao/categoria-publicacao.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IFeed | ICategoriaPublicacao | IAnexo;

type SelectableManyToManyEntity = ICategoriaPublicacao | IAnexo;

@Component({
  selector: 'jhi-publicacao-feed-update',
  templateUrl: './publicacao-feed-update.component.html'
})
export class PublicacaoFeedUpdateComponent implements OnInit {
  isSaving = false;

  feeds: IFeed[] = [];

  categoriapublicacaos: ICategoriaPublicacao[] = [];
  usuarios: IUser[] = [];

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    autor: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    uri: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(150)]],
    link: [null, [Validators.minLength(1), Validators.maxLength(150)]],
    conteudo: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    dataPublicacao: [],
    status: [null, [Validators.required]],
    feed: [null, Validators.required],
    categorias: [null, Validators.required],
    anexos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected publicacaoFeedService: PublicacaoFeedService,
    protected feedService: FeedService,
    protected categoriaPublicacaoService: CategoriaPublicacaoService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,

    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacaoFeed }) => {
      this.updateForm(publicacaoFeed);

      this.feedService
        .query()
        .pipe(
          map((res: HttpResponse<IFeed[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IFeed[]) => (this.feeds = resBody));

      this.categoriaPublicacaoService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoriaPublicacao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoriaPublicacao[]) => (this.categoriapublicacaos = resBody));

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

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

  updateForm(publicacaoFeed: IPublicacaoFeed): void {
    this.editForm.patchValue({
      id: publicacaoFeed.id,
      idUsuarioRegistro: publicacaoFeed.idUsuarioRegistro,
      titulo: publicacaoFeed.titulo,
      autor: publicacaoFeed.autor,
      uri: publicacaoFeed.uri,
      link: publicacaoFeed.link,
      conteudo: publicacaoFeed.conteudo,
      dataRegistro: publicacaoFeed.dataRegistro != null ? publicacaoFeed.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataPublicacao: publicacaoFeed.dataPublicacao != null ? publicacaoFeed.dataPublicacao.format(DATE_TIME_FORMAT) : null,
      status: publicacaoFeed.status,
      feed: publicacaoFeed.feed,
      categorias: publicacaoFeed.categorias,
      anexos: publicacaoFeed.anexos
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
    const publicacaoFeed = this.createFromForm();
    if (publicacaoFeed.id !== undefined) {
      this.subscribeToSaveResponse(this.publicacaoFeedService.update(publicacaoFeed));
    } else {
      this.subscribeToSaveResponse(this.publicacaoFeedService.create(publicacaoFeed));
    }
  }

  private createFromForm(): IPublicacaoFeed {
    return {
      ...new PublicacaoFeed(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      autor: this.editForm.get(['autor'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      link: this.editForm.get(['link'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataPublicacao:
        this.editForm.get(['dataPublicacao'])!.value != null
          ? moment(this.editForm.get(['dataPublicacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status'])!.value,
      feed: this.editForm.get(['feed'])!.value,
      categorias: this.editForm.get(['categorias'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicacaoFeed>>): void {
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
