import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategoriaPublicacao, CategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';
import { CategoriaPublicacaoService } from './categoria-publicacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-categoria-publicacao-update',
  templateUrl: './categoria-publicacao-update.component.html'
})
export class CategoriaPublicacaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    habilitado: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categoriaPublicacaoService: CategoriaPublicacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaPublicacao }) => {
      this.updateForm(categoriaPublicacao);
    });
  }

  updateForm(categoriaPublicacao: ICategoriaPublicacao): void {
    this.editForm.patchValue({
      id: categoriaPublicacao.id,
      titulo: categoriaPublicacao.titulo,
      descricao: categoriaPublicacao.descricao,
      habilitado: categoriaPublicacao.habilitado
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
    const categoriaPublicacao = this.createFromForm();
    if (categoriaPublicacao.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaPublicacaoService.update(categoriaPublicacao));
    } else {
      this.subscribeToSaveResponse(this.categoriaPublicacaoService.create(categoriaPublicacao));
    }
  }

  private createFromForm(): ICategoriaPublicacao {
    return {
      ...new CategoriaPublicacao(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaPublicacao>>): void {
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
