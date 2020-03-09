import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFeed } from 'app/shared/model/sgq/feed.model';

type EntityResponseType = HttpResponse<IFeed>;
type EntityArrayResponseType = HttpResponse<IFeed[]>;

@Injectable({ providedIn: 'root' })
export class FeedService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/feeds';

  constructor(protected http: HttpClient) {}

  create(feed: IFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feed);
    return this.http
      .post<IFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(feed: IFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feed);
    return this.http
      .put<IFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFeed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFeed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(feed: IFeed): IFeed {
    const copy: IFeed = Object.assign({}, feed, {
      dataRegistro: feed.dataRegistro && feed.dataRegistro.isValid() ? feed.dataRegistro.toJSON() : undefined
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
      res.body.forEach((feed: IFeed) => {
        feed.dataRegistro = feed.dataRegistro ? moment(feed.dataRegistro) : undefined;
      });
    }
    return res;
  }
}
