import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProduto, Produto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from './produto.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IEmpresa } from 'app/shared/model/sgq/empresa.model';
import { EmpresaService } from 'app/entities/sgq/empresa/empresa.service';

type SelectableEntity = IAnexo | IEmpresa;

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving = false;

  anexos: IAnexo[] = [];

  empresas: IEmpresa[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    habilitado: [null, [Validators.required]],
    anexos: [],
    empresa: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected produtoService: ProdutoService,
    protected anexoService: AnexoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));

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

  updateForm(produto: IProduto): void {
    this.editForm.patchValue({
      id: produto.id,
      nome: produto.nome,
      descricao: produto.descricao,
      habilitado: produto.habilitado,
      anexos: produto.anexos,
      empresa: produto.empresa
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
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      habilitado: this.editForm.get(['habilitado'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
      empresa: this.editForm.get(['empresa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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

  getSelected(selectedVals: IAnexo[], option: IAnexo): IAnexo {
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
