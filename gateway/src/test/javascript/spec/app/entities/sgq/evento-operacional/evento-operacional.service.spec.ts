import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EventoOperacionalService } from 'app/entities/sgq/evento-operacional/evento-operacional.service';
import { IEventoOperacional, EventoOperacional } from 'app/shared/model/sgq/evento-operacional.model';
import { TipoEventoOperacional } from 'app/shared/model/enumerations/tipo-evento-operacional.model';

describe('Service Tests', () => {
  describe('EventoOperacional Service', () => {
    let injector: TestBed;
    let service: EventoOperacionalService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventoOperacional;
    let expectedResult: IEventoOperacional | IEventoOperacional[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventoOperacionalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EventoOperacional(0, 0, TipoEventoOperacional.PROGRAMADO, 'AAAAAAA', 'AAAAAAA', currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataEvento: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a EventoOperacional', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataEvento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataEvento: currentDate
          },
          returnedFromService
        );
        service
          .create(new EventoOperacional())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EventoOperacional', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            tipo: 'BBBBBB',
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            dataEvento: currentDate.format(DATE_TIME_FORMAT),
            duracao: 'BBBBBB',
            houveParadaProducao: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataEvento: currentDate
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

      it('should return a list of EventoOperacional', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            tipo: 'BBBBBB',
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            dataEvento: currentDate.format(DATE_TIME_FORMAT),
            duracao: 'BBBBBB',
            houveParadaProducao: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataEvento: currentDate
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

      it('should delete a EventoOperacional', () => {
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
