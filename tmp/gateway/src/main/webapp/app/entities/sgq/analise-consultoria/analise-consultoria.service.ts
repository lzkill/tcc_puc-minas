import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';

type EntityResponseType = HttpResponse<IAnaliseConsultoria>;
type EntityArrayResponseType = HttpResponse<IAnaliseConsultoria[]>;

@Injectable({ providedIn: 'root' })
export class AnaliseConsultoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/analise-consultorias';

  constructor(protected http: HttpClient) {}

  create(analiseConsultoria: IAnaliseConsultoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analiseConsultoria);
    return this.http
      .post<IAnaliseConsultoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(analiseConsultoria: IAnaliseConsultoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analiseConsultoria);
    return this.http
      .put<IAnaliseConsultoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnaliseConsultoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnaliseConsultoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(analiseConsultoria: IAnaliseConsultoria): IAnaliseConsultoria {
    const copy: IAnaliseConsultoria = Object.assign({}, analiseConsultoria, {
      dataAnalise:
        analiseConsultoria.dataAnalise && analiseConsultoria.dataAnalise.isValid() ? analiseConsultoria.dataAnalise.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAnalise = res.body.dataAnalise ? moment(res.body.dataAnalise) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((analiseConsultoria: IAnaliseConsultoria) => {
        analiseConsultoria.dataAnalise = analiseConsultoria.dataAnalise ? moment(analiseConsultoria.dataAnalise) : undefined;
      });
    }
    return res;
  }
}
