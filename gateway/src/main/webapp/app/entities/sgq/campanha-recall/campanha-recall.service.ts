import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICampanhaRecall } from 'app/shared/model/sgq/campanha-recall.model';

type EntityResponseType = HttpResponse<ICampanhaRecall>;
type EntityArrayResponseType = HttpResponse<ICampanhaRecall[]>;

@Injectable({ providedIn: 'root' })
export class CampanhaRecallService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/campanha-recalls';

  constructor(protected http: HttpClient) {}

  create(campanhaRecall: ICampanhaRecall): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(campanhaRecall);
    return this.http
      .post<ICampanhaRecall>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(campanhaRecall: ICampanhaRecall): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(campanhaRecall);
    return this.http
      .put<ICampanhaRecall>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICampanhaRecall>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICampanhaRecall[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(campanhaRecall: ICampanhaRecall): ICampanhaRecall {
    const copy: ICampanhaRecall = Object.assign({}, campanhaRecall, {
      dataRegistro: campanhaRecall.dataRegistro && campanhaRecall.dataRegistro.isValid() ? campanhaRecall.dataRegistro.toJSON() : undefined,
      dataInicio: campanhaRecall.dataInicio && campanhaRecall.dataInicio.isValid() ? campanhaRecall.dataInicio.toJSON() : undefined,
      dataFim: campanhaRecall.dataFim && campanhaRecall.dataFim.isValid() ? campanhaRecall.dataFim.toJSON() : undefined,
      dataPublicacao:
        campanhaRecall.dataPublicacao && campanhaRecall.dataPublicacao.isValid() ? campanhaRecall.dataPublicacao.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataInicio = res.body.dataInicio ? moment(res.body.dataInicio) : undefined;
      res.body.dataFim = res.body.dataFim ? moment(res.body.dataFim) : undefined;
      res.body.dataPublicacao = res.body.dataPublicacao ? moment(res.body.dataPublicacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((campanhaRecall: ICampanhaRecall) => {
        campanhaRecall.dataRegistro = campanhaRecall.dataRegistro ? moment(campanhaRecall.dataRegistro) : undefined;
        campanhaRecall.dataInicio = campanhaRecall.dataInicio ? moment(campanhaRecall.dataInicio) : undefined;
        campanhaRecall.dataFim = campanhaRecall.dataFim ? moment(campanhaRecall.dataFim) : undefined;
        campanhaRecall.dataPublicacao = campanhaRecall.dataPublicacao ? moment(campanhaRecall.dataPublicacao) : undefined;
      });
    }
    return res;
  }
}
