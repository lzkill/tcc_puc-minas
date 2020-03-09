import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemChecklist } from 'app/shared/model/sgq/item-checklist.model';

type EntityResponseType = HttpResponse<IItemChecklist>;
type EntityArrayResponseType = HttpResponse<IItemChecklist[]>;

@Injectable({ providedIn: 'root' })
export class ItemChecklistService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/item-checklists';

  constructor(protected http: HttpClient) {}

  create(itemChecklist: IItemChecklist): Observable<EntityResponseType> {
    return this.http.post<IItemChecklist>(this.resourceUrl, itemChecklist, { observe: 'response' });
  }

  update(itemChecklist: IItemChecklist): Observable<EntityResponseType> {
    return this.http.put<IItemChecklist>(this.resourceUrl, itemChecklist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemChecklist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemChecklist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
