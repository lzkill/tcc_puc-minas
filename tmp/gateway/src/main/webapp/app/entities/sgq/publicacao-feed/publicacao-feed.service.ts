import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';

type EntityResponseType = HttpResponse<IPublicacaoFeed>;
type EntityArrayResponseType = HttpResponse<IPublicacaoFeed[]>;

@Injectable({ providedIn: 'root' })
export class PublicacaoFeedService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/publicacao-feeds';

  constructor(protected http: HttpClient) {}

  create(publicacaoFeed: IPublicacaoFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacaoFeed);
    return this.http
      .post<IPublicacaoFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(publicacaoFeed: IPublicacaoFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacaoFeed);
    return this.http
      .put<IPublicacaoFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPublicacaoFeed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPublicacaoFeed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(publicacaoFeed: IPublicacaoFeed): IPublicacaoFeed {
    const copy: IPublicacaoFeed = Object.assign({}, publicacaoFeed, {
      dataRegistro: publicacaoFeed.dataRegistro && publicacaoFeed.dataRegistro.isValid() ? publicacaoFeed.dataRegistro.toJSON() : undefined,
      dataPublicacao:
        publicacaoFeed.dataPublicacao && publicacaoFeed.dataPublicacao.isValid() ? publicacaoFeed.dataPublicacao.toJSON() : undefined
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
      res.body.forEach((publicacaoFeed: IPublicacaoFeed) => {
        publicacaoFeed.dataRegistro = publicacaoFeed.dataRegistro ? moment(publicacaoFeed.dataRegistro) : undefined;
        publicacaoFeed.dataPublicacao = publicacaoFeed.dataPublicacao ? moment(publicacaoFeed.dataPublicacao) : undefined;
      });
    }
    return res;
  }
}
