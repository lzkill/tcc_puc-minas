import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmpresaConsultoria, EmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';
import { EmpresaConsultoriaService } from './empresa-consultoria.service';

@Component({
  selector: 'jhi-empresa-consultoria-update',
  templateUrl: './empresa-consultoria-update.component.html'
})
export class EmpresaConsultoriaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    urlIntegracao: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(150)]],
    tokenAcesso: [null, [Validators.required, Validators.minLength(1)]]
  });

  constructor(
    protected empresaConsultoriaService: EmpresaConsultoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresaConsultoria }) => {
      this.updateForm(empresaConsultoria);
    });
  }

  updateForm(empresaConsultoria: IEmpresaConsultoria): void {
    this.editForm.patchValue({
      id: empresaConsultoria.id,
      nome: empresaConsultoria.nome,
      urlIntegracao: empresaConsultoria.urlIntegracao,
      tokenAcesso: empresaConsultoria.tokenAcesso
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresaConsultoria = this.createFromForm();
    if (empresaConsultoria.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaConsultoriaService.update(empresaConsultoria));
    } else {
      this.subscribeToSaveResponse(this.empresaConsultoriaService.create(empresaConsultoria));
    }
  }

  private createFromForm(): IEmpresaConsultoria {
    return {
      ...new EmpresaConsultoria(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      urlIntegracao: this.editForm.get(['urlIntegracao'])!.value,
      tokenAcesso: this.editForm.get(['tokenAcesso'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresaConsultoria>>): void {
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
