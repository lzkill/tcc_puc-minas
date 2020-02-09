import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NaoConformidadeService } from 'app/entities/sgq/nao-conformidade/nao-conformidade.service';
import { INaoConformidade, NaoConformidade } from 'app/shared/model/sgq/nao-conformidade.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

describe('Service Tests', () => {
  describe('NaoConformidade Service', () => {
    let injector: TestBed;
    let service: NaoConformidadeService;
    let httpMock: HttpTestingController;
    let elemDefault: INaoConformidade;
    let expectedResult: INaoConformidade | INaoConformidade[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NaoConformidadeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new NaoConformidade(
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        false,
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

      it('should create a NaoConformidade', () => {
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
          .create(new NaoConformidade())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NaoConformidade', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            titulo: 'BBBBBB',
            fato: 'BBBBBB',
            procedente: true,
            causa: 'BBBBBB',
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT),
            analiseFinal: 'BBBBBB',
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

      it('should return a list of NaoConformidade', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            titulo: 'BBBBBB',
            fato: 'BBBBBB',
            procedente: true,
            causa: 'BBBBBB',
            prazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            novoPrazoConclusao: currentDate.format(DATE_TIME_FORMAT),
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataConclusao: currentDate.format(DATE_TIME_FORMAT),
            analiseFinal: 'BBBBBB',
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

      it('should delete a NaoConformidade', () => {
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
