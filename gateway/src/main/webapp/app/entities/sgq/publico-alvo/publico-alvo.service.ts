import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublicoAlvo } from 'app/shared/model/sgq/publico-alvo.model';

type EntityResponseType = HttpResponse<IPublicoAlvo>;
type EntityArrayResponseType = HttpResponse<IPublicoAlvo[]>;

@Injectable({ providedIn: 'root' })
export class PublicoAlvoService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/publico-alvos';

  constructor(protected http: HttpClient) {}

  create(publicoAlvo: IPublicoAlvo): Observable<EntityResponseType> {
    return this.http.post<IPublicoAlvo>(this.resourceUrl, publicoAlvo, { observe: 'response' });
  }

  update(publicoAlvo: IPublicoAlvo): Observable<EntityResponseType> {
    return this.http.put<IPublicoAlvo>(this.resourceUrl, publicoAlvo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPublicoAlvo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPublicoAlvo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
