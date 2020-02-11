import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ResultadoAuditoriaService } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.service';
import { IResultadoAuditoria, ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

describe('Service Tests', () => {
  describe('ResultadoAuditoria Service', () => {
    let injector: TestBed;
    let service: ResultadoAuditoriaService;
    let httpMock: HttpTestingController;
    let elemDefault: IResultadoAuditoria;
    let expectedResult: IResultadoAuditoria | IResultadoAuditoria[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResultadoAuditoriaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ResultadoAuditoria(0, 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ResultadoAuditoria', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataFim: currentDate
          },
          returnedFromService
        );
        service
          .create(new ResultadoAuditoria())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResultadoAuditoria', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioResponsavel: 1,
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT),
            descricao: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataFim: currentDate
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

      it('should return a list of ResultadoAuditoria', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioResponsavel: 1,
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT),
            descricao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicio: currentDate,
            dataFim: currentDate
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

      it('should delete a ResultadoAuditoria', () => {
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
