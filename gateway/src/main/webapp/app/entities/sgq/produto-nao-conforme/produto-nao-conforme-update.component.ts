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

import { IProdutoNaoConforme, ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { ProdutoNaoConformeService } from './produto-nao-conforme.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from 'app/entities/sgq/acao-sgq/acao-sgq.service';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IProduto } from 'app/shared/model/sgq/produto.model';
import { ProdutoService } from 'app/entities/sgq/produto/produto.service';

type SelectableEntity = IAcaoSGQ | INaoConformidade | IAnexo | IProduto;

@Component({
  selector: 'jhi-produto-nao-conforme-update',
  templateUrl: './produto-nao-conforme-update.component.html'
})
export class ProdutoNaoConformeUpdateComponent implements OnInit {
  isSaving = false;

  acaos: IAcaoSGQ[] = [];

  naoconformidades: INaoConformidade[] = [];

  anexos: IAnexo[] = [];

  produtos: IProduto[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    idUsuarioResponsavel: [null, [Validators.required]],
    titulo: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    descricao: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    analiseFinal: [],
    statusSGQ: [null, [Validators.required]],
    acao: [null, Validators.required],
    naoConformidade: [],
    anexo: [],
    produto: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected produtoNaoConformeService: ProdutoNaoConformeService,
    protected acaoSGQService: AcaoSGQService,
    protected naoConformidadeService: NaoConformidadeService,
    protected anexoService: AnexoService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produtoNaoConforme }) => {
      this.updateForm(produtoNaoConforme);

      this.acaoSGQService
        .query({ 'produtoNaoConformeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAcaoSGQ[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAcaoSGQ[]) => {
          if (!produtoNaoConforme.acao || !produtoNaoConforme.acao.id) {
            this.acaos = resBody;
          } else {
            this.acaoSGQService
              .find(produtoNaoConforme.acao.id)
              .pipe(
                map((subRes: HttpResponse<IAcaoSGQ>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAcaoSGQ[]) => {
                this.acaos = concatRes;
              });
          }
        });

      this.naoConformidadeService
        .query({ 'produtoNaoConformeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<INaoConformidade[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INaoConformidade[]) => {
          if (!produtoNaoConforme.naoConformidade || !produtoNaoConforme.naoConformidade.id) {
            this.naoconformidades = resBody;
          } else {
            this.naoConformidadeService
              .find(produtoNaoConforme.naoConformidade.id)
              .pipe(
                map((subRes: HttpResponse<INaoConformidade>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: INaoConformidade[]) => {
                this.naoconformidades = concatRes;
              });
          }
        });

      this.anexoService
        .query({ 'produtoNaoConformeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => {
          if (!produtoNaoConforme.anexo || !produtoNaoConforme.anexo.id) {
            this.anexos = resBody;
          } else {
            this.anexoService
              .find(produtoNaoConforme.anexo.id)
              .pipe(
                map((subRes: HttpResponse<IAnexo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAnexo[]) => {
                this.anexos = concatRes;
              });
          }
        });

      this.produtoService
        .query()
        .pipe(
          map((res: HttpResponse<IProduto[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProduto[]) => (this.produtos = resBody));
    });
  }

  updateForm(produtoNaoConforme: IProdutoNaoConforme): void {
    this.editForm.patchValue({
      id: produtoNaoConforme.id,
      idUsuarioRegistro: produtoNaoConforme.idUsuarioRegistro,
      idUsuarioResponsavel: produtoNaoConforme.idUsuarioResponsavel,
      titulo: produtoNaoConforme.titulo,
      descricao: produtoNaoConforme.descricao,
      dataRegistro: produtoNaoConforme.dataRegistro != null ? produtoNaoConforme.dataRegistro.format(DATE_TIME_FORMAT) : null,
      analiseFinal: produtoNaoConforme.analiseFinal,
      statusSGQ: produtoNaoConforme.statusSGQ,
      acao: produtoNaoConforme.acao,
      naoConformidade: produtoNaoConforme.naoConformidade,
      anexo: produtoNaoConforme.anexo,
      produto: produtoNaoConforme.produto
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
    const produtoNaoConforme = this.createFromForm();
    if (produtoNaoConforme.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoNaoConformeService.update(produtoNaoConforme));
    } else {
      this.subscribeToSaveResponse(this.produtoNaoConformeService.create(produtoNaoConforme));
    }
  }

  private createFromForm(): IProdutoNaoConforme {
    return {
      ...new ProdutoNaoConforme(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      idUsuarioResponsavel: this.editForm.get(['idUsuarioResponsavel'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      analiseFinal: this.editForm.get(['analiseFinal'])!.value,
      statusSGQ: this.editForm.get(['statusSGQ'])!.value,
      acao: this.editForm.get(['acao'])!.value,
      naoConformidade: this.editForm.get(['naoConformidade'])!.value,
      anexo: this.editForm.get(['anexo'])!.value,
      produto: this.editForm.get(['produto'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdutoNaoConforme>>): void {
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
}
