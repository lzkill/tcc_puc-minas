import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IPlanoAuditoria>(this.resourceUrl, planoAuditoria, { observe: 'response' });
  }

  update(planoAuditoria: IPlanoAuditoria): Observable<EntityResponseType> {
    return this.http.put<IPlanoAuditoria>(this.resourceUrl, planoAuditoria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
