import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ResultadoChecklistDetailComponent } from 'app/entities/sgq/resultado-checklist/resultado-checklist-detail.component';
import { ResultadoChecklist } from 'app/shared/model/sgq/resultado-checklist.model';

describe('Component Tests', () => {
  describe('ResultadoChecklist Management Detail Component', () => {
    let comp: ResultadoChecklistDetailComponent;
    let fixture: ComponentFixture<ResultadoChecklistDetailComponent>;
    const route = ({ data: of({ resultadoChecklist: new ResultadoChecklist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [ResultadoChecklistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultadoChecklistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadoChecklistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultadoChecklist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultadoChecklist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
