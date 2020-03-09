import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

type EntityResponseType = HttpResponse<IProdutoNaoConforme>;
type EntityArrayResponseType = HttpResponse<IProdutoNaoConforme[]>;

@Injectable({ providedIn: 'root' })
export class ProdutoNaoConformeService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/produto-nao-conformes';

  constructor(protected http: HttpClient) {}

  create(produtoNaoConforme: IProdutoNaoConforme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produtoNaoConforme);
    return this.http
      .post<IProdutoNaoConforme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(produtoNaoConforme: IProdutoNaoConforme): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produtoNaoConforme);
    return this.http
      .put<IProdutoNaoConforme>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProdutoNaoConforme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProdutoNaoConforme[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(produtoNaoConforme: IProdutoNaoConforme): IProdutoNaoConforme {
    const copy: IProdutoNaoConforme = Object.assign({}, produtoNaoConforme, {
      dataRegistro:
        produtoNaoConforme.dataRegistro && produtoNaoConforme.dataRegistro.isValid() ? produtoNaoConforme.dataRegistro.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((produtoNaoConforme: IProdutoNaoConforme) => {
        produtoNaoConforme.dataRegistro = produtoNaoConforme.dataRegistro ? moment(produtoNaoConforme.dataRegistro) : undefined;
      });
    }
    return res;
  }
}
