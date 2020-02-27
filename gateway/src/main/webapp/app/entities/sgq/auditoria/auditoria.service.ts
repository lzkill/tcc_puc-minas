import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditoria } from 'app/shared/model/sgq/auditoria.model';

type EntityResponseType = HttpResponse<IAuditoria>;
type EntityArrayResponseType = HttpResponse<IAuditoria[]>;

@Injectable({ providedIn: 'root' })
export class AuditoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/auditorias';

  constructor(protected http: HttpClient) {}

  create(auditoria: IAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoria);
    return this.http
      .post<IAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditoria: IAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoria);
    return this.http
      .put<IAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditoria: IAuditoria): IAuditoria {
    const copy: IAuditoria = Object.assign({}, auditoria, {
      dataRegistro: auditoria.dataRegistro && auditoria.dataRegistro.isValid() ? auditoria.dataRegistro.toJSON() : undefined,
      dataInicio: auditoria.dataInicio && auditoria.dataInicio.isValid() ? auditoria.dataInicio.toJSON() : undefined,
      dataFim: auditoria.dataFim && auditoria.dataFim.isValid() ? auditoria.dataFim.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataInicio = res.body.dataInicio ? moment(res.body.dataInicio) : undefined;
      res.body.dataFim = res.body.dataFim ? moment(res.body.dataFim) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditoria: IAuditoria) => {
        auditoria.dataRegistro = auditoria.dataRegistro ? moment(auditoria.dataRegistro) : undefined;
        auditoria.dataInicio = auditoria.dataInicio ? moment(auditoria.dataInicio) : undefined;
        auditoria.dataFim = auditoria.dataFim ? moment(auditoria.dataFim) : undefined;
      });
    }
    return res;
  }
}
