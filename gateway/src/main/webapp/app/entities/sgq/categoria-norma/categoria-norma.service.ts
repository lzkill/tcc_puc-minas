import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';

type EntityResponseType = HttpResponse<ICategoriaNorma>;
type EntityArrayResponseType = HttpResponse<ICategoriaNorma[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaNormaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/categoria-normas';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaNorma>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaNorma[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
