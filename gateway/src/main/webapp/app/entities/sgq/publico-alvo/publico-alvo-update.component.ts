import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPublicoAlvo, PublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';
import { PublicoAlvoService } from './publico-alvo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-publico-alvo-update',
  templateUrl: './publico-alvo-update.component.html'
})
export class PublicoAlvoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    habilitado: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected publicoAlvoService: PublicoAlvoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicoAlvo }) => {
      this.updateForm(publicoAlvo);
    });
  }

  updateForm(publicoAlvo: IPublicoAlvo): void {
    this.editForm.patchValue({
      id: publicoAlvo.id,
      nome: publicoAlvo.nome,
      descricao: publicoAlvo.descricao,
      habilitado: publicoAlvo.habilitado
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
    const publicoAlvo = this.createFromForm();
    if (publicoAlvo.id !== undefined) {
      this.subscribeToSaveResponse(this.publicoAlvoService.update(publicoAlvo));
    } else {
      this.subscribeToSaveResponse(this.publicoAlvoService.create(publicoAlvo));
    }
  }

  private createFromForm(): IPublicoAlvo {
    return {
      ...new PublicoAlvo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicoAlvo>>): void {
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
