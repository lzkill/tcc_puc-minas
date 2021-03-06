import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PublicacaoFeedService } from 'app/entities/sgq/publicacao-feed/publicacao-feed.service';
import { IPublicacaoFeed, PublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';
import { StatusPublicacao } from 'app/shared/model/enumerations/status-publicacao.model';

describe('Service Tests', () => {
  describe('PublicacaoFeed Service', () => {
    let injector: TestBed;
    let service: PublicacaoFeedService;
    let httpMock: HttpTestingController;
    let elemDefault: IPublicacaoFeed;
    let expectedResult: IPublicacaoFeed | IPublicacaoFeed[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PublicacaoFeedService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PublicacaoFeed(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        StatusPublicacao.RASCUNHO
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataPublicacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PublicacaoFeed', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataPublicacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataPublicacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new PublicacaoFeed())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PublicacaoFeed', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            titulo: 'BBBBBB',
            autor: 'BBBBBB',
            uri: 'BBBBBB',
            link: 'BBBBBB',
            conteudo: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataPublicacao: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataPublicacao: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PublicacaoFeed', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            titulo: 'BBBBBB',
            autor: 'BBBBBB',
            uri: 'BBBBBB',
            link: 'BBBBBB',
            conteudo: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataPublicacao: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataPublicacao: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PublicacaoFeed', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
