import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISetor, Setor } from 'app/shared/model/sgq/setor.model';
import { SetorService } from './setor.service';
import { IEmpresa } from 'app/shared/model/sgq/empresa.model';
import { EmpresaService } from 'app/entities/sgq/empresa/empresa.service';

@Component({
  selector: 'jhi-setor-update',
  templateUrl: './setor-update.component.html'
})
export class SetorUpdateComponent implements OnInit {
  isSaving = false;

  empresas: IEmpresa[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    empresa: [null, Validators.required]
  });

  constructor(
    protected setorService: SetorService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setor }) => {
      this.updateForm(setor);

      this.empresaService
        .query()
        .pipe(
          map((res: HttpResponse<IEmpresa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEmpresa[]) => (this.empresas = resBody));
    });
  }

  updateForm(setor: ISetor): void {
    this.editForm.patchValue({
      id: setor.id,
      nome: setor.nome,
      empresa: setor.empresa
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const setor = this.createFromForm();
    if (setor.id !== undefined) {
      this.subscribeToSaveResponse(this.setorService.update(setor));
    } else {
      this.subscribeToSaveResponse(this.setorService.create(setor));
    }
  }

  private createFromForm(): ISetor {
    return {
      ...new Setor(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      empresa: this.editForm.get(['empresa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetor>>): void {
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

  trackById(index: number, item: IEmpresa): any {
    return item.id;
  }
}
