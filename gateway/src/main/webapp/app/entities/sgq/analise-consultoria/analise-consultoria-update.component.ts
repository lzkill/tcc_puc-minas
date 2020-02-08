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

import { IAnaliseConsultoria, AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from './analise-consultoria.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { AcaoSGQService } from 'app/entities/sgq/acao-sgq/acao-sgq.service';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IEmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';
import { EmpresaConsultoriaService } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria.service';

type SelectableEntity = IAcaoSGQ | IAnexo | IEmpresaConsultoria;

@Component({
  selector: 'jhi-analise-consultoria-update',
  templateUrl: './analise-consultoria-update.component.html'
})
export class AnaliseConsultoriaUpdateComponent implements OnInit {
  isSaving = false;

  acaos: IAcaoSGQ[] = [];

  anexos: IAnexo[] = [];

  empresaconsultorias: IEmpresaConsultoria[] = [];

  editForm = this.fb.group({
    id: [],
    dataSolicitacaoAnalise: [null, [Validators.required]],
    dataAnalise: [],
    descricao: [null, [Validators.required]],
    responsavelAnalise: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    status: [null, [Validators.required]],
    acao: [null, Validators.required],
    anexo: [],
    empresa: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    protected acaoSGQService: AcaoSGQService,
    protected anexoService: AnexoService,
    protected empresaConsultoriaService: EmpresaConsultoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analiseConsultoria }) => {
      this.updateForm(analiseConsultoria);

      this.acaoSGQService
        .query({ 'analiseConsultoriaId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAcaoSGQ[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAcaoSGQ[]) => {
          if (!analiseConsultoria.acao || !analiseConsultoria.acao.id) {
            this.acaos = resBody;
          } else {
            this.acaoSGQService
              .find(analiseConsultoria.acao.id)
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

      this.anexoService
        .query({ 'analiseConsultoriaId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAnexo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnexo[]) => {
          if (!analiseConsultoria.anexo || !analiseConsultoria.anexo.id) {
            this.anexos = resBody;
          } else {
            this.anexoService
              .find(analiseConsultoria.anexo.id)
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

      this.empresaConsultoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IEmpresaConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEmpresaConsultoria[]) => (this.empresaconsultorias = resBody));
    });
  }

  updateForm(analiseConsultoria: IAnaliseConsultoria): void {
    this.editForm.patchValue({
      id: analiseConsultoria.id,
      dataSolicitacaoAnalise:
        analiseConsultoria.dataSolicitacaoAnalise != null ? analiseConsultoria.dataSolicitacaoAnalise.format(DATE_TIME_FORMAT) : null,
      dataAnalise: analiseConsultoria.dataAnalise != null ? analiseConsultoria.dataAnalise.format(DATE_TIME_FORMAT) : null,
      descricao: analiseConsultoria.descricao,
      responsavelAnalise: analiseConsultoria.responsavelAnalise,
      status: analiseConsultoria.status,
      acao: analiseConsultoria.acao,
      anexo: analiseConsultoria.anexo,
      empresa: analiseConsultoria.empresa
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
    const analiseConsultoria = this.createFromForm();
    if (analiseConsultoria.id !== undefined) {
      this.subscribeToSaveResponse(this.analiseConsultoriaService.update(analiseConsultoria));
    } else {
      this.subscribeToSaveResponse(this.analiseConsultoriaService.create(analiseConsultoria));
    }
  }

  private createFromForm(): IAnaliseConsultoria {
    return {
      ...new AnaliseConsultoria(),
      id: this.editForm.get(['id'])!.value,
      dataSolicitacaoAnalise:
        this.editForm.get(['dataSolicitacaoAnalise'])!.value != null
          ? moment(this.editForm.get(['dataSolicitacaoAnalise'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataAnalise:
        this.editForm.get(['dataAnalise'])!.value != null ? moment(this.editForm.get(['dataAnalise'])!.value, DATE_TIME_FORMAT) : undefined,
      descricao: this.editForm.get(['descricao'])!.value,
      responsavelAnalise: this.editForm.get(['responsavelAnalise'])!.value,
      status: this.editForm.get(['status'])!.value,
      acao: this.editForm.get(['acao'])!.value,
      anexo: this.editForm.get(['anexo'])!.value,
      empresa: this.editForm.get(['empresa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnaliseConsultoria>>): void {
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
