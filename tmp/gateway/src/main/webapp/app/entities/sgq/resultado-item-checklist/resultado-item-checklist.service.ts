import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResultadoItemChecklist } from 'app/shared/model/sgq/resultado-item-checklist.model';

type EntityResponseType = HttpResponse<IResultadoItemChecklist>;
type EntityArrayResponseType = HttpResponse<IResultadoItemChecklist[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoItemChecklistService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/resultado-item-checklists';

  constructor(protected http: HttpClient) {}

  create(resultadoItemChecklist: IResultadoItemChecklist): Observable<EntityResponseType> {
    return this.http.post<IResultadoItemChecklist>(this.resourceUrl, resultadoItemChecklist, { observe: 'response' });
  }

  update(resultadoItemChecklist: IResultadoItemChecklist): Observable<EntityResponseType> {
    return this.http.put<IResultadoItemChecklist>(this.resourceUrl, resultadoItemChecklist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultadoItemChecklist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultadoItemChecklist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
