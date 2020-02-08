import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';

type EntityResponseType = HttpResponse<INaoConformidade>;
type EntityArrayResponseType = HttpResponse<INaoConformidade[]>;

@Injectable({ providedIn: 'root' })
export class NaoConformidadeService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/nao-conformidades';

  constructor(protected http: HttpClient) {}

  create(naoConformidade: INaoConformidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(naoConformidade);
    return this.http
      .post<INaoConformidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(naoConformidade: INaoConformidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(naoConformidade);
    return this.http
      .put<INaoConformidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INaoConformidade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INaoConformidade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(naoConformidade: INaoConformidade): INaoConformidade {
    const copy: INaoConformidade = Object.assign({}, naoConformidade, {
      prazoConclusao:
        naoConformidade.prazoConclusao && naoConformidade.prazoConclusao.isValid() ? naoConformidade.prazoConclusao.toJSON() : undefined,
      novoPrazoConclusao:
        naoConformidade.novoPrazoConclusao && naoConformidade.novoPrazoConclusao.isValid()
          ? naoConformidade.novoPrazoConclusao.toJSON()
          : undefined,
      dataRegistro:
        naoConformidade.dataRegistro && naoConformidade.dataRegistro.isValid() ? naoConformidade.dataRegistro.toJSON() : undefined,
      dataConclusao:
        naoConformidade.dataConclusao && naoConformidade.dataConclusao.isValid() ? naoConformidade.dataConclusao.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.prazoConclusao = res.body.prazoConclusao ? moment(res.body.prazoConclusao) : undefined;
      res.body.novoPrazoConclusao = res.body.novoPrazoConclusao ? moment(res.body.novoPrazoConclusao) : undefined;
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataConclusao = res.body.dataConclusao ? moment(res.body.dataConclusao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((naoConformidade: INaoConformidade) => {
        naoConformidade.prazoConclusao = naoConformidade.prazoConclusao ? moment(naoConformidade.prazoConclusao) : undefined;
        naoConformidade.novoPrazoConclusao = naoConformidade.novoPrazoConclusao ? moment(naoConformidade.novoPrazoConclusao) : undefined;
        naoConformidade.dataRegistro = naoConformidade.dataRegistro ? moment(naoConformidade.dataRegistro) : undefined;
        naoConformidade.dataConclusao = naoConformidade.dataConclusao ? moment(naoConformidade.dataConclusao) : undefined;
      });
    }
    return res;
  }
}
