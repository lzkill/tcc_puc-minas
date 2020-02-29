import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SolicitacaoAnaliseService } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise.service';
import { ISolicitacaoAnalise, SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';
import { StatusSolicitacaoAnalise } from 'app/shared/model/enumerations/status-solicitacao-analise.model';

describe('Service Tests', () => {
  describe('SolicitacaoAnalise Service', () => {
    let injector: TestBed;
    let service: SolicitacaoAnaliseService;
    let httpMock: HttpTestingController;
    let elemDefault: ISolicitacaoAnalise;
    let expectedResult: ISolicitacaoAnalise | ISolicitacaoAnalise[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SolicitacaoAnaliseService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SolicitacaoAnalise(0, 0, currentDate, currentDate, StatusSolicitacaoAnalise.REGISTRADO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a SolicitacaoAnalise', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataSolicitacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new SolicitacaoAnalise())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SolicitacaoAnalise', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataSolicitacao: currentDate
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

      it('should return a list of SolicitacaoAnalise', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            dataSolicitacao: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate,
            dataSolicitacao: currentDate
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

      it('should delete a SolicitacaoAnalise', () => {
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
