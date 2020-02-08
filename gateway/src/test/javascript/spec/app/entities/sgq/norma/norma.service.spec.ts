import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NormaService } from 'app/entities/sgq/norma/norma.service';
import { INorma, Norma } from 'app/shared/model/sgq/norma.model';

describe('Service Tests', () => {
  describe('Norma Service', () => {
    let injector: TestBed;
    let service: NormaService;
    let httpMock: HttpTestingController;
    let elemDefault: INorma;
    let expectedResult: INorma | INorma[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NormaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Norma(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataEdicao: currentDate.format(DATE_TIME_FORMAT),
            dataInicioValidade: currentDate.format(DATE_TIME_FORMAT)
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

      it('should return a list of Norma', () => {
        const returnedFromService = Object.assign(
          {
            orgao: 'BBBBBB',
            titulo: 'BBBBBB',
            descricao: 'BBBBBB',
            versao: 'BBBBBB',
            numeroEdicao: 1,
            dataEdicao: currentDate.format(DATE_TIME_FORMAT),
            dataInicioValidade: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataEdicao: currentDate,
            dataInicioValidade: currentDate
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
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
