import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

type EntityResponseType = HttpResponse<IItemPlanoAuditoria>;
type EntityArrayResponseType = HttpResponse<IItemPlanoAuditoria[]>;

@Injectable({ providedIn: 'root' })
export class ItemPlanoAuditoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/item-plano-auditorias';

  constructor(protected http: HttpClient) {}

  create(itemPlanoAuditoria: IItemPlanoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemPlanoAuditoria);
    return this.http
      .post<IItemPlanoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemPlanoAuditoria: IItemPlanoAuditoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemPlanoAuditoria);
    return this.http
      .put<IItemPlanoAuditoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemPlanoAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemPlanoAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(itemPlanoAuditoria: IItemPlanoAuditoria): IItemPlanoAuditoria {
    const copy: IItemPlanoAuditoria = Object.assign({}, itemPlanoAuditoria, {
      dataInicio:
        itemPlanoAuditoria.dataInicio && itemPlanoAuditoria.dataInicio.isValid() ? itemPlanoAuditoria.dataInicio.toJSON() : undefined,
      dataFim: itemPlanoAuditoria.dataFim && itemPlanoAuditoria.dataFim.isValid() ? itemPlanoAuditoria.dataFim.toJSON() : undefined
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
      res.body.forEach((itemPlanoAuditoria: IItemPlanoAuditoria) => {
        itemPlanoAuditoria.dataInicio = itemPlanoAuditoria.dataInicio ? moment(itemPlanoAuditoria.dataInicio) : undefined;
        itemPlanoAuditoria.dataFim = itemPlanoAuditoria.dataFim ? moment(itemPlanoAuditoria.dataFim) : undefined;
      });
    }
    return res;
  }
}
