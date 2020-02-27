import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsultoria } from 'app/shared/model/sgq/consultoria.model';

type EntityResponseType = HttpResponse<IConsultoria>;
type EntityArrayResponseType = HttpResponse<IConsultoria[]>;

@Injectable({ providedIn: 'root' })
export class ConsultoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/consultorias';

  constructor(protected http: HttpClient) {}

  create(consultoria: IConsultoria): Observable<EntityResponseType> {
    return this.http.post<IConsultoria>(this.resourceUrl, consultoria, { observe: 'response' });
  }

  update(consultoria: IConsultoria): Observable<EntityResponseType> {
    return this.http.put<IConsultoria>(this.resourceUrl, consultoria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConsultoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConsultoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
