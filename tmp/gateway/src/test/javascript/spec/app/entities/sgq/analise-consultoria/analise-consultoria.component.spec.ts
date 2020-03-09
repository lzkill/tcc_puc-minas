import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { AnaliseConsultoriaComponent } from 'app/entities/sgq/analise-consultoria/analise-consultoria.component';
import { AnaliseConsultoriaService } from 'app/entities/sgq/analise-consultoria/analise-consultoria.service';
import { AnaliseConsultoria } from 'app/shared/model/sgq/analise-consultoria.model';

describe('Component Tests', () => {
  describe('AnaliseConsultoria Management Component', () => {
    let comp: AnaliseConsultoriaComponent;
    let fixture: ComponentFixture<AnaliseConsultoriaComponent>;
    let service: AnaliseConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [AnaliseConsultoriaComponent],
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
        .overrideTemplate(AnaliseConsultoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnaliseConsultoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnaliseConsultoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnaliseConsultoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.analiseConsultorias && comp.analiseConsultorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnaliseConsultoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.analiseConsultorias && comp.analiseConsultorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
