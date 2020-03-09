import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBoletimInformativo } from 'app/shared/model/sgq/boletim-informativo.model';

type EntityResponseType = HttpResponse<IBoletimInformativo>;
type EntityArrayResponseType = HttpResponse<IBoletimInformativo[]>;

@Injectable({ providedIn: 'root' })
export class BoletimInformativoService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/boletim-informativos';

  constructor(protected http: HttpClient) {}

  create(boletimInformativo: IBoletimInformativo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimInformativo);
    return this.http
      .post<IBoletimInformativo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(boletimInformativo: IBoletimInformativo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(boletimInformativo);
    return this.http
      .put<IBoletimInformativo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBoletimInformativo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBoletimInformativo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(boletimInformativo: IBoletimInformativo): IBoletimInformativo {
    const copy: IBoletimInformativo = Object.assign({}, boletimInformativo, {
      dataRegistro:
        boletimInformativo.dataRegistro && boletimInformativo.dataRegistro.isValid() ? boletimInformativo.dataRegistro.toJSON() : undefined,
      dataPublicacao:
        boletimInformativo.dataPublicacao && boletimInformativo.dataPublicacao.isValid()
          ? boletimInformativo.dataPublicacao.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataPublicacao = res.body.dataPublicacao ? moment(res.body.dataPublicacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((boletimInformativo: IBoletimInformativo) => {
        boletimInformativo.dataRegistro = boletimInformativo.dataRegistro ? moment(boletimInformativo.dataRegistro) : undefined;
        boletimInformativo.dataPublicacao = boletimInformativo.dataPublicacao ? moment(boletimInformativo.dataPublicacao) : undefined;
      });
    }
    return res;
  }
}
