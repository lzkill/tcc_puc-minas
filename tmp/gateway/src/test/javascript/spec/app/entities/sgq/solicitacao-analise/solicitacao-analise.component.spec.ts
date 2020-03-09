import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { GatewayTestModule } from '../../../../test.module';
import { SolicitacaoAnaliseComponent } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise.component';
import { SolicitacaoAnaliseService } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise.service';
import { SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

describe('Component Tests', () => {
  describe('SolicitacaoAnalise Management Component', () => {
    let comp: SolicitacaoAnaliseComponent;
    let fixture: ComponentFixture<SolicitacaoAnaliseComponent>;
    let service: SolicitacaoAnaliseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicitacaoAnaliseComponent],
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
        .overrideTemplate(SolicitacaoAnaliseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicitacaoAnaliseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicitacaoAnaliseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SolicitacaoAnalise(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.solicitacaoAnalises && comp.solicitacaoAnalises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SolicitacaoAnalise(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.solicitacaoAnalises && comp.solicitacaoAnalises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
