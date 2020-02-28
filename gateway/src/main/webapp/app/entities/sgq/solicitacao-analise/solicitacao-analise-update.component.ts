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
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';
import { ConsultoriaService } from 'app/entities/sgq/consultoria/consultoria.service';

import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = INaoConformidade | IAnaliseConsultoria | IConsultoria;

@Component({
  selector: 'jhi-solicitacao-analise-update',
  templateUrl: './solicitacao-analise-update.component.html'
})
export class SolicitacaoAnaliseUpdateComponent implements OnInit {
  isSaving = false;
  naoconformidades: INaoConformidade[] = [];
  analiseconsultorias: IAnaliseConsultoria[] = [];
  consultorias: IConsultoria[] = [];
  usuarios: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    idUsuarioRegistro: [null, [Validators.required]],
    dataRegistro: [null, [Validators.required]],
    dataSolicitacao: [],
    uuid: [null, []],
    status: [null, [Validators.required]],
    naoConformidade: [null, Validators.required],
    analiseConsultoria: [],
    consultoria: [null, Validators.required]
  });

  constructor(
    protected solicitacaoAnaliseService: SolicitacaoAnaliseService,
    protected naoConformidadeService: NaoConformidadeService,
    protected analiseConsultoriaService: AnaliseConsultoriaService,
    protected consultoriaService: ConsultoriaService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solicitacaoAnalise }) => {
      this.updateForm(solicitacaoAnalise);

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

      this.consultoriaService
        .query()
        .pipe(
          map((res: HttpResponse<IConsultoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IConsultoria[]) => (this.consultorias = resBody));

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) =>
          resBody.forEach(item => {
            if (!this.userService.isAdmin(item)) this.usuarios.push(item);
          })
        );
    });
  }

  updateForm(solicitacaoAnalise: ISolicitacaoAnalise): void {
    this.editForm.patchValue({
      id: solicitacaoAnalise.id,
      idUsuarioRegistro: solicitacaoAnalise.idUsuarioRegistro,
      dataRegistro: solicitacaoAnalise.dataRegistro != null ? solicitacaoAnalise.dataRegistro.format(DATE_TIME_FORMAT) : null,
      dataSolicitacao: solicitacaoAnalise.dataSolicitacao != null ? solicitacaoAnalise.dataSolicitacao.format(DATE_TIME_FORMAT) : null,
      uuid: solicitacaoAnalise.uuid,
      status: solicitacaoAnalise.status,
      naoConformidade: solicitacaoAnalise.naoConformidade,
      analiseConsultoria: solicitacaoAnalise.analiseConsultoria,
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
      uuid: this.editForm.get(['uuid'])!.value,
      status: this.editForm.get(['status'])!.value,
      naoConformidade: this.editForm.get(['naoConformidade'])!.value,
      analiseConsultoria: this.editForm.get(['analiseConsultoria'])!.value,
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
