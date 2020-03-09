import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategoriaPublicacao } from 'app/shared/model/sgq/categoria-publicacao.model';

type EntityResponseType = HttpResponse<ICategoriaPublicacao>;
type EntityArrayResponseType = HttpResponse<ICategoriaPublicacao[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaPublicacaoService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/categoria-publicacaos';

  constructor(protected http: HttpClient) {}

  create(categoriaPublicacao: ICategoriaPublicacao): Observable<EntityResponseType> {
    return this.http.post<ICategoriaPublicacao>(this.resourceUrl, categoriaPublicacao, { observe: 'response' });
  }

  update(categoriaPublicacao: ICategoriaPublicacao): Observable<EntityResponseType> {
    return this.http.put<ICategoriaPublicacao>(this.resourceUrl, categoriaPublicacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaPublicacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaPublicacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
