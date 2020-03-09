import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
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
    const copy = this.convertDateFromClient(anexo);
    return this.http
      .post<IAnexo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anexo: IAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexo);
    return this.http
      .put<IAnexo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnexo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnexo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anexo: IAnexo): IAnexo {
    const copy: IAnexo = Object.assign({}, anexo, {
      dataRegistro: anexo.dataRegistro && anexo.dataRegistro.isValid() ? anexo.dataRegistro.toJSON() : undefined
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
      res.body.forEach((anexo: IAnexo) => {
        anexo.dataRegistro = anexo.dataRegistro ? moment(anexo.dataRegistro) : undefined;
      });
    }
    return res;
  }
}
