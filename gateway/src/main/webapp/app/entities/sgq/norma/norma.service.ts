import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INorma } from 'app/shared/model/sgq/norma.model';

type EntityResponseType = HttpResponse<INorma>;
type EntityArrayResponseType = HttpResponse<INorma[]>;

@Injectable({ providedIn: 'root' })
export class NormaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/normas';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INorma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INorma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(norma: INorma): INorma {
    const copy: INorma = Object.assign({}, norma, {
      dataEdicao: norma.dataEdicao && norma.dataEdicao.isValid() ? norma.dataEdicao.toJSON() : undefined,
      dataInicioValidade: norma.dataInicioValidade && norma.dataInicioValidade.isValid() ? norma.dataInicioValidade.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataEdicao = res.body.dataEdicao ? moment(res.body.dataEdicao) : undefined;
      res.body.dataInicioValidade = res.body.dataInicioValidade ? moment(res.body.dataInicioValidade) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((norma: INorma) => {
        norma.dataEdicao = norma.dataEdicao ? moment(norma.dataEdicao) : undefined;
        norma.dataInicioValidade = norma.dataInicioValidade ? moment(norma.dataInicioValidade) : undefined;
      });
    }
    return res;
  }
}
