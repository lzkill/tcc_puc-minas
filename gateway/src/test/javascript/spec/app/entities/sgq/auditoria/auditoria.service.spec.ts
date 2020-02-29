import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AuditoriaService } from 'app/entities/sgq/auditoria/auditoria.service';
import { IAuditoria, Auditoria } from 'app/shared/model/sgq/auditoria.model';
import { ModalidadeAuditoria } from 'app/shared/model/enumerations/modalidade-auditoria.model';

describe('Service Tests', () => {
  describe('Auditoria Service', () => {
    let injector: TestBed;
    let service: AuditoriaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuditoria;
    let expectedResult: IAuditoria | IAuditoria[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AuditoriaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Auditoria(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        ModalidadeAuditoria.INTERNA,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Auditoria', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataInicio: currentDate,
            dataFim: currentDate
          },
          returnedFromService
        );
        service
          .create(new Auditoria())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Auditoria', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            modalidade: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT),
            auditor: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataRegistro: currentDate,
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

      it('should return a list of Auditoria', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            modalidade: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataInicio: currentDate.format(DATE_TIME_FORMAT),
            dataFim: currentDate.format(DATE_TIME_FORMAT),
            auditor: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
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

      it('should delete a Auditoria', () => {
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
