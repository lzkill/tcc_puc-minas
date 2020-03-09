import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISolicitacaoAnalise, SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';
import { SolicitacaoAnaliseService } from './solicitacao-analise.service';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from 'app/entities/sgq/consultoria/consultoria.service';

type SelectableEntity = IAnaliseConsultoria | INaoConformidade | IConsultoria;

@Component({
  selector: 'jhi-solicitacao-analise-update',
  templateUrl: './solicitacao-analise-update.component.html'
})
export class SolicitacaoAnaliseUpdateComponent implements OnInit {
  isSaving = false;

  analiseconsultorias: IAnaliseConsultoria[] = [];

  naoconformidades: INaoConformidade[] = [];

  consultorias: IConsultoria[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    dataSolicitacao: [],
    idAcompanhamento: [],
    status: [null, [Validators.required]],
    analiseConsultoria: [],
    naoConformidade: [null, Validators.required],
    consultoria: [null, Validators.required]
  });

  constructor(
    protected solicitacaoAnaliseService: SolicitacaoAnaliseService,
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    protected naoConformidadeService: NaoConformidadeService,
    protected consultoriaService: ConsultoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicitacaoAnalise }) => {
      this.updateForm(solicitacaoAnalise);

      this.analiseConsultoriaService
        .query({ 'solicitacaoAnaliseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAnaliseConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAnaliseConsultoria[]) => {
          if (!solicitacaoAnalise.analiseConsultoria || !solicitacaoAnalise.analiseConsultoria.id) {
            this.analiseconsultorias = resBody;
          } else {
            this.analiseConsultoriaService
              .find(solicitacaoAnalise.analiseConsultoria.id)
              .pipe(
                map((subRes: HttpResponse<IAnaliseConsultoria>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAnaliseConsultoria[]) => {
                this.analiseconsultorias = concatRes;
              });
          }
        });

      this.naoConformidadeService
        .query({ 'solicitacaoAnaliseId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<INaoConformidade[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INaoConformidade[]) => {
          if (!solicitacaoAnalise.naoConformidade || !solicitacaoAnalise.naoConformidade.id) {
            this.naoconformidades = resBody;
          } else {
            this.naoConformidadeService
              .find(solicitacaoAnalise.naoConformidade.id)
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

      this.consultoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConsultoria[]) => (this.consultorias = resBody));
    });
  }

  updateForm(solicitacaoAnalise: ISolicitacaoAnalise): void {
    this.editForm.patchValue({
      id: solicitacaoAnalise.id,
      idUsuarioRegistro: solicitacaoAnalise.idUsuarioRegistro,
      dataRegistro: solicitacaoAnalise.dataRegistro != null ? solicitacaoAnalise.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataSolicitacao: solicitacaoAnalise.dataSolicitacao != null ? solicitacaoAnalise.dataSolicitacao.format(DATE_TIME_FORMAT) : null,
      idAcompanhamento: solicitacaoAnalise.idAcompanhamento,
      status: solicitacaoAnalise.status,
      analiseConsultoria: solicitacaoAnalise.analiseConsultoria,
      naoConformidade: solicitacaoAnalise.naoConformidade,
      consultoria: solicitacaoAnalise.consultoria
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const solicitacaoAnalise = this.createFromForm();
    if (solicitacaoAnalise.id !== undefined) {
      this.subscribeToSaveResponse(this.solicitacaoAnaliseService.update(solicitacaoAnalise));
    } else {
      this.subscribeToSaveResponse(this.solicitacaoAnaliseService.create(solicitacaoAnalise));
    }
  }

  private createFromForm(): ISolicitacaoAnalise {
    return {
      ...new SolicitacaoAnalise(),
      id: this.editForm.get(['id'])!.value,
      idUsuarioRegistro: this.editForm.get(['idUsuarioRegistro'])!.value,
      dataRegistro:
        this.editForm.get(['dataRegistro'])!.value != null
          ? moment(this.editForm.get(['dataRegistro'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataSolicitacao:
        this.editForm.get(['dataSolicitacao'])!.value != null
          ? moment(this.editForm.get(['dataSolicitacao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      idAcompanhamento: this.editForm.get(['idAcompanhamento'])!.value,
      status: this.editForm.get(['status'])!.value,
      analiseConsultoria: this.editForm.get(['analiseConsultoria'])!.value,
      naoConformidade: this.editForm.get(['naoConformidade'])!.value,
      consultoria: this.editForm.get(['consultoria'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISolicitacaoAnalise>>): void {
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
