import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SolicitacaoAnaliseDetailComponent } from 'app/entities/sgq/solicitacao-analise/solicitacao-analise-detail.component';
import { SolicitacaoAnalise } from 'app/shared/model/sgq/solicitacao-analise.model';

describe('Component Tests', () => {
  describe('SolicitacaoAnalise Management Detail Component', () => {
    let comp: SolicitacaoAnaliseDetailComponent;
    let fixture: ComponentFixture<SolicitacaoAnaliseDetailComponent>;
    const route = ({ data: of({ solicitacaoAnalise: new SolicitacaoAnalise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SolicitacaoAnaliseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SolicitacaoAnaliseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SolicitacaoAnaliseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load solicitacaoAnalise on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.solicitacaoAnalise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
