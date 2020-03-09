import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { ItemPlanoAuditoriaComponent } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria.component';
import { ItemPlanoAuditoriaService } from 'app/entities/sgq/item-plano-auditoria/item-plano-auditoria.service';
import { ItemPlanoAuditoria } from 'app/shared/model/sgq/item-plano-auditoria.model';

describe('Component Tests', () => {
  describe('ItemPlanoAuditoria Management Component', () => {
    let comp: ItemPlanoAuditoriaComponent;
    let fixture: ComponentFixture<ItemPlanoAuditoriaComponent>;
    let service: ItemPlanoAuditoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ItemPlanoAuditoriaComponent],
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
        .overrideTemplate(ItemPlanoAuditoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemPlanoAuditoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemPlanoAuditoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemPlanoAuditoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemPlanoAuditorias && comp.itemPlanoAuditorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ItemPlanoAuditoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.itemPlanoAuditorias && comp.itemPlanoAuditorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
