import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CategoriaNormaService } from 'app/entities/sgq/categoria-norma/categoria-norma.service';
import { ICategoriaNorma, CategoriaNorma } from 'app/shared/model/sgq/categoria-norma.model';

describe('Service Tests', () => {
  describe('CategoriaNorma Service', () => {
    let injector: TestBed;
    let service: CategoriaNormaService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategoriaNorma;
    let expectedResult: ICategoriaNorma | ICategoriaNorma[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategoriaNormaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CategoriaNorma(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of CategoriaNorma', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            descricao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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
