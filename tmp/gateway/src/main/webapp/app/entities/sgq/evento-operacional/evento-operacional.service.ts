import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';

type EntityResponseType = HttpResponse<IEventoOperacional>;
type EntityArrayResponseType = HttpResponse<IEventoOperacional[]>;

@Injectable({ providedIn: 'root' })
export class EventoOperacionalService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/evento-operacionals';

  constructor(protected http: HttpClient) {}

  create(eventoOperacional: IEventoOperacional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventoOperacional);
    return this.http
      .post<IEventoOperacional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eventoOperacional: IEventoOperacional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventoOperacional);
    return this.http
      .put<IEventoOperacional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEventoOperacional>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEventoOperacional[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(eventoOperacional: IEventoOperacional): IEventoOperacional {
    const copy: IEventoOperacional = Object.assign({}, eventoOperacional, {
      dataRegistro:
        eventoOperacional.dataRegistro && eventoOperacional.dataRegistro.isValid() ? eventoOperacional.dataRegistro.toJSON() : undefined,
      dataEvento: eventoOperacional.dataEvento && eventoOperacional.dataEvento.isValid() ? eventoOperacional.dataEvento.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRegistro = res.body.dataRegistro ? moment(res.body.dataRegistro) : undefined;
      res.body.dataEvento = res.body.dataEvento ? moment(res.body.dataEvento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((eventoOperacional: IEventoOperacional) => {
        eventoOperacional.dataRegistro = eventoOperacional.dataRegistro ? moment(eventoOperacional.dataRegistro) : undefined;
        eventoOperacional.dataEvento = eventoOperacional.dataEvento ? moment(eventoOperacional.dataEvento) : undefined;
      });
    }
    return res;
  }
}
