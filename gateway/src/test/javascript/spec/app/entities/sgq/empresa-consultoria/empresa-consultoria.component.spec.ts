import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { EmpresaConsultoriaComponent } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria.component';
import { EmpresaConsultoriaService } from 'app/entities/sgq/empresa-consultoria/empresa-consultoria.service';
import { EmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';

describe('Component Tests', () => {
  describe('EmpresaConsultoria Management Component', () => {
    let comp: EmpresaConsultoriaComponent;
    let fixture: ComponentFixture<EmpresaConsultoriaComponent>;
    let service: EmpresaConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [EmpresaConsultoriaComponent],
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
        .overrideTemplate(EmpresaConsultoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresaConsultoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpresaConsultoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmpresaConsultoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.empresaConsultorias && comp.empresaConsultorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmpresaConsultoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.empresaConsultorias && comp.empresaConsultorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
