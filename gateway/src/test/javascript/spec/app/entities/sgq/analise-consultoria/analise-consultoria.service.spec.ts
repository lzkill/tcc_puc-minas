import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { IAnaliseConsultoria, AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';
import { StatusAnaliseExterna } from 'app/shared/model/enumerations/status-analise-externa.model';

describe('Service Tests', () => {
  describe('AnaliseConsultoria Service', () => {
    let injector: TestBed;
    let service: AnaliseConsultoriaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnaliseConsultoria;
    let expectedResult: IAnaliseConsultoria | IAnaliseConsultoria[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnaliseConsultoriaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnaliseConsultoria(0, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', StatusAnaliseExterna.PENDENTE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate.format(DATE_TIME_FORMAT),
            dataAnalise: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a AnaliseConsultoria', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataSolicitacaoAnalise: currentDate.format(DATE_TIME_FORMAT),
            dataAnalise: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate,
            dataAnalise: currentDate
          },
          returnedFromService
        );
        service
          .create(new AnaliseConsultoria())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnaliseConsultoria', () => {
        const returnedFromService = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate.format(DATE_TIME_FORMAT),
            dataAnalise: currentDate.format(DATE_TIME_FORMAT),
            descricao: 'BBBBBB',
            responsavelAnalise: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate,
            dataAnalise: currentDate
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

      it('should return a list of AnaliseConsultoria', () => {
        const returnedFromService = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate.format(DATE_TIME_FORMAT),
            dataAnalise: currentDate.format(DATE_TIME_FORMAT),
            descricao: 'BBBBBB',
            responsavelAnalise: 'BBBBBB',
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataSolicitacaoAnalise: currentDate,
            dataAnalise: currentDate
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

      it('should delete a AnaliseConsultoria', () => {
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
