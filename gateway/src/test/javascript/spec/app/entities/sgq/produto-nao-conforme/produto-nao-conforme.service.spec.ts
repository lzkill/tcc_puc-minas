import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProdutoNaoConformeService } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.service';
import { IProdutoNaoConforme, ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';
import { StatusSGQ } from 'app/shared/model/enumerations/status-sgq.model';

describe('Service Tests', () => {
  describe('ProdutoNaoConforme Service', () => {
    let injector: TestBed;
    let service: ProdutoNaoConformeService;
    let httpMock: HttpTestingController;
    let elemDefault: IProdutoNaoConforme;
    let expectedResult: IProdutoNaoConforme | IProdutoNaoConforme[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProdutoNaoConformeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProdutoNaoConforme(0, 0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', StatusSGQ.REGISTRADO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataRegistro: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ProdutoNaoConforme', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate
          },
          returnedFromService
        );
        service
          .create(new ProdutoNaoConforme())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProdutoNaoConforme', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            analiseFinal: 'BBBBBB',
            statusSGQ: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataRegistro: currentDate
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

      it('should return a list of ProdutoNaoConforme', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            idUsuarioResponsavel: 1,
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            analiseFinal: 'BBBBBB',
            statusSGQ: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRegistro: currentDate
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

      it('should delete a ProdutoNaoConforme', () => {
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
