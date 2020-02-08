import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { ProdutoNaoConformeComponent } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.component';
import { ProdutoNaoConformeService } from 'app/entities/sgq/produto-nao-conforme/produto-nao-conforme.service';
import { ProdutoNaoConforme } from 'app/shared/model/sgq/produto-nao-conforme.model';

describe('Component Tests', () => {
  describe('ProdutoNaoConforme Management Component', () => {
    let comp: ProdutoNaoConformeComponent;
    let fixture: ComponentFixture<ProdutoNaoConformeComponent>;
    let service: ProdutoNaoConformeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ProdutoNaoConformeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ProdutoNaoConformeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdutoNaoConformeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdutoNaoConformeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProdutoNaoConforme(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produtoNaoConformes && comp.produtoNaoConformes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProdutoNaoConforme(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produtoNaoConformes && comp.produtoNaoConformes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
