import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

type EntityResponseType = HttpResponse<IResultadoAuditoria>;
type EntityArrayResponseType = HttpResponse<IResultadoAuditoria[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoAuditoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/resultado-auditorias';

  constructor(protected http: HttpClient) {}

  create(resultadoAuditoria: IResultadoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultadoAuditoria);
    return this.http
      .post<IResultadoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resultadoAuditoria: IResultadoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultadoAuditoria);
    return this.http
      .put<IResultadoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResultadoAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResultadoAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resultadoAuditoria: IResultadoAuditoria): IResultadoAuditoria {
    const copy: IResultadoAuditoria = Object.assign({}, resultadoAuditoria, {
      dataInicio:
        resultadoAuditoria.dataInicio && resultadoAuditoria.dataInicio.isValid() ? resultadoAuditoria.dataInicio.toJSON() : undefined,
      dataFim: resultadoAuditoria.dataFim && resultadoAuditoria.dataFim.isValid() ? resultadoAuditoria.dataFim.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicio = res.body.dataInicio ? moment(res.body.dataInicio) : undefined;
      res.body.dataFim = res.body.dataFim ? moment(res.body.dataFim) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resultadoAuditoria: IResultadoAuditoria) => {
        resultadoAuditoria.dataInicio = resultadoAuditoria.dataInicio ? moment(resultadoAuditoria.dataInicio) : undefined;
        resultadoAuditoria.dataFim = resultadoAuditoria.dataFim ? moment(resultadoAuditoria.dataFim) : undefined;
      });
    }
    return res;
  }
}
