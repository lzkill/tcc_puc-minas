import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IChecklist } from 'app/shared/model/sgq/checklist.model';

type EntityResponseType = HttpResponse<IChecklist>;
type EntityArrayResponseType = HttpResponse<IChecklist[]>;

@Injectable({ providedIn: 'root' })
export class ChecklistService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/checklists';

  constructor(protected http: HttpClient) {}

  create(checklist: IChecklist): Observable<EntityResponseType> {
    return this.http.post<IChecklist>(this.resourceUrl, checklist, { observe: 'response' });
  }

  update(checklist: IChecklist): Observable<EntityResponseType> {
    return this.http.put<IChecklist>(this.resourceUrl, checklist, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChecklist>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChecklist[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
