import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnexo } from 'app/shared/model/sgq/anexo.model';

type EntityResponseType = HttpResponse<IAnexo>;
type EntityArrayResponseType = HttpResponse<IAnexo[]>;

@Injectable({ providedIn: 'root' })
export class AnexoService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/anexos';

  constructor(protected http: HttpClient) {}

  create(anexo: IAnexo): Observable<EntityResponseType> {
    return this.http.post<IAnexo>(this.resourceUrl, anexo, { observe: 'response' });
  }

  update(anexo: IAnexo): Observable<EntityResponseType> {
    return this.http.put<IAnexo>(this.resourceUrl, anexo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
