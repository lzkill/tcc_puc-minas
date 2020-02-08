import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFeed, Feed } from 'app/shared/model/sgq/feed.model';
import { FeedService } from './feed.service';

@Component({
  selector: 'jhi-feed-update',
  templateUrl: './feed-update.component.html'
})
export class FeedUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    tipo: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    uri: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(150)]],
    link: [null, [Validators.minLength(1), Validators.maxLength(150)]],
    urlImagem: [null, [Validators.minLength(1), Validators.maxLength(150)]],
    tituloImagem: [null, [Validators.minLength(1), Validators.maxLength(150)]],
    alturaImagem: [null, [Validators.min(32), Validators.max(1024)]],
    larguraImagem: [null, [Validators.min(32), Validators.max(1024)]],
    dataRegistro: [null, [Validators.required]],
    status: [null, [Validators.required]]
  });

  constructor(protected feedService: FeedService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feed }) => {
      this.updateForm(feed);
    });
  }

  updateForm(feed: IFeed): void {
    this.editForm.patchValue({
      id: feed.id,
      idUsuarioRegistro: feed.idUsuarioRegistro,
      tipo: feed.tipo,
      titulo: feed.titulo,
      descricao: feed.descricao,
      uri: feed.uri,
      link: feed.link,
      urlImagem: feed.urlImagem,
      tituloImagem: feed.tituloImagem,
      alturaImagem: feed.alturaImagem,
      larguraImagem: feed.larguraImagem,
      dataRegistro: feed.dataRegistro != null ? feed.dataRegistro.format(DATE_TIME_FORMAT) : null,
      status: feed.status
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feed = this.createFromForm();
    if (feed.id !== undefined) {
      this.subscribeToSaveResponse(this.feedService.update(feed));
    } else {
      this.subscribeToSaveResponse(this.feedService.create(feed));
    }
  }

  private createFromForm(): IFeed {
    return {
      ...new Feed(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      link: this.editForm.get(['link'])!.value,
      urlImagem: this.editForm.get(['urlImagem'])!.value,
      tituloImagem: this.editForm.get(['tituloImagem'])!.value,
      alturaImagem: this.editForm.get(['alturaImagem'])!.value,
      larguraImagem: this.editForm.get(['larguraImagem'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeed>>): void {
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
