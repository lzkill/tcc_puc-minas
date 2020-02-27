import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

type EntityResponseType = HttpResponse<IResultadoChecklist>;
type EntityArrayResponseType = HttpResponse<IResultadoChecklist[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoChecklistService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/resultado-checklists';

  constructor(protected http: HttpClient) {}

  create(resultadoChecklist: IResultadoChecklist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultadoChecklist);
    return this.http
      .post<IResultadoChecklist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resultadoChecklist: IResultadoChecklist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultadoChecklist);
    return this.http
      .put<IResultadoChecklist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResultadoChecklist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResultadoChecklist[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resultadoChecklist: IResultadoChecklist): IResultadoChecklist {
    const copy: IResultadoChecklist = Object.assign({}, resultadoChecklist, {
      dataRegistro:
        resultadoChecklist.dataRegistro && resultadoChecklist.dataRegistro.isValid() ? resultadoChecklist.dataRegistro.toJSON() : undefined,
      dataVerificacao:
        resultadoChecklist.dataVerificacao && resultadoChecklist.dataVerificacao.isValid()
          ? resultadoChecklist.dataVerificacao.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataVerificacao = res.body.dataVerificacao ? moment(res.body.dataVerificacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resultadoChecklist: IResultadoChecklist) => {
        resultadoChecklist.dataRegistro = resultadoChecklist.dataRegistro ? moment(resultadoChecklist.dataRegistro) : undefined;
        resultadoChecklist.dataVerificacao = resultadoChecklist.dataVerificacao ? moment(resultadoChecklist.dataVerificacao) : undefined;
      });
    }
    return res;
  }
}
