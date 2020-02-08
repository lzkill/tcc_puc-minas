import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoAuditoria } from 'app/shared/model/sgq/plano-auditoria.model';

type EntityResponseType = HttpResponse<IPlanoAuditoria>;
type EntityArrayResponseType = HttpResponse<IPlanoAuditoria[]>;

@Injectable({ providedIn: 'root' })
export class PlanoAuditoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/plano-auditorias';

  constructor(protected http: HttpClient) {}

  create(planoAuditoria: IPlanoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoAuditoria);
    return this.http
      .post<IPlanoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planoAuditoria: IPlanoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planoAuditoria);
    return this.http
      .put<IPlanoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanoAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanoAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planoAuditoria: IPlanoAuditoria): IPlanoAuditoria {
    const copy: IPlanoAuditoria = Object.assign({}, planoAuditoria, {
      dataInicio: planoAuditoria.dataInicio && planoAuditoria.dataInicio.isValid() ? planoAuditoria.dataInicio.toJSON() : undefined,
      dataFim: planoAuditoria.dataFim && planoAuditoria.dataFim.isValid() ? planoAuditoria.dataFim.toJSON() : undefined
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
      res.body.forEach((planoAuditoria: IPlanoAuditoria) => {
        planoAuditoria.dataInicio = planoAuditoria.dataInicio ? moment(planoAuditoria.dataInicio) : undefined;
        planoAuditoria.dataFim = planoAuditoria.dataFim ? moment(planoAuditoria.dataFim) : undefined;
      });
    }
    return res;
  }
}
