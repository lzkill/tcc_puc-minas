import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConsultoria, Consultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from './consultoria.service';

@Component({
  selector: 'jhi-consultoria-update',
  templateUrl: './consultoria-update.component.html'
})
export class ConsultoriaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    urlIntegracao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(150)]],
    tokenAcesso: [null, [Validators.required, Validators.minLength(1)]],
    habilitado: [null, [Validators.required]]
  });

  constructor(protected consultoriaService: ConsultoriaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consultoria }) => {
      this.updateForm(consultoria);
    });
  }

  updateForm(consultoria: IConsultoria): void {
    this.editForm.patchValue({
      id: consultoria.id,
      nome: consultoria.nome,
      urlIntegracao: consultoria.urlIntegracao,
      tokenAcesso: consultoria.tokenAcesso,
      habilitado: consultoria.habilitado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consultoria = this.createFromForm();
    if (consultoria.id !== undefined) {
      this.subscribeToSaveResponse(this.consultoriaService.update(consultoria));
    } else {
      this.subscribeToSaveResponse(this.consultoriaService.create(consultoria));
    }
  }

  private createFromForm(): IConsultoria {
    return {
      ...new Consultoria(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      urlIntegracao: this.editForm.get(['urlIntegracao'])!.value,
      tokenAcesso: this.editForm.get(['tokenAcesso'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsultoria>>): void {
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
