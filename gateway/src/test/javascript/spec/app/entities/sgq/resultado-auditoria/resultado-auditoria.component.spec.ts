import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoAuditoriaComponent } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.component';
import { ResultadoAuditoriaService } from 'app/entities/sgq/resultado-auditoria/resultado-auditoria.service';
import { ResultadoAuditoria } from 'app/shared/model/sgq/resultado-auditoria.model';

describe('Component Tests', () => {
  describe('ResultadoAuditoria Management Component', () => {
    let comp: ResultadoAuditoriaComponent;
    let fixture: ComponentFixture<ResultadoAuditoriaComponent>;
    let service: ResultadoAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoAuditoriaComponent],
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
        .overrideTemplate(ResultadoAuditoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadoAuditoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadoAuditoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResultadoAuditoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resultadoAuditorias && comp.resultadoAuditorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResultadoAuditoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resultadoAuditorias && comp.resultadoAuditorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
