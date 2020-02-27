import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

type EntityResponseType = HttpResponse<ISolicitacaoAnalise>;
type EntityArrayResponseType = HttpResponse<ISolicitacaoAnalise[]>;

@Injectable({ providedIn: 'root' })
export class SolicitacaoAnaliseService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/solicitacao-analises';

  constructor(protected http: HttpClient) {}

  create(solicitacaoAnalise: ISolicitacaoAnalise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(solicitacaoAnalise);
    return this.http
      .post<ISolicitacaoAnalise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(solicitacaoAnalise: ISolicitacaoAnalise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(solicitacaoAnalise);
    return this.http
      .put<ISolicitacaoAnalise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISolicitacaoAnalise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISolicitacaoAnalise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(solicitacaoAnalise: ISolicitacaoAnalise): ISolicitacaoAnalise {
    const copy: ISolicitacaoAnalise = Object.assign({}, solicitacaoAnalise, {
      dataRegistro:
        solicitacaoAnalise.dataRegistro && solicitacaoAnalise.dataRegistro.isValid() ? solicitacaoAnalise.dataRegistro.toJSON() : undefined,
      dataSolicitacao:
        solicitacaoAnalise.dataSolicitacao && solicitacaoAnalise.dataSolicitacao.isValid()
          ? solicitacaoAnalise.dataSolicitacao.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataSolicitacao = res.body.dataSolicitacao ? moment(res.body.dataSolicitacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((solicitacaoAnalise: ISolicitacaoAnalise) => {
        solicitacaoAnalise.dataRegistro = solicitacaoAnalise.dataRegistro ? moment(solicitacaoAnalise.dataRegistro) : undefined;
        solicitacaoAnalise.dataSolicitacao = solicitacaoAnalise.dataSolicitacao ? moment(solicitacaoAnalise.dataSolicitacao) : undefined;
      });
    }
    return res;
  }
}
