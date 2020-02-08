import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IAuditoria>(this.resourceUrl, auditoria, { observe: 'response' });
  }

  update(auditoria: IAuditoria): Observable<EntityResponseType> {
    return this.http.put<IAuditoria>(this.resourceUrl, auditoria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuditoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuditoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
