import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';

type EntityResponseType = HttpResponse<IAcaoSGQ>;
type EntityArrayResponseType = HttpResponse<IAcaoSGQ[]>;

@Injectable({ providedIn: 'root' })
export class AcaoSGQService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/acao-sgqs';

  constructor(protected http: HttpClient) {}

  create(acaoSGQ: IAcaoSGQ): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acaoSGQ);
    return this.http
      .post<IAcaoSGQ>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(acaoSGQ: IAcaoSGQ): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(acaoSGQ);
    return this.http
      .put<IAcaoSGQ>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAcaoSGQ>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAcaoSGQ[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(acaoSGQ: IAcaoSGQ): IAcaoSGQ {
    const copy: IAcaoSGQ = Object.assign({}, acaoSGQ, {
      prazoConclusao: acaoSGQ.prazoConclusao && acaoSGQ.prazoConclusao.isValid() ? acaoSGQ.prazoConclusao.toJSON() : undefined,
      novoPrazoConclusao:
        acaoSGQ.novoPrazoConclusao && acaoSGQ.novoPrazoConclusao.isValid() ? acaoSGQ.novoPrazoConclusao.toJSON() : undefined,
      dataRegistro: acaoSGQ.dataRegistro && acaoSGQ.dataRegistro.isValid() ? acaoSGQ.dataRegistro.toJSON() : undefined,
      dataConclusao: acaoSGQ.dataConclusao && acaoSGQ.dataConclusao.isValid() ? acaoSGQ.dataConclusao.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.prazoConclusao = res.body.prazoConclusao ? moment(res.body.prazoConclusao) : undefined;
      res.body.novoPrazoConclusao = res.body.novoPrazoConclusao ? moment(res.body.novoPrazoConclusao) : undefined;
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataConclusao = res.body.dataConclusao ? moment(res.body.dataConclusao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((acaoSGQ: IAcaoSGQ) => {
        acaoSGQ.prazoConclusao = acaoSGQ.prazoConclusao ? moment(acaoSGQ.prazoConclusao) : undefined;
        acaoSGQ.novoPrazoConclusao = acaoSGQ.novoPrazoConclusao ? moment(acaoSGQ.novoPrazoConclusao) : undefined;
        acaoSGQ.dataRegistro = acaoSGQ.dataRegistro ? moment(acaoSGQ.dataRegistro) : undefined;
        acaoSGQ.dataConclusao = acaoSGQ.dataConclusao ? moment(acaoSGQ.dataConclusao) : undefined;
      });
    }
    return res;
  }
}
