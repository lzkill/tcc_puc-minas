import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AcaoSGQService } from 'app/entities/sgq/acao-sgq/acao-sgq.service';
import { IAcaoSGQ, AcaoSGQ } from 'app/shared/model/sgq/acao-sgq.model';
import { TipoAcaoSGQ } from 'app/shared/model/enumerations/tipo-acao-sgq.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

describe('Service Tests', () => {
  describe('AcaoSGQ Service', () => {
    let injector: TestBed;
    let service: AcaoSGQService;
    let httpMock: HttpTestingController;
    let elemDefault: IAcaoSGQ;
    let expectedResult: IAcaoSGQ | IAcaoSGQ[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AcaoSGQService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AcaoSGQ(
        0,
        0,
        0,
        TipoAcaoSGQ.ACAO_CORRETIVA,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        StatusSGQ.REGISTRADO
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a AcaoSGQ', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prazoConclusao: currentDate,
            novoPrazoConclusao: currentDate,
            dataRegistro: currentDate,
            dataConclusao: currentDate
          },
          returnedFromService
        );
        service
          .create(new AcaoSGQ())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AcaoSGQ', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            tipo: 'BBBBBB',
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT),
            resultado: 'BBBBBB',
            statusSGQ: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            prazoConclusao: currentDate,
            novoPrazoConclusao: currentDate,
            dataRegistro: currentDate,
            dataConclusao: currentDate
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

      it('should return a list of AcaoSGQ', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            tipo: 'BBBBBB',
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT),
            resultado: 'BBBBBB',
            statusSGQ: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            prazoConclusao: currentDate,
            novoPrazoConclusao: currentDate,
            dataRegistro: currentDate,
            dataConclusao: currentDate
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

      it('should delete a AcaoSGQ', () => {
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
