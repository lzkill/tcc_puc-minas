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

import { ICampanhaRecall, CampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';
import { CampanhaRecallService } from './campanha-recall.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from 'app/entities/sgq/produto/produto.service';
import { ISetor } from 'app/shared/model/sgq/setor.model';
import { SetorService } from 'app/entities/sgq/setor/setor.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';

type SelectableEntity = IProduto | ISetor | IAnexo;

@Component({
  selector: 'jhi-campanha-recall-update',
  templateUrl: './campanha-recall-update.component.html'
})
export class CampanhaRecallUpdateComponent implements OnInit {
  isSaving = false;

  produtos: IProduto[] = [];

  setors: ISetor[] = [];

  anexos: IAnexo[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [],
    dataRegistro: [null, [Validators.required]],
    dataInicio: [],
    dataFim: [],
    dataPublicacao: [],
    status: [null, [Validators.required]],
    produto: [null, Validators.required],
    setorResponsavel: [null, Validators.required],
    anexos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected campanhaRecallService: CampanhaRecallService,
    protected produtoService: ProdutoService,
    protected setorService: SetorService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campanhaRecall }) => {
      this.updateForm(campanhaRecall);

      this.produtoService
        .query()
        .pipe(
          map((res: HttpResponse<IProduto[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProduto[]) => (this.produtos = resBody));

      this.setorService
        .query()
        .pipe(
          map((res: HttpResponse<ISetor[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISetor[]) => (this.setors = resBody));

      this.anexoService
        .query()
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => (this.anexos = resBody));
    });
  }

  updateForm(campanhaRecall: ICampanhaRecall): void {
    this.editForm.patchValue({
      id: campanhaRecall.id,
      idUsuarioRegistro: campanhaRecall.idUsuarioRegistro,
      titulo: campanhaRecall.titulo,
      descricao: campanhaRecall.descricao,
      dataRegistro: campanhaRecall.dataRegistro != null ? campanhaRecall.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataInicio: campanhaRecall.dataInicio != null ? campanhaRecall.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFim: campanhaRecall.dataFim != null ? campanhaRecall.dataFim.format(DATE_TIME_FORMAT) : null,
      dataPublicacao: campanhaRecall.dataPublicacao != null ? campanhaRecall.dataPublicacao.format(DATE_TIME_FORMAT) : null,
      status: campanhaRecall.status,
      produto: campanhaRecall.produto,
      setorResponsavel: campanhaRecall.setorResponsavel,
      anexos: campanhaRecall.anexos
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
    const campanhaRecall = this.createFromForm();
    if (campanhaRecall.id !== undefined) {
      this.subscribeToSaveResponse(this.campanhaRecallService.update(campanhaRecall));
    } else {
      this.subscribeToSaveResponse(this.campanhaRecallService.create(campanhaRecall));
    }
  }

  private createFromForm(): ICampanhaRecall {
    return {
      ...new CampanhaRecall(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataInicio:
        this.editForm.get(['dataInicio'])!.value != null ? moment(this.editForm.get(['dataInicio'])!.value, DATE_TIME_FORMAT) : undefined,
      dataFim: this.editForm.get(['dataFim'])!.value != null ? moment(this.editForm.get(['dataFim'])!.value, DATE_TIME_FORMAT) : undefined,
      dataPublicacao:
        this.editForm.get(['dataPublicacao'])!.value != null
          ? moment(this.editForm.get(['dataPublicacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status'])!.value,
      produto: this.editForm.get(['produto'])!.value,
      setorResponsavel: this.editForm.get(['setorResponsavel'])!.value,
      anexos: this.editForm.get(['anexos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampanhaRecall>>): void {
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
