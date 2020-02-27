import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AnexoService } from 'app/entities/sgq/anexo/anexo.service';
import { IAnexo, Anexo } from 'app/shared/model/sgq/anexo.model';

describe('Service Tests', () => {
  describe('Anexo Service', () => {
    let injector: TestBed;
    let service: AnexoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnexo;
    let expectedResult: IAnexo | IAnexo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnexoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Anexo(0, 0, currentDate, 'AAAAAAA', 'image/png', 'AAAAAAA');
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

      it('should create a Anexo', () => {
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
          .create(new Anexo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Anexo', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            nomeArquivo: 'BBBBBB',
            conteudo: 'BBBBBB'
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

      it('should return a list of Anexo', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioRegistro: 1,
            dataRegistro: currentDate.format(DATE_TIME_FORMAT),
            nomeArquivo: 'BBBBBB',
            conteudo: 'BBBBBB'
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

      it('should delete a Anexo', () => {
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
